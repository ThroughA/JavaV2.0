package com.fillingstation.mail;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.logic.EmployeeLogic;

import javax.mail.internet.InternetAddress;

@Path("/forgotpassword")
/*
 * @Stateless annotated bean is an EJB which by default provides Container-Managed-Transactions. 
 * CMT will by default create a new transaction if the client of the EJB did not provide one.
 */

@Stateless
public class ForgetPasswordMail {

		@Resource(name="java:jboss/mail/Gmail")
		private Session session;

		@POST
		@Path("/sendOTP")
	    public Response sendmail(EmployeeLoginJSON json) {
			  int flag=1;
			  String subject="Password Recovery";
			  Random rnd = new Random();
			  String to=json.getEmailId();
		      int valid=EmployeeLogic.vaildmailid(json);
		      if (valid==0)
		      {
			  int OTP= 100000 + rnd.nextInt(900000);
			  String body="	\n" + 
			  		" \n" + 
			  		"Hello ," + to +
			  		"\n \n" + 
			  		"\n \n" + 
			  		"You asked us to reset your forgotten password. To complete the process, Kindly  enter the OTP:\n" + 
			  		"\n \n" + 
			  		 "\n \n" + OTP +
			  		"\n" + 
			  		"\n \n" + 
			  		"\n \n" + 
			  		"Thank you,\n\n" + 
			  		" ThroughApps\n" + 
			  		" 	\n" + 
			  		" \n" + 
			  		"	\n" ;
			System.out.println("Storing OTP in Database");
			EmployeeLogic.storeOtp(to,OTP);
			try {
    	        MimeMessage message=new MimeMessage(session);
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
				message.setSubject(subject);
				message.setContent(body, "text/plain");
				Transport.send(message);
               flag=0;
			}catch(MessagingException e) {
				System.out.println("cannot send mail"+e);
			}
			}
		    String result=Integer.toString(flag);
			return Response.status(200).entity(result).build();

	
}
}


	

