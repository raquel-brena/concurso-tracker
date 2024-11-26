# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Garantir que o mvnw seja executável
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]