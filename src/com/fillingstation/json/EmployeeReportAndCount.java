package com.fillingstation.json;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class EmployeeReportAndCount {

	private ArrayList<EmployeeReportJSON> employeeRetrievelist ;
	private ArrayList<EmployeeReportJSON> employeeCountRetrievelist;
	private ArrayList<EmployeeConfigJSON> employeeDepartmentlist;
	private ArrayList<EmployeeConfigJSON> employeeRolelist;
	private ArrayList<EmployeeConfigJSON> employeePermisionlist;
	private ArrayList<EmployeeConfigJSON> employeeList;
	private ArrayList<EmployeeConfigJSON> lockList;
	private ArrayList<EmployeeConfigJSON> reportingManagerList;
	private ArrayList<ShiftConfigJSON> shiftData;
	private ArrayList<EmployeeLeaveConfigJSON> leaveTypeList;
	private String Role;
	private String department;
	private String userName;
	private String employeeId;
	private String companyId;
	private String companyName;
	private int biometricValue;
	private ArrayList<EmployeeLeaveConfigJSON> holidayDatalist;
	private int shiftMode;
	private String totalShift;
	
	public int getShiftMode() {
		return shiftMode;
	}
	public void setShiftMode(int shiftMode) {
		this.shiftMode = shiftMode;
	}
	public ArrayList<ShiftConfigJSON> getShiftData() {
		return shiftData;
	}
	public void setShiftData(ArrayList<ShiftConfigJSON> shiftData) {
		this.shiftData = shiftData;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public ArrayList<EmployeeConfigJSON> getEmployeePermisionlist() {
		return employeePermisionlist;
	}
	public void setEmployeePermisionlist(ArrayList<EmployeeConfigJSON> employeePermisionlist) {
		this.employeePermisionlist = employeePermisionlist;
	}
	
	public ArrayList<EmployeeReportJSON> getEmployeeRetrievelist() {
		return employeeRetrievelist;
	}
	public void setEmployeeRetrievelist(ArrayList<EmployeeReportJSON> employeeRetrievelist) {
		this.employeeRetrievelist = employeeRetrievelist;
	}
	public ArrayList<EmployeeReportJSON> getEmployeeCountRetrievelist() {
		return employeeCountRetrievelist;
	}
	public void setEmployeeCountRetrievelist(ArrayList<EmployeeReportJSON> employeeCountRetrievelist) {
		this.employeeCountRetrievelist = employeeCountRetrievelist;
	}
	public ArrayList<EmployeeConfigJSON> getEmployeeDepartmentlist() {
		return employeeDepartmentlist;
	}
	public void setEmployeeDepartmentlist(ArrayList<EmployeeConfigJSON> employeeDepartmentlist) {
		this.employeeDepartmentlist = employeeDepartmentlist;
	}
	public ArrayList<EmployeeConfigJSON> getEmployeeRolelist() {
		return employeeRolelist;
	}
	public void setEmployeeRolelist(ArrayList<EmployeeConfigJSON> employeeRolelist) {
		this.employeeRolelist = employeeRolelist;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public ArrayList<EmployeeConfigJSON> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(ArrayList<EmployeeConfigJSON> employeeList) {
		this.employeeList = employeeList;
	}
	
	
	public ArrayList<EmployeeConfigJSON> getReportingManagerList() {
		return reportingManagerList;
	}
	public void setReportingManagerList(ArrayList<EmployeeConfigJSON> reportingManagerList) {
		this.reportingManagerList = reportingManagerList;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public ArrayList<EmployeeConfigJSON> getLockList() {
		return lockList;
	}
	public void setLockList(ArrayList<EmployeeConfigJSON> lockList) {
		this.lockList = lockList;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public ArrayList<EmployeeLeaveConfigJSON> getHolidayDatalist() {
		return holidayDatalist;
	}
	public void setHolidayDatalist(ArrayList<EmployeeLeaveConfigJSON> holidayDatalist) {
		this.holidayDatalist = holidayDatalist;
	}
	public int getBiometricValue() {
		return biometricValue;
	}
	public void setBiometricValue(int biometricValue) {
		this.biometricValue = biometricValue;
	}
	public String getTotalShift() {
		return totalShift;
	}
	public void setTotalShift(String totalShift) {
		this.totalShift = totalShift;
	}
	public ArrayList<EmployeeLeaveConfigJSON> getLeaveTypeList() {
		return leaveTypeList;
	}
	public void setLeaveTypeList(ArrayList<EmployeeLeaveConfigJSON> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}
	
	 
	
	}
