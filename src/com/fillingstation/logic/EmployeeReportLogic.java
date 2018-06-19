package com.fillingstation.logic;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.json.EmployeeMaintenanceJSON;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.util.DBUtil;

public class EmployeeReportLogic {

	/*
	 * function for generating employee maintenance report
	 */
	
	public static ArrayList<EmployeeReportJSON> EmployeeMaintenanceReport(EmployeeMaintenanceJSON details) {
		
			ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
			Connection connection=null;
			try {
				connection =DBUtil.getDBConnection();
				
				String querySelect=IQueryConstants.EMP_MAINTENANCE_REPORT;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				preparedStmt.setString(1,details.getCompanyId());
				
		        ResultSet rs=preparedStmt.executeQuery();
		        while(rs.next())
		        {
		        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        	employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
		        	employeeRetrieveobj.setFirstName(rs.getString("FirstName"));
		        	employeeRetrieveobj.setLastName(rs.getString("LastName"));
		        	employeeRetrieveobj.setEmployeeType(rs.getString("Type"));
		        	employeeRetrieveobj.setDepartment(rs.getString("Department"));
		        	employeeRetrieveobj.setRole(rs.getString("Role"));
		        	employeeRetrieveobj.setMobileNo(rs.getString("MobileNo"));
		        	
		        	employeeRetrievelist.add(employeeRetrieveobj);
		        }
		        connection.close();  
		        } catch (SQLException e)
		        {
		        e.printStackTrace();
		        }
		         	
			   finally {
				DBUtil.closeConnection(connection);
			}
		        
			   return employeeRetrievelist;
			
	    }
	
	/*
	 * function for generating employee count per type
	 */
	
public static ArrayList<EmployeeReportJSON> EmployeeMaintenanceReportSummary(EmployeeMaintenanceJSON details) {
		
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
			
			//Generating daily report list
			
			System.out.println("generating employee daily report list...........");
			
	String querySelect1=IQueryConstants.EMP_MAINTENANCE_COUNT_REPORT;
	PreparedStatement preparedStmt1= connection.prepareStatement(querySelect1);
	preparedStmt1.setString(1,"permanent");
	preparedStmt1.setString(2,details.getCompanyId());
	
	preparedStmt1.setString(3,"temporary");
	preparedStmt1.setString(4,details.getCompanyId());
	
	preparedStmt1.setString(5,"contract");
	preparedStmt1.setString(6,details.getCompanyId());
	
    ResultSet rs1=preparedStmt1.executeQuery();
    while(rs1.next())
    {
    EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
    employeeRetrieveobj.setNoOfPermanentEmployee(rs1.getInt("PermanentEmployeeCount"));
	employeeRetrieveobj.setNoOfTemporaryEmployee(rs1.getInt("TemporaryEmployeeCount"));
	employeeRetrieveobj.setNoOfContractEmployee(rs1.getInt("ContractEmployeeCount"));
	employeeRetrievelist.add(employeeRetrieveobj);
    }
    connection.close();  
    } catch (SQLException e)
    {
    e.printStackTrace();
    }
     	
   finally {
	DBUtil.closeConnection(connection);
}
    
   return employeeRetrievelist;
}

	/*
	 * function for generating employee daily attendance report
	 */
	
	public static ArrayList<EmployeeReportJSON> EmployeeDailyReport(EmployeeMaintenanceJSON details) {
		
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
			
			//Generating daily report list
			
			System.out.println("generating employee daily report list...........");
			String querySelect=IQueryConstants.EMP_DAILYREPORT;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1,details.getDate());
			preparedStmt.setString(2,details.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
	        while(rs.next())
	        {
	        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
	        	employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
	        	employeeRetrieveobj.setName(rs.getString("Name"));
	        	employeeRetrieveobj.setCheckinTime(rs.getString("CheckinTime"));
	        	employeeRetrieveobj.setCheckoutTime(rs.getString("CheckoutTime"));
	        	employeeRetrieveobj.setTotalWorkHour(rs.getString("TotalWorkHour"));
	        	employeeRetrieveobj.setEmployeeType(rs.getString("Type"));
	        	employeeRetrieveobj.setDepartment(rs.getString("Department"));
	        	employeeRetrieveobj.setStatus(rs.getString("Status"));
	        	employeeRetrieveobj.setAuthorizedBy(rs.getString("AuthorizedBy"));
	        	employeeRetrievelist.add(employeeRetrieveobj);
	        	
	        }
	        connection.close(); 
	        System.out.println("generated employee daily report list...........");
	        
		} catch (SQLException e)
    {
    e.printStackTrace();
    }
     	
   finally {
	DBUtil.closeConnection(connection);
}
		return employeeRetrievelist;
	}
	        
	 
	/*
	 * function for generating count of employees for the day to be displayed as summary
	 */
	
	  public static ArrayList<EmployeeReportJSON> EmployeeDailyReportSummary(EmployeeMaintenanceJSON details)
	    	{
	    		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	    		
	    		//Generating total employee count of the company
	    		
	    		Connection connection=null;
	    		try {
	    			connection =DBUtil.getDBConnection();
	    		
	        System.out.println("generating total employee count for daily report ...........");
	        String querySelect2=IQueryConstants.EMP_MAINTENANCE_COUNT;
			PreparedStatement preparedStmt2= connection.prepareStatement(querySelect2);
			preparedStmt2.setString(1,"permanent");
			preparedStmt2.setString(2,details.getCompanyId());
	        
			preparedStmt2.setString(3,"temporary");
			preparedStmt2.setString(4,details.getCompanyId());
	        
			preparedStmt2.setString(5,"contract");
			preparedStmt2.setString(6,details.getCompanyId());
	        ResultSet rs2=preparedStmt2.executeQuery();
	        while(rs2.next())
	        {
	 	        EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        employeeRetrieveobj.setNoOfPermanentEmployee(rs2.getInt("PermanentEmployeeCount"));
	        	employeeRetrieveobj.setNoOfTemporaryEmployee(rs2.getInt("TemporaryEmployeeCount"));
	        	employeeRetrieveobj.setNoOfContractEmployee(rs2.getInt("ContractEmployeeCount"));
	         	employeeRetrievelist.add(employeeRetrieveobj);
	         	System.out.println("Permanent Emp count........."+rs2.getInt("PermanentEmployeeCount"));
	         	
	      
	        }
	        
	        System.out.println("generated total employee count for daily report ...........");
	        
	        /*
	         * Generating count of employee PRESENT on the current day
	         */
	        
	        System.out.println("generating employee daily report PRESENT count...........");
	        String querySelect1=IQueryConstants.EMP_DAILYREPORTPRESENTCOUNT;
			PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
			preparedStmt1.setString(1,"permanent");
			preparedStmt1.setString(2,details.getDate());
			preparedStmt1.setString(3,details.getCompanyId());
			preparedStmt1.setString(4,"temporary");
			preparedStmt1.setString(5,details.getDate());
			preparedStmt1.setString(6,details.getCompanyId());
			preparedStmt1.setString(7,"contract");
			preparedStmt1.setString(8,details.getDate());
			preparedStmt1.setString(9,details.getCompanyId());
	        ResultSet rs1=preparedStmt1.executeQuery();
	        while(rs1.next())
	        { 
	        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        employeeRetrieveobj.setPermanentCountPresent(rs1.getInt("PermanentEmployeePresentCount"));
	        	employeeRetrieveobj.setTemporaryCountPresent(rs1.getInt("TemporaryEmployeePresentCount"));
	        	employeeRetrieveobj.setContractCountPresent(rs1.getInt("ContractEmployeePresentCount"));
	        	employeeRetrievelist.add(employeeRetrieveobj);
	        
	        	System.out.println("Permanent Emp present count........."+rs1.getInt("PermanentEmployeePresentCount"));
	         	
	        }
	        System.out.println("generated employee daily report PRESENT count after checkout...........");
	        
	        /*
	         * Generating count of employee ABSENT on the current day
	         */
	        
	        System.out.println("generating employee daily report ABSENT count...........");
	        String querySelect3=IQueryConstants.EMP_DAILYREPORTABSENTCOUNT;
			PreparedStatement preparedStmt3 = connection.prepareStatement(querySelect3);
			preparedStmt3.setString(1,"permanent");
			preparedStmt3.setString(2,details.getDate());
			preparedStmt3.setString(3,details.getCompanyId());
			preparedStmt3.setString(4,"temporary");
			preparedStmt3.setString(5,details.getDate());
			preparedStmt3.setString(6,details.getCompanyId());
			preparedStmt3.setString(7,"contract");
			preparedStmt3.setString(8,details.getDate());
			preparedStmt3.setString(9,details.getCompanyId());
	        ResultSet rs3=preparedStmt3.executeQuery();
	        while(rs3.next())
	        { 
	        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
	 	        employeeRetrieveobj.setPermanentCountAbsent(rs3.getInt("PermanentEmployeeAbsentCount"));
	         	employeeRetrieveobj.setTemporaryCountAbsent(rs3.getInt("TemporaryEmployeeAbsentCount"));
	         	employeeRetrieveobj.setContractCountAbsent(rs3.getInt("ContractEmployeeAbsentCount"));  
	         	employeeRetrievelist.add(employeeRetrieveobj);
	  	      
	        }
	        System.out.println("generated employee daily report count for ABSENT after checkout...........");
	        connection.close();  
	        } catch (SQLException e)
	        {
	        e.printStackTrace();
	        }
	         	
		   finally {
			DBUtil.closeConnection(connection);
		}
	        
		   return employeeRetrievelist;
		
    }

	  /*
		 * function for generating employee period attendance report
		 */
		
		public static ArrayList<EmployeeReportJSON> EmployeePeriodReport(EmployeeMaintenanceJSON details) {
		
			ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
			
			Connection connection=null;
			try {
				connection =DBUtil.getDBConnection();
				
				//Generating period report list
				
				String querySelect=IQueryConstants.EMP_PERIODREPORT;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				preparedStmt.setString(1,details.getFromDate());
				preparedStmt.setString(2,details.getToDate());
				preparedStmt.setString(3,details.getCompanyId());
				
				ResultSet rs=preparedStmt.executeQuery();
		        while(rs.next())
		        {
		        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        	employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
		        	employeeRetrieveobj.setName(rs.getString("Name"));
		        	employeeRetrieveobj.setCheckinTime(rs.getString("CheckinTime"));
		        	employeeRetrieveobj.setCheckoutTime(rs.getString("CheckoutTime"));
		        	employeeRetrieveobj.setTotalWorkHour(rs.getString("TotalWorkHour"));
		        	employeeRetrieveobj.setStatus(rs.getString("Status"));

		        	employeeRetrieveobj.setDate(rs.getString("Date"));
		        	employeeRetrieveobj.setEmployeeType(rs.getString("Type"));
		        	employeeRetrieveobj.setDepartment(rs.getString("Department"));
		        	employeeRetrievelist.add(employeeRetrieveobj);
		        }
		        connection.close();  
		        } catch (SQLException e)
		        {
		        e.printStackTrace();
		        }
		         	
			   finally {
				DBUtil.closeConnection(connection);
			}
		        
			   return employeeRetrievelist;
		}

		/*
		 * function for generating present and absent count of each employee over a period
		 */
		
		public static ArrayList<EmployeeReportJSON> EmployeePeriodReportSummary(EmployeeMaintenanceJSON details)
		{
			ArrayList<EmployeeReportJSON> employeeIdlist = new ArrayList<EmployeeReportJSON>();
			ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
			String hour;
			Connection connection=null;
			try {
				connection =DBUtil.getDBConnection();
				
				//Getting employeeid between the given period
		        String querySelect1=IQueryConstants.EMP_ID_SELECT;
				PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
				preparedStmt1.setString(1,details.getFromDate());
				preparedStmt1.setString(2,details.getToDate());
				preparedStmt1.setString(3,details.getCompanyId());
				
				ResultSet rs1=preparedStmt1.executeQuery();
		        while(rs1.next())
		        {
		        	EmployeeReportJSON employeeRetrieveobj1 = new EmployeeReportJSON();
		        	employeeRetrieveobj1.setEmployeeId(rs1.getString("EmployeeId"));
		        	employeeIdlist.add(employeeRetrieveobj1);
		        }
		        System.out.println(employeeIdlist);
				
		        for(EmployeeReportJSON rd : employeeIdlist) {
				System.out.println(rd.getEmployeeId());
				EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
	        	
				 String querySelect11=IQueryConstants.EMP_NAME_SELECT;
					PreparedStatement preparedStmt11 = connection.prepareStatement(querySelect11);
					preparedStmt11.setString(1,rd.getEmployeeId());
					preparedStmt11.setString(2,details.getCompanyId());

					ResultSet rs11=preparedStmt11.executeQuery();
			        while(rs11.next())
			        {
			        	employeeRetrieveobj.setEmployeeName(rs11.getString("Name"));
			        	
				employeeRetrieveobj.setEmployeeId(rd.getEmployeeId());
				 String querySelect2=IQueryConstants.EMP_PERIODREPORTPRESENTCOUNT;
					PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
					preparedStmt2.setString(1,rd.getEmployeeId());
					preparedStmt2.setString(2,details.getFromDate());
					preparedStmt2.setString(3,details.getToDate());
					preparedStmt2.setString(4,details.getCompanyId());
					ResultSet rs2=preparedStmt2.executeQuery();
			        while(rs2.next())
			        {
			        	employeeRetrieveobj.setNoOfDaysPresent(rs2.getInt("PresentDays"));
			        	//employeeRetrieveobj.setTotalWorkHour(rs2.getString("TotalWorkHour"));
			        	
			        	System.out.println("getting total workhour............");
			        	 String querySelect21=IQueryConstants.EMP_SELECT_PERIODTOTALWORKHOUR;
							PreparedStatement preparedStmt21 = connection.prepareStatement(querySelect21);
							preparedStmt21.setString(1,rd.getEmployeeId());
							preparedStmt21.setString(2,details.getFromDate());
							preparedStmt21.setString(3,details.getToDate());
							preparedStmt21.setString(4,details.getCompanyId());
							ResultSet rs21=preparedStmt21.executeQuery();
					        while(rs21.next())
					        {
					        	 hour = rs21.getString("totalworkhour");
					        	 System.out.println("getting total workhour............"+hour);
					        	 if(!hour.equals("-")) {
					        		 System.out.println("in if loop total workhour............");
					        		 String querySelect31=IQueryConstants.EMP_PERIOD_TOTALWORKHOUR;
										PreparedStatement preparedStmt31 = connection.prepareStatement(querySelect31);
										preparedStmt31.setString(1,rd.getEmployeeId());
										preparedStmt31.setString(2,details.getFromDate());
										preparedStmt31.setString(3,details.getToDate());
										preparedStmt31.setString(4,details.getCompanyId());
										ResultSet rs31=preparedStmt31.executeQuery();
								        while(rs31.next())
								        {
								        	employeeRetrieveobj.setTotalWorkHour(rs31.getString("totalworkHour"));
					        	 }
					        	
					        	 }
			    
			       String querySelect3=IQueryConstants.EMP_PERIODREPORTABSENTCOUNT;
				PreparedStatement preparedStmt3= connection.prepareStatement(querySelect3);
				preparedStmt3.setString(1,rd.getEmployeeId());
				preparedStmt3.setString(2,details.getFromDate());
				preparedStmt3.setString(3,details.getToDate());
				preparedStmt3.setString(4,details.getCompanyId());
				ResultSet rs3=preparedStmt3.executeQuery();
		        while(rs3.next())
		        {
		        	employeeRetrieveobj.setNoOfDaysAbsent(rs3.getInt("AbsentDays"));
		        	
		        
		        }	
			        }
		        }
		        }
			        employeeRetrievelist.add(employeeRetrieveobj);
					
		        }
		        connection.close(); 
			
			}
			 catch (SQLException e)
		{
		e.printStackTrace();
		}
		 	
		finally {
		DBUtil.closeConnection(connection);
		}

			return employeeRetrievelist;
		}

		
		/*
		 * function for generating employee monthly attendance report
		 */
		
 public static ArrayList<EmployeeReportJSON> EmployeeMonthlyReport(EmployeeMaintenanceJSON details) throws ParseException {
			
	ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
	DateFormat inputDF  = new SimpleDateFormat("yy-MM-dd");
	Date date1 = inputDF.parse(details.getDate());
	 
	Calendar cal = Calendar.getInstance();
	cal.setTime(date1);
	 
	int month = (cal.get(Calendar.MONTH))+1;
	System.out.println("....month \n"+month);
	int year = cal.get(Calendar.YEAR);
	System.out.println("............year\n"+year);
	 

	//String month=MonthFormatting(details.getDate());
	//String year=YearFormatting(details.getDate());
	System.out.println("...month of monthly report list.........\n"+month);
	System.out.println("...year of monthly report list.........\n"+year);

	System.out.println("in functioon for generating the monthly report list............");
			Connection connection=null;
			try {
				connection =DBUtil.getDBConnection();
				
				String querySelect=IQueryConstants.EMP_MONTHLYREPORT;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				preparedStmt.setInt(1,month);
				preparedStmt.setInt(2,year);
				preparedStmt.setString(3,details.getCompanyId());
				
				ResultSet rs=preparedStmt.executeQuery();
		        while(rs.next())
		        {
		        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        	employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
		        	employeeRetrieveobj.setName(rs.getString("Name"));
		        	employeeRetrieveobj.setCheckinTime(rs.getString("CheckinTime"));
		        	employeeRetrieveobj.setCheckoutTime(rs.getString("CheckoutTime"));
		        	employeeRetrieveobj.setTotalWorkHour(rs.getString("TotalWorkHour"));
		        	employeeRetrieveobj.setDate(rs.getString("Date"));
		        	employeeRetrieveobj.setEmployeeType(rs.getString("Type"));
		        	employeeRetrieveobj.setDepartment(rs.getString("Department"));
		        	employeeRetrieveobj.setStatus(rs.getString("Status"));
		        	employeeRetrievelist.add(employeeRetrieveobj);
		        }
		        System.out.println("completed generating the monthly report list............");
		        connection.close();  
		        } catch (SQLException e)
		        {
		        e.printStackTrace();
		        }
		         	
			   finally {
				DBUtil.closeConnection(connection);
			}

			return employeeRetrievelist;
		}

		/*
		 * function for generating present and absent count of each employee over a period
		 */

		public static ArrayList<EmployeeReportJSON> EmployeeMonthlyReportSummary(EmployeeMaintenanceJSON details) throws ParseException
		{
			
			ArrayList<EmployeeReportJSON> employeeIdlist = new ArrayList<EmployeeReportJSON>();
			ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
			
			System.out.println("in functioon for generating the monthly report summary...........");
			DateFormat inputDF  = new SimpleDateFormat("yy-MM-dd");
			Date date1 = inputDF.parse(details.getDate());
			 
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			 
			int month = (cal.get(Calendar.MONTH))+1;
			System.out.println("....month \n"+month);
			int year = cal.get(Calendar.YEAR);
			System.out.println("............year\n"+year);
			Connection connection=null;
			String hour;
			try {
				System.out.println("selecting employeeid for  monthly report summary............");
				connection =DBUtil.getDBConnection();
				System.out.println("...month of monthly report list.........\n"+month);
				System.out.println("...year of monthly report list.........\n"+year);
				//Getting employeeid between the given period
		        String querySelect1=IQueryConstants.EMP_MONTHLYID_SELECT;
				PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
				preparedStmt1.setInt(1,month);
				preparedStmt1.setInt(2,year);
				preparedStmt1.setString(3,details.getCompanyId());
				
				ResultSet rs1=preparedStmt1.executeQuery();
		        while(rs1.next())
		        {
		        	EmployeeReportJSON employeeRetrieveobj1 = new EmployeeReportJSON();
		        	employeeRetrieveobj1.setEmployeeId(rs1.getString("EmployeeId"));
		        	employeeIdlist.add(employeeRetrieveobj1);
		        }
		        System.out.println("generating count of days for each employee in the monthly report summary............");
		        for(EmployeeReportJSON rd : employeeIdlist) {
		        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
		        	
				System.out.println(rd.getEmployeeId());
				 String querySelect11=IQueryConstants.EMP_NAME_SELECT;
					PreparedStatement preparedStmt11 = connection.prepareStatement(querySelect11);
					preparedStmt11.setString(1,rd.getEmployeeId());
					preparedStmt11.setString(2,details.getCompanyId());

					ResultSet rs11=preparedStmt11.executeQuery();
			        while(rs11.next())
			        {
			        	
			        	employeeRetrieveobj.setEmployeeName(rs11.getString("Name"));
				       employeeRetrieveobj.setEmployeeId(rd.getEmployeeId());
		
			
				System.out.println("generating the monthly report summary for present count...........");
				 String querySelect2=IQueryConstants.EMP_MONTHLYREPORTPRESENTCOUNT;
					PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
					preparedStmt2.setString(1,rd.getEmployeeId());
					preparedStmt2.setInt(2,month);
					preparedStmt2.setInt(3,year);
					preparedStmt2.setString(4,details.getCompanyId());
					ResultSet rs2=preparedStmt2.executeQuery();
			        while(rs2.next())
			        {
			        	
			        	employeeRetrieveobj.setNoOfDaysPresent(rs2.getInt("PresentDays"));
			        	//employeeRetrieveobj.setTotalWorkHour(rs2.getString("TotalWorkHour"));
			        	System.out.println("getting total workhour............");
			        	 String querySelect21=IQueryConstants.EMP_SELECT_TOTALWORKHOUR;
							PreparedStatement preparedStmt21 = connection.prepareStatement(querySelect21);
							preparedStmt21.setString(1,rd.getEmployeeId());
							preparedStmt21.setInt(2,month);
							preparedStmt21.setInt(3,year);
							preparedStmt21.setString(4,details.getCompanyId());
							ResultSet rs21=preparedStmt21.executeQuery();
					        while(rs21.next())
					        {
					        	 hour = rs21.getString("TotalWorkhour");
					        	 System.out.println("getting total workhour............"+hour);
					        	 if(!hour.equals("-")) {
					        		 System.out.println("in if loop total workhour............");
					        		 String querySelect31=IQueryConstants.EMP_MONTHLY_TOTALWORKHOUR;
										PreparedStatement preparedStmt31 = connection.prepareStatement(querySelect31);
										preparedStmt31.setString(1,rd.getEmployeeId());
										preparedStmt31.setInt(2,month);
										preparedStmt31.setInt(3,year);
										preparedStmt31.setString(4,details.getCompanyId());
										ResultSet rs31=preparedStmt31.executeQuery();
								        while(rs31.next())
								        {
								        	employeeRetrieveobj.setTotalWorkHour(rs31.getString(1));
								        	System.out.println("in if loop total workhour............"+rs31.getString(1));
							        		
					        	 }
								         
					        	
					        	 }
			    
			        System.out.println("generating the monthly report summary for absent count............");
			       String querySelect3=IQueryConstants.EMP_MONTHLYREPORTABSENTCOUNT;
				PreparedStatement preparedStmt3= connection.prepareStatement(querySelect3);
				preparedStmt3.setString(1,rd.getEmployeeId());
				preparedStmt3.setInt(2,month);
				preparedStmt3.setInt(3,year);
				preparedStmt3.setString(4,details.getCompanyId());
				ResultSet rs3=preparedStmt3.executeQuery();
		        while(rs3.next())
		        {
		        	employeeRetrieveobj.setNoOfDaysAbsent(rs3.getInt("AbsentDays"));
		        	
		        	}
		        
		        }
		        }
		        }
			        employeeRetrievelist.add(employeeRetrieveobj);
					   
		        
		        }
		        connection.close(); 
		        
			}
			 catch (SQLException e)
		{
		e.printStackTrace();
		}
		 	
		finally {
		DBUtil.closeConnection(connection);
		}
			System.out.println("completed generating the monthly report summary............");
			return employeeRetrievelist;
				}

		

		 /*
		 * function for generating audit report
		 */

		public static ArrayList<EmployeeReportJSON>AuditReportDisplay(EmployeeReportJSON details) {
			
				ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
				Connection connection=null;
				try {
					
					connection =DBUtil.getDBConnection();
					
					String querySelect=IQueryConstants.EMP_SELECT_AUDIT_REPORT;
					System.out.println("companyID...."+querySelect);
					PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
					preparedStmt.setString(1,details.getCompanyId());
					//preparedStmt.setString(2,details.getDate());
					
			        ResultSet rs=preparedStmt.executeQuery();
			        
			       
			        while(rs.next())
			        {
			        	EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
			        	employeeRetrieveobj.setSuperiorId(rs.getString("SuperiorId"));
			        	employeeRetrieveobj.setName(rs.getString("Name"));
			        	employeeRetrieveobj.setRole(rs.getString("Role"));
			        	employeeRetrieveobj.setOperation(rs.getString("Operation"));
			        	employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
			        	employeeRetrieveobj.setDate(rs.getString("Date"));
			        	employeeRetrieveobj.setTime(rs.getString("Time"));
			        	
			        	
			        	employeeRetrievelist.add(employeeRetrieveobj);
			        }
			        connection.close();  
			        } catch (SQLException e)
			        {
			        e.printStackTrace();
			        }
			         	
				   finally {
					DBUtil.closeConnection(connection);
				}
			        
				   return employeeRetrievelist;
				
		    }
		

		/*
		 * function for searching an employee
		 */

		public static ArrayList<EmployeeReportJSON> AuditSearchEmployee(EmployeeReportJSON details) {
			Connection connection=null;
			ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
			try {
				System.out.println("getting the employee details for searching............");
				connection =DBUtil.getDBConnection();
				String querySelect=IQueryConstants.EMP_AUDIT_SEARCH;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				    preparedStmt.setString(1,details.getSearch());
				    preparedStmt.setString(2,details.getSearch());
				    preparedStmt.setString(3,details.getSearch());
				    preparedStmt.setString(4,details.getSearch());
				    preparedStmt.setString(5,details.getSearch());
				    preparedStmt.setString(6,details.getSearch());
				    preparedStmt.setString(7,details.getCompanyId());
					
				    ResultSet rs=preparedStmt.executeQuery();
				   
			        while(rs.next())
			        {
			        	EmployeeReportJSON searchEmpDetails = new EmployeeReportJSON();
			        	searchEmpDetails.setSuperiorId(rs.getString("SuperiorId"));
			            searchEmpDetails.setEmployeeName(rs.getString("Name"));
			        	searchEmpDetails.setRole(rs.getString("Role"));
			        	searchEmpDetails.setOperation(rs.getString("Operation"));
			        	searchEmpDetails.setDate(rs.getString("date"));
			        	searchEmpDetails.setTime(rs.getString("time"));
			        	employeeRetrievelist.add(searchEmpDetails);
			        }
				    System.out.println("completed searching returing to webservice............");
				    connection.close(); 	 
		    } catch (SQLException e)
		    {
		    e.printStackTrace();
		    }
		     	
		   finally {
			DBUtil.closeConnection(connection);
		}
			return employeeRetrievelist;
		}
		




}