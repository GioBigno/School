/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author bigno
 */
public class Pawn extends Piece{

    private boolean firstMove = true;
    
    public Pawn(int xp, int yp, boolean isWhite, Image img){
        super(xp, yp, isWhite, img);
    }
    
    //is legit to go from (oldXp, oldYp) to (xp, yp)
    public boolean isLegit(int oldXp, int oldYp, int xp, int yp){
        
        if(xp == this.xp && yp == this.yp)
            return false;
        
        //fuori dalla scacchiera
        if(xp < 0 || xp > 7 || yp < 0 || yp > 7)
            return false;
        
        //se il pedone è del mio colore la x aumenta altrimenti diminuisce
        if(isWhite == ChessBoard.isWhite){
            //avanti di uno
            if(oldXp == xp && oldYp == yp+1 && pieces[yp][xp] == null)
                return true;

            //avanti di due
            if(oldXp == xp && oldYp == yp+2 && firstMove == true && pieces[yp][xp] == null && pieces[yp+1][xp] == null)
                return true;

            //cattura in diagonale
            if((oldXp == xp+1 || oldXp == xp-1) && oldYp == yp+1 && pieces[yp][xp] != null && pieces[yp][xp].isWhite != isWhite)
                return true;
        }else{
            //avanti di uno
            if(oldXp == xp && oldYp == yp-1 && pieces[yp][xp] == null)
                return true;

            //avanti di due
            if(oldXp == xp && oldYp == yp-2 && firstMove == true && pieces[yp][xp] == null && pieces[yp-1][xp] == null)
                return true;

            //cattura in diagonale
            if((oldXp == xp-1 || oldXp == xp+1) && oldYp == yp-1 && pieces[yp][xp] != null && pieces[yp][xp].isWhite != isWhite)
                return true;
        }
        
        return false;
    }
            
    public void move(int xp, int yp) {
        
        int oldXp = this.xp;
        int oldYp = this.yp; 
        
        
        System.out.println("oldXp: " + oldXp +" oldYp: " + oldYp);
        System.out.println("xp: " + xp + " yp: " + yp);
        
        if(isLegit(oldXp, oldYp, xp, yp)){
            
            //se c'è qualcosa lo mangio
            if(pieces[yp][xp] != null && pieces[yp][xp].isWhite != isWhite)
                pieces[yp][xp].kill();
            
            go(oldXp, oldYp, xp, yp);
            
            if(ChessBoard.isCheck(isWhite)){
                go(xp, yp, oldXp, oldYp);
                c.repaint();
                //mossa non valida quindi posso muovere di nuovo
                Game.myTurn = true;
                return;
            }
            
            firstMove = false;
            
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
