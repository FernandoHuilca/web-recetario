// package util;

// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// public class JPAUtil {
// 	private static EntityManagerFactory emf = null;

// 	// Constructor privado para evitar instanciación
// 	private JPAUtil() {
// 	}

// 	public static EntityManagerFactory getEntityManagerFactory() {
// 		if (emf == null || !emf.isOpen()) {
// 			emf = Persistence.createEntityManagerFactory("WebRecetario");
// 			System.out.println("EntityManagerFactory creado");
// 		}
// 		return emf;
// 	}

// 	public static void cerrar() {
// 		if (emf != null && emf.isOpen()) {
//             try {
//                 emf.close();
//                 emf = null;
//                 System.out.println("✓ EntityManagerFactory cerrado exitosamente");
//             } catch (Exception e) {
//                 System.err.println("✗ Error al cerrar EntityManagerFactory: " + e.getMessage());
//             }
//         }
//     }
// }

package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static EntityManagerFactory emf = null;

    // Constructor privado para evitar instanciación
    private JPAUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                Map<String, String> properties = new HashMap<>();

                // 1. Intentamos leer las variables de entorno (Railway las inyecta automáticamente)
                String dbHost = System.getenv("MYSQLHOST");
                String dbPort = System.getenv("MYSQLPORT");
                String dbName = System.getenv("MYSQLDATABASE");
                String dbUser = System.getenv("MYSQLUSER");
                String dbPass = System.getenv("MYSQLPASSWORD");

                // 2. Si existen (estamos en la Nube), sobrescribimos la configuración
                if (dbHost != null) {
                    System.out.println("--- DETECTADO ENTORNO DE NUBE (RAILWAY) ---");
                    
                    // Construimos la URL de conexión dinámica
                    String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
                    
                    // Sobrescribimos las propiedades de jakarta persistence
                    properties.put("jakarta.persistence.jdbc.url", url);
                    properties.put("jakarta.persistence.jdbc.user", dbUser);
                    properties.put("jakarta.persistence.jdbc.password", dbPass);
                    
                    // Aseguramos que use el driver correcto (opcional pero recomendado)
                    properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                } else {
                    System.out.println("--- DETECTADO ENTORNO LOCAL (LOCALHOST) ---");
                }

                // 3. Creamos la fábrica pasando el mapa de propiedades (si está vacío, usa el persistence.xml normal)
                // "WebRecetario" debe coincidir con el <persistence-unit name="..."> de tu xml
                emf = Persistence.createEntityManagerFactory("WebRecetario", properties);
                
                System.out.println("EntityManagerFactory creado exitosamente");
                
            } catch (Exception e) {
                System.err.println("ERROR FATAL al crear EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return emf;
    }

    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            try {
                emf.close();
                emf = null;
                System.out.println("✓ EntityManagerFactory cerrado exitosamente");
            } catch (Exception e) {
                System.err.println("✗ Error al cerrar EntityManagerFactory: " + e.getMessage());
            }
        }
    }
}