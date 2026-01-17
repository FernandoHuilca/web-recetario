package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.DetalleIngrediente;
import modelo.Ingrediente;
import modelo.Receta;
import util.JPAUtil;

public class DetalleIngredienteDAO {
	
private EntityManager em;
	
	public DetalleIngredienteDAO() {
		this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
	}
	
	public boolean guardarDetalleIngrediente(DetalleIngrediente detalleIngrediente) {
		try {
			em.getTransaction().begin();
			em.persist(detalleIngrediente);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	
	 public List<DetalleIngrediente> obtenerPorReceta(Long idReceta) {
	        return em.createQuery(
	            "SELECT d FROM DetalleIngrediente d WHERE d.receta.idReceta = :idReceta", 
	            DetalleIngrediente.class)
	            .setParameter("idReceta", idReceta)
	            .getResultList();
	}
	 
	 public boolean eliminarPorReceta(Long idReceta) {
			try {
				em.getTransaction().begin();
				 em.createQuery("DELETE FROM DetalleIngrediente d WHERE d.receta.idReceta = :idReceta")
	                .setParameter("idReceta", idReceta)
	                .executeUpdate();
				em.getTransaction().commit();
				
				return true;
			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				e.printStackTrace();
				return false;
			}
		}
	
	public void cerrar() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

}
