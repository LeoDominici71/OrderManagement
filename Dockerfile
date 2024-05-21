FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

# Copiar o jar da fase anterior para o diretório de trabalho
COPY --from=build /app/target/orderManagement-0.0.1-SNAPSHOT.jar app.jar

# Define a variável de ambiente para o perfil ativo
ENV SPRING_PROFILES_ACTIVE=test