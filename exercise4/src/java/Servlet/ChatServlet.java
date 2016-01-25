/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DataBase.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Adam
 */
public class ChatServlet extends HttpServlet {
    private UserDataBase userDBManager=null;
    private MessagesDataBase messageDBManeger=null;
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
           String page = request.getParameter("page");
            if (page != null)
            {
                System.out.println("page!=null");
                if(page.equals("register"))
                {
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/register.jsp");
                    dispatcher.forward(request, response); 
                    return;
                }

                if (page.equals("logout"))
                {
                    System.out.println("weeeeeeeeeeeeeeeeeeee");
                    HttpSession session = request.getSession();
                    session.setAttribute("login", false);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/index.jsp");
                    dispatcher.forward(request, response); 
                    return;
                }

            }


            HttpSession session = request.getSession();
            if (session.getAttribute("login")==null  || (Boolean) session.getAttribute("login")==false)
            {
                 RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/index.jsp");
                 dispatcher.forward(request, response);
            }
            else 
            {
                DataBaseBean dataBean = new DataBaseBean();
                dataBean.setMessagesDataBase(messageDBManeger.getAllMessages());
                request.setAttribute("DataBaseBean", dataBean);
                RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/forum.jsp");
                dispatcher.forward(request, response); 
            } 
        }
        catch (Exception ex) {
            throw new ServletException(ex.getMessage());
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
            URI currUri = new URI(request.getRequestURI());
            
            
            if (request.getParameter("page").equals("login"))
            {
                String email=request.getParameter("email");
                String password=request.getParameter("pass");
                if (email.isEmpty()|| password.isEmpty())
                {
                    LoginBean error=new LoginBean("Missing parameters");
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/index.jsp");
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

                        RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/forum.jsp");                        
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        LoginBean error=new LoginBean("Incorrect parameters");
                        request.setAttribute("LoginBean", error);
                        RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/index.jsp");
                        dispatcher.forward(request, response);
                    } 
                } 

            }
            else if (request.getParameter("page").equals("register"))
            {
                String email=request.getParameter("regEmail");
                String password=request.getParameter("regPass");
                String firstName = request.getParameter("regFirstName");
                String lastName = request.getParameter("regLastName");
                if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty())
                {
                    LoginBean error=new LoginBean("Missing parameters");
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                String pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
                Pattern r = Pattern.compile(pattern);        
                Matcher m = r.matcher(email);
                if (!m.matches())
                {
                    LoginBean error=new LoginBean("Incorrect email: "+email);
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/register.jsp");
                    dispatcher.forward(request, response);
                    response.sendRedirect("ChatServlet?param=2");
                    return;
                }
                if (password.length()<8)
                {
                    LoginBean error=new LoginBean("Password should be eight characters and above");
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                if (userDBManager.checkExistUser(email))
                {
                    LoginBean error=new LoginBean("Email is alreay used");
                    request.setAttribute("LoginBean", error);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/register.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                
                //Now we can add new user
                userDBManager.addNewUser(email, password, firstName, lastName);
                LoginBean msg=new LoginBean("Added new user!");
                request.setAttribute("LoginBean", msg);
                RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/index.jsp");
                dispatcher.forward(request, response);
            }
            else if (request.getParameter("page").equals("addMessage"))
            {
                System.out.println("addmessage");
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
                
                RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/forum.jsp");
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
