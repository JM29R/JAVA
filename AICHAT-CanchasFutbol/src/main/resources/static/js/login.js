    async function login() {

        const user = document.getElementById("user").value;
        const pass = document.getElementById("pass").value;

        const response = await fetch("/public/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                user: user,
                pass: pass
            })
        });

        if (!response.ok) {
            document.getElementById("error").innerText = "Credenciales incorrectas";
            return;
        }

        const data = await response.json();

        console.log("Respuesta backend:", data);

        localStorage.setItem("token", data.tokenJWT);

        window.location.href = "/admin";
    }