/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Adam
 */
public class UserDataBase {
    
    // the url of the driver
    final String DBaddress = "jdbc:mysql://localhost:3306/ex4?user=root&password="; 
    
    // Call the Oracle driver
    final String odbcDriver = "com.mysql.jdbc.Driver";
 
    /**
     * 
     * @throws java.sql.SQLException
     */
    public UserDataBase() throws SQLException{     
        // The connection to the database	
        Connection con=openConnection();
        // Get a statement object from the connection
        Statement statement = con.createStatement();

        // Creating a table
        //
        String creatTable = "CREATE TABLE IF NOT EXISTS users (email char(255) primary key, hashedPassword char(255), firstName char(255), lastName char(255))";
        statement.executeUpdate(creatTable);
        con.close();
    }
    
    public boolean checkExistUserPassword(String emailCheck ,String pass) throws SQLException, NoSuchAlgorithmException{
        boolean result;
        Connection con = openConnection();

        String CheckQuery = "SELECT * FROM users WHERE email=? AND hashedPassword=?";
        PreparedStatement ps = con.prepareStatement(CheckQuery);
        
        
        ps.setString(1, emailCheck);
        ps.setString(2, hash(pass));
        ps.execute();
        ResultSet res = ps.getResultSet();
        result = res.next();
        con.close();
        return result;
    }
    
        public boolean checkExistUser(String emailCheck) throws SQLException, NoSuchAlgorithmException{
        boolean result;
        Connection con = openConnection();

        String CheckQuery = "SELECT * FROM users WHERE email=?";
        PreparedStatement ps = con.prepareStatement(CheckQuery);

        ps.setString(1, emailCheck);
        ps.execute();
        ResultSet res = ps.getResultSet();
        
        result = res.next();
        con.close();
        return result;
    }
        
    /**
     *
     * @param email
     * @param pass
     * @param firstName
     * @param lastName
     * @throws SQLException
     * @throws java.security.NoSuchAlgorithmException
     */
    public void addNewUser(String email, String pass, String firstName, String lastName) throws SQLException, NoSuchAlgorithmException {
        Connection con=openConnection();
        String query = "INSERT INTO users VALUES (?, ?, ?, ?)";
        PreparedStatement pstatement = con.prepareStatement(query);
        pstatement.setString(1, email);
        pstatement.setString(2, hash(pass));
        pstatement.setString(3, firstName);
        pstatement.setString(4, lastName);
        pstatement.executeUpdate();
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
   
    private String hash(String strToConvert) throws NoSuchAlgorithmException
    {
        byte[] passwordByte=strToConvert.getBytes();
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passwordByte);
        byte[] passwordDigest=md.digest();
        
        return new String(passwordDigest);
    }
    
}
