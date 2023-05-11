
package minesweeper;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Field extends JPanel implements MouseListener{
    
    private int dimCampo;
    private int numBombe;
    private int dimCella;
    
    private boolean firstClick = true;
    
    private Image[] imgs;
    private Image[] imgsOriginal;
    
    private Game game;
    private Finestra finestra;
    
    public static int PIASTRELLA = 9;
    public static int BANDIERA = 10;
    public static int BOMBA = 11;
    public static int BOMBAERRORE = 12;
    
    public static final int COPERTA = 0;
    public static final int SCOPERTA = 1;
    public static final int SOSPETTA = 2;
    
    public Field(int dimCampo, int numBombe, Finestra finestra){
        
        this.finestra = finestra;
        finestra.ridimensiona(700, 740);

        this.dimCampo = dimCampo;
        this.numBombe= numBombe;
        this.dimCella = 700/dimCampo;
           
        
        try {
            
            setImage();
            
        } catch (IOException ex) {
            System.err.println("errore file non trovato");
        }
        
        game = new Game(dimCampo, numBombe);
        
        addMouseListener(this); 
        
        repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        dimCella = Integer.min(finestra.getWidth(), (finestra.getHeight()-40))/dimCampo;
        
        for(int i=0; i<imgsOriginal.length; i++){
            imgs[i] = imgsOriginal[i].getScaledInstance(dimCella, dimCella, Image.SCALE_DEFAULT);
        }
        
        for(int i=0; i<dimCampo; i++){
            
            for(int j=0; j<dimCampo; j++){
                
                switch(game.stato[i][j]){
                    
                    case COPERTA:
                        g.drawImage(imgs[PIASTRELLA], i*dimCella, j*dimCella, this);
                        break;
                    
                    case SCOPERTA:
                        int indexImage = game.matrix[i][j];
                        g.drawImage(imgs[indexImage], i*dimCella, j*dimCella, this);
                        break;
                        
                    case SOSPETTA:
                        g.drawImage(imgs[BANDIERA], i*dimCella, j*dimCella, this);
                        break;
                }
                
            }
        }
        
    }
    
    
    
    private void setImage() throws IOException{
        
        imgsOriginal = new Image[13];
        imgs = new Image[13];
        
        BufferedImage all = ImageIO.read(new File("images/assets.png"));
        
        /*
        le prime 9 immagini sono i numerti che indicano quante bombe ci sono nelle caselle adiacenti alla mia
        */
        
        int dimImg = all.getHeight()/42; //dimensioni del file / numero di immagini
        System.out.println("d: "+dimCella);
        
        for(int i=0, y=all.getHeight()-dimImg; i<9; i++, y-=dimImg){
            
            imgsOriginal[i] = all.getSubimage(0, y, dimImg, dimImg).getScaledInstance(dimCella, dimCella, BufferedImage.SCALE_SMOOTH);          
        }
        
        //piastrella
        imgsOriginal[9] = all.getSubimage(0, 0, dimImg, dimImg).getScaledInstance(dimCella, dimCella, BufferedImage.SCALE_SMOOTH);
        
        //bandiera
        imgsOriginal[10] = all.getSubimage(dimImg*8, all.getHeight()-(dimImg*3), dimImg, dimImg).getScaledInstance(dimCella, dimCella, BufferedImage.SCALE_SMOOTH);
        
        //bomba
        imgsOriginal[11] = all.getSubimage(dimImg*8, all.getHeight()-dimImg, dimImg, dimImg).getScaledInstance(dimCella, dimCella, BufferedImage.SCALE_SMOOTH);
        
        //bomba sbagliata
        imgsOriginal[12] = all.getSubimage(dimImg*8, all.getHeight()-(dimImg*2), dimImg, dimImg).getScaledInstance(dimCella, dimCella, BufferedImage.SCALE_SMOOTH);
    }
    
    private void gameOver(){
        finestra.gameOver();
    }
    
    private void win(){
        finestra.win();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    
        int x = me.getX() / dimCella;
        int y = me.getY() / dimCella;
    
        
        
        if(me.getButton() == MouseEvent.BUTTON1){
            //click sinistro
            
            if(firstClick == true){
                firstClick = false;
                game.click(x, y, 0, true);
            }else{
                //System.out.println("click");
                int esito = game.click(x, y, 0, false);
                
                if(esito == -1){
                    gameOver();
                }
                
                if(esito == 1){
                    win();
                }
                
            }
        
        }else if(me.getButton() == MouseEvent.BUTTON3){
            //click destro
            
            game.click(x, y, 1, true);
        }
        
        repaint();
        
    }
    
    public void restart(){
        game = new Game(dimCampo, numBombe);
        firstClick = true;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}
    
}
