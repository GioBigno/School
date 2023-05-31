package grafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import valutatore.TokenString;

public class Grafico extends JPanel{

    private Finestra parent;
    private TokenString funzione;
    
    private BufferedImage image;
    private Graphics2D g2d;
    
    public Grafico(Finestra parent, TokenString funzione){
        super();
        
        this.parent = parent;
        this.funzione  = new TokenString();
        setBackground(Color.white);
         
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        int width = parent.getGraphicWidth();
        int height = parent.getGraphicHeight();
        
        double zoom = 10;
        
        int[] xs = new int[width];
        int[] ys = new int[width];
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        
        for(int i=0; i<width; i++){
            
            double x = i-(width/2);
            x = x / zoom;
       
            double y = funzione.risolvi(x);
            
            System.out.println("i = "+i+", x = "+x+", y = "+y);
            
            //y = y / zoom;
            y = height - y;
            y = y - height/2;
            y = y / zoom;
            xs[i] = (int)Math.round(i);
            ys[i] = (int)Math.round(y);
        }
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
			
        
        g2d.setColor(Color.BLACK);
        
        g2d.drawPolyline(xs, ys, width);
        
        g2d.setColor(Color.blue);
        g2d.drawLine(0, height/2, width, height/2);
        g2d .drawLine(width/2, 0, width/2, height);
        
        
        g.drawImage(image,0,0, this);
        
   
        //g.drawRect(0, 0, 100, 100);
    }
    
    public void aggiorna(String funzione){
        
        this.funzione.setFunzione(funzione);
        repaint();
    }
    
    
    
}
