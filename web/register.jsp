<%-- 
    Document   : login
    Created on : 21-nov-2016, 18:24:55
    Author     : Antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="formoid_files/formoid1/formoid-solid-red.css" type="text/css" />
        <script type="text/javascript" src="formoid_files/formoid1/jquery.min.js"></script>
        <title>Register to play Lander Game</title>
        <script type="text/javascript">
            function validatePasswords() {
                var pass1 = document.getElementById("pass1").value;
                var pass2 = document.getElementById("pass2").value;
                
                if (pass1 !== pass2) {
                    alert("Passwords do not match, please try again.");
                }
            }
        </script>
    </head>
    <body>
        <!-- Start Formoid form -->
        <form class="formoid-solid-red" style="background-color:#FFFFFF;font-size:11px;font-family:'Palatino Linotype','Book Antiqua',Palatino,serif;color:#34495E;max-width:600px;min-width:150px" action="RegisterServlet" method="post" >
            <div class="title">
                <h2>Register</h2>
            </div>
            <div class="element-input" title="Insert username">
                <label class="title"><span class="required">*</span></label>
                <div class="item-cont"><input class="large" type="text" name="inputUser" required="required" placeholder="Username"/><span class="icon-place"></span></div>
            </div>
            <div class="element-input" title="Insert email">
                <label class="title"><span class="required">*</span></label>
                <div class="item-cont"><input class="large" type="email" name="inputEmail" required="required" placeholder="Email" /><span class="icon-place"></span></div>
            </div>
            <div class="element-input" title="Insert password">
                <label class="title"><span class="required">*</span></label>
                <div class="item-cont"><input class="large" type="password" name="inputPassword" required="required" placeholder="Password" id="pass1"/><span class="icon-place"></span></div>
            </div>
            <div class="element-input" title="Insert password confirmation">
                <label class="title"><span class="required">*</span></label>
                <div class="item-cont"><input class="large" type="password" name="inputPass2" required="required" placeholder="Password confirmation" id="pass2"/><span class="icon-place"></span></div>
            </div>
            <div class="submit"><input type="submit" value="Submit" onclick="validatePasswords()"/></div>
        </form>
        <script type="text/javascript" src="formoid_files/formoid1/formoid-solid-red.js"></script>
        <!-- Stop Formoid form-->
    </body>
</html>
