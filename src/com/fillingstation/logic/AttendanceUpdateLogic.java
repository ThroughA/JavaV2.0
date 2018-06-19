package com.fillingstation.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.fillingstation.constants.IQueryConstants;
import com.fillingstation.util.DBUtil;

public class AttendanceUpdateLogic implements Job  {

	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		Connection connection=null;
		try {
			SchedulerContext schedulerContext = arg0.getScheduler().getContext();
		      //Below line gets the value from context.
		      //Just get it and cast it in to correct type
		      String dateFromContext = (String) schedulerContext.get("date");
		      System.out.println(dateFromContext);
			System.out.println("going to update default absent list............");
			connection =DBUtil.getDBConnection();
			String querySelect=IQueryConstants.EMP_DEFAULT_ATTENDANCE;
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			    preparedStmt.setString(1,dateFromContext);
			    preparedStmt.executeUpdate();
			    System.out.println("updated default absent list.........");			    connection.close();
		}
		catch (SchedulerException e1) {
		      e1.printStackTrace();
		    }
		catch (SQLException e)
	    {
	    e.printStackTrace();
	    }
	     	
	   finally {
		DBUtil.closeConnection(connection);
	}
	}
}