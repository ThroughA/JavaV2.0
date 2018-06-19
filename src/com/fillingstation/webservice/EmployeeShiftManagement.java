package com.fillingstation.webservice;

import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeConfigJSON;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.logic.ShiftManagementLogic;

@Path(value="/employeeshiftmanagement")
public class EmployeeShiftManagement {
	
	 /*
     * API FOR SELECTING TOTAL NO.OF SHIFTS 
     * FOR THE COMPANY
     */
     
     @POST
     @Produces(value="application/json" )
     @Path(value="/selectTotalNoShift")
     @Consumes(value="application/json")
     
     public Response selectTotalNoShift(EmployeeConfigJSON config) throws ParseException {
     	System.out.println("entering  into process of selecting employee id for department.......");
     	EmployeeConfigJSON empList = new EmployeeConfigJSON();
     	String totalShift;
     	totalShift=ShiftManagementLogic.SelectTotalNoOfShift(config);
     	empList.setTotalShift(totalShift);
     	System.out.println("completed selecting Employee successfully.......");
      	return Response.status(200).entity(empList).build();
   }
     /*
 	 * API CALL FOR GETTING EMPLOYEE DATA
 	 * BASED ON THE OPTIONS SELECTED
 	 */
 	
 	  @POST
 	  @Path(value="/SelectAllEmployee")
 	  @Produces(value="application/json" )
 	  @Consumes(value="application/json")
 	  
 	  public Response SelectAllEmployee(EmployeeConfigJSON config) throws ParseException {
 	  	System.out.println("entering  into process of selecting employee id for role.......");
 	  	ArrayList <EmployeeConfigJSON> empList = new ArrayList <EmployeeConfigJSON> ();
 	  	empList=ShiftManagementLogic.SelectAllEmployee(config);
 	  	
 	  	System.out.println("completed selecting Employee successfully.......");
 	   	return Response.status(200).entity(empList).build();
 	}

	/*
	 * API CALL FOR GETTING EMPLOYEE DATA
	 * BASED ON THE OPTIONS SELECTED
	 */
	
	  @POST
	  @Path(value="/selectempidnewone")
	  @Produces(value="application/json" )
	  @Consumes(value="application/json")
	  
	  public Response selectemployeeidrole(EmployeeConfigJSON config) throws ParseException {
	  	System.out.println("entering  into process of selecting employee id for role.......");
	  	ArrayList <EmployeeConfigJSON> empList = new ArrayList <EmployeeConfigJSON> ();
	  	empList=ShiftManagementLogic.SelectEmployee(config);
	  	
	  	System.out.println("completed selecting Employee successfully.......");
	   	return Response.status(200).entity(empList).build();
	}

	/*
	 * API call for Shift updation of employees
	 */
	
	@POST
	@Path("/shiftmanagement")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response shiftmanagement (EmployeeConfigJSON json)throws ParseException
	{  
	
		System.out.println("Employee Shift Management \n");
		ShiftManagementLogic.ShiftUpdation(json);
		EmployeeLoginJSON reportAndCount=new EmployeeLoginJSON();
		reportAndCount.setEmployeeId("444");
	    System.out.println(json);
		return Response.status(200).entity(reportAndCount).build();
		
		}


}

