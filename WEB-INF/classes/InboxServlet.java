package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class InboxServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		
		HttpSession session = request.getSession();
		String checkemail = (String)session.getAttribute("email");
		String[] selectmails = request.getParameterValues("selected");

		MailBean mb = new MailBean();
		mb.setUser(checkemail);
		if(selectmails != null && request.getMethod().equalsIgnoreCase("POST")) {
			mb.reciver_deletemail(selectmails);
			
		}
		doGet(request,response);
		

	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		String checkemail = (String)session.getAttribute("email");
		MailBean mb = new MailBean();
		ResultSet rs = mb.inboxMails(checkemail);
		
		request.setAttribute("result",rs);
		
		RequestDispatcher rd = request.getRequestDispatcher("inbox.jsp");
		rd.forward(request,response);
	}
}