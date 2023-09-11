package grafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Finestra extends JFrame implements ActionListener, MouseWheelListener, MouseMotionListener, MouseListener{
    
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
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                
                if(componentEvent.COMPONENT_RESIZED != 0){
                
                    grafico.geometryChanged(grafico.getWidth(), grafico.getHeight());
                }
            }
        });
        
        addMouseMotionListener(this);
        addMouseListener(this);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
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
    
    public void mouseDragged(MouseEvent e){
        grafico.dragged(e.getPoint().x, e.getPoint().y);
    }
    
    public void mouseMoved(MouseEvent e){
         grafico.move(e.getPoint().x, e.getPoint().y);
    }
    
    public void mouseClicked(MouseEvent e){    
    }


    public void mousePressed(MouseEvent e){
    }


    public void mouseReleased(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){       
    }


    public void mouseExited(MouseEvent e){      
    }  
    
}