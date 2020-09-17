package academia.modelo.dao;

import java.util.ArrayList;

import academia.modelo.pojo.Usuario;

public interface UsuarioDAO {

	ArrayList<Usuario> listar();

	Usuario buscar(String nombre, String password);
}
