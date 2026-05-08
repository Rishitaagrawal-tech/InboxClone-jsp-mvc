package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class TrashServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		String checkemail = (String)session.getAttribute("email");
		MailBean mb = new MailBean();
		ResultSet rs = mb.tashmails(checkemail);
		request.setAttribute("result",rs);
		
		RequestDispatcher rd = request.getRequestDispatcher("tash.jsp");
		rd.forward(request,response);
	}
}