#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app2
COPY src src
COPY pom.xml .
COPY lib lib
RUN mvn clean package

#
# Package stage
#

FROM openjdk:11-jre-slim

WORKDIR /usr/local/lib/
COPY corpus corpus
COPY dict dict
COPY data data
COPY sensesRelations.xml .
COPY synsets.xml .
COPY synsetsRelation.xml .
COPY verbFrames.xml .
COPY verbFrames.xml VerbFrames.xml
COPY words.xml .

COPY --from=build /home/app2/target/gs-handling-form-submission-0.1.0.jar wsd.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wsd.jar"]
