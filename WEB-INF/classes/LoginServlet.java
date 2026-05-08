package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet{
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String un = request.getParameter("email");
		String us = request.getParameter("pass");
		
		LoginBean mb = new LoginBean();
		
		mb.setEmail(un);
		mb.setPassword(us);
		
		String message = null;
		String email = mb.getEmail();
		String password = mb.getPassword();
		String result = mb.validate();
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()){
			request.setAttribute("message", "All fields are required!");
		}else{	
			if ("Success".equals(result)){
				if("yes".equals(request.getParameter("remember"))){
					Cookie emailCookie = new Cookie("email",email);
					Cookie passwordCookie = new Cookie("password",password);
					
					emailCookie.setMaxAge(7 * 24 * 60 * 60);
					passwordCookie.setMaxAge(7 * 24 * 60 * 60);
						
					response.addCookie(emailCookie);
					response.addCookie(passwordCookie);
				}	
				session.setAttribute("name",mb.getFirstname());
				session.setAttribute("email",mb.getEmail());
					
				response.sendRedirect("in");

						
			}else if("Wrong_password".equals(result)){
				message = "Password is wrong!";
				request.setAttribute("message",message);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request,response);
			}else if("no_user".equals(result)){
				
				message = "User does not exist. Please sign up!";
				request.setAttribute("message",message);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request,response);
			}else {
				message= "Error occurred during login!";
				request.setAttribute("message",message);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request,response);
			}
			
		}
		
		
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.sendRedirect("in");
	}
	

}
	