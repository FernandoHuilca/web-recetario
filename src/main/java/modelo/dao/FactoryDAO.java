package modelo.dao;

import modelo.jpa.JPAFactoryDAO;

public abstract class FactoryDAO {
    protected static FactoryDAO factory = new JPAFactoryDAO();  
    //protected static FactoryDAO factory = new XMLFactoryDAO(); WOW!! :O 


    
    public static FactoryDAO getFactory() {
        return factory;
    }


    public abstract RecetaDAO getRecetaDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract IngredienteDAO getIngredienteDAO();
    public abstract DetalleIngredienteDAO getDetalleIngredienteDAO();
    
}
