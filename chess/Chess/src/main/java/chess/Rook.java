/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.awt.Image;

/**
 *
 * @author bigno
 */
public class Rook extends Piece{
    
    public Rook(int xp, int yp, boolean isWhite, Image img){
        super(xp, yp, isWhite, img);
    }
    
    public boolean isLegit(int oldXp, int oldYp, int xp, int yp){
        
        if(xp == this.xp && yp == this.yp)
            return false;
        
        //fuori dalla scacchiera
        if(xp < 0 || xp > 7 || yp < 0 || yp > 7)
            return false;
        
        //si muove in linea retta
        if(oldXp != xp && oldYp != yp)
            return false;
        
        //catturo un pezzo del mio colore
        if(pieces[yp][xp] != null && pieces[yp][xp].isWhite == isWhite)
            return false;
        
        if(oldXp != xp){
            
            int fromX = oldXp, toX = xp;
            
            for(; fromX != toX;){
                
                //first iteration is on my x,y
                if(pieces[yp][fromX] != this){
                
                    //if there is some piece between me and the square i want to go
                    if(pieces[yp][fromX] != null)
                        return false;
                    
                }
                
                //increment
                if(fromX < toX)
                    fromX++;
                else
                    fromX--;
            }
            
        }
        
        if(oldYp != yp){
            
            int fromY = oldYp, toY = yp;
            
            for(; fromY != toY;){
                
                //first iteration is on my x,y
                if(pieces[fromY][xp] != this){
                
                    //if there is some piece between me and the square i want to go
                    if(pieces[fromY][xp] != null)
                        return false;
                    
                }
                
                //increment
                if(fromY < toY)
                    fromY++;
                else
                    fromY--;
            }
        }
        
        return true;
    }
            
    public void move(int xp, int yp) {
        
        int oldXp = this.xp;
        int oldYp = this.yp; 
        
        
        System.out.println("oldXp: " + oldXp +" oldYp: " + oldYp);
        System.out.println("xp: " + xp + " yp: " + yp);
        
        if(isLegit(oldXp, oldYp, xp, yp)){
            
            //se c'è qualcosa lo mangio
            if(pieces[yp][xp] != null)
                pieces[yp][xp].kill();
            
            go(oldXp, oldYp, xp, yp);
            
            if(ChessBoard.isCheck(isWhite)){
                go(xp, yp, oldXp, oldYp);
                c.repaint();
                //mossa non valida quindi posso muovere di nuovo
                Game.myTurn = true;
                return;
            }
            
            ChessBoard.send(""+oldXp+oldYp+xp+yp);

        }else{
            
            x = oldXp*64;
            y = oldYp*64;
        }    
                
                
        //è sotto scacco
        //TODO
        
        //promozione 
        //TODO
        
    }
    
    
    
}
