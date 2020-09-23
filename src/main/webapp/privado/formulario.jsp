<%@include file="../includes/cabecera.jsp" %>

	<c:if test="${not empty mensaje}">
		<div class="alert alert-${mensaje.tipo}" role="alert">${mensaje.texto}</div>
	</c:if>
	
	<form action="../curso" method="post">
		<div class="form-group">
			<label for="nombre">Nombre</label>
    		<input type="text" class="form-control" id="nombre" name="nombre">
  		</div>
  		<div class="form-group">
			<label for="identificador">Identificador</label>
    		<input type="text" class="form-control" id="identificador" name="identificador">
  		</div>
  		<div class="form-group">
			<label for="horas">Horas</label>
    		<input type="number" class="form-control" id="horas" name="horas" value="0">
  		</div>
  		
  		<button type="submit" class="btn btn-primary">Enviar</button>
	</form>
</body>
</html>