package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Receta implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idReceta;
	private String nombre;
	private String descripcion;
	private double tiempoPreparacion;
	private String descripcionPasos;
	private int porciones;
	private List<Ingrediente> ingredientes;
	private String imagen;

	private static List<Receta> recetas = new ArrayList<Receta>(); // TODO: Visual Paradigm

	private int idUsuario;
	
	public Receta() {
	}

	public Receta(int idReceta, String nombre, String descripcion, double tiempoPreparacion, String descripcionPasos,
			int porciones, List<Ingrediente> ingredientes, String imagen, int idUsuario) {
		this.idReceta = idReceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tiempoPreparacion = tiempoPreparacion;
		this.descripcionPasos = descripcionPasos;
		this.porciones = porciones;
		this.ingredientes = ingredientes;
		this.imagen = imagen;
		this.idUsuario = idUsuario;
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

	public int getPorciones() {
		return porciones;
	}

	public void setPorciones(int porciones) {
		this.porciones = porciones;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	/************************* Métodos de negocio *************************/

	// TODO: static en visual paradigm
	public static List<Receta> obtenerRecetas() {
		
		if (recetas.isEmpty()) {
			// receta 1
			List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
			ingredientes.add(new Ingrediente("pollo", 1, Unidad.obtenerUnidad("unidad")));
			ingredientes.add(new Ingrediente("vino", 1, Unidad.obtenerUnidad("l")));

			recetas = new ArrayList<Receta>();
			recetas.add(new Receta(1, "pollo navideño", "Esta es la descripción del pollo navideño", 90.0,
					"1. preparar el pollo, 2. cocinar, 3. decorar", 10, ingredientes, "", 0));

			// receta 2
			List<Ingrediente> ingredientes2 = new ArrayList<Ingrediente>();
			ingredientes2.add(new Ingrediente("papa", 100, Unidad.obtenerUnidad("g")));
			ingredientes2.add(new Ingrediente("rebanadas de jamon", 5, Unidad.obtenerUnidad("unidad")));

			recetas.add(new Receta(2, "ensalada de papa", "Esta es la descripción de la ensalada de papa", 30.0,
					"1. preparar la papa, 2. cocinar, 3. decorar", 3, ingredientes2, "", 0));
		}
		
		return recetas;
	}

	public static List<Receta> obtenerRecetas(int idUsuario) {
		
		if (recetas.isEmpty()) {
			obtenerRecetas();
		}
		
		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		for(Receta receta: recetas) {
			if(receta.getIdUsuario() == idUsuario) {
				recetasEncontradas.add(receta);
			}
		}
		
		return recetasEncontradas;
	}

	public boolean guardarReceta(String nombre, String descripcion, double tiempo, int porciones,
			List<Ingrediente> ingredientes, String pasos, String imagen, int idUsuario) {

		int max = 0;

		for (Receta receta : recetas) {
			if (max < receta.getIdReceta()) {
				max = receta.getIdReceta();
			}
		}

		Receta receta = new Receta(max + 1, nombre, descripcion, tiempo, pasos, porciones, ingredientes, imagen, idUsuario);
		
		recetas.add(receta);
		return true;
	}

	public boolean actualizarReceta(String nombre, String descripcion, double tiempo, int porciones,
			List<Ingrediente> ingredientes, String pasos, String imagen, int idReceta) {
		return false;
	}

	public Receta obtenerReceta(int idReceta) {
		return null;
	}

	public boolean eliminarReceta(int idReceta) {
		return false;
	}
}
