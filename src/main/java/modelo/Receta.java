package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Receta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int idReceta;
	private String nombre;
	private String descripcion;
	private double tiempoPreparacion;
	private String descripcionPasos;
	private String porciones;
	private List<Ingrediente> ingredientes;
	
	public Receta() {}
	
	public Receta(int idReceta, String nombre, String descripcion, double tiempoPreparacion, String descripcionPasos,
			String porciones, List<Ingrediente> ingredientes) {
		this.idReceta = idReceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tiempoPreparacion = tiempoPreparacion;
		this.descripcionPasos = descripcionPasos;
		this.porciones = porciones;
		this.ingredientes = ingredientes;
	}
	
	public int getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(int idReceta) {
		this.idReceta = idReceta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(double tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public String getDescripcionPasos() {
		return descripcionPasos;
	}

	public void setDescripcionPasos(String descripcionPasos) {
		this.descripcionPasos = descripcionPasos;
	}

	public String getPorciones() {
		return porciones;
	}

	public void setPorciones(String porciones) {
		this.porciones = porciones;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	/************************* Métodos de negocio *************************/
	
	public static List<Receta> obtenerRecetas(){
		return null;
	}
	
	public List<Receta> obtenerRecetas(int idUsuario){
		return null;
	}
	
	public void guardarReceta(String nombre, String descripcion, double tiempo, int porciones, List<Ingrediente> ingredientes, String pasos, String imagen, int idUsuario) {
		
	}
	
	public boolean actualizarReceta(String nombre, String descripcion, double tiempo, int porciones, List<Ingrediente> ingredientes, String pasos, String imagen,int idReceta) {
		return false;
	}
	
	public Receta obtenerReceta(int idReceta) {
		return null;
	}

	public boolean eliminarReceta(int idReceta) {
		return false;
	}
}
