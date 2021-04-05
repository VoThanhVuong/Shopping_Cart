<%-- 
    Document   : login
    Created on : Jan 11, 2021, 10:59:13 AM
    Author     : vuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>
        <form action="MainController" method="POST">
            User ID: <input type="text" name="txtUserID"/></br>
            Password: <input type="password" name="txtPassword"/></br>
            <c:if test="${sessionScope.MESSAGE!=null}">
                <mark>${sessionScope.MESSAGE}</mark>
            </c:if>
            <div class="g-recaptcha" data-sitekey="6LeFuOAZAAAAABd8o_63Ol9gCSaR5md7TYIZC0jK"></div>
            <input type="submit" name="btnAction" value="Login"/>
            <input type="reset" value="Reset"/>
            <a href="MainController?btnAction=Create Account">Create Account</a>
        </form>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/Lab01/LoginController&response_type=code
    &client_id=721687399559-ckfdvbuvrlp73ep4k37fe4s05mik6bo4.apps.googleusercontent.com">Login With Google</a>
    </body>
</html>
   
