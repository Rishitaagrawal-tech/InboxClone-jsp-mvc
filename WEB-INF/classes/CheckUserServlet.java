package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CheckUserServlet extends HttpServlet{
		
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String query = request.getParameter("to");
		Boolean found = false;
		MailBean mb = new MailBean();
		
		PrintWriter out = response.getWriter();
		
		ResultSet result  = mb.checkusers(query);
		try{
		while (result.next()) {
			found = true;
			String email = result.getString("email");
			out.println(email);
		}
		if(!found){
			out.println("<p>No suggestions found.</p>");
		}
		}catch( SQLException e){}
	}
}