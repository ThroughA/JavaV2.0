package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.util.DBUtil;
import com.fillingstation.logic.EmployeeLogic;

public class EmployeeShiftLogic {

	static String employeeRole=null;
	static String employeeId=null;
	static String employeeType=null;
	static String department=null;
	static String type=null;
	static String totalWorkHour;
	static String checkinTime = null;
	  static String checkoutTime = null;
	  static long second = 1000l;
	  static long minute = 60l * second;
	  static long hour = 60l * minute;
	  static Date checkinTimecal;
	  static Date checkoutTimecal;
	  static long difference;
	  static String hh;
	  static String mm;
	  static String ss;
	  static int validValue = 0;
	//  static String timeConstraint=String.format("%02d",6);
	  static String Status;
	  static String role;
	  static String dept;
	  static String previousDate;
	  
	  static String existingTotalWorkHour;
	  static String  inPauseTime;
	 static  String pauseTimeWorkHour;
	/*
	 * function to insert employee into DB 
	 * on clicking the checkin button
	 */
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
		try {
			
			System.out.println("got the employee details for checkin from employeetable............");
			connection =DBUtil.getDBConnection();
			details.setEmployeeName("NOT_VAILD");
			valid=vaildEmployeeId(details);
			if(valid==0) {
				
				blockValue=EmployeeLogic.BlockValidation(details.getCompanyId(),details.getEmployeeId());
				
				if(blockValue==0) {
				validValue=CheckinValidation(details);
				if(validValue==0) {
				System.out.println("Not Checked In today First CheckIn....");
				String querySelect=IQueryConstants.EMP_INDETAILS;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				    preparedStmt.setString(1,details.getEmployeeId());
				    preparedStmt.setString(2,details.getCompanyId());
				    ResultSet rs=preparedStmt.executeQuery();
				    while(rs.next()) {
				    	name=rs.getString("Name");
				    	type=rs.getString("Type");
				    	department=rs.getString("Department");
				    	shift=rs.getString("Shift");
				    }
				
				String querySelect1=IQueryConstants.EMP_CHECKIN_INSERT;
				PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
				preparedStmt1.setString(1,details.getCompanyId());
				preparedStmt1.setString(2,details.getEmployeeId());
				preparedStmt1.setString(3,name);
				preparedStmt1.setString(4,type);
				preparedStmt1.setString(5,details.getCheckInTime());
				preparedStmt1.setString(6,department);
				preparedStmt1.setString(7,details.getDate());
				preparedStmt1.executeUpdate();
				    System.out.println("updated the employee checkin details........");
				    details.setEmployeeName("");
				}else if(validValue==2){
					//leave but going to check in
					String querySelect=IQueryConstants.EMP_CHECKIN;
					PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
					    preparedStmt.setString(1,details.getCheckInTime());
					    preparedStmt.setString(2,details.getEmployeeId());
					    preparedStmt.setString(3,details.getDate());
					    preparedStmt.setString(4,details.getCompanyId());
					    preparedStmt.executeUpdate();
					    System.out.println("updated the employee checkin details........");
					    details.setEmployeeName("");
				}else {
				
					
				    
			    	int checkOutValid=CheckoutValidation(details);
					if(checkOutValid==1) {
						    int pauseValid=PauseinValidation(details);
						    if(pauseValid==0) {
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
								
								System.out.println("you are not checkedout already");
								details.setEmployeeName("ALREADY_CHECKIN");
								System.out.println("Already checkedIn");
							}
					
					}else {
						
					System.out.println("you are not checkedout already");
					details.setEmployeeName("ALREADY_CHECKIN");
					System.out.println("Already checkedIn");
				}
		    
				
			    System.out.println("completed checkin returing to webservice............");
			 
				connection.close(); 
				}
				}
				else {
					details.setEmployeeName("BLOCKED");
					System.out.println("employee id is blocked............");
				}
			}else {
				System.out.println("EmployeeId"+details.getEmployeeId()+" is not valid");
			}
			
			} catch (SQLException e)
	    {
	    e.printStackTrace();
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

private static int CheckinValidation(EmployeeAttendanceJSON details) {

	int flag = 1;
	Connection connection=null;
	String checkinTime = null;
	try {
		System.out.println("in checkIn validaation.........."+details.getEmployeeId()+" "+details.getDate()+" "+details.getCompanyId());
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.EMP_SELECT_CHECKINTIME;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,details.getEmployeeId());
		preparedStmt.setString(2,details.getDate());
		preparedStmt.setString(3,details.getCompanyId());
        ResultSet rs=preparedStmt.executeQuery();
       /*while(rs.next()) {
    	   checkinTime=rs.getString("CheckinTime");
    	   System.out.println("checkIn"+checkinTime);
       }*/
       if(!rs.next()) {
    	  
    	   flag=0;//NOT YET CHECKED IN
    	   System.out.println("checkIn"+flag);
           
       }
       else {
    	   System.out.println("st"+rs.getString("CheckinTime"));
    	   if(rs.getString("CheckinTime").equals("-")){
    		   flag=2;//Leave But he is Going to CheckIn
    	   }else{
    	   flag=1;//ALREADY CHECKED IN
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

/*
* function for checkout validation
*/
public static int CheckoutValidation(EmployeeAttendanceJSON details) {
	
	int flag = 1;
	Connection connection=null;
	String checkinTime = null;
	try {
		System.out.println("in checkout validaation..........");
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.EMP_SELECT_CHECKOUTTIME;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,details.getEmployeeId());
		preparedStmt.setString(2,details.getCompanyId());
		preparedStmt.setString(3,details.getDate());
		
        ResultSet rs=preparedStmt.executeQuery();
       while(rs.next()) {
    	   checkoutTime=rs.getString("CheckoutTime");
       }
       if(checkoutTime.equals("-")) {
    	   flag=0; //Not checked out Already
       }
       else {
    	   flag=1;//checked out Already
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

public static int PauseinValidation(EmployeeAttendanceJSON details) {

	int flag = 1;
	Connection connection=null;
	String checkinTime = null;
	try {
		System.out.println("in checkIn validaation..........");
		connection=DBUtil.getDBConnection();
		
		String querySelect=IQueryConstants.EMP_SELECT_INPAUSETIME;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,details.getEmployeeId());
		preparedStmt.setString(2,details.getDate());
		preparedStmt.setString(3,details.getCompanyId());
        ResultSet rs=preparedStmt.executeQuery();
       while(rs.next()) {
    	   checkinTime=rs.getString("InPauseTime");
    	   System.out.println("InPauseTime"+checkinTime);
       }
       if(checkinTime.equals("-")) {
    	   flag=0;//NOT YET Paused IN
    	   System.out.println("InPauseTime"+flag);
           
       }
       else {
    	   flag=1;//ALREADY Paused IN

       }
        
	} catch (Exception e) {
		e.printStackTrace();
	  }finally {
		DBUtil.closeConnection(connection);
	    }
	System.out.println("result flag..."+flag);
	   
	return flag;
}



	/*
	 * function to calculate the total work hour on clicking the checkout button
	 * after calculation checkouttime and total work hour will be updated into DB
	 */

	public static int EmployeeCheckout(EmployeeAttendanceJSON details) throws ParseException, SQLException {
		
		Connection connection=null;
		try {
			System.out.println("getting the employee details for calculating total working hours............");
			connection =DBUtil.getDBConnection();
			details.setEmployeeName("NOT_VAILD");
			int valid=EmployeeLogic.vaildEmployeeId(details);
			if (valid==0) {
			
				int validValue1=CheckoutValidation(details);
				if(validValue1==0) {
				
			validValue=CheckinValidation(details);
			System.out.println("return flag value........."+validValue);
			if(validValue==1)
				{ 
				System.out.println("calling totalwork calculation function..........");
				EmployeeLogic.TotalWorkCalculation(details,connection);
				}
			else {
				details.setEmployeeName("NOT_CHECKED_IN");
				System.out.println("employee not checked in so checkout is not permitted..............");
			}
		}else{
			
			int pauseValid=PauseinValidation(details);
			if(pauseValid==1) {
				 EmployeeLogic.MultipleTotalWorkCalculation(details,connection);
		    	 details.setEmployeeName("");
		    }else{
			
			System.out.println("already checkedout");
			details.setEmployeeName("ALREADY_CHECKOUT");
		    }
			/*
			details.setEmployeeName("ALREADY_CHECKOUT");
			System.out.println("employee Id "+details.getEmployeeId()+ " is already checked out............");
			*/
		}
				
		}
			else {
			
				System.out.println("employee Id "+details.getEmployeeId()+ " is not valid............");
			}	
	    
				
			}
			
	   finally {
		DBUtil.closeConnection(connection);
	}
		return validValue;
	}

	
}