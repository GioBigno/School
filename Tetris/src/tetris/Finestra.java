package tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Finestra extends JFrame{
    
    private Campo campo;
    
    public final int larghezza = 400;
    public final int altezza = 800;
    
    public Finestra() {
    
        setBounds(10, 10, larghezza, altezza+40);
       
        campo = new Campo(larghezza);
        add(campo);
        
        campo.inizia();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
