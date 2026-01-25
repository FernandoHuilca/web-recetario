package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		case "solicitarRegistrarUsuario":
			solicitarRegistrarUsuario(req, resp);
			break;
		case "confirmarRegistro":
			confirmarRegistro(req, resp);
			break;
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

	// 1. Solicitar Registrar Usuario
	private void solicitarRegistrarUsuario(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 2. Presentar
		resp.sendRedirect("vista/FormularioRegistroUsuarios.jsp");

	}

	// 3. Confirmar Registro ( nombre, apellido, fechaNacimiento, correo, clave, confirmacionClave )
	private void confirmarRegistro(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. Obtener parámetros
		String nombre = req.getParameter("name");
		String apellido = req.getParameter("lastname");
		String fechaNacimiento = req.getParameter("birthdate");
		String correo = req.getParameter("email");
		String clave = req.getParameter("password");
		String confirmacionClave = req.getParameter("passwordConfirmation");
		
		// 4. Validar información
		if (!validarInformación(nombre, apellido, fechaNacimiento, correo, clave, confirmacionClave, req, resp)) {
			return;
		}

		// Hablar con el modelo
		Usuario usuario = new Usuario(nombre, apellido, LocalDate.parse(fechaNacimiento), correo, clave);

		try {
			// 5. Registrar Usuario ( Usuario )
			FactoryDAO.getFactory().getUsuarioDAO().create(usuario);

			// 6. Mostrar éxito (contenidoMensaje) [Llamar a la vista]
			// 7. Iniciar Sesión
			MensajeUtil.mostrarExito(req, resp, "ÉXITO", "El usuario ha sido registrado", "/GestionarUsuarioController?ruta=solicitarIniciarSesion");

		} catch (Exception e) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "Ha ocurrido un error inesperado al registrar el usuario", "/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
		}

	}

	private boolean validarInformación(String nombre, String apellido, String fechaNacimiento, String correo,
			String clave, String confirmacionClave, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 6.1. Campos Vacíos
		if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || fechaNacimiento == null
				|| correo == null || correo.isEmpty() || clave == null || clave.isEmpty() || confirmacionClave == null
				|| confirmacionClave.isEmpty()) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "Faltan campos obligatorios por llenar.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}

		LocalDate fechaNacimientoLocalDate = LocalDate.parse(fechaNacimiento);
		// 6.2. Fecha de nacimiento superior a la actual
		if (fechaNacimientoLocalDate.isAfter(LocalDate.now())) {
			MensajeUtil.mostrarError(req, resp, "ERROR",
					"La fecha de nacimiento no debe ser superior a la fecha actual.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}

		// 6.3. Correo con formato incorrecto
		if (!verificarCorreo(correo)) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "El correo debe tener un formato válido.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}

		// 6.4. Tamaño de clave menor a 8
		if (clave.length() < 8) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "El tamaño de la clave debe ser mayor a 8 caracteres.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}

		// 6.5. Clave y confirmacionClave diferentes
		if (!clave.equals(confirmacionClave)) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "La confirmación de la clave es diferente.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}
		
		// 6.6. Correo ya existente
		if(FactoryDAO.getFactory().getUsuarioDAO().verificarCorreoYaExistente(correo)) {
			MensajeUtil.mostrarError(req, resp, "ERROR", "El correo ingresado ya fue registrado.",
					"/GestionarUsuarioController?ruta=solicitarRegistrarUsuario");
			return false;
		}
		return true;
	}

	public boolean verificarCorreo(String correo) {
		String usuario = "[a-zA-Z0-9_+&*-]+";
		String puntosOpcionales = "(?:\\.[a-zA-Z0-9_+&*-]+)*"; // puntos múltiples, evitando puntos consecutivos
		String dominio = "(?:[a-zA-Z0-9-]+\\.)+"; // sub.dominio.com ó dominio.com
		String extension = "[a-zA-Z]{2,7}"; // [TLD]{entre 2 a 7 letras}

		Pattern pattern = Pattern.compile("^" + usuario + puntosOpcionales + "@" + dominio + extension + "$");
		Matcher matcher = pattern.matcher(correo);

		return matcher.matches();
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
			MensajeUtil.mostrarError(req, resp, "Error", "Correo electrónico o clave inválidos.", "/GestionarUsuarioController?ruta=solicitarIniciarSesion");
		}
	}
	
	public void volver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Obtener los parámetros
		// 2. Hablar con el modelo
		// 3. Llamar a la vista
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}

	
}
