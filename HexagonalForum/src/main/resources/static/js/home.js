document.addEventListener("DOMContentLoaded", () => {
    verificarAuth();
    cargarTopicos();
});

function verificarAuth() {
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "/view/login";
    }
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/view/login";
}

async function cargarTopicos() {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch("/topicos/todos", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Error al obtener tópicos");
        }

        const topicos = await response.json();

        renderTopicos(topicos);

    } catch (error) {
        document.getElementById("error").innerText = error.message;
    }
}

function renderTopicos(topicos) {
    const container = document.getElementById("topicos-container");

    if (topicos.length === 0) {
        container.innerHTML = "<p>No hay tópicos aún</p>";
        return;
    }

    container.innerHTML = topicos.map(t => `
        <div class="col-md-6">
            <a href="/view/topico/${t.id}" style="text-decoration:none; color:inherit;">
                <div class="card-custom shadow">
                    <h5>${t.titulo}</h5>
                    <p>${t.mensaje}</p>
                    <small>Autor ID: ${t.idAutor ?? 'Desconocido'}</small>
                </div>
            </a>
        </div>
    `).join("");
}


// Agregar el modal al DOM cuando se carga la página
function agregarModalCrearTopico() {
    if (document.getElementById('modalCrearTopico')) return;

    const modalHTML = `
        <div class="modal fade" id="modalCrearTopico" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-dark text-white">
                        <h5 class="modal-title">Crear Nuevo Tópico</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="formCrearTopico">
                            <div class="mb-3">
                                <label for="tituloTopico" class="form-label">Título</label>
                                <input type="text" class="form-control" id="tituloTopico" required>
                            </div>
                            <div class="mb-3">
                                <label for="mensajeTopico" class="form-label">Mensaje</label>
                                <textarea class="form-control" id="mensajeTopico" rows="4" required></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="enviarCrearTopico()">Crear</button>
                    </div>
                </div>
            </div>
        </div>
    `;
    document.body.insertAdjacentHTML('beforeend', modalHTML);
}

// Función que muestra el modal
function crearTopico() {
    agregarModalCrearTopico();
    const modal = new bootstrap.Modal(document.getElementById('modalCrearTopico'));
    modal.show();
}

// Función que envía el formulario
async function enviarCrearTopico() {
    const titulo = document.getElementById('tituloTopico').value.trim();
    const mensaje = document.getElementById('mensajeTopico').value.trim();

    if (!titulo) {
        alert("El título es obligatorio");
        return;
    }

    if (!mensaje) {
        alert("El mensaje es obligatorio");
        return;
    }

    const token = localStorage.getItem("token");

    const topicoRequest = {
        titulo: titulo,
        mensaje: mensaje
    };

    try {
        const response = await fetch("/topicos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(topicoRequest)
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || `Error ${response.status}: No se pudo crear el tópico`);
        }

        const nuevoTopico = await response.json();

        // Cerrar el modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalCrearTopico'));
        modal.hide();

        // Limpiar el formulario
        document.getElementById('formCrearTopico').reset();

        alert("¡Tópico creado exitosamente!");

        // Recargar la lista de tópicos
        await cargarTopicos();

    } catch (error) {
        console.error("Error:", error);
        alert("Error al crear el tópico: " + error.message);
    }
}