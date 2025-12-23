package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Ingrediente;
import modelo.Receta;
import modelo.Unidad;

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
		List<Unidad> unidades = Unidad.obtenerUnidades();
		//3. Llamar a la vista
		req.setAttribute("unidades", unidades);
		req.getRequestDispatcher("vista/FormularioRegistroRecetas.jsp").forward(req, resp);
	}

	public void guardar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. Obtener los parámetros 
		//int idReceta = Integer.parseInt(req.getParameter("id"));
		String nombre = req.getParameter("name");
		String descripcion = req.getParameter("description");
		double tiempoPreparacion = Double.parseDouble(req.getParameter("time"));
		String descripcionPasos= req.getParameter("instructions");
		int porciones = Integer.parseInt(req.getParameter("servings"));
		String imagen = null;
		
		// Captura de arrays de ingredientes
		String[] nombresIngredientes = req.getParameterValues("ingredients_name[]");
		String[] cantidadesIngredientes = req.getParameterValues("ingredients_quantity[]");
		String[] unidadesIngredientes = req.getParameterValues("ingredients_unit[]");
		
		// validad que existan los ingredientes
		if (nombresIngredientes == null || nombresIngredientes.length == 0) {
            req.setAttribute("error", "Debe agregar al menos un ingrediente");
            registrarReceta(req, resp);
            return;
        }
		
		// 4. Construir lista de ingredientes
        List<Ingrediente> ingredientes = new ArrayList<>();
        for (int i = 0; i < nombresIngredientes.length; i++) {
            String nombreIng = nombresIngredientes[i];
            double cantidad = Double.parseDouble(cantidadesIngredientes[i]);
            Unidad unidad = Unidad.obtenerUnidad(unidadesIngredientes[i]);
            
            ingredientes.add(new Ingrediente(nombreIng, cantidad, unidad));
        }
		//2. Hablar con el modelo
        Receta receta = new Receta();
        boolean resultado = receta.guardarReceta(nombre, descripcion, tiempoPreparacion, porciones, ingredientes, descripcionPasos, imagen, 0);
		
        //3. Llamar a la vista
        if(!resultado) {
        	req.setAttribute("title", "ERROR: Receta NO creada");
            req.setAttribute("description", "mensaje de que NO se pudo crear la receta");
            req.setAttribute("href", "/GestionarRecetasController");
        	req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);

        	return;
        }
        req.setAttribute("title", "Éxito: Receta creada");
        req.setAttribute("description", "mensaje de que SÍ se pudo crear la receta");
        req.setAttribute("href", "/GestionarRecetasController");

    	req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
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
