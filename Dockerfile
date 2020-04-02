# Build Stage for Spring boot application image
FROM openjdk:8-jdk


COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar

# 芜湖 起飞✈
ENTRYPOINT ["java", "-jar", "app.jar"]