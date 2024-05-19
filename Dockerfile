FROM amazoncorretto:17-alpine3.16

LABEL MANTAINER="Dowglas Maia"

ENV SPRING_LOGGING_LEVEL INFO
ENV ACTUATOR_PORT 8088
ENV PORT 8088

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /usr/src/app/address-api.jar

RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

WORKDIR /usr/src/app

ENTRYPOINT java \
           -noverify \
           -Dfile.encoding=UTF-8 \
           -Dlogging.level.root=${SPRING_LOGGING_LEVEL} \
           -Dmanagement.server.port=${ACTUATOR_PORT} \
           -jar \
           /usr/src/app/address-api.jar \
           --server.port=${PORT}

EXPOSE ${PORT} ${ACTUATOR_PORT}
