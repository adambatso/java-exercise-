/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

/**
 *
 * @author Adam
 */
public class LoginBean {
    private String errorMessage;

    public LoginBean(String errorMessage) {
        this.errorMessage=errorMessage;
    }
    public String getErrorMessage(){return errorMessage ;}
    public void setErrorMessage(String e){errorMessage=e;}       
    
    
}
