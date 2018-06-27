<%HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int nc = Integer.parseInt(sesion.getAttribute("nconf").toString().trim());
        int n = Integer.parseInt(request.getParameter("nc").trim());
        sesion.setAttribute("nconf", nc - 1);
%>

<input type="hidden" name="nconf" id="nconf" value="<%=n%>">
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>