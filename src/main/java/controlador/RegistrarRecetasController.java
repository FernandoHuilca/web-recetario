package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.RecetaDAO;
import dao.IngredienteDAO;
import dao.UsuarioDAO;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;
import modelo.Usuario;

@WebServlet("/RegistrarRecetasController")
@MultipartConfig // Necesario para manejar multipart/form-data (archivos)
public class RegistrarRecetasController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public RegistrarRecetasController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	public void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") != null)? req.getParameter("ruta") : "registrarReceta";
		
		switch(ruta) {
		case "registrarReceta":
			registrarReceta(req, resp);
			break;
		case "guardar":
			guardar(req, resp);
			break;
		case "cancelar":
			cancelar(req, resp);
			break;
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
	
	public void registrarReceta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//2. Hablar con el modelo
		
		// Obtener todas las unidades del enum
		List<Unidad> unidades = Arrays.asList(Unidad.values());
		//3. Llamar a la vista
		req.setAttribute("unidades", unidades);
		req.getRequestDispatcher("vista/FormularioRegistroRecetas.jsp").forward(req, resp);
		
	}

	public void guardar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecetaDAO recetaDAO = new RecetaDAO();
		IngredienteDAO ingredienteDAO = new IngredienteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			// 1. Obtener los parámetros del formulario
			String nombre = req.getParameter("name");
			String descripcion = req.getParameter("description");
			double tiempoPreparacion = Double.parseDouble(req.getParameter("time"));
			String descripcionPasos = req.getParameter("instructions");
			int porciones = Integer.parseInt(req.getParameter("servings"));
			String imagen = null;
			long idUsuario = 1L; // Usuario por defecto mientras no hay sesión
			
			// Captura de arrays de ingredientes
			String[] nombresIngredientes = req.getParameterValues("ingredients_name[]");
			String[] cantidadesIngredientes = req.getParameterValues("ingredients_quantity[]");
			String[] unidadesIngredientes = req.getParameterValues("ingredients_unit[]");
			
			// Validar que existan los ingredientes
			if (nombresIngredientes == null || nombresIngredientes.length == 0) {
				req.setAttribute("error", "Debe agregar al menos un ingrediente");
				registrarReceta(req, resp);
				return;
			}
			
			// 3. Construir la receta usando JPA
			Receta receta = new Receta();
			receta.setNombre(nombre);
			receta.setDescripcion(descripcion);
			receta.setTiempoPreparacion(tiempoPreparacion);
			receta.setPorciones(porciones);
			receta.setDescripcionPasos(descripcionPasos);
			receta.setImagen(imagen);
			
			// Asignar usuario (por defecto id=1). Requiere que exista en la BD.
			Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);
			if (usuario == null) {
				req.setAttribute("title", "ERROR: Usuario por defecto no encontrado");
				req.setAttribute("description", "Cree un usuario con id=1 en la tabla Usuario o ajuste el idUsuario por defecto.");
				req.setAttribute("href", "/RegistrarRecetasController");
				req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
				return;
			}
			receta.setUsuario(usuario);

			// 2. Agregar ingredientes usando el método de la entidad
			for (int i = 0; i < nombresIngredientes.length; i++) {
				String nombreIng = nombresIngredientes[i];
				double cantidad = Double.parseDouble(cantidadesIngredientes[i]);
				Unidad unidad = Unidad.valueOf(unidadesIngredientes[i]);
				
				// Buscar o crear ingrediente en BD para evitar cascade PERSIST issues
				Ingrediente ingrediente = ingredienteDAO.guardarIngrediente(new Ingrediente(nombreIng));
				
				if (ingrediente == null) {
					req.setAttribute("title", "ERROR: Ingrediente no pudo guardarse");
					req.setAttribute("description", "Hubo un problema al procesar el ingrediente: " + nombreIng);
					req.setAttribute("href", "/RegistrarRecetasController");
					req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
					return;
				}
				
				receta.agregarIngrediente(ingrediente, cantidad, unidad);
			}
			
			// 4. Guardar usando RecetaDAO con JPA/ORM
			boolean resultado = recetaDAO.guardarReceta(receta);
			
			// 5. Llamar a la vista con el resultado
			if (!resultado) {
				req.setAttribute("title", "ERROR: Receta NO creada");
				req.setAttribute("description", "No fue posible guardar la receta en la base de datos");
				req.setAttribute("href", "/RegistrarRecetasController");
				req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
				return;
			}
			
			req.setAttribute("title", "Éxito: Receta creada");
			req.setAttribute("description", "La receta se ha creado exitosamente");
			req.setAttribute("href", "/GestionarRecetasController");
			req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			
		} catch (NumberFormatException e) {
			req.setAttribute("title", "ERROR: Datos inválidos");
			req.setAttribute("description", "Asegúrese de ingresar números válidos para cantidad y porciones");
			req.setAttribute("href", "/RegistrarRecetasController");
			try {
				req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "ERROR: Excepción del servidor");
			req.setAttribute("description", "Error: " + e.getMessage());
			req.setAttribute("href", "/RegistrarRecetasController");
			try {
				req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			recetaDAO.cerrar();
			ingredienteDAO.cerrar();
			usuarioDAO.cerrar();
		}
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
