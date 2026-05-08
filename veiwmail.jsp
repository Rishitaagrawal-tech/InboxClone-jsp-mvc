<%@ page import="java.sql.*"%>
<%@ include file="header.jsp"%>

<%!
	String from1 = "";
	String subject = ""; 
	String message = ""; 
	String formattedDate = "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Veiw Email</title>
  <link rel="stylesheet" href="./css/inbox.css">
  <link rel ="stylesheet" href="./css/veiw.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<section class="is1">
        <div class="isb1">
			<%@ include file="sidebar.jsp"%>
		</div>

<% 	
	
	String from1 = (String)request.getAttribute("from1");
	String subject = (String)request.getAttribute("subject");
	String message = (String)request.getAttribute("message");
	String attachmentName = (String) request.getAttribute("attachment_name");
	String attachmentPath = (String) request.getAttribute("attachment_path");
	Timestamp ts = (Timestamp)request.getAttribute("date");
    if (ts != null) {
        formattedDate = new java.text.SimpleDateFormat("dd MMM yyyy hh:mm a").format(new java.util.Date(ts.getTime()));
    }

%>
	
        <div class="imc1"id="main-content">
			<div class="content">
			<div class="message-box">
				<a class="back-btn" href="in">Back to Inbox</a>
				
				<div class="message-header">
					<h2><%= subject %></h2>
					<div class="meta-info">
						From: <strong><%= from1 %></strong><br/>
						Date: <%= formattedDate %>
					</div>
				</div>
				<div id="message-content">
					<div class="message-body">
						<%= message !=null ? message : ""%>
					</div>
				

<%   if(attachmentName != null){ 
%>
   <p>
      <i class="fas fa-paperclip"></i> 
      Attachment: 
      <a href="download?file=<%=attachmentName%>">Download</a>
   </p>				
		
			<div class = "attachment-preview">
				<iframe src = "va?file=<%=attachmentName%>"  width="100%" height="500px" style="border:1px solid #ccc; border-radius:8px;"></iframe>
			</div>
<% } %>	
				</div>
				<button type="submit" name="reply" value="reply" onclick="handlereply()" id="reply-btn">Reply</button>
				<div class="reply-box" id="reply-box" style="display: none;">
					<form action="co" method="post" id="replyForm" enctype="multipart/form-data" >
						<!-- hidden recipient + subject -->
						<input type="hidden" name="to" value="<%= from1 %>">
<%
							String subj = subject != null ? subject : "";
							if(!subj.startsWith("Re:")){
								subj = "Re: " + subj;
							}
%>
						<input type="hidden" name="subject" id="replysubject" value="<%=subj%>">
						<!-- reply text -->
						<textarea id="reply" name="reply"  placeholder="Write your message here..."></textarea>
						<!-- combined final -->
						<input type="hidden" name="message" id="message">
						<!-- readonly previous -->
						<p id="replydetails">On <%=formattedDate%>,<%=from1%> worte:</p><br>
						<pre id="previousMessage"><%= message !=null ? message : ""%></pre>
						
						
						
						<div class="btn-group">
							<button type="submit" name="action" value="send">Send</button>
						</div>
					</form>
				</div>
				
			</div>
		</div>
	</div>
</section>
</body>
<script>
	function handlereply(){
		const displayreplybox = document.getElementById("reply-box");
		displayreplybox.style.display = "block";
	}
	document.addEventListener("DOMContentLoaded", function(){
		const form = document.getElementById("replyForm");
		if(form){
			form.addEventListener("submit",(e)=>{
				
				
				let reply = document.getElementById("reply").value.trim();
				let replyde = document.getElementById("replydetails").innerText;
				let prev = document.getElementById("previousMessage").innerText;
				let combined = reply + "\n"+replyde+"\n"+prev;
				document.getElementById("message").value = combined;
			});
		}
	});

	// document.getElementById("replyForm").addEventListener("submit",(e)=>{
		// let subject = "<%= subject %>";
		// let finalSubject = addReplyPrefix(subject);
		// document.getElementById("replysubject").value = finalSubject;
		
		// let reply = document.getElementById("reply").value.trim();
		// let replyde = document.getElementById("replydetails").innerText;
		// let prev = document.getElementById("previousMessage").innerText;
		// let combined = reply + "\n\n"+replyde+"\n\n"+prev;
		// document.getElementById("message").value = combined;
	// });
</script>
</html>
