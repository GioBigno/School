package valutatore;

public enum TipoToken {
    
    PARENTESI_APERTA("("),
    PARENTESI_CHIUSA(")"),
    PIU("+"),
    MENO("-"),
    PER("*"),
    DIVISO("/"),
    ELEVATO("^"),
    NUMERO(""),
    VARIABILE(""),
    COSENO("c"),
    SENO("s"),
    ABS("a"),
    SQRT("r");
    
    public final String nome;
    
    private TipoToken(String nome){
        this.nome = nome;
    }
}
