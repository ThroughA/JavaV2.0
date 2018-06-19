package com.fillingstation.webservice;



import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.logic.EmployeeHolidayLogic;

@Path("/EmployeeHoliday")
public class EmpHolidayWebService {

	

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
