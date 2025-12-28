package dao;

import jakarta.persistence.EntityManager;
import modelo.Usuario;
import util.JPAUtil;

public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO() {
        this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
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
    }
}
