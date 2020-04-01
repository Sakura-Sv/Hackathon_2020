# Build Stage for Spring boot application image
FROM openjdk:8-jdk-alpine AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
# download the dependency if needed or if the pom file is changed
RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine

#ARG JAR_FILE=/app/target/demo-0.0.1-SNAPSHOT.jar
#
## cp target/spring-boot-web.jar /app/app.jar
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java","-jar","app.jar"]