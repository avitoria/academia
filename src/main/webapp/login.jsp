<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
	<title>Login usuario</title>
</head>
<body>
	<c:if test="${not empty mensaje}">
	ddd
		<div class="alert alert-${mensaje.tipo}" role="alert">${mensaje.texto}</div>
	</c:if>
	
	<form action="login" method="post">
		<div class="form-group">
			<label for="nombre">Nombre</label>
    		<input type="text" class="form-control" id="nombre" name="nombre" value="Ander">
  		</div>
  		<div class="form-group">
    		<label for="password">Password</label>
    		<input type="password" class="form-control" id="password" name="password" value="123456">
  		</div>
  		<button type="submit" class="btn btn-primary">Enviar</button>
	</form>

</body>
</html>