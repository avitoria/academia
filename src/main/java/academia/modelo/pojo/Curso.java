package academia.modelo.pojo;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class Curso {

	private int id;

	@NotBlank(message = "Debe introducir un nombre")
	private String nombre;

	@NotBlank(message = "Debe introducir un identificador")
	private String identificador;

	@Min(value = 0, message = "El número de horas no puede ser negativo")
	private int horas;

	private Usuario profesor;

	private int inscripciones;

	private boolean apuntado;
	// TODO
	// private ArrayList<Alumno> alumno

	public Curso() {
		super();
		this.id = 0;
		this.nombre = "";
		this.identificador = "";
		this.horas = 0;
		this.profesor = new Usuario();
		this.inscripciones = 0;
		this.apuntado = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public int getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(int inscripciones) {
		this.inscripciones = inscripciones;
	}

	public boolean isApuntado() {
		return apuntado;
	}

	public void setApuntado(boolean apuntado) {
		this.apuntado = apuntado;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", identificador=" + identificador + ", horas=" + horas
				+ ", profesor=" + profesor + ", inscripciones=" + inscripciones + "]";
	}

}
