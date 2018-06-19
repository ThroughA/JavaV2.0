package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.json.EmployeeConfigJSON;
import com.fillingstation.json.EmployeeReportAndCount;
import com.fillingstation.json.ShiftConfigJSON;
import com.fillingstation.util.DBUtil;

public class EmployeeConfig {

	@Path(value="/employeeshiftconfig")

public static EmployeeConfigJSON biometricSettings(EmployeeConfigJSON json) {
		

		
		Connection connection = null;
	
		try {
			System.out.println("going to update biometric value..........."+ json.getBiometricValue() +" "+json.getCompanyId());
			
			connection = DBUtil.getDBConnection();
			String querySelect2 = IQueryConstants.Biometric_Settings;
			PreparedStatement preparedStmt2 = connection.prepareStatement(querySelect2);
			preparedStmt2.setString(2, json.getCompanyId());
			preparedStmt2.setInt(1, json.getBiometricValue());
			
			preparedStmt2.executeUpdate();
			if(json.getBiometricValue()==0){
				EmployeeLogic.AuditReport(json.getSuperiorId(), " Disabled Biometric", "Changed Biometric ", json.getCompanyId());
			}else{
				EmployeeLogic.AuditReport(json.getSuperiorId(), " Enabled Biometric", "Changed Biometric ", json.getCompanyId());
				
			}
			System.out.println("Returning  biometric Details");
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return json;
	}


}
