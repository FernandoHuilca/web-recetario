package modelo.entidades;

public enum Unidad {
    GRAMOS("Gramos","g"),
    KILOGRAMOS("Kilogramos","kg"),
    MILILITROS("Mililitros","ml"),
    LITROS("Litros","l"),
    UNIDAD("Unidad","unidad"),
    CUCHARADA("Cucharada","cda"),
    CUCHARADITA("Cucharadita","cdita"),
    TAZA("Taza","taza");
    
    private String simbolo;
    private String nombre;
    
    Unidad(String nombre, String simbolo) {
    	this.nombre = nombre;
        this.simbolo = simbolo;
    }
    
    public String getSimbolo() {
        return simbolo;
    }
    
    public String getNombre() {
    	return nombre;
    }
}