<%HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int nc = Integer.parseInt(sesion.getAttribute("nmat").toString().trim());
        int n = Integer.parseInt(request.getParameter("nm").trim());
        sesion.setAttribute("nmat", (nc - 1));
%>

<input type="hidden" name="nmat" id="nmat" value="<%=n%>">
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>