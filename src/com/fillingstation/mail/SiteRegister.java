package com.fillingstation.mail;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.logic.SiteRegisterLogic;


@Stateless

@Path(value="/SitRegistration")
public class SiteRegister {

	@Resource(name = "java:jboss/mail/Gmail")

	Session session;
	
	
	/*
	 * API CALL FOR SITE REGISTRATION
	 */
	
	@POST
	@Path("/RegisterSite")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response RegisterOrganization (EmployeeLoginJSON org)throws ParseException
	{  
		int otp=0;
		String to=org.getEmailId();
		System.out.println("Registering The Organization... ");
		EmployeeLoginJSON org1=new EmployeeLoginJSON();
		org1=SiteRegisterLogic.CreateSite(org);
	   	if(org1.getOtp()!=0) {
	   		String subject="Email Id Verification By ThroughApps";
	   		
			System.out.println("MAILING THE OTP \n");
			
			String body="	\n" + 
			  		" \n" + 
			  		"Hello ," + to +
			  		"\n \n" + 
			  		"\n \n" + 
			  		"Kindly  enter the OTP for completing the Registration process\n" + 
			  		"\n \n" + 
			  		"Your OTP is :\n"+
			  		 "\n \n" + org1.getOtp() +
			  		"\n" + 
			  		"\n \n" + 
			  		"\n \n" + 
			  		"Thank you,\n\n" + 
			  		"ThroughApps\n" + 
			  		" 	\n" + 
			  		" \n" + 
			  		"	\n" ;
			
			try {
		    	
				Message message=new MimeMessage(session);
				Multipart MultiPart = new MimeMultipart();
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				
				System.out.println("Sending Mail to "+to+"\n");
				
				// BodyPart for Sending Alert message 
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(body,"text/plain");
				MultiPart.addBodyPart(messageBodyPart);
			
				message.setContent(MultiPart);
				Transport.send(message);
			    System.out.println("Mail Sent Successfully to "+to+"\n");
		    	 
				} catch (MessagingException e) {
					e.printStackTrace();
					
				}	
			org.setResponse("Mailed_Otp");
		    	}else {
		    		System.out.println("ORGANIZATION ALREADY EXIST \n");
		    				
		    	}
	    
	   	
		return Response.status(200).entity(org1).build();
		
		} 
	
	/*
	 * API CALL FOR INSERTING ORGANIZATION INNFORMATION ON SUCCESSFULL OTP VERIFICATION 
	 */
	
	@POST
	@Path("/InsertSite")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response InsertOrganization (EmployeeLoginJSON org)throws ParseException
	{  
		System.out.println("INSERTING  THE SITE... ");
		EmployeeLoginJSON org1=new EmployeeLoginJSON();
		org1=SiteRegisterLogic.InsertSite(org);
	    System.out.println(" ");
		return Response.status(200).entity(org1).build();
		
		}
}
