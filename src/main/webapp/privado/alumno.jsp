<%@include file="../includes/cabecera.jsp" %>

	<c:if test="${not empty mensaje}">
		<div class="alert alert-${mensaje.tipo}" role="alert">${mensaje.texto}</div>
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
							Estás apuntado (<a href="../privado/curso?accion=baja&id=${c.id}">date de baja</a>)
						</c:if>
						<c:if test="${not c.apuntado}">
							<a href="../privado/curso?accion='alta'&id=${c.id}">Apúntate</a>
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