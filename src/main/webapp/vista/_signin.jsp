<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign-in</title>

    <link rel="stylesheet" href="../css/framework.css">
    <link rel="stylesheet" href="../css/signin.css">
</head>

<body class="margin-0 display-flex justify-content-center align-items-center">
    <main>

        <div><a class="general-button text-decoration-none" href="../index.jsp">Volver</a></div>

        <h1 class="text-align-center">Iniciar sesión</h1>

        <section class="card">

            <div class="fields">
                <label class="form-row-label">Correo*:</label>
                <div>
                    <input class="form-row-input" type="email" placeholder="Ingresa tu correo">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Contrseña*:</label>
                <div>
                    <input class="form-row-input" type="password" placeholder="Ingresa tu contraseña">
                </div>
            </div>
            <div class="button">
                <a class="text-button text-decoration-none" href="../vista/PanelPrincipal.jsp">Iniciar sesión</a>
            </div>
        </section>
    </main>
</body>

</html>