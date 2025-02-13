# Usa una imagen base de Java con JDK 17 (ajusta si usas otra versión)
FROM eclipse-temurin:21-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar al contenedor
COPY taskManager-0.0.1-SNAPSHOT.jar app.jar

# Expón el puerto que usa Spring Boot (por defecto, 8080)
EXPOSE 8080

# Comando para ejecutar el archivo .jar
CMD ["java", "-jar", "app.jar"]
