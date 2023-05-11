package pezzi;

import java.awt.Color;

public class O extends Pezzo{
    
    public static final Color colore = new Color(255, 255, 0);
    
    public O(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public O(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 4;
        
        punti.add(new Punto(x+1, y));
        punti.add(new Punto(x+2, y));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));  
    }    
    
}
