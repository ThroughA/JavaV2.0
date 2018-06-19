package com.fillingstation.json;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class EmployeeConfigJSON {
	private String role;
	private String department;
	private String authorization;
	private String permission;
	private String employeeId;
	private String companyId;
	private int supervisorAuthority=2;
	private  ArrayList<EmployeeConfigJSON> permissionList;
	private String workingHours;
	private int avoidAttendance=2;
	private int biometricValue;
	private String employeeName;
    private String newShift;
    private String shift;
    private String totalShift;
    private String superiorId;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public int getSupervisorAuthority() {
		return supervisorAuthority;
	}
	public void setSupervisorAuthority(int supervisorAuthority) {
		this.supervisorAuthority = supervisorAuthority;
	}
	public ArrayList<EmployeeConfigJSON> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(ArrayList<EmployeeConfigJSON> permissionList) {
		this.permissionList = permissionList;
	}
	public String getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}
	public int getAvoidAttendance() {
		return avoidAttendance;
	}
	public void setAvoidAttendance(int avoidAttendance) {
		this.avoidAttendance = avoidAttendance;
	}
	public int getBiometricValue() {
		return biometricValue;
	}
	public void setBiometricValue(int biometricValue) {
		this.biometricValue = biometricValue;
	}
	public String getNewShift() {
		return newShift;
	}
	public void setNewShift(String newShift) {
		this.newShift = newShift;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getTotalShift() {
		return totalShift;
	}
	public void setTotalShift(String totalShift) {
		this.totalShift = totalShift;
	}
	public String getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	
	
	

}
