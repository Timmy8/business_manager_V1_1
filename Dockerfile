FROM maven:3.8.7-openjdk-17 AS build

WORKDIR /build

COPY . .
COPY pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

RUN adduser --system run-user && addgroup --system management-application && adduser run-user management-application
USER run-user

WORKDIR /app

COPY --from=build build/core-api-service/target/core-api-service.jar ./core-api-service.jar
COPY --from=build build/manager-ui-app/target/manager-ui-app.jar ./manager-ui-app.jar

RUN mkdir -p /app/logs && chown -R run-user:management-application /app/logs

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]