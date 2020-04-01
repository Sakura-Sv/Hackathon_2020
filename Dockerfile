# Build Stage for Spring boot application image
FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw
# download the dependency if needed or if the pom file is changed
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

#ARG JAR_FILE=/app/target/demo-0.0.1-SNAPSHOT.jar
#
## cp target/spring-boot-web.jar /app/app.jar
COPY /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java","-jar","app.jar"]