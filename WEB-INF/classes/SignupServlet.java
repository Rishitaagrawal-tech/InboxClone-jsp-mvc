package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SignupServlet extends HttpServlet{
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String message;
		String fn = request.getParameter("firstname");
		String ln = request.getParameter("lastname");
		String rmail = request.getParameter("email");
		String pass = request.getParameter("pass");
		String rdob = request.getParameter("dob");
		PrintWriter out = response.getWriter();
		
		LoginBean mb = new LoginBean();
		mb.setFirstname(fn);
		mb.setLastname(ln);
		mb.setEmail(rmail);
		mb.setPassword(pass);
		mb.setDob(rdob);
		
		try{
			boolean valid = mb.validUser();
			boolean register = mb.registerUser();
			if(valid){
				message = "User already exist";
				RequestDispatcher rd = request.getRequestDispatcher("Signup.jsp");
				rd.include(request,response);
				out.println("<script>");
				out.println("document.getElementById('message').textContent = '" + message + "';");
				out.println("document.getElementById('message').style.color = 'red';");
				out.println("</script>");
			}
			if(register){
				request.setAttribute("username",fn);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}else{
				out.println("Error inserting user!");
			}
			
		}catch( SQLException e){}
	}
}