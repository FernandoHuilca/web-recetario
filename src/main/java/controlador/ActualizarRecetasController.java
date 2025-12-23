package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ActualizarRecetasController")
public class ActualizarRecetasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ActualizarRecetasController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public boolean actualizarReceta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return true;
	}
	
	public void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public boolean actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return true;
	}
	
	public void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
