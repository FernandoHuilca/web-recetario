package controlador;

import java.io.IOException;
import java.util.List;

import dao.RecetaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Receta;

@WebServlet("/BuscarRecetaController")
public class BuscarRecetaController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutear(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutear(req, resp);
	}

	private void rutear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String ruta = (req.getParameter("ruta") != null) ? req.getParameter("ruta") : "volver";
		
		switch(ruta) {
		case "solicitarBuscarRecetaPorNombre":
			solicitarBuscarRecetaPorNombre(req, resp);
			break;
		case "volver":
			volver(req, resp);
			break;
		}
	}
	
	public void solicitarBuscarRecetaPorNombre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		String nombre = req.getParameter("criterioBusqueda");
		// 2. Hablar con el modelo
		RecetaDAO recetaDAO = new RecetaDAO();
		List<Receta> recetas = recetaDAO.obtenerRecetasPorNombre(nombre);
		// 3. Llamar a la vista
		if (recetas == null || recetas.isEmpty()) {
			req.setAttribute("urlimg", "/assets/images/message/error.png");
			req.setAttribute("title", "Error");
			req.setAttribute("description", "Receta no encontrada.");
			req.setAttribute("href", "/BuscarRecetaController?ruta=volver");
			req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
		}else {
			req.setAttribute("listaRecetasBD", recetas);
			req.setAttribute("showListarRecetas", true);
			req.setAttribute("criterioBusqueda", nombre);
			req.getRequestDispatcher("/vista/PanelPrincipal.jsp").forward(req, resp);
		}
	}
	
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		resp.sendRedirect(req.getContextPath() + "/VerPanelPrincipalController?ruta=cargarRecetas");
	}
	

}
