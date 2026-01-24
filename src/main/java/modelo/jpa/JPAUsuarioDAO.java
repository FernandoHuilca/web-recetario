package modelo.jpa;

import jakarta.persistence.Query;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

public class JPAUsuarioDAO extends JPAGenericDAO<Usuario, Long> implements UsuarioDAO {

    public JPAUsuarioDAO() {
        super(Usuario.class);
    }

	@Override
	public Usuario autenticar(String correo, String clave) {
		try {
			String sentenciaJPQL = "SELECT u FROM Usuario u WHERE u.correo= :correo AND u.clave= :clave";
			
			Query consulta = em.createQuery(sentenciaJPQL);
			consulta.setParameter("correo", correo);
			consulta.setParameter("clave", clave);
			
			return (Usuario) consulta.getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	}
    



}
