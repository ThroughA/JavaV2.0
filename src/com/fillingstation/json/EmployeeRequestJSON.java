package com.fillingstation.json;

import java.util.ArrayList;

public class EmployeeRequestJSON {

	private ArrayList<EmployeeAttendanceJSON> attendanceRegulation;

	public ArrayList<EmployeeAttendanceJSON> getAttendanceRegulation() {
		return attendanceRegulation;
	}

	public void setAttendanceRegulation(ArrayList<EmployeeAttendanceJSON> attendanceRegulation) {
		this.attendanceRegulation = attendanceRegulation;
	}
	
	
}
