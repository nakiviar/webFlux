# Imagen base de Java 17 o 21
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar desde la máquina host al contenedor
COPY target/*.jar app.jar

# Expone el puerto de la app
EXPOSE 7071

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
