package pack;
import java.sql.*;
public class LoginBean{
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String dob;
	
	

	Connection con = Connect.getConnect();
	ResultSet rs;
	PreparedStatement ps;
	
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setPassword(String pass){
		this.password = pass;
	}
	
	public void setDob(String dob){
		this.dob = dob;
	}
	
	public String getFirstname(){
		return firstname;
	}
	public String getLastname(){
		return lastname;
	}
	public String getEmail(){
		return email;
	}
	public String getPassword(){
		return password;
	}
	public String getDob(){
		return dob;
	}
	public boolean validUser()throws SQLException{
		boolean valid = false;
		try{
			ps = con.prepareStatement("SELECT email FROM users WHERE email = ? ");
			ps.setString(1,email);
			rs = ps.executeQuery();
			if(rs.next()==true){
				valid = true;
			}
			return valid;
		}finally{
			try { if(rs != null) rs.close(); } catch(Exception e) {}
			try { if(ps != null) ps.close(); } catch(Exception e) {}
		}
	
	}
	
	public boolean registerUser() {
		boolean registered = false;
		if(firstname != null && lastname != null && email!=null && password!=null && dob!=null){
			try{
				ps = con.prepareStatement("INSERT INTO users(firstname,lastname,dob,email,password) VALUES(?,?,?,?,?)");
					
				ps.setString(1,firstname);
				ps.setString(2,lastname);
				ps.setString(3,dob);
				ps.setString(4,email);
				ps.setString(5,password);
				
				int result = ps.executeUpdate();
				if(result>0){
					registered = true;
				}
				ps.close();
			}catch(SQLException e){}
		}
		return registered;
	}
	
	public String validate(){
		String result ="";
		try{
			
			ps = con.prepareStatement("SELECT firstname FROM users WHERE email = ? AND password=?");
			ps.setString(1,email);
			ps.setString(2,password);
			rs=ps.executeQuery();
			
			if(rs.next()){
				firstname = rs.getString("firstname");
				 result = "Success";
			}else{
				ps = con.prepareStatement("SELECT password FROM users WHERE email=?");
				ps.setString(1,email);
				rs = ps.executeQuery();
				
				if(rs.next()){
					result = "Wrong_password";
				}else{
					result = "no_user";
				}
			}
			
		}catch (Exception e) {}
		return result;
	}
}