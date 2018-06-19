package com.fillingstation.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.util.DBUtil;

import javax.mail.internet.InternetAddress;
@Path("/mail")
/*
 * @Stateless annotated bean is an EJB which by default provides Container-Managed-Transactions. 
 * CMT will by default create a new transaction if the client of the EJB did not provide one.
 */

@Stateless
public class AttendanceRegulationMail {


	

			@Resource(name="java:jboss/mail/Gmail")
			private Session session;

			@POST
			@Path("/AttendanceRegularizationMail")
		    public Response sendmail(EmployeeAttendanceJSON details) throws SQLException {
				  
				  Connection connection=null;
				  connection =DBUtil.getDBConnection();
				  String date="-";
				  String alreadyExits=IQueryConstants.REQUEST_ALREADY_EXITS ;
					
				  	PreparedStatement exits = connection.prepareStatement(alreadyExits);
				  	
				  	exits.setString(1,details.getDate());
				  	exits.setString(2,details.getCompanyId());
				  	exits.setString(3,details.getEmployeeId());
				  	ResultSet rs0=exits.executeQuery();
			        
			        while(rs0.next())
			        {
			        	date=rs0.getString("CheckInTime");
			        }
			        System.out.println("date check"+date);
			        if(date.equals("-")){
				  String querySelect=IQueryConstants.REPORTING_MANAGER_ID ;
					
				  	PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
					preparedStmt.setString(1,details.getEmployeeId());
					preparedStmt.setString(2,details.getCompanyId());
					
					
					ResultSet rs=preparedStmt.executeQuery();
			        
			        String repmanempid = null;
			        String repmanempemailid=null;
			        
			        while(rs.next())
			        {
			        	repmanempid = rs.getString("ReportingManagerId");
			        	System.out.println("repmanager" + "\t" + repmanempid) ;
			        }
			        
			        
			        	if(repmanempid!=null) {
			        		String querySelect0=IQueryConstants.ATTENDANCE_REG_INSERT;
							PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
							
							preparedStmt0.setString(1,details.getCompanyId());
							preparedStmt0.setString(2,details.getEmployeeId());
							preparedStmt0.setString(3,repmanempid);
							preparedStmt0.setString(4,details.getCheckInTime());
							preparedStmt0.setString(5,details.getCheckOutTime());
							preparedStmt0.setString(6,details.getDate());
							
							preparedStmt0.executeUpdate();
							
			        	String querySelect1=IQueryConstants.REPORTING_MANAGER_EMAILID  ;
						PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
						preparedStmt1.setString(1,details.getCompanyId());
						preparedStmt1.setString(2,repmanempid);
						
						ResultSet rs1=preparedStmt1.executeQuery();
						
						while(rs1.next())
							
				        {
							repmanempemailid = rs1.getString("EmailID");
				        	System.out.println("repmanager emailid"   + "\t"+ repmanempemailid) ;
				        }
						
						 
				  String subject="Requesting For Attendance Regularization";
				  String  to = repmanempemailid;
				 //int valid=EmployeeLogic.vaildmailid(json);
				   
			      String body="	\n" + 
				  		" \n" + 
				  		"Hello <b>" + to +",</b>"+
				  	     "<br><br>Employee Id "+ details.getEmployeeId() +"\t is requesting for Attendance Regularization Details are given below"+
				  	     "<br><strong>EmployeeId </strong>:&emsp;" + details.getEmployeeId() +
				  	     "<br><strong>Date </strong>:&emsp;" + details.getDate() +
				  	     "<br><strong>Check In Time </strong>:&emsp;" + details.getCheckInTime() +
				  	     "<br><strong>Check Out Time </strong>:&emsp;" + details.getCheckOutTime() +
				  	     "<br><br><strong>To Approve Or Reject This Request Click the below link \n "+
				  	    " <br><strong><a href=\'http://localhost:3000/EmployeeAttendanceRequest/'>AttendanceRegularizationRequest</a>"+
				  	   /*  "\n http://localhost:3000/AttendanceRegularisationAuthorize/?"+details.getEmployeeId()+"?"+details.getCompanyId()+"?"+
				  	     details.getDate()+"?"+details.getCheckInTime()+"?"+details.getCheckOutTime()+"\n"+
				  	    "\n http://localhost:3000/AttendanceRegularisationAuthorize/?"+details.toString()+*/
				  	     "<br><br>\nThank you,\n\n" + 
				  		"<br> <strong>ThroughApps</strong>" + 
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
					 EmployeeLogic.AuditReport(repmanempid, details.getEmployeeId(), "Attendance Regularization Request",details.getCompanyId());

				}catch(MessagingException e) {
					System.out.println("Cannot Send Mail"+e);
				
				}
			  }else {
				  System.out.println("EmployeeID"+details.getEmployeeId()+" have not Assigned any Reporting Manger..Please Assign first");
				details.setStatus("NOT_ASSIGNED");
			  }
			        }
			        else{
			        	System.out.println("Request Already Exits on the Date"+details.getDate());
			        	details.setStatus("ALREADY_EXITS");
			        }
			    return Response.status(200).entity(details).build();

		
	}
	}


		


	
	
	

