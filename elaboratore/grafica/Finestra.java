package grafica;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import valutatore.TokenString;

public class Finestra extends JFrame implements ActionListener, MouseWheelListener{
    
    private Grafico grafico;
    private JTextField label;
    
    public Finestra(){
        super();
        
        grafico = new Grafico(this);
        label = new JTextField();
        
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){}
        
        JPanel main = new JPanel(new BorderLayout());
        
        main.add(grafico, BorderLayout.CENTER);
        
        label.setPreferredSize(new Dimension(WIDTH, 50));
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        main.add(label, BorderLayout.SOUTH);
        
        add(main);
        label.addActionListener(this);
        
        addMouseWheelListener(this);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("azione");
        grafico.aggiornaFunzione(label.getText());
    }
    
    public int getGraphicWidth(){
        
        return grafico.getWidth();
    }
    
    public int getGraphicHeight(){
        
        return grafico.getHeight();
    }
    
    public void mouseWheelMoved(MouseWheelEvent e){
    	
    	grafico.aggiornaZoom(e.getWheelRotation());
    	
    }
    
}
