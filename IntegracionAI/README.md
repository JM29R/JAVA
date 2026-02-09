# Integración de OpenAI con Spring Boot

Este proyecto forma parte de mi práctica personal para aprender a integrar modelos de IA de OpenAI en aplicaciones Backend con Java y Spring Boot.

## Objetivo

Practicar el uso de **Spring AI** para consumir servicios de OpenAI y crear endpoints que utilicen inteligencia artificial dentro de una API REST.

## Tecnologías utilizadas

* Java
* Spring Boot
* Spring AI
* OpenAI (Chat e Imágenes)
* Maven
* jtokkit (conteo de tokens)

## Funcionalidades implementadas

### 1. Categorizador de productos

Endpoint: `/categorizador`

* Envía la descripción de un producto al modelo.
* La IA devuelve la categoría correspondiente.
* Se realiza conteo de tokens antes de hacer la llamada.
* Selección de modelo según el tamaño del prompt.

---

### 2. Generador de productos

Endpoint: `/generador`

* Genera una lista de productos usando OpenAI.
* Uso de prompts con rol **system** y **user**.
* Validación básica de tokens antes de la ejecución.

---

### 3. Generador de imágenes

Endpoint: `/imagen`

* Genera una imagen a partir de un prompt.
* Devuelve la URL de la imagen generada.
* Configuración de tamaño (1024x1024).

---

## Qué estoy practicando

* Integración de OpenAI en Java
* Uso de **Spring AI**
* Creación de APIs REST con Spring Boot
* Ingeniería de prompts (system + user)
* Selección y configuración de modelos
* Conteo de tokens y control de consumo
* Organización de controladores por funcionalidad

## Estructura del proyecto

* Controller

  * CategorizadorController
  * GeneradorProductosController
  * GeneradorImagenesController
* Tokens

  * ContadorDeToken
* resources

  * application.properties

## Qué aprendí en este proyecto

* Integrar modelos de OpenAI en aplicaciones Backend con Java y Spring Boot.
* Utilizar Spring AI para consumir servicios de generación de texto e imágenes.
* Diseñar prompts utilizando los roles **system** y **user**.
* Implementar endpoints REST que incorporan funcionalidades de inteligencia artificial.
* Generar imágenes desde texto y procesar la respuesta de la API.
* Contar y analizar el uso de tokens con jtokkit para estimar el consumo.
* Seleccionar modelos según el contexto y tamaño de la solicitud.
* Probar y validar endpoints utilizando herramientas como Insomnia o navegador.

## Estado

Proyecto en desarrollo – práctica personal para reforzar conocimientos en Backend con Java y el uso de inteligencia artificial.
