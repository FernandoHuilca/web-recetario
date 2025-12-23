package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Ingrediente;

import java.io.IOException;
import java.util.List;

@WebServlet("/ActualizarRecetasController")
public class ActualizarRecetasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActualizarRecetasController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rutear(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rutear(request, response);
	}

	private void rutear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ruta = (request.getParameter("ruta") != null)? request.getParameter("ruta") : "actualizarReceta";
		
		switch(ruta) {
		case "actualizarReceta":
			actualizarReceta(request, response);
			break;
		case "cancelar":
			cancelar(request, response);
			break;
		case "actualizar":
			actualizar(request, response);
			break;
		case "volver":
			volver(request, response);
			break;
		default:
			System.out.print("Error!");
			break;
		}
	}
	
	/*
	 * Métodos del Controlador relativos al negocio
	 * 1. Obtener Parámetros
	 * 2. Hablar con el modelo
	 * 3. Llamar a la vista
	 */
	public boolean actualizarReceta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// No necesito obtener parámetros ni hablar con el modelo, solo llamar a la vista
		response.sendRedirect("vista/FormularioActualizacionRecetas.jsp");
		return true;
	}

	public void cancelar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.print("cancelar!");
	}

	public boolean actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idReceta = Integer.parseInt(request.getParameter("idReceta"));
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("iddescripcion");
		double tiempoPreparacion = Double.parseDouble(request.getParameter("tiempoPreparacion"));
		String descripcionPasos= request.getParameter("descripcionPasos");
		String porciones = request.getParameter("porciones");
		//List<Ingrediente> ingredientes = ;
		return true;
	}

	public void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
