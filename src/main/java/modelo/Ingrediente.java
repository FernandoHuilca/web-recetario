package modelo;

import java.io.Serializable;

public class Ingrediente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private double cantidad;
	private Unidad unidad;
	
	public Ingrediente() {}

	public Ingrediente(String nombre, double cantidad, Unidad unidad) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.unidad = unidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	
	/************************* Métodos de negocio *************************/
	
	public void guardarIngrediente(String nombre, double cantidad, Unidad unidad) {
		
	}
}
