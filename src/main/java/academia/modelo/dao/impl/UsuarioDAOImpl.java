package academia.modelo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import academia.modelo.ConnectionManager;
import academia.modelo.dao.UsuarioDAO;
import academia.modelo.pojo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	private static UsuarioDAOImpl INSTANCE = null;
	private static final Logger LOG = Logger.getLogger(UsuarioDAOImpl.class);

	private static final String SQL_LISTAR = "SELECT id, nombre, apellidos, rol, password FROM usuarios LIMIT 100;";
	private static final String SQL_BUSCAR = "SELECT * FROM usuarios WHERE nombre = ? AND password = MD5(?);";

	private UsuarioDAOImpl() {
		super();
	}

	static synchronized public UsuarioDAOImpl getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAOImpl();
		}

		return INSTANCE;
	}

	@Override
	public ArrayList<Usuario> listar() {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_LISTAR);
				ResultSet rs = pst.executeQuery();) {

			LOG.debug(pst);
			while (rs.next()) {
				usuarios.add(mapper(rs));
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return usuarios;
	}

	@Override
	public Usuario buscar(String nombre, String password) {
		Usuario usuario = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_BUSCAR);) {
			pst.setString(1, nombre);
			pst.setString(2, password);

			try (ResultSet rs = pst.executeQuery()) {

				if (rs.next()) {
					usuario = mapper(rs);

				} else {
					throw new Exception("Usuario no encontrado");
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return usuario;
	}

	private Usuario mapper(ResultSet rs) throws SQLException {

		Usuario usuario = new Usuario();

		usuario.setId(rs.getInt("id"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setApellidos(rs.getString("apellidos"));
		usuario.setRol(rs.getInt("rol"));
		usuario.setPassword(rs.getString("password"));

		return usuario;
	}

}
