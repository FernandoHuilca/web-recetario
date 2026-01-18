package modelo.jpa;

import java.util.List;

import modelo.dao.FactoryDAO;
import jakarta.persistence.TypedQuery;
import modelo.dao.RecetaDAO;
import modelo.entidades.Receta;

public class JPARecetaDAO extends JPAGenericDAO<Receta, Long> implements RecetaDAO {
    public JPARecetaDAO() {
        super(Receta.class);
    }

    @Override
    public List<Receta> obtenerRecetasPorUsuario(Long idUsuario) {
        try {
			TypedQuery<Receta> query = em.createQuery(
					"SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario",
					Receta.class);
			query.setParameter("idUsuario", idUsuario);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public List<Receta> obtenerRecetasPorNombre(String nombre) {
       try {
			// Validar que el nombre no esté vacío
			if (nombre == null || nombre.trim().isEmpty()) {
				return java.util.Collections.emptyList();
			}
			TypedQuery<Receta> query = em.createQuery(
					"SELECT r FROM Receta r WHERE LOWER(r.nombre) LIKE LOWER(:nombre)",
					Receta.class);
			query.setParameter("nombre", "%" + nombre + "%");
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }


    
	// ==================== ELIMINAR ====================

	/**
	 * Elimina una receta por su ID
	 */
	public boolean eliminarReceta(Long idReceta) {
	    //DetalleIngredienteDAO detalleDAO = new DetalleIngredienteDAO();
		try {
			em.getTransaction().begin();
			
	        //detalleDAO.eliminarPorReceta(idReceta);
			FactoryDAO.getFactory().getDetalleIngredienteDAO().eliminarPorReceta(idReceta);
	        Receta receta = em.find(Receta.class, idReceta);
			if (receta != null) {
				em.remove(receta);
				em.getTransaction().commit();
				return true;
			}
			em.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

    
}
