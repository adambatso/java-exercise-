/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise2;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class readFromDataInputStream implements IReading{

    private BufferedReader input=null;

    readFromDataInputStream(FileReader fileReader) {
        
        input = new BufferedReader(fileReader);
    }
    
    readFromDataInputStream(Reader inputstream){
        
        input = new BufferedReader(inputstream);
    }

    private Exception EOFException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public ArrayList<String> readURLs() 
    {
        ArrayList<String> lines = null;
        
        String newLine;
         
        try {
            while ((newLine=input.readLine())!=null)
            {
                if (!newLine.isEmpty())
                {
                    if (lines == null)
                        lines = new ArrayList();
                        
                    lines.add(newLine);
                }
                  
            }
        } catch (IOException ex) {
            Logger.getLogger(readFromDataInputStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    
    //TODO close buffer reader in destructor!!
}
