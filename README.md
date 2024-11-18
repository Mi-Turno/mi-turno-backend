# Mi Turno Backend

## 1. Descripción General

El backend de **Mi Turno** es el núcleo del sistema, gestionando la lógica de negocio, la persistencia de datos y la comunicación con el frontend. Entre sus funcionalidades principales se encuentran:

- **ABM (Alta, Baja, Modificación)**: Gestión completa de los recursos del sistema como negocios, profesionales, servicios, clientes y turnos.
- **CRUD**: Implementa operaciones básicas de creación, lectura, actualización y eliminación para manejar los datos de los usuarios y servicios.
- **Envío de correos electrónicos**: Sistema integrado para enviar notificaciones por correo, como confirmaciones de turnos o actualizaciones a los usuarios.
- **Persistencia de datos**: Todos los datos se almacenan y gestionan de manera eficiente en una base de datos relacional utilizando JPA e Hibernate.
- **Conexión del frontend con la base de datos**: A través de una API REST, el backend se encarga de procesar las solicitudes del frontend y devolver los datos necesarios.
- **API REST**: Permite que el frontend y otros sistemas se comuniquen con el backend mediante peticiones HTTP.
- **Lógica del negocio**: El backend contiene toda la lógica empresarial necesaria para la correcta operación del sistema de turnos, incluyendo la validación de horarios, disponibilidad y gestión de reservas.
- **Seguridad y robustez del sistema**: Utiliza mecanismos de seguridad como Spring Security y JWT para proteger las rutas y gestionar las autenticaciones de los usuarios, asegurando que solo los usuarios autorizados puedan acceder a los datos sensibles.

### Cómo se comunica

El backend se comunica con el frontend y otros sistemas a través de **peticiones HTTP**, utilizando una arquitectura de API REST para garantizar flexibilidad y escalabilidad en la integración.

---

## 2. Tecnologías Utilizadas

El backend de **Mi Turno** está construido utilizando las siguientes tecnologías:

- **Java**: Lenguaje de programación utilizado para el desarrollo de la aplicación.
- **Spring Boot**: Framework para la creación de aplicaciones Java basadas en microservicios.
  - **Spring Security**: Framework para la gestión de la seguridad y autenticación en la aplicación.
  - **Spring Data**: Facilita la interacción con la base de datos mediante repositorios JPA.
  - **Spring Email**: Utilizado para el envío de correos electrónicos dentro del sistema.
- **Lombok**: Librería que reduce la escritura de código repetitivo, como getters, setters, constructores, etc.
- **Maven**: Herramienta de automatización de proyectos y gestión de dependencias.
- **JWT (JSON Web Tokens)**: Utilizado para gestionar la autenticación de usuarios y proteger las rutas de la API.
- **JPA (Java Persistence API) con Hibernate**: ORM que facilita la interacción con la base de datos y la persistencia de datos.

---

## 3. Estructura del Proyecto
```
mi-turno-backend
├── .idea
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.miTurno.backend
│   │   │       ├── configuracion
│   │   │       │   ├── dbInicializador
│   │   │       │   └── ConfiguracionWeb
│   │   │       ├── controlador
│   │   │       ├── DTO
│   │   │       ├── entidad
│   │   │       ├── excepcion
│   │   │       ├── manejador
│   │   │       ├── mapper
│   │   │       ├── repositorio
│   │   │       ├── request
│   │   │       ├── servicio
│   │   │       └── tipos
│   │   │       └── BackendApplication
│   │   └── resources
│   │       └── application.properties
│   ├── test
├── target
├── .env
├── .gitignore
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── External Libraries
````
---

## Descripción de Carpetas Principales

- **configuracion**: Contiene las configuraciones necesarias para el backend, como inicializadores de la base de datos y configuraciones web.
- **controlador**: Controladores que gestionan las solicitudes HTTP.
- **DTO**: Objetos de transferencia de datos que se utilizan para enviar información entre las distintas capas del sistema.
- **entidad**: Definición de las entidades del dominio del negocio, que representan los datos en la base de datos.
- **excepcion**: Clases para manejar excepciones personalizadas dentro del sistema.
- **manejador**: Lógica específica para la gestión de acciones dentro del sistema, como el manejo de turnos o usuarios.
- **mapper**: Mappers que convierten entre entidades y DTOs para asegurar que los datos se transfieran correctamente entre las capas.
- **repositorio**: Interfaces para interactuar con la base de datos mediante JPA.
- **request**: Objetos utilizados para recibir datos en las solicitudes HTTP, permitiendo una estructura clara y validada.
- **servicio**: Contiene la lógica de negocio principal, gestionando las operaciones críticas del sistema.
- **tipos**: Enumeraciones o clases auxiliares para definir tipos específicos utilizados en el sistema.
- **resources/application.properties**: Archivo de configuración para el proyecto, donde se definen parámetros como conexiones a la base de datos, puertos y otras configuraciones esenciales.

## 4. Configuración e Instalación

### Requisitos Previos

Para ejecutar este proyecto, necesitarás las siguientes herramientas y configuraciones:

- **Java 11 o superior**: Asegúrate de tener la versión correcta de Java instalada en tu máquina.
- **Maven**: Necesitarás Maven para gestionar las dependencias y construir el proyecto.
- **MySQL**: Asegúrate de tener una base de datos MySQL configurada y corriendo.
- **Archivo `.env`**: El archivo de configuración `.env` debe contener las siguientes variables para el envío de correos y otras configuraciones:

    ```env
    MAIL_HOST=smtp.gmail.com
    MAIL_PORT=587
    MAIL_PROTOCOL=smtp
    MAIL_USERNAME=<tu_correo>
    MAIL_PASSWORD=<tu_contraseña>
    MAIL_SMTP_AUTH=true
    MAIL_STARTTLS_ENABLE=true
    MAIL_STARTTLS_REQUIRED=true
    MAIL_SSL_TRUST=smtp.gmail.com
    ```

### Pasos para Instalar y Ejecutar el Backend

1. Clona el repositorio del backend:
   ```bash
   git clone https://github.com/Mi-turno/mi-turno-backend.git
2. cd mi-turno-backend
3. Configura las variables de entorno, creando un archivo .env en la raíz del proyecto y agregando las credenciales de correo.
4. Crea una Base de datos con el nombre "mi-turno".
5. Inicializa un servidor de mySql y apache como XAMPP o WAMPP
6. Compila el proyecto utilizando Maven:
   mvn clean install
7. Inicia el servidor:
   mvn spring-boot:run
8. El backend estará corriendo en http://localhost:8080.

---

## 5. Documentación de la API

Una vez que el sistema esté corriendo, puedes acceder a la documentación interactiva de la API a través de Swagger en:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
