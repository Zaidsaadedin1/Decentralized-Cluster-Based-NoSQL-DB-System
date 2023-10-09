# Build stage
FROM maven:3.8.4 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

# Final stage
FROM openjdk:15
WORKDIR /app
COPY --from=build /app/target/BootsTrapingNode-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar
COPY registered_clients.json /app/config/registered_clients.json
COPY registered_admins.json /app/config/registered_admins.json
COPY json-20210307.jar/* /app/lib/
ADD target/BootsTrapingNode-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

CMD ["java", "-cp", "/app/app.jar:/app/lib/*", "org.example.RunBootStrappingServer"]
