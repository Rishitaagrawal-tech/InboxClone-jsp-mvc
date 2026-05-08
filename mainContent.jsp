<%@ include file ="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Email</title>
  <link rel="stylesheet" href="./css/inbox.css">
  <link rel ="stylesheet" href="./css/draft.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.0/css/all.min.css" integrity="sha512-DxV+EoADOkOygM4IR9yXP8Sb2qwgidEmeqAEmDKIOfPRQZOWbXCzLC6vjbZyy0vPisbH2SyW27+ddLVCN+OMzQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<style>
		#main-content .content {
			padding: 20px;
			background-color: #f9f9f9;
			min-height: 100vh;
		}

		#main-content h2 {
			color: #333;
			margin-bottom: 20px;
			margin-left: -15px;
		}

		#main-content table {
			width: 103%;
			border-collapse: collapse;
			background-color: #fff;
			box-shadow: 0 0 10px rgba(0,0,0,0.05);
			border-radius: 8px;
			overflow: hidden;
			margin-left: -20px;
		}

		#main-content th, #main-content td {
			padding: 12px 15px;
			border-bottom: 1px solid #eee;
			text-align: left;
			color: #555;
		}

		#main-content th {
			background-color: #f1f1f1;
			font-weight: bold;
		}

		#main-content tr:hover {
			background-color: #f5f5f5;
		}

		#main-content th:first-child, 
		#main-content td:first-child {
			width: 40px;
		}

		#main-content th:last-child, 
		#main-content td:last-child {
			width: 120px;
			text-align: right;
		}

		#main-content .delete-btn {
			margin-top: 15px;
			background-color: #d9534f;
			color: white;
			padding: 10px 16px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}

		#main-content .delete-btn:hover {
			background-color: #c9302c;
		}

	</style>
</head>
<body>
<section class="is1">
        <div class="isb1">
			<%@ include file="sidebar.jsp"%>
		</div>
		
        <div class="imc1"id="main-content">
			<div class="draft-container">
				<i class="fa-solid fa-rotate" onclick="refresh()" style="cursor:pointer; transition: color 0.3s ease; font-size=24px" id="refresh-btn"></i>
				<div class = "content">
				<h2>Inbox</h2>

				<form method="post" action="in">
					<table>
						<thead>
							<tr>
							<th><input type="checkbox" onclick="toggleSelectAll(this)" id="checkbox"  /></th>
							<th>From</th>
							<th>Subject</th>
							<th>Message</th>
							<th>Date</th>
							<th>Attachment</th>
						</tr>
					</thead>
				<tbody>
<jsp:useBean id="mb" class="pack.MailBean"/>
<% 
	String messg ="";

	ResultSet rs  = (ResultSet)request.getAttribute("result");

	if(rs==null){
%>
			<tr><td colspan="5">No Mails.</td></tr>
<%
	}else{
		boolean hasData = false;
		while(rs.next()){
			hasData = true;
			Timestamp ts = rs.getTimestamp("date");
            String displayDate = mb.formatdate(ts);
            messg = rs.getString("message");
			String attachmentName = rs.getString("attachment_name");
%>			
			
            <tr class="card" id="card" style="cursor:pointer;">
                <td><input type="checkbox" name="selected" value="<%= rs.getInt("messid") %>" /></td>
                <td class="sender" onclick="window.location='ve?messid=<%= rs.getInt("messid") %>'"><%= rs.getString("from1") %></td>
                <td onclick="window.location='ve?messid=<%= rs.getInt("messid") %>'"><%= rs.getString("subject") %></td>
                <td onclick="window.location='ve?messid=<%= rs.getInt("messid") %>'"><%= messg.length() > 20 ? messg.substring(0,20) + "...." : messg %></td>
                
				<td onclick="window.location='ve?messid=<%= rs.getInt("messid") %>'"><%= displayDate %></td>
				<td onclick="window.location='ve?messid=<%= rs.getInt("messid") %>'">
					<%if(attachmentName != null && !attachmentName.isEmpty()){ %>
						<i class="fas fa-paperclip" style="margin-left: 5px;"></i>
					<%}%>
				</td>
            </tr>
<%
		} if(!hasData){
%>
				<tr><td colspan="5">No Mails.</td></tr>
<%			
		}
		
	}
%>

		</tbody>
		</table>
		<div id="pagination" style="margin-top:20px; text-align:center;"></div>
		<button type="submit" class="delete-btn">Delete Selected</button>
		</form>
		</div>
		</div>
</div>		
    </section>
  <script>
	let rowsPerPage = 4;
	let totalRows;
	let currentPage =1;
	let totalPages;
	let rows;
	function refresh(){
		const refreshbtn = document.getElementById("refresh-btn");
		refreshbtn.style.color = "aqua"; 
		refreshbtn.classList.add("spin");
		
		setTimeout(() => {
			location.reload();
		}, 500);
	}
    function toggleSelectAll(master) {
        const checkboxes = document.getElementsByName("selected");
        for (let i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = master.checked;
        }
    }
	function searchMails(){
		const input = document.getElementById("search").value.toLowerCase();
		let rows = document.getElementsByClassName("card");
		let visibleCount = 0;
			
		for(let i=0;i< rows.length;i++){
			let sender = rows[i].getElementsByClassName("sender")[0].innerText.toLowerCase();
			if(sender.includes(input)){
				rows[i].style.display = "";
				visibleCount++;
			}else{
				rows[i].style.display = "none";
			}
		}
		totalRows = visibleCount;
		currentPage = 1;
		rowsPerPage = 5;
		updatePagination();
			
	}
	
	
	const pagination = document.getElementById("pagination");
	document.addEventListener("DOMContentLoaded", function() {
		rows = document.getElementsByClassName("card");
		totalRows = rows.length;
		updatePagination();
	});
	function updatePagination(){
		totalPages = Math.ceil(totalRows / rowsPerPage);
		showPage(currentPage);
		renderSlider();
	}
	function showPage(page) {
		let start = (page - 1) * rowsPerPage;
		let end = start + rowsPerPage;
		for (let i = 0; i < totalRows; i++) {
			rows[i].style.display = (i >= start && i < end) ? "table-row" : "none";
		}
	}
	function renderSlider() {
		pagination.innerHTML = "";

		let prevBtn = document.createElement("button");
		prevBtn.innerText = "Prev";
		prevBtn.disabled = currentPage === 1;
		prevBtn.onclick = function() {
			if (currentPage > 1) {
				currentPage--;
				showPage(currentPage);
				renderSlider();
			}
		};

		let nextBtn = document.createElement("button");
		nextBtn.innerText = "Next";
		nextBtn.disabled = currentPage === totalPages;
		nextBtn.onclick = function() {
			if (currentPage < totalPages) {
				currentPage++;
				showPage(currentPage);
				renderSlider();
			}
		};
		if(totalPages == 0){
			totalPages = 1;
		}
		let info = document.createElement("span");
		info.innerText = "Page "+ currentPage +" of "+totalPages;
		console.log(currentPage);
		console.log(totalPages);
		info.style.margin = "0 10px";
			
		pagination.appendChild(prevBtn);
		pagination.appendChild(info);
		pagination.appendChild(nextBtn);
	}

    </script>
</body>

</html>