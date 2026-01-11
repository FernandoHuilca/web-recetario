<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign-up</title>
    <link rel="stylesheet" href="../css/framework.css">
    <link rel="stylesheet" href="../css/signup.css">
</head>

<body class="margin-0 display-flex justify-content-center align-items-center">
    <main>
        <div class="return"><a class="general-button text-decoration-none" href="../index.jsp">Volver</a></div>

        <h1 class="text-align-center">Registro</h1>

        <section class="card">

            <div class="fields">
                <label class="form-row-label" for="nombre">Nombre*:</label>
                <div>
                    <input class="form-row-input" id="nombre" type="text" placeholder="Ingresa tu nombre" required>
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label" for="apellido">Apellido*:</label>
                <div>
                    <input class="form-row-input" id="apellido" type="text" placeholder="Ingresa tu apellido" required>
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label" for="fecha">Fecha de nacimiento*:</label>
                <div>
                    <input class="form-row-input" id="fecha" type="date" placeholder="Ingresa tu fecha de nacimiento" required>
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label" for="email">Correo*:</label>
                <div>
                    <input class="form-row-input" id="email" type="email" placeholder="Ingresa tu correo" required>
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label" for="password">Contraseña*:</label>
                <div>
                    <input class="form-row-input" id="password" type="password" placeholder="Ingresa tu contraseña" required>
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label" for="password2">Repetir contraseña*:</label>
                <div>
                    <input class="form-row-input" id="password2" type="password" placeholder="Repite tu contraseña" required>
                </div>
            </div>

            <div class="button">
                <a class="text-button text-decoration-none" href="../vista/_signin.jsp">Registrarse</a>
            </div>

        </section>
    </main>
</body>

</html>