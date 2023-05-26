/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrie
 */
public class ConnectedClient implements Runnable {
    static int idCounter = 0;
    
    int id;
    String adress;
    Server server;
    Socket socket;
    BufferedReader in;
    BufferedWriter out;
    
    public ConnectedClient(Server server, Socket socket) throws IOException{
        System.out.println("Création du ConnectedClient");
        
        this.id = idCounter++;
        
        this.server = server;
        this.socket = socket;
        
        this.adress = this.socket.getRemoteSocketAddress().toString();
        System.out.println(this.adress);
        
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }
    
    public void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void close(){
        System.out.println("Fermeture de " + this.adress);
        try {
            this.server.removeClient(this);
            this.socket.close();
            this.in.close();
            this.out.close();
            
            //System.out.println("IN - OUT - SOCKET CLOSED");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }
    
    @Override
    public String toString(){
        return this.adress + " - " + this.id;
    }
    
    //Attente des messages
    @Override
    public void run() {
        String line;
        
        System.out.println("Debut de l'attente des messages de "+this.adress);
        try {
            while (!this.socket.isClosed()){
                line = in.readLine();

                if (line == null){
                    this.close();
                    break;
                }
                
                System.out.println(this.toString() + " ---- " + line);
            }
        } catch (IOException e) {
            System.out.println("ConnectedClient : "+ this.adress + " : " + e);
            
            if (!this.socket.isClosed()){
                this.close();
            }
        }    
        
        System.out.println("Fin de l'attente des messages de "+this.adress);
    }
    
}
