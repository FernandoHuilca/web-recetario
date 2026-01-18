package util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MensajeUtil {
	
	private static final String RUTA_MENSAJE_JSP = "vista/Mensaje.jsp";
	private static final String IMG_SUCCESS = "/assets/images/message/success.png";
	private static final String IMG_ERROR = "/assets/images/message/error.png";
	private static final String IMG_WARNING = "/assets/images/message/warning.png";
	//private static final String IMG_INFO = "/assets/images/message/info.png";
	
	public static void mostrarExito(HttpServletRequest req, HttpServletResponse resp, 
			String titulo, String descripcion, String href) throws ServletException, IOException {
		mostrarMensaje(req, resp, IMG_SUCCESS, titulo, descripcion, href);
	}
	
	public static void mostrarError(HttpServletRequest req, HttpServletResponse resp, 
			String titulo, String descripcion, String href) throws ServletException, IOException {
		mostrarMensaje(req, resp, IMG_ERROR, titulo, descripcion, href);
	}
	
	
	/**
	 * Muestra un mensaje genérico con imagen personalizada
	 */
	public static void mostrarMensaje(HttpServletRequest req, HttpServletResponse resp, 
			String urlImagen, String titulo, String descripcion, String href) throws ServletException, IOException {
		req.setAttribute("urlimg", urlImagen);
		req.setAttribute("title", titulo);
		req.setAttribute("description", descripcion);
		req.setAttribute("href", href);
		req.getRequestDispatcher(RUTA_MENSAJE_JSP).forward(req, resp);
	}
	
	/**
	 * Muestra un mensaje de confirmación con dos botones (Volver y Confirmar)
	 */
	public static void mostrarMensajeDeAdvertencia(HttpServletRequest req, HttpServletResponse resp, 
			String titulo, String descripcion, String hrefVolver, String hrefConfirmar) throws ServletException, IOException {
		req.setAttribute("urlimg", IMG_WARNING);
		req.setAttribute("title", titulo);
		req.setAttribute("description", descripcion);
		req.setAttribute("hrefVolver", hrefVolver);
		req.setAttribute("hrefConfirmar", hrefConfirmar);
		req.getRequestDispatcher(RUTA_MENSAJE_JSP).forward(req, resp);
	}

}
