<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Gestión de recetas</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/recipeManagement.css" />
</head>

<body class="margin-0 padding-0">
	<header></header>
	<main>
		<div class="return">
			<a class="general-button text-decoration-none"
				href="${pageContext.request.contextPath}/vista/PanelPrincipal.jsp">Volver</a>
		</div>

		<section>
			<div>
				<h1 class="font-h1 text-align-center">Gestión de recetas</h1>
			</div>
			<table>
				<thead>
					<tr>
						<td>Nombre</td>
						<td>Descripción</td>
						<td>Imagen</td>
						<td>Actualizar</td>
						<td>Eliminar</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${recetas}" var="receta">

						<tr>
							<td>${receta.nombre}</td>

							<td>${receta.descripcion}</td>
							<td><img src="${pageContext.request.contextPath}/assets/images/common/genericRecipeIcon.png"
								alt="imagen receta 1" /></td>
							<td>
								<button>
									<a href="${pageContext.request.contextPath}/ActualizarRecetasController"> <img
										src="${pageContext.request.contextPath}/assets/images/recipeManagement/updateIcon.png"
										alt="icono actualizar" />
									</a>
								</button>
							</td>
							<td>
								<button>
									<img src="${pageContext.request.contextPath}/assets/images/recipeManagement/deleteIcon.png"
										alt="icono eliminar" />
								</button>
							</td>
						</tr>

					</c:forEach>
				</tbody>

			</table>
			<section>
				<div class="text-align-center margin-20">
					<a class="general-button text-decoration-none"
						href="${pageContext.request.contextPath}/RegistrarRecetasController">Registrar receta</a>
				</div>
			</section>

		</section>
	</main>
</body>

</html>