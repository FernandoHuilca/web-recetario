package modelo.dao;

import modelo.entidades.Ingrediente;

public interface IngredienteDAO extends GenericDAO<Ingrediente, Long> {
    public Ingrediente obtenerPorNombre(String nombre);    
    //Gregory que hace esto??
    public boolean guardarIngrediente(Ingrediente ingrediente);    
}
