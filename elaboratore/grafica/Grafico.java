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
import java.awt.geom.*;
import java.awt.Point;

public class Grafico extends JPanel{

    private Finestra parent;
    private TokenString funzione;
    
    private BufferedImage image;
    private Graphics2D g2d;
    
    int width;
    int height;
    
    double zoom;
    
    AffineTransform screenToGraph;
    AffineTransform graphToScreen;
    
    public Grafico(Finestra parent){
        super();
        
        this.parent = parent;
        this.funzione  = new TokenString();
        zoom = 1;
        setBackground(Color.white);
        
        screenToGraph = new AffineTransform();
        graphToScreen = new AffineTransform();
    }
    
    public void geometryChanged(int width, int heights){
        this.width = width;
        this.height = heights;
        computeMatrix();
    }
    
    void resetMatrix(){
        screenToGraph = new AffineTransform();
        graphToScreen = new AffineTransform();
    }
    
    public void computeMatrix(){
        
        resetMatrix();
        
        graphToScreen.translate(width/2, height/2);
        graphToScreen.scale(zoom, zoom);
        graphToScreen.scale(1, -1);
        
        screenToGraph.scale(1, -1);
        screenToGraph.scale(1/zoom, 1/zoom);
        screenToGraph.translate(-(width/2), -(height/2));
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        width = parent.getGraphicWidth();
        height = parent.getGraphicHeight();
        
        Point2D[] pointScreen = new Point2D.Double[width];
        Point2D[] pointGraph = new Point2D.Double[width];
        
        for(int i=0; i<width; i++){
            pointScreen[i] = new Point2D.Double();
            pointGraph[i] = new Point2D.Double();
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        
        for(int i=0; i<width; i++){
            pointScreen[i].setLocation(i, 0);
        }
        
        screenToGraph.transform(pointScreen, 0, pointGraph, 0, width);
        
        for(int i=0; i<width; i++){
            pointGraph[i].setLocation(pointGraph[i].getX(), funzione.risolvi(pointGraph[i].getX())); 
        }
        
        graphToScreen.transform(pointGraph, 0, pointScreen, 0, width);
        
        int[] xInt = new int[width];
        int[] yInt = new int[width];
     
        for(int i=0; i<width; i++){
            xInt[i] = i;
            yInt[i] = (int)pointScreen[i].getY();
        }
        
        g2d.setColor(Color.BLACK);
        g2d.drawPolyline(xInt, yInt, width);
  
        //asse x
        g2d.setColor(Color.red);
        g2d.drawLine(0, height/2, width, height/2);
        //asse y
        g2d.drawLine(width/2, 0, width/2, height);

        g.drawImage(image,0,0, this);
    }
    
    public void aggiornaFunzione(String funzione){
        
        this.funzione.setFunzione(funzione);
        
        zoom = 1;
        
        resetMatrix();
        computeMatrix();
        repaint();
    }
    
    public void aggiornaZoom(int diff){
                
        if(diff < 0){
            zoom *= 1.5;
        }else{
            zoom /= 1.5;
        }
        
        computeMatrix();
        repaint();
    }
    
}