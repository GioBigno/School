
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ChessBoard extends JPanel{
    
    //public static ArrayList<Piece> pieces = new ArrayList<Piece>();
    public static Piece[][] pieces = new Piece[8][8];
    public static boolean isWhite;
    public static PrintWriter out;
    
    public static boolean checkMe = false;
    public static boolean checkYou = false;
    public static int oldXp = -1;
    public static int oldYp = -1;
    public static int newXp = -1;
    public static int newYp = -1;
    
    public ChessBoard() throws IOException{
        
        BufferedImage all = ImageIO.read(new File(ChessBoard.class.getResource("../images/chess.png").getFile()));
        Image imgs[] = new Image[12];
        int index = 0;
        for(int y=0; y<400; y+=200){
            for(int x=0; x<1200; x+=200){
                imgs[index] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }
        
        if(out != null)
        System.out.println("out funziona");
        
        Piece.pieces = pieces;
        Piece.c = this;
        
        int a=0, b=1, c=7, d=6;
        
        if(isWhite == false){
            a=7;
            b=6;
            c=0;
            d=1;
        }
        
        
        //black
        pieces[a][0] = new Rook(0, a, false, imgs[10]);
        pieces[a][1] = new Knight(1, a, false, imgs[9]);
        pieces[a][2] = new Bishop(2, a, false, imgs[8]);
        pieces[a][3] = new Queen(3, a, false, imgs[7]);
        pieces[a][5] = new Bishop(5, a, false, imgs[8]);
        
        if(isWhite){
            pieces[a][3] = new Queen(3, a, false, imgs[7]);
            pieces[a][4] = new King(4, a, false, imgs[6]);
        }else{
            pieces[a][4] = new Queen(4, a, false, imgs[7]);
            pieces[a][3] = new King(3, a, false, imgs[6]);
        }
        
        pieces[a][6] = new Knight(6, a, false, imgs[9]);
        pieces[a][7] = new Rook(7, a, false, imgs[10]);
        pieces[b][0] = new Pawn(0, b, false, imgs[11]);
        pieces[b][1] = new Pawn(1, b, false, imgs[11]);
        pieces[b][2] = new Pawn(2, b, false, imgs[11]);
        pieces[b][3] = new Pawn(3, b, false, imgs[11]);
        pieces[b][4] = new Pawn(4, b, false, imgs[11]);
        pieces[b][5] = new Pawn(5, b, false, imgs[11]);
        pieces[b][6] = new Pawn(6, b, false, imgs[11]);
        pieces[b][7] = new Pawn(7, b, false, imgs[11]);
        
        //white
        pieces[c][0] = new Rook(0, c, true, imgs[4]);
        pieces[c][1] = new Knight(1, c, true, imgs[3]);
        pieces[c][2] = new Bishop(2, c, true, imgs[2]);
        
        if(isWhite){
            pieces[c][3] = new Queen(3, c, true, imgs[1]);
            pieces[c][4] = new King(4, c, true, imgs[0]);
        }else{
            pieces[c][4] = new Queen(4, c, true, imgs[1]);
            pieces[c][3] = new King(3, c, true, imgs[0]);
        }
        
        pieces[c][5] = new Bishop(5, c, true, imgs[2]);
        pieces[c][6] = new Knight(6, c, true, imgs[3]);
        pieces[c][7] = new Rook(7, c, true, imgs[4]);
        pieces[d][0] = new Pawn(0, d, true, imgs[5]);
        pieces[d][1] = new Pawn(1, d, true, imgs[5]);
        pieces[d][2] = new Pawn(2, d, true, imgs[5]);
        pieces[d][3] = new Pawn(3, d, true, imgs[5]);
        pieces[d][4] = new Pawn(4, d, true, imgs[5]);
        pieces[d][5] = new Pawn(5, d, true, imgs[5]);
        pieces[d][6] = new Pawn(6, d, true, imgs[5]);
        pieces[d][7] = new Pawn(7, d, true, imgs[5]);
        
    }
    
    
    
    public void paint(Graphics g){
        
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                
                if(checkMe && pieces[y][x] instanceof King && pieces[y][x].isWhite == isWhite){
                    g.setColor(new Color(255, 20, 20));
                }else if(checkYou && pieces[y][x] instanceof King && pieces[y][x].isWhite != isWhite){
                    g.setColor(new Color(255, 20, 20));
                }else if(x == oldXp && y == oldYp || x == newXp && y == newYp){
                    g.setColor(new Color(173, 195, 64));
                }else if(y%2 == x%2){
                    g.setColor(new Color(255, 235, 166));
                }else{
                    g.setColor(new Color(178, 143, 27));
                }
                   
                g.fillRect(x*64, y*64, 64, 64);
                
            }
        }
        
        
        for(Piece[] row : pieces){
            for(Piece p: row){
                if(p != null){
                    //System.out.print("x ");
                    g.drawImage(p.img, p.x, p.y, this);
                }else{
                    //System.out.print("- ");
                }
            }
            //System.out.println("");
        }
        
        //System.out.println("fine");
           
    }
    
    public static Piece getPiece(int x, int y){
        
        if(x > 64*8 || x < 0)
            return null;
                
        if(y > (64*8)+40 || y < 0)
            return null;
        
       return pieces[(y-40)/64][x/64];
    }
    
    public static void command(String message){
        
        //tipo  fromX.fromY toX,toY, fine
        // tipo -> m = mossa 
        // fine -> f
        
        System.out.println("ricevuto: " + message);
        
        char tipo;
        int fromX, fromY;
        int toX, toY;
        
        int found = 0;
        
        int i=0;
        while(message.charAt(i) != 'm' && message.charAt(i) != 'c'){
            i++;
        };
        
        if(message.charAt(i) == 'c')
            ChessBoard.checkMe=true;
        else
            ChessBoard.checkMe = false;
        
        i++;
        fromX = Integer.parseInt(message.charAt(i)+"");
        i++;
        fromY = Integer.parseInt(message.charAt(i)+"");
        i++;
        toX = Integer.parseInt(message.charAt(i)+"");
        i++;
        toY = Integer.parseInt(message.charAt(i)+"");
        
        fromX  = 7 - fromX;
        toX = 7 - toX;
        fromY = 7 - fromY;
        toY = 7 - toY;
        
        if(pieces[fromY][fromX] != null){
            pieces[fromY][fromX].go(fromX, fromY, toX, toY);
            //ha mosso, tocca a me 
            Game.myTurn = true;
        }
        
        if(isCheck(isWhite))
            checkMe = true;
        else
            checkMe = false;
        
        if(isCheck(!isWhite))
            checkYou = true;
        else
            checkYou = false;
        
        oldXp = fromX;
        oldYp = fromY;
        newXp = toX;
        newYp = toY;
        
    }
    
    public static void send(String message){
        
        String c = "m";
        
        checkMe = false;
        
        if(isCheck(!isWhite))
            checkYou = true;
        else
            checkYou = false;
        
        oldXp = Integer.parseInt(message.charAt(0)+"");
        oldYp = Integer.parseInt(message.charAt(1)+"");
        newXp = Integer.parseInt(message.charAt(2)+"");
        newYp = Integer.parseInt(message.charAt(3)+"");
      
        System.out.println("mando: " + c + message);
        out.println(c+message);
    }
    
    //è sotto scacco il re di colore color?
    public static boolean isCheck(boolean color){ 
    
        int kingX=0;
        int kingY=0;
          
        for(Piece[] row : pieces){
            for(Piece p: row){
                if(p instanceof King && p.isWhite == color){
                    kingX = p.xp;
                    kingY = p.yp;
                    break;
                }
                    
            }
        }
              
        for(Piece[] row : pieces){
            for(Piece p: row){
                if(p != null && p.isWhite != color && p.isLegit(p.xp, p.yp, kingX, kingY))
                    return true;
            }
        }
        
        return false;
    }
    
    //è sotto scacco il re di colore color se va in x,y ? 
    public static boolean isCheck(boolean color, int x, int y){ 
    
        for(Piece[] row : pieces){
            for(Piece p: row){
                if(p != null && p.isWhite != color && p.isLegit(p.xp, p.yp, x, y))
                    return true;
            }
        }
        
        return false;
    }

    
}
