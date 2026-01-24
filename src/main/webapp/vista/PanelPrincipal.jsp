<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="verificarSesion.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
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

            <div class="avaterPerfil" id="avatarContainer" style="position: relative;">
                <button class="avatar-link" aria-label="Perfil de usuario" id="avatarBtn" style="background: none; border: none; padding: 0; cursor: pointer;">
                    <img class="icon-user" src="${pageContext.request.contextPath}/assets/images/dashboard/defaultPerfil.jpeg" alt="Imagen de perfil">
                </button>
                
                <div class="profile-dropdown" id="profileDropdown" style="position: absolute; top: 60px; right: 0; background: white; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); min-width: 180px; visibility: hidden; opacity: 0; transition: opacity 0.3s ease; z-index: 1000;">
                    <a href="${pageContext.request.contextPath}/GestionarPanelPrincipalController?ruta=cerrarSesion" id="logoutBtn" class="profile-option logout-btn" style="display: block; padding: 12px 16px; color: #000; text-decoration: none; cursor: pointer; border: none; width: 100%; text-align: left;">Cerrar sesión</a>
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
        document.addEventListener('DOMContentLoaded', function() {
            const avatarBtn = document.getElementById('avatarBtn');
            const profileDropdown = document.getElementById('profileDropdown');
            const avatarContainer = document.getElementById('avatarContainer');

            avatarBtn.addEventListener('click', function(e) {
                e.stopPropagation();
                
                if (profileDropdown.style.visibility === 'hidden' || profileDropdown.style.visibility === '') {
                    profileDropdown.style.visibility = 'visible';
                    profileDropdown.style.opacity = '1';
                } else {
                    profileDropdown.style.visibility = 'hidden';
                    profileDropdown.style.opacity = '0';
                }
            });

            document.addEventListener('click', function(e) {
                if (!avatarContainer.contains(e.target)) {
                    profileDropdown.style.visibility = 'hidden';
                    profileDropdown.style.opacity = '0';
                }
            });
        });
    </script>

</body>
</html>