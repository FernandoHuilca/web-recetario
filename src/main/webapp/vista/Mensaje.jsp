<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mensaje</title>
</head>
<body>

	<div>
		<div>Ícono del mensaje</div>
		<h1>${title}</h1>
		<p>${description}</p>
		<a href="${pageContext.request.contextPath}${href}">Volver</a>
	</div>

</body>
</html>