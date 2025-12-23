package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminarRecetasController")
public class EliminarRecetasController extends HttpServlet{

	/**
	 * Fernando estuvo aquí :3
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	public boolean eliminarReceta(int idReceta, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		return true; //Pilas aqui le puse porque si no me da error xd 
	}
	
	public void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	public void cancelarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	public void listarRecetas(int idReceta, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	}
	
	
}
