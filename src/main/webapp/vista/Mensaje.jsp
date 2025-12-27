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
			<img class="icon-message" src="../assets/images/message/success.png" alt="">
		</div>
		<h1 class="margin-20">${title}</h1>
		<p class="margin-20">${description}</p>
		<div class="display-flex justify-content-center">
			<div class="button">
				<a class="text-button text-decoration-none" href="${pageContext.request.contextPath}${href}">Volver</a>
			</div>
		</div>
	</section>

</body>

</html>