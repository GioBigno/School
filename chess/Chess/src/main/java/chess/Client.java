package chess;

import java.net.*;
import java.io.*;

public class Client implements Runnable{
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private static String ip;
    private static int port;
    
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public void run() {
    
        try{
            startConnection(ip, port);
        }catch(IOException e){
            System.out.println("errore connessione client");
        }
    }

   
    
    public void startConnection(String ip, int port) throws IOException{
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        System.out.println("client pronto");
        
        ChessBoard.isWhite = false;
        Game.myTurn = false;
        ChessBoard.out = out;
        Chess.StartGame();
        
        while(!clientSocket.isClosed()){
            
            String message = in.readLine();
            ChessBoard.command(message);
            
        }
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
    }
    
    public PrintWriter getOut(){
        return out;
    }
    
}
