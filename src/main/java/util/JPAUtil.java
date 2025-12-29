package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = null;

	// Constructor privado para evitar instanciación
	private JPAUtil() {
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory("WebRecetario");
			System.out.println("EntityManagerFactory creado");
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