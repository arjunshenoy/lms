package com.germanium.lms.model.factory;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.dto.LeaveRequestDto;

public abstract class Leave {

	private int employeeId;

	private Date dateOfApplication;

	private int leaveId;

	private int departmentId;

	private Date fromDate;

	private Date toDate;

	private Timestamp updatedTs;

	public Leave(LeaveRequestDto active) {
		this.employeeId = active.getEmployeeId();
		this.dateOfApplication = active.getDateOfApplication();
		this.leaveId = active.getLeaveId();
		this.departmentId = active.getDepartmentId();
		this.fromDate = active.getFromDate();
		this.toDate = active.getToDate();
		this.updatedTs = active.getUpdatedTs();
	}

	public abstract Blob getDocument();

	public abstract String getReason();

	public abstract Float getPay();

	public abstract Float getPayScale();
	
	public abstract String getComment();

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Timestamp getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

}
