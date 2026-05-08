package pack;
import java.sql.*;
public class Connect{
	static Connection con;
	
	public static Connection getConnect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin", "root", "admin");
		} catch (Exception e) {
		}
		return con;
	}
	
}