FROM maven:3.8.7 as build
COPY . .
RUN mvn  package

FROM openjdk:17
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar",  "app.jar"]