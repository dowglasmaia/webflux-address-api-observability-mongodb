FROM amazoncorretto:17-alpine3.16

ARG JAR_FILE=target/*.jar
ARG OLTP=opentelemetry/opentelemetry-javaagent.jar

COPY ${JAR_FILE} /usr/src/app/address-api.jar
COPY ${OLTP} /usr/src/app/opentelemetry-javaagent.jar

WORKDIR /usr/src/app
EXPOSE 8085

RUN rm -f /etc/localtime && \
    ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

CMD ["java","-Duser.timezone=UTC-3","-javaagent:/usr/src/app/opentelemetry-javaagent.jar","-jar","address-api.jar"]
