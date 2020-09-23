<%@include file="../includes/cabecera.jsp" %>

	<c:if test="${not empty mensaje}">
		<div class="alert alert-${mensaje.tipo}" role="alert">${mensaje.texto}</div>
	</c:if>
	
	<a href="formulario.jsp">Crear nuevo curso</a>
	
	<table class="tabla table table-striped">
		<thead>
			<tr>
				<td>Id</td>
				<td>Curso</td>
				<td>Identificador</td>
				<td>Horas</td>
				<td>Inscripciones</td>
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
					<td>${c.inscripciones}</td>
					<td><a href="../curso?id=${c.id}" onclick="confirmar('${c.nombre}')"><i class="fas fa-trash fa-2x" title="Borrar curso"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- nuestro javascript -->
    <script src="../js/custom.js"></script>
</body>
</html>