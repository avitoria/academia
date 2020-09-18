package academia.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import academia.modelo.dao.impl.CursoDAOImpl;
import academia.modelo.pojo.Curso;
import academia.modelo.pojo.Usuario;

/**
 * Servlet implementation class CursoController
 */
@WebServlet("/curso")
public class CursoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();
	private static CursoDAOImpl dao = CursoDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		String pIdCurso = request.getParameter("id");
		String mensaje = "";

		Usuario usuario = (Usuario) sesion.getAttribute("usuario");

		if (pIdCurso != null) {
			int idCurso = Integer.parseInt(pIdCurso);

			if (usuario.getRol() == Usuario.ROL_ALUMNO) {
				// Damos de alta al alumno en el curso idCurso
				try {
					dao.apuntarAlumnoEnCurso(usuario.getId(), idCurso);
					mensaje = "Te has apuntado al curso correctamente.";

				} catch (Exception e) {
					mensaje = "No se ha podido realizar el alta en el curso";
				}

			} else if (usuario.getRol() == Usuario.ROL_PROFESOR) {
				// Borramos el curso idCurso
				try {
					dao.borrarCurso(idCurso);
					mensaje = "Se ha eliminado el curso con id " + idCurso;

				} catch (Exception e) {
					mensaje = "No se ha podido eliminar el curso con id " + idCurso;
					e.printStackTrace();
				}
			}

			sesion.setAttribute("mensaje", mensaje);
		}

		if (usuario.getRol() == Usuario.ROL_ALUMNO) {
			ArrayList<Curso> cursos = dao.buscarCursosPorAlumno(usuario.getId());
			sesion.setAttribute("cursos", cursos);

			response.sendRedirect("privado/alumno.jsp");

		} else if (usuario.getRol() == Usuario.ROL_PROFESOR) {
			ArrayList<Curso> cursos = dao.buscarCursosPorProfesor(usuario.getId());
			sesion.setAttribute("cursos", cursos);

			response.sendRedirect("privado/profesor.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mensaje = "";

		try {
			String nombre = request.getParameter("nombre");
			String identificador = request.getParameter("identificador");
			int horas = Integer.parseInt(request.getParameter("horas"));
			Usuario profesor = ((Usuario) request.getSession().getAttribute("usuario"));

			Curso curso = new Curso();
			curso.setNombre(nombre);
			curso.setIdentificador(identificador);
			curso.setHoras(horas);
			curso.setProfesor(profesor);

			Set<ConstraintViolation<Curso>> violations = validator.validate(curso);

			if (violations.isEmpty()) {
				dao.crearCurso(curso);
				mensaje = "El nuevo curso ha sido creado correctamente.";
				// request.setAttribute("mensaje", mensaje);

			} else {
				for (ConstraintViolation<Curso> v : violations) {
					mensaje += "<p><b>" + v.getPropertyPath() + "</b>: " + v.getMessage() + "</p>";
				}
			}

		} catch (Exception e) {
			mensaje = "No se ha podido crear el nuevo curso.";
			e.printStackTrace();

		} finally {
			// request.setAttribute("mensaje", mensaje);
			// request.getRequestDispatcher("privado/formulario.jsp").forward(request,
			// response);
			request.getSession().setAttribute("mensaje", mensaje);
			response.sendRedirect("privado/formulario.jsp");
		}

	}

}
