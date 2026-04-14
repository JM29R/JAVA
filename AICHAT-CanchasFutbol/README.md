# 🤖 AI Chat - Canchas de Fútbol

Aplicación web que integra inteligencia artificial en un sistema de gestión de reservas de canchas de fútbol.

Permite a los usuarios interactuar con un chat inteligente capaz de responder consultas, asistir en reservas y mejorar la experiencia del usuario mediante el uso de modelos de IA.

FrontEnd que simula ser un chat estilo META para testear funcionalidad simulando estar conectado a un chat real.

---

## 🚀 Objetivo del proyecto

Proyecto personal enfocado en explorar la integración de **Inteligencia Artificial en aplicaciones backend con Spring Boot**.

Principales objetivos:

* Integrar servicios de IA usando **Spring AI**
* Consumir APIs externas (Groq)
* Implementar autenticación segura con **JWT**
* Construir una aplicación real combinando negocio + IA

---

## 🧠 Qué aprendí

* Integración de **Spring AI** con proveedores externos
* Uso de APIs de IA (Groq) en backend Java
* Manejo de prompts y respuestas en aplicaciones reales
* Implementación de seguridad con **JWT**
* Despliegue con **Docker + Render**
* Conexión a bases de datos externas (Aiven)

---

## 🛠️ Tecnologías utilizadas

### Backend

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA
* Spring AI

### Inteligencia Artificial

* API de Groq (modelo compatible con OpenAI)

### Seguridad

* Spring Security
* JWT (Auth0)

### Base de datos

* MySQL
* Flyway (migraciones)
* Aiven (hosting DB)

### Frontend

* Thymeleaf

### DevOps

* Docker
* Render (deploy)

### Calidad

* SonarQube

---

## ⚙️ Funcionalidades

* Registro y login de usuarios.
* Autenticación con JWT.
* Gestión de:

  * Canchas.
  * Reservas.
  * Usuarios.
* Chat con IA:

  * Respuestas automáticas a consultas.
  * Asistencia para reservas.
  * Persistencia de mensajes para obtener mejor contexto.
* Integración con API externa de IA.
* Persistencia de turnos, usuarios y mensajes.
* Panel ADMIN donde ver turnos, canchas y mensajes con sus intenciones.

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura **monolítica en capas**, organizada de la siguiente manera:

```id="arch123"
Controller
 ├── Services
 │    ├── Entities
 │    ├── Repositories
 │
 ├── Security
 ├── AI (Chat)
 ├── Views (Thymeleaf)
```

### 🔍 Nota

Si bien no sigue una arquitectura avanzada como hexagonal, este proyecto fue clave para entender:

* Flujo completo de una aplicación real
* Integración de múltiples tecnologías
* Comunicación con servicios externos (IA)

---

## 🐳 Deploy

La aplicación está desplegada utilizando:

* Docker (contenedorización)
* Render (hosting)
* Aiven (base de datos)

👉 *(Podés agregar acá tu link de deploy)*

---

## 🔐 Variables de entorno

```properties id="env123"
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.ai.openai.api-key=${GROQ_API_KEY}

api.security.token.secret=${JWT_SECRET}

server.port=${PORT:8080}
```

---

## 🧪 Testing

* Tests básicos
* Análisis de calidad con **SonarQube**

---

## 📌 Mejoras futuras

* Implementar arquitectura más desacoplada (Hexagonal / Clean)
* Mejorar manejo de prompts de IA
* Agregar historial de conversaciones
* Implementar frontend moderno (React)
* Tests unitarios y de integración más completos
* Rate limiting para la API de IA

---

## 👨‍💻 Autor

**Juan Manuel Rios**

Desarrollador backend enfocado en Java, arquitectura de software e integración con IA.

---



