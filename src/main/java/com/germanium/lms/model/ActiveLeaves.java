package com.germanium.lms.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.germanium.lms.exception.LeaveServiceException;
import com.germanium.lms.service.memento.LeaveMemento;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author, Teena Joseph
 */
@Entity
@Table(name = "active_leaves")

@ApiModel(value = "ActiveLeaves", description = "Class holds the details of active leaves")
public class ActiveLeaves implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "leave_request_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "leaveRequestId")
	private int leaveRequestId;

	@Column(name = "employee_id")
	@NotNull
	@ApiModelProperty(value = "employeeId")
	private int employeeId;

	@Column(name = "date_of_application")
	@NotNull
	@ApiModelProperty(value = "dateOfApplication")
	private Date dateOfApplication;

	@Column(name = "leave_id")
	@ApiModelProperty(value = "leaveId")
	private int leaveId;

	@Column(name = "department_id")
	@ApiModelProperty(value = "departmentId")
	private int departmentId;

	@Column(name = "from_date")
	@ApiModelProperty(value = "fromDate")
	private Date fromDate;

	@Column(name = "to_date")
	@ApiModelProperty(value = "toDate")
	private Date toDate;

	@Column(name = "reason")
	@ApiModelProperty(value = "reason")
	private String reason;

	@Column(name = "comments")
	@ApiModelProperty(value = "comments")
	private String comments;

	@Column(name = "updated_ts")
	@UpdateTimestamp
	@ApiModelProperty(value = "updatedTs")
	private Timestamp updatedTs;

	@Transient
	private Blob document;

	@Transient
	private String leaveName;

	@Transient
	private Float pay;

	@Transient
	private Float payScale;

	@Transient
	private String leaveStatus;

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

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
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

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	/* Functions to support Memento DP */

	public LeaveMemento createMemento() {

		return new LeaveMemento(this.leaveRequestId, this.employeeId, this.dateOfApplication, this.leaveId,
				this.departmentId, this.fromDate, this.toDate, this.reason, this.comments, this.leaveName,this.leaveStatus);
	}

	public void restore(LeaveMemento memento) {
		if (memento != null) {
			this.leaveRequestId = memento.getLeaveRequestId();
			this.employeeId = memento.getEmployeeId();
			this.dateOfApplication = memento.getDateOfApplication();
			this.leaveId = memento.getLeaveId();
			this.departmentId = memento.getDepartmentId();
			this.fromDate = memento.getFromDate();
			this.toDate = memento.getToDate();
			this.reason = memento.getReason();
			this.comments = memento.getComments();
			this.leaveName = memento.getLeaveName();
			this.leaveStatus = memento.getLeaveStatus();
		} else {
			// throw new LeaveServiceException("Cannot Perform restore with null memento
			// object");

		}
	}

}
