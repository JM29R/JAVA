function paneladmin(){
    window.location.href = "http://localhost:8080/login";
}

    let nombreGlobal = "";
    let telefonoGlobal = "";

    function iniciarChat() {
        const nombre = document.getElementById("nombre").value;
        const telefono = document.getElementById("telefono").value;

        if (!nombre || !telefono) {
            alert("Completa nombre y teléfono");
            return;
        }

        nombreGlobal = nombre;
        telefonoGlobal = telefono;

        document.getElementById("formularioInicial").style.display = "none";
        document.getElementById("chatContainer").style.display = "block";

        agregarMensajeBot("Hola " + nombre + ", ¿en qué puedo ayudarte?");
    }

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

    async function enviarMensaje() {

        const input = document.getElementById("mensaje");
        const texto = input.value;

        if (!texto) return;

        agregarMensajeUser(texto);
        input.value = "";

        try {

            const response = await fetch("http://localhost:8080/public/chat", {
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

            if (!response.ok) {
                agregarMensajeBot("Hubo un error, intenta nuevamente.");
                return;
            }

            const data = await response.text();

            agregarMensajeBot(data);

        } catch (error) {
            agregarMensajeBot("Error de conexión.");
        }
    }