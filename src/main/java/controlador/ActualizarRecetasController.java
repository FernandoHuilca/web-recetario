package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;
import modelo.Usuario;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import dao.IngredienteDAO;

import dao.RecetaDAO;

@WebServlet("/ActualizarRecetasController")
@MultipartConfig
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
			break;
		case "volver":
			volver(request, response);
			break;
		default:
			System.out.print("Error!");
			break;
		}
	}
	
	public boolean actualizar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idReceta = Long.parseLong(request.getParameter("id"));
		System.out.println(idReceta);
		String nombre = request.getParameter("name");
		String descripcion = request.getParameter("description");
		Double tiempoPreparacion = Double.parseDouble(request.getParameter("time"));
		Integer porciones = Integer.parseInt(request.getParameter("servings"));
		String pasos = request.getParameter("instructions");
		String imagen = request.getParameter("image");
		String[] nombresIngredientes = request.getParameterValues("ingredients_name[]");
		String[] cantidadesIngredientes = request.getParameterValues("ingredients_quantity[]");
		String[] unidadesIngredientes = request.getParameterValues("ingredients_unit[]");

		// 2. Hablar con el modelo
		RecetaDAO recetaDAO = new RecetaDAO();
		Receta receta = recetaDAO.obtenerRecetaPorId(idReceta);
		receta.setNombre(nombre);
		receta.setDescripcion(descripcion);
		receta.setTiempoPreparacion(tiempoPreparacion);
		receta.setPorciones(porciones);
		receta.setDescripcionPasos(pasos);
		receta.setImagen(imagen);
		
		
		receta.getRecetaIngredientes().clear();
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		
		for (int i = 0; i < nombresIngredientes.length; i++) {
			String nombreIng = nombresIngredientes[i];
			double cantidad = Double.parseDouble(cantidadesIngredientes[i]);
			Unidad unidad = Unidad.valueOf(unidadesIngredientes[i]);
			
			// Buscar o crear ingrediente en BD para evitar cascade PERSIST issues
			Ingrediente ingrediente = ingredienteDAO.guardarIngrediente(new Ingrediente(nombreIng));
			
			receta.agregarIngrediente(ingrediente, cantidad, unidad);
		}
	
		boolean respuesta = recetaDAO.actualizarReceta(receta);
		
		// 3. Llamar a la vista
		if(respuesta) {
			request.setAttribute("urlimg", "/assets/images/message/success.png");
			request.setAttribute("title", "Éxito");
			request.setAttribute("description", "Actualización exitosa.");
			request.setAttribute("href", "/ActualizarRecetasController?ruta=volver&idUsuario=" + receta.getUsuario().getIdUsuario());
			request.getRequestDispatcher("vista/Mensaje.jsp").forward(request, response);
		}else {
			request.setAttribute("urlimg", "/assets/images/message/error.png");
			request.setAttribute("title", "Error");
			request.setAttribute("description", "Actualización fallida.");
			request.setAttribute("href", "/ActualizarRecetasController?ruta=actualizarReceta&idReceta=" + receta.getIdReceta());
			request.getRequestDispatcher("vista/Mensaje.jsp").forward(request, response);
		}
		
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
		listarRecetas(request, response);
	}

	public void listarRecetas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		response.sendRedirect(request.getContextPath() + "/GestionarRecetasController?idUsuario=" + idUsuario);
	}
	
	public void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listarRecetas(request, response);
	}
}
