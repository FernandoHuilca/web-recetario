<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign-in</title>

    <link rel="stylesheet" href="../css/framework.css">
    <link rel="stylesheet" href="../css/signin.css">
</head>

<body class="margin-0 display-flex justify-content-center align-items-center">
    <main class="display-flex flex-column justify-content-center align-items-center">

        <div style="align-self: flex-start;"><a class="general-button text-decoration-none" href="${pageContext.request.contextPath}/GestionarUsuarioController?ruta=volver">Volver</a></div>

        <h1 class="text-align-center">Iniciar sesión</h1>

        <!-- <section class="card">

            <div class="fields">
                <label class="form-row-label">Correo*:</label>
                <div>
                    <input class="form-row-input" type="email" placeholder="Ingresa tu correo">
                </div>
            </div>

            <div class="fields">
                <label class="form-row-label">Contrseña*:</label>
                <div>
                    <input class="form-row-input" type="password" placeholder="Ingresa tu clave">
                </div>
            </div>
            <div class="button">
                <a class="text-button text-decoration-none" href="../vista/PanelPrincipal.jsp">Iniciar sesión</a>
            </div>
        </section> -->
        
        <section class="card">
            <form action="${pageContext.request.contextPath}/GestionarUsuarioController?ruta=confirmarInicioSesion" method="POST">
                
                <div class="fields">
                    <label class="form-row-label">Correo*:</label>
                    <div>
                        <input name="email" id="email" class="form-row-input" type="email" placeholder="Ingresa tu correo">
                    </div>
                </div>

                <div class="fields">
                    <label class="form-row-label">Clave*:</label>
                    <div>
                        <input name="password" id="password" class="form-row-input" type="password" placeholder="Ingresa tu clave">
                    </div>
                </div>

                <div class="button">
                    <button type="submit" class="text-button">
                        Iniciar sesión
                    </button>
                </div>
                

                
                
            </form>
        </section>
        
        
    </main>
</body>

</html>