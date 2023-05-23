package valutatore;

public class Token {
    
    private TipoToken tipo;
    private double dato;
    
    public Token(TipoToken tipo){
        this(tipo, 0);
    }
    
    public Token(TipoToken tipo, double dato){
        this.tipo = tipo;
        this.dato = dato;
    }
    
}
