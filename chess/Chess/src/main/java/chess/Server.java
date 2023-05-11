/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author bigno
 */
public class Server implements Runnable{
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    private static int port;

    public Server(int port){
        this.port = port;
    }
    
    @Override
    public void run() {
        
        try{
            start();
        }catch(IOException e){
            System.out.println("errore apertura server");
        }
    }

    
    
    public void start() throws IOException{
        
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("server pronto");
        
        ChessBoard.isWhite = true;
        Game.myTurn = true;
        ChessBoard.out = out;
        Chess.StartGame();
        
        while(!serverSocket.isClosed()){
            
            String message = in.readLine();
            ChessBoard.command(message);
            
        }
    }

    public void stop() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public PrintWriter getOut(){
        return out;
    }
    

}
