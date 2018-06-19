package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.json.EmployeeLeaveJSON;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.util.DBUtil;

public class EmployeeLeaveLogic {

	
	static String emailId = null;
	static String employeeId;
	static String companyId;
	static String reportingManagerMailId;
	static String days;
	static String date;
	static String fromdate;
	static String todate;
	static String subject;

/*
public static void LeaveConfig(EmployeeLeaveJSON leave) {
	
	Connection connection=null;
	try {
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.INSERTLEAVECONFIG;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1, "leave");
		preparedStmt.setString(2, "leave");
		preparedStmt.executeUpdate();
		int flag=0;
	}
	catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}	
}
*/
	
	/*
	*FUNCTION FOR GETTING LEAVE DAYS BASED ON LEAVE TYPE OPTED
	*/
public static String GetDays(EmployeeLeaveJSON leave) {
		String tableName=leave.getCompanyId()+"LeaveTable";
		String days=null;
		 Connection connection=null;
		 try {
				connection=DBUtil.getDBConnection();
				String querySelect1=IQueryConstants.GET_EMPLEAVEDAYS.replace("$columnName", leave.getLeaveType()).replace("$tableName", tableName);
				PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
				preparedStmt1.setString(1,leave.getEmployeeId());
				ResultSet rs1=preparedStmt1.executeQuery();
				while(rs1.next()) {
					days=rs1.getString(leave.getLeaveType());
						}
				connection.close(); 
	}
	catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
		 return days;
}
	

/*
*FUNCTION FOR SELECTING LEAVETYPE FOR THE EMPLOYEE REQUESTING LEAVE
*/


public static ArrayList<EmployeeLeaveConfigJSON> GetLeaveType(EmployeeLeaveConfigJSON leave) {
String tableName=leave.getCompanyId()+"LeaveTable";
String days=null;
 Connection connection=null;
   ArrayList<EmployeeLeaveConfigJSON> leaveTypeList = new ArrayList<EmployeeLeaveConfigJSON>();
   ArrayList<EmployeeLeaveConfigJSON> leaveTypeList1 = new ArrayList<EmployeeLeaveConfigJSON>();
	System.out.println("CompanyId: \t"+leave.getCompanyId());
			try {
				connection=DBUtil.getDBConnection();
				System.out.println("getting leave type \n");
				String querySelect=IQueryConstants.GET_LEAVETYPE;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getCompanyId());
				ResultSet rs=preparedStmt.executeQuery();
				while(rs.next()) {
					EmployeeLeaveConfigJSON leaveinfo=new EmployeeLeaveConfigJSON();
					leaveinfo.setLeaveType(rs.getString("LeaveType"));
					System.out.println("leave type: \t"+leaveinfo.getLeaveType()+"leaveDays: \t"+leaveinfo.getDays());
					leaveTypeList.add(leaveinfo);
				}
				for(EmployeeLeaveConfigJSON leavetype:leaveTypeList) {
					System.out.println("LeaveType: \t"+leavetype.getLeaveType());
					String querySelect1=IQueryConstants.GET_EMPLEAVETYPE.replace("$columnName", leavetype.getLeaveType()).replace("$tableName", tableName);
					PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
					preparedStmt1.setString(1,leave.getEmployeeId());
					ResultSet rs1=preparedStmt1.executeQuery();
					while(rs1.next()) {
						EmployeeLeaveConfigJSON leaveinfo=new EmployeeLeaveConfigJSON();
						days=rs1.getString(leavetype.getLeaveType());
						leaveinfo.setLeaveType(leavetype.getLeaveType());
						leaveTypeList1.add(leaveinfo);
					}
				
				}
				connection.close(); 
				System.out.println("getting leave type completed \n");
			}
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}
			return leaveTypeList1;

}



public static void LeaveRequest(EmployeeLeaveJSON leave) {
	Connection connection=null;
	String employeeName=SelectName(leave);
	String tableName=leave.getCompanyId()+"LeaveTable";
	String days = null;
	int newDays;
	
	try {
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.LEAVEINFO;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,employeeName);
		preparedStmt.setString(2,leave.getEmployeeId());
		preparedStmt.setString(3,leave.getCompanyId());
		preparedStmt.setString(4,leave.getNoOfDays());
		preparedStmt.setString(5,leave.getDate());
		preparedStmt.setString(6,leave.getFromDate());
		preparedStmt.setString(7,leave.getToDate());
		preparedStmt.setString(8,leave.getSubject());
		preparedStmt.setString(9,leave.getReportingManagerId());
		preparedStmt.setString(10,leave.getLeaveType());
		preparedStmt.executeUpdate();
		LeaveReduce(leave,"request");
		
		connection.close();
	}
	 
	catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	
}

/*
*FUNCTION FOR REDUCING THEE LEAVE DAYS ON SUBMITTING THE REQUEST
*/

private static void LeaveReduce(EmployeeLeaveJSON leave,String action) {
Connection connection=null;
String tableName=leave.getCompanyId()+"LeaveTable";
String days = null;
int newDays1 = 0;
double newDays = 0 ;

try {
connection=DBUtil.getDBConnection();
System.out.println("Getting leave days of employeeId"+leave.getEmployeeId());
System.out.println("Getting leave type of employee"+leave.getLeaveType());
System.out.println("Getting leave days of employee"+leave.getNoOfDays());

String querySelect0=IQueryConstants.GET_EMPLEAVEDAYS.replace("$columnName", leave.getLeaveType()).replace("$tableName",tableName);
PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
preparedStmt0.setString(1,leave.getEmployeeId());
ResultSet rs=preparedStmt0.executeQuery();
while(rs.next()) {
days=rs.getString(leave.getLeaveType());	
}
System.out.println("leave days of employee"+days);

try {

if(action.equals("request")) {
System.out.println("subtracting since request \n");
newDays=Double.parseDouble(days)-Double.parseDouble(leave.getNoOfDays());
}else {
System.out.println("adding since rejected \n");
newDays=Double.parseDouble(days)+Double.parseDouble(leave.getNoOfDays());

}
}catch(NumberFormatException ex) {


}
System.out.println("Got leave days of employee \t"+leave.getLeaveType());


System.out.println("Reducing Leave of the Employee for authorized \t"+leave.getLeaveType());
String querySelect1=IQueryConstants.EMPLEAVECHANGE.replace("$columnName", leave.getLeaveType()).replace("$tableName",tableName);
PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
preparedStmt1.setDouble(1,newDays);
preparedStmt1.setString(2,leave.getEmployeeId());
preparedStmt1.executeUpdate();

System.out.println("Reduced Leave of the Employee for authorized \t"+leave.getLeaveType());

connection.close();
}catch (Exception e) {
e.printStackTrace();
} finally {
DBUtil.closeConnection(connection);
}

}


/*
   public static EmployeeLoginJSON login(EmployeeLoginJSON json) {
	
	Connection connection=null;
	EmployeeLoginJSON leaveLogin=new EmployeeLoginJSON();
	try {
		 
		connection=DBUtil.getDBConnection();
		String querySelect=IQueryConstants.LEAVELOGIN;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,json.getPassword());
		preparedStmt.setString(2,json.getEmailId());
		ResultSet rs=preparedStmt.executeQuery();
		while(rs.next()) {
			leaveLogin.setEmployeeId(rs.getString("EmployeeId"));
			leaveLogin.setCompanyId(rs.getString("CompanyId"));
			leaveLogin.setReportingManagerId(rs.getString("ReportingManagerId"));
		 
		}	
		connection.close();
	}
	catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	return leaveLogin;
}

*/
	public static String OneDayMailBody(EmployeeLeaveJSON leave) {
		String employeeName=SelectName(leave);
		String body="\n EmployeeId:"+leave.getEmployeeId()
		  		+"\n EmployeeName:"+employeeName
		  		+"\n Date:"+leave.getDate()
		  		+"\n Session:"+leave.getNoOfDays()
		  		+"\n LeaveType:"+leave.getLeaveType()
		  		+"\n Subject:"+leave.getSubject();
		
				return body;
	}

	public static String MoreDayMailBody(EmployeeLeaveJSON leave) {
		String employeeName=SelectName(leave);	
		String body="\n EmployeeId:"+leave.getEmployeeId()
		  		+"\n EmployeeName:"+employeeName
		  		+"\n From:"+leave.getFromDate()
		  		+"\n To:"+leave.getToDate()
		  		+"\n LeaveType:"+leave.getLeaveType()
		  		+"\n No.Of.Days:"+leave.getNoOfDays()
		  		+"\n Subject:"+leave.getSubject();

	return body;
	}

	public static void LeaveConfig(EmployeeLeaveJSON leave) {
		// TODO Auto-generated method stub
		
	}

	public static EmployeeLeaveJSON SelectMailId(EmployeeLeaveJSON leave) {
		Connection connection=null;
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		 String repmanempid = null;
	        String repmanempemailid=null;
	       
			try {
			connection=DBUtil.getDBConnection();
			
			String querySelect=IQueryConstants.REPORTING_MANAGER_ID ;
			
		  	PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getEmployeeId());
			preparedStmt.setString(2,leave.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
	        while(rs.next())
	        {
	        	repmanempid = rs.getString("ReportingManagerId");
	        	System.out.println("repmanager" + "\t" + repmanempid) ;
	        }
	        System.out.println("inserting data into DB \n");
		    
	        leave.setReportingManagerId(repmanempid);
	        EmployeeLeaveLogic.LeaveRequest(leave);
	        System.out.println("inserted data into DB \n");	
	        String querySelect1=IQueryConstants.REPORTING_MANAGER_EMAILID  ;
			PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
			preparedStmt1.setString(1,leave.getCompanyId());
			preparedStmt1.setString(2,repmanempid);
			ResultSet rs1=preparedStmt1.executeQuery();
			while(rs1.next())	
	        {
				repmanempemailid = rs1.getString("EmailID");
	        	System.out.println("repmanager emailid"   + "\t"+ repmanempemailid) ;
	        }
			leave.setReportingManagerEmailId(repmanempemailid);
			connection.close(); 
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		System.out.println("reporting manager mailId: \t"+emailId);
		return leave;

	}
	public static String SelectName(EmployeeLeaveJSON leave) {
		String employeeName = null;
		Connection connection=null;
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			String querySelect=IQueryConstants.SELECTEMPNAME;
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getEmployeeId());
			preparedStmt.setString(2,leave.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				employeeName=rs.getString("Name");
				
			}
			connection.close(); 
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return employeeName;

	}

	public static ArrayList<EmployeeLeaveJSON> LeaveAuthorize(EmployeeLeaveJSON leave) {
		Connection connection=null;
		System.out.println("in leave authorize function");
		ArrayList<EmployeeLeaveJSON> employeeLeavelist = new ArrayList<EmployeeLeaveJSON>();
		String ReportingManagerEmailId=leave.getReportingManagerEmailId();
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			
			connection=DBUtil.getDBConnection();
			String querySelect=IQueryConstants.SELECTLEAVEDATA;
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getReportingManagerId());
			preparedStmt.setString(2,leave.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				System.out.println("in leave authorize function6");
				
				if(rs.getString("Subject")!=null) {
				EmployeeLeaveJSON leaveData=new EmployeeLeaveJSON();
				leaveData.setEmployeeName(rs.getString("Name"));
				leaveData.setEmployeeId(rs.getString("EmployeeId"));
				System.out.println("employeeid"+leaveData.getEmployeeId());
				leaveData.setDay(rs.getString("Days"));
				leaveData.setDate(rs.getString("Date"));
				leaveData.setFromDate(rs.getString("FromDate"));
				leaveData.setToDate(rs.getString("ToDate"));
				leaveData.setSubject(rs.getString("Subject"));
				leaveData.setCompanyId(rs.getString("CompanyId"));
				employeeLeavelist.add(leaveData);
				}
			}
			connection.close(); 
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		System.out.println("leaving leave authorize function");
		return employeeLeavelist;

	}

	public static void LeaveAuthorized(EmployeeLeaveJSON leave) {
		Connection connection=null;
		System.out.println("leave authorized \n");
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("Update Accepted Status to Leave Request\n"+leave.getEmployeeId()+"1 "+leave.getCompanyId() +"2 "+leave.getNoOfDays()+ "3 "+leave.getFromDate() +" 4"+ leave.getToDate()+ " 5"+ leave.getSubject()+ " 6");
			//Query to Set Request Status to 1 (Indicate Leave Status Accepted)
			String querySelect=IQueryConstants.EMPLEAVEAUTHORIZE;
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getEmployeeId());
			preparedStmt.setString(2,leave.getCompanyId());
			preparedStmt.setString(3,leave.getNoOfDays());
			//preparedStmt.setString(4,leave.getDate());
			preparedStmt.setString(4,leave.getFromDate());
			preparedStmt.setString(5,leave.getToDate());
			preparedStmt.setString(6,leave.getSubject());
			preparedStmt.setString(7,leave.getReportingManagerId());
			
			preparedStmt.executeUpdate();
			
			if(!leave.getDate().equals("-")) {
				System.out.println("inserting authorized leave data into Attendance table FOR 1 Day \n");
			
				
				System.out.println("The info to  be added are:\n"+leave.getEmployeeId()+"\t"+leave.getCompanyId()
				+"\t"+leave.getDate()+"\t"+"\t"+leave.getEmployeeName());
			
				
				AttendanceTableInsert(leave,leave.getDate());
				
			}else {
			
			System.out.println("inserting authorized leave data into Attendance table FOR more than 1 Day \n");
			
			System.out.println("From Date"+leave.getFromDate());
			System.out.println("To Date"+leave.getToDate());
			
			
			
			
			
			List<Date> dates = new ArrayList<Date>();

			DateFormat formatter ; 

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date  startDate = (Date)formatter.parse(leave.getFromDate()); 
			Date  endDate = (Date)formatter.parse(leave.getToDate());
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			for(int i=0;i<dates.size();i++){
			    Date lDate =(Date)dates.get(i);
			    String date = formatter.format(lDate);    
			    System.out.println(" Date is ..." + date);
			    AttendanceTableInsert(leave,date);
			    
		}
						
}	connection.close(); 
			
		}
		
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		System.out.println("leaving leave authorization function");
		

		
	}
private static void AttendanceTableInsert(EmployeeLeaveJSON leave, String date) {
		
		Connection connection=null;
		String type=null;
		String department=null;
			try {
		
				System.out.println("inside innserting function \n");
				connection=DBUtil.getDBConnection();
				
				String querySelect=IQueryConstants.SELECTEMPTYPE;	
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getEmployeeId());
				preparedStmt.setString(2,leave.getCompanyId());
				
				ResultSet rs=preparedStmt.executeQuery();
			    while(rs.next()) {
			    	type=rs.getString("Type");
			    	department=rs.getString("department");
			    }
				System.out.println("The info to  be added are:\n"+type+"\t"+leave.getEmployeeId()+"\t"+leave.getCompanyId()
				+"\t"+date+"\t"+"\t"+leave.getEmployeeName());
			    
			    
			    
		String querySelect1=IQueryConstants.EMPLEAVEAUTHORIZEINSERT;
		PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
		preparedStmt1.setString(1,leave.getEmployeeId());
		preparedStmt1.setString(2,leave.getCompanyId());
		preparedStmt1.setString(3,date);
		preparedStmt1.setString(4,leave.getEmployeeName());
		preparedStmt1.setString(5,type);
		preparedStmt1.setString(6, department);
		preparedStmt1.executeUpdate();
		
		connection.close(); 
		
			}
			
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}

	}


	public static void LeaveNotAuthorized(EmployeeLeaveJSON leave) {
		Connection connection=null;
		System.out.println("leave Not Authorized \n");
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("Update Accepted Status to Leave Request\\n");
			//Query to Set Request Status to 1 (Indicate Leave Status Accepted)
			String querySelect=IQueryConstants.EMPLEAVENOTAUTHORIZE;
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getEmployeeId());
			preparedStmt.setString(2,leave.getCompanyId());
			preparedStmt.setString(3,leave.getNoOfDays());
			//preparedStmt.setString(4,leave.getDate());
			preparedStmt.setString(4,leave.getFromDate());
			preparedStmt.setString(5,leave.getToDate());
			preparedStmt.setString(6,leave.getSubject());
			preparedStmt.setString(7,leave.getReportingManagerId());
			preparedStmt.setString(8,leave.getLeaveType());
			
			LeaveReduce(leave,"reject");//FUNCTION CALL NEED TO BE ADDED
			
			preparedStmt.executeUpdate();
			
			connection.close(); 
			
		}
		
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		System.out.println("leaving leave Not authorization function");
		
	}

	public static String SelectEmployeeMailId(EmployeeLeaveJSON leave) {
		Connection connection=null;
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			String querySelect=IQueryConstants.SELECTMAILID;
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getEmployeeId());
			preparedStmt.setString(2,leave.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				emailId=rs.getString("EmailId");
				
			}
			connection.close(); 
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return emailId;

		
	}
	/*
	 * function for getting configured leave data
	 */
	public static ArrayList<EmployeeLeaveConfigJSON> GetLeaveInfo(EmployeeLeaveConfigJSON leave) {
		   Connection connection=null;
		   ArrayList<EmployeeLeaveConfigJSON> employeeLeavelist = new ArrayList<EmployeeLeaveConfigJSON>();
			
					try {
						connection=DBUtil.getDBConnection();
						System.out.println("getting leave info \n");
						String querySelect=IQueryConstants.GET_LEAVE;
						PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
						preparedStmt.setString(1,leave.getCompanyId());
						ResultSet rs=preparedStmt.executeQuery();
						while(rs.next()) {
							EmployeeLeaveConfigJSON leaveinfo=new EmployeeLeaveConfigJSON();
							leaveinfo.setLeaveType(rs.getString("LeaveType"));
							leaveinfo.setDays(rs.getString("Days"));
							employeeLeavelist.add(leaveinfo);
							System.out.println("getting leave info \n"+employeeLeavelist.toString());
						}
						connection.close(); 
						System.out.println("Selecting leave completed \n");
					}
					catch (Exception e) {
							e.printStackTrace();
					} finally {
							DBUtil.closeConnection(connection);
					}
					return employeeLeavelist;
	}





	/*
	 * function for adding leave info
	 */
	
	public static String AddLeaveInfo(EmployeeLeaveConfigJSON leave) {
			
			Connection connection=null;
			String description="New";
			String tableName=leave.getCompanyId()+"LeaveTable";
			int check=0;
			int leaveType = 0;
			String leaveTypeSelect=null;
			System.out.println("adding leave info \n");
				try {
				connection=DBUtil.getDBConnection();
				System.out.println("checking whether the column already exist in  \t"+tableName);
				DatabaseMetaData md = connection.getMetaData();
				ResultSet rs = md.getColumns(null, null,tableName,leave.getLeaveType());
				 if (rs.next()) {
				      System.out.println(leave.getLeaveType()+"\t Already Exist \n");
				      description="Already Exist";
				      System.out.println("Leave type Already exist checking whether the status is 1 \n" );
				      
				  	String querySelect=IQueryConstants.SELECT_LEAVESTATUS;
					PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
					//preparedStmt.setString(1,leave.getNoofLeave());
					preparedStmt.setString(1,leave.getCompanyId());
					preparedStmt.setString(2,leave.getLeaveType());
					ResultSet rs1=preparedStmt.executeQuery();
					while(rs1.next()) {
						String status=rs1.getString("LeaveStatus");
						check=Integer.parseInt(status);
					}
					
		            if(description.equals("Already Exist") && check ==1) {
		              	description="New";
		            	
		            		String querySelect1=IQueryConstants.UPDATE_LEAVETYPEEMPCONFIG;
							PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
							preparedStmt1.setString(1,leave.getNoofLeave());
							preparedStmt1.setString(2,leave.getCompanyId());
							preparedStmt1.setString(3,leave.getLeaveType());
							preparedStmt1.executeUpdate();
							String querySelect2=IQueryConstants.UPDATE_LEAVETYPELEAVE.replace("$tableName", tableName).replace("$leaveType", leave.getLeaveType());
							PreparedStatement preparedStmt2=connection.prepareStatement(querySelect2);
							preparedStmt2.setString(1,leave.getNoofLeave());
							preparedStmt2.executeUpdate();
							EmployeeLogic.AuditReport(leave.getSuperiorId(), leave.getLeaveType()+" For "+leave.getNoofLeave(), "Declared New Leave", leave.getCompanyId());
							
							
					
		            	 }else {
		            		 
		            		 description="Already Exist";
		            	 }
				    		
				 }else {
				    	System.out.println("Adding the new leave type to EmployeeTable \t"+leave.getLeaveType());
						
				    	  System.out.println(leave.getLeaveType()+"\t Doesnot Already Exist \n");
				    	  String querySelect=IQueryConstants.ADD_LEAVETYPE.replace("$tableName",tableName).replace("$columnName", leave.getLeaveType());
							PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
							preparedStmt.executeUpdate();
						
							System.out.println("Adding the no of leave days specified to EmployeeTable\t"+leave.getNoofLeave());
							String querySelect0=IQueryConstants.ADD_LEAVEDAYS.replace("$tableName",tableName).replace("$columnName", leave.getLeaveType());
							PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
							preparedStmt0.setString(1,leave.getNoofLeave());
							preparedStmt0.executeUpdate();
						
				 /*
							System.out.println("Checking whether the leavetype already exist \n"+leave.getLeaveType());
							 String querySelect1=IQueryConstants.SELECT_EXISTLEAVETYPE;
								PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
								preparedStmt1.setString(1,leave.getLeaveType());
								preparedStmt1.setString(2,leave.getCompanyId());
								ResultSet rs1=preparedStmt1.executeQuery();
								System.out.println("query executed \n");
							if(rs1.next()) {
								System.out.println("query executed \n");
								leaveTypeSelect=rs1.getString("LeaveType");
								System.out.println("The data in config table is \t ");
							}
							if(leaveTypeSelect==null)
							{
						*/	
							System.out.println(" adding \t"+leave.getLeaveType()+"\t to ConfigTable \n");
					    	  String querySelect2=IQueryConstants.INSERT_LEAVETYPE;
								PreparedStatement preparedStmt2=connection.prepareStatement(querySelect2);
								preparedStmt2.setString(1,leave.getCompanyId());
								preparedStmt2.setString(2,leave.getLeaveType());
								preparedStmt2.executeUpdate();
							
								System.out.println("Adding the no of leave days specified to EmployeeConfig\t"+leave.getNoofLeave());
								String querySelect3=IQueryConstants.INSERT_LEAVEDAYS;
								PreparedStatement preparedStmt3=connection.prepareStatement(querySelect3);
								preparedStmt3.setString(1,leave.getNoofLeave());
								preparedStmt3.setString(2,leave.getCompanyId());
								preparedStmt3.setString(3,leave.getLeaveType());
								preparedStmt3.executeUpdate();
								
								EmployeeLogic.AuditReport(leave.getSuperiorId(), leave.getLeaveType()+" For "+leave.getNoofLeave(), "Declared New Leave", leave.getCompanyId());
						/*	}else {
								System.out.println("Data already exist in EmployeeConfig Table \n" );
							}
						*/	
				    }
			
				connection.close(); 
				System.out.println("adding leave info completed \n");
			}
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}
			return description;
	}


	/*
	 * function for updating leave info
	
	public static void UpdateLeaveInfo(EmployeeLeaveConfigJSON leave) {
			
			Connection connection=null;
			String tableName=leave.getCompanyId()+"LeaveTable";
			String noofLeave=null;
			int leaveDifference;
			int newNoOfDays;
			int leaveNo;
			String leaveType;
			ArrayList<EmployeeLeaveConfigJSON> empIdList=new ArrayList<EmployeeLeaveConfigJSON> ();
			try {
				connection=DBUtil.getDBConnection();
				System.out.println("edited leavetype: \t"+leave.getLeaveTypeEdited());
				
				
				if(leave.getLeaveType().equals(leave.getOldLeaveType())) {
					System.out.println("LeaveType's are same \n");
					ChangeDays(leave,leave.getOldnoofLeave());
					
					//ChangeDays(leave,noofLeave,leave.getLeaveType());
						
				}else {
					System.out.println("LeaveType's name are not same \n");
					
					UpdateLeaveInfoChangeLeaveType(leave);
					UpdateLeaveInfoChange(leave);
				
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}
				
				
			}
				

	
	*FUNCTION FOR CHANGIG THE LEAVE DAYS
	
private static void UpdateLeaveInfoChange(EmployeeLeaveConfigJSON leave) {
		Connection connection=null;
		String noofLeave=null;
		try {
			connection=DBUtil.getDBConnection();
			
			if(Integer.parseInt(leave.getNoofLeave())==Integer.parseInt(leave.getOldnoofLeave())) {
				System.out.println("No of leave days are same \n");
				
			}else {
				System.out.println("No of leave days are not same \n");
				
				
				System.out.println("getting days from DB before update \n");
				String querySelect2=IQueryConstants.SELECT_LEAVEDAYS;
				PreparedStatement preparedStmt2=connection.prepareStatement(querySelect2);
				preparedStmt2.setString(1, leave.getCompanyId());
				preparedStmt2.setString(2, leave.getLeaveType());
				ResultSet rs2=preparedStmt2.executeQuery();
				while(rs2.next()) {
					noofLeave=rs2.getString("Days");
				}
				
				System.out.println("no.of.leave:\t"+noofLeave);
				
				ChangeDays(leave,noofLeave);
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
		
		
	}
	*/	


	/*
	*FUNCTION FOR CHANGIG THE LEAVE TYPE NAME
	*/
     private static void UpdateLeaveInfoChangeLeaveType(EmployeeLeaveConfigJSON leave) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"LeaveTable";
		
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("Changing column name in \t"+tableName);
			String querySelect0=IQueryConstants.CHANGE_LEAVETYPE.replace("$tableName",tableName).replace("$columnName", leave.getOldLeaveType()).replace("$editColumnName", leave.getLeaveType());
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.executeUpdate();
			
			System.out.println("updating column name in EmployeeConfig \n");
			String querySelect1=IQueryConstants.CHANGE_LEAVETYPECONFIG;
			PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
			preparedStmt1.setString(1,leave.getLeaveType());
			preparedStmt1.setString(2,leave.getCompanyId());
			preparedStmt1.setString(3,leave.getOldLeaveType());
			preparedStmt1.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
		
		
	}


	/*
	 * function for deleting leave info  
	 */
	
	public static void DeleteLeaveInfo(EmployeeLeaveConfigJSON leave) {
		
		   Connection connection=null;
			try {
				connection=DBUtil.getDBConnection();
				System.out.println("deleting leave info \n"+leave.getCompanyId()+"\n"+leave.getLeaveType());
				String querySelect=IQueryConstants.DELETE_LEAVE;
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getCompanyId());
				preparedStmt.setString(2,leave.getLeaveType());
				preparedStmt.executeUpdate();
				EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getLeaveType() , "Deleted Leave", leave.getCompanyId());
				connection.close(); 
				System.out.println("deleting leave completed \n");
			}
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}
	}

	
	
	/*
	 * function for changing leave days
	 */
	public static void ChangeDays(EmployeeLeaveConfigJSON leave,String noofLeave) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"LeaveTable";
		int leaveDifference;
		double newNoOfDays;
		int leaveNo;
		String dbNewLeave;
		ArrayList<EmployeeLeaveConfigJSON> empIdList=new ArrayList<EmployeeLeaveConfigJSON> ();
		try {
			connection=DBUtil.getDBConnection();
			 System.out.println("updating leave info into EmployeeConfig Tbale \n");
				String querySelect3=IQueryConstants.UPDATE_CONFIGNOOFLEAVE.replace("$tableName","EmployeeConfig");
				PreparedStatement preparedStmt3=connection.prepareStatement(querySelect3);
				preparedStmt3.setString(1,leave.getNoofLeave());
				preparedStmt3.setString(2,leave.getCompanyId());
				preparedStmt3.setString(3,leave.getLeaveType());
				preparedStmt3.executeUpdate();
				
				EmployeeLogic.AuditReport(leave.getSuperiorId(), leave.getLeaveType()+" to "+leave.getNoofLeave(), "Changed Leave Value", leave.getCompanyId());
				
		
			System.out.println("new "+leave.getNoofLeave()+"old \n"+noofLeave);
		 if( Integer.parseInt(leave.getNoofLeave()) > Integer.parseInt(noofLeave) )
		 {
			 	
			 System.out.println("check2 \n");
				leaveDifference= Integer.parseInt(leave.getNoofLeave())-Integer.parseInt(noofLeave);
				System.out.println("new value is greater so add difference\n");
				String querySelect1=IQueryConstants.SELECT_EMPID.replace("$columnName",leave.getLeaveType()).replace("$tableName",tableName);
				PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
				ResultSet rs1=preparedStmt1.executeQuery();
				while(rs1.next()) {
					EmployeeLeaveConfigJSON empId=new EmployeeLeaveConfigJSON();
				
					   dbNewLeave=rs1.getString(leave.getLeaveType());
						newNoOfDays=Double.parseDouble(dbNewLeave)+leaveDifference;
						System.out.println("NO of leave are :\t"+dbNewLeave+" "+newNoOfDays);
						if(newNoOfDays<=0) {
							newNoOfDays=0;
						}
						System.out.println("Updating Leave info in EmployeeTable \n");;
						String querySelect2=IQueryConstants.UPDATE_NOOFLEAVE.replace("$tableName",tableName).replace("$columnName",leave.getLeaveType());
						PreparedStatement preparedStmt2=connection.prepareStatement(querySelect2);
						preparedStmt2.setDouble(1,newNoOfDays);
						preparedStmt2.setString(2,rs1.getString("EmployeeId"));
						preparedStmt2.executeUpdate();
					}
				
				
		 }else {
				System.out.println("check2 \n");
				leaveDifference= Integer.parseInt(noofLeave)-Integer.parseInt(leave.getNoofLeave());
				System.out.println("new value is lesser so sub difference\n");
				String querySelect1=IQueryConstants.SELECT_EMPID.replace("$columnName",leave.getLeaveType()).replace("$tableName",tableName);
				PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
				ResultSet rs1=preparedStmt1.executeQuery();
				while(rs1.next()) {
					EmployeeLeaveConfigJSON empId=new EmployeeLeaveConfigJSON();
				
						dbNewLeave=rs1.getString(leave.getLeaveType());
						newNoOfDays=Integer.parseInt(dbNewLeave)-leaveDifference;
						System.out.println("NO of leave are :\t"+dbNewLeave+" "+newNoOfDays);
						if(newNoOfDays<=0) {
							newNoOfDays=0;
						}
						System.out.println("Updating Leave info in EmployeeTable \n");;
						String querySelect2=IQueryConstants.UPDATE_NOOFLEAVE.replace("$tableName",tableName).replace("$columnName",leave.getLeaveType());
						PreparedStatement preparedStmt2=connection.prepareStatement(querySelect2);
						preparedStmt2.setDouble(1,newNoOfDays);
						preparedStmt2.setString(2,rs1.getString("EmployeeId"));
						preparedStmt2.executeUpdate();
					}
						
		 }
		
		
		
		
	}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	

	}
	
	
	

	/*
	*FUNCTION FOR ADDING HOLIDAY INFO
	*/
public static ArrayList<EmployeeLeaveConfigJSON> GetHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		ArrayList<EmployeeLeaveConfigJSON> holidayDatalist= new ArrayList<EmployeeLeaveConfigJSON>();
		
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("gettting holiday info with description \n");
			String querySelect=IQueryConstants.GET_HOLIDAYDATA.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				EmployeeLeaveConfigJSON holidayData=new EmployeeLeaveConfigJSON();
				holidayData.setDate(rs.getString("dt"));
				holidayData.setDescription(rs.getString("holidayDescr"));
				holidayData.setHoliDayShift(rs.getString("HoliDayShift"));
				holidayDatalist.add(holidayData);
			}
			}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return holidayDatalist;
		
	}

	
	/*
	 * function for getting total no.of shifts
	 */
	public static String TotalNoOfShifts(EmployeeLeaveConfigJSON leave) {
		
		Connection connection=null;
		String totalShift=null;
		EmployeeLeaveConfigJSON noOfShifts=new EmployeeLeaveConfigJSON();
		try {
			connection=DBUtil.getDBConnection();
	String querySelect=IQueryConstants.GET_SHIFT_NO;
	PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
	preparedStmt.setString(1,leave.getCompanyId());
	ResultSet rs=preparedStmt.executeQuery();
	while(rs.next()) {
		
    totalShift=rs.getString("TotalShift");
	System.out.println("gettting shift completed \n"+totalShift);

	}
//	noOfShifts.setTotalShift(totalShift);
	connection.close(); 
		}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}

		return totalShift;
	}

	/*
	 * function for adding holiday info
	 */
	public static String AddHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		Connection connection=null;
		String description="New";
		int check=0;
		String shift=null;
		System.out.println("tablename: \t"+tableName );
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			check=CheckHolidayInfo(leave);
			if(check==0) {
				System.out.println("date doesnot exist alreay \n");

				List<String> shiftList = Arrays.asList(leave.getShift().split(","));
				System.out.println("length of the Shift \n"+shiftList.size());
				
			System.out.println("adding holiday info with description \n");
			String querySelect=IQueryConstants.ADD_HOLIDAYSHIFTS.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getShift());
			preparedStmt.setString(2,leave.getDate());
			preparedStmt.executeUpdate();
			
			for(int i=0;i<shiftList.size();i++)
			{
				shift="Shift"+shiftList.get(i);
				shift=shift.replaceAll("\\s", "");
				 System.out.println(" -->"+shift);
				  
			String querySelect1=IQueryConstants.ADD_HOLIDAY.replace("$tableName", tableName).replace("$Shift",shift);
			PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
			preparedStmt1.setString(1,leave.getDescription());
			preparedStmt1.setString(2,leave.getDate());
			preparedStmt1.executeUpdate();
			EmployeeLogic.AuditReport(leave.getSuperiorId(), " For Shift" +shift+" on"+leave.getDate() , "Declared Holiday", leave.getCompanyId());
			}
			}else {
				System.out.println("date already exist alreay \n");
				description="Already Exist";
			}
			
			connection.close(); 
			System.out.println("adding holiday info with description completed \n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return description;
		
	}

	
	/*
	 * function for updating holiday info
	 */
	public static void UpdateHolidayInfo(EmployeeLeaveConfigJSON leave) {
		
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		List<String> shiftListDB = new ArrayList<String>();
		
		Connection connection=null;
			try {
			connection=DBUtil.getDBConnection();
		
			System.out.println("Getting Shifts from DB \n");
		
			shiftListDB=GetDBShifts(leave);
			
					
		if(leave.getDate().equals(leave.getOldDate())) {
			System.out.println("Dates are same \n");
			
			UpdateHolidayInfo1(leave,shiftListDB);
			
		}else {
			System.out.println("Dates are not same \n");
			UpdateHolidayInfo1(leave,shiftListDB);
			UpdateHolidayInfoOldDate(leave,leave.getOldDate(),shiftListDB);
			
		}
		
		String querySelect1=IQueryConstants.ADD_HOLIDAYSHIFTS.replace("$tableName", tableName);
		PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
		preparedStmt1.setString(1,leave.getShift());
		preparedStmt1.setString(2,leave.getDate());
		preparedStmt1.executeUpdate();
	
		EmployeeLogic.AuditReport(leave.getSuperiorId(),"For Shift "+leave.getShift()+" on"+leave.getDate() , "Updated Holiday", leave.getCompanyId());
		connection.close(); 
	}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}

		
	}


	/*
	 * FUNCTION UPDATING THE HOLIDAY INFO INTO DB
	 */
	
	private static void UpdateHolidayInfo1(EmployeeLeaveConfigJSON leave, List<String> shiftListDB) {
		
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
	//	List<String> shiftListDB = new ArrayList<String>();
		List<String> shiftList1 = Arrays.asList((leave.getShift().split(",")));
		List<String> shiftList =new ArrayList<String>();
		List<String> shiftListNotNull =new ArrayList<String>();
		List<String> shiftListNull =new ArrayList<String>();
		String shift=null;
		Connection connection=null;
		
		try {
			connection=DBUtil.getDBConnection();
		
			for(int y=0;y<shiftList1.size();y++) {
				  
				shiftList.add(shiftList1.get(y).replaceAll("\\s",""));
			}
		System.out.println("list size from DB :\t"+shiftListDB.size());
		System.out.println("list size from user :\t"+shiftList.size());

	/*
	Set<String> set1 = new HashSet<String>();
    set1.addAll(shiftListDB);

    Set<String> set2 = new HashSet<String>();
    set2.addAll(shiftList);
    set2.removeAll(set1);
    for (String diffElement : set2) {
        System.out.println("different element : \t"+diffElement.toString());
        shiftListNotNull.add(diffElement.toString());
    	
    }
*/
	List<String> intersection = new ArrayList(shiftListDB);
	intersection.retainAll(shiftList);
	  for (String diffElement : intersection) {
	        System.out.println("Common element : \t"+diffElement.toString());
	        shiftListNotNull.add(diffElement.toString());
	    	
	    }
	  
	  List<String> symmetricDifference = new ArrayList(shiftListDB);
	  symmetricDifference.removeAll(shiftList);
	  for (String diffElement : symmetricDifference) {
	        System.out.println("Un Common element : \t"+diffElement.toString());
	        shiftListNull.add(diffElement.toString());
	    	
	    }
	
		
		System.out.println("Not Null list size :\t"+shiftListNotNull.size());
		System.out.println("Null list  size :\t"+shiftListNull.size());
				
			for(int z=0;z<shiftListNotNull.size();z++)
			{
						System.out.println("updating holiday info of not null \n");

						  shift="shift"+shiftListNotNull.get(z);
			    		   System.out.println("updating holiday info with description on same shift \n"+shift);
							String querySelect1=IQueryConstants.UPDATE_HOLIDAY.replace("$tableName", tableName).replace("$Shift",shift );
							PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
							preparedStmt1.setString(1,leave.getDescription());
							preparedStmt1.setString(2,"0");
							preparedStmt1.setString(3,leave.getDate());
							preparedStmt1.executeUpdate();
			
			}
			for(int w=0;w<shiftListNull.size();w++)
			{
						System.out.println("updating holiday info of null \n");
						  
						  shift="shift"+shiftListNull.get(w);
						   String querySelect1=IQueryConstants.UPDATE_HOLIDAY.replace("$tableName", tableName).replace("$Shift",shift );
							PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
							preparedStmt1.setString(1,leave.getDescription());
							preparedStmt1.setString(2,"NULL");
							preparedStmt1.setString(3,leave.getDate());
							preparedStmt1.executeUpdate();
			}
			
			connection.close(); 
		}
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}
	}	
		
	
			/*
	 * FUNCTION FOR UPDATING OLD DAY 
	 * INFO INTO NULL IN 
	 *  THE HOLIDAY INFO INTO DB
	 */

	private static void UpdateHolidayInfoOldDate(EmployeeLeaveConfigJSON leave, String oldDate, List<String> shiftListDB) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		String shift=null;
Connection connection=null;
		
		try {
			connection=DBUtil.getDBConnection();
	
			for(int i=0;i<shiftListDB.size();i++) {
				  
				shift="Shift"+shiftListDB.get(i);
				
			String querySelect1=IQueryConstants.UPDATE_OLDDATEHOLIDAY.replace("$tableName", tableName).replace("$Shift",shift );
			PreparedStatement preparedStmt1=connection.prepareStatement(querySelect1);
			preparedStmt1.setString(1,oldDate);
			preparedStmt1.executeUpdate();
			}
			
			connection.close(); 
		}
			catch (Exception e) {
					e.printStackTrace();
			} finally {
					DBUtil.closeConnection(connection);
			}

	}

			/*
	 * function for deleting holiday info while adding a new holiday 
	 */
	public static void DeleteHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		String shift=null;
		List<String> shiftListDB = new ArrayList<String>();
		
		//String querySelect=IQueryConstants.EMP_UPDATE.replace("$tableName", tableName);
		
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("Getting Shifts from DB \n");
			shiftListDB=GetDBShifts(leave);
			System.out.println("deleting holiday info with description \n");
			
			for(int i=0;i<shiftListDB.size();i++)
			{
				shift="Shift"+shiftListDB.get(i);
			String querySelect=IQueryConstants.UPDATE_OLDDATEHOLIDAY.replace("$tableName", tableName).replace("$Shift", shift);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDate());
			preparedStmt.executeUpdate();
			}
			EmployeeLogic.AuditReport(leave.getSuperiorId(), "Declared on "+leave.getDate(), " Deleted Holiday", leave.getCompanyId());
			connection.close(); 
			System.out.println("deleting holiday info with description completed \n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
	}
	/*
	 * function for getting holiday info while adding a new holiday 
	 */

	private static List<String> GetDBShifts(EmployeeLeaveConfigJSON leave) {
		
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		List<String> shiftListDB = new ArrayList<String>();
		
		try {
			connection=DBUtil.getDBConnection();
	
		String querySelect=IQueryConstants.Get_SHIFT;
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,leave.getCompanyId());
		ResultSet rs=preparedStmt.executeQuery();
	while(rs.next()) {
		shiftListDB.add(rs.getString("ShiftType"));
	}
		
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		
		return shiftListDB;
		
	}
	
	/*
	 * function for checking already existing holiday info
	 */
	public static int CheckHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		ArrayList<EmployeeLeaveConfigJSON> holidayDatalist= new ArrayList<EmployeeLeaveConfigJSON>();
		 int check=2;
		 String description = null;
			try {
			connection=DBUtil.getDBConnection();
			System.out.println("gettting holiday info for check \n");
			String querySelect=IQueryConstants.ADD_HOLIDAY_CHECK.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDate());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				description=rs.getString("holidayDescr");
					}
			System.out.println("Description value is: \t"+description+"\n");
			
			if(description==null || description.equals("NULL") ) {
				check=0;
				System.out.println("checkvalue: \n"+check);
				
			}else {
				check=1;
				System.out.println("checkvalue: \n"+check);
				
			}
			System.out.println("checkvalue: \n"+check);
			connection.close();  
			System.out.println("gettting holiday info check completed\n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return check;
	}
	
	
	

/*
 * function for getting holiday configuration data	
 */
	
	public static EmpWeekEndJSON  GetHoliDayInfo(EmpWeekEndJSON leave) throws ParseException {
		
		// ArrayList<EmployeeLeaveConfigJSON>
		ArrayList<EmployeeLeaveConfigJSON> weekendList=new ArrayList <EmployeeLeaveConfigJSON>();
		ArrayList<EmployeeLeaveConfigJSON> weekendOptionList=new ArrayList <EmployeeLeaveConfigJSON>();
		
		String weekEndHoliday=null;
		Connection connection=null;
		EmpWeekEndJSON holidayInfo=new EmpWeekEndJSON();
		String saturdayLeave=null;
		String leavePerYear=null;
		String WeekendHolidaysOption=null;
		String shiftOptions=leave.getShift()+"Options";
		
			try {
			connection=DBUtil.getDBConnection();
			System.out.println("getting holiday info \n");
			String querySelect=IQueryConstants.GET_HOLIDAYINFO.replace("$shiftOptions",shiftOptions );
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				leavePerYear=rs.getString("LeavePerYear");
				WeekendHolidaysOption=rs.getString(shiftOptions);
				if(WeekendHolidaysOption!=null) {
					List<String> aList= Arrays.asList(WeekendHolidaysOption.split(","));
					for(int i=0;i<aList.size();i++)
					{
					System.out.println("WeekendHolidaysOption list is............"+aList.get(i));
		 			EmployeeLeaveConfigJSON holidayConf=new EmployeeLeaveConfigJSON();
					holidayConf.setWeekdaysHoliday(aList.get(i));
					weekendOptionList.add(holidayConf);
					}
					}else {
						System.out.println("no weekendOptionList opted");
					}
				
			}
			holidayInfo.setLeavePerYear(leavePerYear);
			holidayInfo.setWeekendOptionList(weekendOptionList);
			
			connection.close(); 
			System.out.println("getting holiday info completed \n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}

			return holidayInfo;
	}


	/*
	 * Function for configuring Saturday leave 
	 */
	public static void HoliDayConfig(EmpWeekEndJSON leave) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		String weekendHoildayOption=leave.getSundayOption()+","+leave.getMondayOption()
		+","+leave.getTuesdayOption()+","+leave.getWednesdayOption()
		+","+leave.getThursdayOption()+","+leave.getFridayOption()
		+","+leave.getSaturdayOption();
		String shiftOptionsHoliday=leave.getShift();
		String shiftOptionsCompany=leave.getShift()+"Options";
		
		try {
			connection=DBUtil.getDBConnection();
			
				
			if(!(leave.getSundayOption().equals("NoneSundays"))) {
				System.out.println("opted sunday option is not NONE \n");
			SundayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getSundayOption() +" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				System.out.println("opted sunday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Sunday");
				preparedStmt.executeUpdate();
				
			
			}
			
			if(!(leave.getMondayOption().equals("NoneMondays"))) {
				System.out.println("opted monday option is not NONE \n");
			MondayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getMondayOption()+" for "+shiftOptionsHoliday , "Changed Week End Holiday", leave.getCompanyId());
			

			}else {System.out.println("opted monday option is NONE \n");
				System.out.println("opted tuesday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Monday");
				preparedStmt.executeUpdate();
							}
			
			if(!(leave.getTuesdayOption().equals("NoneTuesdays"))) {
				System.out.println("opted tuesday option is not NONE \n");
			TuesdayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getTuesdayOption() +" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Tuesday");
				preparedStmt.executeUpdate();
				
			
			}
			
			if(!(leave.getWednesdayOption().equals("NoneWednesdays"))) {
				System.out.println("opted wednesday option is not NONE \n");
			WednesdayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getWednesdayOption()+" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				System.out.println("opted wednesday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Wednesday");
				preparedStmt.executeUpdate();
			
				
			}
		
			if(!(leave.getThursdayOption().equals("NoneThursdays"))) {
				System.out.println("opted thursday option is not NONE \n");
			ThursdayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getThursdayOption()+" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				System.out.println("opted thursday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Thursday");
				preparedStmt.executeUpdate();
				
			
			}
		
			if(!(leave.getFridayOption().equals("NoneFridays"))) {
				System.out.println("opted friay option is not NONE \n");
			FridayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getFridayOption()+" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				System.out.println("opted friday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Friday");
				preparedStmt.executeUpdate();
				
			
			}
			
			if(!(leave.getSaturdayOption().equals("NoneSaturdays"))) {
				System.out.println("opted saturday option is not NONE \n");
			SaturdayOption(leave,shiftOptionsHoliday,shiftOptionsCompany);
			EmployeeLogic.AuditReport(leave.getSuperiorId(),leave.getSaturdayOption()+" for "+shiftOptionsHoliday, "Changed Week End Holiday", leave.getCompanyId());
			
			}else {
				
				System.out.println("opted saturday option is NONE \n");
				String querySelect=IQueryConstants.NONEHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptionsHoliday);
				PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
				preparedStmt.setString(1,leave.getDate());
				preparedStmt.setString(2,"Saturday");
				preparedStmt.executeUpdate();
		    
			}
			System.out.println("inserting data into companytable \n");
			String querySelect0=IQueryConstants.INS_HOLIDAYDATA.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,weekendHoildayOption);
			preparedStmt0.setString(2,leave.getLeavePerYear());
			preparedStmt0.setString(3,leave.getCompanyId());
			preparedStmt0.executeUpdate();
			System.out.println("inserting data into companytable completed \n");
	
			
			connection.close(); 
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
			

	}


	/*
	 * function for performing operation for opted sundayoption
	 */
	private static void SundayOption(EmpWeekEndJSON leave,String shiftOptionsHoliday,String shiftOptionsCompany) {
	
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenSundayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		//if(weekendHolidayOption.equals(null) || weekendHolidayOption.equals("NULL") ) {
		choosenSundayOption = weekendHolidayOption.split(",")[0];			
			
				
				if(choosenSundayOption.equals(leave.getSundayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenSundayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getSundayOption());
				}else {
					
					if(leave.getSundayOption().equals("EvenSundays"))
					{
						System.out.println("Newly Opted for EvenSundays \n");
						
					if(!choosenSundayOption.equals("NoneSundays")) {
				if(choosenSundayOption.equals("OddSundays")) {
					
					OddOptionChange(leave,"Sunday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Sunday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneSundays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Sunday",shiftOptionsHoliday);
				
					}else if (leave.getSundayOption().equals("OddSundays")){
				
				System.out.println("Newly Opted for oddSunday \n");
				
				if(!choosenSundayOption.equals("NoneSundays")) {
					if(choosenSundayOption.equals("EvenSundays")) {
						
						EvenOptionChange(leave,"Sunday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Sunday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneSundays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Sunday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all sunday \n");
				
				NewAllOPtionUpdate	(leave,"Sunday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	}
	
		/*
		 * function for performing operation for opted mondayoption
		 */
	private static void MondayOption(EmpWeekEndJSON leave,String shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenMondayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenMondayOption = weekendHolidayOption.split(",")[1];			
			
				
				if(choosenMondayOption.equals(leave.getMondayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenMondayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getMondayOption());
				}else {
					
					if(leave.getMondayOption().equals("EvenMondays"))
					{
						System.out.println("Newly Opted for EvenMondays \n");
						
					if(!choosenMondayOption.equals("NoneMondays")) {
				if(choosenMondayOption.equals("OddMondays")) {
					
					OddOptionChange(leave,"Monday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Monday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneMondays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Monday",shiftOptionsHoliday);
				
					}else if (leave.getMondayOption().equals("OddMondays")){
				
				System.out.println("Newly Opted for oddMonday \n");
				
				if(!choosenMondayOption.equals("NoneMondays")) {
					if(choosenMondayOption.equals("EvenMondays")) {
						
						EvenOptionChange(leave,"Monday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Monday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneMondays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Monday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all sunday \n");
				
				NewAllOPtionUpdate	(leave,"Monday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
}
	
	/*
	 * function for performing operation for opted tuesdayoption
	 */
	private static void TuesdayOption(EmpWeekEndJSON leave, String shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenTuesdayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenTuesdayOption = weekendHolidayOption.split(",")[2];			
			
				
				if(choosenTuesdayOption.equals(leave.getTuesdayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenTuesdayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getTuesdayOption());
				}else {
					
					if(leave.getTuesdayOption().equals("EvenTuesdays"))
					{
						System.out.println("Newly Opted for EvenTuesdays \n");
						
					if(!choosenTuesdayOption.equals("NoneTuesdays")) {
				if(choosenTuesdayOption.equals("OddTuesdays")) {
					
					OddOptionChange(leave,"Tuesday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Tuesday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneTuesdays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Tuesday",shiftOptionsHoliday);
				
					}else if (leave.getTuesdayOption().equals("OddTuesdays")){
				
				System.out.println("Newly Opted for OddTuesdays \n");
				
				if(!choosenTuesdayOption.equals("NoneTuesdays")) {
					if(choosenTuesdayOption.equals("EvenTuesdays")) {
						
						EvenOptionChange(leave,"Tuesday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Tuesday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneTuesdays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Tuesday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all Tuesdays \n");
				
				NewAllOPtionUpdate	(leave,"Tuesday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
}
	
	/*
	 * function for performing operation for opted wednesdayoption
	 */
	private static void WednesdayOption(EmpWeekEndJSON leave,String shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenWednesdayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenWednesdayOption = weekendHolidayOption.split(",")[3];			
			
				
				if(choosenWednesdayOption.equals(leave.getWednesdayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenWednesdayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getWednesdayOption());
				}else {
					
					if(leave.getWednesdayOption().equals("EvenWednesdays"))
					{
						System.out.println("Newly Opted for EvenWednesdays \n");
						
					if(!choosenWednesdayOption.equals("NoneWednesdays")) {
				if(choosenWednesdayOption.equals("OddWednesdays")) {
					
					OddOptionChange(leave,"Wednesday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Wednesday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneWednesdays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Wednesday",shiftOptionsHoliday);
				
					}else if (leave.getWednesdayOption().equals("OddWednesdays")){
				
				System.out.println("Newly Opted for OddWednesdays \n");
				
				if(!choosenWednesdayOption.equals("NoneWednesdays")) {
					if(choosenWednesdayOption.equals("EvenWednesdays")) {
						
						EvenOptionChange(leave,"Wednesday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Wednesday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneWednesdays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Wednesday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all Wednesdays \n");
				
				NewAllOPtionUpdate	(leave,"Wednesday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	}
	
	
	/*
	 * function for performing operation for opted thursdayoption
	 */
	private static void ThursdayOption(EmpWeekEndJSON leave, String shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenThursdayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenThursdayOption = weekendHolidayOption.split(",")[4];			
			
				
				if(choosenThursdayOption.equals(leave.getThursdayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenThursdayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getThursdayOption());
				}else {
					
					if(leave.getThursdayOption().equals("EvenThursdays"))
					{
						System.out.println("Newly Opted for EvenThursdays \n");
						
					if(!choosenThursdayOption.equals("NoneThursdays")) {
				if(choosenThursdayOption.equals("OddThursdays")) {
					
					OddOptionChange(leave,"Thursday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Thursday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneThursdays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Thursday",shiftOptionsHoliday);
				
					}else if (leave.getThursdayOption().equals("OddThursdays")){
				
				System.out.println("Newly Opted for OddThursdays \n");
				
				if(!choosenThursdayOption.equals("NoneThursdays")) {
					if(choosenThursdayOption.equals("EvenThursdays")) {
						
						EvenOptionChange(leave,"Thursday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Thursday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneThursdays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Thursday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all Thursdays \n");
				
				NewAllOPtionUpdate	(leave,"Thursday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
		}

	/*
	 * function for performing operation for opted fridayoption
	 */
	private static void FridayOption(EmpWeekEndJSON leave,String shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenFridayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenFridayOption = weekendHolidayOption.split(",")[5];			
			
				
				if(choosenFridayOption.equals(leave.getFridayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenFridayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getFridayOption());
				}else {
					
					if(leave.getFridayOption().equals("EvenFridays"))
					{
						System.out.println("Newly Opted for EvenFridays \n");
						
					if(!choosenFridayOption.equals("NoneFridays")) {
				if(choosenFridayOption.equals("OddFridays")) {
					
					OddOptionChange(leave,"Friday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Friday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneFridays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Friday",shiftOptionsHoliday);
				
					}else if (leave.getFridayOption().equals("OddFridays")){
				
				System.out.println("Newly Opted for OddFridays \n");
				
				if(!choosenFridayOption.equals("NoneFridays")) {
					if(choosenFridayOption.equals("EvenFridays")) {
						
						EvenOptionChange(leave,"Friday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Friday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneFridays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Friday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all Friday \n");
				
				NewAllOPtionUpdate	(leave,"Friday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
	}
	
	/*
	 * function for performing operation for opted saturdayoption
	 */
	private static void SaturdayOption(EmpWeekEndJSON leave, String  shiftOptionsHoliday,String shiftOptionsCompany) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		String weekendHolidayOption=null;
		String choosenSaturdayOption=null;
		try {
			connection=DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.GETHOLIDAYCONFIGOPTION.replace("$shiftOptions", shiftOptionsCompany);
			PreparedStatement preparedStmt0=connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,leave.getCompanyId());
			ResultSet rs=preparedStmt0.executeQuery();
		while(rs.next()) {
			weekendHolidayOption=rs.getString(shiftOptionsCompany);
		}
		choosenSaturdayOption = weekendHolidayOption.split(",")[6];			
			
				
				if(choosenSaturdayOption.equals(leave.getSaturdayOption())) {
					System.out.println("Already choosen and newly choosen options are same \n");
					System.out.println("Already choosen options are :\t"+choosenSaturdayOption);
					System.out.println("Newly choosen options are same : \t"+leave.getSaturdayOption());
				}else {
					
					if(leave.getSaturdayOption().equals("EvenSaturdays"))
					{
						System.out.println("Newly Opted for EvenSaturdays \n");
						
					if(!choosenSaturdayOption.equals("NoneSaturdays")) {
				if(choosenSaturdayOption.equals("OddSaturdays")) {
					
					OddOptionChange(leave,"Saturday",shiftOptionsHoliday);
						
				}else {
					AllOptionChange(leave,"Saturday",shiftOptionsHoliday);
					
				}
					}else {
						System.out.println("Already opted for NoneSaturdays OPtion");
					}
				NewEvenOPtionUpdate	(leave,"Saturday",shiftOptionsHoliday);
				
					}else if (leave.getSaturdayOption().equals("OddSaturdays")){
				
				System.out.println("Newly Opted for OddSaturdays \n");
				
				if(!choosenSaturdayOption.equals("NoneSaturdays")) {
					if(choosenSaturdayOption.equals("EvenSaturdays")) {
						
						EvenOptionChange(leave,"Saturday",shiftOptionsHoliday);
							
					}else {
						AllOptionChange(leave,"Saturday",shiftOptionsHoliday);
						
					}
						}else {
							System.out.println("Already opted for NoneSaturdays OPtion");
						}
					NewOddOPtionUpdate	(leave,"Saturday",shiftOptionsHoliday);
						}else {
				System.out.println("Newly Opted for all Saturdays \n");
				
				NewAllOPtionUpdate	(leave,"Saturday",shiftOptionsHoliday);	}
				}
			}catch (Exception e) {
			e.printStackTrace();
	} finally {
			DBUtil.closeConnection(connection);
	}
		
	}


	/*
	 * function for updating already chosen OddOPtion for the Day
	 */
	private static void OddOptionChange(EmpWeekEndJSON leave, String day,String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("EXISTING ODD OPTION CHANGE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
		System.out.println("Alrfeady Opted for Odd \t:"+day);
		String querySelect=IQueryConstants.ODDHOLIDAYCONFIGCHANGE.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,leave.getDate());
		preparedStmt.setString(2,day);
		preparedStmt.executeUpdate();
	
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}
	
	/*
	 * function for updating already chosen AllOPtion for the Day
	 */
	private static void AllOptionChange(EmpWeekEndJSON leave, String day, String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("EXISTING ALL OPTION CHANGE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
		System.out.println("Alrfeady Opted for Odd \t:"+day);
		String querySelect=IQueryConstants.ALLHOLIDAYCONFIGCHANGE.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,leave.getDate());
		preparedStmt.setString(2,day);
		preparedStmt.executeUpdate();
	
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}


	/*
	 * function for updating newly chosen EvenOPtion for the Day
	 */
	private static void NewEvenOPtionUpdate(EmpWeekEndJSON leave, String day, String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("NEW EVEN OPTION UPDATE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
			String querySelect=IQueryConstants.EVENHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDate());
			preparedStmt.setString(2,day);
			preparedStmt.executeUpdate();
		
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}
	
	/*
	 * function for updating already chosen EvenOPtion for the Day
	 */
	private static void EvenOptionChange(EmpWeekEndJSON leave, String day, String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("EXISTING EVEN OPTION CHANGE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
		System.out.println("Alrfeady Opted for Odd \t:"+day);
		String querySelect=IQueryConstants.EVENHOLIDAYCONFIGCHANGE.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
		PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
		preparedStmt.setString(1,leave.getDate());
		preparedStmt.setString(2,day);
		preparedStmt.executeUpdate();
	
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}

	/*
	 * function for updating newly chosen OddOPtion for the Day
	 */
	private static void NewOddOPtionUpdate(EmpWeekEndJSON leave, String day, String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("NEW ODD OPTION UPDATE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
			String querySelect=IQueryConstants.ODDHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDate());
			preparedStmt.setString(2,day);
			preparedStmt.executeUpdate();
		
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}
	
	
	/*
	 * function for updating newly chosen AllOPtion for the Day
	 */
	private static void NewAllOPtionUpdate(EmpWeekEndJSON leave, String day, String shiftOptions) {
		Connection connection=null;
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("NEW ALL OPTION UPDATE \n");
		System.out.println("TABLE NAME: \t"+tableName);
		System.out.println("DATE: \t"+leave.getDate());
		try {
			connection=DBUtil.getDBConnection();
		
			String querySelect=IQueryConstants.ALLHOLIDAYCONFIG.replace("$tableName", tableName).replace("$shiftOptions", shiftOptions);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDate());
			preparedStmt.setString(2,day);
			preparedStmt.executeUpdate();
		
	}catch (Exception e) {
		e.printStackTrace();
} finally {
		DBUtil.closeConnection(connection);
}
	}

	

}