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
}
