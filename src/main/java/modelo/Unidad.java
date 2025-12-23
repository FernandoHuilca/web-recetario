package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String simbolo;
	
	private static List<Unidad> unidades = new ArrayList<Unidad>(); // TODO: visual paradigm
	
	public Unidad() {
		
	}

	public Unidad(String nombre, String simbolo) {
		super();
		this.nombre = nombre;
		this.simbolo = simbolo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	/************************* Métodos de negocio *************************/
	
	//TODO: static
	public static List<Unidad> obtenerUnidades(){
		if(unidades.isEmpty()) {
			unidades.add(new Unidad("unidad", "unidad"));
			unidades.add(new Unidad("gramos", "g"));
			unidades.add(new Unidad("kilogramos", "kg"));
			unidades.add(new Unidad("mililitros", "ml"));
			unidades.add(new Unidad("litros", "l"));
			
		}
		return unidades;
	}

	// TODO: Visual Paradigm
	public static Unidad obtenerUnidad(String unidad) {
		if(unidades.isEmpty()) {
			obtenerUnidades();
		}
		switch(unidad) {
		case "unidad":
			return unidades.get(0);
		case "g":
			return unidades.get(1);
		case "kg":
			return unidades.get(2);
		case "ml":
			return unidades.get(3);
		case "l":
			return unidades.get(4);
		default:
			return null;
		}
	}

}
