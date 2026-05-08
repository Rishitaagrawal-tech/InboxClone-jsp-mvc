package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class VeiwSendMailServlet extends HttpServlet{
		
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
			ResultSet rs = mb.veiwsenddetails(Integer.parseInt(mailId));
			if(rs != null){
				try{
					if(rs.next()){
						request.setAttribute("to1", rs.getString("to1"));
						request.setAttribute("subject", rs.getString("subject"));
						request.setAttribute("message", rs.getString("message"));
						request.setAttribute("date", rs.getTimestamp("date"));
						request.setAttribute("attachment_name", rs.getString("attachment_name"));
						request.setAttribute("attachment_path", rs.getString("attachment_path"));
						RequestDispatcher rd = request.getRequestDispatcher("veiwsendmail.jsp");
						rd.forward(request, response);
					}else{
						response.sendRedirect("se");
					}
				}catch(SQLException e){}
			}
		}
	}
}