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
        
        <form method="POST" action="${pageContext.request.contextPath}/GestionarUsuarioController">
                    <input type="hidden" name="ruta" value="confirmarRegistro">
        
        <section class="card">

            <div class="fields">
                <label class="form-row-label">Nombre*:</label>
                <div>
                    <input name="name" id="name" class="form-row-input" type="text" placeholder="Ingresa tu nombre" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Apellido*:</label>
                <div>
                    <input name="lastname" id="lastname" class="form-row-input" type="text" placeholder="Ingresa tu apellido" value="<%= request.getParameter("lastname") != null ? request.getParameter("lastname") : "" %>">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Fecha de nacimiento*:</label>
                <div>
                    <input name="birthdate" id="birthdate" class="form-row-input" type="date" placeholder="Ingresa tu fecha de nacimiento" value="<%= request.getParameter("birthdate") != null ? request.getParameter("birthdate") : "" %>">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Correo*:</label>
                <div>
                    <input name="email" id="email" class="form-row-input" type="email" placeholder="Ingresa tu correo" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Clave*:</label>
                <div>
                    <input name="password" id="password" class="form-row-input" type="password" placeholder="Ingresa tu clave">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Repetir clave*:</label>
                <div>
                    <input name="passwordConfirmation" id="passwordConfirmation" class="form-row-input" type="password" placeholder="Repite tu clave">
                </div>
            </div>

            <div class="button">
                <button type="submit" class="text-button">Registrarse</button>
            </div>

        </section>
               </form>
       
    </main>
</body>

</html>