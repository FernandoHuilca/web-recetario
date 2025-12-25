package modelo;

public enum Unidad {
    GRAMOS("g"),
    KILOGRAMOS("kg"),
    MILILITROS("ml"),
    LITROS("l"),
    UNIDAD("unidad"),
    CUCHARADA("cda"),
    CUCHARADITA("cdita"),
    TAZA("taza");
    
    private String simbolo;
    
    Unidad(String simbolo) {
        this.simbolo = simbolo;
    }
    
    public String getSimbolo() {
        return simbolo;
    }
}