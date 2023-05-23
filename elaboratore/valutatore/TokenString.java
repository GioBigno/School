package valutatore;

import java.util.ArrayList;

public class TokenString {
    
    private ArrayList<Token> tokenList; 
    
    public TokenString(){
        
        tokenList = new ArrayList();
        
    }
    
    public void dividi(String espressione){
        
        //5+(2.56/6+13)-3
        //TODO: -5 -> 0-5
        double valore=0; //valore di un numero con più cifre
        int decimale=0; //numero di cifre decimale di valore
        
        for(int i=0; i<espressione.length(); i++){
            
            char corrente = espressione.charAt(i);
            
            if(Character.isDigit(corrente)){
                
                valore+=(Double.parseDouble(""+corrente) * (Math.pow(10, -decimale)));
                if(decimale != 0)
                    decimale++;
                
                
            }else if(corrente == '.'){
                
                decimale=1;
                
            }else if(isSimbolo(corrente)){
                
                decimale=0;
                
            }
            
            
            
        }
        
        
    }
    
    private boolean isSimbolo(char c){
        
        return (c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/' ||
                c == '^' ||
                c == '(' ||
                c == ')');
    }
    
}
