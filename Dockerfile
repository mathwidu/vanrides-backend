# Etapa 1: imagem base para build
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
RUN apt-get install maven -y

# Copia os arquivos do projeto para o container
COPY . .

# Compila o projeto
RUN mvn clean package -DskipTests

# Etapa 2: imagem leve para rodar a aplicação
FROM openjdk:17-jdk-slim

EXPOSE 8080

# Copia o .jar da etapa de build
COPY --from=build target/*.jar app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
