/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Adam
 */
public class URLImplementation {
    
    //constant
    final private String pattern = "(<[iI][mM][gG] )";
    
    //private parameter
    private URL url = null;
    private BufferedReader reader = null;
    
    public class Pair<A,B> {
        private A first;
        private B second;
        
        Pair(A a,B b)
        {
            this.first=a;
            this.second=b;
        }
        public A getFirst() {return this.first;}
        public B getSecond() {return this.second;}
    }
    
    private ArrayList<Pair<String,Integer>> urlResult=new ArrayList();
    
    public Boolean putUrl(String CheckUrl)
    {
      url=null;
 
     
        try {
            url=new URL(CheckUrl);
            String path=url.getPath();
            //if there is file in the url 
            if (!path.isEmpty())
            {
                int apearOfPeriod=path.indexOf('.');
                if (apearOfPeriod!=-1)
                {
                    path=path.substring(apearOfPeriod);
                }
                String pattern = "(.htm)l?";
                Pattern r = Pattern.compile(pattern);
                
                Matcher m = r.matcher(path);
                if (!m.matches())
                    throw new MalformedURLException("Missing htm or html extension from URL");
            }

        } catch (MalformedURLException ex) {
            System.out.println("Illegal URL: " + ex.getMessage());
            return false;
            
        }
        
        
        return true;
    };
    
    public void openConnection() throws ConnectException, IOException {
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
    }
    
     public void closeConnection() throws IOException{
         if (reader!=null)
            reader.close();
         
         reader = null;
     }
     
     public void insertToArray() throws IOException{
         
        String inputLine;
        int counter=0;
 
        Pattern r = Pattern.compile(pattern);
        while ((inputLine = reader.readLine()) != null)
        {
            Matcher m = r.matcher(inputLine);
            while (m.find())
                counter++;
        }
        
        urlResult.add(new Pair<String,Integer>(url.getProtocol() +"://"+ url.getHost() + url.getFile(),counter));

     }
     
     public ArrayList<Pair<String,Integer>> getUrlArray()
     {
         return urlResult;
     }
     
     public boolean isURLAlreadyExist(String checkURL){
         
         if (urlResult != null)
         {
             for (Pair p : urlResult)
             {
                 if (p.getFirst().equals(checkURL))
                     return true;
            }
         }
         
         return false;
     }
}
