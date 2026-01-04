package controlador;

import java.io.IOException;
import java.util.List;

import dao.RecetaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Receta;

@WebServlet("/VerPanelPrincipalController")
public class VerPanelPrincipalController extends HttpServlet {

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
            case "cargarRecetas":
                cargarRecetas(req, resp);
                break;
            case "salir":
                salir(req, resp);
                break;
            default:
                // Por defecto, siempre mostramos el panel con las recetas
                cargarRecetas(req, resp);
                break;
        }
    }

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

    protected void salir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirige al login o inicio (index.jsp)
        // Usamos sendRedirect porque es un cambio de contexto completo (salir)
        //resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}