package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Game extends JPanel implements Runnable, KeyListener, MouseInputListener{
    
    private static final double FPS = 30;
    
    private boolean[][] matrix1;
    private boolean[][] matrix2;
    private int rows;
    private int cols;
    private int dimCell;
    
    private boolean stop;
    private int click;
    
    private Thread t;
    
    public Game(int rows, int cols){
        
        this.rows = rows;
        this.cols = cols;
        
        matrix1 = new boolean[rows][cols];
        matrix2 = new boolean[rows][cols];
        
        stop = true;
        click = 0;
    }
    
    public void start(){
        
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setFocusable(true);

        
        t = new Thread(this);
        t.start();
    }
    
    private void setDimCell(){
        
        //no blank spaces
        if(getSize().width > getSize().height){
            dimCell = (int)Math.ceil((double)getSize().width/cols);
        }else{
            dimCell = (int)Math.ceil((double)getSize().height/rows);
        }
        
    }
    
    public void run(){
        
        long lastTime = System.nanoTime();
        double ns = (double)1000000000 / FPS;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0 ;
        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
                repaint();
            
                frames++;

                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println("FPS: " + frames);
                    frames = 0;
                }
            }    
        }
    }
    
    private void update(){
     
        if(stop){
            return;
        }
        
        int sum=0;
        
        for(int i=0; i<matrix1.length; i++){
            for(int j=0; j<matrix1[0].length; j++){
            
                sum=0;
                
                for(int k=i-1; k<=i+1; k++){
                    for(int z=j-1; z<=j+1; z++){
                        
                        if(k>=0 && k<matrix1.length && z>=0 && z<matrix1[0].length){
                            if(matrix1[k][z])
                                sum++;
                        } 
                    }
                }
                
                //i don't have to count myself
                if(matrix1[i][j])
                    sum--;
                
                if(matrix1[i][j]){
                    if(sum != 2 && sum != 3)
                        matrix2[i][j] = false;
                    else
                        matrix2[i][j] = true;
                }else{
                    if(sum == 3)
                        matrix2[i][j] = true;
                    else
                        matrix2[i][j] = false;
                }
            
            } 
        }
        
        for(int i=0; i<matrix1.length; i++){
            for(int j=0; j<matrix1[0].length; j++){
            
                matrix1[i][j] = matrix2[i][j];
            } 
        }
               
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        setDimCell();

        for(int i=0; i<matrix1.length; i++){
            for(int j=0; j<matrix1[0].length; j++){
                
                if(matrix1[i][j] == true){
                    g.setColor(Color.green);
                }else{
                    g.setColor(Color.darkGray);
                }
                
                g.fillRect(j*dimCell, i*dimCell, dimCell, dimCell);
            }
        }
        
        g.setColor(Color.black);
        for(int i=0; i<matrix1.length; i++){
            for(int j=0; j<matrix1[0].length; j++){
                g.drawRect(j*dimCell, i*dimCell, dimCell, dimCell);
            }
        }
    }
    
    private int realX(int x){
        
        return x/dimCell;
        
    }
    
    private int realY(int y){
        return y/dimCell;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            stop = !stop;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
    
        int x = realX(e.getX());
        int y = realY(e.getY());
        
        System.out.println("click");
        
        if(e.getButton() == MouseEvent.BUTTON1){
            matrix1[y][x] = true;
        }else if(e.getButton() == MouseEvent.BUTTON3){
            matrix1[y][x] = false;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        click = e.getButton();
        //1 sinistro, 3 destro
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
        click = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
    
        int x = realX(e.getX());
        int y = realY(e.getY());


        if(y<0 || y>=matrix1.length || x<0 || x>=matrix1[0].length)
            return;
        
        if(click == 1){
            matrix1[y][x] = true;
        }else if(click == 3){
            matrix1[y][x] = false;
        }  
    }

    @Override
    public void mouseMoved(MouseEvent e) {}   
}
