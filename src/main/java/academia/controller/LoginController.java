package academia.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import academia.modelo.dao.impl.UsuarioDAOImpl;
import academia.modelo.pojo.Mensaje;
import academia.modelo.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");

		UsuarioDAOImpl dao = UsuarioDAOImpl.getInstance();

		Usuario usuario = dao.buscar(nombre, password);

		if (usuario != null) {
			session.setMaxInactiveInterval(60 * 5);
			session.setAttribute("usuario", usuario);

			response.sendRedirect("privado/curso");

		} else {
			request.setAttribute("mensaje", new Mensaje("warning", "Error: usuario y/o password incorrectos."));
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
