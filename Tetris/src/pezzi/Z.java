package pezzi;

import java.awt.Color;

public class Z extends Pezzo{
    
    public static final Color colore = new Color(255, 0, 0);
    
    public Z(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public Z(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 7;
        
        punti.add(new Punto(x, y));
        punti.add(new Punto(x+1, y));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));  
    }    
    
}
