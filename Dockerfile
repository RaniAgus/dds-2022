FROM maven:3.8.6-openjdk-8 as builder

WORKDIR /usr/src

COPY pom.xml ./

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

COPY . ./

RUN mvn package

FROM openjdk:8-jdk-alpine

COPY --from=builder /usr/src/target ./

ENTRYPOINT [ "java", "-jar" ]
