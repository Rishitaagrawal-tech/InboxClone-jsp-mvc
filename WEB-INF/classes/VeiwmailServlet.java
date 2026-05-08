package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class VeiwmailServlet extends HttpServlet{
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("email");
		String mailId = request.getParameter("messid");
		if (mailId == null || mailId.isEmpty()) {
			response.sendRedirect("in"); // or show error
			return;
		}else{

			MailBean mb = new MailBean();
			mb.setUser(user);
			ResultSet rs = mb.veiwdetails(Integer.parseInt(mailId));
			if(rs != null){
				try{
					if(rs.next()){
						request.setAttribute("from1", rs.getString("from1"));
						request.setAttribute("subject", rs.getString("subject"));
						request.setAttribute("message", rs.getString("message"));
						request.setAttribute("date", rs.getTimestamp("date"));
						request.setAttribute("attachment_name", rs.getString("attachment_name"));
						request.setAttribute("attachment_path", rs.getString("attachment_path"));
						RequestDispatcher rd = request.getRequestDispatcher("veiwmail.jsp");
						rd.forward(request, response);
					}else{
						response.sendRedirect("in");
					}
				}catch(SQLException e){}
			}
		}
	}
}