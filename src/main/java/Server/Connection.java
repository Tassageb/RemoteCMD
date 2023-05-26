/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author adrie
 */
public class Connection implements Runnable{
    ServerSocket listener;
    Server server;
    
    
    public Connection(Server server, int port){
        this.server = server;
        try {
            listener = new ServerSocket(port);
            System.out.println("Connection démarré");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void close(){
        try{ 
            this.listener.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        Socket client;
        if (this.listener != null) {
            try{
                while (true) {
                    System.out.println("Serveur en attente de connexions");

                    client = this.listener.accept();
                    System.out.println("Nouveau client");

                    this.server.addClient(new ConnectedClient(this.server, client));
                }
            } catch (IOException e) {
                System.out.println("Connection : " + e);
            }
        }
    }
    
}
