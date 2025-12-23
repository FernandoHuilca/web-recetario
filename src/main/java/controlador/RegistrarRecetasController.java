package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistrarRecetasControlle")
public class RegistrarRecetasController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}
	
	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") != null) ? "listarRecetas": req.getParameter("ruta");
		
		switch(ruta) {
		case "registrarReceta":
			this.registrarReceta(req, resp);
			break;
		case "guardar":
			this.guardar(req, resp);
			break;
		case "cancelar":
			this.cancelar(req, resp);
			break;
		case "volver":
			this.volver(req, resp);
			break;
		case "listarRecetas":
			this.listarRecetas(req, resp);
			break;
		}
	}

	public void registrarReceta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		//3. Llamar a la vista
	}

	public void guardar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		//3. Llamar a la vista
	}

	public void cancelar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		//3. Llamar a la vista
	}

	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		//3. Llamar a la vista
	}

	public void listarRecetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		//3. Llamar a la vista
	}

}
