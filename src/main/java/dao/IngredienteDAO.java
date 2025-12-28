package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.Ingrediente;
import util.JPAUtil;

public class IngredienteDAO {
	
	private EntityManager em;
	
	public IngredienteDAO() {
		this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
	}
	
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
	
	public Ingrediente guardarIngrediente(Ingrediente ingrediente) {
		
		Ingrediente existente = obtenerPorNombre(ingrediente.getNombre());
		if (existente != null) {
			return existente; // Retorna el que ya existe
		}
		
		try {
			em.getTransaction().begin();
			em.persist(ingrediente);
			em.getTransaction().commit();
			return ingrediente;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			return null;
		}

		
		/*try {
			// Buscar si ya existe
			Ingrediente existente = obtenerPorNombre(ingrediente.getNombre());
			if (existente != null) {
				return existente; // Retorna el que ya existe
			}
			
			// Si no existe, crear nuevo
			em.getTransaction().begin();
			em.persist(ingrediente);
			em.getTransaction().commit();
			return ingrediente;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}*/
	}
	
	public void cerrar() {
		if (em != null && em.isOpen()) {
			em.close();
		}

	}
}
