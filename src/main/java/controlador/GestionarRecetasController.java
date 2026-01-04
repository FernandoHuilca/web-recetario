package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RecetaDAO;
import modelo.Receta;

@WebServlet("/GestionarRecetasController")
public class GestionarRecetasController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	public void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") != null)? req.getParameter("ruta") : "listarRecetas";
		
		switch(ruta) {
		case "volver":
			volver(req, resp);
			break;
		case "listarRecetas":
			listarRecetas(req, resp);
			break;
		default:
			System.out.println("Error!");
			break;
		
		}
		
	}
	/**
	 * Lista las recetas de un usuario específico usando RecetaDAO
	 */
	public void listarRecetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecetaDAO recetaDAO = new RecetaDAO();
		
		try {
			// Obtener ID de usuario del request
			String idUsuarioParam = req.getParameter("idUsuario");
			Long idUsuario = (idUsuarioParam != null && !idUsuarioParam.isEmpty())
					? Long.parseLong(idUsuarioParam)
					: 1; // valor por defecto
			
			// Obtener recetas por usuario
			List<Receta> recetas = recetaDAO.obtenerRecetasPorUsuario(idUsuario);
			req.setAttribute("recetas", recetas);
			req.setAttribute("idUsuario", idUsuario);
			req.getRequestDispatcher("/vista/ListadoRecetas.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Error al cargar las recetas del usuario");
			req.getRequestDispatcher("/vista/error.jsp").forward(req, resp);
		} finally {
			recetaDAO.cerrar();
		}
	}
	
	
		public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// Redirige al panel principal (vista anterior)
			resp.sendRedirect(req.getContextPath() + "/vista/PanelPrincipal.jsp");
	}
	
}