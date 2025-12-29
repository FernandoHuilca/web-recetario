package controlador;

import java.io.IOException;

import dao.RecetaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminarRecetasController")
public class EliminarRecetasController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
    private static final String RUTA_VOLVER = "/GestionarRecetasController";
	private static final String RUTAELIMINARCONTROLLER = "/EliminarRecetasController?ruta=";
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		rutar(req, resp);
	}

	private void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = req.getParameter("ruta");
		if(ruta == null) {
			volver(req, resp);
			return;
		}
		
		switch(ruta) {
		case "solicitarEliminarReceta":
			solicitarEliminarReceta(req, resp);
			break;
		case "confirmarEliminacion":
			confirmarEliminacion(req, resp);
			break;
		default:
			volver(req, resp);
			break;
		}
	}
	
	private void solicitarEliminarReceta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String idReceta = req.getParameter("idReceta");
		
		if (idReceta == null || idReceta.trim().isEmpty()) {
			establecerContenidoMensaje(req, resp, "ERROR: ID no válido", "No se proporcionó un ID de receta");
            return;
        }
		establecerContenidoMensajeConfirmacion(req, resp, "ADVERTENCIA: Confirmar eliminación de receta", "¿Está seguro de eliminar la receta?", RUTAELIMINARCONTROLLER+"confirmarEliminacion&idReceta=" + idReceta);
	
	}

	private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		RecetaDAO recetaDAO = new RecetaDAO();
		try {
			Long idReceta = Long.parseLong(req.getParameter("idReceta"));
			boolean resultado = recetaDAO.eliminarReceta(idReceta);	
			
			if(resultado) {
				establecerContenidoMensaje(req, resp, "ÉXITO: Receta Eliminada", "La receta se ha eliminado exitosamente");
				return;
			}
			establecerContenidoMensaje(req, resp, "ERROR: La receta no fue eliminada", "La receta NO se ha eliminado");
		}catch(Exception e){
			establecerContenidoMensaje(req, resp, "ERROR: Excepción","Error: " + e.getMessage());
		}finally{
			recetaDAO.cerrar();
		}	
	}
	
	private void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.sendRedirect(req.getContextPath() + "/GestionarRecetasController");
	}
	
	/*
	 * ---------------- REFACTOR DE MENSAJES ---------------------
	 */
	private void establecerContenidoMensaje(HttpServletRequest req, HttpServletResponse resp, String titulo, 
			String descripcion) throws ServletException, IOException{
		req.setAttribute("title", titulo);
		req.setAttribute("description", descripcion);
		req.setAttribute("href", RUTA_VOLVER);
		req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
	}
	
	private void establecerContenidoMensajeConfirmacion(HttpServletRequest req, HttpServletResponse resp, String titulo,
			String descripcion, String hrefConfirmar)  throws ServletException, IOException{
		req.setAttribute("title", titulo);
		req.setAttribute("description", descripcion);
		req.setAttribute("hrefVolver", RUTA_VOLVER);
		req.setAttribute("hrefConfirmar", hrefConfirmar);
		req.getRequestDispatcher("vista/Mensaje.jsp").forward(req, resp);
	}

}
