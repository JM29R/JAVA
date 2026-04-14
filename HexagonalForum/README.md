# 🧩 Hexagonal Forum

Aplicación web tipo foro desarrollada con **Arquitectura Hexagonal (Ports & Adapters)**, orientada al aprendizaje de diseño de sistemas backend modernos y buenas prácticas con Spring Boot.

Permite a los usuarios registrarse, autenticarse y participar en discusiones mediante la creación de tópicos y respuestas, similar a plataformas como Taringa o foros tradicionales.

---

## 🚀 Objetivo del proyecto

Este proyecto fue desarrollado como iniciativa personal con fines de aprendizaje y demostración profesional.

Principales objetivos:

* Aplicar **Arquitectura Hexagonal** en un proyecto real
* Mejorar el uso de **Spring Security**
* Implementar autenticación con **JWT**
* Diseñar un backend limpio, escalable y desacoplado

---

## 🧠 Qué aprendí

* Separación de responsabilidades usando **domain, application e infrastructure**
* Implementación de **casos de uso (use cases)** desacoplados
* Integración de **Spring Security + JWT**
* Manejo de migraciones con **Flyway**
* Testing con **JUnit, Mockito y análisis con SonarQube**
* Buenas prácticas en diseño de APIs REST

---

## 🛠️ Tecnologías utilizadas

### Backend

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Spring Security

### Seguridad

* JWT (JSON Web Tokens)
* Encriptación de contraseñas

### Base de datos

* MySQL
* Flyway (migraciones)

### Frontend

* Thymeleaf
* HTML/CSS

### Testing y calidad

* JUnit
* Mockito
* JaCoCo (cobertura)
* SonarQube

### DevOps

* Docker
* Railway (deploy)
* DockerHub (imagen)

---

## ⚙️ Funcionalidades

* Registro de usuarios
* Login con autenticación JWT
* Roles:

  * Usuario
  * Administrador
* Creación de tópicos
* Respuestas a tópicos
* Relaciones entre entidades:

  * Usuario ↔ Tópico
  * Usuario ↔ Respuesta
  * Tópico ↔ Respuesta
* Control de permisos según rol
* Persistencia en base de datos SQL

---

## 🏗️ Arquitectura

El proyecto sigue el patrón de **Arquitectura Hexagonal**, separando claramente las capas:

```
domain/
 ├── model/
 ├── repository/

application/
 ├── service/
 ├── usecase/

infrastructure/
 ├── controller/
 ├── persistence/
 ├── config/
 ├── security/
 ├── dtos/
```

### 🔍 Descripción

* **Domain** → Núcleo del negocio (entidades y contratos)
* **Application** → Casos de uso y lógica de aplicación
* **Infrastructure** → Implementaciones concretas (DB, controllers, seguridad)

Esto permite:

* Bajo acoplamiento
* Alta testabilidad
* Escalabilidad

---

## 🐳 Deploy

La aplicación está desplegada utilizando:

* Docker (contenedorización)
* DockerHub (imagen)
* Railway (hosting)
* Puedes probar el modo admin con: user=admin password=admin

👉 https://hexagonal-forum-production.up.railway.app/view/login

---

## 🔐 Variables de entorno

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

JWT_SECRET=tu_clave_secreta
```

---

## 🧪 Testing

El proyecto incluye:

* Tests unitarios con **JUnit**
* Mocks con **Mockito**
* Cobertura de código con **JaCoCo**
* Análisis estático con **SonarQube**

---

## 📌 Mejoras futuras

* Implementar frontend en React
* Agregar paginación en tópicos
* Sistema de likes/reacciones
* Notificaciones
* Cache (Redis)
* Documentación con Swagger

---

## 👨‍💻 Autor

**Juan Manuel Rios**

* Proyecto personal orientado a backend con Java
* Enfocado en arquitectura y buenas prácticas

---

