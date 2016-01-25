/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import DataBase.MessagesDataBase;
import DataBase.UserDataBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adambe
 */
public class LoginForumServlet extends HttpServlet {
    
    private MessagesDataBase messageDBManeger=null;
    private static UserDataBase userDBManager=null;
    @Override
    public void init() throws ServletException {
        try{
            userDBManager=new UserDataBase();
            messageDBManeger=new MessagesDataBase();
        }
        catch (SQLException e){
            throw new ServletException(e.getMessage());
        }
         
    }
    
    public static UserDataBase getUserDB() { return userDBManager;}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String currentUri = request.getRequestURI();
            HttpSession session = request.getSession();
            String pattern = "(.*/forum)";
            Pattern r = Pattern.compile(pattern);        
            Matcher m = r.matcher(currentUri);
            if (m.matches())
            {
               
                if (session.getAttribute("login")==null  || (Boolean) session.getAttribute("login")==false)
                {
                    response.sendRedirect("/exercise4/welcome");
                    
                }
                else {
                    DataBaseBean dataBean = new DataBaseBean();
                    dataBean.setMessagesDataBase(messageDBManeger.getAllMessages());
                    request.setAttribute("DataBaseBean", dataBean);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("forum.jsp");
                    dispatcher.forward(request, response);    
                }
                return;

            }
            else
            {
                if (session.getAttribute("login")!=null  && (Boolean) session.getAttribute("login")==true)
                {
                    response.sendRedirect("/exercise4/forum"); 
                    
                }
                else 
                {
                    RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response); 
                }
            }
 
        }
        catch(Exception e ) 
        {
            throw new ServletException(e.getMessage());
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            HttpSession session = request.getSession();
            String currentUri = request.getRequestURI();
            
            String pattern = "(.*/welcome)";
            Pattern r = Pattern.compile(pattern);        
            Matcher m = r.matcher(currentUri);
            if (m.matches())
            {
                System.out.println("enter user");
                String email=request.getParameter("email");
                String password=request.getParameter("pass");
                if (email.isEmpty()|| password.isEmpty())
                {
                    LoginBean error=new LoginBean("Missing parameters");
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");
                    dispatcher.forward(request, response);
                }
                else 
                {

                    if (userDBManager.checkExistUserPassword(email, password))
                    {

                        System.out.println("enterforum");
                        session.setAttribute("login", true);
                        session.setAttribute("userName", email);

                        DataBaseBean dataBean = new DataBaseBean();
                        dataBean.setMessagesDataBase(messageDBManeger.getAllMessages());
                        request.setAttribute("DataBaseBean", dataBean);
                        response.sendRedirect("/exercise4/forum");

                    }
                    else
                    {
                        LoginBean error=new LoginBean("Incorrect parameters");
                        request.setAttribute("LoginBean", error);
                        RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");
                        dispatcher.forward(request, response);
                    } 
                }
                return;
            }
            
            pattern = "(.*/forum)";
            r = Pattern.compile(pattern);        
            m = r.matcher(currentUri);
            if (m.matches())
            {
                String email=(String) session.getAttribute("userName");
                String content=request.getParameter("contentMessage");
                
                
                if (content.length() > 255)
                {
                   LoginBean error=new LoginBean("Message length should not be above 255 characters");
                   request.setAttribute("LoginBean", error); 
                }
                else
                {
                    messageDBManeger.addNewMessage(email,content);
                }
                
                DataBaseBean dataBean = new DataBaseBean();
                dataBean.setMessagesDataBase(messageDBManeger.getAllMessages());
                request.setAttribute("DataBaseBean", dataBean);
                
                RequestDispatcher dispatcher=request.getRequestDispatcher("forum.jsp");
                dispatcher.forward(request, response);
                
            }
            
                
        } 
        catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        }  
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
