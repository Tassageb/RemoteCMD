/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import Logger.Logger; 

/**
 *
 * @author adrie
 */

public class Client implements Runnable {
    private final String adress;
    private final int port;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private final CommandPrompt cmd;
    private final Logger logger = new Logger();

    public Client(String adress, int port) {
        this.adress = adress;
        this.port = port;
        
        
        this.cmd = new CommandPrompt(this);
        
        this.connect();
    }
    
    private void connect(){
        while (this.socket == null || this.socket.isClosed()){
            try {
                logger.log("Tentative de connexion");
                this.socket = new Socket(this.adress, this.port);
                logger.log("Connecté !");
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

                new Thread(this).start();

            } catch (IOException e){
                logger.log("Client : " + e);
            }
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                logger.log("SLEEP : " + ex);
            }
        }
        
    }
    
    public void sendMessage(String message){
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
         
    public void disconnect(){
        try {
            this.socket.close();
            this.cmd.stop();
            
            this.connect(); //Auto Reconnect
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        String line;
        try {
            while (!this.socket.isClosed()){
                line = in.readLine();
                if (line == null){
                    
                    break;
                }
                //System.out.println(line);
                
                this.cmd.execute(line);
            }
        } catch (IOException ex){
            System.out.println(ex);
        }
        
        this.disconnect();
        logger.log("Deconnecté !");
    }
}
