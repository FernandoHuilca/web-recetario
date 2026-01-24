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

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = null;

    private JPAUtil() {}

    public static EntityManagerFactory getEntityManagerFactory() {

        if (emf == null || !emf.isOpen()) {

            Map<String, String> props = new HashMap<>();

            // String url = System.getenv("DB_URL");
            // String user = System.getenv("DB_USER");
            // String password = System.getenv("DB_PASSWORD");
                        String url = System.getenv("DB_URL");            String user = System.getenv("DB_USER");            String password = System.getenv("DB_PASSWORD");
            

            if (url != null) {
                props.put("jakarta.persistence.jdbc.url", url);
            }
            if (user != null) {
                props.put("jakarta.persistence.jdbc.user", user);
            }
            if (password != null) {
                props.put("jakarta.persistence.jdbc.password", password);
            }

            emf = Persistence.createEntityManagerFactory("WebRecetario", props);

            System.out.println("✓ EntityManagerFactory creado correctamente");
        }

        return emf;
    }

    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
            System.out.println("✓ EntityManagerFactory cerrado");
        }
    }
}

