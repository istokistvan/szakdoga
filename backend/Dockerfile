#Backend Dockerfile
# First Stage: Build the JAR
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar -x test

# Second Stage: Run the Application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
