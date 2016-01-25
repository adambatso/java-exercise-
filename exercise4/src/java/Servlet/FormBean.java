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
public class FormBean {
    private String email=null;
    private String password=null;        
    private String firstName=null;
    private String lastName=null;
    
    public FormBean(String email,String password,String firstName,String lastName)
    {
        this.email= email!=null?"\"" + email +"\"":"\"\"";
        this.password= password!=null?"\""+password + "\"":"\"\"";
        this.firstName= firstName!=null?"\"" + firstName + "\"":"\"\"";
        this.lastName= lastName!=null?"\""+ lastName + "\"":"\"\"";
    }
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
}
