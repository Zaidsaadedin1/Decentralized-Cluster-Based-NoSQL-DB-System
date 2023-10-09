# Build stage
FROM maven:3.8.4 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Final stage
FROM openjdk:15
WORKDIR /app
COPY --from=build /app/target/lib/Clients-1.0-SNAPSHOT.jar ./app.jar
ADD target/lib/Clients-1.0-SNAPSHOT.jar /app.jar

# Copy the contents of the json-20210307.jar directory to /app/lib/
COPY json-20210307.jar/* /app/lib/

CMD ["java", "-cp", "/app/app.jar:/app/lib/*", "org.example.RunClients"]