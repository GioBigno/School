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
public class King extends Piece{
    
    private boolean firstMove = true;
    
    public King(int xp, int yp, boolean isWhite, Image img){
        super(xp, yp, isWhite, img);
    }
    
    public boolean isLegit(int oldXp, int oldYp, int xp, int yp){
        
        if(xp == this.xp && yp == this.yp)
            return false;
        
        //fuori dalla scacchiera
        if(xp < 0 || xp > 7 || yp < 0 || yp > 7)
            return false;
        
        //avanti di uno
        if((oldXp == xp || oldXp == xp+1 || oldXp == xp-1) && (oldYp == yp || oldYp == yp+1 || oldYp == yp-1) && (pieces[yp][xp] == null || pieces[yp][xp].isWhite != isWhite))
            return true;
        
        //    mi muovo solo orizzontalmente di 2 o 3                                è la prima mossa                sono vicino ad una torre                                      //non ci sono pezzi in mezzo
        
        if(firstMove ){
        
            //bianco
            if(isWhite){

                //arrocco corto (destra)
                if(oldYp == yp && oldXp == xp-2){
                    
                    if(pieces[yp][xp+1] instanceof Rook && pieces[yp][xp] == null && pieces[yp][xp-1] == null){
                        ChessBoard.send(""+(xp+1)+(yp)+(xp-1)+(yp));
                        pieces[yp][xp+1].go(xp+1, yp, xp-1, yp);
                        return true;
                    }
                }
                //arrocco lungo (sinistra)
                if(oldYp == yp && oldXp == xp+2){
                    
                    if(pieces[yp][xp-2] instanceof Rook && pieces[yp][xp] == null && pieces[yp][xp+1] == null && pieces[yp][xp-1] == null){
                         ChessBoard.send(""+(xp-2)+(yp)+(xp+1)+(yp));
                        pieces[yp][xp-2].go(xp-2, yp, xp+1, yp);
                        return true;
                    }
                }
                

            }
            //nero
            else{

                //arrocco corto (sinistra)
                if(oldYp == yp && oldXp == xp+2){
                    
                    if(pieces[yp][xp-1] instanceof Rook && pieces[yp][xp] == null && pieces[yp][xp+1] == null){
                         ChessBoard.send(""+(xp-1)+(yp)+(xp+1)+(yp));
                        pieces[yp][xp-1].go(xp-1, yp, xp+1, yp);
                        return true;
                    }
                }
                //arrocco lungo (destra)
                if(oldYp == yp && oldXp == xp-2){
                    
                    if(pieces[yp][xp+2] instanceof Rook && pieces[yp][xp] == null && pieces[yp][xp-1] == null && pieces[yp][xp+1] == null){
                        ChessBoard.send(""+(xp+2)+(yp)+(xp-1)+(yp));
                        pieces[yp][xp+2].go(xp+2, yp, xp-1, yp);
                        return true;
                    }
                }
            }
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
