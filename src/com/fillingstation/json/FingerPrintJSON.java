package com.fillingstation.json;

public class FingerPrintJSON {
	private String 	fingerPrint;
	private String 	employeeId="003";
	private String 	companyId="001";
	private String dbFingerValue;
	private String currentFingerValue;
	private String superiorId;
	
	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
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

	public String getDbFingerValue() {
		return dbFingerValue;
	}

	public void setDbFingerValue(String dbFingerValue) {
		this.dbFingerValue = dbFingerValue;
	}

	public String getCurrentFingerValue() {
		return currentFingerValue;
	}

	public void setCurrentFingerValue(String currentFingerValue) {
		this.currentFingerValue = currentFingerValue;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	
	
	
}
