package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Receta")
public class Receta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_receta")
	private Long idReceta;
	
	@Column(nullable = false, length = 200)
	private String nombre;
	
    @Column(columnDefinition = "TEXT", nullable = false)
	private String descripcion;
	
    @Column(name = "tiempo_preparacion", nullable = false)
	private Double tiempoPreparacion;
	
    @Column(name = "descripcion_pasos", columnDefinition = "TEXT", nullable = false)
	private String descripcionPasos;
	
    @Column(nullable = false)
	private Integer porciones;
    
    @Column(name="imagen")
	private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
    
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaIngrediente> recetaIngredientes = new ArrayList<>();
	
    // Constructores
	public Receta() {}

	public Receta(String nombre, String descripcion, double tiempoPreparacion, String descripcionPasos,
			int porciones, String imagen, Usuario Usuario) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tiempoPreparacion = tiempoPreparacion;
		this.descripcionPasos = descripcionPasos;
		this.porciones = porciones;
		this.imagen = imagen;
		this.usuario = Usuario;
	}

	// Getters y Setters
	public Long getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(Long idReceta) {
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

	public Usuario getUsuario() {
		return usuario;
	}
	
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<RecetaIngrediente> getRecetaIngredientes() {
		return recetaIngredientes;
	}

	public void setRecetaIngredientes(List<RecetaIngrediente> recetaIngredientes) {
		this.recetaIngredientes = recetaIngredientes;
	}

	public void setTiempoPreparacion(Double tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public void setPorciones(Integer porciones) {
		this.porciones = porciones;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// Método de ayuda para registrar ingredientes
    public void agregarIngrediente(Ingrediente ingrediente, Double cantidad, Unidad unidad) {
        RecetaIngrediente ri = new RecetaIngrediente(this, ingrediente, cantidad, unidad);
        recetaIngredientes.add(ri);
    }

	@Override
	public String toString() {
		return "Receta [idReceta=" + idReceta + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", tiempoPreparacion=" + tiempoPreparacion + ", descripcionPasos=" + descripcionPasos + ", porciones="
				+ porciones + ", imagen=" + imagen + ", idUsuario=" + usuario 
				+ "]";
	}
}
