package modelo.jpa;

import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

public class JPAUsuarioDAO extends JPAGenericDAO<Usuario, Long> implements UsuarioDAO {

    public JPAUsuarioDAO() {
        super(Usuario.class);
    }
    



}
