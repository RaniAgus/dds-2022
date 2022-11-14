FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /app

COPY pom.xml ./

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ./

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

RUN sed -i "s,name=\"hibernate\.connection\.url\" value=\".*\",name=\"hibernate\.connection\.url\" value=\"${DB_URL}\",g" "src/main/resources/META-INF/persistence.xml" \
    && sed -i "s,name=\"hibernate\.connection\.username\" value=\".*\",name=\"hibernate\.connection\.username\" value=\"${DB_USERNAME}\",g" "src/main/resources/META-INF/persistence.xml" \
    && sed -i "s,name=\"hibernate\.connection\.password\" value=\".*\",name=\"hibernate\.connection\.password\" value=\"${DB_PASSWORD}\",g" "src/main/resources/META-INF/persistence.xml"

RUN mvn package


FROM openjdk:8-jre-alpine as cron

ARG CRON_ENVIO_GUIA

WORKDIR /etc/cron.d

RUN echo "${CRON_ENVIO_GUIA} sh -c \"java -cp /app/target/application.jar ar.edu.utn.frba.dds.impactoambiental.JobEnvioGuia\"" >> cronjob

RUN chmod 0644 cronjob

RUN crontab cronjob

WORKDIR /app/target

COPY --from=builder /app/target ./

ENTRYPOINT ["crond", "-f"]


FROM openjdk:8-jre-alpine as java

WORKDIR /app/target

COPY --from=builder /app/target ./
COPY public ./public

ENTRYPOINT ["java", "-jar", "application.jar"]
