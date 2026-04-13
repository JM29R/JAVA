// topico.js
let topicoActual = null;
let respuestaEditandoId = null;

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

function getTopicoId() {
    const pathParts = window.location.pathname.split("/");
    return pathParts[pathParts.length - 1];
}

async function cargarTopicoYRespuestas() {
    const token = localStorage.getItem("token");
    const id = getTopicoId();

    if (!id) {
        mostrarMensaje("ID de tópico no válido", "error");
        return;
    }

    try {
        // Cargar tópico
        const topicoResponse = await fetch(`/topicos/${id}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!topicoResponse.ok) throw new Error("Error al cargar tópico");

        const topico = await topicoResponse.json();
        topicoActual = topico;
        renderTopico(topico);

        // Cargar respuestas
        const respuestasResponse = await fetch(`/respuestas/topico/${id}`, {
            headers: { "Authorization": "Bearer " + token }
        });

        if (!respuestasResponse.ok) throw new Error("Error al cargar respuestas");

        const respuestas = await respuestasResponse.json();
        renderRespuestas(respuestas);

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

function renderTopico(t) {
    document.getElementById("titulo").innerText = t.titulo;
    document.getElementById("mensaje").innerText = t.mensaje;
    document.getElementById("autor").innerHTML = `<i class="fas fa-user"></i> Autor ID: ${t.idAutor ?? "Desconocido"}`;

    // Mostrar botones de edición (el backend valida los permisos)
    document.getElementById("botones-topico").style.display = "block";
}

function renderRespuestas(respuestas) {
    const container = document.getElementById("respuestas-container");
    const contador = document.getElementById("contador-respuestas");

    if (!respuestas || respuestas.length === 0) {
        container.innerHTML = '<div class="alert alert-info">No hay respuestas aún. ¡Sé el primero en responder!</div>';
        if (contador) contador.textContent = "0";
        return;
    }

    if (contador) contador.textContent = respuestas.length;

    container.innerHTML = respuestas.map(r => `
        <div class="respuesta-item">
            <div class="respuesta-acciones">
                <button class="btn-accion btn-editar" onclick="abrirModalEditarRespuesta(${r.id}, '${escapeHtml(r.contenido)}')" title="Editar respuesta">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-accion btn-eliminar" onclick="eliminarRespuesta(${r.id})" title="Eliminar respuesta">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
            <div class="respuesta-contenido">
                <p class="mb-2">${escapeHtml(r.contenido)}</p>
                <small class="text-muted">
                    <i class="fas fa-user"></i> Autor ID: ${r.idAutor ?? 'Desconocido'} |
                    <i class="fas fa-calendar"></i> ${formatFecha(r.fechaCreacion)}
                </small>
            </div>
        </div>
    `).join("");
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function formatFecha(fecha) {
    if (!fecha) return 'Fecha desconocida';
    return new Date(fecha).toLocaleString();
}

async function crearRespuesta() {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const topicoId = getTopicoId();
    const contenido = document.getElementById("nuevaRespuesta").value.trim();

    if (!contenido) {
        mostrarMensaje("Escribí una respuesta", "error");
        return;
    }

    try {
        const response = await fetch("/respuestas", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({
                contenido: contenido,
                topicoId: Number(topicoId),
                autorId: Number(userId)
            })
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || "Error al crear respuesta");
        }

        document.getElementById("nuevaRespuesta").value = "";
        await cargarTopicoYRespuestas();
        mostrarMensaje("Respuesta creada correctamente", "success");

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

function abrirModalEditarTopico() {
    if (!topicoActual) return;

    document.getElementById("editTopicoTitulo").value = topicoActual.titulo || "";
    document.getElementById("editTopicoMensaje").value = topicoActual.mensaje || "";

    new bootstrap.Modal(document.getElementById("modalEditarTopico")).show();
}

async function guardarEdicionTopico() {
    const token = localStorage.getItem("token");
    const id = getTopicoId();
    const titulo = document.getElementById("editTopicoTitulo").value.trim();
    const mensaje = document.getElementById("editTopicoMensaje").value.trim();

    if (!titulo || !mensaje) {
        mostrarMensaje("Todos los campos son obligatorios", "error");
        return;
    }

    try {
        const response = await fetch(`/topicos/${id}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({ titulo, mensaje })
        });

        if (!response.ok) throw new Error("Error al editar tópico");

        bootstrap.Modal.getInstance(document.getElementById("modalEditarTopico")).hide();
        await cargarTopicoYRespuestas();
        mostrarMensaje("Tópico actualizado correctamente", "success");

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

async function eliminarTopico() {
    if (!confirm("¿Estás seguro de eliminar este tópico? También se eliminarán todas las respuestas.")) return;

    const token = localStorage.getItem("token");
    const id = getTopicoId();

    try {
        const response = await fetch(`/topicos/${id}`, {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) throw new Error("Error al eliminar tópico");

        window.location.href = "/view/inicio";

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

function abrirModalEditarRespuesta(id, contenido) {
    respuestaEditandoId = id;
    document.getElementById("editRespuestaMensaje").value = contenido;
    new bootstrap.Modal(document.getElementById("modalEditarRespuesta")).show();
}

async function guardarEdicionRespuesta() {
    const token = localStorage.getItem("token");
    const contenido = document.getElementById("editRespuestaMensaje").value.trim();

    if (!contenido) {
        mostrarMensaje("El mensaje es obligatorio", "error");
        return;
    }

    try {
        const response = await fetch(`/respuestas/${respuestaEditandoId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify({ contenido })
        });

        if (!response.ok) throw new Error("Error al editar respuesta");

        bootstrap.Modal.getInstance(document.getElementById("modalEditarRespuesta")).hide();
        await cargarTopicoYRespuestas();
        mostrarMensaje("Respuesta actualizada correctamente", "success");

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

async function eliminarRespuesta(id) {
    if (!confirm("¿Estás seguro de eliminar esta respuesta?")) return;

    const token = localStorage.getItem("token");

    try {
        const response = await fetch(`/respuestas/${id}`, {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + token }
        });

        if (!response.ok) throw new Error("Error al eliminar respuesta");

        await cargarTopicoYRespuestas();
        mostrarMensaje("Respuesta eliminada correctamente", "success");

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

function mostrarMensaje(mensaje, tipo) {
    const container = document.getElementById("mensaje-container");
    if (!container) return;

    container.innerHTML = `<div class="alert alert-${tipo === 'error' ? 'danger' : 'success'} alert-dismissible fade show" role="alert">
        <i class="fas fa-${tipo === 'error' ? 'exclamation-triangle' : 'check-circle'}"></i> ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>`;

    setTimeout(() => {
        container.innerHTML = "";
    }, 3000);
}