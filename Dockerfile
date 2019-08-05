#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app2/src
COPY pom.xml /home/app2
COPY lib /home/app2/lib
COPY corpus /home/app2/corpus
RUN mvn -f /home/app2/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app2/target/gs-handling-form-submission-0.1.0.jar /usr/local/lib/gs-handling-form-submission-0.2.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/gs-handling-form-submission-0.2.0.jar"]