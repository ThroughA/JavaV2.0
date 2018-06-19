package com.fillingstation.webservice;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.json.EmployeeConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.json.EmployeeMaintenanceJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.json.EmployeeRequestJSON;
import com.fillingstation.json.EmployeeResponseJSON;
import com.fillingstation.logic.AttendanceUpdateLogic;
import com.fillingstation.logic.CheckInCheckOutLogic;
import com.fillingstation.logic.EmployeeLeaveLogic;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.logic.EmployeeReportLogic;
import com.fillingstation.logic.EmployeeShiftLogic;


@Path(value="/employee")


public class EmployeeWebService {


	/*
	 * API call for Login verification
	 */
	
	@POST
	@Path(value="/employeeLogin")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response loginVerify (EmployeeLoginJSON json)throws ParseException
	{  
		
		
		//ArrayList<EmployeeConfigJSON> employeePermissionlist = new ArrayList<EmployeeConfigJSON>();
		System.out.println((new StringBuilder("Login Verification... ")).append(json.getEmailId().toString()));
	    
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
		reportAndCount =EmployeeLogic.loginVerify(json);
	    System.out.println(json);
		return Response.status(200).entity(reportAndCount).build();
		
		 
		}
	
	
	
	 /*
     * API for unlocking the locked employee
     */
    
    @POST
    @Path(value="/empunlock")
    @Consumes(value={"application/json"})
	@Produces(value={"application/json"})
    
    public Response empunlock(EmployeeAttendanceJSON details){
    	
    	
    	System.out.println("unlocking the locked employeeId.........");
    	details=EmployeeLogic.UnLock(details);
    	System.out.println("completed unlocking the locked employeeid Successfully......");
     	return Response.status(200).entity(details).build();
    }   
   
	
	/*
	*API for blocking the employee 
	*/
	 @POST
	 @Produces(value="application/json" ) 
	 @Path(value="/employeeBlockUnblock")
	 @Consumes(value="application/json") public Response employeeblock(EmployeeAttendanceJSON details) throws ParseException {
	System.out.println("entering  into process of regulating time.......");
	details=EmployeeLogic.BlockUnblock(details);
		System.out.println("completed leave updation successfully.......");
	return Response.status(200).entity(details).build();
	}	    

	
	

	/*	
	    * API for getting permision for each role while login
	    
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeepelogin")
	    @Consumes(value="application/json")
	    
	    public Response employeepelogin(EmployeeConfigJSON config) throws ParseException {
	    	System.out.println("entering  into process of getting permission for each role.......");
	    	ArrayList<EmployeeConfigJSON> value=EmployeeLogic.GetPermissionDetails(config);
	    	System.out.println("completed employee permission successfully.......");
	     	return Response.status(200).entity(value).build();
}	   

*/	
	
	/*
	 * API call for Chart	
	 *  */
	
	@POST
	@Path("/employeeChart")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response chart(EmployeeMaintenanceJSON details)
	{  

	    System.out.println("chart..... ");
	    ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=EmployeeReportLogic.EmployeeDailyReportSummary(details);
	    System.out.println("completed start rendering");
		return Response.status(200).entity(employeeRetrievelist).build();
		
		
		}
	
	
	
	/*
	 * API for registering and adding new employee	
	 */
		
		    @POST
		    @Produces(value="application/json" )
		    @Path(value="/EmployeeList")
		    @Consumes(value="application/json")
		 
		 public Response EmployeeList(EmployeeLoginJSON details) throws ParseException {
			    System.out.println("Employeen List Retriveing..... ");
			    ArrayList<EmployeeConfigJSON> employeeList=new ArrayList<EmployeeConfigJSON>();
			    employeeList=EmployeeLogic.EmployeeList(details.getCompanyId());
		    	System.out.println("completed start rendering");
				return Response.status(200).entity(employeeList).build();
					}

		    
		    

/*
 * API for registering and adding new employee	
 */
	
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/addemployee")
	    @Consumes(value="application/json")
	 
	 public Response addemployee(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to add employee.......");
	    	details=EmployeeLogic.AddEmployee(details);
	     	return Response.status(200).entity(details).build();
}

	    /*
	     * API - details for editing employee detail
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/updateEmployeeDetails")
	    @Consumes(value="application/json")
	 
	 public Response editEmployee(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to Retreive employee detail for editng.......");
	    	details=EmployeeLogic.EditEmployee(details);
	    	System.out.println("retrieved employee successfully.......");
	     	return Response.status(200).entity(details).build();
}
    
	    /*
	     * API for updating the employee details
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/updateemployee")
	    @Consumes(value="application/json")
	 
	 public Response updateemployee(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to update employee.......");
	    	details=EmployeeLogic.UpdateEmployee(details);
	    	System.out.println("updated employee successfully.......");
	     	return Response.status(200).entity(details).build();
}
	   
	    /*
	     * API for  employee details
	     */

	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeedetails")
	    @Consumes(value="application/json")
	 
	 public Response employeeDetails(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to retrieve  employee deatails.......");
	    	details=EmployeeLogic.EmployeeDetail(details);
	    	System.out.println("going to retrieve  employee deatails......."+details.getEmployeeName());
	    	
	    	return Response.status(200).entity(details).build();
}
	    
	    
	    
	    /*
	     * API for deleting employee
	     */

	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/deleteemployee")
	    @Consumes(value="application/json")
	 
	 public Response deleteemployee(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to delete employee.......");
	    	EmployeeLogic.DeleteEmployee(details);
	    	System.out.println("deleted employee successfully.......");
	     	
	    	return Response.status(200).entity(details).build();
}
	    
	
	    
	  

	    		/*
	    	     * API for searching the employee
	    	     */
	    	    
	    	    @POST
	    	    @Produces(value="application/json" )
	    	    @Path(value="/searchemployee")
	    	    @Consumes(value="application/json")
	    	 
	    	 public Response searchemployee(EmployeeMaintenanceJSON details)  {
	    	    	System.out.println("going to search employee.......");
	    	    	ArrayList<EmployeeMaintenanceJSON> employeeRetrievelist = new ArrayList<EmployeeMaintenanceJSON>();
	    	    	employeeRetrievelist=EmployeeLogic.SearchEmployee(details);
	    	    	System.out.println("searhed the employee successfully.......");
	    	     	return Response.status(200).entity(employeeRetrievelist).build();
	    }
	    
	    	    


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

	    
	    	   /* 
	    	     * API for checkin process
	    	     
	    	    
	    	    @POST
	    	    @Produces(value="application/json" )
	    	    @Path(value="/employeecheckin")
	    	    @Consumes(value="application/json")
	    	 
	    	 public Response employeecheckin(EmployeeAttendanceJSON details) throws ParseException, SchedulerException {
	    	    	
	    	    	System.out.println("going to add employee checkin details......."+details.getEmployeeId());
	    	    	int valid=EmployeeLogic.EmployeeCheckin(details);
	    	    	if(valid==0) {
	    	    	System.out.println("added employee "+details.getEmployeeId()+"checkin details successfully.......");
	    	    	}
	    	    	else {
	    	    		System.out.println(details.getEmployeeId()+"not a valid employeeId checkin details not inserted.......");
	    	    	}
	    	    	
	    	    	// Trigger for updating the status as absent for employee who are not checked out
	    	    	System.out.println("going to trigger for default absent.......");
	    	    	JobDetail job = new JobDetail();
	    	    	job.setName("attendanceUpdateJob");
	    	    	job.setJobClass(AttendanceUpdateLogic.class);
	    	    
	    	    	CronTrigger trigger = new CronTrigger();
	    	    	trigger.setName("attendanceUpdateTrigger");
	    	    	trigger.setCronExpression("0 55 23 1/1 * ? *");
	    	    	
	    	    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
	    	    	 scheduler.getContext().put("date",details.getDate());
	    	    	scheduler.start();
	    	    	scheduler.scheduleJob(job, trigger);
	    	    	System.out.println("triggered for default absent successfully.......");
	    	    	
	    	     	return Response.status(200).entity(details).build();
	    }
	    	    
	    	    
	    	     * API for checkout process
	    	     
	    	    
	    	    @POST
	    	    @Produces(value="application/json" )
	    	    @Path(value="/employeecheckout")
	    	    @Consumes(value="application/json")
	    	 
	    	 public Response employeecheckout(EmployeeAttendanceJSON details) throws ParseException {
	    	    	System.out.println("going to update employee checkout details.......");
	    	    	int valid=EmployeeLogic.EmployeeCheckout(details);
	    	    	System.out.println("updated employee check details successfully.......");
	    	    	if(valid==0) {
	    		    	System.out.println("added employee checkout details successfully.......");
	    		    	}
	    		    	else{
	    		    		System.out.println("employee didn't checkin hence checkout details not inserted.......");
	    		    	}
	    	    	return Response.status(200).entity(details).build();
	    }	    
	    */
	    	    /*
	     * API for Attendance process
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/attendance")
	    @Consumes(value="application/json")
	 
	 public Response attendance(EmployeeAttendanceJSON details) throws ParseException {
	    	System.out.println("going to add employee checkin details.......");
	    	//details=EmployeeLogic.EmployeeCheckin(details);
	    	ArrayList<EmployeeResponseJSON> employeeAttenDance= new ArrayList<EmployeeResponseJSON>();
	    	employeeAttenDance=EmployeeLogic.Attendance(details);
	    	return Response.status(200).entity(employeeAttenDance).build();
}
	   
	    
	    /*
	     * API for employee daily attendance report
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeeAttendanceDailyReport")
	    @Consumes(value="application/json")
	    public Response employeedailyreport(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to generate employee daily report details.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	ArrayList<EmployeeReportJSON> employeeSummarylist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.EmployeeDailyReport(details);
	    	employeeSummarylist=EmployeeReportLogic.EmployeeDailyReportSummary(details);
	    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
	    	reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
	        System.out.println("generated employee daily report details successfully.......");
	     	return Response.status(200).entity(reportAndCount).build();
}
	
	    
	    
	    
	    
	    
	    
	    
	    
	    /*
	     * API for employee monthly report
	     */
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeeAttendanceMonthlyReport")
	    @Consumes(value="application/json")
	    public Response employeemonthlyreport(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to generate employee period report details.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	ArrayList<EmployeeReportJSON> employeeSummarylist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.EmployeeMonthlyReport(details);
	    	employeeSummarylist=EmployeeReportLogic.EmployeeMonthlyReportSummary(details);
	    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
	    	reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
	    	System.out.println("generated employee period report details successfully.......");
	     	return Response.status(200).entity(reportAndCount).build();
}
	    
	    

	    /*
	     * API for employee Period report
	     */
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeeAttendancePeriodReport")
	    @Consumes(value="application/json")
	    public Response employeeperiodreport(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to generate employee Period report details.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	ArrayList<EmployeeReportJSON> employeeSummarylist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.EmployeePeriodReport(details);
	    	employeeSummarylist=EmployeeReportLogic.EmployeePeriodReportSummary(details);
	    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
	    	reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
	    	System.out.println("generated employee Period report details successfully.......");
	     	return Response.status(200).entity(reportAndCount).build();
}
	    
	    
	    
	    /*
	     * API for employee maintenance report
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/employeeMaintenance")
	    @Consumes(value="application/json")
	 
	 public Response employeemaintenance(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("going to generate employee maintenance report details.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	ArrayList<EmployeeReportJSON> employeeSummarylist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.EmployeeMaintenanceReport(details);
	    	employeeSummarylist=EmployeeReportLogic.EmployeeMaintenanceReportSummary(details);
	    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
	    	reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
	    	
	    	System.out.println("generated employee maintenance report details successfully.......");
	     	return Response.status(200).entity(reportAndCount).build();
}
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/auditReport")
	    @Consumes(value="application/json")
	    public Response auditreport(EmployeeReportJSON details) throws ParseException {
	    	System.out.println("going to generate audit report details.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.AuditReportDisplay(details);
	    	
	    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
	    	
	        System.out.println("generated audit report details successfully.......");
	     	return Response.status(200).entity(reportAndCount).build();
	    }
	    
	    
	    /*
	     * API for search operation
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/auditsearchemployee")
	    @Consumes(value="application/json")
	 
	 public Response auditsearchemployee(EmployeeReportJSON details)  {
	    	System.out.println("going to search employee in auditreport.......");
	    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    	employeeRetrievelist=EmployeeReportLogic.AuditSearchEmployee(details);
	    	System.out.println("searhed the employee in audit report successfully.......");
	     	return Response.status(200).entity(employeeRetrievelist).build();
}
	    
	    
	    /*
	     *API for supervisor authorization 
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/supervisorAuthorization")
	    @Consumes(value="application/json")
	    public Response supervisorauthorization(EmployeeMaintenanceJSON details) throws ParseException {
	    	System.out.println("supervisor is authorizing entering into process.......");
	    	int value=EmployeeLogic.Authorization(details);
	    	System.out.println("supervisor authorization done successfully.......");
	     	return Response.status(200).entity(value).build();
}
	    
	    
	    /*
	     *API for Retrieving EmployeeRequest  
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/EmployeeRequest")
	    @Consumes(value="application/json")
	    public Response EmployeeRequests(EmployeeAttendanceJSON details) throws ParseException {
	    	EmployeeRequestJSON result = new EmployeeRequestJSON();
			
	    	System.out.println("Going to Retrieve Employee Requests.......");
	    	result=EmployeeLogic.EmployeeRequest(details);
	    	
	    	System.out.println("Successfully Retrieved.......");
	     	return Response.status(200).entity(result).build();
}	  
	    
	    
	  	    
	    /*
	     *API for time regulation  
	     */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/AttendanceRegulatioAccept")
	    @Consumes(value="application/json")
	    public Response employeetimeregulation(EmployeeAttendanceJSON details) throws ParseException {
	    	System.out.println("entering  into process of regulating time.......");
	    	details=EmployeeLogic.TimeRegulation(details);
	    	
	    	System.out.println("completed time regulation successfully.......");
	     	return Response.status(200).entity(details).build();
}	    
	    
	    
	  
	    
	    /*
		    * API for adding department
		    */
		    
		    @POST
		    @Produces(value="application/json" )
		    @Path(value="/addDepartment")
		    @Consumes(value="application/json")
		    
		    public Response adddepartment(EmployeeConfigJSON config) throws ParseException {
		    	System.out.println("entering  into process of adding department.......");
		    	config =EmployeeLogic.AddDeprtment(config);
		    	
		    	
		    	System.out.println("completed add department successfully.......");
		     	return Response.status(200).entity(config).build();
	}	    

		       /*
			    * API for adding role
			    */
			    
			    @POST
			    @Produces(value="application/json" )
			    @Path(value="/addRole")
			    @Consumes(value="application/json")
			    
			    public Response role(EmployeeConfigJSON config) throws ParseException {
			    	System.out.println("entering  into process of adding role.......");
			    	config =EmployeeLogic.AddRole(config);
			    	
			    	
			    	System.out.println("completed add role successfully.......");
			     	return Response.status(200).entity(config).build();
		}	   
			    
			    	/*
				    * API for specifying permision for each role
				    */
				    
				    @POST
				    @Produces(value="application/json" )
				    @Path(value="/employeePermission")
				    @Consumes(value="application/json")
				    
				    public Response employeepermission(EmployeeConfigJSON config) throws ParseException {
				    	
				    	System.out.println("entering  into process of setting permission for each role......."+ config.getPermission());
				    	config=EmployeeLogic.SetPermission(config);
				    	
				    		System.out.println("completed setting permission.......");	
				    	
				    	
				    	System.out.println("completed employee permission successfully.......");
				     	return Response.status(200).entity(config).build();
			}	   


			    	/*
				    * API for specifying permision for each role
				    */
				    
				    @POST
				    @Produces(value="application/json" )
				    @Path(value="/retrievePermission")
				    @Consumes(value="application/json")
				    
				    public Response retreivePermission(EmployeeConfigJSON config) throws ParseException {
				    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
					    
				    	System.out.println("entering  into process of setting permission for each role......."+ config.getPermission());
				    	reportAndCount=EmployeeLogic.RetreivePermission(config);
				    	
				    		System.out.println("completed setting permission.......");	
				    	
				    	
				    	System.out.println("completed employee permission successfully.......");
				     	return Response.status(200).entity(reportAndCount).build();
			}	   

	    	    
	    
	    

/*
 * API for removing department
 */
 
 @POST
 @Produces(value="application/json" )
 @Path(value="/deletedepartment")
 @Consumes(value="application/json")
 
 public Response deletedepartment(EmployeeConfigJSON config) throws ParseException {
 	System.out.println("entering  into process of deleting department.......");
 	config=EmployeeLogic.DeleteDeprtment(config);
 	
 	System.out.println("completed add department successfully.......");
  	return Response.status(200).entity(config).build();
}



/*
	    * API for removing role
	    */
	    
	    @POST
	    @Produces(value="application/json" )
	    @Path(value="/deleterole")
	    @Consumes(value="application/json")
	    
	    public Response deleterole(EmployeeConfigJSON config) throws ParseException {
	    	System.out.println("entering  into process of deleting role.......");
	    	config=EmployeeLogic.DeleteRole(config);
	    	
	    	System.out.println("completed add role successfully.......");
	     	return Response.status(200).entity(config).build();
}
	    
	    
	    
	    
	    /*
		 * API CALL FOR VERIFYING THE OTP
		 */
		@POST
		@Path("/verifyOTP")
		@Consumes(value= {"application/json"})
		public Response otpverify(EmployeeLoginJSON json)
		{   
		   
		    System.out.println((new StringBuilder("Otp Verification  ")).append(json.getEmailId().toString()));
		    
		    int  verify =EmployeeLogic.otpVerify(json);
		    System.out.println((new StringBuilder("verified ")).append(verify));
		    String result=Integer.toString(verify);
			return Response.status(200).entity(result).build();
			
			}
		
		
		
		/*
		 * API CALL FOR UPDATING THE NEW PASSWORD
		 */
		@POST
		@Path("/updatePassword")
		@Consumes(value= {"application/json"})
		public Response updatepswd(EmployeeLoginJSON json) {
			
			System.out.println((new StringBuilder("Updating password for  ")).append(json.getEmailId().toString()));
		    
		    int  update =EmployeeLogic.updatePassword(json);
		    System.out.println((new StringBuilder("Password Updated status:")).append(update));
		    String result=Integer.toString(update);
			return Response.status(200).entity(result).build();
			
		}
		
		
		/*
		 * API CALL FOR UPDATING THE NEW PASSWORD
		 */
		@POST
		@Path("/ReportingManagerDetails")
		@Consumes(value= {"application/json"})
		public Response ReportingManagerDetails(EmployeeMaintenanceJSON json) {
			System.out.println("Going to Retreive Reporting Manager Details");
			json=EmployeeLogic.ReportingManagerDetails(json);
			System.out.println("completed Reporting Manager Details");
			
		 
			return Response.status(200).entity(json).build();
			
		}
		
		
		
		
		


		/*
		 * API CALL FOR UPDATING THE NEW PASSWORD
		 */
		@POST
		@Path("/ReportingManagerEmpList")
		@Consumes(value= {"application/json"})
		public Response ReportingManagerEmpList(EmployeeLoginJSON details) {
			System.out.println("Going to Retreive Reporting Manager Details");
			 ArrayList<EmployeeConfigJSON> employeeList=new ArrayList<EmployeeConfigJSON>();
			    employeeList=EmployeeLogic.ReportManangerngEmployeeList(details);
		    	System.out.println("completed Reporting Manager Details");
			
		 
			return Response.status(200).entity(employeeList).build();
			
		}
		
		
		
		
/*
		 * API FOR AUTHORIZE PAGE DATA
		 * 
		 */
 		@POST
 		@Path("/leaveauthorize")
 		@Consumes(value= {"application/json"})
		public Response leaveauthorize(EmployeeLeaveJSON leave) {
			System.out.println("Leave Authorization \n");
			ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
			employeeLeavelist=EmployeeLeaveLogic.LeaveAuthorize(leave);
		    System.out.println("Leave  Authorization completed");
			return Response.status(200).entity(employeeLeavelist).build();
			
		}

		
		
}