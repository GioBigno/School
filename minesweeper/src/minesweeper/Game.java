
package minesweeper;

import java.util.Random;

public class Game {
    
    private int dimCampo;
    private int numBombe;
    public int matrix[][];
    public int stato[][]; //0=coperta, 1=scoperta, 2=sospetta
   
    private int numScoperte = 0;
    private boolean fine = false;
    
    public Game(int dimCampo, int numBombe){
        
        this.dimCampo = dimCampo;
        this.numBombe= numBombe;
        
        matrix = new int[dimCampo][dimCampo];
        stato = new int[dimCampo][dimCampo];
        
        //Random rand = new Random();
        
        for(int i=0; i<dimCampo; i++){
            for(int j=0; j<dimCampo; j++){
                
                matrix[i][j] = 0;
                stato[i][j] = Field.COPERTA;
                //System.out.println("matrix["+i+"]["+j+"] = "+matrix[i][j]);
            }
        } 
    }
    
    public void click(int x, int y, int button){
        click(x, y, button, false);
    }
    
    //-1=perso, 0=niente, 1=vinto
    public int click(int x, int y, int button, boolean firstClick){
        
        if(fine == true)
            return 0;
        
        if(button == 0){
            //click sinistro
            
            if(firstClick == true){
                creaCampo(x, y);
                espandi(x, y);
                return 0;
            }else{
                
                if(matrix[x][y] == Field.BOMBA){
                    
                    matrix[x][y] = Field.BOMBAERRORE;
                    stato[x][y] = Field.SCOPERTA;
                    
                    for(int i=0; i<dimCampo; i++){
                        for(int j=0; j<dimCampo; j++){
                            if(matrix[i][j] == Field.BOMBA && stato[i][j] == Field.COPERTA){
                                stato[i][j] = Field.SCOPERTA;
                            }
                        }
                    }
                    fine = true;
                    return -1;
                }
                
                if(stato[x][y] == Field.SCOPERTA){
                    return 0;
                }
                
                if(stato[x][y] == Field.COPERTA){
                    espandi(x, y);
                    if(numScoperte == (dimCampo*dimCampo)-numBombe){
                        fine = true;
                        return 1;
                    }
                        
                } 
            }
            
        }else{
            //click destro
            
            if(stato[x][y] == Field.COPERTA){
                stato[x][y] = Field.SOSPETTA; 
                return 0;
            }
            
            if(stato[x][y] == Field.SOSPETTA){
                stato[x][y] = Field.COPERTA; 
                return 0;
            }
            
        }
        
        return 0;
        
    }
    
    private void creaCampo(int x, int y){
        //la cella (x;y) non dovra contenere bombe
        
        Random rand = new Random();
        
        int index=0;
        while(index<numBombe){
            
            int randX = rand.nextInt(dimCampo);
            int randY = rand.nextInt(dimCampo);
            
            if(!((randX == x || randX == x-1 || randX == x+1) &&
                (randY == y || randY == y-1 || randY == y+1)) &&
                (matrix[randX][randY] >= 0 && matrix[randX][randY] < 8)){
               
                matrix[randX][randY] = Field.BOMBA;
                
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        
                        if(randX+i >= 0 && randX+i < dimCampo && randY+j >= 0 && randY+j < dimCampo && matrix[randX+i][randY+j] != Field.BOMBA){
                            matrix[randX+i][randY+j]++;
                        }
                    }
                }
                
                System.out.println("generate " + index + "bombe");
                index++;
            }
        }
        
        matrix[x][y] = 0;     
    }
    
    private void espandi(int x, int y){
        
        if(x >= 0 && x < dimCampo && y >= 0 && y < dimCampo && stato[x][y] == Field.COPERTA){
            
            stato[x][y] = Field.SCOPERTA;
            numScoperte++;
            
            if(matrix[x][y] != 0){
                return;
            }
            
            for(int i=-1; i<2; i++){
                for(int j=-1; j<2; j++){

                    espandi(x+i, y+j);
                }
            }
        }
        
    }
    
}
