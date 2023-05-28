package valutatore;

import java.util.ArrayList;
import java.util.Stack;

public class TokenString {
    
    private String espressione;
    private ArrayList<Token> tokenList;
    private ArrayList<Token> postfix;
    
    public TokenString(String espressione){
        
        tokenList = new ArrayList();
        postfix = new ArrayList();
        this.setEspressione(espressione);
    }
    
    public void setEspressione(String espressione){
        
        this.espressione = espressione;
        tokenList.clear();
        this.dividi();
        this.postFissa();
    }
    
    private void dividi(){
        
        //5+(2.56/6+13)-3
        //TODO: -5 -> 0-5
        boolean numero = false;
        double valore=0; //valore di un numero con più cifre
        int decimale=0; //numero di cifre decimale di valore
        
        for(int i=0; i<espressione.length(); i++){
            
            char corrente = espressione.charAt(i);
            System.out.println("analizzo: "+corrente);
            
            if(Character.isDigit(corrente)){
                
                numero = true;
                if(decimale == 0)
                    valore*=10;
                
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
                
                System.out.println("trovato simbolo");
                
                decimale=0;
                if(numero){
                    numero = false;
                    System.out.println("add numero, "+valore);
                    tokenList.add(new Token(TipoToken.NUMERO, valore));
                    valore = 0;
                }
                
                switch(corrente){
                    case '(':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.PARENTESI_APERTA));
                        break;
                    case ')':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.PARENTESI_CHIUSA));
                        break;
                    case '+':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.PIU));
                        break;
                    case '-':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.MENO));
                        break;
                    case '*':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.PER));
                        break;
                    case '/':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.DIVISO));
                        break;
                    case '^':
                        System.out.println("add simbolo");
                        tokenList.add(new Token(TipoToken.ELEVATO));
                        break;
                }
                
            }else if(Character.isLetter(corrente)){
                //TODO
            }else{
                System.out.println("carattere non valido!");
            }
                       
        }
        
        if(numero){
            numero = false;
            System.out.println("add numero, "+valore);
            tokenList.add(new Token(TipoToken.NUMERO, valore));
            valore = 0;
        }
        
    }
    
    private void postFissa(){
        
        Stack<Token> stack = new Stack();
        
        for(int i=0; i<tokenList.size(); i++){
            
            Token corrente = tokenList.get(i);
            
            //operando
            if(corrente.getTipo() == TipoToken.NUMERO){
                postfix.add(corrente);
                
            }else if(corrente.getTipo() == TipoToken.PARENTESI_APERTA){
                stack.push(corrente);
                
            }else if(corrente.getTipo() == TipoToken.PARENTESI_CHIUSA){
                while(!stack.isEmpty() && stack.peek().getTipo() != TipoToken.PARENTESI_APERTA){
                    postfix.add(stack.pop());
                }
            }else{
                 while(!stack.isEmpty() && prec(corrente) <= prec(stack.peek())){
                     postfix.add(stack.pop());
                 }
                 stack.push(corrente);
            }
        }
        
        while(!stack.isEmpty()){
            if(stack.peek().getTipo() == TipoToken.PARENTESI_APERTA){
                System.out.println("espressione non valida");
            }
            postfix.add(stack.pop());
        }
    }
    
    static int prec(Token t){
        
        TipoToken tipo = t.getTipo();
        
        switch (tipo) {
            case PIU:
            case MENO:
                return 1;
            case PER:
            case DIVISO:
                return 2;
            case ELEVATO:
                return 3;
        }
        return -1;
    }
    
    public void printList(){
        System.out.println("print, size = "+tokenList.size());
        for(Token t : postfix){
            System.out.println("tipo = "+t.getTipo() + ", dato = "+t.getDato());
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
