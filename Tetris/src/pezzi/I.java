package pezzi;

import java.awt.Color;
public class I extends Pezzo{
    
    public static final Color colore = new Color(0, 255, 255);
    
    public I(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public I(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 1;
        
        punti.add(new Punto(x, y+1));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));
        punti.add(new Punto(x+3, y+1));  
    }    
    
}
