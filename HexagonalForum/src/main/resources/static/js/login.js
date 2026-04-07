async function login() {
    const btn = document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Ingresando...";

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nombre: document.getElementById("user").value,
                password: document.getElementById("pass").value
            })
        });

        if (!response.ok) {
            const text = await response.text();
            document.getElementById("error").innerText = text;
            return;
        }

        const data = await response.json();
        localStorage.setItem("token", data.JWT);
        localStorage.setItem("userId",data.id);

        window.location.href = "/view/home";

    } catch (e) {
        document.getElementById("error").innerText = "Error de conexión";
    } finally {
        btn.disabled = false;
        btn.innerText = "Ingresar";
    }
}