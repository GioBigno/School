
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Finestra extends JFrame implements ActionListener{
   
    private Menu menu;
    private Field field;
    
    private int dimCampo;
    private int numBombe;
    
    private JButton button;    
    private ImageIcon buttonIcon[];
    private static final int dimButton = 90;
    
    public Finestra(){
        
        setSize(new Dimension(600, 550));
        setLocationRelativeTo(null);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            BufferedImage all = ImageIO.read(new File("images/assets.png"));
            int dimImg = 25;
            
            buttonIcon = new ImageIcon[3];
            
            Image i = all.getSubimage(232, 0, dimImg, dimImg).getScaledInstance(dimButton, dimButton, BufferedImage.SCALE_SMOOTH);
            buttonIcon[0] = new ImageIcon(i);
            
            dimImg--;
            
            i = all.getSubimage(232, dimImg, dimImg, dimImg).getScaledInstance(dimButton, dimButton, BufferedImage.SCALE_SMOOTH);
            buttonIcon[1] = new ImageIcon(i);
            
            i = all.getSubimage(232, (dimImg*2), dimImg, dimImg).getScaledInstance(dimButton, dimButton, BufferedImage.SCALE_SMOOTH);
            buttonIcon[2] = new ImageIcon(i);
            
        }catch(Exception e){}
        
        
        
        menu = new Menu(this);   
        add(menu);
        
        setVisible(true);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void avvia(int dimCampo, int numBombe){
        
        this.dimCampo = dimCampo;
        this.numBombe = numBombe;
        
        remove(menu);
        
        System.out.println("tolto menu");
        
        JPanel main = new JPanel(new BorderLayout(0,0));
        
        field = new Field(dimCampo, numBombe, this);
        main.add(field, BorderLayout.CENTER);
 
        JPanel panelButton = new JPanel();
        button = new JButton(buttonIcon[0]);
        button.setPreferredSize(new Dimension(90, 90));
        button.addActionListener(this);
        panelButton.add(button);
        
        main.add(panelButton, BorderLayout.NORTH);
        
        add(main);
        
        setVisible(true);
        repaint();
        
        System.out.println("aggiunto field");
        
    }
    
    public void ridimensiona(int width, int height){
        setBounds(getX(), getY(), width, height+dimButton);
    }
   
    public void gameOver(){
        button.setIcon(buttonIcon[2]);
    }
    
    public void win(){
        button.setIcon(buttonIcon[1]);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        field.restart();
        button.setIcon(buttonIcon[0]);
        repaint();
        
    }
    
    
    
}
