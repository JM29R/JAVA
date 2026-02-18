let ListaAmigos = [];
function agregarAmigo() {
    let nombreInput = document.getElementById("amigo");
    let nombre = nombreInput.value.trim();

    if (nombre === "") {
        alert("Por favor, ingrese un nombre.");
        return;
    }
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(nombre)) {
        alert("El nombre no debe contener números ni caracteres especiales.");
        return;
    }
    for (let i = 0; i < ListaAmigos.length; i++) {
        if (ListaAmigos[i] == nombre) {
            alert("El nombre ya existe. Por favor, ingrese un nombre diferente.");
            return;
        }
    }
    ListaAmigos.push(nombre);
    alert("Amigo añadido: " + nombre);
    return;
}
function sortearAmigo() {
    if (ListaAmigos.length < 2) {
        alert("Debe haber al menos dos amigos para realizar el sorteo.");
        return;
    }
    let asignados = [];
    let valido = false;
    while (!valido) {
        asignados = [...ListaAmigos];
        for (let i = asignados.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [asignados[i], asignados[j]] = [asignados[j], asignados[i]];
        }

        valido = true;
        for (let i = 0; i < ListaAmigos.length; i++) {
            if (ListaAmigos[i] === asignados[i]) {
                valido = false;
                break;
            }
        }
    }
    const unResultado = document.getElementById("resultado");
    unResultado.innerHTML = "";
    for (let i = 0; i < ListaAmigos.length; i++) {
        const li = document.createElement("li");
        li.textContent = `${ListaAmigos[i]} → ${asignados[i]}`;
        unResultado.appendChild(li);
    }
}

console.log("Lista de amigos:", ListaAmigos);
