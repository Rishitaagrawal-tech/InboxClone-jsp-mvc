package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.MultipartConfig;

@MultipartConfig   // enables file uploads here too

public class ComposeServlet extends HttpServlet{
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		String user= (String)session.getAttribute("email");
		String saveas = request.getParameter("action");
		String messid = request.getParameter("id");
		
		String errormessage="";
		String recipient = "";
		String subject = "";
		String message = "";
		
		Part filePart = request.getPart("file");  
        String fileName = null; 
		String filePath = null;
		MailBean mb = new MailBean();
		
		if (filePart != null && filePart.getSize() > 0) {
				
			fileName = filePart.getSubmittedFileName();
			String baseName = fileName.substring(0, fileName.lastIndexOf(".")); // before dot
			String extension = fileName.substring(fileName.lastIndexOf("."));   // after dot
			
			String uploadPath = getServletContext().getRealPath("")+"upload"; 
			// Ensure upload folder exists
			File fileSaveDir = new File(uploadPath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}

			// Check duplicates
			File file = new File(uploadPath, fileName);
			int count = 1;
			while (file.exists()) {
				String newName = baseName + "(" + count + ")" + extension;
				file = new File(uploadPath, newName);
				count++;
			}

			// Final unique name
			String finalFileName = file.getName();
			filePart.write(file.getAbsolutePath());
			System.out.println("Saved file as: " + finalFileName);

			// Save in DB
			mb.setAttachmentName(finalFileName);
			mb.setAttachmentPath(file.getAbsolutePath());
		}
		
		
		
		

		
		mb.setSaveas(saveas);
		mb.setUser(user);
		
		boolean hasDraftId = messid != null && !messid.isEmpty();
		boolean isFromSubmitted = request.getParameter("to") != null;
		
		if(hasDraftId && !isFromSubmitted){

			mb.draftdetails(Integer.parseInt(messid));
			request.setAttribute("id", messid); 
			request.setAttribute("recipient",mb.getRecipient());
			request.setAttribute("subject",mb.getSubject());
			request.setAttribute("message",mb.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
			rd.forward(request,response);
			return;
		}
		if("cancel".equalsIgnoreCase(saveas)){
			response.sendRedirect("in");
			return;
		}
		
		if(isFromSubmitted){
			recipient = request.getParameter("to");
			subject = request.getParameter("subject");
			message = request.getParameter("message");
			
			mb.setRecipient(recipient);
			mb.setSubject(subject);
			mb.setMessage(message);
			
			if("send".equalsIgnoreCase(saveas)){
				String result = mb.addmail();
				if("Enter the receiver mail".equals(result)){
					errormessage = "Enter the receiver mail";
					request.setAttribute("errorMessage", errormessage);
					RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
					rd.forward(request,response);
				}else if("Success".equals(result)){
					response.sendRedirect("in");
				}
			}else if("draft".equalsIgnoreCase(saveas)){
				if(hasDraftId){
					String checkduplicate = mb.checkDuplicateDraft();
					if("This draft is already saved!".equals(checkduplicate)){
						errormessage = checkduplicate;
						request.setAttribute("errorMessage", errormessage);
						RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
						rd.forward(request,response);
					}else{
						mb.setRecipient(recipient);
						mb.setSubject(subject);
						mb.setMessage(message);
						mb.setUser(user);

						String updateDraft = mb.updatedraft(Integer.parseInt(messid));
						if("update draft".equals(updateDraft)){
							response.sendRedirect("dr");	
						}else{
							errormessage="error in updating";
							request.setAttribute("errorMessage", errormessage);
							RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
							rd.forward(request,response);
						}
					}
				}else{
					String checkduplicate = mb.checkDuplicateDraft();
					if("This draft is already saved!".equals(checkduplicate)){
						errormessage = checkduplicate;
						request.setAttribute("errorMessage", errormessage);
						RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
						rd.forward(request,response);						
					}else{
						String savedraft = mb.addmail();
						if("saved as draft".equals(savedraft)){
							response.sendRedirect("dr");
						}
					}
				}
			}
		}		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String messid = request.getParameter("id");
		if(messid != null && !messid.isEmpty()){
			MailBean mb = new MailBean();
			mb.draftdetails(Integer.parseInt(messid));
			request.setAttribute("id", messid);
			request.setAttribute("recipient", mb.getRecipient());
			request.setAttribute("subject", mb.getSubject());
			request.setAttribute("message", mb.getMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("compose.jsp");
		rd.forward(request,response);

	}
}