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
COPY --from=build /app/target/AffinityVmJar-jar-with-dependencies.jar ./app.jar
COPY DataBases.json /app/config/DataBases.json

COPY json-20210307.jar/* /app/lib/
ADD target/AffinityVmJar-jar-with-dependencies.jar /app.jar

CMD ["java", "-cp", "/app/app.jar:/app/lib/*", "org.example.RunAffinityVm"]