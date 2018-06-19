package com.fillingstation.webservice;

import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.logic.EmpWeekEndJSON;
import com.fillingstation.logic.EmployeeLeaveLogic;

@Path(value="/EmployeeLeaveHoliday")
public class EmployeeLeaveWebService {
	
	/*
	 * API FOR getting leave info  for 
	 * updating or deleting the already 
	 * existing leave details
	 */
		
		@POST
		@Path("/getleaveinfodata")
		@Consumes(value= {"application/json"})
	public Response getleaveinfodata(EmployeeLeaveConfigJSON leave) {
		System.out.println("getting leave info \n");
		ArrayList<EmployeeLeaveConfigJSON> leaveDatalist = new ArrayList<EmployeeLeaveConfigJSON>();
		leaveDatalist=EmployeeLeaveLogic.GetLeaveInfo(leave);
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setHolidayDatalist(leaveDatalist);
	    System.out.println("getting leave info completed");
		return Response.status(200).entity(reportAndCount).build();
		
	}

		/*
		 * API FOR adding leave info
		 */
		
 		@POST
 		@Path("/addLeaveinfo")
 		@Consumes(value= {"application/json"})
		public Response addleaveinfo(EmployeeLeaveConfigJSON leave) {
			System.out.println("Adding leave info \n");
			EmployeeLeaveConfigJSON leaveData = new EmployeeLeaveConfigJSON();
			String description=EmployeeLeaveLogic.AddLeaveInfo(leave);
			leaveData.setDescription(description);
		    System.out.println("Adding leave info completed");
			return Response.status(200).entity(leaveData).build();
			
		}



		/*
		 * API FOR updating holiday info 
		 */
 		
 		@POST
 		@Path("/updateleaveinfo")
 		@Consumes(value= {"application/json"})
		public Response updateleaveinfo(EmployeeLeaveConfigJSON leave) {
			System.out.println("updating leave info \n");
			ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
			EmployeeLeaveLogic.ChangeDays(leave,leave.getOldnoofLeave());
		    System.out.println("updating leave info completed");
			return Response.status(200).entity(employeeLeavelist).build();
			
		}


		/*
		 * API FOR deleting leave info 
		 */
 		
 		@POST
 		@Path("/deleteleaveinfo")
 		@Consumes(value= {"application/json"})
		public Response deleteleaveinfo(EmployeeLeaveConfigJSON leave) {
			System.out.println("deleting leave info \n");
			ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
			EmployeeLeaveLogic.DeleteLeaveInfo(leave);
		    System.out.println("deleting leave info completed");
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
		//EmployeeLeaveConfigJSON totalShift=new EmployeeLeaveConfigJSON();
	String totalShift;
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
			System.out.println("companyid :\t"+leave.getCompanyId());
			System.out.println("date: \t"+leave.getDate());
			System.out.println("calling function \n");
			holidayDatalist=EmployeeLeaveLogic.GetHolidayInfo(leave);
			totalShift=EmployeeLeaveLogic.TotalNoOfShifts(leave);
	    	reportAndCount.setHolidayDatalist(holidayDatalist);
	    	reportAndCount.setTotalShift(totalShift);
	    	
		    System.out.println("getting holiday info completed");
			return Response.status(200).entity(reportAndCount).build();
			
		}


		/*
		 * API FOR adding holiday info
		 */
		
 		@POST
 		@Path("/addholidayinfo")
 		@Consumes(value= {"application/json"})
 		@Produces(value={"application/json"})
		public Response addholidayinfo(EmployeeLeaveConfigJSON leave) {
			System.out.println("Adding holiday info \n");
			EmployeeLeaveConfigJSON holidayData = new EmployeeLeaveConfigJSON();
			String description=EmployeeLeaveLogic.AddHolidayInfo(leave);
			holidayData.setDescription(description);
		    System.out.println("Adding holiday info completed");
			return Response.status(200).entity(holidayData).build();
			
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
			
			EmployeeLeaveLogic.UpdateHolidayInfo(leave);
		    System.out.println("updating holiday info completed");
			return Response.status(200).entity(employeeLeavelist).build();
			
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
			
			EmployeeLeaveLogic.DeleteHolidayInfo(leave);
		    System.out.println("deleting holiday info completed");
			return Response.status(200).entity(employeeLeavelist).build();
			
		}

 		
 		
 		
 		  /*
 				 * API FOR getting weekend,leave peryear Configuration details
 				 */
 		 		
 		 		@POST
 		 		@Path("/getholidayconfigurationdata")
 		 		@Consumes(value= {"application/json"})
 				public Response getholidayconfigurationdata(EmpWeekEndJSON leave) throws ParseException {
 					System.out.println("getting holiday configuration data \n");
 					ArrayList<EmployeeLeaveConfigJSON> holidayList = new ArrayList<EmployeeLeaveConfigJSON>();
 					EmpWeekEndJSON hoidayInfo=new EmpWeekEndJSON();
 					String totalShift=null;
 					System.out.println("companyid :\t"+leave.getCompanyId());
 					System.out.println("date: \t"+leave.getDate());
 					System.out.println("calling function \n");
 					
 					hoidayInfo=EmployeeLeaveLogic.GetHoliDayInfo(leave);
 				//	totalShift=EmployeeLeaveLogic.TotalNoOfShifts(leave);
 					//hoidayInfo.setTotalShift(totalShift);
 				    
 				    System.out.println("getting holiday configuration data completed");
 					return Response.status(200).entity(hoidayInfo).build();
 					
 				}



 				/*
 				 * API FOR weekend holiday Configuration
 				 * 
 				 */


 				@POST
 		 		@Path("/holidayconfiguration")
 		 		@Consumes(value= {"application/json"})
 				public Response holidayconfiguration(EmpWeekEndJSON leave) {
 					System.out.println("holiday configuration \n");
 					ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
 					EmployeeLeaveLogic.HoliDayConfig(leave);
 				    System.out.println("holiday  configuration completed");
 					return Response.status(200).entity(employeeLeavelist).build();
 					
 				}
 		 			



}
