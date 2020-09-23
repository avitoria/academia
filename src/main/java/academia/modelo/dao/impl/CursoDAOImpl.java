package academia.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import academia.modelo.ConnectionManager;
import academia.modelo.dao.CursoDAO;
import academia.modelo.pojo.Curso;
import academia.modelo.pojo.Usuario;

public class CursoDAOImpl implements CursoDAO {

	private static CursoDAOImpl INSTANCE = null;
	private static final Logger LOG = Logger.getLogger(CursoDAOImpl.class);

	private final static String SQL_LISTAR = "SELECT \n" + "	c.id as 'curso_id',\n" + "	c.identificador,\n"
			+ "	c.nombre as 'curso_nombre',\n" + "	c.horas,\n" + "	f.id as 'profesor_id',\n"
			+ "	f.nombre as 'profesor_nombre',\n" + "	f.apellidos as 'profesor_apellidos'\n" + "\n"
			+ "FROM cursos c, profesores f\n" + "WHERE\n" + "	c.id_profesor = f.id;";
	private final static String SQL_GET_BY_ID = "SELECT id, curso, identificador, horas, id_profesor FROM cursos WHERE id = ?;";
	// private final static String SQL_BUSCAR_POR_PROFESOR = "SELECT id, curso,
	// identificador, horas FROM cursos WHERE id_profesor = ?;";
	private final static String SQL_BUSCAR_POR_PROFESOR = "SELECT c.id, c.curso, c.identificador, c.horas, COUNT(ac.id_alumno) AS num_alumnos FROM cursos c, usuarios u, alumnos_curso ac WHERE c.id_profesor = u.id AND c.id = ac.id_curso AND u.id = ? GROUP BY c.id ORDER BY c.id ASC;";
	private final static String SQL_BUSCAR_POR_ALUMNO = "SELECT c.id, c.curso, c.identificador, c.horas, ac.id_alumno FROM cursos c, alumnos_curso ac WHERE c.id = ac.id_curso ORDER BY c.curso;";
	private final static String SQL_APUNTAR_ALUMNO_EN_CURSO = "INSERT INTO alumnos_curso (id_alumno, id_curso) VALUES (?, ?);";
	private final static String SQL_DESAPUNTAR_ALUMNO_DE_CURSO = "DELETE FROM alumnos_curso WHERE id_alumno = ? AND id_curso = ?";
	private final static String SQL_BORRAR_CURSO = "DELETE FROM cursos WHERE id = ?";
	private final static String SQL_CREAR_CURSO = "INSERT INTO cursos (curso, identificador, horas, id_profesor) VALUES (?, ?, ?, ?);";

	private CursoDAOImpl() {
		super();
	}

	static synchronized public CursoDAOImpl getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new CursoDAOImpl();
		}

		return INSTANCE;
	}

	@Override
	public ArrayList<Curso> listar() {

		ArrayList<Curso> cursos = new ArrayList<Curso>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_LISTAR);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {

				Curso c = new Curso();
				c.setId(rs.getInt("curso_id"));
				c.setNombre(rs.getString("curso_nombre"));
				c.setIdentificador(rs.getString("identificador"));
				c.setHoras(rs.getInt("horas"));

				Usuario p = new Usuario();
				p.setId(rs.getInt("profesor_id"));
				p.setNombre(rs.getString("profesor_nombre"));
				// TODO apellidos

				c.setProfesor(p);

				cursos.add(c);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cursos;
	}

	@Override
	public Curso getById(int id) throws Exception {
		Curso curso = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				curso = mapper(rs);

			} else {
				throw new Exception("No se ha encontrado el curso con el id indicado.");
			}
		}

		return curso;
	}

	@Override
	public ArrayList<Curso> buscarCursosPorProfesor(int idProfesor) {

		ArrayList<Curso> cursos = new ArrayList<Curso>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_BUSCAR_POR_PROFESOR)) {
			pst.setInt(1, idProfesor);

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					Curso c = mapper(rs);
					c.setInscripciones(rs.getInt("num_alumnos"));
					cursos.add(c);
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return cursos;
	}

	@Override
	public ArrayList<Curso> buscarCursosPorAlumno(int idAlumno) {
		HashMap<Integer, Curso> cursos = new HashMap<Integer, Curso>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_BUSCAR_POR_ALUMNO)) {
			// pst.setInt(1, idAlumno);

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					int idCurso = rs.getInt("id");

					Curso c = cursos.get(idCurso);

					if (c == null) {
						// El curso no está en el HashMap. Lo creamos y lo metemos.
						c = mapper(rs);

						if (rs.getInt("id_alumno") == idAlumno) {
							c.setApuntado(true);

						} else {
							c.setApuntado(false);
						}

					} else {
						// El curso sí está en el HashMap.
						if (rs.getInt("id_alumno") == idAlumno) {
							c.setApuntado(true);
						}
					}

					cursos.put(idCurso, c);
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return new ArrayList<Curso>(cursos.values());
	}

	@Override
	public Curso crearCurso(Curso curso) throws Exception {

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_CREAR_CURSO,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			pst.setString(1, curso.getNombre());
			pst.setString(2, curso.getIdentificador());
			pst.setInt(3, curso.getHoras());
			pst.setInt(4, curso.getProfesor().getId());

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 1) {
				try (ResultSet rsKeys = pst.getGeneratedKeys()) {
					if (rsKeys.next()) {
						int id = rsKeys.getInt(1);
						curso.setId(id);
					}
				}

			} else {
				throw new Exception("No se ha podido crear el curso.");
			}
		}

		return curso;
	}

	@Override
	public Curso borrarCurso(int idCurso) throws Exception {

		Curso curso = getById(idCurso);

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_BORRAR_CURSO)) {

			pst.setInt(1, idCurso);
			int affectedRows = pst.executeUpdate();

			if (affectedRows == 0) {
				throw new Exception("No se ha podido borrar el curso con el id indicado.");
			}
		}

		return curso;
	}

	@Override
	public void apuntarAlumnoEnCurso(int idAlumno, int idCurso) throws Exception {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_APUNTAR_ALUMNO_EN_CURSO,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			pst.setInt(1, idAlumno);
			pst.setInt(2, idCurso);

			int affectedRows = pst.executeUpdate();

			if (affectedRows < 1) {
				throw new Exception("No se ha podido realizar la inscripción en el curso.");
			}
		}
	}

	@Override
	public void desapuntarAlumnoDeCurso(int idAlumno, int idCurso) throws Exception {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DESAPUNTAR_ALUMNO_DE_CURSO,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			pst.setInt(1, idAlumno);
			pst.setInt(2, idCurso);

			int affectedRows = pst.executeUpdate();

			if (affectedRows < 1) {
				throw new Exception("No se ha podido realizar la baja del curso.");
			}
		}
	}

	private Curso mapper(ResultSet rs) throws SQLException {
		Curso curso = new Curso();
		curso.setId(rs.getInt("id"));
		curso.setNombre(rs.getString("curso"));
		curso.setIdentificador(rs.getString("identificador"));
		curso.setHoras(rs.getInt("horas"));

		// Este set casca si no se llama a SQL_BUSCAR_POR_PROFESOR, porque no tenemos
		// num_alumnos
		// curso.setInscripciones(rs.getInt("num_alumnos"));

		return curso;
	}

}
