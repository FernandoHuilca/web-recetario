package modelo.jpa;

import jakarta.persistence.TypedQuery;
import modelo.dao.IngredienteDAO;
import modelo.entidades.Ingrediente;

public class JPAIngredienteDAO extends JPAGenericDAO<Ingrediente, Long> implements IngredienteDAO {
    
    public JPAIngredienteDAO() {
        super(Ingrediente.class);
    }

    @Override
    public Ingrediente obtenerPorNombre(String nombre) {
     try {
			TypedQuery<Ingrediente> query = em.createQuery(
				"SELECT i FROM Ingrediente i WHERE i.nombre = :nombre", 
				Ingrediente.class
			);
			query.setParameter("nombre", nombre);
			return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}  
    }

    @Override
    public boolean guardarIngrediente(Ingrediente ingrediente) {
      Ingrediente existente = obtenerPorNombre(ingrediente.getNombre());
		if (existente != null) {
			return true;
		}
		
		try {
			em.getTransaction().begin();
			em.persist(ingrediente);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return false;
		}
    }

    
}
