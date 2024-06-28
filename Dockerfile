# Build Stage
FROM maven:3.9.7-amazoncorretto-17 AS build
WORKDIR /address-service

# Copy only the necessary files first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the files and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM amazoncorretto:17-alpine3.16
LABEL maintainer="Dowglas Maia"

# Environment Variables
ENV SPRING_LOGGING_LEVEL=INFO
ENV ACTUATOR_PORT=8088
ENV PORT=8088

# Install timezone package and set timezone
RUN apk --no-cache add tzdata \
    && cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime \
    && echo "America/Sao_Paulo" > /etc/timezone \
    && apk del tzdata

# Create application directory
WORKDIR /usr/src/app

# Copy the built JAR from the build stage
COPY --from=build /address-service/target/*.jar /usr/src/app/address-api.jar

# Copy the OpenTelemetry agent
COPY ./opentelemetry/opentelemetry-javaagent.jar /usr/src/app/opentelemetry-javaagent.jar

# Verify the JAR file exists
RUN if [ ! -f /usr/src/app/address-api.jar ]; then echo "JAR file not found"; exit 1; fi

# Verify the OpenTelemetry agent exists
RUN if [ ! -f /usr/src/app/opentelemetry-javaagent.jar ]; then echo "OpenTelemetry agent not found"; exit 1; fi

# Expose ports
EXPOSE ${PORT} ${ACTUATOR_PORT}

# Entry Point
ENTRYPOINT ["java", \
            "-noverify", \
            "-Dfile.encoding=UTF-8", \
            "-Dlogging.level.root=${SPRING_LOGGING_LEVEL}", \
            "-Dmanagement.server.port=${ACTUATOR_PORT}", \
            "-javaagent:/usr/src/app/opentelemetry-javaagent.jar", \
            "-Dotel.service.name=address-api", \
            "-Dotel.traces.exporter=otlp", \
            "-Dotel.metrics.exporter=none", \
            "-Dotel.exporter.otlp.endpoint=http://collector-api:4318", \
            "-Dotel.exporter.otlp.protocol=http/protobuf", \
            "-jar", "/usr/src/app/address-api.jar", \
            "--server.port=${PORT}"]
