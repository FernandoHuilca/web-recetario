package controlador;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.FactoryDAO;
import modelo.entidades.DetalleIngrediente;
import modelo.entidades.Receta;
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
    		
    		// 5. CERRAR SESION
    		case "cerrarSesion":
    			cerrarSesion(req, resp);
    			break;
    		
    		// 7. LOG OUT
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


        try {
            // Obtener TODAS las recetas de la BD
            //List<Receta> listaRecetas = JPAFactoryDAO.getFactory().getRecetaDAO().getAll(); <-- Si hago esto ya estoy especificando que quiero JPA y entonces me maté haciendo todo un sabado la arquitectura para que no ocupes? XD
            List<Receta> listaRecetas = FactoryDAO.getFactory().getRecetaDAO().getAll();

            // Guardarlas en el request
            request.setAttribute("listaRecetasBD", listaRecetas);

            // 2. Ir al Panel Principal
            request.getRequestDispatcher("/vista/PanelPrincipal.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, podrías redirigir a una página de error o recargar vacio
        } finally {
            FactoryDAO.getFactory().getRecetaDAO().cerrar();
        }
    }
    
    
    /*
     * ------------------------------------------- SOLICITAR BUSCAR -----------------------------------------------------
     */
    public void solicitarBuscarRecetaPorNombre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		String nombre = req.getParameter("criterioBusqueda");
		// 2. Hablar con el modelo
		List<Receta> recetas = FactoryDAO.getFactory().getRecetaDAO().obtenerRecetasPorNombre(nombre);
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
     * ------------------------------------------- GESTIONAR CUENTA -----------------------------------------------------
     */
    
    // Ya no es necesario: el menú se maneja en el cliente con JavaScript
    /*
    public void solicitarGestionarCuenta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean showMenu = (Boolean) req.getAttribute("showGestionCuenta");
        
        if (showMenu != null && showMenu) {
            req.setAttribute("showGestionCuenta", false);
        } else {
            req.setAttribute("showGestionCuenta", true);
        }
    }
    */
    
    public void cerrarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getSession().invalidate();
    	resp.sendRedirect(req.getContextPath() + "/index.jsp");
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

        //RecetaDAO dao = new RecetaDAO();
        //DetalleIngredienteDAO  detalleIngredienteDAO = new DetalleIngredienteDAO();
        
        try {
            Long idReceta = Long.parseLong(idParam);
            Receta receta = FactoryDAO.getFactory().getRecetaDAO().getById(idReceta);
            
            if(receta != null) {
                //Inicialización de datos LAZY (Carga perezosa)
                
                // 1. Cargamos la lista intermedia (DetalleIngrediente)
                List<DetalleIngrediente> detallesIngredientes = FactoryDAO.getFactory().getDetalleIngredienteDAO().obtenerPorReceta(idReceta); 
                
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
            FactoryDAO.getFactory().getRecetaDAO().cerrar();
            FactoryDAO.getFactory().getDetalleIngredienteDAO().cerrar();
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