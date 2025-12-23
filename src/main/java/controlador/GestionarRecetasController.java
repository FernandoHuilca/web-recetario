package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Receta;

@WebServlet("/GestionarRecetasController")
public class GestionarRecetasController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		listar(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		listar(req, resp);
	}
	
	public void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Receta> recetas = Receta.obtenerRecetas();
		
		req.setAttribute("recetas", recetas);
		req.getRequestDispatcher("vista/ListadoRecetas.jsp").forward(req, resp);
	}
	
}
