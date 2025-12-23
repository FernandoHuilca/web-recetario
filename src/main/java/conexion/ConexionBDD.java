package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBDD {
	
	private static Connection cnn = null;
	
	private ConexionBDD() {
		String servidor = "localhost";
		String usuario = "root";
		String clave = "root";
		String nombreBdd = "recetario";
		
		String url = "jdbc:mysql://" + servidor + "/" + nombreBdd;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			cnn = DriverManager.getConnection(url, usuario, clave);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getInstance() {
		if (cnn == null)
			new ConexionBDD();
		return cnn;
	}
	
	public static void cerrar() {
		if (cnn != null) {
			try {
				cnn.close();
				cnn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void cerrar(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs = null;
	}
	
	public static void cerrar(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
