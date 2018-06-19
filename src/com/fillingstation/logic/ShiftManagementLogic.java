package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeConfigJSON;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.util.DBUtil;

public class ShiftManagementLogic {
	
	
	/*
	 * FUNCTION FOR SELECTING TOTAL NO.OF SHIFTS
	 */
	
	public static String SelectTotalNoOfShift(EmployeeConfigJSON config) {
		Connection connection = null;
		String totalShift=null;
			try{
			connection = DBUtil.getDBConnection();
		    String querySelect=IQueryConstants.Select_EmpIDTotalShift;
		    PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
		    
		    preparedStmt.setString(1,config.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				totalShift=rs.getString("TotalShift");
				
			}
			System.out.println("TotalNoShift selected");
		    connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);}
		return totalShift;
		
	}
	
	
	/*
	 * FUNCTION FOR IDENTIFYING THE OPTIONS BEING OPTED
	 */
	
	public static ArrayList <EmployeeConfigJSON> SelectAllEmployee(EmployeeConfigJSON config) {
		Connection connection = null;
		//int i=0;
		ArrayList <EmployeeConfigJSON> empList=new ArrayList <EmployeeConfigJSON> ();
		try{
			connection = DBUtil.getDBConnection();	
					String querySelect=IQueryConstants.Select_All_Emp;
				    PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
				   
				    preparedStmt.setString(1,config.getCompanyId());
					ResultSet rs=preparedStmt.executeQuery();
					while(rs.next()) {
						EmployeeConfigJSON empId=new EmployeeConfigJSON();
						empId.setEmployeeId(rs.getString("EmployeeId"));
						empId.setEmployeeName(rs.getString("Name"));
						empList.add(empId);
						
					}
				
		    System.out.println("employeeID selected" );
		    connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);}
		return empList;
	}
	/*
	 * FUNCTION FOR IDENTIFYING THE OPTIONS BEING OPTED
	 */
	
	public static ArrayList <EmployeeConfigJSON> SelectEmployee(EmployeeConfigJSON config) {
		Connection connection = null;
		//int i=0;
		ArrayList <EmployeeConfigJSON> empList=new ArrayList <EmployeeConfigJSON> ();
		String data1=null;
		String data2=null;
		String data3=null;
		String option1=null;
		String option2=null;
		String option3=null;
		int count=0;
		
		try{
			connection = DBUtil.getDBConnection();
	
			System.out.println("Check values:\t"+config.getRole()+"\t"+config.getDepartment()+"\t"+config.getShift()+"\n");
			
			
				if(!config.getRole().isEmpty()) {
					System.out.println("role" + config.getRole() );
					count++;
					System.out.println("count" + count );
				}
				if(!config.getDepartment().isEmpty()) {
					System.out.println("dept" + config.getDepartment() );
					count++;
					System.out.println("count" + count );
				}
				if(!config.getShift().isEmpty()) {
					System.out.println("shift" + config.getShift() );
					count++;
					System.out.println("count" + count );
				}
			
			System.out.println("COUNT VALUE : \t"+count);
			
			if(count==0) {
				empList=ShiftManagementLogic.SelectAllEmployee(config);
				System.out.println("All EMp VALUE : \t"+count);
			}
			
			//if(config.getCount()==1) {
				if(count==1) {	
					if(!config.getRole().isEmpty()) {
					data1=config.getRole();
					option1="role";
					System.out.println("Single option :\t"+data1+"\n"+option1+"\n"+config.getCompanyId()+"\n"+config.getDepartment());
					empList=SingleOption(data1,option1,config);
					for(EmployeeConfigJSON newlist:empList) {
						System.out.println("came back \t"+newlist.getEmployeeId()+"\n");
					}
				}else if(!config.getDepartment().isEmpty()) {
					data1=config.getDepartment();
					option1="department";
					System.out.println("Single option :\t"+data1+"\n"+option1+"\n");
					empList=SingleOption(data1,option1,config);
							
				}else {
					data1=config.getShift();
					option1="shift";
					System.out.println("Single option :\t"+data1+"\n"+option1+"\n");
					empList=SingleOption(data1,option1,config);
				}
					
				}
		
				//if(config.getCount()==2) {
				if(count==2) {
				if(!config.getRole().isEmpty() && !config.getDepartment().isEmpty()) {
					data1=config.getRole();
					option1="role";
					data2=config.getDepartment();
					option2="department";
					System.out.println("Double option :\t"+data1+"\n"+option1+"\n"+data2+"\n"+option2+"\n");
					empList=DoubleOption(data1,option1,data2,option2,config);
				}else if(!config.getDepartment().isEmpty() && !config.getShift().isEmpty()) {
					data1=config.getDepartment();
					option1="department";
					data2=config.getShift();
					option2="shift";	
					System.out.println("Double option :\t"+data1+"\n"+option1+"\n"+data2+"\n"+option2+"\n");
					empList=DoubleOption(data1,option1,data2,option2,config);
					
				}else if(!config.getRole().isEmpty() && !config.getShift().isEmpty()){
					data1=config.getRole();
					option1="role";
					data2=config.getShift();
					option2="shift";
					System.out.println("Double option :\t"+data1+"\n"+option1+"\n"+data2+"\n"+option2+"\n");
					empList=DoubleOption(data1,option1,data2,option2,config);
					
				}
				
				}
				
				//if(config.getCount()==3) {
                 if(count==3) {
				if(!config.getRole().isEmpty() && !config.getDepartment().isEmpty() && !config.getShift().isEmpty()) {
					System.out.println("Triple option :\t"+config.getRole()+"\n"+config.getDepartment()+"\n"+config.getShift()+"\n");
					String querySelect=IQueryConstants.Select_EmpID3;
				    PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
				    preparedStmt.setString(1,config.getRole());
				    preparedStmt.setString(2,config.getDepartment());
				    preparedStmt.setString(3,config.getShift());
				    preparedStmt.setString(4,config.getCompanyId());
					ResultSet rs=preparedStmt.executeQuery();
					while(rs.next()) {
						EmployeeConfigJSON empId=new EmployeeConfigJSON();
						empId.setEmployeeId(rs.getString("EmployeeId"));
						empId.setEmployeeName(rs.getString("Name"));
						empList.add(empId);
						
					}
				}
				}

			
		   		System.out.println("employeeID selected" );
		    connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);}
		return empList;
	}

	
	/*
	 * FUNCTION FOR RETRIEVING DATA WHEN ONLY ONE PARTICULAR 
	 * OPTION IS SELECTED
	 */
	private static ArrayList <EmployeeConfigJSON>  SingleOption(String data1, String option1, EmployeeConfigJSON config) {
		
		Connection connection = null;
		ArrayList <EmployeeConfigJSON> empList=new ArrayList <EmployeeConfigJSON> ();

		try{
			connection = DBUtil.getDBConnection();
		 String querySelect=IQueryConstants.Select_EmpID1.replace("$option1", option1);
		    PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
		    preparedStmt.setString(1,data1);
		    preparedStmt.setString(2,config.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
			while(rs.next()) {
				EmployeeConfigJSON empId=new EmployeeConfigJSON();
				empId.setEmployeeId(rs.getString("EmployeeId"));
				empId.setEmployeeName(rs.getString("Name"));
				empList.add(empId);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);
		}
	return empList;

	}

	
	/*
	 * FUNCTION FOR RETRIEVING DATA WHEN TWO PARTICULAR 
	 * OPTION IS SELECTED
	 */
	private static ArrayList<EmployeeConfigJSON> DoubleOption(String data1, String option1, String data2, String option2,
			EmployeeConfigJSON config) {
		Connection connection = null;
		ArrayList <EmployeeConfigJSON> empList=new ArrayList <EmployeeConfigJSON> ();

		try{
			connection = DBUtil.getDBConnection();
		
			 String querySelect=IQueryConstants.Select_EmpID2.replace("$option1", option1).replace("$option2",option2);
			    PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,data1);
			    preparedStmt.setString(2,data2);
			    preparedStmt.setString(3,config.getCompanyId());
				ResultSet rs=preparedStmt.executeQuery();
				while(rs.next()) {
					EmployeeConfigJSON empId=new EmployeeConfigJSON();
					empId.setEmployeeId(rs.getString("EmployeeId"));
					empId.setEmployeeName(rs.getString("Name"));
					empList.add(empId);
					
				}
			

			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);
		}
			
			return empList;
	}

	/*
	 * FUNCTION FOR UPDATING THE EMPLOYEE SHIFT DETAILS
	 */
	public static void ShiftUpdation(EmployeeConfigJSON json) {
		Connection connection = null;
		System.out.println("Companyid: \t"+json.getCompanyId()+"\n");
		System.out.println("Employeeid: \t"+json.getEmployeeId()+"\n");
		System.out.println("New Shift :\t"+json.getNewShift()+"\n");
		
		List<String> employeeIdList = Arrays.asList(json.getEmployeeId().split(","));
		System.out.println("length of the employeeId \n"+employeeIdList.size());
		
		try{
			connection = DBUtil.getDBConnection();
		
		for(int i=0;i<employeeIdList.size();i++)
		{
		    System.out.println(" -->"+employeeIdList.get(i));
		    String querySelect=IQueryConstants.SHIFTMANAGEMENTUPDATE;
			 PreparedStatement preparedStmt= connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,json.getNewShift());
			    preparedStmt.setString(2,employeeIdList.get(i));
			    preparedStmt.setString(3,json.getCompanyId());
			preparedStmt.executeUpdate();

			EmployeeLogic.AuditReport(json.getSuperiorId(),employeeIdList.get(i)+" Changed to "+json.getNewShift(),"Changed Employee Shift ", json.getCompanyId());
		}
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	   	
	   finally {
		DBUtil.closeConnection(connection);
		}

		
	}



	
	
	

}
