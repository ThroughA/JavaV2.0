package com.fillingstation.logic;

import java.util.ArrayList;

import com.fillingstation.json.EmployeeLeaveConfigJSON;

public class EmpWeekEndJSON {

	
	private String companyId;
	private String sundayOption;
	private String mondayOption;
	private String tuesdayOption;
	private String wednesdayOption;
	private String thursdayOption;
	private String fridayOption;
	private String saturdayOption;
	private String shift;
	private String date;
	private String leavePerYear;
	private String superiorId;
	private ArrayList<EmployeeLeaveConfigJSON>  weekendOptionList;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getSundayOption() {
		return sundayOption;
	}
	public void setSundayOption(String sundayOption) {
		this.sundayOption = sundayOption;
	}
	public String getMondayOption() {
		return mondayOption;
	}
	public void setMondayOption(String mondayOption) {
		this.mondayOption = mondayOption;
	}
	public String getTuesdayOption() {
		return tuesdayOption;
	}
	public void setTuesdayOption(String tuesdayOption) {
		this.tuesdayOption = tuesdayOption;
	}
	public String getWednesdayOption() {
		return wednesdayOption;
	}
	public void setWednesdayOption(String wednesdayOption) {
		this.wednesdayOption = wednesdayOption;
	}
	public String getThursdayOption() {
		return thursdayOption;
	}
	public void setThursdayOption(String thursdayOption) {
		this.thursdayOption = thursdayOption;
	}
	public String getFridayOption() {
		return fridayOption;
	}
	public void setFridayOption(String fridayOption) {
		this.fridayOption = fridayOption;
	}
	public String getSaturdayOption() {
		return saturdayOption;
	}
	public void setSaturdayOption(String saturdayOption) {
		this.saturdayOption = saturdayOption;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLeavePerYear() {
		return leavePerYear;
	}
	public void setLeavePerYear(String leavePerYear) {
		this.leavePerYear = leavePerYear;
	}
	public ArrayList<EmployeeLeaveConfigJSON> getWeekendOptionList() {
		return weekendOptionList;
	}
	public void setWeekendOptionList(ArrayList<EmployeeLeaveConfigJSON> weekendOptionList) {
		this.weekendOptionList = weekendOptionList;
	}
	public String getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
		
	
	
}
