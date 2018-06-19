package com.fillingstation.constants;

public interface  NewQueryConstants {
	//Daily Report QUery
	
	  String EMP_DAILYREPORT1 = "SELECT EmployeeId,Name,date(Date) AS Date,CheckinTime,CheckoutTime,TotalWorkHour,Status,"
			+ "Type,Department , AuthorizedBy FROM EmployeeAttendanceTable WHERE CompanyId = ? AND date(Date) = ? AND EmployeeId = ?  ";

String EMP_DAILYREPORTSHIFT = "SELECT EmployeeId,CONCAT(FirstName,' ',LastName) AS Name,Type,Department,Shift FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? " ;

String EMP_DAILYREPORTHOLIDAY = "SELECT HoliDayShift , $Shift FROM $tableName WHERE dt = ? " ;

String EMP_REPORTLIST = "SELECT EmployeeId FROM EmployeeTable WHERE CompanyId = ? and status = 0 AND Role <> 'Propertier' And AvoidAttendance=0 ";

String EMP_TOTALID = "SELECT EmployeeId FROM EmployeeTable WHERE CompanyId = ? AND Status = 0 AND Role <> 'Propertier'  " ;

String EMP_ATTNTOTALID = "SELECT EmployeeId FROM EmployeeAttendanceTable WHERE CompanyId = ? AND date(Date)= ? " ;


String EMP_ABSENTINFO="SELECT Shift,Type FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? " ; 



String EMP_MAINTENANCE_COUNT="SELECT (SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 And Role <> 'Propertier' )as PermanentEmployeeCount,"
		+ "(SELECT COUNT(EmployeeId) FROM EmployeeTable WHERE Type = ?  AND CompanyId = ? And Status=0 And Role <> 'Propertier' )as TemporaryEmployeeCount,"
		+ "COUNT(EmployeeId) as ContractEmployeeCount FROM EmployeeTable WHERE Type = ? AND CompanyId = ? And Status=0 And Role <> 'Propertier'";

String EMP_DAILYREPORTPRESENTCOUNT="SELECT (SELECT  COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? )as PermanentEmployeePresentCount," 
		+ "(SELECT  COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? )as TemporaryEmployeePresentCount,"
		+ " COUNT(EmployeeId) as ContractEmployeePresentCount FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'P' AND CompanyId = ? ";

String EMP_DAILYREPORTLEAVECOUNT = "SELECT (SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'L' AND CompanyId = ? )as PermanentEmployeeLeaveCount," 
		+ "(SELECT COUNT(EmployeeId) FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'L' AND CompanyId = ? )as TemporaryEmployeeLeaveCount,"
		+ "COUNT(EmployeeId) as ContractEmployeeLeaveCount FROM EmployeeAttendanceTable WHERE Type = ? AND date(Date) = ? AND Status = 'L' AND CompanyId = ? ";



//Monthly and period


String EMP_NAME_SELECT="SELECT CONCAT(FirstName,' ',LastName) AS Name FROM EmployeeTable WHERE EmployeeId = ? AND CompanyId = ? " ;

String EMP_PERIODREPORTPRESENTCOUNT="SELECT COUNT(EmployeeId) AS PresentDays  FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) between ? and ? AND Status = 'P' AND CompanyId =? ";
	
String EMP_SELECT_PERIODTOTALWORKHOUR="SELECT TotalWorkhour from EmployeeAttendanceTable where employeeid = ? and date(Date) between ? And  ? and companyid = ? ";

String EMP_INDIVIDUALPERIOD_TOTALWORKHOUR = "select CAST((sec_to_time(sum(time_to_sec(totalworkhour)) )) as char) As TotalWorkhour from EmployeeAttendanceTable where employeeid= ? AND CompanyId =? AND date(Date) between ? and ? ";	
	

String EMP_PERIODREPORTLEAVECOUNT="SELECT COUNT(EmployeeId)AS LeaveDays FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND date(Date) BETWEEN ? AND ? AND Status = 'L' AND CompanyId = ? ";

String EMP_ID = "SELECT EmployeeId FROM EmployeeAttendanceTable WHERE EmployeeId = ? AND CompanyId = ? AND date(Date) = ? ";




}
