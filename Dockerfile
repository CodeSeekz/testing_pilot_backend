# my-backend-repo/Dockerfile

# Stage 1: Build the application
# FROM eclipse-temurin:17-jdk-jammy as builder # Original
FROM eclipse-temurin:17-jdk-jammy AS builder # Fixed casing warning (Optional)
WORKDIR /app

# Copy build configuration first
COPY ./mvnw .
COPY ./.mvn ./.mvn
COPY ./pom.xml .

# ---> FIX: Add this line to make the script executable <---
RUN chmod +x ./mvnw

# Download dependencies. -B runs in non-interactive (batch) mode.
RUN ./mvnw dependency:go-offline -B # This should now have permission to run

# Copy the source code
COPY ./src ./src

# Build the application JAR. Skip tests for faster CI builds.
RUN ./mvnw package -DskipTests -B

# Stage 2: Create the final production image using a JRE for smaller size
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
