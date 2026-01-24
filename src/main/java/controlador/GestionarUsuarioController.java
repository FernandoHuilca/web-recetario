package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.FactoryDAO;
import modelo.entidades.Usuario;
import util.MensajeUtil;

@WebServlet("/GestionarUsuarioController")
public class GestionarUsuarioController extends HttpServlet{

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
		String ruta = (req.getParameter("ruta") != null)  ? req.getParameter("ruta") : null;
		
		switch(ruta) {
		case "solicitarIniciarSesion":
			solicitarIniciarSesion(req, resp);
			break;
		case "confirmarInicioSesion":
			confirmarInicioSesion(req, resp);
			break;
		case "volver":
			volver(req, resp);
		}
	}
	

	public void solicitarIniciarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		resp.sendRedirect("vista/FormularioInicioSesion.jsp");
	}
	
	private void confirmarInicioSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener parámetros
		String correo = req.getParameter("email");
		String clave = req.getParameter("password");
		// 2. Hablar con el modelo
		Usuario usuario = FactoryDAO.getFactory().getUsuarioDAO().autenticar(correo, clave);
		// 3. Llamar a la vista
		if (usuario != null) {
			HttpSession sesionUsuario = req.getSession();
			sesionUsuario.setAttribute("autorizado", usuario.getIdUsuario());
			// le permito pasar listar usuarios
			resp.sendRedirect("GestionarPanelPrincipalController?ruta=cargarRecetas");
		} else {
			MensajeUtil.mostrarError(req, resp, "Error", "Correo electrónico o contraseña inválidos.", "/GestionarUsuarioController?ruta=solicitarIniciarSesion");
		}
	}
	
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}

	
}
