package chess;

import java.awt.Image;
import java.util.ArrayList;


public abstract class Piece {
    
    public static Piece[][] pieces;
    
    //position on the board
    public int xp; 
    public int yp;
    //position on the screen
    public int x;
    public int y;
    public boolean isWhite;
    public Image img;
    public static ChessBoard c;

    public Piece(int xp, int yp, boolean isWhite, Image img) {
        this.xp = xp;
        this.yp = yp;
        x = xp*64;
        y = yp*64;
        this.isWhite = isWhite;
        this.img = img;
    }
    
    public abstract boolean isLegit(int oldXp, int oldYp, int xp, int yp);
    public abstract void move(int xp, int yp);
    
    public void go(int oldXp, int oldYp, int xp, int yp){
        //aggiorno la posizione del pezzo nella matrice
        pieces[oldYp][oldXp] = null;
        this.xp = xp;
        this.yp = yp;
        x = xp*64;
        y = yp*64;
        pieces[this.yp][this.xp] = this;
        
        c.repaint();
        //ho mosso quindi non tocca più a me, quando riceverò una mossa sarà il mio turno
        Game.myTurn = false;
    }
    
    public void kill(){
        pieces[yp][xp] = null;
        System.out.println("kill " + xp + " " + yp);
    }
    
}

