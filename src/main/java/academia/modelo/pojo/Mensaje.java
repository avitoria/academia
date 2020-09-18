package academia.modelo.pojo;

public class Mensaje {

	private String tipo;
	private String texto;

	public Mensaje() {
		super();
		this.tipo = "";
		this.texto = "";
	}

	public Mensaje(String tipo, String texto) {
		this();
		this.tipo = tipo;
		this.texto = texto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
