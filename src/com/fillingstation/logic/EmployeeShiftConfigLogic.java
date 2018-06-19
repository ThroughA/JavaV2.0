package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeAttendanceJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.json.ShiftConfigJSON;
import com.fillingstation.util.DBUtil;

public class EmployeeShiftConfigLogic {

		/*
	 * FUNCTION FOR GETTING  CONFIG DATA
	 */
	public static ArrayList<ShiftConfigJSON> ConfigGet(ShiftConfigJSON json) throws SQLException {
		
		Connection connection=null;
		ArrayList<ShiftConfigJSON> shiftData = new ArrayList<ShiftConfigJSON>();
		
		try {
			connection =DBUtil.getDBConnection();
			String querySelect=IQueryConstants.SHT_CONFIGDATA;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,json.getCompanyId());
			   ResultSet rs=preparedStmt.executeQuery();
			   while(rs.next()) {
	
		ShiftConfigJSON shiftdata=new ShiftConfigJSON();
		int data=rs.getInt("ShiftType");
		System.out .println("data exist in table data value is  \t"+data);	
		if(data > 0) {
			System.out .println("data exist in table \n");	
		shiftdata.setShift(Integer.toString(data));
		shiftdata.setFrom(rs.getString("Start"));
		shiftdata.setTo(rs.getString("End"));
		shiftData.add(shiftdata);
		
		}else {
				   System.out .println("data doesnot exist in table \n");
						shiftdata.setResponse("NoData");
				   shiftData.add(shiftdata);
			   }
			
		}
		}
		   finally {
			DBUtil.closeConnection(connection);
		}
			
		return shiftData;
	}
	
	
	
	/*
	 * FUNCTION FOR INSERTING NEW CONFIG DATA INITIALLY
	 */
	public static ShiftConfigJSON ConfigInitialInsert(ShiftConfigJSON json) throws SQLException {
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
		
		
		if(!(json.getShift1().equals(""))) {
			
				ConfigInitialInsert1(json.getCompanyId(),json.getShift1(),json.getShift1From(),json.getShift1To());
			}
		if(!(json.getShift2().equals(""))) {
			
			ConfigInitialInsert1(json.getCompanyId(),json.getShift2(),json.getShift2From(),json.getShift2To());
		}

		if(!(json.getShift3().equals(""))) {
			
			ConfigInitialInsert1(json.getCompanyId(),json.getShift3(),json.getShift3From(),json.getShift3To());
		}

	}  finally {
		DBUtil.closeConnection(connection);
	}
		return null;
	}

public static void ConfigInitialInsert1(String companyId,String shift,String from,String to) throws SQLException {
				Connection connection=null;
				try {
					connection =DBUtil.getDBConnection();
			
	
			String querySelect=IQueryConstants.SHT_CONFIG_INIINSERT;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,companyId);
			    preparedStmt.setString(2,shift);
			    preparedStmt.setString(3,from);
			    preparedStmt.setString(4,to);
			  preparedStmt.executeUpdate();
			  
				}finally {
					DBUtil.closeConnection(connection);
				}
}

	/*
	 * FUNCTION FOR INSERTING NEW CONFIG DATA
	 */
	public static ShiftConfigJSON ConfigInsert(ShiftConfigJSON json) throws SQLException {
	
		Connection connection=null;
		/*System.out.println("shift time:\t"+json.getShiftTime());
		String start = json.getShiftTime().split(",")[0];
		String end = json.getShiftTime().split(",")[1];
		System.out.println("going to insert data into table \n"+"start:\t"+start+"\t"+"end:\t"+end+"\n");
		
	*/	try {
			connection =DBUtil.getDBConnection();
			
			String querySelect=IQueryConstants.SHT_CONFIGDETAILS;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,json.getCompanyId());
			    preparedStmt.setString(2,json.getShift());
			   ResultSet rs=preparedStmt.executeQuery();
			   if(rs.next()) {
					System.out.println("data to be inserted already exist in the table \n");
					
				   json.setResponse("Already Exist");
				   
				 
					   
			   }else {
				   String querySelect1=IQueryConstants.SHT_TIME_DETAILS;
					PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
					    preparedStmt1.setString(1,json.getCompanyId());
					    preparedStmt1.setString(2,json.getFrom());
						preparedStmt1.setString(3,json.getTo());
					   ResultSet rs1=preparedStmt1.executeQuery();
					   if(rs1.next()){
						   System.out.println("Duplicate Shift Timing \n");
							
						   json.setResponse("Already_Time_Exist"); 
						   
					   }else{
					System.out.println("data to be inserted doesnot already exist in the table \n");
					
					String querySelect0=IQueryConstants.SHT_CONFIGINSERT;
					PreparedStatement preparedStmt0 = connection.prepareStatement(querySelect0);
					preparedStmt0.setString(1,json.getCompanyId());
					preparedStmt0.setString(2,json.getShift());
					preparedStmt0.setString(3,json.getFrom());
					preparedStmt0.setString(4,json.getTo());
					preparedStmt0.executeUpdate();
					System.out.println("data inserted in to table successfully \n");
					EmployeeLogic.AuditReport(json.getSuperiorId(), "Created Shift "+json.getShift(), "Shift Creation", json.getCompanyId());
					   }		
			
			   }
	
			
		
			
		}
	     	
	   finally {
		DBUtil.closeConnection(connection);
	}
		return json;
	}

	
	/*
	 * FUNCTION FOR UPDATING  CONFIG DATA
	 */
	public static ShiftConfigJSON ConfigUpdate(ShiftConfigJSON json) {
		Connection connection=null;
		String shift;
		String from;
		String to;
		System.out.println("going to update data into table \n");
		try {
			connection =DBUtil.getDBConnection();
			
					System.out.println("updating config data in the table  \n");
					
					if(json.getNewShift().equals("")) {
						shift=json.getShift();
					}else {
						shift=json.getNewShift();
					}
					
					if(json.getNewFrom().equals("")) {
						from=json.getFrom();
					}else {
						from=json.getNewFrom();
					}
					
					if(json.getNewTo().equals("")) {
						to=json.getTo();
					}else {
						to=json.getNewTo();
					}
					
					
					String querySelect0=IQueryConstants.SHT_CONFIGUPDATE;
					PreparedStatement preparedStmt0 = connection.prepareStatement(querySelect0);
					preparedStmt0.setString(1,shift);
					preparedStmt0.setString(2,from);
					preparedStmt0.setString(3,to);
					preparedStmt0.setString(4,json.getCompanyId());
					preparedStmt0.setString(5,json.getShift());
					preparedStmt0.setString(6,json.getFrom());
					preparedStmt0.setString(7,json.getTo());
					preparedStmt0.executeUpdate();
					System.out.println("data inserted in to table successfully \n");
					
			
			   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   finally {
			DBUtil.closeConnection(connection);
		}
		return null;    
	}
	/*
	 * FUNCTION FOR UPDATING  CONFIG DATA
	 */
	public static ShiftConfigJSON ShiftUpdate(ShiftConfigJSON json) {
		Connection connection=null;
		String shift;
		String from;
		String to;
		System.out.println("going to update data into table \n");
		try {
			connection =DBUtil.getDBConnection();
			
			String querySelect1=IQueryConstants.SHT_TIME_DETAILS;
			PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
			    preparedStmt1.setString(1,json.getCompanyId());
			    preparedStmt1.setString(2,json.getNewFrom());
				preparedStmt1.setString(3,json.getNewTo());
			   ResultSet rs1=preparedStmt1.executeQuery();
			   if(rs1.next()){
				   
				   System.out.println("Duplicate Shift Timing \n");
				   json.setResponse("Already_Time_Exist");
				   
			   }else{
					System.out.println("updating config data in the table"+json.getNewFrom()+" " + json.getNewTo()+" "+json.getCompanyId()+" "+json.getShift()+"  \n");
					
					String querySelect0=IQueryConstants.SHIFT_UPDATE;
					PreparedStatement preparedStmt0 = connection.prepareStatement(querySelect0);
					preparedStmt0.setString(1,json.getNewFrom());
					preparedStmt0.setString(2,json.getNewTo());
					
					preparedStmt0.setString(3,json.getCompanyId());
					preparedStmt0.setString(4,json.getShift());
					
					preparedStmt0.executeUpdate();
					EmployeeLogic.AuditReport(json.getSuperiorId(),"Changed  Shift timing from "+json.getNewFrom()+" to"+json.getNewTo(), "Updated Shift"+json.getShift(), json.getCompanyId());
					System.out.println("data inserted in to table successfully \n");
			   }
			   	
			
			   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   finally {
			DBUtil.closeConnection(connection);
		}
		return json;    
	}


	/*
	 * FUNCTION FOR DELETING  CONFIG DATA
	 */
	public static ShiftConfigJSON ConfigDelete(ShiftConfigJSON json) {
		Connection connection=null;
		String shift;
		String from;
		String to;
		System.out.println("going to update data into table \n");
		try {
			connection =DBUtil.getDBConnection();
			
			String querySelect0=IQueryConstants.SH_EMP_EXITS;
			PreparedStatement preparedStmt0 = connection.prepareStatement(querySelect0);
			preparedStmt0.setString(1,json.getCompanyId());
			preparedStmt0.setString(2,json.getShift());
			ResultSet rs=preparedStmt0.executeQuery();
			   if(rs.next()) {
				    json.setStatus("EMP_EXITS");
			   }else {
				   
			   
					System.out.println(" There is no Employee so deleting config data in the table  \n");
					
					String querySelect1=IQueryConstants.SHT_CONFIGDELETE;
					PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
					preparedStmt1.setString(1,json.getCompanyId());
					preparedStmt1.setString(2,json.getShift());
					preparedStmt1.setString(3,json.getFrom());
					preparedStmt1.setString(4,json.getTo());
					preparedStmt1.executeUpdate();
					EmployeeLogic.AuditReport(json.getSuperiorId(), json.getShift(), "Deleted Shift", json.getCompanyId());
					System.out.println("data deleted from table successfully \n");
			   }
			
			   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   finally {
			DBUtil.closeConnection(connection);
		}
	return json;
	}

	/*
	 * FUNCTION FOR SETTING MAXIMUM EXCUSE TIME
	 * FOR CHECKING IN FROM THE EMPLOYEE SHIFT
	 */
	public static ShiftConfigJSON ShiftStrictMode(ShiftConfigJSON json) {
		Connection connection=null;
		String shift;
		String from;
		String to;
		System.out.println("going to update the strict mode data into table \n");
		try {
			connection =DBUtil.getDBConnection();
			
					System.out.println("updating strict mode data in the table  \n");
					
						String querySelect0=IQueryConstants.SHT_STRICTMODEDATA;
					PreparedStatement preparedStmt0 = connection.prepareStatement(querySelect0);
					preparedStmt0.setString(1,json.getShiftSwitched());
					preparedStmt0.setString(2,json.getHours());
					preparedStmt0.setString(3,json.getCompanyId());
					
					preparedStmt0.executeUpdate();
					if(json.getShiftSwitched().equals("1")){
						EmployeeLogic.AuditReport(json.getSuperiorId(),"Shift Mode On" , "Changed Shift Mode", json.getCompanyId());
							
					}else{
					EmployeeLogic.AuditReport(json.getSuperiorId(),"Shift Mode Off" , "Changed Shift Mode", json.getCompanyId());
					}
					System.out.println("updated shitmode into table successfully \n");
					
					json.setHours("SUCCESS");
			
			   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	
		   finally {
			DBUtil.closeConnection(connection);
		}
		return json;
	}




	
	
	
}