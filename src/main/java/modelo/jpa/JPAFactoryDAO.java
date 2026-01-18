package modelo.jpa;

import modelo.dao.UsuarioDAO;
import modelo.dao.DetalleIngredienteDAO;
import modelo.dao.FactoryDAO;
import modelo.dao.IngredienteDAO;
import modelo.dao.RecetaDAO;

public class JPAFactoryDAO extends FactoryDAO {

    @Override
    public RecetaDAO getRecetaDAO() {
      return new JPARecetaDAO();
    }

    
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new JPAUsuarioDAO();
    }
  

    @Override
    public IngredienteDAO getIngredienteDAO() {
      return new JPAIngredienteDAO();
    }

    @Override
    public DetalleIngredienteDAO getDetalleIngredienteDAO() {
        return new JPADetalleIngredienteDAO();
    }


    
}
