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
        boolean numero = false;
        double valore=0; //valore di un numero con più cifre
        int decimale=0; //numero di cifre decimale di valore
        
        for(int i=0; i<espressione.length(); i++){
            
            char corrente = espressione.charAt(i);
            
            if(Character.isDigit(corrente)){
                
                numero = true;
                valore+=(Double.parseDouble(""+corrente) * (Math.pow(10, -decimale)));
                if(decimale != 0)
                    decimale++;
                
            }else if(corrente == '.'){
                
                if(decimale == 0){
                    decimale=1;
                }else{
                    // TODO errore
                }
            }else if(isSimbolo(corrente)){
                
                decimale=0;
                if(numero){
                    numero = false;
                    tokenList.add(new Token(TipoToken.NUMERO, valore));
                }
                
                switch(corrente){
                    case '(':
                        tokenList.add(new Token(TipoToken.PARENTESI_APERTA));
                        break;
                    case ')':
                        tokenList.add(new Token(TipoToken.PARENTESI_CHIUSA));
                        break;
                    case '+':
                        tokenList.add(new Token(TipoToken.PIU));
                        break;
                    case '-':
                        tokenList.add(new Token(TipoToken.MENO));
                        break;
                    case '*':
                        tokenList.add(new Token(TipoToken.PER));
                        break;
                    case '/':
                        tokenList.add(new Token(TipoToken.DIVISO));
                        break;
                    case '^':
                        tokenList.add(new Token(TipoToken.ELEVATO));
                        break;
                }
                
            }else if(Character.isLetter(corrente)){
                //TODO
            }else{
                System.out.println("carattere non valido!");
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
