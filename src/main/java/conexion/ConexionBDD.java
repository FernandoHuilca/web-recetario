package conexion;

public class ConexionBDD {
	public ConexionBDD() {
		String servidor = "localhost";
		String usuario = "root";
		String clave = "root";
		String nombreBdd = "recetario";
		
		String url = "jdbc:mysql://" + servidor + "/" + nombreBdd;
	}
}
