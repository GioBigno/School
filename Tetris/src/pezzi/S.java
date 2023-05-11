package pezzi;

import java.awt.Color;

public class S extends Pezzo{
    
    public static final Color colore = new Color(0, 255, 0);
    
    public S(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public S(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 5;
        
        punti.add(new Punto(x, y+1));
        punti.add(new Punto(x+1, y));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y));  
    }    
    
}
