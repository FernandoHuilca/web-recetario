package dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Receta;
import util.JPAUtil;

/**
 * DAO (Data Access Object) para la entidad Receta
 * Maneja todas las operaciones de acceso a datos de recetas usando JPA
 */
public class RecetaDAO {

	private EntityManager em;

	/**
	 * Constructor - inicializa la conexión con JPA
	 */
	public RecetaDAO() {
		this.em = JPAUtil.getEntityManagerFactory().createEntityManager();
	}
	
	// ==================== CREAR ====================

	/**
	 * Guarda una nueva receta en la base de datos
	 */
	public boolean guardarReceta(Receta receta) {
		try {
			em.getTransaction().begin();
			em.persist(receta);
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

	// ==================== LEER ====================

	/**
	 * Obtiene todas las recetas
	 */
	public List<Receta> obtenerRecetas() {
		try {
			TypedQuery<Receta> query = em.createQuery("SELECT r FROM Receta r", Receta.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene una receta por su ID	
	 */
	public Receta obtenerRecetaPorId(Long idReceta) {
		try {
			return em.find(Receta.class, idReceta);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene todas las recetas de un usuario
	 */
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

	// ==================== ACTUALIZAR ====================

	/**
	 * Actualiza una receta existente
	 */
	public boolean actualizarReceta(Receta receta) {
		try {
			em.getTransaction().begin();
			em.merge(receta);
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

	// ==================== ELIMINAR ====================

	/**
	 * Elimina una receta por su ID
	 */
	public boolean eliminarReceta(Long idReceta) {
		try {
			em.getTransaction().begin();
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

	// ==================== UTILIDADES ====================

	/**
	 * Obtiene el total de recetas
	 */
	public long contarRecetas() {
		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Receta r", Long.class);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// ==================== GESTIÓN DE RECURSOS ====================

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrar() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

}
