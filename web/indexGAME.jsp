<%-- 
    Document   : indexGAME
    Created on : 24-nov-2016, 17:05:00
    Author     : Antonio
--%>
<%@page import="Services.AmusersJpaController"%>
<%@page import="Services.AmregistryJpaController"%>
<%@page import="JPA.Amusers"%>
<%@page import="JPA.Amregistry"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lander Menu</title>
        <meta charset="UTF-8" >
        <style>
            body {
                background-image: url(space.jpg);
            }
            h5 {
                color: white;
                text-align: right;
            }
            MARQUEE {
                color: white;
                font: bold serif;
                font-family: fantasy;
            }
            footer {
                margin-top: 37%;
                text-align: center;
                width: 100%;
                background-color: #000000;
                height: auto;
                color: #FFFFFF;
            }
            #scores {
                border-radius: 22px 22px 22px 22px;
                background-color: #f7e2b9;
                display: none;
                text-align: center;
                -moz-border-radius: 22px 22px 22px 22px;
                -webkit-border-radius: 22px 22px 22px 22px;
                border: 0px solid #000000;
            }
            #menu-bar {
                width: 68%;
                margin: 0px 0px 0px 0px;
                margin-left: 15%;
                padding: 6px 6px 4px 6px;
                height: 30px;
                line-height: 100%;
                border-radius: 0px;
                -webkit-border-radius: 0px;
                -moz-border-radius: 0px;
                box-shadow: 0px 0px 0px #666666;
                -webkit-box-shadow: 0px 0px 0px #666666;
                -moz-box-shadow: 0px 0px 0px #666666;
                background: #8B8B8B;
                background: linear-gradient(top,  #A9A9A9,  #7A7A7A);
                background: -ms-linear-gradient(top,  #A9A9A9,  #7A7A7A);
                background: -webkit-gradient(linear, left top, left bottom, from(#A9A9A9), to(#7A7A7A));
                background: -moz-linear-gradient(top,  #A9A9A9,  #7A7A7A);
                border: solid 0px #6D6D6D;
                position:relative;
                z-index:999;
            }
            #menu-bar li {
                margin: 0px 0px 6px 0px;
                padding: 0px 6px 0px 6px;
                float: left;
                position: relative;
                list-style: none;
            }
            #menu-bar a {
                font-weight: bold;
                font-family: 'times new roman';
                font-style: normal;
                font-size: 12px;
                color: #E7E5E5;
                text-decoration: none;
                display: block;
                padding: 6px 20px 6px 20px;
                margin: 0;
                margin-bottom: 6px;
                border-radius: 0px;
                -webkit-border-radius: 0px;
                -moz-border-radius: 0px;
                text-shadow: 0px 0px 0px #000000;
            }
            #menu-bar li ul li a {
                margin: 0;
            }
            #menu-bar .active a, #menu-bar li:hover > a {
                background: #0399D4;
                background: linear-gradient(top,  #EBEBEB,  #A1A1A1);
                background: -ms-linear-gradient(top,  #EBEBEB,  #A1A1A1);
                background: -webkit-gradient(linear, left top, left bottom, from(#EBEBEB), to(#A1A1A1));
                background: -moz-linear-gradient(top,  #EBEBEB,  #A1A1A1);
                color: #444444;
                -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
                -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
                box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
                text-shadow: 2px 2px 3px #FFFFFF;
            }
            #menu-bar ul li:hover a, #menu-bar li:hover li a {
                background: none;
                border: none;
                color: #666;
                -box-shadow: none;
                -webkit-box-shadow: none;
                -moz-box-shadow: none;
            }
            #menu-bar ul a:hover {
                background: #D4A97D !important;
                color: #FFFFFF !important;
                border-radius: 0;
                -webkit-border-radius: 0;
                -moz-border-radius: 0;
                text-shadow: 2px 2px 3px #FFFFFF;
            }
            #menu-bar li:hover > ul {
                display: block;
            }
            #menu-bar ul {
                background: #DDDDDD;
                display: none;
                margin: 0;
                padding: 0;
                width: 185px;
                position: absolute;
                top: 30px;
                left: 0;
                border: solid 0px #B4B4B4;
                border-radius: 0px;
                -webkit-border-radius: 0px;
                -moz-border-radius: 0px;
                -webkit-box-shadow: 2px 2px 3px #222222;
                -moz-box-shadow: 2px 2px 3px #222222;
                box-shadow: 2px 2px 3px #222222;
            }
            #menu-bar ul li {
                float: none;
                margin: 0;
                padding: 0;
            }
            #menu-bar ul a {
                padding:10px 0px 10px 15px;
                color:#424242 !important;
                font-size:12px;
                font-style:normal;
                font-family:arial;
                font-weight: normal;
                text-shadow: 2px 2px 3px #FFFFFF;
            }
            #menu-bar ul li:first-child > a {
                border-top-left-radius: 0px;
                -webkit-border-top-left-radius: 0px;
                -moz-border-radius-topleft: 0px;
                border-top-right-radius: 0px;
                -webkit-border-top-right-radius: 0px;
                -moz-border-radius-topright: 0px;
            }
            #menu-bar ul li:last-child > a {
                border-bottom-left-radius: 0px;
                -webkit-border-bottom-left-radius: 0px;
                -moz-border-radius-bottomleft: 0px;
                border-bottom-right-radius: 0px;
                -webkit-border-bottom-right-radius: 0px;
                -moz-border-radius-bottomright: 0px;
            }
            #menu-bar:after {
                content: ".";
                display: block;
                clear: both;
                visibility: hidden;
                line-height: 0;
                height: 0;
            }
            #menu-bar {
                display: inline-block;
            }
            html[xmlns] #menu-bar {
                display: block;
            }
            * html #menu-bar {
                height: 1%;
            }
        </style>
        <script type="text/javascript">
            function show() {
                document.getElementById('scores').style.display = 'block';
            }
        </script>
        <%  Amregistry rUsers = new Amregistry();
            Amusers u = new Amusers();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("004-JPAPU");
            AmregistryJpaController rjc = new AmregistryJpaController(emf);
            AmusersJpaController us = new AmusersJpaController(emf);
                    String message = "";%>
        <% for (int i = 0; i < us.getUsers().size(); i++) {
            if (rjc.getMaxScores().size() == 0) {
                message = "NONE";
            } else {
                message += rjc.getMaxScores().get(i).getIdUser().getNameUser() + " ";
            } }%>
    <MARQUEE WIDTH=100%>BEST PLAYERS ARE: <%= message %></MARQUEE>
    <center><img src="cooltext219441709678063.png"></center>
    <ul id="menu-bar">
        <li><a href="lander/index.html" type="Submit">Play</a></li>
        <li><a href="#">Scores</a>
            <ul>
                <li><a href="scores.jsp">TOP 3</a></li>
                <li><a href="scores10.jsp">TOP 10</a></li>
                <li><a href="pScores.jsp" onclick="">My scores</a></li>
            </ul>
        </li>
        <li><a href="#">About</a></li>
        <li><a href="online.jsp">Who's online?</a></li>
        <form action="LogoutServlet" method="get">
            <li><a href="LogoutServlet" type="Submit">LOGOUT</a></li></form>
    </ul>
</head>
<body>
    <%
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("cNAME")) {
                response.getWriter().print("<h5>Welcome, " + cookies[i].getValue().toUpperCase() + "</h5>");
            }
        }%>
    <footer>
        Copyright© 2016, Antonio</br>
        All rights reserved.
    </footer>
</body>
</html>
