package modelo.dao;

import java.util.List;

public interface GenericDAO <T, ID> {
    //Estos son los métodos comunes en nuestras clases DAO concretas. 
    public boolean create (T t);
    public boolean update (T t);
    public boolean delete (ID id);
    public T getById (ID id);
    public List<T> getAll (); 

    public void cerrar();
}
