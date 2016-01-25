/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DataBase.Message;
import DataBase.MessagesDataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Adam
 */
public class DataBaseBean {
    private Vector<Message> messageDataBase = null;
    private int row=0;
    
    public void setMessagesDataBase(Vector<Message> m) {this.messageDataBase = m;}
    public String getEmail() throws SQLException{return messageDataBase.get(row).getEmail();}
    public String getContent() throws SQLException{return messageDataBase.get(row).getMessage();}
    public String getDate() throws SQLException{return messageDataBase.get(row).getDate();}
    public void next() {row++;}
    public boolean end(){return row>=messageDataBase.size();}
}
