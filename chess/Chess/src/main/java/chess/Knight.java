
package chess;

import java.awt.Image;

/**
 *
 * @author bigno
 */
public class Knight extends Piece{
    
    public Knight(int xp, int yp, boolean isWhite, Image img){
        super(xp, yp, isWhite, img);
    }   
        
    public boolean isLegit(int oldXp, int oldYp, int xp, int yp){
        
        if(xp == this.xp && yp == this.yp)
            return false;
        
        //fuori dalla scacchiera
        if(xp < 0 || xp > 7 || yp < 0 || yp > 7)
            return false;
        
        if(pieces[yp][xp] != null && pieces[yp][xp].isWhite == isWhite)
            return false;
        
        //è una L
        if(Math.abs(oldXp-xp) == 2){
            
            if(Math.abs(oldYp-yp) == 1)
                return true; 
                
        }else if(Math.abs(oldXp-xp) == 1){
            
            if(Math.abs(oldYp-yp) == 2)
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
        
    }
    
    
    
}
