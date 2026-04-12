async function register() {
    const nombre = document.getElementById("user").value;
    const password = document.getElementById("pass").value;
    const errorEl = document.getElementById("error");

    errorEl.innerText = "";

    // 🔹 Validaciones básicas
    if (!nombre || !password) {
        errorEl.innerText = "Completá todos los campos";
        return;
    }

    try {
        const response = await fetch("/user/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nombre,
                password
            })
        });

        if (!response.ok) {
            const text = await response.text();
            errorEl.innerText = text || "Error al registrarse";
            return;
        }

        // 🔹 éxito
        alert("Usuario creado correctamente");

        // opcional: redirigir al login
        window.location.href = "/view/login";

    } catch (error) {
        errorEl.innerText = "Error de conexión con el servidor";
    }
}