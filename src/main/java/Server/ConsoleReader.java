/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author adrie
 */
public class ConsoleReader implements Runnable {
    private Server server;

    public ConsoleReader(Server server) {
        this.server = server;
    }
    
    
    @Override
    public void run() {
        System.out.println("Démarrage de ConsoleReader");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        while (true) {
            try {
                line = reader.readLine();
                if (line.equals("STOP")) {
                    this.server.stop();
                    break;
                } else if (line.equals("LISTE")) {
                    this.server.showList();
                } else if (line.startsWith("SELECT")) {
                    String[] mot = line.split(" ");
                    if (mot.length > 1) {
                        try {
                            this.server.setSelected(Integer.parseInt(mot[1]));
                        } catch (NumberFormatException ex){
                            System.out.println(ex);
                        }
                    }
                } else {
                    this.server.sendCommand(line);
                }
            } catch (IOException e){
                System.out.println(e);
            }
        }
        System.out.println("Arrêt de ConsoleReader");
    }
    
}
