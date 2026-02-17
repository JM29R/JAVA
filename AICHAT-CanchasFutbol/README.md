# AI Chat – Sistema de Reservas de Canchas vía WhatsApp

**AI Chat** es un sistema inteligente desarrollado en **Java Spring Boot** que permite a los usuarios **reservar, cancelar, modificar y consultar canchas deportivas** automáticamente a través de WhatsApp, utilizando la **API de Groq** en su capa gratuita para procesar intenciones y generar respuestas inteligentes.

## Tecnologías

- Java 21  
- Spring Boot 3.5.10  
- Spring Web / Spring Data JPA / Spring Validation  
- MySQL como base de datos  
- Flyway para versionado de esquemas de base de datos  
- Lombok para reducir código boilerplate  
- Spring AI (Groq y OpenAI) para procesamiento de lenguaje natural  
- JUnit / Spring Boot Test para pruebas

## Funcionalidades

1. **Reservar canchas automáticamente**: los usuarios envían un mensaje por WhatsApp indicando cancha, fecha y hora; el sistema interpreta la intención y genera la reserva.  
2. **Cancelar reservas**: permite cancelar reservas existentes mediante conversación.  
3. **Modificar reservas**: cambiar fecha, hora o cancha de reservas previamente creadas.  
4. **Consultar reservas**: los usuarios pueden consultar el estado de sus reservas por WhatsApp.  
5. **Integración con Groq**: utiliza la capa gratuita de Groq para procesar intenciones y cambia automáticamente entre modelos si se alcanza el límite de llamadas gratuitas.

## Estructura del Proyecto



* aichat/
* -src/
* -- main/
* --- java/
* ---- jmr/aichat/
* ----- CANCHAS/ # Entidad JPA.
* -----  CONTROLLER/ # Endpoints REST (ej. /chat)
* -----  Inteligencia Artifical/ # RECORDS para AI
* -----  RESERVAS/ # Entidad JPA.
* -----  SERVICE/ # Lógica de negocio y comunicación con Groq
* -----  resources/
* -----  application.properties # Configuración DB y API Keys
* ----- db/migration # Scripts Flyway para inicializar tablas
* ---- pom.xml
* ---- README.md


## Uso del Chat
Enviar un mensaje por WhatsApp y el sistema lo interpretara como:
Intencion: reservar
Cancha: 3
Fecha: 2026-02-20
Hora: 18:00

El sistema procesará la intención, confirmará la reserva y responderá automáticamente.

Funciona también para **cancelar, modificar o consultar reservas**.

## Dependencias Clave

- `spring-boot-starter-web` – para exponer endpoints REST  
- `spring-boot-starter-data-jpa` – para persistencia de datos  
- `spring-boot-starter-validation` – para validar inputs  
- `mysql-connector-j` – conexión con MySQL  
- `flyway-core` – migraciones de base de datos  
- `spring-ai-starter-model-openai` – integración con modelos de lenguaje (Groq/OpenAI)  
- `lombok` – reducción de código boilerplate  

## Próximas Mejoras

- Integración completa con WhatsApp Business API o Twilio.  
- Registro de logs de conversación para entrenamiento de modelos.  
- Manejo de múltiples usuarios y roles (administrador, cliente).  
- Persistencia de historiales de chat y reservas.  

## Licencia

Este proyecto es **open source** bajo licencia MIT.




