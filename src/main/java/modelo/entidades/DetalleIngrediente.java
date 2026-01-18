package modelo.entidades;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DetalleIngrediente")
public class DetalleIngrediente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_receta_ingrediente")
    private Long idRecetaIngrediente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;
    
    @Column(nullable = false)
    private Double cantidad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unidad unidad;
    
    // Constructores
    public DetalleIngrediente() {}
    
    public DetalleIngrediente(Receta receta, Ingrediente ingrediente, 
                             Double cantidad, Unidad unidad) {
        this.receta = receta;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }
    
    // Getters y Setters
    public Long getId() { return idRecetaIngrediente; }
    public void setId(Long id) { this.idRecetaIngrediente = id; }
    
    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }
    
    public Ingrediente getIngrediente() { return ingrediente; }
    public void setIngrediente(Ingrediente ingrediente) { 
        this.ingrediente = ingrediente; 
    }
    
    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }
    
    public Unidad getUnidad() { return unidad; }
    public void setUnidad(Unidad unidad) { this.unidad = unidad; }
    
}