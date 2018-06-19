package com.fillingstation.webservice;

import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.json.EmployeeConfigJSON;
import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.logic.EmployeeConfig;
import com.fillingstation.logic.EmployeeHolidayLogic;
import com.fillingstation.logic.EmployeeLogic;

@Path("/EmployeeConfig")
public class EmployeeConfigWebService {


	/*
	 * API call for Retreiving workingHours
	 */
	
	@POST
	@Path("/WorkingHours")
	@Consumes(value= {"application/json"})
		
	public Response workingHours (EmployeeAttendanceJSON json)throws ParseException
	{  
		
		System.out.println("Going to Retrive Company Minimum Working Time\n");
		String workHour =EmployeeLogic.retriveWorkingHour(json);
		
		return Response.status(200).entity(workHour).build();
		
		}
	
	
	 /*
     * API for updating the Company Minimum Working Hour
     */
    
		    @POST
		    @Produces(value="application/json" )
		    @Path(value="/UpdateWorkingHours")
		    @Consumes(value="application/json")
		    
		 public Response updateworkinghours(EmployeeConfigJSON details) throws ParseException {
		    	System.out.println("going to update employee working hours.......");
		    	details=EmployeeLogic.UpdateEmployeeWorkingHours(details);
		    	System.out.println("updated employee working hours successfully.......");
		     	return Response.status(200).entity(details).build();
		}
			
		
	

/*
		 * API call for Biometric settings
		 */
		
		@POST
		@Path("/BiometricSettings")
		@Produces(value="application/json" )
		@Consumes(value= "application/json")
			
		public Response BiometricSettings (EmployeeConfigJSON json)throws ParseException
		{  
			System.out.println("going to update Biometric values ......");
			
			EmployeeConfigJSON biometricValue =EmployeeConfig.biometricSettings(json);
			System.out.println("going to update Biometric values ......");
			
			return Response.status(200).entity(biometricValue).build();
			
			}
		
		    
		    
	

	/*
	 * API FOR adding holiday info
	 */
	
		@POST
		@Path("/addholidayinfo")
		@Consumes(value= {"application/json"})
	public Response addholidayinfo(EmployeeLeaveConfigJSON leave) {
		System.out.println("Adding holiday info \n");
		System.out.println("companyid :\t"+leave.getCompanyId());
		System.out.println("date: \t"+leave.getDate());
		EmployeeLeaveConfigJSON holidayData = new EmployeeLeaveConfigJSON();
		String description=EmployeeHolidayLogic.AddHolidayInfo(leave);
		System.out.println(description+"hi");
		holidayData.setDescription(description);
	    System.out.println("Adding holiday info completed");
		return Response.status(200).entity(holidayData).build();
		
		
	}
		
		/*
	 * API FOR deleting holiday info 
	 */
		
		@POST
		@Path("/deleteholidayinfo")
		@Consumes(value= {"application/json"})
	public Response deleteholidayinfo(EmployeeLeaveConfigJSON leave) {
		System.out.println("deleting holiday info \n");
		ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
		System.out.println("companyid :\t"+leave.getCompanyId());
		System.out.println("date: \t"+leave.getDate());
		System.out.println("calling function \n");
		
		EmployeeHolidayLogic.DeleteHolidayInfo(leave);
	    System.out.println("deleting holiday info completed");
		return Response.status(200).entity(employeeLeavelist).build();
		
	}
		
		/*
	 * API FOR getting holiday info  for 
	 * updating or deleting the already 
	 * existing holiday details
	 */
		
		@POST
		@Path("/getholidayinfodata")
		@Consumes(value= {"application/json"})
	public Response getholidayinfodata(EmployeeLeaveConfigJSON leave) {
		System.out.println("getting holiday info \n");
		ArrayList<EmployeeLeaveConfigJSON> holidayDatalist = new ArrayList<EmployeeLeaveConfigJSON>();
		System.out.println("companyid :\t"+leave.getCompanyId());
		System.out.println("date: \t"+leave.getDate());
		System.out.println("calling function \n");
		holidayDatalist=EmployeeHolidayLogic.GetHolidayInfo(leave);
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setHolidayDatalist(holidayDatalist);
    	
	    System.out.println("getting holiday info completed");
		return Response.status(200).entity(reportAndCount).build();
		
	}
		
		/*
	 * API FOR updating holiday info 
	 */
		
		@POST
		@Path("/updateholidayinfo")
		@Consumes(value= {"application/json"})
	public Response updateholidayinfo(EmployeeLeaveConfigJSON leave) {
		System.out.println("updating holiday info \n");
		ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
		System.out.println("companyid :\t"+leave.getCompanyId());
		System.out.println("date: \t"+leave.getDate());
		System.out.println("calling function \n");
		
		EmployeeHolidayLogic.UpdateHolidayInfo(leave);
	    System.out.println("updating holiday info completed");
		return Response.status(200).entity(employeeLeavelist).build();
		
	}
}
