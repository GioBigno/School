
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Menu extends JPanel implements ActionListener{
    
    private JLabel titolo;
    private JLabel textSelezione;
    private JComboBox<String> selezione;
    private JLabel dimCampo;
    private JLabel numBombe;
    private JTextField dimCampoField;
    private JTextField numBombeField;
    private JButton avvia;
    
    private Finestra padre;
    
    public Menu(Finestra padre){
        
        this.padre = padre;
        
        //setPreferredSize(new Dimension(getWidth(), getHeight()));
        //setBackground(Color.red);
        
        //setLayout(new BorderLayout(0, 0));
        
        JPanel main = new JPanel(new GridLayout(4, 1, 0, 30));
        
        JPanel panelTitolo = new JPanel();
        titolo = new JLabel("minesweeper");
        titolo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        panelTitolo.add(titolo);
        
        main.add(panelTitolo);
        
        
        JPanel sel = new JPanel(new GridLayout(2, 1, 0, 0));
        
        JPanel panelTextSelezione = new JPanel();
        textSelezione = new JLabel("seleziona difficoltà");
        textSelezione.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        panelTextSelezione.add(textSelezione);
        sel.add(panelTextSelezione);
        
        String[] difficolta = {"facile", "media", "difficile", "personalizzata"};
        JPanel panelSelezione = new JPanel();
        selezione = new JComboBox<>(difficolta);
        //selezione.setBounds(100, 100, 150, 100);
        selezione.setPreferredSize(new Dimension(300, 40));
        selezione.addActionListener(this);
        panelSelezione.add(selezione);
        
        sel.add(panelSelezione);
        
        
        main.add(sel);
        
        JPanel insert = new JPanel(new GridLayout(2, 1, 0, 0));
        
        dimCampo = new JLabel("dimensione del campo: ");
        dimCampo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        dimCampoField = new JTextField("16");
        dimCampoField.setPreferredSize(new Dimension(60, 30));
        dimCampoField.setEditable(false);
        
        JPanel dim = new JPanel();
        dim.add(dimCampo);
        dim.add(dimCampoField);
        
        numBombe = new JLabel("numero di bombe: ");
        numBombe.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        numBombeField = new JTextField("32");
        numBombeField.setPreferredSize(new Dimension(60, 30));
        numBombeField.setEditable(false);
        
        JPanel num = new JPanel();
        num.add(numBombe);
        num.add(numBombeField);
        
        insert.add(dim);
        insert.add(num);
        
        main.add(insert);
        
        
        JPanel panelButton = new JPanel();
        avvia = new JButton("avvia");
        avvia.addActionListener(this);
       
        panelButton.add(avvia);
        
        main.add(avvia);
        
        add(main);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        if(ae.getSource() == selezione){
            
            
            String testoSelezionato = selezione.getItemAt(selezione.getSelectedIndex());
            
            if(testoSelezionato.equals("facile")){
                dimCampoField.setText("16");
                numBombeField.setText("40");
                dimCampoField.setEditable(false);
                numBombeField.setEditable(false);
            }else if(testoSelezionato.equals("media")){
                dimCampoField.setText("30");
                numBombeField.setText("160");
                dimCampoField.setEditable(false);
                numBombeField.setEditable(false);
            }else if(testoSelezionato.equals("difficile")){
                dimCampoField.setText("40");
                numBombeField.setText("280");
                dimCampoField.setEditable(false);
                numBombeField.setEditable(false);
            }else if(testoSelezionato.equals("personalizzata")){
                dimCampoField.setEditable(true);
                numBombeField.setEditable(true);
            }
            
            repaint();
            
        }else if(ae.getSource() == avvia){
            
            int dim, num;
            
            if(Pattern.matches("[a-zA-Z]+", dimCampoField.getText()) == false){
                dim = Integer.parseInt(dimCampoField.getText());
            }else{
                return;
            }
            
            if(Pattern.matches("[a-zA-Z]+", numBombeField.getText()) == false){
                num = Integer.parseInt(numBombeField.getText());
            }else{
                return;
            }
            
            if(dim < 6 || num < 1 || num >= (dim*dim)/3 || dim > 70){
                return;
            }
            
            System.out.println("avvio");
            padre.avvia(dim, num);
        }
        
    }
    
}
