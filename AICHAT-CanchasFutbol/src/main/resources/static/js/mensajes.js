const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "/login";
}

document.addEventListener("DOMContentLoaded", cargarMensajes);

/* ============================= */
/* CARGAR TODOS                  */
/* ============================= */
function cargarMensajes() {

    fetch("/API/mensajes", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Error cargando mensajes");
        return res.json();
    })
    .then(data => renderTabla(data))
    .catch(err => alert(err.message));
}

/* ============================= */
/* RENDER TABLA                  */
/* ============================= */
function renderTabla(data) {

    const tabla = document.getElementById("tablaMensajes");
    tabla.innerHTML = "";

    data.forEach(m => {

        const fila = `
            <tr>
                <td>${m.id}</td>
                <td>${m.telefono}</td>
                <td>${m.mensajeUsuario}</td>
                <td>${m.respuestaSistema}</td>
                <td>${m.intencion}</td>
                <td>${m.fechayhora}</td>
                <td>
                    <button class="btn btn-sm btn-danger"
                        onclick="eliminarPorId(${m.id})">
                        Eliminar
                    </button>
                </td>
            </tr>
        `;

        tabla.innerHTML += fila;
    });
}

/* ============================= */
/* FILTRAR                       */
/* ============================= */
function filtrar() {

    const telefono = document.getElementById("filtroTelefono").value.trim();
    const intencion = document.getElementById("filtroIntencion").value.trim();

    if (telefono) {
        fetch(`/API/mensajes/${telefono}`, {
            headers: { "Authorization": "Bearer " + token }
        })
        .then(res => res.json())
        .then(data => renderTabla(data));
        return;
    }

    if (intencion) {
        fetch(`/API/mensajes/intencion/${intencion}`, {
            headers: { "Authorization": "Bearer " + token }
        })
        .then(res => res.json())
        .then(data => renderTabla(data));
        return;
    }

    cargarMensajes();
}

/* ============================= */
/* ELIMINAR POR ID               */
/* ============================= */
function eliminarPorId(id) {

    if (!confirm("¿Eliminar este mensaje?")) return;

    fetch(`/API/mensajes/id/${id}`, {
        method: "DELETE",
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => {
        if (res.status === 204) {
            cargarMensajes();
        } else {
            throw new Error("Error eliminando mensaje");
        }
    })
    .catch(err => alert(err.message));
}

/* ============================= */
/* ELIMINAR POR TELEFONO         */
/* ============================= */
function eliminarPorTelefono() {

    const telefono = document.getElementById("filtroTelefono").value.trim();

    if (!telefono) {
        alert("Ingrese un teléfono para eliminar");
        return;
    }

    if (!confirm("¿Eliminar todos los mensajes de este teléfono?")) return;

    fetch(`/API/mensajes/telefono/${telefono}`, {
        method: "DELETE",
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => {
        if (res.status === 204) {
            cargarMensajes();
        } else {
            throw new Error("Error eliminando mensajes");
        }
    })
    .catch(err => alert(err.message));
}

/* ============================= */
/* ELIMINAR TODOS                */
/* ============================= */
function eliminarTodos() {

    if (!confirm("¿Eliminar TODOS los mensajes?")) return;

    fetch("/API/mensajes/todos", {
        method: "DELETE",
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => {
        if (res.status === 204) {
            cargarMensajes();
        } else {
            throw new Error("Error eliminando todos");
        }
    })
    .catch(err => alert(err.message));
}