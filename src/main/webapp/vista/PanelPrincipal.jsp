<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3956990650935404"
     crossorigin="anonymous"></script>
</head>

<body>
    <header class="display-flex justify-content-between align-center padding-header">

        <div class="font-h1 ">Cocina de Gregory</div>

        <section class="display-flex">
            <input class="search-input" type="text" placeholder="Buscar receta...">
            <div class="button-search">Buscar</div>
        </section>

        <nav class="display-flex">
            <ul class="display-flex list-none-style">
                <li class="margin-nav-header">
                    <a class="general-button text-decoration-none"
                       href="${pageContext.request.contextPath}/GestionarRecetasController">Gestión de recetas</a>
                </li>
                
                <li class="margin-nav-header">
                    <a class="general-button text-decoration-none"
                       href="${pageContext.request.contextPath}/vista/_favorites.jsp">Favoritos</a>
                </li>
            </ul>

            <div class="avaterPerfil">
                <a href="#" class="avatar-link">
                    <img class="icon-user" src="${pageContext.request.contextPath}/assets/images/dashboard/defaultPerfil.jpeg" alt="Icono de usuario">
                </a>
            </div>
        </nav>

    </header>


    <main>
        <section class="display-flex  margin-cards">

            <article class="sizing-card bg-1-card margin-card">
                <div class="card-image">
                    <img src="${pageContext.request.contextPath}/assets/images/dashboard/pollonavideño.jpeg" alt="Pollo Navideño" loading="lazy">
                </div>

                <button class="favorite-btn" aria-pressed="false" aria-label="Guardar en favoritos">
                    <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                        <path d="M6 2h12v18l-6-4-6 4V2z" />
                    </svg>
                </button>

                <div class="recipeInfo name-description-recipe">
                    <h2 class="font-h3">Pollo Navideño</h2>
                    <p class="color-subTitle">Deliciosa receta de pollo navideño...</p>
                </div>

                <div>
                    <div class="button-viewRecipe text-align-center">
                        <a class="text-decoration-none text-while" 
                           href="#">Ver receta</a>
                    </div>
                </div>
            </article>
<!-- 

            <article class="sizing-card bg-1-card margin-card ad-card">
                <div class="card-image ad-image">
                    <img src="${pageContext.request.contextPath}/assets/images/publicidad/publicidad.png"
                         alt="Publicidad" loading="lazy">
                </div>

                <div class="recipeInfo name-description-recipe">
                    <span class="ad-badge">Publicidad</span>
                </div>

                <div>
                    <div class="button-viewRecipe text-align-center">
                        <a class="text-decoration-none text-while" href="#">Más información</a>
                    </div>
                </div>
            </article> -->



           <c:forEach items="${listaRecetasBD}" var="receta">
                
                <article class="sizing-card bg-1-card margin-card">

                    <div class="card-image">
                        <img src="${pageContext.request.contextPath}/assets/images/common/genericRecipeIcon.png" 
                             alt="${receta.nombre}" loading="lazy">
                    </div>

                    <button class="favorite-btn" aria-pressed="false" aria-label="Guardar en favoritos">
                        <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false" xmlns="http://www.w3.org/2000/svg">
                            <path d="M6 2h12v18l-6-4-6 4V2z" />
                        </svg>
                    </button>

                    <div class="recipeInfo name-description-recipe">
                        <h2 class="font-h3"><c:out value="${receta.nombre}"/></h2>
                        <p class="color-subTitle"><c:out value="${receta.descripcion}"/></p>
                    </div>

                    <div>
                        <form method="POST" action="${pageContext.request.contextPath}/VerRecetaController?ruta=verReceta">
                            <input type="hidden" name="idReceta" value="${receta.idReceta}" />

                            <div class="button-viewRecipe text-align-center">
                                <a class="text-decoration-none text-while"
                                   href="#"
                                   onclick="this.closest('form').submit(); return false;">
                                    Ver receta
                                </a>
                            </div>
                        </form>
                    </div>

                </article>

            </c:forEach>

        </section>

    </main>

</body>
</html>