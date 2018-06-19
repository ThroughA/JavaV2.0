
package com.fillingstation.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.logic.EmployeeLeaveLogic;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.util.DBUtil;

@Path(value="/employeeleaverequest")
public class LeaveRequestMail {
	@Resource(name="java:jboss/mail/Gmail")
	private Session session;
	
	
	/*
		 * API FOR GETTING LEAVE Days
		 */
		@POST
		@Path("/getleavedays")
		@Consumes(value= {"application/json"})
		public Response getleavedays(EmployeeLeaveJSON leave) {
			
			System.out.println("getting days for \t"+leave.getLeaveType());
			EmployeeLeaveJSON leavedays=new EmployeeLeaveJSON();
			String days=EmployeeLeaveLogic.GetDays(leave);
			leavedays.setDay(days);
			System.out.println("completed getting days for \t"+leave.getLeaveType());
			
	      return Response.status(200).entity(leavedays).build();
			
			
		}


	/*
	 * API FOR getting leave type
	 */
	
		@POST
		@Path("/getleavetype")
		@Consumes(value= {"application/json"})
	public Response getLeaveType(EmployeeLeaveConfigJSON leave) {
		System.out.println("Getting leave type \n");
		ArrayList<EmployeeLeaveConfigJSON> leaveTypeList=new ArrayList<EmployeeLeaveConfigJSON>();
		EmployeeLeaveConfigJSON leaveData = new EmployeeLeaveConfigJSON();
		leaveTypeList=EmployeeLeaveLogic.GetLeaveType(leave);
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
		reportAndCount.setLeaveTypeList(leaveTypeList);
		for(EmployeeLeaveConfigJSON testlist:reportAndCount.getLeaveTypeList()) {
			System.out.println("Selected leavetype \n"+testlist.getLeaveType());
		}
		System.out.println("Getting leave type completed");
		return Response.status(200).entity(reportAndCount).build();
		
	}

	/*
	 * API FOR LEAVE REQUEST
	 */
	@POST
	@Path("/leaverequest")
	@Consumes(value= {"application/json"})
	public Response leaverequest(EmployeeLeaveJSON leave)throws SQLException  {
		
		System.out.println("Leave Request \n");
		
		  
		  Connection connection=null;
		  connection =DBUtil.getDBConnection();
		  /*String date=null;
		  String alreadyExits=IQueryConstants.ALREADY_EXIST_DATE ;
		  
			
		  	PreparedStatement exits = connection.prepareStatement(alreadyExits);
		  	
		  	exits.setString(1,leave.getDate());
		  	exits.setString(2,leave.getCompanyId());
		  	exits.setString(3,leave.getEmployeeId());
		  	ResultSet rs0=exits.executeQuery();
	        
	        while(rs0.next())
	        {
	        	date=rs0.getString("CheckInTime");
	        }
	        if(date==null){*/
		  
	    String subject="leave management processing.!!";
	    leave=EmployeeLeaveLogic.SelectMailId(leave);
	    if(leave.getReportingManagerId()!=null) {
	    String to=leave.getReportingManagerEmailId();
	    System.out.println("Reporting Manager EmailId : \t"+to);
			String body;
			String subBody;
			System.out.println("Mailing to user id.... \n");	
			if(!(leave.getDay()).equals("oneday")) {
				subBody=EmployeeLeaveLogic.MoreDayMailBody(leave);
				
			}else {
				subBody=EmployeeLeaveLogic.OneDayMailBody(leave);
			}
			body="	\n" + 
			  		" \n" + 
			  		"Hello ," + to +
			  		"\n " + 
			  		" 	\n" + 
			  		"\n"+"Details Of Employee Requesting Leave \n"+subBody +"\n"+"\n Reason:"+leave.getReason()+"\n"
			  		+"\nThank you,\n" + 
			  		"Leave Management Process\n" + 
			  		" 	\n" + 
			  		" \n" + 
			  		"	\n" ;
		/*
			String link="\n \n"
					+"To Authorize Click Here"
					+"<a href= \"http://localhost:3000/LeaveAuthorize?"+"!@#="+leave.getReportingManagerId() +"&" +"!@#=" +leave.getCompanyId() +"&" +"!@#=" +leave.getNoOfDays() + "&" +":@=" +leave.getFromDate() + 
					"&"+"/@@=" + leave.getToDate() + "&"+ "/<#%**@=" +leave.getSubject()  + "\" > " 
					+ "Authorize" + "</a>";
					System.out.println("mailing link to registered mailid \n");
			*/		
			String link="\n \n"
					+"To Authorize Click Here"
					+"<a href= \'http://localhost:3000/EmployeeLeaveRequest/'> " 
					+ "LeaveRequest" + "</a>";
					System.out.println("mailing link to registered mailid \n");
			
			try {
			
			Message message=new MimeMessage(session);
			Multipart MultiPart = new MimeMultipart();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			
			System.out.println("Registered mailId is \n"+to);
			
			// BodyPart for Sending Alert message 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body,"text/plain");
			MultiPart.addBodyPart(messageBodyPart);

			MimeBodyPart messageLinkPart = new MimeBodyPart();
			messageLinkPart.setContent(link,"text/HTML");
			MultiPart.addBodyPart(messageLinkPart);

			message.setContent(MultiPart);
			Transport.send(message);
		    System.out.println("Sent mail with leave authorize link..");
		    EmployeeLogic.AuditReport(leave.getReportingManagerId(),leave.getEmployeeId(), "Leeave Request Sent ",leave.getCompanyId());
		    System.out.println("Mailed Leave Request successfully \n");
			   
		 
			} catch (MessagingException e) {
				e.printStackTrace();
			}	
	        }else {
				  System.out.println("EmployeeID"+leave.getEmployeeId()+" have not Assigned any Reporting Manger..Please Assign first");
				leave.setStatus("NOT_ASSIGNED");
			  }
	    /*	        
	}   else{
			        	System.out.println("Request Already Exits on the Date"+leave.getDate());
			        	leave.setStatus("ALREADY_EXITS");
			        }
	*/
				   
				    
		return Response.status(200).entity(leave).build();
		
	}
	
	/*
	 * API FOR AUTHORIZED LEAVE
	 * 
	 */
		@POST
		@Path("/leaveauthorized")
		@Consumes(value= {"application/json"})
	public Response leaveauthorized(EmployeeLeaveJSON leave) {
			String subBody;
			System.out.println("Leave Authorized \n");
		EmployeeLeaveJSON reponse =new EmployeeLeaveJSON();
		System.out.println("Leave Confirmation \n");
	    String subject="Leave Confirmation";
	    String to=EmployeeLeaveLogic.SelectEmployeeMailId(leave);
	    EmployeeLeaveLogic.LeaveAuthorized(leave);
		reponse.setResponse(leave.getEmployeeId());
	    System.out.println("Employee EmailId : \t"+to);
	    if(leave.getFromDate().equals("-")) {
	    	System.out.println("its oneday \n");
	    	subBody=EmployeeLeaveLogic.OneDayMailBody(leave);
	    
		}else {
			System.out.println("its morethan oneday \n");
			subBody=EmployeeLeaveLogic.MoreDayMailBody(leave);
		}
			String body="	\n" + 
			  		" \n" + 
			  		"Hello ," + to +
			  		"\n " + 
			  		"\n Your Leave Request Details \n" +subBody+"\n Your Leave Request Hasbeen Authorized By Your Reporting Manager \n"
			  		+"\nThank you,\n" 
	  		+"\n Leave Management Process\n" + 
	  		" 	\n" + 
	  		" \n" + 
	  		"	\n" ;
			System.out.println("Mailing to user id.... \n");
						
			try {
			
			Message message=new MimeMessage(session);
			Multipart MultiPart = new MimeMultipart();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			
			System.out.println("Registered mailId is \n"+to);
			
			// BodyPart for Sending Alert message 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body,"text/plain");
			MultiPart.addBodyPart(messageBodyPart);

			message.setContent(MultiPart);
			Transport.send(message);
		    System.out.println("Sent Leave confirmation mail \n");
		    EmployeeLogic.AuditReport(leave.getSuperiorId(), leave.getEmployeeId(), "Leave Request Accepted",leave.getCompanyId());

	    System.out.println("Leave  Authorized completed");
		
	}catch (MessagingException e) {
			e.printStackTrace();
		}
			return Response.status(200).entity(reponse).build();
		
		
		}
		
	/*
	 * API FOR NOT AUTHORIZED LEAVE
	 */
		
		@POST
		@Path("/leavenotauthorized")
		@Consumes(value= {"application/json"})
	public Response leavenotauthorized(EmployeeLeaveJSON leave) {
		System.out.println("Leave Not Authorized \n");
		String subBody;
		EmployeeLeaveJSON reponse =new EmployeeLeaveJSON();
		System.out.println("Leave Confirmation \n");
	    String subject="Leave Confirmation";
	    reponse.setResponse(leave.getEmployeeId());
	    String to=EmployeeLeaveLogic.SelectEmployeeMailId(leave);
	    System.out.println("Employee EmailId : \t"+to);
	    if(leave.getFromDate().equals("-")) {
	    	System.out.println("its oneday \n");
	    	subBody=EmployeeLeaveLogic.OneDayMailBody(leave);
	    
		}else {
			System.out.println("its morethan oneday \n");
			subBody=EmployeeLeaveLogic.MoreDayMailBody(leave);
		}
			String body="	\n" + 
			  		" \n" + 
			  		"Hello ," + to +
			  		"\n " + 
			  		"\n Your Leave Request Details \n" +subBody+"\n Your Leave Request Is Not Authorized By Your Reporting Manager \n"
			  		+"\n Kindly Contact Him For More Details \n"
			  		+"\nThank you,\n" 
	  		+"\n Leave Management Process\n" + 
	  		" 	\n" + 
	  		" \n" + 
	  		"	\n" ;
			System.out.println("Mailing to user id.... \n");
						
			try {
			
			Message message=new MimeMessage(session);
			Multipart MultiPart = new MimeMultipart();
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			
			System.out.println("Registered mailId is \n"+to);
			
			// BodyPart for Sending Alert message 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body,"text/plain");
			MultiPart.addBodyPart(messageBodyPart);

			message.setContent(MultiPart);
			Transport.send(message);
		    System.out.println("Sent Leave confirmation mail \n");

	    System.out.println("Leave  Not Authorized completed");
	    EmployeeLogic.AuditReport(leave.getSuperiorId(), leave.getEmployeeId(), "Leave Request Rejected",leave.getCompanyId());

	    EmployeeLeaveLogic.LeaveNotAuthorized(leave);
		
	}
			
			catch (MessagingException e) {
			e.printStackTrace();
		}
			return Response.status(200).entity(reponse).build();			
	}

	

	
}
