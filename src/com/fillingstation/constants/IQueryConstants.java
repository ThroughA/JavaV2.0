package com.fillingstation.constants;

public interface IQueryConstants {



	String LOGIN_VERIFY ="SELECT  EmployeeId, ET.CompanyId, FirstName, LastName ,Password , Block ,  LockValue ,ET.Role ,ET.Department, EC.Role,EC.Permission  FROM EmployeeTable ET inner join EmployeeConfig EC on EC.Role = ET.Role  Where EmailId= ? or MobileNo = ? and Status = 0";
	
	String COMPANY_NAME="Select CompanyName ,BiometricValue,ShiftMode from CompanyTable where CompanyId = ? ";
	
	String EMP_SELECTION="SELECT Role FROM EmployeeTable WHERE EmployeeId = ? ";

    String ROLE_PERMISSION="SELECT Permission FROM EmployeeConfig where Role = ? And CompanyId = ? ";

    String EMP_GET_DEPT="SELECT DISTINCT Department FROM EmployeeConfig WHERE CompanyId = ? And Department is not null";

    String EMP_GET_ROLE="SELECT DISTINCT Role FROM EmployeeConfig WHERE CompanyId = ? And Role <> 'proprietor'";

    String EMP_LIST="SELECT DISTINCT EmployeeId FROM EmployeeTable where status = 0 And CompanyId = ? And Role <> 'proprietor' "; 
    
    
    String EMP_LOCK_LIST="SELECT DISTINCT EmployeeId FROM EmployeeTable WHERE status = 0 And CompanyId = ?  And LockValue >=3";
    
    String REPORTING_MANAGER_LIST="SELECT Role  FROM EmployeeConfig WHERE CompanyId = ?  And permission LIKE '%supervisorAuthority%'";
    
    
    
    String EMP_VALIDATION="SELECT EmployeeId FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? ";
	
    String EMP_RESET_LOCK="UPDATE EmployeeTable SET LockValue = 0 WHERE EmployeeId = ? And CompanyId = ? ";
	
    String EMP_SELECT_LOCK = "SELECT LockValue FROM EmployeeTable WHERE EmployeeId = ? And CompanyId = ? ";

    String EMP_LOCK =" UPDATE EmployeeTable SET LockValue = ? WHERE EmployeeId = ? And CompanyId = ? ";

    
	
    String EMP_BLOCK="UPDATE EmployeeTable SET Block = 1  WHERE EmployeeId = ? And CompanyId = ?   ";

      String EMP_UNBLOCK="UPDATE EmployeeTable SET Block = 0 WHERE EmployeeId = ? And CompanyId = ? ";

	String EMPLOYEE_ATTENDANCE="SELECT  EA.EmployeeId ,EA.Name,EA.CheckInTime,EA.CheckOutTime,EA.Status,E.MobileNo FROM EmployeeAttendanceTable EA INNER JOIN EmployeeTable E Where EA.EmployeeId=E.EmployeeId AND EA.CompanyId=E.CompanyId AND date(Date) = ? AND E.CompanyId = ?  AND EA.RemoveStatus=0 ";
	
	
	
	
	String VALID_MAILID="SELECT FirstName FROM EmployeeTable Where EmailId = ? or MobileNo = ?";
	
		
	String EMP_INSERT="INSERT INTO EmployeeTable(FirstName,LastName,DOB,EmailId,MobileNo,"
						+ "Address,Type,Role,Department,EmployeeProofType,"
						+ "EmployeeProofNum,CompanyId,EmployeeId,ReportingManagerId,ReportingManagerRole ,FingerPrint1 )VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,? , ?) ";
	
	String EMP_ID= "SELECT EmployeeId FROM EmployeeTable where EmployeeProofNum= ?";
	
	String EMP_SEARCH="SELECT EmployeeId, FirstName, LastName, MobileNo, Address, Type, Role, Department FROM EmployeeTable WHERE (EmployeeId = ? or Type = ? or Department = ? or MobileNo = ? or Role = ? or FirstName = ? or LastName = ? ) AND CompanyId = ? ";
 
	String EMP_DETAILS= "SELECT * FROM EmployeeTable where EmployeeId= ? AND CompanyId = ? ";
	
	
	String EMP_DELETE="UPDATE EmployeeTable SET Status = ? WHERE EmployeeId = ? AND CompanyId = ?";

	
	
	String EMP_UPDATE="UPDATE EmployeeTable SET EmployeeId = ? ,FirstName = ?,"
						+ "LastName = ?,DOB = ?,EmailId = ?,MobileNo = ? ,"
						+ "Address = ?,Type = ?,Role = ?,Department = ?,"
						+ "EmployeeProofType = ?,EmployeeProofNum =? ,ReportingManagerId = ? , ReportingManagerRole= ? "
						+ " WHERE EmployeeId = ? AND CompanyId  = ? ";
	
	
    
	
	String CHECKED_IN_TODAY="SELECT EmployeeId FROM EmployeeAttendanceTable Where EmployeeId = ? AND Date = ? ";
	
	String EMP_CHECKIN="UPDATE EmployeeAttendanceTable SET CheckinTime = ? , Status = 'P' WHERE EmployeeId = ? AND date(Date) = ? AND CompanyId = ? ";	
	
	
	

    String EMP_PAUSETIME_SELECT = "SELECT PauseTime FROM EmployeeAttendanceTable WHERE CompanyId = ? ";

	
	String EMP_PAUSETIME_SELECTTIME="SELECT SELECT PauseTime FROM EmployeeAttendanceTable WHERE CompanyId = ?  " ;

	
	
	
	
	
	
	

	String EMP_ID_SELECT="SELECT DISTINCT EmployeeId FROM EmployeeAttendanceTable WHERE date(Date) BETWEEN ? AND ? AND CompanyId = ? ";
	
	String EMP_NAME_SELECT="SELECT CONCAT(FirstName,' ',LastName) AS Name FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? " ;
	
	String EMP_MAINTENANCE_REPORT="SELECT EmployeeId,FirstName,LastName,Type,Department,Role,MobileNo from EmployeeTable  where  CompanyId  = ? And Status=0  ";
	
	String EMP_MAINTENANCE_COUNT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 And AvoidAttendance=0)as PermanentEmployeeCount,"
			+ "(SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 And AvoidAttendance=0 )as TemporaryEmployeeCount,"
			+ "COUNT(EmployeeId) as ContractEmployeeCount FROM EmployeeTable WHERE Type = ? AND CompanyId = ? And Status=0 And AvoidAttendance=0";
	
	
	String EMP_MAINTENANCE_COUNT_REPORT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 )as PermanentEmployeeCount,"
			+ "(SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 )as TemporaryEmployeeCount,"
			+ "COUNT(EmployeeId) as ContractEmployeeCount FROM EmployeeTable WHERE Type = ? AND CompanyId = ? And Status=0 ";
	
	
	String EMP_DAILYREPORT="SELECT EA.EmployeeId,EA.Name,EA.CheckinTime,EA.CheckoutTime,EA.TotalWorkHour,EA.Status,"
			+ "E.Type,E.Department ,AuthorizedBy FROM EmployeeTable E INNER JOIN EmployeeAttendanceTable EA ON EA.EmployeeId=E.EmployeeId AND EA.CompanyId=E.CompanyId WHERE date(Date) = ? AND EA.CompanyId = ? And RemoveStatus=0 ";

	
	String EMP_DAILYREPORTPRESENTCOUNT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? AND RemoveStatus=0 )as PermanentEmployeePresentCount," 
			+ "(SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? AND RemoveStatus=0  )as TemporaryEmployeePresentCount,"
			+ "COUNT(EmployeeId) as ContractEmployeePresentCount FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? AND RemoveStatus=0 ";

	
	String EMP_DAILYREPORTABSENTCOUNT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'A' AND CompanyId = ? AND RemoveStatus=0 )as PermanentEmployeeAbsentCount," 
			+ "(SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'A' AND CompanyId = ? AND RemoveStatus=0 )as TemporaryEmployeeAbsentCount,"
			+ "COUNT(EmployeeId) as ContractEmployeeAbsentCount FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'A' AND CompanyId = ?  AND RemoveStatus=0  ";
	
	String EMP_DAILYREPORTCOUNT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND CompanyId = ? )as PermanentEmployeeCount," 
			+ "(SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND CompanyId = ? )as TemporaryEmployeeCount,"
			+ "COUNT(EmployeeId) as ContractEmployeeCount FROM EmployeeAttendanceTable WHERE Type = ? AND Date = ? ";

	
	
	
	
	String EMP_MONTHLYID_SELECT="SELECT DISTINCT EmployeeId FROM EmployeeAttendanceTable WHERE month(Date) = ? AND year(Date) =  ? AND CompanyId = ? ";
	
	
	String EMP_MONTHLYREPORT="SELECT EA.EmployeeId,EA.Name,EA.CheckinTime,EA.CheckoutTime,date(Date) as Date,EA.TotalWorkHour,EA.Status,"  
       		+"E.Type,E.Department FROM EmployeeTable E INNER JOIN EmployeeAttendanceTable EA ON EA.EmployeeId=E.EmployeeId AND EA.CompanyId=E.CompanyId WHERE month(Date) = ? AND year(Date) = ? AND EA.CompanyId =? " ;


	String EMP_MONTHLYREPORTPRESENTCOUNT="SELECT COUNT(EmployeeId) AS PresentDays  FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND month(Date) = ? And year(Date) = ? AND Status = 'P' AND CompanyId =? ";

	String EMP_MONTHLYREPORTABSENTCOUNT="SELECT COUNT(EmployeeId) AS AbsentDays FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND month(Date) = ? And year(Date) = ? AND Status = 'A' AND CompanyId =? ";

	
	String EMP_SELECT_TOTALWORKHOUR="SELECT TotalWorkhour from EmployeeAttendanceTable where employeeid = ? and month(Date) = ? And year(Date) = ? and companyid = ? ";
	
	String EMP_MONTHLY_TOTALWORKHOUR="select CAST((sec_to_time(sum(time_to_sec(totalworkhour)) )) as char) As TotalWorkhour from EmployeeAttendanceTable where employeeid= ? and month(Date) = ? And year(Date) = ? and CompanyId = ?  ";
    
	  
	
	
	
	
	String EMP_PERIODREPORT="SELECT EA.EmployeeId,EA.Name,EA.CheckinTime,EA.CheckoutTime,date(Date) as Date,EA.Status,EA.TotalWorkHour,EA.Status,"
       		+"E.Type,E.Department FROM EmployeeTable E INNER JOIN EmployeeAttendanceTable EA ON EA.EmployeeId=E.EmployeeId AND EA.CompanyId=E.CompanyId WHERE date(Date) BETWEEN ? AND ? AND EA.CompanyId = ? ";


	String EMP_PERIODREPORTPRESENTCOUNT="SELECT COUNT(EmployeeId) AS PresentDays  FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) between ? and ? AND Status = 'P' AND CompanyId =? ";

	String EMP_PERIODREPORTABSENTCOUNT="SELECT COUNT(EmployeeId)AS AbsentDays FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) BETWEEN ? AND ? AND Status = 'A' AND CompanyId = ? ";
	
	String EMP_SELECT_PERIODTOTALWORKHOUR="SELECT TotalWorkhour from EmployeeAttendanceTable where employeeid = ? and date(Date) between ? And  ? and companyid = ? ";

	String EMP_PERIOD_TOTALWORKHOUR="select concat('',sec_to_time(sum(time_to_sec(totalworkhour)))) AS TotalWorkHour  from EmployeeAttendanceTable where employeeid= ? and date(Date) between ? and ? and CompanyId =? ";
      
	
	
	//Audit Report

	String EMP_SELECT_AUDIT_REPORT="SELECT SuperiorId,Name,Role,Operation,EmployeeId,date(date) as date,time(date) as time from AuditReport where CompanyId = ?";
	
	String EMP_AUDIT_SEARCH="SELECT SuperiorId,Name,Role,Operation,EmployeeId,date(date) as date,time(date) as time from AuditReport where  (SuperiorId = ? or Name = ? or Role = ? or Operation = ? or EmployeeId = ? or date = ?) AND CompanyId = ?";
	
	String EMP_AUDIT_ROLE_NAME = "SELECT CONCAT(FirstName,' ',LastName) AS Name , Role from EmployeeTable WHERE EmployeeId = ? AND CompanyId = ?  ";
			
	//String SELECTEMPNAME = "SELECT CONCAT(FirstName,' ',LastName) AS Name FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? ";
	
	String EMP_AUDIT_REPORT="INSERT INTO AuditReport(SuperiorId,Role,EmployeeId,Operation,CompanyId,Name)VALUES(?,?,?,?,?,?)";
	
	
	
		
		
	String EMP_REQUEST= "select ET.EmployeeId,FirstName,LastName, CheckInTime, CheckOutTime, FromDate from EmployeeRequestTable  ER inner join EmployeeTable ET on ET.EmployeeId=ER.EmployeeId and ET.CompanyId=ER.CompanyId WHERE ER.ReportingManagerId = ? And ER.CompanyId = ? And RequestStatus =0 ";
	
	/*String EMP_REQUEST_REJECT= "Delete from EmployeeRequestTable WHERE CheckInTime = ? And " + " CheckOutTime = ? "
									+ " And date(FromDate) = ? And CompanyId = ? AND EmployeeId = ? And ReportingManagerId = ? ";
	
	*/ String EMP_REGULATION="UPDATE EmployeeAttendanceTable SET CheckinTime = ? ,"
	    		+ "CheckoutTime = ?  WHERE EmployeeId = ? AND date(Date) = ? And CompanyId = ?  ";
	 
	 
	 String ATTENDANCE_REQUEST_REJECT_STATUS= "Update EmployeeRequestTable set RequestStatus= 2 where CheckInTime = ? And " + 
			 				                   "CheckOutTime = ?  AND date(FromDate) = ? And CompanyId = ? AND EmployeeId = ?  And ReportingManagerId = ?";
	 
	 String ATTENDANCE_REQUEST_ACCEPT_STATUS="Update EmployeeRequestTable set RequestStatus= 1 where CheckInTime = ? And " +
	 			 			    		    "CheckOutTime = ?  AND date(FromDate) = ? And CompanyId = ? AND EmployeeId = ?  And ReportingManagerId = ?";
	 
	 
	 
	 
	 String EMP_DEFAULT_ATTENDANCE="UPDATE EmployeeAttendanceTable SET Status = 'A' WHERE CheckoutTime IS NULL AND date(Date) = ? ";

	String EMP_GET_AUTHORIZATIONDETAILS="SELECT Authorization FROM EmployeeAttendanceTable WHERE Date = ? ";

	String AUTHORIZER_NAME= "select FirstName, LastName from EmployeeTable WHERE EmployeeId = ? And CompanyId = ? ";
	
	String SUP_AUTHORIZATION="UPDATE EmployeeAttendanceTable SET Authorization = ? ,AuthorizedBy = ? WHERE date(Date) = ? And CompanyId = ? ";


	
	
	
	String EMP_ADD_DEPT="INSERT INTO EmployeeConfig (Department,CompanyId) VALUES(?,?) ";
	    
	String EMP_SELECT_DEPT="SELECT Department from EmployeeConfig WHERE Department = ? And CompanyId = ? ";
	    
	String EMP_SELECT_ROLE="SELECT Role FROM EmployeeConfig WHERE Role = ? And CompanyId = ? ";
	    
	//String  EMP_ADD_ROLE="UPDATE EmployeeConfig SET Role = ? WHERE Role is NULL ";
	    
	String EMP_ADD_ROLE1="INSERT INTO EmployeeConfig(Role,CompanyId)VALUES(?,?) ";
	    
	String EMP_SET_PERMISSION="UPDATE EmployeeConfig SET Permission =? WHERE Role = ? And CompanyId = ? ";
	
	String EMP__AVOID_ATTENDANCE="UPDATE EmployeeTable SET AvoidAttendance = ? WHERE Role = ? And CompanyId = ? ";


	String REPORTING_MANAGERROLE="SELECT distinct ReportingManagerRole FROM EmployeeTable Where CompanyId = ? ";


	

	String EMP_DELETE_DEPT="DELETE  FROM EmployeeConfig WHERE Department = ? And CompanyId = ? ";
    
    String EMP_DELETE_ROLE="DELETE FROM EmployeeConfig WHERE Role = ? And CompanyId = ? ";

  
    
    String EMP_VERIFY_MAIL = "SELECT EmailId FROM EmployeeTable where CompanyId = ?  And  EmailId  = ? " ; 
	
    String EMP_VERIFY_MOBILENO= "SELECT  MobileNo FROM EmployeeTable where CompanyId = ?  And MobileNo = ? " ; 
	
	String EMP_INSERT_SELECT = "SELECT MAX(EmployeeId) as EmployeeId FROM EmployeeTable where CompanyId = ?  " ; 
	
	
	
	
	String EMP_DEPT_SELECT = "SELECT EmployeeId FROM EmployeeTable WHERE Department = ? And Status = 0  And CompanyId = ? " ;

	String EMP_ROLE_SELECT = "SELECT EmployeeId FROM EmployeeTable WHERE Role = ? AND  Status = 0 And CompanyId = ? " ;

	
	
	
	
	String STORE_OTP="UPDATE EmployeeTable set Otp = ? where EmailId= ? or MobileNo = ? ";
	
	String OTP_VERIFY="SELECT Otp FROM   EmployeeTable where EmailId= ?";
    
	String UPADTE_NEW_PASSWORD="UPDATE EmployeeTable set Password = ? where EmailId= ?";

	
	
	
	String REPORTING_MANAGER_ID = "SELECT ReportingManagerId FROM EmployeeTable Where EmployeeId = ?  And CompanyId = ?";
	
	String REQUEST_ALREADY_EXITS="Select  CheckInTime from EmployeeRequestTable where  date(FromDate) = ? And CompanyId = ? AND EmployeeId = ?  And RequestStatus = 0 ";
	
	
	String ATTENDANCE_REG_INSERT="INSERT INTO EmployeeRequestTable (CompanyId,EmployeeId,ReportingManagerId,CheckInTime,CheckOutTime,FromDate) VALUES(?,?,?,?,?,?) ";

	String REPORTING_MANAGER_EMAILID = " SELECT EmailId FROM EmployeeTable Where  CompanyId = ?  And EmployeeId = ?  ";
    
	String REP_EMP_LIST="SELECT DISTINCT EmployeeId FROM EmployeeTable where status = 0 And CompanyId = ? And ReportingManagerId = ? ";

	
	
	
	String GET_EMPLEAVEDAYS = "SELECT $columnName FROM $tableName WHERE EmployeeId = ?  ";
	
	String GET_LEAVETYPE = "SELECT LeaveType FROM EmployeeConfig WHERE LeaveStatus = 0 AND CompanyId = ? AND LeaveType <> 'NULL' ";

	String GET_EMPLEAVETYPE = "SELECT $columnName FROM $tableName WHERE ($columnName <> 0.0 OR $columnName <> 0) AND EmployeeId = ? ";

	
	String EMPLEAVECHANGE = "UPDATE $tableName SET  $columnName = ? WHERE EmployeeId = ? " ;

	String LEAVEINFO = "INSERT INTO EmployeeRequestTable(Name,EmployeeId,CompanyId,Days,Date,FromDate,ToDate,Subject,ReportingManagerId,LeaveType)VALUES(?,?,?,?,?,?,?,?,?,?)";

	String SELECTMAILID = "SELECT EmailId ,ReportingManagerId FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? " ;
	
	String SELECTEMPNAME = "SELECT CONCAT(FirstName,' ',LastName) AS Name FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? ";
	
	String SELECTLEAVEDATA = "SELECT * FROM EmployeeRequestTable WHERE ReportingManagerId =  ? AND CompanyId = ? AND RequestStatus = 0 ";
	
	String EMPLEAVEAUTHORIZE = "UPDATE EmployeeRequestTable SET RequestStatus = 1 WHERE EmployeeId = ? AND CompanyId = ? AND Days = ? AND FromDate = ? AND ToDate = ? AND Subject = ? And ReportingManagerId = ? "  ;

	String EMPLEAVENOTAUTHORIZE = "UPDATE EmployeeRequestTable SET RequestStatus = 2 WHERE EmployeeId = ? AND CompanyId = ? AND Days = ? AND FromDate = ? AND ToDate = ? AND Subject = ?  And ReportingManagerId = ? And LeaveType= = ?";

	
	
	//Holiday Queries
	
	String ADD_HOLIDAY="UPDATE $tableName SET holidayDescr = ? , $Shift = 0 where dt = ? " ;

	String UPDATE_HOLIDAY ="UPDATE $tableName SET holidayDescr = ?,$Shift = ? where dt = ? " ;

	String DELETE_HOLIDAY ="UPDATE $tableName SET isHoliday = ? ,holidayDescr = ? where dt = ? " ;

	
	String GET_HOLIDAYDATA = "SELECT dt,holidayDescr, HoliDayShift from $tableName where holidayDescr <> 'NULL' ";

	String ADD_HOLIDAY_CHECK="SELECT holidayDescr FROM $tableName WHERE dt = ? ";
	
	
	String GET_SHIFT_NO = "SELECT MAX(ShiftType) AS TotalShift FROM ShiftConfig WHERE CompanyId = ? " ;

	String GET_HOLIDAYINFO="SELECT $shiftOptions,LeavePerYear From CompanyTable WHERE CompanyId = ?  " ;

	String ADD_HOLIDAYSHIFTS = "UPDATE $tableName SET HolidayShift = ? WHERE dt = ? ";

	


	String UPDATE_OLDDATEHOLIDAY = "UPDATE $tableName SET $Shift = 'NULL', holidayDescr = 'NULL' ,HolidayShift = 'NULLL'  WHERE dt = ?  ";


	
	
	
	String WORKING_HOURS ="SELECT WorkingTime FROM CompanyTable WHERE CompanyId = ? ";
	
	String COMPANY_UPDATE_WORKING_HOURS= "UPDATE CompanyTable SET WorkingTime = ?  WHERE CompanyId  = ? ";
	
	//SHIFT QUERIES
	
	String Get_SHIFT = "SELECT ShiftType FROM ShiftConfig WHERE CompanyId = ? " ;

	
	//String EMP_CHECKIN="INSERT INTO EmployeeAttendanceTable(CompanyId,EmployeeId,Name,Type,CheckInTime,Department,Date,Status)Values(?,?,?,?,?,?,?,'P')";	

	//String VALID_EMP_ID="SELECT EmployeeId FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? AND Status <> 1  ";
//*JEEVA
	
	String VALID_EMP_ID_EMPLOYETABLE="SELECT EmployeeId FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? AND Status <> 1  ";

	String EMP_CHECKIN_INSERT="INSERT INTO EmployeeAttendanceTable(CompanyId,EmployeeId,Name,Type,CheckInTime,Department,Date,Status)Values(?,?,?,?,?,?,?,'P')";	


//SHIFT CONFIG QUERIES

String SHT_CONFIGINSERT = "INSERT INTO ShiftConfig(CompanyId,ShiftType,Start,End)VALUES(?,?,?,?) ";

String SHT_CONFIGDETAILS = "SELECT CompanyId FROM ShiftConfig where CompanyId = ? AND ShiftType = ? AND Status = 0 " ;

String SHT_TIME_DETAILS=" Select CompanyId from ShiftConfig where CompanyId = ? AND Start = ? AND End = ?   ";

String SHT_CONFIGUPDATE = "UPDATE ShiftConfig SET ShiftType = ? ,Start = ? ,End = ? WHERE CompanyId = ? AND ShiftType = ? AND Start = ? AND End = ? " ;

String SHIFT_UPDATE="Update ShiftConfig Set Start = ? , End = ? where CompanyId = ? And ShiftType = ? And Status= 0";

String SHT_CONFIGDATA = "SELECT ShiftType,Start,End FROM ShiftConfig WHERE CompanyId = ? AND Status = 0 " ;

String SHT_CONFIG_INIINSERT = "INSERT INTO ShiftConfig(CompanyId,ShiftType,Start,End) VALUES(?,?,?,?) " ;

String SH_EMP_EXITS="select EmployeeId from EmployeeTable where CompanyId = ? AND Shift = ?"; 

String SHT_CONFIGDELETE = "UPDATE ShiftConfig SET Status = 1 WHERE CompanyId = ? AND ShiftType = ? AND Start = ? AND End = ? ";



// Biometric Query

String STORE_FINGERPRINT ="Update EmployeeTable set FingerPrint1 = ? where EmployeeId = ? And CompanyId = ?";



String GET_EMPLOYEEBIO = "SELECT FingerPrint1 from EmployeeTable Where EmployeeId = ? and companyId =? And Status=0";

String Biometric_Settings = "Update CompanyTable set BiometricValue= ? where companyId = ? ";

String ANDROID_LOGIN = "Select CompanyId ,Password from EmployeeTable where EmailId= ? or MobileNo = ? and Status =0  ";





//Shift Change

String Select_EmpIDTotalShift="select MAX(ShiftType) AS TotalShift from ShiftConfig where companyId= ? ";

String Select_All_Emp="select EmployeeId,CONCAT(FirstName,' ',LastName) AS Name from EmployeeTable where CompanyId = ? And Status=0 And  Role <> 'proprietor'";

String Select_EmpID1="select EmployeeId,CONCAT(FirstName,' ',LastName) AS Name from EmployeeTable where $option1 = ? AND CompanyId = ? And Status=0 ";
   
	String Select_EmpID2 = "select EmployeeId,CONCAT(FirstName,' ',LastName) AS Name from EmployeeTable where $option1 = ? AND $option2 = ? AND CompanyId = ? And Status=0 ";
	

	String Select_EmpID3 = "select EmployeeId,CONCAT(FirstName,' ',LastName) AS Name from EmployeeTable where role = ? AND department = ? AND shift = ? AND CompanyId = ? And Status=0 ";

	String SHIFTMANAGEMENTUPDATE = "UPDATE EmployeeTable SET Shift = ? WHERE EmployeeId = ? AND CompanyId = ? ";

	String SHT_STRICTMODEDATA = "UPDATE  CompanyTable SET ShiftMode = ? ,Hours = ?  WHERE CompanyId = ? " ;


	

String GET_LEAVE = "SELECT LeaveType,Days From EmployeeConfig WHERE CompanyId = ? AND LeaveStatus = 0 AND LeaveType <> 'NULL' AND Days <> 'NULL' ";

String SELECT_LEAVESTATUS = "SELECT LeaveStatus FROM EmployeeConfig WHERE CompanyId = ? AND LeaveType = ? "  ;
	
String UPDATE_LEAVETYPEEMPCONFIG = "UPDATE EmployeeConfig SET LeaveStatus = 0,Days = ? WHERE CompanyId= ? AND LeaveType = ? ";

String UPDATE_LEAVETYPELEAVE = "UPDATE $tableName SET $leaveType = ? ";

String ADD_LEAVETYPE = " ALTER TABLE $tableName ADD $columnName varchar(30)  ";
	
String ADD_LEAVEDAYS = "UPDATE $tableName SET $columnName = ? " ;

String INSERT_LEAVETYPE = "INSERT INTO EmployeeConfig (CompanyId,LeaveType) VALUES(?,?)";

String INSERT_LEAVEDAYS = "UPDATE EmployeeConfig SET Days = ? WHERE CompanyId = ? AND LeaveType = ? ";

String SELECT_LEAVEDAYS = " SELECT Days FROM EmployeeConfig WHERE CompanyId = ? AND LeaveType = ?  " ;

String CHANGE_LEAVETYPE = "ALTER TABLE $tableName CHANGE $columnName $editColumnName int(5)  ";

	String CHANGE_LEAVETYPECONFIG = "UPDATE EmployeeConfig SET LeaveType = ? WHERE CompanyId = ? AND LeaveType = ? " ;

String DELETE_LEAVE = "UPDATE EmployeeConfig SET LeaveStatus = 1 WHERE CompanyId = ? AND LeaveType = ? " ;

String SELECT_EMPID = " SELECT EmployeeId ,$columnName FROM $tableName " ;

String SELECT_NOOFLEAVE = "SELECT $columnName FROM $tableName WHERE EmployeeId = ? " ;

String UPDATE_NOOFLEAVE = "UPDATE $tableName SET $columnName = ? WHERE EmployeeId = ? " ;

String UPDATE_CONFIGNOOFLEAVE="UPDATE $tableName SET Days = ? WHERE CompanyId = ? AND LeaveType = ? " ;
	
	
//Week ENd Query

//String GET_HOLIDAYINFO="SELECT $shiftOptions,LeavePerYear From CompanyTable WHERE CompanyId = ?  " ;
	
String NONEHOLIDAYCONFIG ="update $tableName SET $shiftOptions = 'NULL' WHERE dt >= ? AND dayName = ?  ";

String GETHOLIDAYCONFIGOPTION= "SELECT $shiftOptions FROM CompanyTable WHERE CompanyId = ?  ";
	
String ODDHOLIDAYCONFIGCHANGE ="update $tableName SET  $shiftOptions = 'NULL' WHERE dt >= ? AND W%2 = 1 AND dayName = ? AND holidayDescr IS NULL   ";
	
String EVENHOLIDAYCONFIG = "update $tableName SET $shiftOptions = 'H' WHERE dt >= ? AND W%2 = 0 AND dayName = ? ";

String ODDHOLIDAYCONFIG = "update $tableName SET $shiftOptions = 'H' WHERE dt >= ? AND W%2 = 1 AND dayName = ? ";
	
String ALLHOLIDAYCONFIG = "update $tableName SET $shiftOptions = 'H' WHERE dt >= ? AND dayName = ? ";

String EVENHOLIDAYCONFIGCHANGE = "update $tableName SET $shiftOptions = 'NULL' WHERE dt >= ? AND W%2 = 0 AND dayName = ? AND holidayDescr IS NULL  ";

String ALLHOLIDAYCONFIGCHANGE = "update $tableName SET $shiftOptions = 'NULL' WHERE dt >= ? AND dayName = ? AND holidayDescr IS NULL  ";

String INS_HOLIDAYDATA="UPDATE CompanyTable SET $shiftOptions = ? , LeavePerYear = ? WHERE CompanyId = ? ";

String EMP_ID_LEAVETABLE = "INSERT INTO $tableName (EmployeeId,CompanyId)VALUES(?,?) " ;

String GET_LEAVE_TYPE="Select LeaveType, Days from EmployeeConfig where CompanyId = ? And LeaveStatus = 0 And LeaveType IS NOT NULL";

//String SELECT_LEAVETYPE = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME NOT IN ('EmployeeId','CompanyId') ";	//not in use

//String SELECT_LEAVETYPE ="Select Days from EmployeeConfig WHERE LeaveType = ? AND CompanyId = ? AND LeaveStatus = 0 ";

String INS_HOLIDAYDATAEMPCONFIG = "UPDATE EmployeeConfig SET  WeekendShift = ? , WeekendHolidays = ? WHERE CompanyId = ? ";
;

String SELECT_HOLIDAYDATAEMPCONFIG = "SELECT WeekendShift FROM EmployeeConfig WHERE WeekendShift = ? AND CompanyId = ? " ;

//String SELECT_LEAVESTATUS = "SELECT LeaveStatus FROM EmployeeConfig WHERE CompanyId = ? AND LeaveType = ? "  ;


//String UPDATE_LEAVETYPEEMPCONFIG = "UPDATE EmployeeConfig SET LeaveStatus = 0,Days = ? WHERE CompanyId= ? AND LeaveType = ? ";

//String UPDATE_LEAVETYPELEAVE = "UPDATE $tableName SET $leaveType = ? ";

String UPDATE_LEAVETYPE = "UPDATE $tableName SET $columnName = ? WHERE EmployeeId = ? ";

String ALREADY_EXIST_DATE = "SELECT LeaveType, Date FROM employeerequesttable WHERE  EmployeeId = ? AND CompanyId = ?  ";


//Shift CheckIn Checkout Query


String EMP_SELECTSHIFMODE = "SELECT ShiftMode FROM CompanyTable WHERE CompanyId = ? ";

String EMP_INDETAILS = "SELECT CONCAT(FirstName,' ',LastName) AS Name,Type,Department,Shift FROM EmployeeTable  WHERE EmployeeId = ? AND CompanyId = ? ";
		

String EMP_SELECT_CHECKINFORCHEKOUT = "SELECT CheckInTime FROM EmployeeAttendanceTable WHERE CompanyId = ? AND EmployeeId = ? AND date(Date) = ? ";

String EMP_PAUSETIMEIN_UPDATE = "UPDATE EmployeeAttendanceTable SET InPauseTime = ? WHERE CompanyId = ? AND EmployeeId = ? AND date(Date) = ?  " ;

String VALID_EMP_ID="SELECT EmployeeId FROM EmployeeTable WHERE EmployeeId = ? AND  CompanyId = ?  AND Status <> 1 ";

String EMP_SELECT_BLOCK="SELECT Block FROM EmployeeTable WHERE EmployeeId = ?  And CompanyId = ? ";
  

String EMP_SELECT_CHECKINTIME="SELECT CheckinTime FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) = ? AND CompanyId = ? ";
	
String EMP_SELECT_CHECKOUTTIME="SELECT CheckoutTime FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND CompanyId = ? AND date(Date) = ? ";

String EMP_SELECT_INPAUSETIME = "SELECT InPauseTime FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) = ? AND CompanyId = ? " ; 
	
	
String EMP_CHECKOUTUPDATE="UPDATE EmployeeAttendanceTable SET CheckoutTime = ? , TotalWorkHour = ? ,Status = ?"
			+ " WHERE EmployeeId = ? AND date(Date) = ? AND CompanyId = ? ";

	
String EMP_SELECT_TOTALWORKHOUR1="SELECT TotalWorkHour from EmployeeAttendanceTable WHERE EmployeeId = ? AND CompanyId = ? AND date(Date) = ? " ;


String EMP_PAUSETIMEOUT_UPDATE = "UPDATE EmployeeAttendanceTable SET InPauseTime = '-' ,CheckoutTime = ? , TotalWorkHour = ? , Status= ? "
+ " WHERE EmployeeId = ? AND date(Date) = ? AND CompanyId = ?  ";


String EMP_SELECT_CHECKINID="SELECT EmployeeId FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND CompanyId = ? AND date(Date) = ?" ;

String EMP_SELECTSHIFTNOS = "SELECT ShiftType FROM ShiftConfig WHERE CompanyId = ? " ;

String EMP_SELECTSHIFT = "SELECT Start,End FROM ShiftConfig WHERE CompanyId = ? AND ShiftType = ? " ;
	
	

// my query


String EMP_SESSION_DETAILS=" SELECT Session ,CheckoutTime , InPauseTime FROM   EmployeeAttendanceTable WHERE  EmployeeId = ? And CompanyId = ? ORDER  BY date(Date) DESC LIMIT  1";

String EMP_SESSION_CHECKIN_INSERT="INSERT INTO EmployeeAttendanceTable(CompanyId,EmployeeId,Name,Type,CheckInTime,Session,Department,CheckInDate,Status)Values(?,?,?,?,?,?,?,'P')";

String EMP_SESSION_PAUSEIN_UPDATE="UPDATE EmployeeAttendanceTable SET InPauseTime = ? WHERE CompanyId = ? AND EmployeeId = ? ORDER BY  date(Date) DESC LIMIT 1  ";



String SELECTEMPTYPE = "select type ,department from EmployeeTable where EmployeeId=? and companyId=?" ;

String EMPLEAVEAUTHORIZEINSERT = "INSERT into EmployeeAttendanceTable(employeeid,companyid,status,date,name,type,department) values(? ,? ,'L',?,?,?,?)";


//signupquey

String SITE_ALREADYEXIST_EMAILID = "SELECT EmailId  From EmployeeTable WHERE EmailId = ? ";

String SITE_ALREADYEXIST_MOBILENO = "SELECT MobileNo From EmployeeTable WHERE MobileNo = ? ";

String SITE_INSERT_COMPANY = "INSERT INTO CompanyTable(CompanyName,Address,City,PinCode,State,Country,Phone,EmailId)VALUES(?,?,?,?,?,?,?,?) ";

String SITE_SELECTID = "SELECT CompanyId FROM CompanyTable WHERE EmailId = ? OR Phone = ? ";

String SITE_INSERTEMPLOYEE = "INSERT INTO EmployeeTable(CompanyId,EmployeeId,FirstName,LastName,DOB,EmailId,MobileNo,Address,Type,Role,Department)VALUES(?,?,?,?,?,?,?,?,'Permanent','Propertier','Admin' )";


String SITE_INSERTEMPLOYEE_CONFIG = "INSERT INTO EmployeeConfig (CompanyId,Role,Permission)values(? , 'Propertier' ,'attendance,chart,maintenance,report,attendanceRegulation,taskMapping,supervisorAuthority,avoidAttendanceTracking')";

String SITE_SHIFT_INSERT="Insert Into ShiftConfig (CompanyId,ShiftType) values(?,1)";

String CREATE_TABLE_HOLIDAY = "CREATE TABLE $tableName( dt DATE NOT NULL PRIMARY KEY, "
		+ "y SMALLINT NULL,q tinyint NULL, m tinyint NULL,d tinyint NULL,"
		+ "dw tinyint NULL,monthName VARCHAR(9) NULL,dayName VARCHAR(9) NULL,"
		+ "w tinyint NULL,isWeekday BINARY(1) NULL,isHoliday BINARY(1) NULL,"
		+ "holidayDescr VARCHAR(32) NULL,isPayday BINARY(1) NULL)";

String INSERT_HOLIDAYTABLE="INSERT INTO $tableName (dt) SELECT DATE( ? ) "
		+ " + INTERVAL a.i*10000 + b.i*1000 + c.i*100 + d.i*10 + e.i DAY FROM ints a "
		+ "JOIN ints b JOIN ints c JOIN ints d JOIN ints e WHERE (a.i*10000 + b.i*1000 + "
		+ "c.i*100 + d.i*10 + e.i) <= 365 ORDER BY 1" ;

String UPDATE_HOLIDAYTABLE=" update $tableName set y=year(dt),q=quarter(dt),m=month(dt),d=day(dt),dw=dayofweek(dt),"
		+ "monthName=monthname(dt),dayName=dayname(dt),w=week(dt),isWeekday="
		+"case " 
		+"when dayofweek(dt) = 1 or dayofweek(dt) = 7 then 0 "  
		+"   else 1 end ";

String CREATE_LEAVETABLE = "create table $tableName (EmployeeId int(3) zerofill not null,"
		+ "CompanyId int(3) zerofill not null ) " ;




}
