package modelo.jpa;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.Query;
import modelo.dao.GenericDAO;
import util.JPAUtil;

public class JPAGenericDAO<T, ID> implements GenericDAO<T, ID> {
    private Class<T> persistentClass;
	protected EntityManager em;

    public JPAGenericDAO(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
		this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
	}

    @Override
    public boolean create(T t) {
        try {
			em.getTransaction().begin();
			em.persist(t);
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

    @Override
    public boolean update(T t) {
         try {
			em.getTransaction().begin();
			em.merge(t);
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

    @Override
    public boolean delete(ID id) {
		T entity = this.getById(id);
		if (entity != null){
			em.getTransaction().begin();
            try {
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                System.out.println(">>>> ERROR:JPAGenericDAO:delete " + e);
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
            }
        }
        return false;
    }



    @Override
    public T getById(ID id) {
      	try {
		    return em.find(persistentClass, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    // Que baje Dios (Iniguez) y me explique esto:
	@SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
      	// Se crea un criterio de consulta
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.persistentClass);
		// Se establece la clausula FROM
		Root<T> root = criteriaQuery.from(this.persistentClass);
		// Se establece la clausula SELECT
		criteriaQuery.select(root); // criteriaQuery.multiselect(root.get(atr))
		
		Query query = em.createQuery(criteriaQuery);
		return query.getResultList();
    }

    @Override
	public void cerrar() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
