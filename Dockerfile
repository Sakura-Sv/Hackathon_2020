# Build Stage for Spring boot application image
FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java","-jar","app.jar"]