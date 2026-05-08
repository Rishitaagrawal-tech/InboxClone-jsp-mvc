<%
	String errormessage= (String)request.getAttribute("errorMessage");
	String saveas = "";
	String recipient = (String)request.getAttribute("recipient");
	String subject = (String)request.getAttribute("subject");
	String message = (String)request.getAttribute("message");
	String user = (String)session.getAttribute("email");
		
%>

<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Compose Email - Gmail Clone</title>
  <link rel="stylesheet" href="./css/compose.css">
  <link rel="stylesheet" href="./css/inbox.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<section class="is1">
        <div class="isb1">
			<%@ include file="sidebar.jsp"%>
		</div>
		
        <div class="imc1"id="main-content">
			<div class="compose-container">
			<h2>New Message</h2>
			<% if (errormessage != null) { %> 
				<p style="color:red; text-align: center;">
					<%= errormessage %>
				</p>
			<% } %>

			<form action="co" method="post" enctype="multipart/form-data">
			  <input type="hidden" name="id" value="<%= request.getAttribute("id") != null ? request.getAttribute("id") : "" %>" />		  
			  <label for="to">To</label>
			  <input type="email" id="to" name="to" autocomplete="off" placeholder="Recipient's email"  onkeyup="ajaxFunction(this.value)" value="<%=recipient !=null ? recipient:""%>">
			  <div id="emailSuggestions" value="hi"></div>
			  
			  <label for="subject">Subject</label>
			  <input type="text" id="subject" name="subject"  placeholder="Email subject" required  value="<%=subject != null ? subject:""%>"/>

			  <label for="message">Message</label>
			  <textarea id="message" name="message"  placeholder="Write your message here..."><%= message !=null ? message : ""%></textarea>
			
			
			  <label class="attach-label">
                <i class="fas fa-paperclip"></i> Attach File/Photo
      			<input type="file" name="file">
			  </label>
			  
			  <div class="btn-group">
				<button type="submit" name="action" value="send">Send</button>
				<button type="submit" name="action" class="cancel" value="cancel">Cancel</button>
				<button type="submit" name="action" value="draft" id="draft-btn">Save as Draft</button>
			  </div>
			</form>
		  </div>
		</div>
</section>

<script>
	const email_to = document.getElementById("to");
	const emailSuggList = document.getElementById("emailSuggestions");
	function getXMLObject(){
		var xmlHttp = false;
		try{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")
		}catch(e){
			try{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")
			}catch(e2){
				xmlHttp = false;
			}
		}
		if (!xmlHttp && typeof XMLHttpRequest != 'undefined'){
			xmlHttp = new XMLHttpRequest();
		}
		return xmlHttp;
	}
	var xmlhttp = getXMLObject();
	
	function ajaxFunction(a){
		if(a.length>0){
			if(xmlhttp){
				xmlhttp.open("GET","cu?to="+a);
				xmlhttp.onreadystatechange = handleServerResponse;
				
				xmlhttp.send();
			}
		}else{
			emailSuggList.innerHTML="enter recipient email";
			emailSuggList.style.display = "block";
			email_to.style.border = "1px solid #ddd";
		}
	}
	function handleServerResponse(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
			console.log(xmlhttp.responseText);
			let emails = xmlhttp.responseText.split("\n");
			let html="";
			emails.forEach(email =>{
				html += `
						<div class="suggestion-item" 
							onclick="selectEmail('${email}')"
							style="padding: 5px; cursor: pointer; margin-left: 10px; width: 96%; border-bottom: groove;">
								${email}
						</div>`;
			});
			emailSuggList.innerHTML= html;
			emailSuggList.style.display = "block";
			email_to.style.border = "1px solid #ddd";			
		}
	}
	
	function selectEmail(email){
		email_to.value = email;
		email_to.style.border = "groove";
		emailSuggList.style.display = "none";
	}
	function resetfunction(){
		document.querySelector("form").reset();
	}
	
</script>
</body>
</html>
