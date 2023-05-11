package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
import pezzi.*;

public class Campo extends JPanel implements Runnable{
   
    public static final int larghezza = 10;
    public static final int altezza = 20;
    private int dimGriglia;
    private boolean fine;
    private int[][] matrix;
    private Pezzo attuale;
    private Random rand;
    
    private Color[] colori; 
    
    private Thread gioco;
    
    public Campo(int dimPanel){
        
        setBackground(Color.black);
        dimGriglia = dimPanel/10;
        matrix = new int[altezza][larghezza];
        colori = new Color[]{
                            null,
                            I.colore,
                            J.colore,
                            L.colore,
                            O.colore,
                            S.colore,
                            T.colore,
                            Z.colore };
        
        rand = new Random();
        
        Tastiera tastiera = new Tastiera(this); 
        
        addKeyListener(tastiera); 
        setFocusable(true);
    }
    
    public void inizia(){
        
        fine = false;
        gioco = new Thread(this);
        gioco.start();
    }
    
    public void stop(){
        fine = true;
    }
    
    public void run(){
        
        nuovoPezzo();
        
        while(!fine){  
            
            aggiorna();
        
            repaint();
        }
    }
    
    public void aggiorna() {
        
        try{
            gioco.sleep(400);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
        if(attuale.puoAvanzare()){
            attuale.avanza();
        }else{
            for(Punto p:attuale.punti){
                matrix[p.y][p.x] = attuale.codice;
            }
            //TODO: controllo se devo eliminare delle righe
            nuovoPezzo();
        }
    }
    
    public void nuovoPezzo(){
        
       //TODO: casuale
       
        int scelta = rand.nextInt(7);
        System.out.println("estratto: " + scelta);
       
        switch(scelta){
           
            case 0:
                attuale = new I(3, 0, matrix);
                break;
            case 1:
                attuale = new J(3, 0, matrix);
                break;
            case 2:
                attuale = new L(3, 0, matrix);
                break;
            case 3:
                attuale = new O(3, 0, matrix);
                break;
            case 4:
                attuale = new S(3, 0, matrix);
                break;
            case 5:
                attuale = new T(3, 0, matrix);
                break;
            case 6:
                attuale = new Z(3, 0, matrix);
                break;    
            default:
                System.out.println("errore: numero estratto non valido (Campo.java)");
       }  
    }
    
    public void ruota(){
        //TODO
    }
    
    public void sinistra(){
        attuale.sinistra();
    }
    
    public void destra(){
        attuale.destra();
    }
    
    public void paint(Graphics g){
        super.paint(g);
        
        g.setColor(colori[attuale.codice]);
        for(Punto p:attuale.punti){
            g.fillRect(dimGriglia*p.x, dimGriglia*p.y, dimGriglia, dimGriglia);
        }
        
        
        for(int i=0; i<altezza; i++){
            for(int j=0; j<larghezza; j++){
                
                if(matrix[i][j] != 0){
                    g.setColor(colori[matrix[i][j]]);
                    g.fillRect(dimGriglia*j, dimGriglia*i, dimGriglia, dimGriglia);
                }
            
                g.setColor(Color.gray);
                g.drawRect(dimGriglia*j, dimGriglia*i, dimGriglia, dimGriglia);
            }
        }
        
    }
    
}
