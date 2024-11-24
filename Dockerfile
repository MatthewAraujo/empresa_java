# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copiar o código fonte para o contêiner
COPY . ./

# Criar e escrever as variáveis no arquivo .env
RUN echo "DATABASE_URL=jdbc:postgresql://junction.proxy.rlwy.net:46588/railway" > /app/.env && \
    echo "DATABASE_USER=postgres" >> /app/.env && \
    echo "DATABASE_PASSWORD=WcqUfNlqKdgPgIjzkmNjVMcAHiuYRnaY" >> /app/.env

# Construir o projeto
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copiar o JAR para o contêiner
COPY --from=build /app/target/projetoapi-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta
EXPOSE 8081

# Iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

