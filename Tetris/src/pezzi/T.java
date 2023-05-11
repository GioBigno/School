package pezzi;

import java.awt.Color;

public class T extends Pezzo{
    
    public static final Color colore = new Color(127, 0, 255);
    
    public T(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public T(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 6;
        
        punti.add(new Punto(x, y+1));
        punti.add(new Punto(x+1, y));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));  
    }    
    
}
