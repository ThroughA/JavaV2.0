package com.fillingstation.webservice;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.json.ShiftConfigJSON;
import com.fillingstation.logic.EmployeeLogic;
import com.fillingstation.logic.EmployeeShiftConfigLogic;

@Path(value="/employeeshiftconfig")
public class EmployeeShiftConfigWebService {

	

/*
 * API call for getting existing config data from table
 */

@POST
@Path("/getshiftconfigdata")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response getshiftconfigdata (ShiftConfigJSON json)throws ParseException, SQLException
{  
	   
	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	ArrayList<ShiftConfigJSON> shiftData = new ArrayList<ShiftConfigJSON>();
	
	ShiftConfigJSON shift=new ShiftConfigJSON();
	shiftData =EmployeeShiftConfigLogic.ConfigGet(json);
	reportAndCount.setShiftData(shiftData);
    System.out.println(json);
	return Response.status(200).entity(reportAndCount).build();
	
	}  

/*
	 * API call for inserting new config data initially into table
	 */
	
	@POST
	@Path("/shiftconfiginitialinsert")
	@Consumes(value= {"application/json"})
	@Produces(value={"application/json"})
	public Response shiftconfiginitialinsert (ShiftConfigJSON json)throws ParseException, SQLException
	{  
		   
		EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
		ShiftConfigJSON shift=new ShiftConfigJSON();
		shift =EmployeeShiftConfigLogic.ConfigInitialInsert(json);
		return Response.status(200).entity(reportAndCount).build();
		
		}
/*
 * API call for inserting new config data into table
 */

@POST
@Path("/shiftconfiginsert")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response shiftconfiginsert (ShiftConfigJSON json)throws ParseException, SQLException
{  
	   
	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	ShiftConfigJSON shift=new ShiftConfigJSON();
	shift =EmployeeShiftConfigLogic.ConfigInsert(json);
    System.out.println(json);
	return Response.status(200).entity(shift).build();
	
	}

/*
 * API call for updating existing config data into table
 */

@POST
@Path("/shiftconfigupdate")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response shiftconfigupdate (ShiftConfigJSON json)throws ParseException, SQLException
{  
	   
	EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	ShiftConfigJSON shift=new ShiftConfigJSON();
	shift =EmployeeShiftConfigLogic.ConfigUpdate(json);
    System.out.println(json);
	return Response.status(200).entity(reportAndCount).build();
	
	} 

/*
 * API call for updating existing Shift data into table
 */

@POST
@Path("/ShiftUpdate")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response shiftupdate (ShiftConfigJSON json)throws ParseException, SQLException
{  
	   
	
	ShiftConfigJSON shift=new ShiftConfigJSON();
	shift =EmployeeShiftConfigLogic.ShiftUpdate(json);
    System.out.println(json);
	return Response.status(200).entity(shift).build();
	
	} 

/*
 * API call for deleting existing config data in table
 */

@POST
@Path("/shiftconfigdelete")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response shiftconfigdelete (ShiftConfigJSON json)throws ParseException, SQLException
{  
	   System.out.println("datas:\n"+json.getShift() +"\t"+json.getFrom() +"\t" +json.getTo());
	   json =EmployeeShiftConfigLogic.ConfigDelete(json);
	return Response.status(200).entity(json).build();
	
	} 


/*
 * API call for shift mode change
 */

@POST
@Path("/shiftstrictmode")
@Consumes(value= {"application/json"})
@Produces(value={"application/json"})
public Response shiftstrictmode (ShiftConfigJSON json)throws ParseException, SQLException
{  
	     EmployeeReportAndCount reportAndCount=new EmployeeReportAndCount();
	   ShiftConfigJSON shift=new ShiftConfigJSON();
	shift =EmployeeShiftConfigLogic.ShiftStrictMode(json);
	return Response.status(200).entity(shift).build();
	
	}
	
}
