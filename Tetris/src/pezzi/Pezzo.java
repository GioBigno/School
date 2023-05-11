package pezzi;

import java.awt.Color;
import java.util.ArrayList;
import tetris.*;

public class Pezzo{
    
    public int stato;
    public Color colore;
    public ArrayList<Punto> punti;
    public int[][] matrix;
    public int codice;
   
    public Pezzo(int[][] matrix){
        
        stato=0;
        punti = new ArrayList<Punto>();
        this.matrix = matrix;
    }
    
    public void ruota(){
         
    }
    
    public boolean puoAvanzare(){
        
        for(Punto p:punti){
            if(p.y == Campo.altezza-1 || matrix[p.y+1][p.x] != 0){
                //stop
                return false;
            }
        }
        
        return true;
    }
    
    public void avanza(){
        
        for(Punto p:punti){
            p.y++;
        }
    }
    
    public void sinistra(){
        
        for(Punto p:punti){
            if(p.x == 0 || matrix[p.y][p.x-1] != 0){
                //stop
                return;
            }
        }
        
        for(Punto p:punti){
            p.x--;
        }
        
    }
    
    public void destra(){
        
        for(Punto p:punti){
            if(p.x == Campo.larghezza-1 || matrix[p.y][p.x+1] != 0){
                //stop
                return;
            }
        }
        
        for(Punto p:punti){
            p.x++;
        }
        
    }
    
}
