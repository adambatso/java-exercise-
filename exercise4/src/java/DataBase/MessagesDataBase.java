/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Vector;

/**
 *
 * @author Adam
 */
public class MessagesDataBase {
    // the url of the driver
    final String DBaddress = "jdbc:mysql://localhost:3306/ex4?user=root&password="; 
    
    // Call the Oracle driver
    final String odbcDriver = "com.mysql.jdbc.Driver";
    
    public MessagesDataBase() throws SQLException{     
        // The connection to the database	
        Connection con=openConnection();
        // Get a statement object from the connection
        Statement statement = con.createStatement();

        // Creating a table
        //
        String creatTable = "CREATE TABLE IF NOT EXISTS messages (email char(255), content char(255), m_date char(255))";
        statement.executeUpdate(creatTable);
        con.close();
    }
    
    private Connection openConnection() throws SQLException{
        Connection  con=null;
           try {
            // Call the Oracle driver
            Class.forName(odbcDriver);
            }

            catch (Exception e) {
                throw new SQLException("Could not connect to remote DB");
            }
        try {
            // Create the connection to the database
            con = DriverManager.getConnection(DBaddress);

            } 
            catch (Exception e) {
                throw new SQLException("Could not connect to remote DB");              
            }
        return con;
    }
    
        public void addNewMessage(String email, String content) throws SQLException, NoSuchAlgorithmException {
        Connection con=openConnection();
        
        String date=String.valueOf(LocalDateTime.now().getDayOfMonth()) 
            + "/" + String.valueOf(LocalDateTime.now().getMonth().getValue())
            + "/" + String.valueOf(LocalDateTime.now().getYear());
        
        String query = "INSERT INTO messages VALUES (?, ?, ?)";
        PreparedStatement pstatement = con.prepareStatement(query);
        pstatement.setString(1, email);
        pstatement.setString(2, content);
        pstatement.setString(3, date);
        pstatement.executeUpdate();
        System.out.println("added message new : "+content);
        con.close();
    }
   
    public Vector<Message> getAllMessages() throws SQLException
    {
        Connection con = openConnection();
        Vector<Message> chatMessages = new Vector();
        String CheckQuery = "SELECT * FROM messages";
        PreparedStatement ps = con.prepareStatement(CheckQuery);

        ps.execute();
        ResultSet res = ps.getResultSet();
        while (res.next())
        {
            System.out.println("enter new message");
            chatMessages.add(new Message(res.getString(1),res.getString(2),res.getString(3)));
        }
        System.out.println("messages: ");
        con.close();
        return chatMessages;
    }
}
