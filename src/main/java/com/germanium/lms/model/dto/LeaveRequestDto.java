package com.germanium.lms.model.dto;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

public class LeaveRequestDto {

	private int leaveRequestId;

	private int employeeId;

	private Date dateOfApplication;

	private int leaveId;

	private int departmentId;

	private Date fromDate;

	private Date toDate;

	private String reason;

	private String comments;

	private Timestamp updatedTs;
	
	private Blob document;
	
	private Float pay;
	
	private Float payScale;
	
	private String addressDuringLeave;
	
	private Float houseRentAllowance;
	
	private String leaveName;

	public int getLeaveRequestId() {
		return leaveRequestId;
	}

	public void setLeaveRequestId(int leaveRequestId) {
		this.leaveRequestId = leaveRequestId;
	}

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Blob getDocument() {
		return document;
	}

	public void setDocument(Blob document) {
		this.document = document;
	}

	public Float getPay() {
		return pay;
	}

	public void setPay(Float pay) {
		this.pay = pay;
	}

	public Float getPayScale() {
		return payScale;
	}

	public void setPayScale(Float payScale) {
		this.payScale = payScale;
	}

	public String getAddressDuringLeave() {
		return addressDuringLeave;
	}

	public void setAddressDuringLeave(String addressDuringLeave) {
		this.addressDuringLeave = addressDuringLeave;
	}

	public Float getHouseRentAllowance() {
		return houseRentAllowance;
	}

	public void setHouseRentAllowance(Float houseRentAllowance) {
		this.houseRentAllowance = houseRentAllowance;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}
	
	
	
	
	
	
}
