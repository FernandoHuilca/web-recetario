package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//Ingrediente (catálogo sin cantidad ni unidad)
@Entity
@Table(name = "Ingrediente")
public class Ingrediente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id_Ingrediente")
	private Long idIngrediente;

    @Column(nullable = false, unique = true, length = 100)
	private String nombre;
    
    @OneToMany(mappedBy = "ingrediente")
    private List<RecetaIngrediente> recetaIngredientes = new ArrayList<>();
	
	public Ingrediente() {}

	public Ingrediente(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
