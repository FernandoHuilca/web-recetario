package controlador;

import java.io.IOException;
import java.util.List;

import dao.DetalleIngredienteDAO;
import dao.RecetaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DetalleIngrediente;
import modelo.Receta;
import util.MensajeUtil;

@WebServlet("/GestionarPanelPrincipalController")
public class GestionarPanelPrincipalController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rutar(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rutar(req, resp);
    }

    private void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Obtener el parámetro ruta
        String ruta = req.getParameter("ruta");

        // 2. Si es null cargar las recetas por defecto
        if (ruta == null) {
            cargarRecetas(req, resp);
            return;
        }

        // 3. Switch para manejar acciones futuras (ej. filtrar, buscar, logout)
        switch (ruta) {
        
        	// 1. CARGAR RECETAS
            case "cargarRecetas":
                cargarRecetas(req, resp);
                break;
                
            // 2. SOLICITAR BUSCAR
            case "solicitarBuscarRecetaPorNombre":
    			solicitarBuscarRecetaPorNombre(req, resp);
    			break;
    			
    		// 3. VER RECETA
    		case "verReceta":
    			verReceta(req, resp);
    			break;
    			
    		// 4. VOLVER A LA MISMA PÁGINA
    		case "volver":
    			volver(req, resp);
    			break;
    		
    		// 5. LOG OUT
            case "salir":
                salir(req, resp);
                break;
                
            default:
                // Por defecto, siempre mostramos el panel con las recetas
                cargarRecetas(req, resp);
                break;
        }
    }

    /*
     * ------------------------------------------- CARGAR RECETA -----------------------------------------------------
     */
    protected void cargarRecetas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        RecetaDAO dao = new RecetaDAO();

        try {
            // Obtener TODAS las recetas de la BD
            List<Receta> listaRecetas = dao.obtenerRecetas();

            // Guardarlas en el request
            request.setAttribute("listaRecetasBD", listaRecetas);

            // Ir al Panel Principal
            request.getRequestDispatcher("/vista/PanelPrincipal.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, podrías redirigir a una página de error o recargar vacio
        } finally {
            dao.cerrar();
        }
    }
    
    
    /*
     * ------------------------------------------- SOLICITAR BUSCAR -----------------------------------------------------
     */
    public void solicitarBuscarRecetaPorNombre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		String nombre = req.getParameter("criterioBusqueda");
		// 2. Hablar con el modelo
		RecetaDAO recetaDAO = new RecetaDAO();
		List<Receta> recetas = recetaDAO.obtenerRecetasPorNombre(nombre);
		// 3. Llamar a la vista
		if (recetas == null || recetas.isEmpty()) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "Receta no encontrada.", "/GestionarPanelPrincipalController?ruta=volver");
		}else {
			req.setAttribute("listaRecetasBD", recetas);
			req.setAttribute("showListarRecetas", true);
			req.setAttribute("criterioBusqueda", nombre);
			req.getRequestDispatcher("/vista/PanelPrincipal.jsp").forward(req, resp);
		}
	}
	
	
    /*
     * ------------------------------------------- VER RECETA -----------------------------------------------------
     */
	private void verReceta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idParam = request.getParameter("idReceta");
        
        if(idParam == null || idParam.isEmpty()) {
            volver(request, response);
            return;
        }

        RecetaDAO dao = new RecetaDAO();
        DetalleIngredienteDAO  detalleIngredienteDAO = new DetalleIngredienteDAO();
        
        try {
            Long idReceta = Long.parseLong(idParam);
            Receta receta = dao.obtenerRecetaPorId(idReceta);
            
            if(receta != null) {
                //Inicialización de datos LAZY (Carga perezosa)
                
                // 1. Cargamos la lista intermedia (DetalleIngrediente)
                List<DetalleIngrediente> detallesIngredientes = detalleIngredienteDAO.obtenerPorReceta(idReceta); 
                
                // 2. Cargamos el Ingrediente real dentro de cada elemento
                // Si no hacemos esto, al pedir el nombre en el JSP dará error
                for(DetalleIngrediente ri : detallesIngredientes) {
                    ri.getIngrediente().getNombre(); // "Tocamos" el atributo para forzar la carga
                }
                
                request.setAttribute("receta", receta);
                request.setAttribute("detalles", detallesIngredientes);
                request.getRequestDispatcher("/vista/VisualizacionReceta.jsp").forward(request, response);
            } else {
                volver(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            volver(request, response);
        } finally {
            dao.cerrar();
            detalleIngredienteDAO.cerrar();
        }
    }

	
	/*
     * ------------------------------------------- VOLVER -----------------------------------------------------
     */
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		resp.sendRedirect(req.getContextPath() + "/GestionarPanelPrincipalController?ruta=cargarRecetas");
	}
	
	/*
     * ------------------------------------------- LOG OUT -----------------------------------------------------
     */
    protected void salir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirige al login o inicio (index.jsp)
        // Usamos sendRedirect porque es un cambio de contexto completo (salir)
        //resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
    
    
    
}