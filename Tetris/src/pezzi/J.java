package pezzi;

import java.awt.Color;

public class J extends Pezzo{
    
    public static final Color colore = new Color(0, 102, 204);
    
    public J(Punto p, int[][] matrix){
        this(p.x, p.y, matrix);
    }
    
    public J(int x, int y, int[][] matrix){
        super(matrix);
        
        codice = 2;
        
        punti.add(new Punto(x, y));
        punti.add(new Punto(x, y+1));
        punti.add(new Punto(x+1, y+1));
        punti.add(new Punto(x+2, y+1));  
    }    
    
}
