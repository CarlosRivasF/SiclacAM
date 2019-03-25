<%-- 
    Document   : testPush
    Created on : 17/03/2019, 11:45:00 AM
    Author     : Carlos Rivas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/push.min.js"></script>
    </head>
    <body>

        <script>
            new Notification("Notificación",{
                     body:"Esta es una notificación desde SICLAC",
                     icon:"${pageContext.request.contextPath}/images/iconNotification.png"
                     }                    
                     });
                    /*
                     Push.create("Notificación",{
                     body:"Esta es una notificación desde SICLAC",
                     icon:"${pageContext.request.contextPath}/images/iconNotification.png",
                     timeout:8000,
                     onclick:function(){
                     window.location="https://www.youtube.com/watch?v=kA2C2DGVnWY";
                     }                    
                     });
                     */
        </script>        

    </body>        
</html>
