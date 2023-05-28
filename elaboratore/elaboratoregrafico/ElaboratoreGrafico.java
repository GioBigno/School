package elaboratoregrafico;

import java.util.Scanner;
import valutatore.TokenString;

public class ElaboratoreGrafico {

    private static String espressione;
    
    public static void main(String[] args) {
       
        Scanner in = new Scanner(System.in);
        
        System.out.println("inserisci espressione: ");
        espressione = in.nextLine();
        
        TokenString t = new TokenString(espressione);
        
        t.printList();
        
    }
    
}
