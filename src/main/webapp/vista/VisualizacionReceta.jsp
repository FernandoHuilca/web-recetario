<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/recipeDetails.css" />
  <title>Detalle de la receta</title>
</head>

<body class="margin-0 padding-0 display-flex align-items-center flex-column">
  <header>
  </header>
  <main>
    <div class="return">
        <a class="general-button text-decoration-none" href="${pageContext.request.contextPath}/VerPanelPrincipalController">Volver</a>
    </div>
    
    <section>
      <div>
        <h1 class="text-align-center font-h1">Receta: <c:out value="${receta.nombre}"/></h1>
      </div>
      <div>
        <div class="display-flex">
          <div>
             <c:choose>
                <c:when test="${not empty receta.imagen}">
                     <img src="${pageContext.request.contextPath}/assets/images/dashboard/${receta.imagen}" alt="${receta.nombre}" style="max-width: 100%; width: auto; border-radius: 10px;"/>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/assets/images/common/genericRecipeIcon.png" alt="imagen genérica" style="max-width: 100%; width: auto; border-radius: 10px;" />
                </c:otherwise>
            </c:choose>
          </div>
          <div style="margin-left: 20px; flex-grow: 1;">
            <div>
              <h2 class="margin-0 font-italic padding-bottom-15">
                <c:out value="${receta.nombre}"/>
              </h2>
            </div>
            <div class="padding-bottom-15">
              <c:out value="${receta.descripcion}"/>
            </div>
            <div class="display-flex">
              <div class="display-flex padding-right-15">
                <div class="sub-title">Total:&nbsp;</div> <c:out value="${receta.tiempoPreparacion}"/> min
              </div>
              <div class="display-flex">
                <div class="sub-title">Porciones:&nbsp;</div> <c:out value="${receta.porciones}"/>
              </div>
            </div>
          </div>
        </div>

        <div class="padding-bottom-30">
          <div>
            <h2 class="margin-0">Ingredientes</h2>
          </div>
          <ul class="margin-0">
            <c:forEach items="${receta.recetaIngredientes}" var="ri">
                <li>
                    <strong><c:out value="${ri.ingrediente.nombre}"/></strong>: 
                    <c:out value="${ri.cantidad}"/> 
                    <c:out value="${ri.unidad}"/>
                </li>
            </c:forEach>
            
            <c:if test="${empty receta.recetaIngredientes}">
                <li>No hay ingredientes registrados.</li>
            </c:if>
          </ul>
        </div>

        <div>
          <section>
            <div>
              <h2 class="margin-0">Pasos</h2>
            </div>
            <div class="margin-0" style="white-space: pre-line; line-height: 1.6;">
                <c:out value="${receta.descripcionPasos}"/>
            </div>
          </section>
        </div>
      </div>
    </section>
  </main>
</body>

</html>