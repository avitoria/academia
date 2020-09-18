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
    
	<title>Panel alumno</title>
</head>
<body>
	<c:if test="${not empty mensaje}">
		<div class="alert alert-success" role="alert">${mensaje}</div>
	</c:if>
	
	<table class="tabla table table-striped">
		<thead>
			<tr>
				<td>Curso</td>
				<td>Identificador</td>
				<td>Horas</td>
				<td>Apuntado</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cursos}" var="c">
				<tr>
					<td>${c.nombre}</td>
					<td>${c.identificador}</td>
					<td>${c.horas} h.</td>
					<td>
						<c:if test="${c.apuntado}">
							Estás apuntado
						</c:if>
						<c:if test="${not c.apuntado}">
							<a href="../curso?id=${c.id}">Apúntate</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- nuestro javascript -->
    <script src="../js/custom.js"></script>
</body>
</html>