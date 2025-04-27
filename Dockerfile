# my-backend-repo/Dockerfile

# Stage 1: Build the application using a specific JDK version
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app

# Copy build configuration first to leverage Docker cache for dependencies
COPY ./mvnw .
COPY ./.mvn ./.mvn
COPY ./pom.xml .

# Download dependencies. -B runs in non-interactive (batch) mode.
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY ./src ./src

# Build the application JAR. Skip tests for faster CI builds.
RUN ./mvnw package -DskipTests -B

# Stage 2: Create the final production image using a JRE for smaller size
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Argument to easily locate the JAR file, handles different naming patterns
ARG JAR_FILE=target/*.jar

# Copy the built JAR from the builder stage
COPY --from=builder /app/${JAR_FILE} app.jar

# Expose the port your Spring Boot application listens on (defined in application.properties/yml)
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
