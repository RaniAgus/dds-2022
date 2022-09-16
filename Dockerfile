FROM maven:3.8.6-openjdk-8 as builder

WORKDIR /usr/src

COPY pom.xml ./

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ./

RUN mvn package


FROM openjdk:8-jdk-alpine as cron

ADD crontab /etc/cron.d/cronjob

RUN chmod 0644 /etc/cron.d/cronjob

RUN crontab /etc/cron.d/cronjob

WORKDIR /usr/src/target

COPY --from=builder /usr/src/target ./

ENTRYPOINT ["crond", "-f"]


FROM openjdk:8-jdk-alpine as java

WORKDIR /usr/src/target

COPY --from=builder /usr/src/target ./

ENTRYPOINT [ "java", "-jar", "application.jar" ]
