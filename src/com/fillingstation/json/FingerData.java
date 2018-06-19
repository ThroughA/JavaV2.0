package com.fillingstation.json;

import java.util.Base64;

public class FingerData {
	 String fingerPrint = null;
	 String Enroll_Template;
	 int Verify_Template;
	 String superiorId;
	 
	 public int getVerify_Template() {
		return Verify_Template;
	}
	public void setVerify_Template(int verify_Template) {
		Verify_Template = verify_Template;
	}
	String employeeId;
	 String companyId="001";
	
	 String emailId;
	 String password;
	
	 byte[] byteISo;
	public String getFingerPrint() {
		return fingerPrint;
	}
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	public String getEnroll_Template() {
		return Enroll_Template;
	}
	public void setEnroll_Template(String enroll_Template) {
		Enroll_Template = enroll_Template;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getByteISo() {
		return byteISo;
	}
	public void setByteISo(byte[] byteISo) {
		this.byteISo = byteISo;
	}
	public String getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	
	
	
}
