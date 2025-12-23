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
		<div>Ícono del mensaje</div>
		<h1>${title}</h1>
		<p>${description}</p>
		<a class="general-button text-decoration-none button"
			href="${pageContext.request.contextPath}${href}">Volver</a>
	</section>

</body>

</html>