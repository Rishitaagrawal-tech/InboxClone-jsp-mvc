<%@ page import="java.sql.*"%>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies
	
	session = request.getSession();
	if(session == null || session.isNew() || session.getAttribute("email")==null){
		response.sendRedirect("index.jsp");
	}
%>

<%
	String headerusername = (String)session.getAttribute("name");
	String useremail = (String)session.getAttribute("email");
	char firstchar = 'U';
	if(headerusername != null && !headerusername.trim().isEmpty()){
			firstchar = Character.toUpperCase(headerusername.trim().charAt(0));
		}else{
			headerusername = "User";
			useremail = "blank";
		}
	
%>
    <div class="header">
        <ul id="hitems">
            <li class="logo"><a href="#"><img src="./img/image.png" alt="logo"></a></li>
            <li id="search_div"><input type="text" id="search" placeholder="search" onkeyup="searchMails()"></li>
            <li class="users"><h2 id="greeting">HI! <%=headerusername%></h2></li>
            <li class="circle" id="circle" >
				<div style="font-size: 30px;margin-top: 5%; margin-bottom: 25%;" title="<%=useremail%>"> <%=firstchar%> </div>
				<form action="logout.jsp" method="post" style="display:inline;">
					<button type="submit" id="logout-btn">Logout</button>
				</form>
			</li>    
        </ul>
        
    </div>
