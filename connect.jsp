<%@ page import="java.sql.*" %>
<%! Connection con = null;%>
<%
    
    try {
        Class.forName("com.mysql.jdbc.Driver"); // Updated driver class
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "admin");
    } catch (Exception e) {
        out.println("Error connecting to the database: " + e.getMessage());
        e.printStackTrace();
    }
%>