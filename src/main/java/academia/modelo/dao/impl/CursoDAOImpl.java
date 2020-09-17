package academia.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private final static String SQL_BUSCAR_POR_PROFESOR = "SELECT id, curso, identificador, horas FROM cursos WHERE id_profesor = ?;";
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
	public ArrayList<Curso> buscarCursosPorProfesor(int idProfesor) {

		ArrayList<Curso> cursos = new ArrayList<Curso>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_BUSCAR_POR_PROFESOR)) {
			pst.setInt(1, idProfesor);

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					cursos.add(mapper(rs));
				}
			}

		} catch (Exception e) {

		}

		return cursos;
	}

	private Curso mapper(ResultSet rs) throws SQLException {
		Curso curso = new Curso();
		curso.setId(rs.getInt("id"));
		curso.setNombre(rs.getString("curso"));
		curso.setIdentificador(rs.getString("identificador"));
		curso.setHoras(rs.getInt("horas"));

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

}
