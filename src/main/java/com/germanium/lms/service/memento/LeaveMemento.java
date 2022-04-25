package com.germanium.lms.service.memento;

import java.util.Date;

public class LeaveMemento {

	private int leaveRequestId;

	private int employeeId;

	private Date dateOfApplication;

	private int leaveId;

	private int departmentId;

	private Date fromDate;

	private Date toDate;

	private String reason;

	private String comments;

	private String leaveName;

	private String leaveStatus;

	public LeaveMemento(int leaveRequestId, int employeeId, Date dateOfApplication, int leaveId, int departmentId,
			Date fromDate, Date toDate, String reason, String comments, String leaveName, String leaveStatus) {
		super();
		this.leaveRequestId = leaveRequestId;
		this.employeeId = employeeId;
		this.dateOfApplication = dateOfApplication;
		this.leaveId = leaveId;
		this.departmentId = departmentId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reason = reason;
		this.comments = comments;
		this.leaveName = leaveName;
		this.leaveStatus = leaveStatus;
	}

	public int getLeaveRequestId() {
		return leaveRequestId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public String getReason() {
		return reason;
	}

	public String getComments() {
		return comments;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}
	
	

}
