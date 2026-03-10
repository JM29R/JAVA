// js/chat.js
console.log('🔄 Cargando chat.js - Versión:', Date.now());

// Variables globales del usuario
let nombreGlobal = "";
let telefonoGlobal = "";

// Usar BASE_URL de window (configurado por Thymeleaf desde el controlador)
const BASE_URL = window.BASE_URL || window.location.origin;
console.log('🌐 BASE_URL:', BASE_URL);

// Verificar que no estamos usando localhost incorrectamente
if (BASE_URL.includes('localhost') && !window.location.hostname.includes('localhost')) {
    console.warn('⚠️ ATENCIÓN: Usando localhost en producción, forzando origin');
    window.BASE_URL = window.location.origin;
}

// ---------- PANEL DE ADMIN ----------
function paneladmin() {
    const loginUrl = BASE_URL + "/login";
    console.log('🔐 Redirigiendo a:', loginUrl);
    window.location.href = loginUrl;
}

// ---------- INICIO DEL CHAT ----------
function iniciarChat() {
    const nombre = document.getElementById("nombre").value.trim();
    const telefono = document.getElementById("telefono").value.trim();

    if (!nombre || !telefono) {
        alert("Completa nombre y teléfono");
        return;
    }

    nombreGlobal = nombre;
    telefonoGlobal = telefono;

    document.getElementById("formularioInicial").style.display = "none";
    document.getElementById("chatContainer").style.display = "block";

    agregarMensajeBot(`Hola ${nombre}, ¿en qué puedo ayudarte?`);
}

// ---------- FUNCIONES DE MENSAJES ----------
function agregarMensajeUser(texto) {
    const chatBox = document.getElementById("chatBox");
    chatBox.innerHTML += `
        <div class="mensaje-user">
            <div class="bubble-user">${texto}</div>
        </div>
    `;
    chatBox.scrollTop = chatBox.scrollHeight;
}

function agregarMensajeBot(texto) {
    const chatBox = document.getElementById("chatBox");
    chatBox.innerHTML += `
        <div class="mensaje-bot">
            <div class="bubble-bot">${texto}</div>
        </div>
    `;
    chatBox.scrollTop = chatBox.scrollHeight;
}

// ---------- ENVÍO DE MENSAJE ----------
async function enviarMensaje() {
    const input = document.getElementById("mensaje");
    const texto = input.value.trim();
    if (!texto) return;

    agregarMensajeUser(texto);
    input.value = "";

    try {
        const url = BASE_URL + "/public/chat";
        console.log('📤 Enviando mensaje a:', url);
        console.log('📦 Datos:', {
            nombre: nombreGlobal,
            telefono: telefonoGlobal,
            mensaje: texto
        });

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nombre: nombreGlobal,
                telefono: telefonoGlobal,
                mensaje: texto
            })
        });

        console.log('📥 Respuesta - Status:', response.status);

        if (!response.ok) {
            const errorText = await response.text();
            console.error('❌ Error del servidor:', errorText);
            agregarMensajeBot("Hubo un error, intenta nuevamente.");
            return;
        }

        const data = await response.text();
        console.log('✅ Respuesta exitosa:', data);
        agregarMensajeBot(data);

    } catch (error) {
        console.error('❌ Error completo:', error);
        console.error('URL que falló:', BASE_URL + "/public/chat");
        console.error('BASE_URL actual:', BASE_URL);
        agregarMensajeBot("Error de conexión. Revisa consola (F12)");
    }
}

console.log('✅ chat.js cargado correctamente');