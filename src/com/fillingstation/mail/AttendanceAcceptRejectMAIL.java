package com.fillingstation.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.json.EmployeeRequestJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.util.DBUtil;
@Path(value="/SupervisorAuthority")
public class AttendanceAcceptRejectMAIL {

	/*
	 * Function for Attendance Regulation request Accept
	 */

	@Resource(name="java:jboss/mail/Gmail")
	private Session session;

	@POST
	 @Produces(value="application/json" )
    @Path(value="/AttendanceRegulationAccept")
    @Consumes(value="application/json")
    public Response AcceptAttendancerequest(EmployeeAttendanceJSON details) throws SQLException {
	
		
	
		
			System.out.println("Attendance Regulation is Accepted for..."+details.getEmployeeId());	
				Connection connection=null;
				
				try {
					connection=DBUtil.getDBConnection();
					String querySelect=IQueryConstants.EMP_REGULATION;
					PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
					preparedStmt.setString(1,details.getCheckInTime());
					preparedStmt.setString(2,details.getCheckOutTime());
					preparedStmt.setString(3,details.getEmployeeId());
					preparedStmt.setString(4,details.getDate());
					preparedStmt.setString(5,details.getCompanyId());
				    
					preparedStmt.executeUpdate();
					
					EmployeeLogic.TotalWorkCalculation(details,connection);
					
					String empemailid=null;
					
		        	String querySelect1=IQueryConstants.REPORTING_MANAGER_EMAILID  ;//Same Query For Retrieving Employee Mail Id
					PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
					preparedStmt1.setString(1,details.getCompanyId());
					preparedStmt1.setString(2,details.getEmployeeId());
					
					ResultSet rs1=preparedStmt1.executeQuery();
					
					while(rs1.next())
						
			        {
						empemailid = rs1.getString("EmailID");
			        	System.out.println("Employee emailid"   + "\t"+ empemailid) ;
			        }
					
					 
					  String subject=" Attendance Regulation Request Accpeted";
					  String  to = empemailid;
					 //int valid=EmployeeLogic.vaildmailid(json);
					   
				      String body="	\n" + 
					  		" \n" + 
					  		"Hello <b>" + to +",</b>"+
					  	     "<br><br>Your Attendance Regularization request has been Accepted by your Reporting Manager\n "
					  		+ "<br><h4><b>Details About the Request</b></h4>"+ 
					  		"<br><strong>EmployeeId </strong>:&emsp;" + details.getEmployeeId() +
					  	     "<br><strong>Date </strong>:&emsp;" + details.getDate() +
					  	     "<br><strong>Check In Time </strong>:&emsp;" + details.getCheckInTime() +
					  	     "<br><strong>Check Out Time </strong>:&emsp;" + details.getCheckOutTime() +
					  	      "<br>These Details has been Updated"+
					  	    "<br><br>Thank you,\n\n" + 
					  		" <br><strong>ThroughApps</strong>" + 
					  		" 	\n" + 
					  		" \n" + 
					  		"	\n" ;
					System.out.println("Mail for Attendance Regularization was sent succesfully");
					try {
		    	        MimeMessage message=new MimeMessage(session);
						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
						message.setSubject(subject);
						message.setContent(body, "text/html");
						Transport.send(message);
						 EmployeeLogic.AuditReport(details.getSuperiorId(), details.getEmployeeId(), "Attendance Regularization Accepted",details.getCompanyId());

					}catch(MessagingException e) {
						System.out.println("Cannot Send Mail"+e);
					
					}
					//Query sets Request Status 1 (indicates Accepted)
					
					System.out.println("Update Accepted Status in the Request  table");
					String req_status=IQueryConstants.ATTENDANCE_REQUEST_ACCEPT_STATUS;
					PreparedStatement preparedStmt0=connection.prepareStatement(req_status);
					preparedStmt0.setString(1,details.getCheckInTime());
					preparedStmt0.setString(2,details.getCheckOutTime());
					preparedStmt0.setString(3,details.getDate());
					preparedStmt0.setString(4,details.getCompanyId());
					preparedStmt0.setString(5,details.getEmployeeId());
					preparedStmt0.setString(6,details.getReportingMangerId());
					preparedStmt0.executeUpdate();
					/*details.setEmployeeId(details.getReportingMangerId());
					result=EmployeeLogic.EmployeeRequest(details);
					*/connection.close();
			        } catch (Exception e) {
					e.printStackTrace();
				  }finally {
					DBUtil.closeConnection(connection);
				    }
				
			
			
	System.out.println("completed the time regulation changes for the entered employeeid for the ...");	
	return Response.status(200).entity(details).build();
	}

	
	
	
	/*
	 * Function for Attendance Regulation request Accept
	 */

	@Resource(name="java:jboss/mail/Gmail")
	private Session sessionReject;

	@POST
	 @Produces(value="application/json" )
    @Path(value="/AttendanceRegulationReject")
    @Consumes(value="application/json")
    public Response RejectAttendancerequest(EmployeeAttendanceJSON details) throws SQLException {
	
		
		
			System.out.println("Attendance Regulation is Rejected for..."+details.getEmployeeId());	
				Connection connection=null;
				
				try {
					connection=DBUtil.getDBConnection();
					
					
					String empemailid=null;
					
		        	String querySelect1=IQueryConstants.REPORTING_MANAGER_EMAILID  ;//Same Query For Retrieving Employee Mail Id
					PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
					preparedStmt1.setString(1,details.getCompanyId());
					preparedStmt1.setString(2,details.getEmployeeId());
					
					ResultSet rs1=preparedStmt1.executeQuery();
					
					while(rs1.next())
						
			        {
						empemailid = rs1.getString("EmailID");
			        	System.out.println("Employee emailid"   + "\t"+ empemailid) ;
			        }
					
					 
					  String subject=" Attendance Regulation Request Rejected";
					  String  to = empemailid;
					 //int valid=EmployeeLogic.vaildmailid(json);
					   
				      String body="	\n" + 
					  		" \n" + 
					  		"Hello <b>" + to +",</b>"+
					  	     "<br><br>Your Attendance Regularization request has been Rejected by your Reporting Manager\n "
					  	  + "<br><h4><b>Details About the Request</b></h4>"+ 
					  		"<br><strong>EmployeeId </strong>:&emsp;" + details.getEmployeeId() +
					  	     "<br><strong>Date </strong>:&emsp;" + details.getDate() +
					  	     "<br><strong>Check In Time </strong>:&emsp;" + details.getCheckInTime() +
					  	     "<br><strong>Check Out Time </strong>:&emsp;" + details.getCheckOutTime() +
					  	     "<br><br>Please Contact your Reporting Manager for Further Details"+
					  	     "<br><br>Thank you,\n\n" + 
					  		" <br><strong>ThroughApps</strong>" + 
					  		" 	\n" + 
					  		" \n" + 
					  		"	\n" ;
					System.out.println("Mail for Attendance Regularization was sent succesfully");
					try {
		    	        MimeMessage message=new MimeMessage(sessionReject);
						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
						message.setSubject(subject);
						message.setContent(body, "text/html");
						Transport.send(message);
						 EmployeeLogic.AuditReport(details.getSuperiorId(), details.getEmployeeId(), "Attendance Regularization Rejected",details.getCompanyId());

					}catch(MessagingException e) {
						System.out.println("Cannot Send Mail"+e);
					
					}
					System.out.println("Update Rejected Status in the Request  table");
					//Query sets Request Status 2 (indicates Rejected)
					String deleteData=IQueryConstants.ATTENDANCE_REQUEST_REJECT_STATUS;
					PreparedStatement preparedStmt0=connection.prepareStatement(deleteData);
					preparedStmt0.setString(1,details.getCheckInTime());
					preparedStmt0.setString(2,details.getCheckOutTime());
					preparedStmt0.setString(3,details.getDate());
					preparedStmt0.setString(4,details.getCompanyId());
					preparedStmt0.setString(5,details.getEmployeeId());
					preparedStmt0.setString(6,details.getReportingMangerId());
					preparedStmt0.executeUpdate();
					/*details.setEmployeeId(details.getReportingMangerId());
					result=EmployeeLogic.EmployeeRequest(details);
					*/connection.close();
			        } catch (Exception e) {
					e.printStackTrace();
				  }finally {
					DBUtil.closeConnection(connection);
				    }
				
			
			
	System.out.println("completed the time regulation changes for the entered employeeid for the ...");	
	return Response.status(200).entity(details).build();
	}


	
	
	
}
