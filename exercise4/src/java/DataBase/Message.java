/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;



/**
 * class that represent message - save user name and content message.
 * @author Adam Ben Aharon and Liron Amiel
 */
public class Message {
       
        /**
         * user name 
         */
        private String email;
        /**
         * message 
         */
        private String message;
        /**
         * date of creation 
         */
        private String date;

        
        /**
         * constructor 
         * @param userName - user name 
         * @param message - message content 
         */
        public Message(String userName, String message,String date) {
                this.email  = userName;
                this.message  = message;
                
                this.date=date;
        }
        
        /**
         * 
         * @return the user name of the message
         */
        public String getEmail() { return email; }
        /**
         * 
         * @return message content
         */
        public String getMessage() { return message; }
        /**
         * 
         * @return date of creation 
         */
        public String getDate() { return date; }

}
