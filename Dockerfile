# Build Stage for Spring boot application image
FROM openjdk:8-jdk

WORKDIR /app

COPY ./target/application-0.0.1-SNAPSHOT.jar /app/app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java", "-jar", "/app/app.jar"]