package com.fillingstation.webservice;

import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeMaintenanceJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.logic.Report;


@Path(value="/EmployeeReport")
public class EmployeeReportWebService {

	 /*
     * API for employee individual daily attendance report
     */
    
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeIndividualAttendanceDailyReport")
    @Consumes(value="application/json")
    public Response employeeindividualdailyreport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee daily report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeindividualDailyReport(details);
    	
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
    	  System.out.println("generated employee daily report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
}



    /*
     * API for employee Organization daily report
     */
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeOrganizationAttendanceDailyReport")
    @Consumes(value="application/json")
    public Response employeeOrganizationAttendanceDailyReport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee daily report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeOrganizationDailyReport(details);
    	/*employeeCountRetrievelist=Report.EmployeeDailyReportSummary(details);
    	for( EmployeeReportJSON employeeRetrievelist1 :employeeCountRetrievelist  ) {
    		System.out.println(employeeRetrievelist1.getEmployeeId());
    	}*/
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
        System.out.println("generated employee daily report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
    }
	

    /*
     * API for employee Individual Period report
     */
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeIndividualAttendancePeriodReport")
    @Consumes(value="application/json")
    public Response employeeindividualperiodreport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee Period report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeIndividualPeriodReport(details);
    	//employeeSummarylist=Report.EmployeeIndividualPeriodReportSummary(details,details.getEmployeeId());
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
    	//reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
    	System.out.println("generated employee Period report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
    }
    

    /*
     * API for employee Organization Period report
     */
    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeOrganizationAttendancePeriodReport")
    @Consumes(value="application/json")
    public Response employeeorganizationperiodreport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee Period report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeOrganizationPeriodReport(details);
    	//employeeSummarylist=Report.EmployeeOrganizationPeriodReportSummary(details);
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
    	//reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
    	System.out.println("generated employee Period report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
    }

 /*
     * API for employee individual monthly report
     */

    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeIndividualAttendanceMonthlyReport")
    @Consumes(value="application/json")
    public Response employeindividualemonthlyreport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee period report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeIndividualPeriodReport(details);
    	//employeeSummarylist=Report.EmployeeIndividualPeriodReportSummary(details,details.getEmployeeId());
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
    	//reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
    	System.out.println("generated employee period report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
    }
    
    /*
     * API for employee Organization Monthly report
     */

    @POST
    @Produces(value="application/json" )
    @Path(value="/employeeOrganizationAttendanceMonthlyReport")
    @Consumes(value="application/json")
    public Response employeeorganizationmonthlyreport(EmployeeMaintenanceJSON details) throws ParseException {
    	System.out.println("going to generate employee period report details.......");
    	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
    	employeeRetrievelist=Report.EmployeeOrganizationPeriodReport(details);
    	//employeeSummarylist=Report.EmployeeOrganizationPeriodReportSummary(details);
    	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
    	reportAndCount.setEmployeeRetrievelist(employeeRetrievelist);
    	//reportAndCount.setEmployeeCountRetrievelist(employeeSummarylist);
    	System.out.println("generated employee period report details successfully.......");
     	return Response.status(200).entity(reportAndCount).build();
    }





	
}
