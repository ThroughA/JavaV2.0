package com.fillingstation.json;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeReportJSON {

	String firstName;
	String lastName;
	String employeeId;
	String department;
	String role;
	String employeeType;
	String name;
	String checkinTime;
	String checkoutTime;
	String totalWorkHour="-";
	String date;
	String status;
	String EmployeeName;
	String mobileNo;
	String authorizedBy="-";
	String superiorId;
	String operation;
	String time;
	String search;
	String companyId;
	int permanentCountLeave;
	int temporaryCountLeave;
	int contractCountLeave;
	int noOfDaysLeave;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	//Variables used to assign the count values of the total employee of the company
	int noOfPermanentEmployee=0;
	int noOfTemporaryEmployee=0;
	int noOfContractEmployee=0;
	
	//Variables used to assign the present employee count values after checkout is done
	int permanentCountPresent = 0;
	int temporaryCountPresent = 0;
	int contractCountPresent = 0;
	
	//Variables used to assign the absent count values 
	int permanentCountAbsent=0;
	int temporaryCountAbsent=0;
	int contractCountAbsent=0;
		
	//Variables used to assign the no.of absent and present days for each employee
	int noOfDaysPresent=0;
	int noOfDaysAbsent=0;
		
	
	
		
	public int getPermanentCountPresent() {
		return permanentCountPresent;
	}
	public void setPermanentCountPresent(int permanentCountPresent) {
		this.permanentCountPresent = permanentCountPresent;
	}
	public int getTemporaryCountPresent() {
		return temporaryCountPresent;
	}
	public void setTemporaryCountPresent(int temporaryCountPresent) {
		this.temporaryCountPresent = temporaryCountPresent;
	}
	public int getContractCountPresent() {
		return contractCountPresent;
	}
	public void setContractCountPresent(int contractCountPresent) {
		this.contractCountPresent = contractCountPresent;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public String getTotalWorkHour() {
		return totalWorkHour;
	}
	public void setTotalWorkHour(String totalWorkHour) {
		this.totalWorkHour = totalWorkHour;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNoOfPermanentEmployee() {
		return noOfPermanentEmployee;
	}
	public void setNoOfPermanentEmployee(int noOfPermanentEmployee) {
		this.noOfPermanentEmployee = noOfPermanentEmployee;
	}
	public int getNoOfTemporaryEmployee() {
		return noOfTemporaryEmployee;
	}
	public void setNoOfTemporaryEmployee(int noOfTemporaryEmployee) {
		this.noOfTemporaryEmployee = noOfTemporaryEmployee;
	}
	public int getNoOfContractEmployee() {
		return noOfContractEmployee;
	}
	public void setNoOfContractEmployee(int noOfContractEmployee) {
		this.noOfContractEmployee = noOfContractEmployee;
	}
	public int getPermanentCountAbsent() {
		return permanentCountAbsent;
	}
	public void setPermanentCountAbsent(int permanentCountAbsent) {
		this.permanentCountAbsent = permanentCountAbsent;
	}
	public int getTemporaryCountAbsent() {
		return temporaryCountAbsent;
	}
	public void setTemporaryCountAbsent(int temporaryCountAbsent) {
		this.temporaryCountAbsent = temporaryCountAbsent;
	}
	public int getContractCountAbsent() {
		return contractCountAbsent;
	}
	public void setContractCountAbsent(int contractCountAbsent) {
		this.contractCountAbsent = contractCountAbsent;
	}
	public int getNoOfDaysPresent() {
		return noOfDaysPresent;
	}
	public void setNoOfDaysPresent(int noOfDaysPresent) {
		this.noOfDaysPresent = noOfDaysPresent;
	}
	public int getNoOfDaysAbsent() {
		return noOfDaysAbsent;
	}
	public void setNoOfDaysAbsent(int noOfDaysAbsent) {
		this.noOfDaysAbsent = noOfDaysAbsent;
	}
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public String getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public int getPermanentCountLeave() {
		return permanentCountLeave;
	}
	public void setPermanentCountLeave(int permanentCountLeave) {
		this.permanentCountLeave = permanentCountLeave;
	}
	public int getTemporaryCountLeave() {
		return temporaryCountLeave;
	}
	public void setTemporaryCountLeave(int temporaryCountLeave) {
		this.temporaryCountLeave = temporaryCountLeave;
	}
	public int getContractCountLeave() {
		return contractCountLeave;
	}
	public void setContractCountLeave(int contractCountLeave) {
		this.contractCountLeave = contractCountLeave;
	}
	public int getNoOfDaysLeave() {
		return noOfDaysLeave;
	}
	public void setNoOfDaysLeave(int noOfDaysLeave) {
		this.noOfDaysLeave = noOfDaysLeave;
	}
	
	
	}