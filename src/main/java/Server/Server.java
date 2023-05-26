/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author adrie
 */
public class Server {
    private int port;
    private Connection connection;
    private Thread consoleReader;
    
    private ArrayList<ConnectedClient> clientList;
    private int selected = 0;

    public void setSelected(int selected) {
        this.selected = selected;
    }
    
    public Server(int port){
        System.out.println("Création du serveur sur le port "+ port);
        this.clientList = new ArrayList();
        
        this.connection = new Connection(this, port);
        this.consoleReader = new Thread(new ConsoleReader(this));
        
        this.start();
    }
    
    private void start(){
        new Thread(this.connection).start();
        this.consoleReader.start();
    }
    
    public void addClient(ConnectedClient client){
        new Thread(client).start();
        this.clientList.add(client);
    }
    
    public void removeClient(ConnectedClient client){
        if (this.clientList.contains(client)){
            this.clientList.remove(client);
        }
    }
   
    public void showList(){
        int index = 0;
        String line;
        System.out.println("LISTE DES CLIENTS CONNECTES");
        for (ConnectedClient client : this.clientList){
            line = " - " + index + " : " + client.toString();
            if (this.selected == index){
                line += "   selectionne";
            }
            System.out.println(line);
            index++;
        }
        System.out.println("FIN DE LA LISTE DES CLIENTS CONNECTES");
    }
    
    public void sendCommand(String command){
        if (selected >= 0 && selected < this.clientList.size()){
            ConnectedClient current = this.clientList.get(selected);
            System.out.println("Envoie à " + current.toString());
            current.sendMessage(command);
        } else {
            System.out.println("Envoie incorrect");
        }
    }
    
    public void broadcastMessage(String message){
        System.out.println("Envoie de : " + message);
        this.clientList.forEach(e -> {
            e.sendMessage(message);
        });
    }
    
    public void stop(){
        this.connection.close();
        while (!this.clientList.isEmpty()){
            this.clientList.remove(0).close();;
        }
    }
}
