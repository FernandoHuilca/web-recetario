package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf = null;
    
    // Constructor privado para evitar instanciación
    private JPAUtil() {}
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (JPAUtil.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("WebRecetario");
                }
            }
        }
        return emf;
    }
    
    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}