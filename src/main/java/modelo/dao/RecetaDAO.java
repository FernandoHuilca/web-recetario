package modelo.dao;

import java.util.List;

import modelo.entidades.Receta;

public interface RecetaDAO extends GenericDAO<Receta, Long>{
    //Fernando estuvo aquí 
    //Estos son los métodos que son propios de RecetaDAO y no los comunes que ya me traigo del GenericDAO
    public List<Receta> obtenerRecetasPorUsuario(Long idUsuario); 
    public List<Receta> obtenerRecetasPorNombre(String nombre); 
    
}
