package chess;

import static chess.ChessBoard.pieces;
import java.awt.Image;

/**
 *
 * @author bigno
 */
public class Bishop extends Piece{
    
    public Bishop(int xp, int yp, boolean isWhite, Image img){
        super(xp, yp, isWhite, img);
    }
    
    public boolean isLegit(int oldXp, int oldYp, int xp, int yp){
        
        if(xp == this.xp && yp == this.yp)
            return false;
        
        //out of the chessboard
        if(xp < 0 || xp > 7 || yp < 0 || yp > 7)
            return false;
        
        //check if it moves diagonally
        if(Math.abs(oldXp-xp) != Math.abs(oldYp-yp))
            return false;
        
        int fromX = oldXp, toX = xp;
        int fromY = oldYp, toY = yp;
        
        for(; fromX != toX && fromY != toY;){
            
            //first iteration is on my x,y
            if(pieces[fromY][fromX] != this){
                
                //if there is some piece between me and the square i want to go
                if(pieces[fromY][fromX] != null)
                    return false;
            }
                
            //increment
            if(fromX < toX)
                fromX++;
            else
                fromX--;
            
            if(fromY < toY)
                fromY++;
            else
                fromY--;
        }
        
        if(pieces[yp][xp] != null && pieces[yp][xp].isWhite == isWhite)
            return false;
            
        return true;
    }
            
    public void move(int xp, int yp) {
        
        int oldXp = this.xp;
        int oldYp = this.yp; 
        
        
        System.out.println("oldXp: " + oldXp +" oldYp: " + oldYp);
        System.out.println("xp: " + xp + " yp: " + yp);
        
        if(isLegit(oldXp, oldYp, xp, yp)){
            
            //if there is something I eat it
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
            
            ChessBoard.send(""+oldXp+oldYp+xp+yp);

        }else{
            
            x = oldXp*64;
            y = oldYp*64;
        }    
                
                
        //è sotto scacco
        //TODO
        
        
    }
       
    
}
