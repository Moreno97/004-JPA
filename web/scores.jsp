<%-- 
    Document   : scores
    Created on : 26-nov-2016, 19:16:13
    Author     : Antonio
--%>

<%@page import="Services.AmusersJpaController"%>
<%@page import="Services.AmregistryJpaController"%>
<%@page import="JPA.Amusers"%>
<%@page import="JPA.Amregistry"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scores</title>
    </head>
    <style type="text/css">
        body { background-image: url('space.jpg'); }
        .tg  {border-collapse:collapse;border-spacing:0; border-color: white;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:6px 20px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal; color: white;}
        .tg th{color: white; font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:6px 20px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
        .tg .tg-baqh{color: white; text-align:center;vertical-align:top}
        .tg .tg-ue9f{color: white; font-size:16px;font-family:"Palatino Linotype", "Book Antiqua", Palatino, serif !important;;background-color: gray;text-align:center;vertical-align:top}
    </style>
    <body>
    <center><img src="scores.png"></center>
    <center>
        <table class="tg">
            <tr>
                <th class="tg-ue9f">Nickname</th>
                <th class="tg-ue9f">Game</th>
                <th class="tg-ue9f">Speed (m/s)</th>
                <th class="tg-ue9f">Time (s)</th>
                <th class="tg-ue9f">Date</th>
            </tr>
            <% Amregistry rUsers = new Amregistry();
                Amusers u = new Amusers();
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("004-JPAPU");
                AmregistryJpaController rjc = new AmregistryJpaController(emf);
                AmusersJpaController us = new AmusersJpaController(emf);
                        List<Amregistry> r = rjc.getMaxScores(); %>
            <% for (int i = 0; i < r.size(); i++) {%>
            <tr>
                <td class="tg-baqh"><%= r.get(i).getIdUser().getNameUser()%></td>
                <td class="tg-baqh"><%= r.get(i).getIdPlay()%></td>
                <td class="tg-baqh"><%= r.get(i).getSpeed()%></td>
                <td class="tg-baqh"><%= (r.get(i).getEndGame().getTime() - r.get(i).getStartGame().getTime()) / 1000%></td>
                <td class="tg-baqh"><%= r.get(i).getStartGame().getDate() + "/" + (r.get(i).getStartGame().getMonth() + 1) + "/" + (r.get(i).getStartGame().getYear() + 1900)%></td>
            </tr>
            <%}%>

    </center>
</table>
</body>
<a href="indexGAME.jsp" style="color: white">Home</a>
</html>
