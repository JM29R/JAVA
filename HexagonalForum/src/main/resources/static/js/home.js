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