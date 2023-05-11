/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Finestra extends JFrame implements ActionListener {

    private JPanel pannello;
    
    public static Piece selectedPiece = null;
    
    public Finestra() throws IOException{
       
        setBounds(10, 10, 560, 560);
                
        ChessBoard chessBoard = new ChessBoard();
        add(chessBoard);  
        
        MouseListener l = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}

            @Override
            public void mouseEntered(MouseEvent me) {}

            @Override
            public void mouseExited(MouseEvent me) {}

            @Override
            public void mousePressed(MouseEvent me) {
                selectedPiece = chessBoard.getPiece(me.getX(), me.getY());
                System.out.println("fatto");
                
                if(selectedPiece == null)
                    return;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                                
                if(selectedPiece == null)
                    return; 
                
                selectedPiece.move(me.getX()/64, (me.getY()-40)/64);
                
                
                
                repaint();
                
                System.out.println("" + selectedPiece.getClass().getName() + " " + selectedPiece.isWhite);
                
                System.out.println("x: " + selectedPiece.x + " y: " + selectedPiece.y);
                System.out.println("xp: " + selectedPiece.x/64 + " yp: " + selectedPiece.y/64);
                
                selectedPiece = null;
            }   
        };
        
        addMouseListener(l);
        
        MouseMotionListener m = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                
                if(selectedPiece == null)
                    return;
                
                
                selectedPiece.x = e.getX()-32;
                selectedPiece.y =  e.getY()-32 -40;
                repaint();
                
            }

            @Override
            public void mouseMoved(MouseEvent me) {}
        };
        
        addMouseMotionListener(m);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
   
    public void actionPerformed(ActionEvent e) {
        
      
        
    }
    
}