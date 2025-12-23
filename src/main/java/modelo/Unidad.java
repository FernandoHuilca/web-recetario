package modelo;

import java.io.Serializable;
import java.util.List;

public class Unidad implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String simbolo;
	
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
	
	public List<Unidad> obtenerUnidades(){
		return null;
	}

}
