package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeLeaveConfigJSON;
import com.fillingstation.util.DBUtil;

public class EmployeeHolidayLogic {
	
	
	/*
	 * function for adding holiday info
	 */

	public static String AddHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		Connection connection=null;
		String description="New";
		int check=0;
		System.out.println("tablename: \t"+tableName );
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		System.out.println(description+"hi");
		try {
			connection=DBUtil.getDBConnection();
			check=CheckHolidayInfo(leave);
			if(check==0) {
				System.out.println("date doesnot exist alreay \n");
			
			System.out.println("adding holiday info with description \n");
			String querySelect=IQueryConstants.ADD_HOLIDAY.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDescription());
			preparedStmt.setString(2,leave.getDate());
			preparedStmt.executeUpdate();
			System.out.println(description+"hi");
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
	System.out.println(description+"hi");
		return description;
		
	}

	/*
	 * function for deleting holiday info while adding a new holiday 
	 */
	public static void DeleteHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		//String querySelect=IQueryConstants.EMP_UPDATE.replace("$tableName", tableName);
		
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("deleting holiday info with description \n");
			String querySelect=IQueryConstants.DELETE_HOLIDAY.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setNull(1, java.sql.Types.BINARY);
			preparedStmt.setNull(2, java.sql.Types.VARCHAR);
			//preparedStmt.setString(1,leave.getDescription());
			preparedStmt.setString(3,leave.getDate());
			preparedStmt.executeUpdate();
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
				holidayDatalist.add(holidayData);
			}
			connection.close(); 
			System.out.println("gettting holiday info with description completed\n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}
		return holidayDatalist;
		
	}

	/*
	 * function for checking already existing holiday info
	 */
	public static int CheckHolidayInfo(EmployeeLeaveConfigJSON leave) {
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		ArrayList<EmployeeLeaveConfigJSON> holidayDatalist= new ArrayList<EmployeeLeaveConfigJSON>();
		 int check=0;
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
				System.out.println("Description: \n"+description);
			}
			if(description!=null ) {
				check=1;
			}else {
				check=0;
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
	 * function for updating holiday info
	 */
	public static void UpdateHolidayInfo(EmployeeLeaveConfigJSON leave) {
		
		String tableName=leave.getCompanyId()+"HolidayTable";
		System.out.println("tablename: \t"+tableName );
		Connection connection=null;
		//String querySelect=IQueryConstants.EMP_UPDATE.replace("$tableName", tableName);
		
		//EmployeeLeaveJSON mailId=new EmployeeLeaveJSON();
		try {
			connection=DBUtil.getDBConnection();
			System.out.println("updating holiday info with description \n");
			String querySelect=IQueryConstants.UPDATE_HOLIDAY.replace("$tableName", tableName);
			PreparedStatement preparedStmt=connection.prepareStatement(querySelect);
			preparedStmt.setString(1,leave.getDescription());
			preparedStmt.setString(2,leave.getDate());
			preparedStmt.executeUpdate();
			connection.close(); 
			System.out.println("updating holiday info with description completed \n");
		}
		catch (Exception e) {
				e.printStackTrace();
		} finally {
				DBUtil.closeConnection(connection);
		}

		
	}

}
