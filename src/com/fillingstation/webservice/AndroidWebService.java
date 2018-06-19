package com.fillingstation.webservice;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.FingerData;
import com.fillingstation.json.FingerPrintJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.util.DBUtil;
@Path(value="/AndroidAPI")

public class AndroidWebService {
	
	

	@POST
	@Path(value="/Login")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public  FingerData AndroidLogin (FingerData json) {
		JSONObject jsonOp=new JSONObject();
        
		try{
			 
			Connection connection=null;
				connection=DBUtil.getDBConnection();
				String querySelect=IQueryConstants.ANDROID_LOGIN;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1, json.getEmailId());
				preparedStmt.setString(2, json.getPassword());
		        ResultSet rs = preparedStmt.executeQuery();
		        System.out.println(" Login mm" );
		         
		        if(rs.next()){
		        	
		        	if(json.getPassword().equals(rs.getString("password"))){
		        		json.setCompanyId(rs.getString("CompanyId"));
		        		System.out.println("Password correct"+ json.getEmailId()+" "+json.getPassword());
		        	}
		        	else{
		        		json.setCompanyId("PASSWORD_INCORRECT");
		        		System.out.println("Password in correct"+ json.getEmailId()+" "+json.getPassword());
		        	}
		        }	
		        else{
		        	json.setCompanyId("NOT_REGEISTERED");
		        	System.out.println("NOT Register"+ json.getEmailId()+" password"+json.getPassword() +" enroll"+json.getEnroll_Template());
		        	
		        }
		        
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
      
	}
	@POST
	@Path(value="/StoreScanFinger")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public FingerData storing (FingerData json) {
		//byte[] data=new byte[json.getEmailId()];
		//String str = Arrays.toString(data);
		
		try{
		Connection connection=null;
		System.out.println(" going to Store Fingerprint" + json.getFingerPrint());

		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.STORE_FINGERPRINT;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,json.getFingerPrint());
		preparedStmt.setString(2, json.getEmployeeId());
		preparedStmt.setString(3, json.getCompanyId());
        preparedStmt.executeUpdate();
        System.out.println(" Stored Finger Print Value"+json.getFingerPrint() + "decode" +json.getEmailId());
        EmployeeLogic.AuditReport(json.getSuperiorId(), json.getEmployeeId(), " Finger Print Registered ", json.getCompanyId());
        connection.close();	    
        System.out.println(" Finished ");
		
			}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
      
	}

	@POST
	@Path(value="/Get_Employee_Fingerprint")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public  FingerPrintJSON checkInBio (FingerPrintJSON json) {
		JSONObject jsonOp=new JSONObject();
		System.out.println(" check In Validation" );
        
		try{
			 
			Connection connection=null;
				connection=DBUtil.getDBConnection();
				String querySelect=IQueryConstants.GET_EMPLOYEEBIO;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				
				preparedStmt.setString(1, json.getEmployeeId());
				preparedStmt.setString(2, json.getCompanyId());
		        ResultSet rs = preparedStmt.executeQuery();
		        System.out.println(" Android Fingerprint" );
		        
		        while (rs.next()){
		        	jsonOp.put("fingerPrint", rs.getString("FingerPrint1"));
		        	json.setFingerPrint(rs.getString("FingerPrint1"));
		        	System.out.println(" finger "+jsonOp);
		        }
		        
		        
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
	
	}
	
	@POST
	@Path(value="/checkOutBio")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public  FingerPrintJSON checkOutBio (FingerPrintJSON json) {
		JSONObject jsonOp=new JSONObject();
		System.out.println(" check In Validation" );
        
		try{
			 
			Connection connection=null;
				connection=DBUtil.getDBConnection();
				String querySelect=IQueryConstants.GET_EMPLOYEEBIO;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				
				preparedStmt.setString(1, json.getEmployeeId());
				preparedStmt.setString(2, json.getCompanyId());
		        ResultSet rs = preparedStmt.executeQuery();
		        System.out.println(" Android Fingerprint" );
		        
		        while (rs.next()){
		        	jsonOp.put("fingerPrint", rs.getString("FingerPrint1"));
		        	json.setFingerPrint(rs.getString("FingerPrint1"));
		        	System.out.println(" finger "+jsonOp);
		        }
		        
		        
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
	
	}

	
	@POST
	@Path(value="/jeevaBioVerify")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public Response storing1 (FingerData json) {
		try{
			
		
			
		String	jeeva = "[B@428d92f8";
			
			String test= new String( json.getEmailId());
			if(test==jeeva){
				
				System.out.println("accepted");	
				 System.out.println("emailid : " + test + " jeeva" + jeeva);
				
			}
			else{
				System.out.println("not accepted,failed");
				 System.out.println("emailid : " + test + " jeeva" + jeeva);
			}
			byte[] bytesData = test.getBytes();
			
			 System.out.println("testString : " + test + " jeeva" + jeeva);
			 System.out.println("\nbytesData : " + bytesData);  // .getBytes on String will return Hashcode value
			 System.out.println("bytesData.toString() : " + bytesData.toString());  // .toString() will return Hashcode value
		 
			
			
			String decodedData = new String(bytesData);  // Create new String Object and assign byte[] to it
		    System.out.println("\nText Decryted : " + decodedData);
		    String decodedDataUsingUTF8;
			try {
				decodedDataUsingUTF8 = new String(bytesData, "UTF-8");  // Best way to decode using "UTF-8"
			    System.out.println("Text Decryted using UTF-8 : " + decodedDataUsingUTF8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			
			
			
			System.out.println(" verifying template  ");
			//byte[] data=new byte[json.getEnroll_Template()];
			//byte data = json.getEnroll_Template();
			//System.out.println(" finger data "+ data);
			System.out.println("  value emailid "+json.getEmailId());
		System.out.println(" password  value enrol value "+  json.getPassword()+ "  email value verify" + json.getEmailId());
		
		/*Connection connection=null;
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.STORE_FINGERPRINT;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setBytes(1,data);
		preparedStmt.setString(2, json.getEmployeeId());
		preparedStmt.setString(3, json.getCompanyId());
        preparedStmt.executeUpdate();
        connection.close();	    
        System.out.println(" Finished ");
		*/
	
			}
		catch(Exception e){ System.out.println(e);
		} 
		return Response.status(200).entity(json).build(); 
      
	
	  
	}
	
	
	@POST
	@Path(value="/EmployeeFingerPrint")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public  FingerPrintJSON EmployeeBio (FingerPrintJSON json) {
		JSONObject jsonOp=new JSONObject();
		System.out.println(" Android Fingerprint" ); 
        
		try{
			 
			Connection connection=null;
				connection=DBUtil.getDBConnection();
				String querySelect=IQueryConstants.GET_EMPLOYEEBIO;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				
				preparedStmt.setString(1, json.getEmployeeId());
				preparedStmt.setString(2, json.getCompanyId());
		        ResultSet rs = preparedStmt.executeQuery();
		        System.out.println(" Android Fingerprint" );
		        
		        while (rs.next()){
		        	jsonOp.put("fingerPrint", rs.getString("FingerPrint1"));
		        	json.setFingerPrint(rs.getString("FingerPrint1"));
		        	System.out.println(" finger "+jsonOp);
		        }
		        
		        
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
      
	}
	@POST
	@Path(value="/StoringFingerPrint")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public FingerPrintJSON FingerPrintStore(FingerPrintJSON json) {
		//byte[] data=new byte[json.getEmailId()];
		//String str = Arrays.toString(data);
		
		try{
		Connection connection=null;
		System.out.println(" going to Store Fingerprint" + json.getFingerPrint());
		
		connection=DBUtil.getDBConnection();
		
		String querySelect=IQueryConstants.STORE_FINGERPRINT;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,json.getFingerPrint());
		preparedStmt.setString(2, json.getEmployeeId());
		preparedStmt.setString(3, json.getCompanyId());
        preparedStmt.executeUpdate();
        System.out.println(" Stored Finger Print Value"+json.getFingerPrint() ); 
        connection.close();	    
        System.out.println(" Finished ");
		
			}
		catch(Exception e){ System.out.println(e);
		} 
		return json; 
      
	}
}
