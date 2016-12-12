<%-- 
    Document   : index2.jsp
    Created on : 25-nov-2016, 17:12:36
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lander Game</title>
    </head>
    <style type="text/css">
    #formID { text-align: center; margin-top: 10%; }
    body { background-image: url('space.jpg'); }
    p { color: white; text-align: center; font-family: monospace; }
    </style>
    <body>
        <p>Lander GAME is a free game in which you have to land correctly.</p>
        <p>Join now, because it's free and... IS THE BEST GAME EVER!!!</p>
        <div id="formID">
            <form action="login.jsp">
                <div id="loginID">
                    <input type="image" src="img/button_login.png"></div></form>
            <form action="register.jsp">
                <div id="registerID">
                    <input type="image" src="img/button_register.png"></div></form>
        </div>
    </body>
</html>
