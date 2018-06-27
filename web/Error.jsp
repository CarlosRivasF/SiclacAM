<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%@ page isErrorPage="true" %> 
 
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offcanvas.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">        
    </head>
    <body style=" background: #666666">
        <div id="Main" style="color: white">
            <nav class="navbar navbar-expand-md fixed-top navbar-light bg-light">
                <strong><a class="navbar-brand"  style="color: #72d0f6" href=""><ins>Ha ocurrido algo mal...</ins></a></strong>                
            </nav>                        
            <div><br>                  
                <Strong><h2 style="text-align: center; color: #ffffff"><%exception.printStackTrace(response.getWriter()); %></h2></Strong>                                 
            </div>            
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>        
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/holder.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/offcanvas.js"></script>        
    </body>
</html>