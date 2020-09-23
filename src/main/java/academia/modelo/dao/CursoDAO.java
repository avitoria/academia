package academia.modelo.dao;

import java.util.ArrayList;

import academia.modelo.pojo.Curso;

public interface CursoDAO {

	/**
	 * Devuelve una lista de todos los cursos
	 * 
	 * @return lista de cursos
	 */
	ArrayList<Curso> listar();

	Curso getById(int id) throws Exception;

	/**
	 * Devuelve todos los cursos de un profesor
	 * 
	 * @param idProfesor
	 * @return todos los cursos del profesor especificado
	 */
	ArrayList<Curso> buscarCursosPorProfesor(int idProfesor); // ¿Por qué no lanza excepción?

	/**
	 * Devuelve todos los cursos en los que está apuntado un alumno
	 * 
	 * @param idAlumno
	 * @return todos
	 */
	ArrayList<Curso> buscarCursosPorAlumno(int idAlumno);

	/**
	 * Elimina un curso
	 * 
	 * @param idCurso
	 * @return curso eliminado
	 * @throws Exception
	 */
	Curso borrarCurso(int idCurso) throws Exception;

	/**
	 * Crea un nuevo curso
	 * 
	 * @param curso
	 * @return curso creado
	 * @throws Exception
	 */
	Curso crearCurso(Curso curso) throws Exception;

	/**
	 * Da de alta a un alumno en un curso
	 * 
	 * @param idAlumno
	 * @param idCurso
	 * @throws Exception
	 */
	void apuntarAlumnoEnCurso(int idAlumno, int idCurso) throws Exception;

	/**
	 * Da de baja a un alumno de un curso
	 * 
	 * @param idAlumno
	 * @param idCurso
	 * @throws Exception
	 */
	void desapuntarAlumnoDeCurso(int idAlumno, int idCurso) throws Exception;
}
