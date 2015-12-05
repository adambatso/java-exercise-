/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author LotemA
 */
public class DBWriter implements IWriting{

    // the url of the driver
    final String DBaddress = "jdbc:mysql://localhost:3306/ex2?user=root&password="; 
    
    // Call the Oracle driver
    final String odbcDriver = "com.mysql.jdbc.Driver";
    
    
    public void writeUrlArray(ArrayList<URLImplementation.Pair<String,Integer>> URLarr)
    {
        // The connection to the database	
        Connection con = null;
                
        try {
            // Call the Oracle driver
            Class.forName(odbcDriver);
            }

            catch (Exception e) {
                    System.out.println("Failed to load the driver");
                    return;
            }
        try {
            // Create the connection to the database
            con = DriverManager.getConnection(DBaddress);

            // Get a statement object from the connection
            Statement statement = con.createStatement();


            // Droping a the table books if it is exists so that later we
            // can create a new table books. If the table does not exists
            // then the dropping will cause an exception to be thrown.
            // The exception is caught so that nothing happens.
            //
            try {
                    statement.executeUpdate("DROP TABLE pages");
            } catch (Exception e) {
                    // do nothing 
            }


            // Creating a table
            //
            String createBooks = "CREATE TABLE pages (url char(255) primary key, number int)";
            statement.executeUpdate(createBooks);

            for (URLImplementation.Pair<String,Integer> p : URLarr)
            {
                String query = "INSERT INTO pages VALUES (?, ?)";
                statement.executeUpdate
                    ("INSERT INTO pages VALUES ('" + p.getFirst() +"',"+ p.getSecond().toString() +")");
//                PreparedStatement pstatement = con.prepareStatement(query);
//                pstatement.setString(1, p.getFirst());
//                pstatement.setInt(2, p.getSecond().toString());
//                pstatement.executeQuery();
            }
         
        } catch (Exception e) {
                System.out.println("Exception was thrown:\n"); 
                e.printStackTrace();
        }


    finally {
            try {
            if (con != null) { con.close(); }
            } catch (SQLException e) {
                    e.printStackTrace();
            }
    }
        
    }
    
    
}
