# Blog Management API

## Descripción

Esta aplicación proporciona una API REST para la gestión de blogs. Permite crear, actualizar y obtener blogs, así como registrar y recuperar comentarios en los blogs. Está implementada en Java utilizando Spring Boot y emplea una base de datos en memoria H2 para simplificar el desarrollo y pruebas.

## Estrategia/Tecnología/Frameworks Utilizados

- **Java**: Lenguaje de programación utilizado para el desarrollo de la aplicación.
- **Spring Boot**: Framework para la creación de aplicaciones Java basadas en Spring. Utilizado para gestionar la configuración de la aplicación y las dependencias.
- **Spring Data JPA**: Proporciona una interfaz para la interacción con la base de datos. Facilita la implementación de repositorios y la gestión de entidades.
- **H2 Database**: Base de datos en memoria utilizada para simplificar el desarrollo y las pruebas. No es necesario configurar una base de datos externa para el funcionamiento de la aplicación.
- **Docker**: Utilizado para contenerizar la aplicación y facilitar su despliegue en entornos diversos. Incluye un archivo Dockerfile para construir una imagen de Docker de la aplicación y un archivo docker-compose.yml para ejecutar el contenedor.
- **JHipster** (como base de código): Proporciona una estructura inicial y configuraciones para facilitar y agilizar el desarrollo del proyecto.

## Ejecutar la Aplicación

Para ejecutar la aplicación, usa el siguiente comando:

```
./gradlew
```

La aplicación estará disponible en http://localhost:8080.

## Docker

Si deseas desplegar la aplicación en un contenedor Docker, primero asegúrate de tener Docker instalado en tu sistema. Luego, contruye la aplicacion, construye la imagen y ejecuta el contenedor usando los siguientes comandos:

Construir la aplicacion

```
./gradlew clean bootJar
```
Verifica la ejecución del JAR (Opcional)

```
java -jar .\build\libs\blog-api-0.0.1-SNAPSHOT.jar
```
Construir la imagen

```
docker build -t blog-api:latest .   
```
Ejecuta el contenedor

```
docker-compose up -d
```

## Documentación de la API

### Colección de Postman

Se ha proporcionado una colección de Postman que incluye ejemplos de solicitudes para todos los endpoints de la API. Puedes importar esta colección en Postman para explorar y probar la API fácilmente.

- **Archivo de Colección de Postman**: [Blog API.postman_collection.json](docs/Blog%20API.postman_collection.json)

### Archivo de Entorno de Postman

También se incluye un archivo de entorno de Postman que permite configurar variables como la `baseUrl` para que puedas modificar la URL base de la API según tu entorno de ejecución.

- **Archivo de Entorno de Postman**: [Blog API.postman_environment.json](docs/Blog%20API.postman_environment.json)