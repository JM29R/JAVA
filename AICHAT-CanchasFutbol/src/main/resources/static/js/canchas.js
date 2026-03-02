

 const token = localStorage.getItem("token");

 if (!token) {
     window.location.href = "/login";
 }

 let listaCanchas = [];

 /* ============================= */
 /* CARGAR CANCHAS                */
 /* ============================= */
 function CargarCanchas() {

     fetch("/API/canchas/todas", {
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

         listaCanchas = data;

         const tabla = document.getElementById("tablaCanchas");
         tabla.innerHTML = "";

         data.forEach(cancha => {

             const fila = `
                 <tr>
                     <td>${cancha.id}</td>
                     <td>${cancha.nombre}</td>
                     <td>${cancha.tipo}</td>
                     <td>
                         <button class="btn btn-sm btn-warning me-2"
                             onclick="editarCancha(${cancha.id})">
                             Editar
                         </button>

                         <button class="btn btn-sm btn-danger"
                             onclick="eliminarCancha(${cancha.id})">
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
         alert("Error cargando canchas");
     });
 }


 /* ============================= */
 /* ABRIR MODAL EN MODO CREAR     */
 /* ============================= */
 function abrirModalCrear() {

     document.getElementById("editId").value = "";
     document.getElementById("editNombre").value = "";
     document.getElementById("editTipo").value = "";

     document.querySelector("#modalEditar .modal-title").innerText = "Crear Cancha";

     const modal = new bootstrap.Modal(document.getElementById("modalEditar"));
     modal.show();
 }


 /* ============================= */
 /* EDITAR CANCHA                 */
 /* ============================= */
 function editarCancha(id) {

     const cancha = listaCanchas.find(c => c.id === id);

     if (!cancha) {
         alert("Cancha no encontrada");
         return;
     }

     document.getElementById("editId").value = cancha.id;
     document.getElementById("editNombre").value = cancha.nombre;
     document.getElementById("editTipo").value = cancha.tipo;

     document.querySelector("#modalEditar .modal-title").innerText = "Editar Cancha";

     const modal = new bootstrap.Modal(document.getElementById("modalEditar"));
     modal.show();
 }


 /* ============================= */
 /* GUARDAR (CREAR O EDITAR)      */
 /* ============================= */
 function guardarCambios() {

     const id = document.getElementById("editId").value;
     const nombre = document.getElementById("editNombre").value.trim();
     const tipo = parseInt(document.getElementById("editTipo").value);

     if (!nombre || isNaN(tipo)) {
         alert("Completar todos los campos correctamente");
         return;
     }

     const datos = {
         nombre: nombre,
         tipo: tipo
     };

     let url = "/API/canchas";
     let metodo = "POST";

     if (id) {
         url = `/API/canchas/${id}`;
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

         alert(id ? "Cancha modificada correctamente" : "Cancha creada correctamente");

         bootstrap.Modal
             .getInstance(document.getElementById("modalEditar"))
             .hide();

         CargarCanchas();
     })
     .catch(error => {
         alert("Error: " + error.message);
     });
 }


 /* ============================= */
 /* ELIMINAR CANCHA               */
 /* ============================= */
 function eliminarCancha(id) {

     if (!confirm("¿Seguro que querés eliminar esta cancha?")) {
         return;
     }

     fetch(`/API/canchas/${id}`, {
         method: "DELETE",
         headers: {
             "Authorization": "Bearer " + token
         }
     })
     .then(response => {
         if (response.status === 204 || response.ok) {
             alert("Cancha eliminada correctamente");
             CargarCanchas();
         } else {
             return response.text().then(text => { throw new Error(text); });
         }
     })
     .catch(error => {
         alert("Error: " + error.message);
     });
 }


 /* ============================= */
 /* INIT                          */
 /* ============================= */
 document.addEventListener("DOMContentLoaded", CargarCanchas);