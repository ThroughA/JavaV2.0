package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeLoginJSON;
import com.fillingstation.util.DBUtil;


public class SiteRegisterLogic {

	/*
	 * FUNCTION FOR REGISTERING THE NEW SITE
	 */
	public static EmployeeLoginJSON CreateSite(EmployeeLoginJSON org) {
		 Connection connection=null;
		  int check;
		  int otp = 0;
		  
		  EmployeeLoginJSON org1=new EmployeeLoginJSON();
		  org1.setEmployeeId(null);
		 try {
				connection =com.fillingstation.util.DBUtil.getDBConnection();
				
				System.out.println("CALLING FUNCTION FOR CHECKING WHETHER THE ORGANIZATION WITH SAME NAME ALREADY EXIST \n");
				org1=CheckOrganizationAlreadyExist(org);
				System.out.println("return");
                 if(org1.getEmployeeId()==null) {
                	 System.out.println("GENERATING  OTP FOR THE NEW ORGANIZATION \n ");
                	 org1.setOtp(GenerateOTP());
                	 System.out.println("OTP FOR THE NEW ORGANIZATION " +org1.getOtp());
                 	 
                 }
				 
		 }
		  	catch (Exception e) {
		  		e.printStackTrace();
		  	} finally {
		  		DBUtil.closeConnection(connection);
		  	}
			 
		return org1;
		
	}

	/*
	 * FUNCTION FOR CHECKING WHETHER SITE WITH 
	 * SAME NAME(EMAILID & MOBILR NO) ALREADY EXIST
	 */
	private static EmployeeLoginJSON CheckOrganizationAlreadyExist(EmployeeLoginJSON org) {
		 Connection connection=null;
		 int check=0;
		 String emailId = null;
		 String mobileNo = null;
		 String tableName=null;
		
		  
		 try {
				connection =DBUtil.getDBConnection();
			
				System.out.println("CHECKING WHETHER THE ORGANIZATION WITH SAME NAME ALREADY EXIST \n");
				String querySelectEMAIL=IQueryConstants.SITE_ALREADYEXIST_EMAILID;
				PreparedStatement preparedStmtEMAIL = connection.prepareStatement(querySelectEMAIL);
				preparedStmtEMAIL.setString(1,org.getEmailId());
				ResultSet rsEMAIL=preparedStmtEMAIL.executeQuery();
				
				while(rsEMAIL.next()) {
					emailId=rsEMAIL.getString("EmailId");
					
				}
				System.out.println("EMAILID : \t"+emailId);
				
				String querySelectMOB=IQueryConstants.SITE_ALREADYEXIST_MOBILENO;
				PreparedStatement querySelectMOBp = connection.prepareStatement(querySelectMOB);
				querySelectMOBp.setLong(1,org.getMobileNo());
				ResultSet rsMOB=querySelectMOBp.executeQuery();
				
				while(rsMOB.next()) {
					mobileNo=rsMOB.getString("MobileNo");
					
				}
					
				
				System.out.println("MOBILENO : \t"+mobileNo);
			
				if(emailId==null && mobileNo==null ) {
					org.setEmployeeId(null);
					//both new
					System.out.println("Both New");
					
				}else if(mobileNo!=null) {
					org.setEmployeeId("MOBILE");
					System.out.println("Mobile Exits");
					
				}else {
					org.setEmployeeId("EMAILID");
					System.out.println("emailId Exits");
				}
				
				
				
		 }
		  	catch (Exception e) {
		  		e.printStackTrace();
		  	} finally {
		  		DBUtil.closeConnection(connection);
		  	}
			 return org;	
	}

	/*
	 * FUNCTION FOR GENERATING OTP IF A NEW ORGANIZATIION IS REGISTERING
	 */
	public static int GenerateOTP() {
		
		Random rnd = new Random();
		int OTP= 100000 + rnd.nextInt(900000);
		System.out.println("generated OTP successfully \n");
		return OTP;
	}

	
	/*
	 * FUNCTION FOR INSERTING THE SITE DETAILS
	 * INTO COMPANY AND EMPLOYEE TABLE ON SUCCESSFUL OTP VERIFICATION
	 */

	public static EmployeeLoginJSON InsertSite(EmployeeLoginJSON org) {
		
		Connection connection=null;
		String companyId = null;
		String tableName=null;
		String employeeId=null;
		 try {
				connection =DBUtil.getDBConnection();
				
				System.out.println("INSERTING THE SITE DETAILS INTO COMPANYTABLE TABLE \n");
				String querySelectINS=IQueryConstants.SITE_INSERT_COMPANY;
				PreparedStatement preparedStmtINS = connection.prepareStatement(querySelectINS);
				preparedStmtINS.setString(1,org.getSiteName());
				preparedStmtINS.setString(2,org.getAddress());
				preparedStmtINS.setString(3,org.getCity());
				preparedStmtINS.setString(4,org.getPinCode());
				preparedStmtINS.setString(5,org.getState());
				preparedStmtINS.setString(6,org.getCountry());
				preparedStmtINS.setLong(7,org.getMobileNo());
				preparedStmtINS.setString(8,org.getEmailId());
				
				preparedStmtINS.executeUpdate();
				
				org.setSiteName("INSERTED");
				
				System.out.println("SELECTING THE SITE ID FOR NEWLY REGISTERED SITE \n");
				String querySelectSELORGID=IQueryConstants.SITE_SELECTID;
				PreparedStatement preparedStmtSELORGID = connection.prepareStatement(querySelectSELORGID);
				preparedStmtSELORGID.setString(1,org.getEmailId());
				preparedStmtSELORGID.setLong(2,org.getMobileNo());
				ResultSet rsSELORGID=preparedStmtSELORGID.executeQuery();
				while(rsSELORGID.next()) {
					companyId=rsSELORGID.getString("CompanyId");
				}
				
				System.out.println("CALLING INSERT FUNCTION TO INSERT DATA INTO EMPLLOYEE TABLE \n");
				InsertIntoEmployeeTable(org,companyId);
				
		 }
		 catch (Exception e) {
		  		e.printStackTrace();
		  	} finally {
		  		DBUtil.closeConnection(connection);
		  	}
	
	return org;
	}

	/*
	 * FUNCTION FOR INSERTING DATA INTO LOGINTABLE ON 
	 * ORGANIZATION REGISTRATION AND SITE REGISTRATION
	 */
	private static void InsertIntoEmployeeTable(EmployeeLoginJSON org, String companyId) {
		
		Connection connection=null;
        String tableName=null;
        int employeeNo=0;
		 try {
				connection =DBUtil.getDBConnection();
				
				String querySelect=IQueryConstants.EMP_INSERT_SELECT;
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				preparedStmt.setString(1,org.getCompanyId());
				ResultSet rs=preparedStmt.executeQuery();
				while(rs.next()) {
					employeeNo=rs.getInt("EmployeeId");
				}
				System.out.println("got the employee details to be added............"+employeeNo);
				/*System.out.println("reportingmanagerid \t"+details.getReportingManagerId());
				System.out.println("reportingmanagerRole \t"+details.getReportingManagerRole());
			*/
				String employeeId;
				if(employeeNo==0) {
					int employeeId1=000+1;
					employeeId = String.format("%03d", employeeId1);
				}else {
				int employeeId1=employeeNo+1;
				employeeId = String.format("%03d", employeeId1);
				}
		
		System.out.println("INSERTING THE SITE DETAILS INTO EMPLOYEE TABLE \n"+employeeId);
		String address=org.getAddress()+","+org.getCity()+","+org.getState()+","+org.getCountry()+","+org.getPinCode();
		String querySelectINSLOG=IQueryConstants.SITE_INSERTEMPLOYEE;
		PreparedStatement preparedStmtINSLOG = connection.prepareStatement(querySelectINSLOG);
		preparedStmtINSLOG.setString(1,companyId);
		preparedStmtINSLOG.setString(2,employeeId);
		preparedStmtINSLOG.setString(3,org.getFirstName());
		preparedStmtINSLOG.setString(4,org.getLastName());
		preparedStmtINSLOG.setString(5,org.getDob());
		preparedStmtINSLOG.setString(6,org.getEmailId());
		preparedStmtINSLOG.setLong(7,org.getMobileNo());
		preparedStmtINSLOG.setString(8,address);
		preparedStmtINSLOG.executeUpdate();
		
		String config=IQueryConstants.SITE_INSERTEMPLOYEE_CONFIG;
		PreparedStatement preparedStmtconfig = connection.prepareStatement(config);
		preparedStmtconfig.setString(1,companyId);
	
		preparedStmtconfig.executeUpdate();
		
		//Insert Default First Shift
		String Shift=IQueryConstants.SITE_SHIFT_INSERT;
		PreparedStatement ShiftSt = connection.prepareStatement(Shift);
		ShiftSt.setString(1,companyId);
	
		ShiftSt.executeUpdate();
		System.out.println("CALLING FUNCTIONTO CREATE TABLE \n");
		CreateTable(org,companyId);
		
		 }
		 catch (Exception e) {
		  		e.printStackTrace();
		  	} finally {
		  		DBUtil.closeConnection(connection);
		  	}
	
	}
	
	
	/*
	 * FUNCTION TO CREATE TABLE OF HOLIDAY AND LEAVE 
	 */

	private static void CreateTable(EmployeeLoginJSON org, String companyId) {
		Connection connection=null;
        int employeeNo=0;
        String tableName=companyId+"HolidayTable";
        String tableName1=companyId+"LeaveTable";
        
		 try {
				connection =DBUtil.getDBConnection();
				System.out.println("CREATING HOLIDAY TABLE \n");
				String querySelect=IQueryConstants.CREATE_TABLE_HOLIDAY.replace("$tableName",tableName);
				PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
				preparedStmt.executeUpdate();
		
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				   LocalDateTime now = LocalDateTime.now();
				   String date=dtf.format(now);
				   System.out.println(date);
				
				System.out.println("INSERTING INTO HOLIDAY TABLE \n");
				String querySelect1=IQueryConstants.INSERT_HOLIDAYTABLE.replace("$tableName",tableName);
				PreparedStatement preparedStmt1 = connection.prepareStatement(querySelect1);
				preparedStmt1.setString(1,date);
				preparedStmt1.executeUpdate();
				
				System.out.println("UPDATING INTO HOLIDAY TABLE \n");
				String querySelect2=IQueryConstants.UPDATE_HOLIDAYTABLE.replace("$tableName",tableName);
				PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
				preparedStmt2.executeUpdate();
				
				System.out.println("CREATING  LEAVE TABLE \n");
				String querySelect3=IQueryConstants.CREATE_LEAVETABLE.replace("$tableName",tableName1);
				PreparedStatement preparedStmt3 = connection.prepareStatement(querySelect3);
				preparedStmt3.executeUpdate();
				
				

		 }
		 catch (Exception e) {
		  		e.printStackTrace();
		  	} finally {
		  		DBUtil.closeConnection(connection);
		  	}
	
	
	
}
	
}
