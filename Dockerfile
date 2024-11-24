# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw

# Go offline and download dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN mvn clean package -DskipTests

# Check if the JAR file was generated
RUN ls /app/target

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/projetoapi-0.0.1-SNAPSHOT.jar app.jar


# Expose port 8090
EXPOSE 8081

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
