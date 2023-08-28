# Documento Técnico

## Introducción

Este documento proporciona una visión general de la arquitectura y tecnologías utilizadas en el desarrollo de la plataforma de noticias. La plataforma consta de una interfaz web de una sola página (SPA) desarrollada con Angular y Angular Material, y un microservicio desarrollado con Java, Spring Boot y Spring Security.

## Arquitectura de la Solución

La solución se compone de dos partes principales:

1. **Interfaz Web SPA:** Es la parte frontal de la aplicación que interactuará directamente con los usuarios. Está desarrollada utilizando Angular y Angular Material, y se encarga de mostrar las noticias más recientes, permitir la búsqueda, ordenamiento y paginación de las noticias, y permitir a los usuarios guardar noticias en favoritos.

2. **Microservicio:** Es la parte backend de la aplicación y se encarga de gestionar las noticias favoritas de los usuarios. Está desarrollada utilizando Java, Spring Boot y Spring Security, y almacena las noticias favoritas en una base de datos en memoria (H2).

La interfaz web consumirá un API de terceros para obtener las noticias y un API propia para gestionar los favoritos.

## Tecnologías Utilizadas

- **Angular:** Utilizado para el desarrollo de la interfaz web SPA. Es un framework de desarrollo para construir aplicaciones web de una sola página.

- **Angular Material:** Biblioteca de componentes de UI para Angular. Se utiliza para el diseño de la interfaz web.

- **Java:** Lenguaje de programación utilizado para el desarrollo del microservicio.

- **Spring Boot:** Framework utilizado para el desarrollo del microservicio. Facilita la creación de aplicaciones basadas en Spring.

- **Spring Security:** Framework de seguridad para Java. Se utiliza para manejar la autenticación y autorización en el microservicio.

- **H2 Database:** Base de datos en memoria utilizada para almacenar las noticias favoritas en el microservicio.

- **Gradle:** Herramienta de automatización de la construcción utilizada para gestionar las dependencias y construir el microservicio.

- **Swagger (OpenAPI):** Utilizado para documentar los endpoints del API del microservicio.

## Instalación y Configuración

### Requisitos

- Java 17
- Npm 9.5.0
- Node.js 18.15.0
- Angular CLI 15.2.6

### Pasos de Instalación

1. **Instalar Node.js y npm:** Si no están instalados, descargar e instalar Node.js y npm desde el [sitio oficial](https://nodejs.org/).

2. **Instalar Angular CLI:** Ejecutar el siguiente comando para instalar Angular CLI globalmente:
    ```
    npm install -g @angular/cli
    ```

3. **Clonar el Repositorio:** Clonar el repositorio de GitHub que contiene el código fuente de la plataforma.

4. **Instalar Dependencias:**
    - Navegar al directorio del proyecto de Angular y ejecutar:
        ```
        npm install
        ```
    - Navegar al directorio del proyecto de Spring Boot y ejecutar:
        ```
        ./gradlew build
        ```

## Compilación y Ejecución

### Interfaz Web SPA

1. Navegar al directorio del proyecto de Angular.
2. Ejecutar el siguiente comando para compilar y ejecutar la aplicación:
    ```
    ng serve
    ```
3. Abrir un navegador web y acceder a `http://localhost:4200/`.

### Microservicio

1. Navegar al directorio del proyecto de Spring Boot.
2. Ejecutar el siguiente comando para compilar y ejecutar la aplicación:
    ```
    ./gradlew bootRun
    ```
3. El microservicio estará en ejecución en `http://localhost:8080/`.

## API

La documentación del API del microservicio se puede encontrar en el contrato Swagger (OpenAPI) proporcionado en formato YML en la ruta.
```
src/main/resources/api-docs.yaml
```
o en el enlace de Swagger Ui 
```
http://localhost:8080/swagger-ui/index.html
```
## Pruebas

### Pruebas Unitarias

Para ejecutar las pruebas unitarias:

- Para la interfaz web SPA, navegar al directorio del proyecto de Angular y ejecutar:
    ```
    ng test
    ```

- Para el microservicio, navegar al directorio del proyecto de Spring Boot y ejecutar:
    ```
    ./gradlew test
    ```

## Despliegue

### Interfaz Web SPA

1. Compilar la aplicación para producción ejecutando el siguiente comando en el directorio del proyecto de Angular:
    ```
    ng build --prod
    ```
2. El código compilado se encontrará en el directorio `dist/`. Este código se puede desplegar en cualquier servidor web.

### Microservicio

1. Empaquetar la aplicación ejecutando el siguiente comando en el directorio del proyecto de Spring Boot:
    ```
    ./gradlew bootJar
    ```
2. El archivo JAR generado se encontrará en el directorio `build/libs/`. Este archivo se puede ejecutar en cualquier servidor con Java instalado.

## Conclusión

Este documento proporciona una guía para la instalación, configuración, compilación, ejecución y despliegue de la plataforma de noticias. La solución consta de una interfaz web SPA desarrollada con Angular y Angular Material, y un microservicio desarrollado con Java, Spring Boot y Spring Security.
