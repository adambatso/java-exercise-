/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import static Servlet.LoginForumServlet.getUserDB;
import java.io.IOException;
import java.io.PrintWriter;
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
public class RegisterServlet extends HttpServlet {



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
        
             HttpSession session = request.getSession();

            if (session.getAttribute("login")!=null )
            {
                session.setAttribute("login", false);
            }
            
            FormBean formDetail=new FormBean(null,null,null,null);
            request.setAttribute("FormBean", formDetail);
            RequestDispatcher dispatcher=request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response); 
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
        boolean legalForm=true;
        try {
            System.out.println("12312312387128937128937812378912738912");
        String email=request.getParameter("regEmail");
        String password=request.getParameter("regPass");
        String firstName = request.getParameter("regFirstName");
        String lastName = request.getParameter("regLastName");
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty())
        {
            LoginBean error=new LoginBean("Missing parameters");
            request.setAttribute("LoginBean", error);
            legalForm=false;
            
        }

        String pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
        Pattern r = Pattern.compile(pattern);        
        Matcher m = r.matcher(email);
        if (legalForm && !m.matches())
        {
            LoginBean error=new LoginBean("Incorrect email: "+email);
            request.setAttribute("LoginBean", error);
            legalForm=false;
        }
        if (legalForm && password.length()<8)
        {
            LoginBean error=new LoginBean("Password should be eight characters and above");
            request.setAttribute("LoginBean", error);
            legalForm=false;
        }

        if (legalForm && getUserDB().checkExistUser(email))
        {
            LoginBean error=new LoginBean("Email is alreay used");
            request.setAttribute("LoginBean", error);
            legalForm=false;
        }
        if (!legalForm)
        {
            FormBean formDetail=new FormBean(email,password,firstName,lastName);
            request.setAttribute("FormBean", formDetail);
            
            RequestDispatcher dispatcher=request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        //Now we can add new user
        getUserDB().addNewUser(email, password, firstName, lastName);
        response.sendRedirect("/exercise4/welcome");
        }
        catch(Exception e ) 
        {
            throw new ServletException(e.getMessage());
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
