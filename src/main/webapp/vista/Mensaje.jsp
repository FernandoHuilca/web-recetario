<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Mensaje</title>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/message.css">
</head>

<body class="margin-0 display-flex justify-content-center align-items-center">

	<section class="card text-align-center">
		<div>
			<img class="icon-message" src="${pageContext.request.contextPath}${urlimg}" alt="">
		</div>
		<h1 class="margin-20">${title}</h1>
		<p class="margin-20">${description}</p>
		<div class="display-flex justify-content-center">
			<c:choose>
				<c:when test="${not empty hrefConfirmar}">
					<!-- Modo confirmación: dos botones -->
					<div class="button-confirmacion">
						<a class="button text-decoration-none" href="${pageContext.request.contextPath}${hrefVolver}">Volver</a>
					</div>
					<div class="button-confirmacion">
						<a class="button text-decoration-none" href="${pageContext.request.contextPath}${hrefConfirmar}">Confirmar</a>
					</div>
				</c:when>
				<c:otherwise>
					<!-- Modo simple: un botón -->
					<div class="button-simple">
						<a class="button text-decoration-none" href="${pageContext.request.contextPath}${href}">Volver</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

</body>

</html>