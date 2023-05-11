package pezzi;

import java.awt.Color;

public class L extends Pezzo{
    
    public static final Color colore = new Color(255, 153, 51);
    
    public L(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public L(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 3;
        
        punti.add(new Punto(x, y+1));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));
        punti.add(new Punto(x+2, y));  
    }    
    
}
