package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.constants.NewQueryConstants;
import com.fillingstation.json.EmployeeMaintenanceJSON;
import com.fillingstation.json.EmployeeReportJSON;
import com.fillingstation.util.DBUtil;

public class Report {
	/*
	 * FUNCTION FOR RETRIEVING INDIVIDUAL DAILY REPORT
	 */
	public static ArrayList<EmployeeReportJSON> EmployeeindividualDailyReport(EmployeeMaintenanceJSON details) throws ParseException {
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		
		Connection connection=null;
		String tableName=details.getCompanyId()+"HolidayTable";
		
		List<String> holidayList =new ArrayList<String>();
		
		try {
			connection =DBUtil.getDBConnection();
			System.out.println("Calling Daily Report Working Function ........... \n");
			
			employeeRetrievelist=DailyReportWorking(details,details.getEmployeeId(),details.getDate());
				
		}
     	
   finally {
	DBUtil.closeConnection(connection);
}
		return employeeRetrievelist;

	}

	/*
	 * DAILY REPORT WORKING FUNCTION
	 */
	public static ArrayList<EmployeeReportJSON> DailyReportWorking(EmployeeMaintenanceJSON details, String empId,
			String date) {

		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		List<String> empList = new ArrayList<String>();
		String tableName = details.getCompanyId() + "HolidayTable";

		List<String> holidayList = new ArrayList<String>();
		int absentcount = details.getAbsentCount();
		String sampId = null;
		Connection connection = null;

		try {
			connection = DBUtil.getDBConnection();
			System.out.println("generating employee daily report list..........." + date);
			String querySelect = NewQueryConstants.EMP_DAILYREPORT1;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1, details.getCompanyId());
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, empId);

			ResultSet rs = preparedStmt.executeQuery();
			System.out.println("The rs data in report :\t");
			while (rs.next()) {

				EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
				employeeRetrieveobj.setEmployeeId(rs.getString("EmployeeId"));
				sampId = employeeRetrieveobj.getEmployeeId();
				employeeRetrieveobj.setName(rs.getString("Name"));
				employeeRetrieveobj.setCheckinTime(rs.getString("CheckinTime"));
				employeeRetrieveobj.setCheckoutTime(rs.getString("CheckoutTime"));
				employeeRetrieveobj.setTotalWorkHour(rs.getString("TotalWorkHour"));
				employeeRetrieveobj.setEmployeeType(rs.getString("Type"));
				employeeRetrieveobj.setDepartment(rs.getString("Department"));
				employeeRetrieveobj.setStatus(rs.getString("Status"));
				employeeRetrieveobj.setDate(rs.getString("Date"));
				employeeRetrieveobj.setAuthorizedBy(rs.getString("AuthorizedBy"));

				employeeRetrievelist.add(employeeRetrieveobj);

			}
			System.out.println("employeeId is:\t" + empId);

			if (sampId == null) {
				System.out.println("null value \n");

				String querySelect1 = NewQueryConstants.EMP_DAILYREPORTSHIFT;
				PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
				preparedStmt1.setString(1, empId);
				preparedStmt1.setString(2, details.getCompanyId());
				ResultSet rs1 = preparedStmt1.executeQuery();
				String employeeId = null;
				String name = null;
				String type = null;
				String department = null;
				String shift = null;
				while (rs1.next()) {

					employeeId = rs1.getString("EmployeeId");
					name = rs1.getString("Name");
					type = rs1.getString("Type");
					department = rs1.getString("Department");
					shift = rs1.getString("Shift");

				}
				System.out.println("EmployeeId Shift" + shift);

				System.out.println("searching in Holiday Table \n");

				String querySelect2 = NewQueryConstants.EMP_DAILYREPORTHOLIDAY.replace("$tableName", tableName)
						.replace("$Shift", "Shift" + shift);
				PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
				preparedStmt2.setString(1, date);
				ResultSet rs2 = preparedStmt2.executeQuery();
				String holiday = null;
				String shiftHoliday = null;
				while (rs2.next()) {
					holiday = rs2.getString("HoliDayShift");
					shiftHoliday = rs2.getString(2);
					System.out.println("test \n" + holiday + "Shift Holiday" + shiftHoliday);
				}

				String status = null;
				if (holiday == null && shiftHoliday == null) {
					// Both Value Are Null So increase Absent Count
					System.out.println("test inside if \n");
					System.out.println("Employee is Absent No weekEnd and National Holiday \n");
					status = "A";
					absentcount++;
					details.setAbsentCount(absentcount);

				} else if (holiday == null) {
					// Its not any national Holiday So check it has Weekend
					// Holiday
					if (shiftHoliday.equals("0") || shiftHoliday.equals("H") ) {
						status = "H";
						System.out.println("WeekEnd Holiday For Shift \n" + shift);
					} else {
						status = "A";
						absentcount++;
						details.setAbsentCount(absentcount);
						System.out.println(" No WeekEnd Holiday For Shift \n" + shift);

					}

				} else {
					// This day has Shift Holiday Check Whether Employee Shift
					// is same as HolidayShift
					System.out.println("test inside else \n");
					holidayList = Arrays.asList((holiday.split(",")));
					System.out.println("test \n");
					if (holidayList.contains(shift)) {
						status = "H";
						System.out.println("Holiday declared \n" + status);

					} else {
						status = "A";
						absentcount++;
						details.setAbsentCount(absentcount);
						System.out.println("Holiday not declared for the employee shift \n" + status);
					}
				}

				EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
				employeeRetrieveobj.setEmployeeId(employeeId);
				employeeRetrieveobj.setName(name);
				employeeRetrieveobj.setEmployeeType(type);
				employeeRetrieveobj.setDepartment(department);
				employeeRetrieveobj.setCheckinTime("-");
				employeeRetrieveobj.setCheckoutTime("-");
				employeeRetrieveobj.setTotalWorkHour("-");
				employeeRetrieveobj.setStatus(status);
				employeeRetrieveobj.setDate(date);
				employeeRetrievelist.add(employeeRetrieveobj);

			} // empid if loop
			else {
				System.out.println("not null value \n");
			}

			connection.close();
			System.out.println("generated employee daily report list...........");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			DBUtil.closeConnection(connection);
		}
		return employeeRetrievelist;

	}
	
/*
	 * FUNCTION FOR RETRIEVING ORGANIZATION DAILY REPORT
	 */
public static ArrayList<EmployeeReportJSON> EmployeeOrganizationDailyReport(EmployeeMaintenanceJSON details) {
		
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		List<String> empList =new ArrayList<String>();
		
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
			String employeeId;
			//Generating daily report list
			
			System.out.println("Getting EmployeeList \n");
			String querySelect=NewQueryConstants.EMP_REPORTLIST;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1,details.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
	        while(rs.next())
	        {
	        	employeeId=rs.getString("EmployeeId");
	        	employeeRetrievelist.addAll(DailyReportWorking(details,employeeId,details.getDate()));
	    		
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
    		ArrayList<EmployeeReportJSON> employeelistTotal = new ArrayList<EmployeeReportJSON>();
    		List<String> empListTotal =new ArrayList<String>();
    		List<String> empListAttendance =new ArrayList<String>();
    		List<String> empListAbsentCheck =new ArrayList<String>();
    		List<String> holidayList =new ArrayList<String>();
    		EmployeeReportJSON datajson=new EmployeeReportJSON();
    		String tableName=details.getCompanyId()+"HolidayTable";
    		
    		//Generating total employee count of the company
    		String empId;
    		int count=0;
    		Connection connection=null;
    		try {
    			connection =DBUtil.getDBConnection();
    		
    			System.out.println("---------SUMMARY------------- \n");
    			System.out.println("getting total employee id from employeetable \n");
    			
    			String querySelect11=NewQueryConstants.EMP_TOTALID;
    			PreparedStatement preparedStmt11= connection.prepareStatement(querySelect11);
    			preparedStmt11.setString(1,details.getCompanyId());
    			ResultSet rs11=preparedStmt11.executeQuery();
    			while(rs11.next()) {
    				empListTotal.add(rs11.getString("EmployeeId"));
    				System.out.println(rs11.getString("EmployeeId"));
    			}

    			System.out.println("Total employee id from employeetable are \n");
    			for(String empid:empListTotal) {
    				System.out.println(empid);
    	    		
    			}
    			
    			System.out.println("getting total employee id from employeeattendancetable \n");
    			String querySelect12=NewQueryConstants.EMP_ATTNTOTALID;
    			PreparedStatement preparedStmt12= connection.prepareStatement(querySelect12);
    			preparedStmt12.setString(1,details.getCompanyId());
    			preparedStmt12.setString(2,details.getDate());
    			ResultSet rs12=preparedStmt12.executeQuery();
    			while(rs12.next()) {
    				empListAttendance.add(rs12.getString("EmployeeId"));
    			}
    			
    			System.out.println("Total employee id from employeeAttendancetable are \n");
    			for(String empid:empListAttendance) {
    				System.out.println(empid);
    			}
    			
    			/*List<String> intersection = new ArrayList(shiftListDB);
    			intersection.retainAll(shiftList);
    			  for (String diffElement : intersection) {
    			        System.out.println("Common element : \t"+diffElement.toString());
    			        shiftListNotNull.add(diffElement.toString());
    			    	
    			    }*/
    			  
    			  List<String> symmetricDifference = new ArrayList(empListTotal);
    			  symmetricDifference.removeAll(empListAttendance);
    			  for (String diffElement : symmetricDifference) {
    			        System.out.println("Un Common element : \t"+diffElement.toString());
    			        empListAbsentCheck.add(diffElement.toString());
    			    	
    			    }
    			
    			System.out.println("updating ABSENT count \n");
    			
    			System.out.println("Slecting HolidaY Shift \n");	
    			
    			
    			   String querySelect13=NewQueryConstants.EMP_DAILYREPORTHOLIDAY.replace("$tableName", tableName);
    						PreparedStatement preparedStmt13 = connection.prepareStatement(querySelect13);
    						preparedStmt13.setString(1,details.getDate());
    						ResultSet rs13=preparedStmt13.executeQuery();
    						String holiday = null;
							while(rs13.next()) {
    							holiday=rs13.getString("HoliDayShift" );
    						}
    						//holidayList=holiday.split(",");
    						System.out.println("HolidayShifts \n"+holiday);
    					    
    						String shift = null;
    						String type = null;
    						EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
    						int permanentcountAbsent = employeeRetrieveobj.getPermanentCountAbsent();
    			    		int temporarycountAbsent=employeeRetrieveobj.getTemporaryCountAbsent();
    			    		int contractcountAbsent=employeeRetrieveobj.getContractCountAbsent();
    						if(holiday==null) {
    							
    							
    							System.out.println("NO holiday Shifts \n");
        						for(int z=0;z<empListAbsentCheck.size();z++) {
            						
        							System.out.println("----EMPLOYEEID from absent list :\t"+empListAbsentCheck.get(z));
        							String querySelect14=NewQueryConstants.EMP_ABSENTINFO;
            						PreparedStatement preparedStmt14 = connection.prepareStatement(querySelect14);
            						preparedStmt14.setString(1,empListAbsentCheck.get(z));
            						preparedStmt14.setString(2,details.getCompanyId());
            						ResultSet rs14=preparedStmt14.executeQuery();
            						while(rs14.next()) {
            				  shift=rs14.getString("Shift");
            				  type=rs14.getString("Type");
        							
            						}
        							System.out.println("SHIFT & TYPE :\t"+shift +","+type);
          								
        								if(type.equals("Permanent")) {
        								permanentcountAbsent++;
        								         
        						 	}else if(type.equals("Temporary")) {
        						 		
        									temporarycountAbsent++;
        										}else if(type.equals("Contract")) {
        									contractcountAbsent++;
        								
        								}
        								System.out.println("Holiday not declared for the employee shift \n"+permanentcountAbsent);
        								 employeeRetrieveobj.setPermanentCountAbsent(permanentcountAbsent);
        								 employeeRetrieveobj.setTemporaryCountAbsent(temporarycountAbsent);
        								employeeRetrieveobj.setContractCountAbsent(contractcountAbsent);
                							
        									
        							}
        						employeeRetrievelist.add(employeeRetrieveobj);
    			     			
        							
        							 			
        					}else {
        			
        						System.out.println("Holidays available \n"+holidayList);
        						holidayList=Arrays.asList((holiday.split(",")));
        						System.out.println("test \n");
    						
    						for(int z=0;z<empListAbsentCheck.size();z++) {
    						System.out.println("emplist"+empListAbsentCheck.get(z));
    							String querySelect14=NewQueryConstants.EMP_ABSENTINFO;
        						PreparedStatement preparedStmt14 = connection.prepareStatement(querySelect14);
        						preparedStmt14.setString(1,empListAbsentCheck.get(z));
        						preparedStmt14.setString(2,details.getCompanyId());
        						ResultSet rs14=preparedStmt14.executeQuery();
        						while(rs14.next()) {
        				  shift=rs14.getString("Shift");
        				  type=rs14.getString("Type");
    							
        						}
        						if(holidayList.contains(shift)){
        							System.out.println("Holiday declared \n");
        							
        							}else {
        								if(type.equals("Permanent")) {
        								permanentcountAbsent++;
        								System.out.println(empListAbsentCheck.get(z)+"---PERMANENT COUNT-------"+permanentcountAbsent);
        								          
        						 	}else if(type.equals("Temporary")) {
        									temporarycountAbsent++;
        									System.out.println(empListAbsentCheck.get(z)+"---TEMPORARY COUNT-------"+temporarycountAbsent);
        	    							
        									
        						 	}else if(type.equals("Contract")) {
        									contractcountAbsent++;
        									System.out.println(empListAbsentCheck.get(z)+"---CONTRACT COUNT-------"+contractcountAbsent);
        	    							
        									
        								}
        								System.out.println("Holiday not declared for the employee shift \n");
        										
        							}
        					}
    							
    						/*for(int y=0;y<holidayList.size();y++) {
    							System.out.println("Holiday Shift \n"+holidayList.get(y));
    							if(holidayList.get(y).equals(shift)) {
    								
    							System.out.println("Holiday declared \n");
    							break;
    							}else {
    								if(type.equals("Permanent")) {
    								permanentcountAbsent++;
    								System.out.println(empListAbsentCheck.get(z)+"---PERMANENT COUNT-------"+permanentcountAbsent);
    								          
    						 	}else if(type.equals("Temporary")) {
    									temporarycountAbsent++;
    									System.out.println(empListAbsentCheck.get(z)+"---TEMPORARY COUNT-------"+temporarycountAbsent);
    	    							
    									
    						 	}else if(type.equals("Contract")) {
    									contractcountAbsent++;
    									System.out.println(empListAbsentCheck.get(z)+"---CONTRACT COUNT-------"+contractcountAbsent);
    	    							
    									
    								}
    								System.out.println("Holiday not declared for the employee shift \n");
    										
    							}
    							
    							
    							 			
    					}*/
    			
    						
    						  employeeRetrieveobj.setPermanentCountAbsent(permanentcountAbsent);
    						  employeeRetrieveobj.setTemporaryCountAbsent(temporarycountAbsent);
    						  employeeRetrieveobj.setContractCountAbsent(contractcountAbsent);
  							
    						  employeeRetrievelist.add(employeeRetrieveobj);
      			     		
        					}
    						
    						
    						System.out.println("checking whether the id is absent or its leave \n");
    						
    							
        System.out.println("generating total employee count for daily report ...........");
        String querySelect2=IQueryConstants.EMP_MAINTENANCE_COUNT;
		PreparedStatement preparedStmt2= connection.prepareStatement(querySelect2);
		preparedStmt2.setString(1,"Permanent");
		preparedStmt2.setString(2,details.getCompanyId());
        
		preparedStmt2.setString(3,"Temporary");
		preparedStmt2.setString(4,details.getCompanyId());
        
		preparedStmt2.setString(5,"Contract");
		preparedStmt2.setString(6,details.getCompanyId());
        ResultSet rs2=preparedStmt2.executeQuery();
        while(rs2.next())
        {
 	       // EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
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
        String querySelect1=NewQueryConstants.EMP_DAILYREPORTPRESENTCOUNT;
		PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
		preparedStmt1.setString(1,"Permanent");
		preparedStmt1.setString(2,details.getDate());
		preparedStmt1.setString(3,details.getCompanyId());
		preparedStmt1.setString(4,"Temporary");
		preparedStmt1.setString(5,details.getDate());
		preparedStmt1.setString(6,details.getCompanyId());
		preparedStmt1.setString(7,"Contract");
		preparedStmt1.setString(8,details.getDate());
		preparedStmt1.setString(9,details.getCompanyId());
        ResultSet rs1=preparedStmt1.executeQuery();
        while(rs1.next())
        { 
        	//EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
	        employeeRetrieveobj.setPermanentCountPresent(rs1.getInt("PermanentEmployeePresentCount"));
        	employeeRetrieveobj.setTemporaryCountPresent(rs1.getInt("TemporaryEmployeePresentCount"));
        	employeeRetrieveobj.setContractCountPresent(rs1.getInt("ContractEmployeePresentCount"));
        	employeeRetrievelist.add(employeeRetrieveobj);
        
        	System.out.println("Permanent Emp present count........."+rs1.getInt("PermanentEmployeePresentCount"));
         	
        }
        System.out.println("generated employee daily report PRESENT count after checkout...........");
        
        
        /*
         * Generating count of employee Leave
         */
        
        System.out.println("generating employee daily report LEAVE count...........");
        String querySelect5=NewQueryConstants.EMP_DAILYREPORTLEAVECOUNT;
		PreparedStatement preparedStmt5 = connection.prepareStatement(querySelect5);
		preparedStmt5.setString(1,"Permanent");
		preparedStmt5.setString(2,details.getDate());
		preparedStmt5.setString(3,details.getCompanyId());
		preparedStmt5.setString(4,"Temporary");
		preparedStmt5.setString(5,details.getDate());
		preparedStmt5.setString(6,details.getCompanyId());
		preparedStmt5.setString(7,"Contract");
		preparedStmt5.setString(8,details.getDate());
		preparedStmt5.setString(9,details.getCompanyId());
        ResultSet rs5=preparedStmt5.executeQuery();
        while(rs5.next())
        { 
        	//EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
	        employeeRetrieveobj.setPermanentCountLeave(rs5.getInt("PermanentEmployeeLeaveCount"));
        	employeeRetrieveobj.setTemporaryCountLeave(rs5.getInt("TemporaryEmployeeLeaveCount"));
        	employeeRetrieveobj.setContractCountLeave(rs5.getInt("ContractEmployeeLeaveCount"));
        	employeeRetrievelist.add(employeeRetrieveobj);
        
        	System.out.println("Permanent Emp leave count........."+rs5.getInt("PermanentEmployeeLeaveCount"));
         	
        }
        System.out.println("generated employee daily report LEAVE count after checkout...........");
        
       
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
	 * function for generating employee individual report over a period
	 */
	public static ArrayList<EmployeeReportJSON> EmployeeIndividualPeriodReport(EmployeeMaintenanceJSON details) throws ParseException {
						
						ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
						EmployeeReportJSON periodReport=new EmployeeReportJSON();
						Connection connection=null;
						try {
							connection =DBUtil.getDBConnection();
							
							List<Date> dates = new ArrayList<Date>();

							DateFormat formatter ; 

							formatter = new SimpleDateFormat("yyyy-MM-dd");
							Date  startDate = (Date)formatter.parse(details.getFromDate()); 
							Date  endDate = (Date)formatter.parse(details.getToDate());
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
							   // employeeRetrievelist=  DailyReportWorking(details,details.getEmployeeId(),date);
							    
							  employeeRetrievelist.addAll(DailyReportWorking(details,details.getEmployeeId(),date));
							    
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
	
	public static ArrayList<EmployeeReportJSON> EmployeeIndividualPeriodReportSummary(EmployeeMaintenanceJSON details,String employeeId)
	{
		ArrayList<EmployeeReportJSON> employeeIdlist = new ArrayList<EmployeeReportJSON>();
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		String hour;
		int absent;
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
			
			
	        
			EmployeeReportJSON employeeRetrieveobj = new EmployeeReportJSON();
      	
			 String querySelect11=IQueryConstants.EMP_NAME_SELECT;
				PreparedStatement preparedStmt11 = connection.prepareStatement(querySelect11);
				preparedStmt11.setString(1,employeeId);
				preparedStmt11.setString(2,details.getCompanyId());

				ResultSet rs11=preparedStmt11.executeQuery();
		        while(rs11.next())
		        {
		        	employeeRetrieveobj.setEmployeeName(rs11.getString("Name"));
		        	
			
			 String querySelect2=NewQueryConstants.EMP_PERIODREPORTPRESENTCOUNT;
				PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
				preparedStmt2.setString(1,employeeId);
				preparedStmt2.setString(2,details.getFromDate());
				preparedStmt2.setString(3,details.getToDate());
				preparedStmt2.setString(4,details.getCompanyId());
				ResultSet rs2=preparedStmt2.executeQuery();
		        while(rs2.next())
		        {
		        	employeeRetrieveobj.setNoOfDaysPresent(rs2.getInt("PresentDays"));
		        	//employeeRetrieveobj.setTotalWorkHour(rs2.getString("TotalWorkHour"));
		        	
		        	System.out.println("getting total workhour............");
		        	 String querySelect21=NewQueryConstants.EMP_SELECT_PERIODTOTALWORKHOUR;
						PreparedStatement preparedStmt21 = connection.prepareStatement(querySelect21);
						preparedStmt21.setString(1,details.getEmployeeId());
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
				        		 String querySelect31=NewQueryConstants.EMP_INDIVIDUALPERIOD_TOTALWORKHOUR;
									PreparedStatement preparedStmt31 = connection.prepareStatement(querySelect31);
									preparedStmt31.setString(1,details.getEmployeeId());
									preparedStmt31.setString(2,details.getCompanyId());
									preparedStmt31.setString(3,details.getFromDate());
									preparedStmt31.setString(4,details.getToDate());
									
									ResultSet rs31=preparedStmt31.executeQuery();
							        while(rs31.next())
							        {
							        	employeeRetrieveobj.setTotalWorkHour(rs31.getString("totalworkHour"));
				        	 }
				        	
				        	 }
		    
		       String querySelect3=NewQueryConstants.EMP_PERIODREPORTLEAVECOUNT;
			PreparedStatement preparedStmt3= connection.prepareStatement(querySelect3);
			preparedStmt3.setString(1,employeeId);
			preparedStmt3.setString(2,details.getFromDate());
			preparedStmt3.setString(3,details.getToDate());
			preparedStmt3.setString(4,details.getCompanyId());
			ResultSet rs3=preparedStmt3.executeQuery();
	        while(rs3.next())
	        {
	        	employeeRetrieveobj.setNoOfDaysLeave((rs3.getInt("LeaveDays")));
	        	
	        
	        }	
		        }
	        }
	        }
		        absent=details.getAbsentCount();
		        employeeRetrieveobj.setNoOfDaysAbsent(absent);
		        employeeRetrieveobj.setEmployeeId(employeeId);
		        employeeRetrievelist.add(employeeRetrieveobj);
				
	        
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
	 * function for generating report list for  each employee over a period
	 */

	public static ArrayList<EmployeeReportJSON> EmployeeOrganizationPeriodReport(EmployeeMaintenanceJSON details) throws ParseException {
		
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		List<String> empList =new ArrayList<String>();
		
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
						
			System.out.println("Getting EmployeeList \n");
			String querySelect=NewQueryConstants.EMP_REPORTLIST;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1,details.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
	        while(rs.next())
	        {
	        	empList.add(rs.getString("EmployeeId"));
	        }
			
			List<Date> dates = new ArrayList<Date>();

			DateFormat formatter ; 

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date  startDate = (Date)formatter.parse(details.getFromDate()); 
			Date  endDate = (Date)formatter.parse(details.getToDate());
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			
			for(int i=0;i<empList.size();i++){
			for(int j=0;j<dates.size();j++){
			    Date lDate =(Date)dates.get(j);
			    String date = formatter.format(lDate);    
			    System.out.println(" Date is ..." + date);
			    System.out.println(" employeeId  is ..." + empList.get(i));
			   // employeeRetrievelist=  DailyReportWorking(details,details.getEmployeeId(),date);
			    
			  employeeRetrievelist.addAll(DailyReportWorking(details,empList.get(i),date));
			    
		}
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
	
	public static ArrayList<EmployeeReportJSON> EmployeeOrganizationPeriodReportSummary(EmployeeMaintenanceJSON details) throws ParseException
	{
		ArrayList<EmployeeReportJSON> employeeIdlist = new ArrayList<EmployeeReportJSON>();
		ArrayList<EmployeeReportJSON> employeeRetrievelist = new ArrayList<EmployeeReportJSON>();
		String hour;
		List<String> empList =new ArrayList<String>();
		List<String> holidayList =new ArrayList<String>();
		String tableName=details.getCompanyId()+"HolidayTable";
		int summaryAbsentCount=0;
		Connection connection=null;
		try {
			connection =DBUtil.getDBConnection();
			
			
			System.out.println("Getting EmployeeList \n");
			String querySelect=NewQueryConstants.EMP_REPORTLIST;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1,details.getCompanyId());
			ResultSet rs=preparedStmt.executeQuery();
	        while(rs.next())
	        {
	        	empList.add(rs.getString("EmployeeId"));
	        }
			
	        List<Date> dates = new ArrayList<Date>();

			DateFormat formatter ; 

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date  startDate = (Date)formatter.parse(details.getFromDate()); 
			Date  endDate = (Date)formatter.parse(details.getToDate());
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			
			System.out.println("----------PERIOD ORGANIZATION SUMMARY --------- \n");
	        
	        
			for(int i=0;i<empList.size();i++) {
				summaryAbsentCount=0;
				//employeeRetrievelist.addAll(EmployeeIndividualPeriodReportSummary(details,empList.get(i)));
				EmployeeReportJSON employeeRetrieveobj=new EmployeeReportJSON();
				 String querySelect11=IQueryConstants.EMP_NAME_SELECT;
					PreparedStatement preparedStmt11 = connection.prepareStatement(querySelect11);
					preparedStmt11.setString(1,empList.get(i));
					preparedStmt11.setString(2,details.getCompanyId());

					ResultSet rs11=preparedStmt11.executeQuery();
			        while(rs11.next())
			        {
			        	employeeRetrieveobj.setEmployeeName(rs11.getString("Name"));
			        	employeeRetrieveobj.setEmployeeId(empList.get(i));
						 String querySelect2=IQueryConstants.EMP_PERIODREPORTPRESENTCOUNT;
							PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
							preparedStmt2.setString(1,empList.get(i));
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
									preparedStmt21.setString(1,empList.get(i));
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
												preparedStmt31.setString(1,empList.get(i));
												preparedStmt31.setString(2,details.getFromDate());
												preparedStmt31.setString(3,details.getToDate());
												preparedStmt31.setString(4,details.getCompanyId());
												ResultSet rs31=preparedStmt31.executeQuery();
										        while(rs31.next())
										        {
										        	employeeRetrieveobj.setTotalWorkHour(rs31.getString("totalworkHour"));
							        	 }
							        	
							        	 }
					    
					       String querySelect3=NewQueryConstants.EMP_PERIODREPORTLEAVECOUNT;
						PreparedStatement preparedStmt3= connection.prepareStatement(querySelect3);
						preparedStmt3.setString(1,empList.get(i));
						preparedStmt3.setString(2,details.getFromDate());
						preparedStmt3.setString(3,details.getToDate());
						preparedStmt3.setString(4,details.getCompanyId());
						ResultSet rs3=preparedStmt3.executeQuery();
				        while(rs3.next())
				        {
				        	employeeRetrieveobj.setNoOfDaysLeave(rs3.getInt("LeaveDays"));
				        	
				        
				        }	
					        }
				        }
				        }
			        
			        
			        //GETTING ABSENT COUNT
			      
			        for(int j=0;j<dates.size();j++){
			        	  String empidPeriod=null;
					    Date lDate =(Date)dates.get(j);
					    String date = formatter.format(lDate);

					       String querySelect4=NewQueryConstants.EMP_ID;
						PreparedStatement preparedStmt4= connection.prepareStatement(querySelect4);
						preparedStmt4.setString(1,empList.get(i));
						preparedStmt4.setString(2,details.getCompanyId());
						preparedStmt4.setString(3,date);
						
						ResultSet rs4=preparedStmt4.executeQuery();
				        while(rs4.next())
				        {
				        	empidPeriod=rs4.getString("EmployeeId");
					    
			        }
				        System.out.println("EMPLOYEEID from Employee table:\t "+empList.get(i));
					       
				        System.out.println("EMPLOYEEID from Attendance table:\t "+empidPeriod);
				        System.out.println("DATE :\t "+date);
				        if(empidPeriod==null) {
				        	System.out.println("null value \n");
				        	
				        	String querySelect1=NewQueryConstants.EMP_DAILYREPORTSHIFT;
							PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
							preparedStmt1.setString(1,empList.get(i));
							preparedStmt1.setString(2,details.getCompanyId());
							ResultSet rs1=preparedStmt1.executeQuery();
							String shift = null;
							while(rs1.next()) {
								
					        	/*employeeId=rs1.getString("EmployeeId");
					        	name=rs1.getString("Name");
					        	type=rs1.getString("Type");
					        	department=rs1.getString("Department");*/
					        	shift=rs1.getString("Shift");
						           
								
							}
							System.out.println("EmployeeId Shift"+shift);
				        	
				        	System.out.println("searching in Holiday Table \n");
				        	
				        	String querySelect2=NewQueryConstants.EMP_DAILYREPORTHOLIDAY.replace("$tableName", tableName);
							PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
							preparedStmt2.setString(1,date);
							ResultSet rs2=preparedStmt2.executeQuery();
							String holiday = null;
							while(rs2.next()) {
								holiday=rs2.getString("HoliDayShift" );
								System.out.println("test \n"+holiday);
							}
							//holidayList=holiday.split(",");
							System.out.println("test \n"+holiday);
							System.out.println("Employeeid \n"+empList.get(i));
							
							if(holiday == null) {
								System.out.println("test inside if \n");
							  	System.out.println("Employee is Absent since no  holiday shift is declared \n");
								//status="A";
								summaryAbsentCount++;
								employeeRetrieveobj.setNoOfDaysAbsent(summaryAbsentCount);
								     
								
							}else {
								System.out.println("test inside else \n");
								holidayList=Arrays.asList((holiday.split(",")));
								System.out.println("test \n");
								for(int y=0;y<holidayList.size();y++) {
									System.out.println("Holiday Shift \n"+holidayList.get(y));
									if(holidayList.get(y).equals(shift)) {
										//status="H";
									System.out.println("Holiday declared \n");
									}else {
										//status="A";
										summaryAbsentCount++;
										employeeRetrieveobj.setNoOfDaysAbsent(summaryAbsentCount);
										System.out.println("Holiday not declared for the employee shift \n");
									}
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


}
	



