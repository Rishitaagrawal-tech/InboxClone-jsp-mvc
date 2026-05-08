package pack;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.text.*;
public class MailBean{
	private String recipient ;
	private String subject ;
	private String message ;
	private String user;
	private String displayDate = "—";
	private String saveas;
	private int id;
	private String attachmentName;
	private String attachmentPath;
	Connection con = Connect.getConnect();
	PreparedStatement ps ;
	ResultSet rs;
	String result="";
	// setters
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath; 
	}
	public void setAttachmentName(String attachmentName) { 
		this.attachmentName = attachmentName; 
	}
	public void setRecipient(String recipient){
		this.recipient = recipient;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void setUser(String user){
		this.user =user;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setSaveas(String saveas){
		this.saveas = saveas;
	}
	public void setDisplayDate(String date){
		this.displayDate = date;
	}
	
	// getters
	public String getAttachmentPath(){
		return attachmentPath;
	}
	
	public String getAttachmentName(){
		return attachmentName;
	}
	public String getSaveas(){
		return saveas;
	}

	public int getId(){
		return id;
	}
	public String getUser(){
		return user;
	}
	public String getMessage(){
		return message;
	}
	public String getSubject(){
		return subject;
	}
	public String getRecipient(){
		return recipient;
	}
	public String getDisplayDate(){
		return displayDate;
	}
	// main methods
	public String addmail(){
		String result ="";
		try {
			if("send".equalsIgnoreCase(saveas)){
				if(recipient == null || recipient.trim().isEmpty()){
					return "Enter the receiver mail";
				}else{
					ps = con.prepareStatement("SELECT email FROM users WHERE email=?");
					ps.setString(1, recipient);
					rs = ps.executeQuery();
					if(rs.next()){
						ps.close();
						ps = con.prepareStatement("INSERT INTO senddetails(to1,from1,subject,message,date,receiver_status,sender_status,attachment_name, attachment_path)VALUES(?,?,?,?,NOW(),?,?,?,?)");
						ps.setString(1, recipient);
						ps.setString(2, user);
						ps.setString(3, subject);
						ps.setString(4, message);
						ps.setString(5, "active");
						ps.setString(6,"active");
						ps.setString(7, getAttachmentName());  
						ps.setString(8, getAttachmentPath()); 
						
						ps.executeUpdate();
						result = "Success";
					}else { 
						result ="This email is not registered!";
					}
				}
			}else if("draft".equalsIgnoreCase(saveas)){
				ps = con.prepareStatement("INSERT INTO messdetails(to1,from1,subject,message,date,attachment_name, attachment_path)VALUES(?,?,?,?,NOW(),?,?)");
				ps.setString(1, recipient);
				ps.setString(2, user);
				ps.setString(3, subject);
				ps.setString(4, message);
				ps.setString(5, getAttachmentName());  
				ps.setString(6, getAttachmentPath()); 		
					
				ps.executeUpdate();
				result = "saved as draft";
				
			}
		}catch(SQLException e){
		}finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch(SQLException e){}
		}
		return result;	
	}
	
	public String updatedraft(int id){
		String result="";
		try{
			// Keep existing attachment if no new one uploaded
			String currentAttachmentName = getAttachmentName();
			String currentAttachmentPath = getAttachmentPath();

			if(currentAttachmentName == null || currentAttachmentName.isEmpty() || 
			   currentAttachmentPath == null || currentAttachmentPath.isEmpty()){
				// fetch old values from DB
				PreparedStatement ps2 = con.prepareStatement("SELECT attachment_name, attachment_path FROM messdetails WHERE messid=?");
				ps2.setInt(1,id);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()){
					currentAttachmentName = rs2.getString("attachment_name");
					currentAttachmentPath = rs2.getString("attachment_path");
				}
				rs2.close();
				ps2.close();
			}
			ps = con.prepareStatement("UPDATE messdetails SET to1=?, subject=?, message=?, date=NOW(), attachment_name=?, attachment_path=? WHERE messid=?");
			ps.setString(1,recipient != null ? recipient:"");
			ps.setString(2, user!=null?user:"");
			ps.setString(3,subject != null ? subject:"");
			ps.setString(4,message!= null ? message : "");
			ps.setString(5, getAttachmentName()!= null ? attachmentName : "");  
			ps.setString(6, getAttachmentPath()!= null ? attachmentPath : ""); 
			ps.setInt(7,id);
			
			ps.executeUpdate();
			result = "update draft";
			ps.close();
		}catch(SQLException e){
		}
		return result;
	}
	
	public String checkDuplicateDraft(){
		String result="";
		try{
			ps = con.prepareStatement("SELECT to1,from1,subject,message FROM messdetails WHERE to1=? AND from1=? AND subject=? AND message=?");
			ps.setString(1, recipient);
			ps.setString(2, user);
			ps.setString(3, subject);
			ps.setString(4, message);
			rs = ps.executeQuery();
				
			if(rs.next()){
				result = "This draft is already saved!";
			}
			rs.close();
			ps.close();
		}catch(SQLException e){
			System.out.print(e);
		}
		return result;
	}
	public void draftdetails(int id){
		try{
			ps = con.prepareStatement("SELECT messid,to1,subject,message,date FROM messdetails WHERE messid=?");
			ps.setInt(1,id);
			rs = ps.executeQuery();
			if(rs.next()){
				recipient = rs.getString("to1");
				subject = rs.getString("subject");
				message = rs.getString("message");
			}
			rs.close();
			ps.close();
		}catch(SQLException e){
		}
	}
	public void reciver_deletemail(String[] selected){
		try{
			ps = con.prepareStatement("UPDATE senddetails SET receiver_status = 'tash' WHERE messid=? AND to1=?");
			for(int i=0;i<selected.length;i++){
				ps.setInt(1,Integer.parseInt(selected[i]));
				ps.setString(2,user);
				ps.executeUpdate();
			}
		}catch(Exception e){
		}
	}
	public ResultSet inboxMails(String user){
	
		try{
			ps = con.prepareStatement("SELECT  * FROM senddetails WHERE to1 = ? AND receiver_status = 'active' ORDER BY date DESC");

			ps.setString(1,user);
			rs = ps.executeQuery();
			
		}catch(Exception e){}
		return rs;
	}
	
	public String formatdate(Timestamp ts){
		if(ts == null) return "";
		
		Date receivedDate = new Date(ts.getTime());
		Calendar now = Calendar.getInstance();
		Calendar received = Calendar.getInstance();
		received.setTime(receivedDate);
				
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
		SimpleDateFormat dateMonthYearFormat = new SimpleDateFormat("dd MMM yyyy");
			
		boolean isToday =now.get(Calendar.YEAR) == received.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == received.get(Calendar.DAY_OF_YEAR);

		Calendar weekAgo = Calendar.getInstance();
		weekAgo.add(Calendar.DAY_OF_YEAR, -7);
		boolean isWithinWeek = received.after(weekAgo);
				
		if (isToday)
		{
			displayDate = timeFormat.format(receivedDate);
		} 
		else if (isWithinWeek) 
		{
			displayDate = dayFormat.format(receivedDate);
		} else 
		{
			displayDate = dateMonthYearFormat.format(receivedDate);
		}
		return displayDate;
	}
	
	public void sender_deletemails(String[] selectmails){
		try{
			ps = con.prepareStatement("UPDATE senddetails SET sender_status = 'tash' WHERE messid=? AND from1=?");
			for(int i=0;i<selectmails.length;i++){
				ps.setInt(1,Integer.parseInt(selectmails[i]));
				ps.setString(2,user);
				ps.executeUpdate();
			}
		}catch(Exception e){
		}
	}
	
	public ResultSet sentMails(String checkemail){

		try{
		
			ps = con.prepareStatement("SELECT * FROM senddetails WHERE from1 = ? And sender_status = 'active' ORDER BY date DESC");

			ps.setString(1,checkemail);
			rs = ps.executeQuery();
		}catch(Exception e){}
		return rs;
	}
	
	
	public ResultSet veiwdetails(int id){
		try{
			ps = con.prepareStatement("SELECT * FROM senddetails WHERE messid=? AND to1=?");
			ps.setInt(1,id);
			ps.setString(2,user);
			rs = ps.executeQuery();
			
		}catch (Exception e) {}
		return rs;
	}
	public ResultSet veiwsenddetails(int id){
		try{
			ps = con.prepareStatement("SELECT * FROM senddetails WHERE messid=? AND from1=?");
			ps.setInt(1,id);
			ps.setString(2,user);
			rs = ps.executeQuery();
			
		}catch (Exception e) {}
		return rs;
	}
	
	public void deleteDraft(String[] selectmails){
		try{
			ps = con.prepareStatement("DELETE FROM messdetails WHERE messid=?");
			for(int i=0;i<selectmails.length;i++){
				ps.setInt(1,Integer.parseInt(selectmails[i]));
				ps.executeUpdate();
			}
		}catch(Exception e){
		}
	}
	
	public ResultSet draftmails(String checkemail){

		try{
			ps = con.prepareStatement("SELECT * FROM messdetails WHERE from1 = ? ORDER BY date DESC");

			ps.setString(1,checkemail);
			rs = ps.executeQuery();
			
		}catch(Exception e){}
		return rs;
	}
	
	public ResultSet checkusers(String query){
		
		try{
			if (query != null && !query.trim().isEmpty()) {
				PreparedStatement ps = con.prepareStatement("SELECT email FROM users WHERE email LIKE ? LIMIT 10");
				ps.setString(1, query + "%");
				rs = ps.executeQuery();
			}
		}catch(Exception e){
		}
		return rs;
	}
	
	public ResultSet tashmails(String checkemail){
		try{
			ps = con.prepareStatement("SELECT * FROM senddetails WHERE (to1 = ? And receiver_status ='tash' ) OR (from1=? AND sender_status ='tash') ORDER BY date DESC");
			ps.setString(1,checkemail);
			ps.setString(2,checkemail);
			rs = ps.executeQuery();
		}catch(Exception e){} 
		return rs;
	}
	
	
}