// js/chat.js - VERSIÓN 2.0 (con detección de caché)

console.log('🔄 Cargando chat.js - Versión:', Date.now());

// Detectar si es una versión en caché (la que usa localhost)
if (typeof BASE_URL === 'undefined' ||
    (typeof BASE_URL === 'string' && BASE_URL.includes('localhost'))) {
    console.warn('⚠️ Versión antigua detectada! Forzando recarga sin caché...');

    // Crear un timestamp único y recargar
    var timestamp = new Date().getTime();
    var scripts = document.getElementsByTagName('script');
    for (var i = 0; i < scripts.length; i++) {
        if (scripts[i].src && scripts[i].src.includes('chat.js')) {
            var newSrc = scripts[i].src.split('?')[0] + '?v=' + timestamp;
            console.log('📦 Recargando script desde:', newSrc);
            scripts[i].src = newSrc;
            break;
        }
    }
}

// Obtener BASE_URL (de window, forzado por Thymeleaf)
const BASE_URL = window.BASE_URL || window.location.origin;
console.log('🌐 BASE_URL final:', BASE_URL);

let nombreGlobal = "";
let telefonoGlobal = "";

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
        // Construir URL de manera explícita
        const url = BASE_URL + "/public/chat";
        console.log('📤 Enviando mensaje a:', url);
        console.log('📦 Datos:', { nombre: nombreGlobal, telefono: telefonoGlobal, mensaje: texto });

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

        console.log('📥 Respuesta recibida - Status:', response.status);

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error del servidor:', errorText);
            agregarMensajeBot("Hubo un error, intenta nuevamente.");
            return;
        }

        const data = await response.text();
        console.log('✅ Respuesta exitosa:', data);
        agregarMensajeBot(data);

    } catch (error) {
        console.error('❌ ERROR COMPLETO:');
        console.error('Nombre:', error.name);
        console.error('Mensaje:', error.message);
        console.error('URL intentada:', BASE_URL + "/public/chat");
        console.error('BASE_URL actual:', BASE_URL);
        console.error('Stack:', error.stack);

        agregarMensajeBot("Error de conexión. Revisa la consola (F12)");
    }
}

console.log('✅ chat.js cargado completamente - Versión moderna');
console.log('📌 Configuración final:', {
    BASE_URL: BASE_URL,
    origin: window.location.origin,
    href: window.location.href
});