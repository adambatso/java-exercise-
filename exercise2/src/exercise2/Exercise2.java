/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise2;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class Exercise2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        try {
            IReading input;
            ArrayList<String> urls = null;
            URLImplementation urlManager = new URLImplementation();
            IWriting output= new DBWriter();
            input = new readFromDataInputStream(new FileReader("url.txt"));

            urls = input.readURLs();
            if (urls == null)
                throw new EOFException("Empty file. Closing process...");
            
            System.out.println(urls);

            for (String urlCheck : urls)
            {
                try{
                    if (urlManager.putUrl(urlCheck)){
                        urlManager.openConnection();
                        if (!urlManager.isURLAlreadyExist(urlCheck))
                            urlManager.insertToArray();   
                    }   
                }
                catch (ConnectException ex) {
                    System.out.println("Could not connect to:" + ex.getMessage());
                }
                catch (IOException ex) {
                    System.out.println("Could not connect to:" + ex.getMessage());
                }
                finally {
                    try {
                        urlManager.closeConnection(); 
                    }
                    catch (IOException ex){
                        System.out.println("Could not disconnect to:" + ex.getMessage());
                    }
                   
                }
            }

            //connect to DB and write to it
            output.writeUrlArray(urlManager.getUrlArray());
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Exercise2.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (EOFException ex){
            System.out.println(ex.getMessage());
        }
        
        
        

    }
    
}
