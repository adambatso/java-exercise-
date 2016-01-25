<%-- 
    Document   : forum
    Created on : Jan 13, 2016, 7:24:47 PM
    Author     : Adam
--%>

<%@page import="Servlet.LoginBean"%>
<%@page import="DataBase.MessagesDataBase"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="Servlet.DataBaseBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
    </head>
    <body>
        
            <%
                LoginBean login = (LoginBean) request.getAttribute("LoginBean");
                if (login!=null)
                {
                    %><div>${LoginBean.errorMessage}</div><%  
                }
                DataBaseBean dataBaseBean=(DataBaseBean) request.getAttribute("DataBaseBean");
                System.out.println("we got it");
                if (dataBaseBean!=null)
                {
                    %><table border="1">
                    <tr><td>E-mail</td><td>Content</td><td>Date</td></tr>
                    <%
                        System.out.println("before while");
                   while (!dataBaseBean.end()) 
                   {
                       System.out.println("in while");
                       %>
                         <tr><td>${DataBaseBean.email}</td><td>${DataBaseBean.content}</td><td>${DataBaseBean.date}</td></tr>
                         <%
                         dataBaseBean.next();    
                   }
                }
            %>
            
            
        </table>
            <form method="POST" action="/exercise4/forum">
                <input type="text" style="width:300px" style="height: 250px" name="contentMessage"/>
                <input type="submit" />
            </form>
            <button onclick="location.href='/exercise4/logout'">Logout</button>
    </body>
</html>
