/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.serveurclient;

import Client.Client;
import Server.Server;

/**
 *
 * @author adrie
 */
public class ServeurClient {
    
    public static void main(String[] args) {
        if (args.length > 0){
            if (args[0].equals("SERVER") && args.length > 1){
                try {
                    int port = Integer.parseInt(args[1]);
                    
                    Server server = new Server(port);
                    
                    
                } catch (NumberFormatException ex) {
                    System.out.println("Le port spécifié n'est pas valide");
                }
            } else if (args[0].equals("CLIENT") && args.length > 2){
                try {
                    int port = Integer.parseInt(args[2]);
                    String adress = args[1];
                    
                    new Client(adress, port);
                } catch (NumberFormatException ex) {
                    
                }
            }
        } else {
            System.out.println("Nombre de paramètres incorrect");
        }
        
    }
}
