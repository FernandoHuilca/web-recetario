package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Receta;
import modelo.RecetaIngrediente; // Importar esto
import dao.RecetaDAO;

import java.io.IOException;

@WebServlet("/VerRecetaController")
public class VerRecetaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String RUTA_PANELPRINCIPAL = "/VerPanelPrincipalController?ruta=cargarRecetas";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rutar(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rutar(request, response);
    }
    
    private void rutar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = req.getParameter("ruta");
        
        if (ruta == null || ruta.equals("verReceta")) {
            verReceta(req, resp);
        } else {
            volver(req, resp);
        }
    }
    
    private void verReceta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idParam = request.getParameter("idReceta");
        
        if(idParam == null || idParam.isEmpty()) {
            volver(request, response);
            return;
        }

        RecetaDAO dao = new RecetaDAO();
        try {
            Long idReceta = Long.parseLong(idParam);
            Receta receta = dao.obtenerRecetaPorId(idReceta);
            
            if(receta != null) {
                //Inicialización de datos LAZY (Carga perezosa)
                
                // 1. Cargamos la lista intermedia (RecetaIngrediente)
                receta.getRecetaIngredientes().size(); 
                
                // 2. Cargamos el Ingrediente real dentro de cada elemento
                // Si no hacemos esto, al pedir el nombre en el JSP dará error
                for(RecetaIngrediente ri : receta.getRecetaIngredientes()) {
                    ri.getIngrediente().getNombre(); // "Tocamos" el atributo para forzar la carga
                }
                
                request.setAttribute("receta", receta);
                request.getRequestDispatcher("/vista/VisualizacionReceta.jsp").forward(request, response);
            } else {
                volver(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            volver(request, response);
        } finally {
            dao.cerrar();
        }
    }
    
    private void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + RUTA_PANELPRINCIPAL);
    }
}