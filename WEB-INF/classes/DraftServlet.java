package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DraftServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String[] selectmails = request.getParameterValues("selected");
		MailBean mb = new MailBean();
		if(selectmails != null && request.getMethod().equalsIgnoreCase("POST")) {
			mb.deleteDraft(selectmails);
		}
		doGet(request,response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		String checkemail = (String)session.getAttribute("email");
		MailBean mb = new MailBean();
		ResultSet rs = mb.draftmails(checkemail);
		request.setAttribute("result",rs);
		RequestDispatcher rd = request.getRequestDispatcher("draft.jsp");
		rd.forward(request,response);
	}
}