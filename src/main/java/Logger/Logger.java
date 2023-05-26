/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author adrie
 */
public class Logger {
    private File file;
    
    public Logger(){
        this.file = new File("history.log");
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
    
    public void log(String message){
        System.out.println(message);
        try {
            Date date = Calendar.getInstance().getTime();        
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");  
            String strDate = dateFormat.format(date);    
            
            FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            
            bw.write("[" +strDate+ "]"+message);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        
    }
    
}
