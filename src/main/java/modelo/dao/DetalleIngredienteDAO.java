package modelo.dao;

import java.util.List;

import modelo.entidades.DetalleIngrediente;

public interface DetalleIngredienteDAO extends GenericDAO<DetalleIngrediente, Long> {
    public List<DetalleIngrediente> obtenerPorReceta(Long idReceta);
}
