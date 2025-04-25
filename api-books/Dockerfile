FROM openjdk:21
LABEL authors="karla"

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged application JAR file into the container at /app
COPY target/api-books-0.0.1-SNAPSHOT.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8081

# Command to start the service
CMD ["java", "-jar", "app.jar"]
