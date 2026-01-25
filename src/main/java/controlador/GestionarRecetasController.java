package controlador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.dao.FactoryDAO;
import modelo.entidades.DetalleIngrediente;
import modelo.entidades.Ingrediente;
import modelo.entidades.Receta;
import modelo.entidades.Unidad;
import modelo.entidades.Usuario;
import util.MensajeUtil;

@WebServlet("/GestionarRecetasController")
@MultipartConfig // Necesario para manejar multipart/form-data (archivos)
public class GestionarRecetasController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String RUTA_GESTIONAR_RECETAS_CONTROLLER = "/GestionarRecetasController?ruta=";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	public void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") != null) ? req.getParameter("ruta") : "listarRecetas";

		switch (ruta) {
		// 1. LISTAR
		case "listarRecetas":
			listarRecetas(req, resp);
			break;

		// 2. REGISTRAR
		case "solicitarRegistrarReceta":
			solicitarRegistrarReceta(req, resp);
			break;
		case "confirmarRegistro":
			confirmarRegistro(req, resp);
			break;

		// 3. ACTUALIZAR
		case "solicitarActualizarReceta":
			solicitarActualizarReceta(req, resp);
			break;
		case "actualizar":
			actualizar(req, resp);
			break;

		// 4. ELIMINAR
		case "solicitarEliminarReceta":
			solicitarEliminarReceta(req, resp);
			break;
		case "confirmarEliminacion":
			confirmarEliminacion(req, resp);
			break;

		// VOLVER
		case "volver":
			volver(req, resp);
			break;

		default:
			System.out.println("Error!");
			break;
		}
	}

	/*
	 * ---------------------------- 1. LISTAR ----------------------------
	 */
	public void listarRecetas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//RecetaDAO recetaDAO = new RecetaDAO();
		
		try {
			// Obtener ID de usuario del request
			//Receta receta = (Receta) req.getSession().getAttribute("recetaFallida");
			//String idUsuarioParam = req.getParameter("idUsuario");
			Long idUsuario = (Long) req.getSession().getAttribute("autorizado");

			// Obtener recetas por usuario
			//List<Receta> recetas = recetaDAO.obtenerRecetasPorUsuario(idUsuario);
			List<Receta> recetas = FactoryDAO.getFactory().getRecetaDAO().obtenerRecetasPorUsuario(idUsuario);
			req.setAttribute("recetas", recetas);
			req.setAttribute("idUsuario", idUsuario);
			req.getRequestDispatcher("/vista/ListadoRecetas.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			MensajeUtil.mostrarError(req, resp, "ERROR", "No se pudo listar las recetas",
					"/GestionarPanelPrincipalController");

		} finally {
			//recetaDAO.cerrar();
			FactoryDAO.getFactory().getRecetaDAO().cerrar();
		}
	}

	/*
	 * ----------------------------  2. REGISTRAR ---------------------------- 
	 */
	public void solicitarRegistrarReceta(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Obtener todas las unidades del enum
		List<Unidad> unidades = Arrays.asList(Unidad.values());

		// Llamar a la vista (Presentar)
		req.setAttribute("unidades", unidades);
		req.getRequestDispatcher("vista/FormularioRegistroRecetas.jsp").forward(req, resp);
	}
	
	
	public void confirmarRegistro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			// 1. Obtener los parámetros del formulario
			String nombre = req.getParameter("name");
			String descripcion = req.getParameter("description");
			Double tiempoPreparacion = Double.parseDouble(req.getParameter("time"));
			String pasos = req.getParameter("instructions");
			Integer porciones = Integer.parseInt(req.getParameter("servings"));
			String imagen = null;
			Long idUsuario = (Long) req.getSession().getAttribute("autorizado");

			// Captura de arrays de ingredientes
			String[] nombresIngredientes = req.getParameterValues("ingredients_name[]");
			String[] cantidadesIngredientes = req.getParameterValues("ingredients_quantity[]");
			String[] unidadesIngredientes = req.getParameterValues("ingredients_unit[]");

			// ===================== VALIDACIONES ===================== 
			// 2. Validar campos principales
			if (nombre == null || descripcion == null || tiempoPreparacion == null || pasos == null
					|| porciones == null || idUsuario == null || nombre.isBlank() || descripcion.isBlank()
					|| pasos.isBlank()) {
				MensajeUtil.mostrarError(req, resp, "ERROR", "Campos obligatorios no completados correctamente",
						RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
				return;
			}

			// 3. Validar que existan los ingredientes
			if (nombresIngredientes == null || nombresIngredientes.length == 0 || cantidadesIngredientes == null
					|| unidadesIngredientes == null || cantidadesIngredientes.length != nombresIngredientes.length
					|| unidadesIngredientes.length != nombresIngredientes.length) {
				MensajeUtil.mostrarError(req, resp, "ERROR", "Debe agregar al menos un ingrediente con todos sus datos",
						RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
				return;
			}

	        // Validar TODOS los ingredientes ANTES de guardar
			for (int i = 0; i < nombresIngredientes.length; i++) {

				if (nombresIngredientes[i] == null || cantidadesIngredientes[i] == null
						|| unidadesIngredientes[i] == null || nombresIngredientes[i].isBlank()
						|| cantidadesIngredientes[i].isBlank() || unidadesIngredientes[i].isBlank()) {
					MensajeUtil.mostrarError(req, resp, "ERROR", "Campos obligatorios no completados correctamente",
							RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
					return;
				}

				// Validar formato numérico
				try {
					Double.parseDouble(cantidadesIngredientes[i]);
				} catch (NumberFormatException e) {
					MensajeUtil.mostrarError(req, resp, "ERROR",
							"Cantidad inválida en ingrediente #" + (i + 1) + ": " + nombresIngredientes[i],
							RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
					return;
				}

				// Validar unidad
				try {
					Unidad.valueOf(unidadesIngredientes[i]);
				} catch (IllegalArgumentException e) {
					MensajeUtil.mostrarError(req, resp, "ERROR",
							"Unidad inválida en ingrediente #" + (i + 1) + ": " + unidadesIngredientes[i],
							RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
					return;
				}
			}

			// ===================== GUARDAR IMAGEN, RECETA, INGREDIENTE Y DETALLE INGREDIENTE ===================== 
			// Guardar imagen si viene en el request
			Part imagePart = req.getPart("image");
			if (imagePart != null && imagePart.getSize() > 0) {
				String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
				Path uploadDir = Paths.get(req.getServletContext().getRealPath("/assets/images/dashboard/"));
				Files.createDirectories(uploadDir);
				Path target = uploadDir.resolve(fileName);
				Files.copy(imagePart.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				imagen = fileName; // solo se persiste el nombre
			}

			// Construir la receta usando JPA
			Receta receta = new Receta();
			receta.setNombre(nombre);
			receta.setDescripcion(descripcion);
			receta.setTiempoPreparacion(tiempoPreparacion);
			receta.setPorciones(porciones);
			receta.setDescripcionPasos(pasos);
			receta.setImagen(imagen);

			// Asignar usuario (por defecto id=1). Requiere que exista en la BD.
			//Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);
			Usuario usuario = FactoryDAO.getFactory().getUsuarioDAO().getById(idUsuario);
			if (usuario == null) {
				MensajeUtil.mostrarError(req, resp, "ERROR",
						"No fue posible obtener el usuario.",
						RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
				return;
			}
			receta.setUsuario(usuario);
			
			// 4. Guardar usando RecetaDAO con JPA/ORM
			//boolean resultado = recetaDAO.guardarReceta(receta);
			boolean resultado = FactoryDAO.getFactory().getRecetaDAO().create(receta);
			// 5. Llamar a la vista con el resultado
			if (!resultado) {
				MensajeUtil.mostrarError(req, resp, "ERROR", "No fue posible guardar la receta en la base de datos",
						RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
				return;
			}

			// 8. Guardar los Ingredientes y DetallesIngredientes
			for (int i = 0; i < nombresIngredientes.length; i++) {
				String nombreIng = nombresIngredientes[i];
				
				// Buscar o crear ingrediente en BD para evitar cascade PERSIST issues
				//Ingrediente ingrediente = ingredienteDAO.guardarIngrediente(new Ingrediente(nombreIng));
				Ingrediente ingrediente = FactoryDAO.getFactory().getIngredienteDAO().guardarIngrediente(new Ingrediente(nombreIng));
				if (ingrediente == null) {
					// Si falla un ingrediente, deberías eliminar la receta que acabas de crear
					// o hacer rollback si usas transacciones
					MensajeUtil.mostrarError(req, resp, "ERROR",
							"Hubo un problema al procesar el ingrediente: " + nombreIng,
							RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
					return;
				}

				// Guardar DetalleIngrediente
				Double cantidad = Double.parseDouble(cantidadesIngredientes[i]);
				Unidad unidad = Unidad.valueOf(unidadesIngredientes[i]);
				DetalleIngrediente detalleIngrediente = new DetalleIngrediente(receta, ingrediente, cantidad, unidad);
				//detalleIngredienteDAO.guardarDetalleIngrediente(detalleIngrediente);
				FactoryDAO.getFactory().getDetalleIngredienteDAO().create(detalleIngrediente);
			}
			
			MensajeUtil.mostrarExito(req, resp, "Éxito: Receta creada", "La receta se ha creado exitosamente", RUTA_GESTIONAR_RECETAS_CONTROLLER+"listarRecetas");
			
		} catch (NumberFormatException e) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "Campos obligatorios no completados correctamente",
					RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
		} catch (Exception e) {
			e.printStackTrace();
			MensajeUtil.mostrarError(req, resp, "ERROR", "Error: " + e.getMessage(),
					RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarRegistrarReceta");
		} finally {
			// recetaDAO.cerrar();
			FactoryDAO.getFactory().getRecetaDAO().cerrar();
			// ingredienteDAO.cerrar();
			FactoryDAO.getFactory().getIngredienteDAO().cerrar();
			// usuarioDAO.cerrar();
			FactoryDAO.getFactory().getUsuarioDAO().cerrar();
			// detalleIngredienteDAO.cerrar();
			FactoryDAO.getFactory().getDetalleIngredienteDAO().cerrar();
		}
	}
	
	/*
	 * ----------------------------  3. ACTUALIZAR ----------------------------  
	 */
	public void solicitarActualizarReceta(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {

			// 1. Obtener los parámetros
			Long idReceta = Long.parseLong(req.getParameter("idReceta"));

			// 2. Hablar con el modelo
			List<Unidad> unidades = Arrays.asList(Unidad.values());

			// Verificar si hay una receta fallida en sesión
			Receta receta = (Receta) req.getSession().getAttribute("recetaFallida");
	        List<DetalleIngrediente> detalles = null;
			
	        if (receta != null) {
	            // Limpiar la sesión
	            req.getSession().removeAttribute("recetaFallida");
	            // Cargar los detalles de la BD porque los necesitamos para el formulario
	            //detalles = detalleDAO.obtenerPorReceta(idReceta);
				detalles = FactoryDAO.getFactory().getDetalleIngredienteDAO().obtenerPorReceta(idReceta);
			} else {
	            // Si no hay receta en sesión, obtenerla de la BD
	            //receta = recetaDAO.obtenerRecetaPorId(idReceta);
	            receta = FactoryDAO.getFactory().getRecetaDAO().getById(idReceta);
				// CARGAR LOS DETALLES DE INGREDIENTES
	            //detalles = detalleDAO.obtenerPorReceta(idReceta);
	        	detalles = FactoryDAO.getFactory().getDetalleIngredienteDAO().obtenerPorReceta(idReceta);
			}
			
			// 3. Llamar a la vista
			if (receta == null) {
				MensajeUtil.mostrarError(req, resp, "Error", "Receta no encontrada",
						RUTA_GESTIONAR_RECETAS_CONTROLLER + "volver");
			} else {
				req.setAttribute("unidades", unidades);
				req.setAttribute("receta", receta);
				req.setAttribute("detalles", detalles); // PASAR LOS DETALLES AL JSP
				req.getRequestDispatcher("vista/FormularioActualizacionRecetas.jsp").forward(req, resp);
			}
		} finally {
			// recetaDAO.cerrar();
			FactoryDAO.getFactory().getRecetaDAO().cerrar();
			// detalleDAO.cerrar();
			FactoryDAO.getFactory().getDetalleIngredienteDAO().cerrar();
		}
	}
	
	public void actualizar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	    
		// 1. Obtener los parámetros
		Long idReceta = Long.parseLong(req.getParameter("id"));
		String nombre = req.getParameter("name");
		String descripcion = req.getParameter("description");
		Double tiempoPreparacion = req.getParameter("time").isBlank() ? null : Double.parseDouble(req.getParameter("time"));
		Integer porciones = req.getParameter("servings").isBlank() ? null
				: Integer.parseInt(req.getParameter("servings"));
		String pasos = req.getParameter("instructions");
		String imagen = null;

		// Procesar imagen si se proporcionó
		Part imagePart = req.getPart("image");
		if (imagePart != null && imagePart.getSize() > 0) {
			String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
			Path uploadDir = Paths.get(req.getServletContext().getRealPath("/assets/images/dashboard/"));
			Files.createDirectories(uploadDir);
			Path target = uploadDir.resolve(fileName);
			Files.copy(imagePart.getInputStream(), target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			imagen = fileName;
		} else {
			// Si no se carga una nueva imagen, mantener la existente
			//Receta recetaTemp = recetaDAOTemp.obtenerRecetaPorId(idReceta);
			Receta recetaTemp = FactoryDAO.getFactory().getRecetaDAO().getById(idReceta);
			imagen = recetaTemp.getImagen();
		}

		String[] nombresIngredientes = req.getParameterValues("ingredients_name[]");
		String[] cantidadesIngredientes = req.getParameterValues("ingredients_quantity[]");
		String[] unidadesIngredientes = req.getParameterValues("ingredients_unit[]");

		boolean ingredientesInvalidos = false;

		for (int i = 0; i < nombresIngredientes.length; i++) {
			if (nombresIngredientes[i].isBlank() || cantidadesIngredientes[i] == null
					|| cantidadesIngredientes[i].isBlank()) {
				ingredientesInvalidos = true;
				break;
			}
		}

		// Validar campos
        if (nombre.isEmpty() || descripcion.isEmpty() || pasos.isEmpty() ||
                porciones == null || tiempoPreparacion == null || ingredientesInvalidos) {
            //Receta receta = recetaDAO.obtenerRecetaPorId(idReceta);
            Receta receta = FactoryDAO.getFactory().getRecetaDAO().getById(idReceta);
			req.getSession().setAttribute("recetaFallida", receta);
            MensajeUtil.mostrarError(req, resp, "Error", "Campos obligatorios vacíos.", 
                RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarActualizarReceta&idReceta=" + idReceta);
            return;
        }
		
		// 2. Actualizar la receta
		//Receta receta = recetaDAO.obtenerRecetaPorId(idReceta);
		Receta receta = FactoryDAO.getFactory().getRecetaDAO().getById(idReceta);
		//DetalleIngredienteDAO detalleIngredienteDAO = new DetalleIngredienteDAO();
		receta.setNombre(nombre);
		receta.setDescripcion(descripcion);
		receta.setTiempoPreparacion(tiempoPreparacion);
		receta.setPorciones(porciones);
		receta.setDescripcionPasos(pasos);
		receta.setImagen(imagen);
		
		//boolean respuesta = recetaDAO.actualizarReceta(receta);
		boolean respuesta = FactoryDAO.getFactory().getRecetaDAO().update(receta);
		// 3. Llamar a la vista
        if (!respuesta) {
            req.getSession().setAttribute("recetaFallida", receta);
            MensajeUtil.mostrarError(req, resp, "Error", "Actualización fallida.", 
                RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarActualizarReceta&idReceta=" + idReceta);
            return;
        }
		
        // 4. ELIMINAR los DetalleIngrediente antiguos
		//detalleIngredienteDAO.eliminarPorReceta(idReceta);
		FactoryDAO.getFactory().getDetalleIngredienteDAO().eliminarPorReceta(idReceta);
        // 5. CREAR y GUARDAR los nuevos DetalleIngrediente
		for (int i = 0; i < nombresIngredientes.length; i++) {
		    Ingrediente ingrediente =
		        //ingredienteDAO.guardarIngrediente(new Ingrediente(nombresIngredientes[i]));
				FactoryDAO.getFactory().getIngredienteDAO().guardarIngrediente(new Ingrediente(nombresIngredientes[i]));
		    if (ingrediente == null) {
                MensajeUtil.mostrarError(req, resp, "Error", 
                    "No se pudo guardar el ingrediente: " + nombresIngredientes[i], 
                    RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarActualizarReceta&idReceta=" + idReceta);
                return;
            }

			DetalleIngrediente detalleIngrediente = new DetalleIngrediente(receta, ingrediente, Double.parseDouble(cantidadesIngredientes[i]), Unidad.valueOf(unidadesIngredientes[i]));
		    //boolean detalleIngredienteGuardado = detalleIngredienteDAO.guardarDetalleIngrediente(detalleIngrediente);
		    boolean detalleIngredienteGuardado = FactoryDAO.getFactory().getDetalleIngredienteDAO().create(detalleIngrediente);
		    if (!detalleIngredienteGuardado) {
                MensajeUtil.mostrarError(req, resp, "Error", 
                    "No se pudo guardar el detalle del ingrediente", 
                    RUTA_GESTIONAR_RECETAS_CONTROLLER + "solicitarActualizarReceta&idReceta=" + idReceta);
                return;
            }
		}

		MensajeUtil.mostrarExito(req, resp, "Éxito", "Actualización exitosa.",
				RUTA_GESTIONAR_RECETAS_CONTROLLER + "volver");
	}

	/*
	 * ---------------------------------------------------- 4. ELIMINAR
	 * ---------------------------------------------------------
	 */
	private void solicitarEliminarReceta(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long idReceta = Long.parseLong(req.getParameter("idReceta"));

		/*
		 * if (idReceta == null || idReceta.trim().isBlank()) {
		 * establecerContenidoMensaje(req, resp, "ERROR: ID no válido",
		 * "No se proporcionó un ID de receta"); return; }
		 */
		MensajeUtil.mostrarMensajeDeAdvertencia(req, resp, "ADVERTENCIA", "¿Está seguro de eliminar la receta?",
				RUTA_GESTIONAR_RECETAS_CONTROLLER + "volver",
				RUTA_GESTIONAR_RECETAS_CONTROLLER + "confirmarEliminacion&idReceta=" + idReceta);
	}

	private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		try {
			Long idReceta = Long.parseLong(req.getParameter("idReceta"));
			boolean resultado = FactoryDAO.getFactory().getRecetaDAO().eliminarReceta(idReceta);
			if(resultado) {
				MensajeUtil.mostrarExito(req, resp, "ÉXITO", "La receta se ha eliminado exitosamente", RUTA_GESTIONAR_RECETAS_CONTROLLER+"volver");
				return;
			}
			MensajeUtil.mostrarError(req, resp, "ERROR", "La receta no fue eliminada", RUTA_GESTIONAR_RECETAS_CONTROLLER+"volver");
		}catch(Exception e){
			MensajeUtil.mostrarError(req, resp, "ERROR", "Error: " + e.getMessage(), RUTA_GESTIONAR_RECETAS_CONTROLLER+"volver");
		}finally{
			FactoryDAO.getFactory().getRecetaDAO().cerrar();
		}	
	}

	/*
	 * ---------------------------------------------- VOLVER
	 * -------------------------------------------
	 */
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Intentar obtener ruta específica si existe
		String rutaVolver = req.getParameter("rutaVolver");

		if (rutaVolver != null && !rutaVolver.trim().isBlank()) {
			// Redirigir a la ruta específica
			resp.sendRedirect(req.getContextPath() + rutaVolver);
			return;
		}

		// 2. Si no hay ruta específica, listar las recetas del usuario
		resp.sendRedirect(req.getContextPath() + "/GestionarRecetasController?ruta=listarRecetas");
	}

}