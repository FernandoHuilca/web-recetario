package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Usuario;

public class UsuarioDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public UsuarioDAO() {
        this.emf = Persistence.createEntityManagerFactory("WebRecetario");
        this.em = emf.createEntityManager();
    }

    public Usuario obtenerPorId(Long idUsuario) {
        try {
            return em.find(Usuario.class, idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cerrar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
