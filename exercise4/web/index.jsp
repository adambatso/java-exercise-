<%-- 
    Document   : index
    Created on : Jan 10, 2016, 8:33:15 PM
    Author     : Adam
--%>


<%@page import="Servlet.LoginBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Please sign in!</h1>
        <%
        LoginBean login = (LoginBean) request.getAttribute("LoginBean");
        if (login!=null)
        {
            %><div>${LoginBean.errorMessage}</div><%  
        }
        %>
        
        <form method="POST" action="/exercise4/welcome">
            <div>E-mail: <input name="email" type="text" /></div>
            <div>Password: <input name="pass" type="password" /></div>
            <input type="submit" value="Login"/>
        </form>
        <button onclick="location.href='/exercise4/register'">Register</button>
    </body>
</html>
