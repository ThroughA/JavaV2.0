package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.util.DBUtil;

public class CheckInCheckOutLogic {

	static long second = 1000l;
	  static long minute = 60l * second;
	  static long hour = 60l * minute;
	 
public static int EmployeeCheckin(EmployeeAttendanceJSON details) {
		
		Connection connection=null;
		String firstName = null;
		String lastName = null;
		String name = null;
		int valid = 0;
		int blockValue=0;
		String type=null;
		String department=null;
		String shift=null;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			
			System.out.println("got the employee details for checkin from employeetable............");
			connection =DBUtil.getDBConnection();
			details.setEmployeeName("NOT_VAILD");
			valid=vaildEmployeeId(details);
			if(valid==0) {
				
				blockValue=EmployeeLogic.BlockValidation(details.getCompanyId(),details.getEmployeeId());
				
				if(blockValue==0) {
					
					
				 SessionValidation(details);
				}
				else {
					details.setEmployeeName("BLOCKED");
					System.out.println("employee id is blocked............");
				}
			}else {
				System.out.println("EmployeeId"+details.getEmployeeId()+" is not valid");
			}
			
			} 
	     	
	   finally {
		DBUtil.closeConnection(connection);
	}
		return valid;
	}



/*
* Checking whether employee id is valid for checkin and checkout
*/

public static int vaildEmployeeId(EmployeeAttendanceJSON details) {

int flag=0;//TRue
Connection connection=null;

try {
	connection=DBUtil.getDBConnection();
	String querySelect=IQueryConstants.VALID_EMP_ID;
	PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
	preparedStmt.setString(1,details.getEmployeeId());

	preparedStmt.setString(2,details.getCompanyId());
	
    ResultSet result=preparedStmt.executeQuery();
   
    if(!result.next()) {
    	flag=1; //set the flag value 0 if it is valid email id otherwise it return 1 means invalid
    	
    }
} catch (Exception e) {
	e.printStackTrace();
  }finally {
	DBUtil.closeConnection(connection);
    }
    
 return flag;   
}

/*
 * function to check whether employee is checked in before checking out,
 * checking whether the employee is checked in already to prevent more than one checkin and
 * checkout without checkin once 
 */

private static int SessionValidation(EmployeeAttendanceJSON details) {

	int flag = 1;
	Connection connection=null;
	String session = "-";
	 Date sessionCal;
	 Date checkInCal;
	  String checkOut="-";
	  String inPauseTime="-";
	 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	    
	try {
		System.out.println("in checkIn validaation.........."+details.getEmployeeId()+" "+details.getDate()+" "+details.getCompanyId());
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.EMP_SESSION_DETAILS;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,details.getEmployeeId());
		preparedStmt.setString(2,details.getCompanyId());
        ResultSet rs=preparedStmt.executeQuery();
       while(rs.next()) {
    	   session=rs.getString("Session");
    	   checkOut=rs.getString("CheckoutTime");
    	    inPauseTime = rs.getString("InPauseTime");
    	   System.out.println("checkIn"+session);
       }
       if(session.equals("-")){
    	   flag=0;
    	   System.out.println("NOt checkIn"+flag);
    	   FirstCheckIn(details);
           
       }else{
    	   sessionCal=format.parse(session);
    	   checkInCal=format.parse(details.getCheckInTime());
    	   int result=sessionCal.compareTo(checkInCal);
    	   System.out.println("NOt checkIn"+flag);
    	   if(result>=0) {
    		   flag=0;//NOT YET CHECKED IN
        	    
    		   FirstCheckIn(details);
    	         
		    }
    	   else{
    		   flag=1;//ALREADY CHECKED IN
    		   System.out.println("Already checkIn"+flag);
    		   
    		   if(checkOut.equals("-")) {
    	    	   flag=0; //Not checked out Already
    	    	   System.out.println("you are not checkedout already");
					details.setEmployeeName("ALREADY_CHECKIN");
					System.out.println("Already checkedIn");
    	       }
    	       else {
    	    	   flag=1;//checked out Already
    	    	   if(inPauseTime.equals("-")) {
    	        	   flag=0;//NOT YET Paused IN
    	        	   System.out.println("InPauseTime"+flag);
    	        	   System.out.println("Already checkedIn and Checked OUt so pausing checkin time");
				    	String querySelect2=IQueryConstants.EMP_PAUSETIMEIN_UPDATE;
						PreparedStatement preparedStmt2= connection.prepareStatement(querySelect2);
						 preparedStmt2.setString(1,details.getCheckInTime());   
						preparedStmt2.setString(2,details.getCompanyId());
						preparedStmt2.setString(3,details.getEmployeeId());
						preparedStmt2.setString(4,details.getDate());
						preparedStmt2.executeUpdate();
						System.out.println("paused time for first time is inserted");
						details.setEmployeeName("");
    	               
    	           }
    	           else {
    	        	   flag=1;//ALREADY Paused IN
    	        	   System.out.println("you are not checkedout already");
						details.setEmployeeName("ALREADY_CHECKIN");
						System.out.println("Already checkedIn");
    	           }
    	       }
             
    	   }
       
    	   }
	    
       
	} catch (Exception e) {
		e.printStackTrace();
	  }finally {
		DBUtil.closeConnection(connection);
	    }
	System.out.println("result flag..."+flag);
	   
	return flag;
}


private static int FirstCheckIn(EmployeeAttendanceJSON details) throws SQLException{
	 String name = null;
		String type = null;
		String department = null;
		Connection connection=null;
		connection=DBUtil.getDBConnection();
		
	   System.out.println("Not Checked In today First CheckIn....");
		String firstInsert=IQueryConstants.EMP_INDETAILS;
		PreparedStatement preparedStmt0 = connection.prepareStatement(firstInsert);
		    preparedStmt0.setString(1,details.getEmployeeId());
		    preparedStmt0.setString(2,details.getCompanyId());
		    ResultSet rs0=preparedStmt0.executeQuery();
		    
			while(rs0.next()) {
		    	name=rs0.getString("Name");
		    	type=rs0.getString("Type");
		    	department=rs0.getString("Department");
		    	String shift = rs0.getString("Shift");
		    }
		
		String querySelect1=IQueryConstants.EMP_SESSION_CHECKIN_INSERT;
		PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
		preparedStmt1.setString(1,details.getCompanyId());
		preparedStmt1.setString(2,details.getEmployeeId());
		preparedStmt1.setString(3,name);
		preparedStmt1.setString(4,type);
		preparedStmt1.setString(5,details.getCheckInTime());
		preparedStmt1.setString(6,details.getCheckInTime());
		preparedStmt1.setString(7,department);
		preparedStmt1.setString(8,details.getDate());
		preparedStmt1.executeUpdate();
		    System.out.println("updated the employee checkin details........");
		    details.setEmployeeName("");
			return 0;
	 }


	
}
