# Imagen base de Java 17
FROM eclipse-temurin:17-jdk

# Instalar netcat para esperar a MySQL
RUN apt-get update && apt-get install -y netcat-openbsd

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar desde la máquina host al contenedor
COPY target/*.jar app.jar

# Expone el puerto de la app
EXPOSE 7071

# Comando para esperar a MySQL y luego ejecutar la app
ENTRYPOINT ["sh", "-c", "echo 'Esperando a MySQL...'; until nc -z mysql 3306; do sleep 2; done; echo 'MySQL listo. Arrancando app...'; java -jar app.jar"]
