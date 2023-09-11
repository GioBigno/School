package grafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import valutatore.TokenString;
import java.awt.geom.*;

public class Grafico extends JPanel{

    final private Finestra parent;
    final private TokenString funzione;
    
    private BufferedImage image;
    private Graphics2D g2d;
    
    static final int NUM_BARRE = 20;
    
    int width;
    int height;
    
    double zoom;
    
    Point2D mouseDragPoint;
    
    Point2D GraphCenter = new Point2D.Double(0, 0);
    Point2D screenCenter = new Point2D.Double(0, 0);
    
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
        
        screenCenter = new Point(width/2, heights/2);
        
        computeMatrix();
    }
    
    void resetMatrix(){
        screenToGraph = new AffineTransform();
        graphToScreen = new AffineTransform();
    }
    
    public void computeMatrix(){
        
        resetMatrix();
        
        graphToScreen.scale(zoom, zoom);
        graphToScreen.scale(1, -1);
        
        Point2D transformedGraphCenter = new Point2D.Double();
        graphToScreen.transform(GraphCenter, transformedGraphCenter);
        
        Point2D delta = new Point2D.Double(screenCenter.getX() - transformedGraphCenter.getX(), screenCenter.getY() - transformedGraphCenter.getY());

        resetMatrix();
        
        graphToScreen.translate(delta.getX(), delta.getY());
        graphToScreen.scale(zoom, zoom);
        graphToScreen.scale(1, -1);
        
        screenToGraph.scale(1, -1);
        screenToGraph.scale(1/zoom, 1/zoom);
        screenToGraph.translate(-delta.getX(), -delta.getY());
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
        g2d.setColor(Color.red);
        g2d.drawPolyline(xInt, yInt, width);
        
        //asse x
        Point2D.Double asseXGraph = new Point2D.Double(0, 0);
        Point2D.Double asseXScreen = new Point2D.Double();
            
        graphToScreen.transform(asseXGraph, asseXScreen);
        
        g2d.setColor(Color.black);
        g2d.drawLine(0, (int)asseXScreen.getY(), width,(int)asseXScreen.getY());
        
        //asse y
        
        Point2D.Double asseYGraph = new Point2D.Double(0, 0);
        Point2D.Double asseYScreen = new Point2D.Double();
            
        graphToScreen.transform(asseYGraph, asseYScreen);
        
        g2d.setColor(Color.black);
        g2d.drawLine((int)asseYScreen.getX(), 0, (int)asseYScreen.getX(), height);
        
        
        ////////////////////////////////////////////////////////////////////////////////////////7
        
        double dim_barra = 0;
        
        double dim_assex = pointGraph[width-1].getX() - pointGraph[0].getX();

        double temp = dim_assex / NUM_BARRE;
       
        int num_cifre = 0;
        if(temp*10>1){
            
            while(temp >= 1){
                num_cifre++;
                temp /= 10;
            }
            
        }else{
            
            while(temp <= 1){
                num_cifre--;
                temp *= 10;
            }
            num_cifre++;
        }
        
        double max = Math.pow(10, num_cifre);
        double min = max/2.0;
        
        dim_barra = max - (dim_assex / NUM_BARRE) < (dim_assex / NUM_BARRE) - min ? max : min;
        
        for(int i=-(NUM_BARRE/2); i<NUM_BARRE; i++){
               
            Point2D.Double xGraph = new Point2D.Double(dim_barra*i, 0);
            Point2D.Double xScreen = new Point2D.Double();
            
            graphToScreen.transform(xGraph, xScreen);
            
            g2d.drawLine((int)xScreen.getX(), (int)xScreen.getY()-3, (int)xScreen.getX(), (int)xScreen.getY()+3);     
            g2d.drawString(""+xGraph.getX(), (int)xScreen.getX(), (int)xScreen.getY()-10);
            
        }
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        
        Point2D.Double yScreenMax = new Point2D.Double(0, height);
        Point2D.Double yGraphMax = new Point2D.Double();
        screenToGraph.transform(yScreenMax, yGraphMax);
        Point2D.Double yScreenMin = new Point2D.Double(0, 0);
        Point2D.Double yGraphMin = new Point2D.Double();
        screenToGraph.transform(yScreenMin, yGraphMin);
        double dim_assey = yGraphMax.getY() - yGraphMin.getY();
        
        temp = dim_assex / NUM_BARRE;
       
        num_cifre = 0;
        if(temp*10>1){
            
            while(temp >= 1){
                num_cifre++;
                temp /= 10;
            }
            
        }else{
            
            while(temp <= 1){
                num_cifre--;
                temp *= 10;
            }
            num_cifre++;
        }
        
        max = Math.pow(10, num_cifre);
        min = max/2.0;
        
        dim_barra = max - (dim_assey / NUM_BARRE) < (dim_assey / NUM_BARRE) - min ? max : min;
        
        for(int i=-(NUM_BARRE/2); i<NUM_BARRE; i++){
               
            if(i == 0)
                continue;
            
            Point2D.Double pGraph = new Point2D.Double(0, dim_barra*i);
            Point2D.Double pScreen = new Point2D.Double();
            
            graphToScreen.transform(pGraph, pScreen);
            
            g2d.drawLine((int)pScreen.getX()-3, (int)pScreen.getY(), (int)pScreen.getX()+3, (int)pScreen.getY());     
            g2d.drawString(""+pGraph.getY(), (int)pScreen.getX()+4, (int)pScreen.getY());
            
        }
        
        /////////////////////////////////////////////////////////////////////////////////////////////////
        

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
        
        Point2D mouseOnScreen = new Point2D.Double(mouseDragPoint.getX(), mouseDragPoint.getY());
        Point2D expectedWorldCenter = new Point2D.Double();
        screenToGraph.transform(mouseOnScreen, expectedWorldCenter);
        
        computeMatrix();
        
        Point2D graphUnderMouse = new Point2D.Double();
        screenToGraph.transform(mouseOnScreen, graphUnderMouse);
        
        Point2D diffPoint = new Point2D.Double(expectedWorldCenter.getX() - graphUnderMouse.getX(), expectedWorldCenter.getY() - graphUnderMouse.getY());
        GraphCenter = new Point2D.Double(GraphCenter.getX() + diffPoint.getX(), GraphCenter.getY() + diffPoint.getY());
        
        computeMatrix();
        repaint();
    }
    

    public void dragged(int x, int y){

        Point2D mouseGraph = new Point2D.Double();
        screenToGraph.transform(mouseDragPoint, mouseGraph);
        
        Point2D currentPointScreen = new Point2D.Double(x, y);
        Point2D currentPointGraph = new Point2D.Double();
        screenToGraph.transform(currentPointScreen, currentPointGraph);
        
        Point2D deltaGraph = new Point2D.Double(currentPointGraph.getX() - mouseGraph.getX(), currentPointGraph.getY() - mouseGraph.getY());

        GraphCenter = new Point2D.Double(GraphCenter.getX()-deltaGraph.getX(), GraphCenter.getY()-deltaGraph.getY());

        mouseDragPoint = new Point2D.Double(x, y);
            
        computeMatrix();
        repaint();
    }
    
    public void move(int x, int y){
        
            mouseDragPoint = new Point2D.Double(x, y);
                  
    }
    
}