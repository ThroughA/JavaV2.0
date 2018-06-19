package com.fillingstation.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.FingerPrintJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.util.DBUtil;



@Path(value="/fingerprint")
public class EmployeeBiometricWebService {

	@POST
	@Path(value="/store")
	@Consumes(value={"application/json"})
	@Produces(value={"application/json"})
	public Response storing (FingerPrintJSON json) {
		try{
			Connection connection=null;
				connection=DBUtil.getDBConnection();
			  	String querySelect=IQueryConstants.STORE_FINGERPRINT;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1, json.getFingerPrint());
				preparedStmt.setString(2, json.getEmployeeId());
				preparedStmt.setString(3, json.getCompanyId());
		        preparedStmt.executeUpdate();
		        EmployeeLogic.AuditReport(json.getSuperiorId(), json.getEmployeeId(), "Finger Print Registered ", json.getCompanyId());
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return Response.status(200).entity(json).build(); 
      
	}
	@POST
	@Path(value="/employeeBio")
	@Consumes(value={"application/json"})
	
	@Produces(value={"application/json"})
	public Response EmployeeBio (FingerPrintJSON json) {
		try{
			Connection connection=null;
				connection=DBUtil.getDBConnection();
				String querySelect=IQueryConstants.GET_EMPLOYEEBIO;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				
				preparedStmt.setString(1, json.getEmployeeId());
				preparedStmt.setString(2, json.getCompanyId());
		        ResultSet rs = preparedStmt.executeQuery();
		        System.out.println(" finger "+json.getEmployeeId()+" "+json.getCompanyId() );
		        
		        while (rs.next()){
		        	json.setFingerPrint(rs.getString("FingerPrint1"));
		        	System.out.println(" finger "+ rs.getString("FingerPrint1"));
		        }
		        
		        
		        connection.close();
		    
		}
		catch(Exception e){ System.out.println(e);
		} 
		return Response.status(200).entity(json).build(); 
      
	}
}


