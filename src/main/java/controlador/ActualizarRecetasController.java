package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import dao.RecetaDAO;

@WebServlet("/ActualizarRecetasController")
public class ActualizarRecetasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rutear(request, response);	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		rutear(request, response);
	}

	private void rutear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ruta = (request.getParameter("ruta") != null)? request.getParameter("ruta") : "listarRecetas";
		
		switch(ruta) {
		case "actualizar":
			actualizar(request, response);
			break;
		case "actualizarReceta":
			actualizarReceta(request, response);
			break;
		case "cancelar":
			cancelar(request, response);
			break;
		case "listarRecetas":
			listarRecetas(request, response);
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
	public boolean actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		/*
		int idReceta = Integer.parseInt(request.getParameter("idReceta"));
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("iddescripcion");
		double tiempoPreparacion = Double.parseDouble(request.getParameter("tiempoPreparacion"));
		String descripcionPasos= request.getParameter("descripcionPasos");
		String porciones = request.getParameter("porciones");
		//List<Ingrediente> ingredientes = ;
		return true;
		*/
		return false;
	}
	
	public boolean actualizarReceta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idReceta = Long.parseLong(request.getParameter("idReceta"));
		// 2. Hablar con el modelo
		List<Unidad> unidades = Arrays.asList(Unidad.values());
		
		RecetaDAO recetaDAO = new RecetaDAO();
		Receta receta = recetaDAO.obtenerRecetaPorId(idReceta);
		// 3. Llamar a la vista
		if (receta == null) {
			// haga algo
			return false;
		} else {
			request.setAttribute("unidades", unidades);
			request.setAttribute("receta", receta);
			request.getRequestDispatcher("vista/FormularioActualizacionRecetas.jsp").forward(request, response);	
			return true;
		}
	}

	public void cancelar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		System.out.print("cancelar!");
	}

	public void listarRecetas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
	}

	public void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
	}
	
}
