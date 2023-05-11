package chess;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 *  
 * @author bignosoft
 * 
 */
public class Chess {

    public static JFrame f;
    
    public static void main(String[] args) throws IOException{

        f = new Menu();
        f.setTitle("Bignosoft chess");
        f.setVisible(true);         
    }
    
    public static void StartGame() throws IOException{
        System.out.println("Avvio il gioco!");
        
        f = new Game();
        f.setTitle("Bignosoft chess");
        f.setVisible(true);
        
        
    }
}
