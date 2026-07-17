# 🤖 AgenteMercadoCentral_ALURALATAM

[![Java](https://img.shields.io/badge/Java-21-orange)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-brightgreen)](https://spring.io/projects/spring-boot)
[![Telegram](https://img.shields.io/badge/Telegram-Bot-blue)](https://core.telegram.org/bots)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED)](https://www.docker.com/)

> **Bot inteligente para Telegram con doble agente de IA (Groq + Gemini)**, diseñado para gestionar pedidos y consultar información desde documentos PDF. Proyecto desarrollado como desafío para **Alura Latam**.


## 📝 Descripción General

**AgenteMercadoCentral_ALURALATAM** es un bot de Telegram que utiliza **dos agentes de IA** para ofrecer una experiencia inteligente y automatizada:

- **Groq** clasifica la intención del usuario mediante un `Enum` de intenciones.
- **Gemini** genera respuestas contextualizadas basadas en documentos PDF precargados.

Además, cuenta con un **sistema CRUD de pedidos**, donde Groq extrae la información del pedido desde el mensaje del usuario y el backend lo registra automáticamente.

---

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura hexagonal (puertos y adaptadores)**, lo que garantiza:

- **Desacoplamiento** entre la lógica de negocio y los frameworks.
- **Mantenibilidad** y **testabilidad**.
- **Flexibilidad** para cambiar adaptadores (ej: cambiar de Telegram a otra plataforma).

## 🧩 Tecnologías

| Capa | Tecnología |
|------|------------|
| **Backend** | Java 21, Spring Boot 3.5.16 |
| **IA** | Groq (clasificador), Gemini (generador de respuestas) |
| **Base de Datos** | MySQL, JPA/Hibernate, Flyway (migraciones) |
| **Bot** | Telegram Bots API (long polling) |
| **Procesamiento PDF** | Apache PDFBox 3.0.5 |
| **Contenedores** | Docker |
| **Despliegue** | Oracle Cloud Infrastructure (OCI) |
| **Arquitectura** | Hexagonal (Puertos y Adaptadores) |

---

## ⚙️ Funcionalidades

| Módulo | Descripción |
|--------|-------------|
| **🤖 Clasificador de Intención** | Usa **Groq** para determinar la intención del usuario (ej: *CONSULTAR_PDF*, *CREAR_PEDIDO*, *AYUDA*). |
| **📄 Respuesta con Gemini** | **Gemini** genera respuestas personalizadas usando información extraída de archivos PDF. |
| **📦 CRUD de Pedidos** | Groq extrae los datos del pedido (producto, cantidad, precio, etc.) y el backend lo persiste en MySQL. |
| **📑 Lectura de PDFs** | Los PDFs cargados en el sistema son leídos y su contenido se usa como base de conocimiento. |
| **💬 Bot de Telegram** | Interfaz principal del usuario. Recibe mensajes y devuelve respuestas inteligentes. |

---

## 🔄 Flujo de Trabajo

<img width="681" height="761" alt="Diagrama sin título drawio (2)" src="https://github.com/user-attachments/assets/c17d0657-5e9a-4fcd-84cb-05e308242d13" />


## 📦 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener:

- **Java 21** (OpenJDK o Adoptium)
- **Maven** 3.8+
- **MySQL** 8.0+
- **Docker** (opcional, para despliegue)
- **Cuentas API**:
  - [Groq API Key](https://console.groq.com/)
  - [Google Gemini API Key](https://ai.google.dev/)
  - [Telegram Bot Token](https://t.me/BotFather)

---


## 🔧 Variables de Entorno

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_HOST` | Host de MySQL | `localhost` |
| `DB_PORT` | Puerto de MySQL | `3306` |
| `DB_NAME` | Nombre de la base de datos | `agente_mercado` |
| `DB_USER` | Usuario de MySQL | `root` |
| `DB_PASSWORD` | Contraseña de MySQL | `admin123` |
| `GROQ_API_KEY` | API Key de Groq | `gsk_...` |
| `GEMINI_API_KEY` | API Key de Gemini | `AIza...` |
| `TELEGRAM_BOT_TOKEN` | Token del bot de Telegram | `123456:ABC...` |
| `TELEGRAM_BOT_USERNAME` | Username del bot | `@MiBotMercadoBot` |

## MEJORAS

- Mejora en PROMPTS.
. Mejorar Coneccion con TELEGRAM.
- Front-end basico para manejo de pedidos.
- Integracion con Spring-Security.
- Despliegue en OCI.

## 📬 Contacto

- **Autor:** JMR
- **Desafío:** Alura Latam
- **Repositorio:** [https://github.com/tu-usuario/AgenteMercadoCentral_ALURALATAM](https://github.com/tu-usuario/AgenteMercadoCentral_ALURALATAM)

---

⭐ **Si este proyecto te ha sido útil, ¡no olvides darle una estrella en GitHub!** ⭐
