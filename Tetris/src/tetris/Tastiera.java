package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tastiera implements KeyListener{
    
    Campo campo;
    
    public Tastiera(Campo campo){
        
        this.campo = campo; 
        
        System.out.println("costruttore");
    }

    @Override
    public void keyPressed(KeyEvent e) {
         int codice = e.getKeyCode();
        
        System.out.println("codice: " + codice);
        
        switch(codice){
            case KeyEvent.VK_UP:
                campo.ruota();
                break;
            case KeyEvent.VK_DOWN:
                //TODO: accelera
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("sinistra");
                campo.sinistra();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("destra");
                campo.destra();
                break; 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
      
        int codice = e.getKeyCode();
        
        System.out.println("codice: " + codice);
        
        switch(codice){
            case KeyEvent.VK_UP:
                campo.ruota();
                break;
            case KeyEvent.VK_DOWN:
                //TODO: accelera
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("sinistra");
                campo.sinistra();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("destra");
                campo.destra();
                break; 
        }
        
    }
    
    
    
}
