<%-- 
    Document   : index
    Created on : 4/03/2018, 03:52:48 PM
    Author     : Carlos Rivas
--%>
<%HttpSession sesion = request.getSession();
    String Error = "";
    if (sesion.getAttribute("Error") != null) {
        Error = sesion.getAttribute("Error").toString();
        sesion.removeAttribute("Error");
    }%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SICLAC | Acceso</title>
        <meta charset="UTF-8">        
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.min.css">        
        <link rel="stylesheet" href="css/signin.css">
        <link rel="shortcut icon" href="images/favicon.ico">
    </head>
    <body style="background: lightcyan; text-align: center">
        <div class="container-fluid">
            <div>               
                <form class="form-signin" name="fors" method="POST" action="acces">                                          
                    <img src="images/Am_Labs.png"  alt="" width="650" class="img-fluid">
                    <br><h1 onclick="fullScreen();" style="text-align: center;" class="h3 mb-3 font-weight-normal"><strong>Acceso al sistema</strong></h1>
                    <p style="text-align: center; color: red"><strong><%=Error%></strong></p>
                    <label for="inputEmail" class="sr-only">Usuario</label>
                    <input type="text" name="user" id="user" class="form-control" placeholder="Usuario" required autofocus><br>
                    <label for="inputPassword" class="sr-only">Contraseña</label>
                    <input type="password" name="pass" id="pass" class="form-control" placeholder="Contraseña" required><br>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Aceptar</button>        
                    <img src="images/adn0.gif"  alt="" width="650" height="100" class="img-fluid">          
                </form> 
            </div>
        </div>        
    </body>
</html>