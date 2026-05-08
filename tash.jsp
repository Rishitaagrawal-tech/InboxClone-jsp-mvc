
<%@ include file="header.jsp"%>


	
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Tash Mails</title>
  <link rel="stylesheet" href="./css/inbox.css">
  <link rel ="stylesheet" href="./css/draft.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<section class="is1">
        <div class="isb1">
			<%@ include file="sidebar.jsp"%>
		</div>
		
        <div class="imc1"id="main-content">
			<h2>Your Deleted Mails</h2>
			<div class="draft-container" style="overflow-y: scroll; height: 88%">
			
			 <!-- Make draft clickable: goes to compose.jsp with draftId -->

<%			
	ResultSet rs = (ResultSet)request.getAttribute("result");
	boolean hasmail = false;
	while(rs.next())
	{
		hasmail = true;
%>
				<div class="draft-card">
					<div class="text">
						
						<h3>to:<%= rs.getString("to1") %></h4>
						<h4>From:<%=rs.getString("from1")%></h4>
						<p><strong>Subject:<%=rs.getString("subject")%></strong> </p>
						<p><%=rs.getString("message")%></p>
						<small>Saved on: <%=rs.getTimestamp("date")%></small>
					</div>
					<div class="edit">
						<i class="fa-solid fa-trash"></i>
					</div>
				</div>

<%
	}
	if(!hasmail){
%>
				<p>No drafts saved.</p>
<%
	}
%>
			</div>
		</div>
    </section>
  
</body>
</html>