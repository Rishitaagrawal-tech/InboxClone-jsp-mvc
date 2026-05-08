<%
	session.invalidate();
	
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
		for(Cookie c : cookies){
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}
	
	response.sendRedirect("si");
%>