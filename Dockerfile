FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /app

COPY pom.xml ./

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ./

RUN mvn package


FROM openjdk:8-jre-alpine as cron

ADD crontab /etc/cron.d/cronjob

RUN chmod 0644 /etc/cron.d/cronjob

RUN crontab /etc/cron.d/cronjob

WORKDIR /app/target

COPY --from=builder /app/target ./
COPY start.sh ./

ENTRYPOINT ["sh", "start.sh", "crond -f"]


FROM openjdk:8-jre-alpine as java

WORKDIR /app/target

COPY --from=builder /app/target ./
COPY public ./public
COPY start.sh ./

ENTRYPOINT ["sh", "start.sh", "java -jar application.jar"]
