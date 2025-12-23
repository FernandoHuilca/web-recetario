package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class VerRecetaController
 */
@WebServlet("/VerRecetaController")
public class VerRecetaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public VerRecetaController() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verReceta(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verReceta(request, response);
	}
	
	private void verReceta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO: 1 obtener los parametros
		// TODO: 2 hablar con el modelo
		// TODO: 3 llamar a la vista
	}
	
	private void volver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO: 1 obtener los parametros
		// TODO: 2 hablar con el modelo
		// TODO: 3 llamar a la vista	
	}

}
