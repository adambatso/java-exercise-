<%-- 
    Document   : register
    Created on : Jan 12, 2016, 8:53:55 PM
    Author     : Adam
--%>

<%@page import="Servlet.FormBean"%>
<%@page import="Servlet.LoginBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
    </head>
    <body>
        
        <%
        LoginBean login = (LoginBean) request.getAttribute("LoginBean");
        if (login!=null)
        {
            %><div>${LoginBean.errorMessage}</div><%  
        }
 
        %>
        <form method="POST" action="/exercise4/register">
            <input type="hidden" name="page" value="register">
        <table border="1">
            <tr><td>E-mail</td><td><input type="text" name="regEmail" value=${FormBean.email} /></td></tr>
            <tr><td>Password</td><td><input type="password" name="regPass" value=${FormBean.password} /></td></tr>
            <tr><td>First name</td><td><input type="text" name="regFirstName" value=${FormBean.firstName} /></td></tr>
            <tr><td>Last name</td><td><input type="text" name="regLastName" value=${FormBean.lastName} /></td></tr>
        </table>
            <input type="submit" value="register" />
        </form>
        <button onclick="location.href='/exercise4/welcome'">Return</button>
    </body>
</html>
