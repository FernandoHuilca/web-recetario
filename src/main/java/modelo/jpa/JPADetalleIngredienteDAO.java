package modelo.jpa;

import java.util.List;

import modelo.dao.DetalleIngredienteDAO;
import modelo.entidades.DetalleIngrediente;

public class JPADetalleIngredienteDAO extends JPAGenericDAO<DetalleIngrediente, Long> implements DetalleIngredienteDAO {

    public JPADetalleIngredienteDAO() {
        super(DetalleIngrediente.class);
    }

    @Override
    public List<DetalleIngrediente> obtenerPorReceta(Long idReceta) {
       return em.createQuery(
	        "SELECT d FROM DetalleIngrediente d WHERE d.receta.idReceta = :idReceta", 
	        DetalleIngrediente.class)
	        .setParameter("idReceta", idReceta)
	        .getResultList();
    }

    @Override
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

}
