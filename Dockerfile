# Build Stage for Spring boot application image
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/demo-0.0.1-SNAPSHOT.jar /app/

# 芜湖 起飞✈
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]