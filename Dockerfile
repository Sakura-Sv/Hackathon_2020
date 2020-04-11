# Build Stage for Spring boot application image
FROM openjdk:8-jdk

ARG MYSQL_PASSWORD

ENV MYSQL_PASSWORD ${MYSQL_PASSWORD}

COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java", "-jar", "app.jar"]