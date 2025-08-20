# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml files
COPY ./mvnw ./mvnw
COPY ./mvnw.cmd ./mvnw.cmd
COPY ./.mvn ./.mvn
COPY ./pom.xml ./pom.xml

# Copy the source code
COPY ./src ./src

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port your app runs on (change if not 8080)
EXPOSE 8090 

# Run the jar file
CMD ["java", "-jar", "target/property-plug-0.0.1-SNAPSHOT.jar"] 