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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rutear(req, resp);	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rutear(req, resp);
	}

	private void rutear(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") != null)? req.getParameter("ruta") : "listarRecetas";
		
		switch(ruta) {
		case "actualizar":
			actualizar(req, resp);
			break;
		case "actualizarReceta":
			actualizarReceta(req, resp);
			break;
		case "cancelar":
			cancelar(req, resp);
			break;
		case "listarRecetas":
			listarRecetas(req, resp);
			break;
		case "volver":
			volver(req, resp);
			break;
		}
	}
	
	public boolean actualizar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idReceta = Long.parseLong(req.getParameter("id"));
		System.out.println(idReceta);
		String nombre = req.getParameter("name");
		String descripcion = req.getParameter("description");
		Double tiempo = req.getParameter("time").isEmpty() ? 0.0 : Double.parseDouble(req.getParameter("time"));
		Integer porciones = req.getParameter("servings").isEmpty() ? 0 : Integer.parseInt(req.getParameter("servings"));
		String pasos = req.getParameter("instructions");
		String imagen = req.getParameter("image");
		String[] nombresIngredientes = req.getParameterValues("ingredients_name[]");
		String[] cantidadesIngredientes = req.getParameterValues("ingredients_quantity[]");
		String[] unidadesIngredientes = req.getParameterValues("ingredients_unit[]");

		// 2. Hablar con el modelo
		RecetaDAO recetaDAO = new RecetaDAO();
		Receta receta = recetaDAO.obtenerRecetaPorId(idReceta);
		receta.setNombre(nombre);
		receta.setDescripcion(descripcion);
		receta.setTiempoPreparacion(tiempo);
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

		// Campos obligatorios vacíos
		if (receta.getNombre().isEmpty() || receta.getDescripcion().isEmpty() || 
			receta.getDescripcionPasos().isEmpty() || receta.getPorciones() == 0 || receta.getTiempoPreparacion() == 0 || 
			receta.getRecetaIngredientes().isEmpty()) {
			req.setAttribute("urlimg", "/assets/images/message/error.png");
			req.setAttribute("title", "Error");
			req.setAttribute("description", "Campos obligatorios vacíos.");
			req.setAttribute("href", "/ActualizarRecetasController?ruta=actualizarReceta&idReceta=" + receta.getIdReceta());
			// Guardar la receta en sesión para recuperarla después del mensaje
			req.getSession().setAttribute("recetaFallida", receta);
			req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			return false;
		}

		boolean respuesta = recetaDAO.actualizarReceta(receta);
		
		// 3. Llamar a la vista
		if(respuesta) {
			req.setAttribute("urlimg", "/assets/images/message/success.png");
			req.setAttribute("title", "Éxito");
			req.setAttribute("description", "Actualización exitosa.");
			req.setAttribute("href", "/ActualizarRecetasController?ruta=volver&idUsuario=" + receta.getUsuario().getIdUsuario());
			req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			return true;
		}else {
			req.setAttribute("urlimg", "/assets/images/message/error.png");
			req.setAttribute("title", "Error");
			req.setAttribute("description", "Actualización fallida.");
			req.setAttribute("href", "/ActualizarRecetasController?ruta=actualizarReceta&idReceta=" + receta.getIdReceta());
			// Guardar la receta en sesión para recuperarla después del mensaje
			req.getSession().setAttribute("recetaFallida", receta);
			req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			return false;
		}
	}
	
	public boolean actualizarReceta(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idReceta = Long.parseLong(req.getParameter("idReceta"));
		// 2. Hablar con el modelo
		List<Unidad> unidades = Arrays.asList(Unidad.values());
		
		// Verificar si hay una receta fallida en sesión
		Receta receta = (Receta) req.getSession().getAttribute("recetaFallida");
		if (receta != null) {
			// Limpiar la sesión
			req.getSession().removeAttribute("recetaFallida");
		} else {
			// Si no hay receta en sesión, obtenerla de la BD
			RecetaDAO recetaDAO = new RecetaDAO();
			receta = recetaDAO.obtenerRecetaPorId(idReceta);
		}
		
		// 3. Llamar a la vista
		if (receta == null) {
			return false;
		} else {
			req.setAttribute("unidades", unidades);
			req.setAttribute("receta", receta);
			req.getRequestDispatcher("vista/FormularioActualizacionRecetas.jsp").forward(req, resp);	
			return true;
		}
	}

	public void cancelar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		listarRecetas(req, resp);
	}

	public void listarRecetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		Long idUsuario = Long.parseLong(req.getParameter("idUsuario"));
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		req.setAttribute("idUsuario", idUsuario);
		req.getRequestDispatcher("GestionarRecetasController").forward(req, resp);
		/*resp.sendRedirect(req.getContextPath() + "/GestionarRecetasController?idUsuario=" + idUsuario);*/
	}
	
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		listarRecetas(req, resp);
	}
}
