


const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "/login";
}

let listaReservas = [];

document.addEventListener("DOMContentLoaded", cargarReservas);

/* ============================= */
/* CARGAR RESERVAS               */
/* ============================= */
function cargarReservas() {

    fetch("/API/reservar/todas", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("No autorizado o error en la petición");
        }
        return response.json();
    })
    .then(data => {

        listaReservas = data;

        const tabla = document.getElementById("tablaReservas");
        tabla.innerHTML = "";

        data.forEach(reserva => {

            const fila = `
                <tr>
                    <td>${reserva.id}</td>
                    <td>${reserva.fecha}</td>
                    <td>${reserva.hora}</td>
                    <td>${reserva.nombreCliente}</td>
                    <td>${reserva.telefono}</td>
                    <td>${reserva.canchaId}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-2"
                            onclick="editarReserva(${reserva.id})">
                            Editar
                        </button>

                        <button class="btn btn-sm btn-danger"
                            onclick="eliminarReserva(${reserva.id})">
                            Eliminar
                        </button>
                    </td>
                </tr>
            `;

            tabla.innerHTML += fila;
        });
    })
    .catch(error => {
        console.error(error);
        alert("Error cargando reservas");
    });
}

/* ============================= */
/* CARGAR CANCHAS EN SELECT      */
/* ============================= */
function cargarCanchasSelect(canchaSeleccionada = null) {

    fetch("/API/canchas/todas", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error cargando canchas");
        }
        return response.json();
    })
    .then(data => {

        const select = document.getElementById("editCancha");
        select.innerHTML = "";

        data.forEach(cancha => {
            const option = document.createElement("option");
            option.value = cancha.id;
            option.textContent = cancha.nombre;

            if (canchaSeleccionada && cancha.id === canchaSeleccionada) {
                option.selected = true;
            }

            select.appendChild(option);
        });

    })
    .catch(error => {
        console.error("Error cargando canchas:", error);
    });
}

/* ============================= */
/* ABRIR MODAL CREAR             */
/* ============================= */
function abrirModalCrear() {

    document.getElementById("editId").value = "";
    document.getElementById("editFecha").value = "";
    document.getElementById("editHora").value = "";
    document.getElementById("editNombre").value = "";
    document.getElementById("editTelefono").value = "";

    document.getElementById("modalTitulo").innerText = "Crear Reserva";

    cargarCanchasSelect(); // 🔥 ESTA ERA LA CLAVE

    const modal = new bootstrap.Modal(document.getElementById("reservaModal"));
    modal.show();
}

/* ============================= */
/* EDITAR RESERVA                */
/* ============================= */
function editarReserva(id) {

    const reserva = listaReservas.find(r => r.id === id);

    if (!reserva) {
        alert("Reserva no encontrada");
        return;
    }

    document.getElementById("editId").value = reserva.id;
    document.getElementById("editFecha").value = reserva.fecha;
    document.getElementById("editHora").value = reserva.hora;
    document.getElementById("editNombre").value = reserva.nombreCliente;
    document.getElementById("editTelefono").value = reserva.telefono;

    document.getElementById("modalTitulo").innerText = "Editar Reserva";

    cargarCanchasSelect(reserva.canchaId);

    const modal = new bootstrap.Modal(document.getElementById("reservaModal"));
    modal.show();
}

/* ============================= */
/* GUARDAR (CREAR / EDITAR)      */
/* ============================= */
function guardarCambios() {

    const id = document.getElementById("editId").value;

    const fecha = document.getElementById("editFecha").value;
    const hora = document.getElementById("editHora").value;
    const nombreCliente = document.getElementById("editNombre").value.trim();
    const telefono = document.getElementById("editTelefono").value.trim();
    const canchaId = parseInt(document.getElementById("editCancha").value);

    if (!fecha || !hora || !nombreCliente || !telefono || isNaN(canchaId)) {
        alert("Completar todos los campos correctamente");
        return;
    }

    const datos = {
        fecha,
        hora,
        nombreCliente,
        telefono,
        canchaId
    };

    let url = "/API/reservar";
    let metodo = "POST";

    if (id) {
        url = `/API/reservar/${id}`;
        metodo = "PUT";
    }

    fetch(url, {
        method: metodo,
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datos)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
        }
        return response.json();
    })
    .then(() => {

        alert(id ? "Reserva modificada correctamente" : "Reserva creada correctamente");

        bootstrap.Modal
            .getInstance(document.getElementById("reservaModal"))
            .hide();

        cargarReservas();
    })
    .catch(error => {
        alert("Error: " + error.message);
    });
}

/* ============================= */
/* ELIMINAR RESERVA              */
/* ============================= */
function eliminarReserva(id) {

    if (!confirm("¿Seguro que querés eliminar esta reserva?")) {
        return;
    }

    fetch(`/API/reservar/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(response => {
        if (response.status === 204 || response.ok) {
            alert("Reserva eliminada correctamente");
            cargarReservas();
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        alert("Error: " + error.message);
    });
}