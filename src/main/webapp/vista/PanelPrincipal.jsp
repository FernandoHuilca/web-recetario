<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="verificarSesion.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="monetag" content="0f1ad801d6a26680e64fcc83fdafdd9b">
<script>(function(s){s.dataset.zone='10518971',s.src='https://nap5k.com/tag.min.js'})([document.documentElement, document.body].filter(Boolean).pop().appendChild(document.createElement('script')))</script>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3956990650935404"
     crossorigin="anonymous"></script>
</head>

<body>
    <header class="display-flex justify-content-between align-center padding-header">

        <div class="font-h1">Cocina de Gregory</div>

		<!--<section class="display-flex">
            <input class="search-input" type="text" placeholder="Buscar receta..." name="criterioBusqueda" id="criterioBusqueda">
             <div class="button-search">Buscar</div>
            <a class="button-search text-decoration-none" href="${pageContext.request.contextPath}/BuscarRecetaController?ruta=solicitarBuscarRecetaPorNombre&nombre=">Buscar</a>
        </section> -->

        <form action="${pageContext.request.contextPath}/GestionarPanelPrincipalController?ruta=solicitarBuscarRecetaPorNombre" method="POST" class="display-flex" id="formularioBusqueda">
            <input class="search-input" type="text" placeholder="Buscar receta..." name="criterioBusqueda" id="criterioBusqueda" value="${criterioBusqueda}">
            <button type="submit" class="button-search" style="font: inherit;">Buscar</button>
        </form>
        <c:if test="${not empty showListarRecetas}">
            <a id="listarRecetasBtn"
               class="button-search text-decoration-none"
               href="${pageContext.request.contextPath}/GestionarPanelPrincipalController?ruta=cargarRecetas"
               style="font: inherit;">
                Listar recetas
            </a>
        </c:if>


        <nav class="display-flex">
            <ul class="display-flex list-none-style">
                <li class="margin-nav-header">
                    <a class="general-button text-decoration-none"
                       href="${pageContext.request.contextPath}/GestionarRecetasController?ruta=listarRecetas">Gestión de recetas</a>
                </li>
                
                <li class="margin-nav-header">
                    <a class="general-button text-decoration-none"
                       href="${pageContext.request.contextPath}/vista/_favorites.jsp">Favoritos</a>
                </li>
            </ul>

            <div class="avaterPerfil" style="position: relative;">
                <img class="icon-user" 
                     src="${pageContext.request.contextPath}/assets/images/dashboard/defaultPerfil.jpeg" 
                     alt="Imagen de perfil"
                     onclick="var m = document.getElementById('menuPerfil'); m.style.display = (m.style.display === 'block') ? 'none' : 'block';"
                     style="cursor: pointer;">
                
                <div id="menuPerfil" style="position: absolute; top: 55px; right: 0; background: white; border: 1px solid #ccc; border-radius: 5px; padding: 0; min-width: 150px; display: none; z-index: 9999; box-shadow: 0 2px 8px rgba(0,0,0,0.2);">
                    <a href="${pageContext.request.contextPath}/GestionarPanelPrincipalController?ruta=cerrarSesion" 
                       style="display: block; padding: 10px 15px; color: #333; text-decoration: none;">Cerrar sesión</a>
                </div>
            </div>
        </nav>

    </header>


    <main>
        <section class="display-flex margin-cards">

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

            <c:forEach items="${listaRecetasBD}" var="receta">
                
                <article class="sizing-card bg-1-card margin-card">

                    <div class="card-image">
                        <c:choose>
                            <c:when test="${not empty receta.imagen}">
                                <img src="${pageContext.request.contextPath}/assets/images/dashboard/${receta.imagen}"
                                     alt="${receta.nombre}" loading="lazy">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/assets/images/common/genericRecipeIcon.png"
                                     alt="${receta.nombre}" loading="lazy">
                            </c:otherwise>
                        </c:choose>
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
                        <form method="POST" action="${pageContext.request.contextPath}/GestionarPanelPrincipalController?ruta=verReceta">
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

    <script>
        // Cerrar menú al hacer click fuera
        document.addEventListener('click', function(e) {
            var menu = document.getElementById('menuPerfil');
            if (menu && !e.target.closest('.avaterPerfil')) {
                menu.style.display = 'none';
            }
        });
    </script>

</body>
</html>
