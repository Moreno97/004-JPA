<%-- 
    Document   : index
    Created on : 21-nov-2016, 18:01:32
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lander Game</title>
        <script type="text/javascript">
            function redirectServlet() {
               window.location="${pageContext.request.contextPath}/indexServlet";
            }
        </script>
    </head>
    <body onload='redirectServlet()'>
    </body>
</html>
