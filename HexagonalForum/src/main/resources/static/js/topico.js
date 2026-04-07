document.addEventListener("DOMContentLoaded", () => {
    verificarAuth();
    cargarTopicoYRespuestas();
});

function verificarAuth() {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/view/login";
    }
}

async function cargarTopicoYRespuestas() {
    const token = localStorage.getItem("token");

    const pathParts = window.location.pathname.split("/");
    const id = pathParts[pathParts.length - 1];

    if (!id) {
        document.getElementById("error").innerText = "ID de tópico no válido";
        return;
    }

    try {
        // 🔹 1. Obtener tópico
        const topicoResponse = await fetch(`/topicos/${id}`, {
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!topicoResponse.ok) {
            throw new Error("Error al cargar tópico");
        }

        const topico = await topicoResponse.json();
        renderTopico(topico);

        // 🔹 2. Obtener respuestas
        const respuestasResponse = await fetch(`/respuestas/topico/${id}`, {
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!respuestasResponse.ok) {
            throw new Error("Error al cargar respuestas");
        }

        const respuestas = await respuestasResponse.json();
        renderRespuestas(respuestas);

    } catch (error) {
        document.getElementById("error").innerText = error.message;
    }
}

function renderTopico(t) {
    document.getElementById("titulo").innerText = t.titulo;
    document.getElementById("mensaje").innerText = t.mensaje;
    document.getElementById("autor").innerText =
        "Autor ID: " + (t.idAutor ?? "Desconocido");
}

function renderRespuestas(respuestas) {
    const container = document.getElementById("respuestas");

    if (!respuestas.length) {
        container.innerHTML = "<p>No hay respuestas aún</p>";
        return;
    }

    container.innerHTML = respuestas.map(r => `
        <div class="card-custom shadow mb-3">
            <p>${r.contenido}</p>
            <small>Autor: ${r.autorNombre ?? 'Desconocido'}</small>
        </div>
    `).join("");
}

async function crearRespuesta() {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId")

    const pathParts = window.location.pathname.split("/");
    const id = pathParts[pathParts.length - 1];

    const contenido = document.getElementById("nuevaRespuesta").value;

    if (!contenido) {
        document.getElementById("error").innerText = "Escribí una respuesta";
        return;
    }

    try {
        console.log("userId:", userId);
        const response = await fetch("/respuestas", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                contenido: contenido,
                topicoId: Number(id),
                autorId: userId
            })
        });

        if (!response.ok) {
            throw new Error("Error al crear respuesta");
        }

        document.getElementById("nuevaRespuesta").value = "";

        cargarTopicoYRespuestas();

    } catch (error) {
        document.getElementById("error").innerText = error.message;
    }
}
