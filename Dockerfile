# Build del backend con Maven.
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .
COPY src src

# Empaqueta el JAR ejecutable sin tests para imagen final.
RUN chmod +x mvnw && ./mvnw -DskipTests package

# Runtime liviano con JRE.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# curl se usa para healthcheck del contenedor.
RUN apk add --no-cache curl

COPY --from=build /app/target/backend.jar app.jar

EXPOSE 3000
# Comando de arranque de la API.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
