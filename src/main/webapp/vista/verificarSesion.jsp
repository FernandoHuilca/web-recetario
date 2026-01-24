<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Verificar si existe sesión y si el usuario está autorizado
    if (session.getAttribute("autorizado") == null) {
        // Usuario no autorizado, redirigir a index.jsp
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
