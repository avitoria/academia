<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
    <!-- fontawesome 5 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
    
	<title>Panel profesor</title>
</head>
<body>
	<c:if test="${not empty mensaje}">
		<div class="alert alert-success" role="alert">${mensaje}</div>
	</c:if>
	
	<a href="formulario.jsp">Crear nuevo curso</a>
	
	<table class="tabla table table-striped">
		<thead>
			<tr>
				<td>Id</td>
				<td>Curso</td>
				<td>Identificador</td>
				<td>Horas</td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cursos}" var="c">
				<tr>
					<td>${c.id}</td>
					<td>${c.nombre}</td>
					<td>${c.identificador}</td>
					<td>${c.horas} h.</td>
					<td><a href="../curso?id=${c.id}" onclick="confirmar('${c.nombre}')"><i class="fas fa-trash fa-2x" title="Borrar curso"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- nuestro javascript -->
    <script src="../js/custom.js"></script>
</body>
</html>