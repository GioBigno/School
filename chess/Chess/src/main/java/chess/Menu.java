/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Menu extends JFrame implements ActionListener {

    private JPanel pannello;
    private JLabel label;
    private JButton buttonServer;
    private JButton buttonClient;
    private JButton createServer;
    private JButton CreateClient;
    private JButton buttonStartClient;
    private JLabel labelIndirizzo;
    private JLabel labelPorta;
    private JTextField textIndirizzo;
    private JTextField textPorta;
    
    private static final int larghezza = 455;
    private static final int altezza = 400;
    private static final int port = 8080;
    
    
    public Menu() throws IOException{
       
        setBounds(10, 10, larghezza, altezza);
                
        pannello = new JPanel();
        
        label = new JLabel("Bignosoft chess on LAN");
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD,  30));
        pannello.add(label);
        
        buttonServer = new JButton("   Server   ");
        buttonServer.addActionListener(this);
        pannello.add(buttonServer);
        
        
        buttonClient = new JButton("Client");
        buttonClient.addActionListener(this);
        pannello.add(buttonClient);
        
        add(pannello);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
   
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == buttonServer){
          
            System.out.println("SERVER");
            
            pannello.remove(buttonServer);
            pannello.remove(buttonClient);
          
            String s = "";
          
            try{
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("google.it", 80));
                s += socket.getLocalAddress();
            
            }catch(IOException ex){
                System.out.println("errore di connessione");
            }
          
            label.setText("Bignosoft chess (Server)");
          
            labelIndirizzo = new JLabel("indirizzo ip: " + s);
            labelIndirizzo.setFont(new Font(Font.MONOSPACED, Font.BOLD,  20));
            pannello.add(labelIndirizzo);
          
            labelPorta = new JLabel("porta: " + port);
            labelPorta.setFont(new Font(Font.MONOSPACED, Font.BOLD,  20));
            //setBounds(getLocation().x, getLocation().y, larghezza+1, altezza);
            pannello.add(labelPorta);
            
            pannello.repaint();
            repaint();
            

            Server server = new Server(port);
            
            ChessBoard.out = server.getOut();
                
            Thread thread = new Thread(server);
            thread.start();
          
            System.out.println("eseguito menu server");
            
        }
      
        else if(e.getSource() == buttonClient){
          
            System.out.println("CLIENT");
            
            pannello.remove(buttonServer);
            pannello.remove(buttonClient);
          
            String s = "";

            label.setText("Bignosoft chess (Client)");
            
            labelIndirizzo = new JLabel("indirizzo ip del server: ");
            labelIndirizzo.setFont(new Font(Font.MONOSPACED, Font.BOLD,  20));
            pannello.add(labelIndirizzo);
            
            textIndirizzo = new JTextField();
            textIndirizzo.setPreferredSize(new Dimension(100, 20));
            pannello.add(textIndirizzo);
         
          
            labelPorta = new JLabel("porta: ");
            labelPorta.setFont(new Font(Font.MONOSPACED, Font.BOLD,  20));
            pannello.add(labelPorta);
            
            textPorta = new JTextField();
            textPorta.setPreferredSize(new Dimension(100, 20));
            pannello.add(textPorta);
            
            buttonStartClient = new JButton("Start");
            buttonStartClient.addActionListener(this);
            pannello.add(buttonStartClient);
            pannello.repaint();
            repaint();
            
        }
        
        if(e.getSource() == buttonStartClient){
            
            try{
                startClient(textIndirizzo.getText(), textPorta.getText());
            }catch(IOException ex){
                System.out.println("erroe inizializzazione client");
            }
        }

        
    }
    
    public void startClient(String ip, String porta) throws IOException{
        
        int num = Integer.parseInt(porta);
        
        Client client = new Client(ip, num);
        Thread thread = new Thread(client);
        thread.start();
          
        System.out.println("eseguito menu client");
        
        
    }
    
}