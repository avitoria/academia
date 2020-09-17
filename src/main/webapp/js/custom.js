function confirmar(curso) {
	
	// The confirm() method returns true if the user clicked "OK", and false otherwise. 
	if (confirm('¿Est\u00E1 seguro de que desea eliminar el curso ' + curso + '?')) {
		
	} else {
		event.preventDefault();
	}
	
}