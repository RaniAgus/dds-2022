FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /app

COPY pom.xml ./

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ./

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

RUN mvn package -Dhibernate.connection.url=${DB_URL} -Dhibernate.connection.username=${DB_USERNAME} -Dhibernate.connection.password=${DB_PASSWORD}


FROM openjdk:8-jre-alpine as cron

ARG CRON_ENVIO_GUIA

WORKDIR /etc/cron.d

RUN echo "${CRON_ENVIO_GUIA} sh -c \"java -cp /app/target/application.jar ar.edu.utn.frba.dds.impactoambiental.JobEnvioGuia\"" >> cronjob

RUN chmod 0644 cronjob

RUN crontab cronjob

WORKDIR /app/target

COPY --from=builder /app/target ./

ENTRYPOINT ["crond", "-f"]
