
package com.fillingstation.webservice;

import java.sql.SQLException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.logic.EmployeeShiftLogic;

@Path(value="/employeeshift")
public class EmployeeShiftWebService {
	
	
	 /*
     * API for checkin process
     */
    
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeecheckin")
    @Consumes(value="application/json")
 
 public Response employeecheckin(EmployeeAttendanceJSON details) throws ParseException{
    	
    	System.out.println("going to add employee checkin details......."+details.getEmployeeId());
    	int valid=EmployeeShiftLogic.EmployeeCheckin(details);
    	if(valid==0) {
    	System.out.println("added employee "+details.getEmployeeId()+"checkin details successfully.......");
    	}
    	else {
    		System.out.println(details.getEmployeeId()+"not a valid employeeId checkin details not inserted.......");
    	}
    	     	return Response.status(200).entity(details).build();
}

	
    /*
     * API for checkout process
     */
    
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeecheckout")
    @Consumes(value="application/json")
 
 public Response employeecheckout(EmployeeAttendanceJSON details) throws ParseException, SQLException {
    	System.out.println("going to update employee checkout details.......");
    	int valid=EmployeeShiftLogic.EmployeeCheckout(details);
    	System.out.println("updated employee checkout details successfully.......");
    	if(valid==0) {
	    	System.out.println("added employee checkout details successfully.......");
	    	}
	    	else{
	    		System.out.println("employee didn't checkin hence checkout details not inserted.......");
	    	}
    	return Response.status(200).entity(details).build();
}	    

    
    
    
}