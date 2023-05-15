package gameoflife;

import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameOfLife {
    
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    
    public static void main(String[] args) {    
        
        JFrame window = new JFrame("Game of life");
        window.setBounds(10, 10, WIDTH, HEIGHT);
        
        Game game = new Game(45, 60);
        window.add(game);

        game.start();
        
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
}
