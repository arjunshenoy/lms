package com.germanium.lms.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author, Teena Joseph
 */

@Entity
@Table(name = "leave_history")

@ApiModel(value = "LeaveHistory", description = "Class which holds the Leave History details")
public class LeaveHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LeaveHistoryId leaveHistoryId;

	@Column(name = "leave_id")
	@NotNull
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

	@Column(name = "leave_status")
	@ApiModelProperty(value = "leaveStatus")
	private String leaveStatus;

	@Column(name = "reason")
	@ApiModelProperty(value = "reason")
	private String reason;

	@Column(name = "comments")
	@ApiModelProperty(value = "comments")
	private String comments;

	@Column(name = "decision_date")
	@ApiModelProperty(value = "decisionDate")
	private Date decisionDate;

	@Column(name = "updated_ts")
	@UpdateTimestamp
	@ApiModelProperty(value = "updatedTs")
	private Timestamp updatedTs;



	public LeaveHistoryId getLeaveHistoryId() {
		return leaveHistoryId;
	}

	public void setLeaveHistoryId(LeaveHistoryId leaveHistoryId) {
		this.leaveHistoryId = leaveHistoryId;
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

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
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

	public Date getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public Timestamp getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

	public LeaveHistory(LeaveHistoryBuilder leaveHistoryBuilder) {		
		this.leaveHistoryId = leaveHistoryBuilder.leaveHistoryId;
		this.comments = leaveHistoryBuilder.comments;
		this.decisionDate = leaveHistoryBuilder.decisionDate;
		this.departmentId = leaveHistoryBuilder.departmentId;
		this.fromDate = leaveHistoryBuilder.fromDate;
		this.leaveId = leaveHistoryBuilder.leaveId;
		this.leaveStatus = leaveHistoryBuilder.leaveStatus;
		this.toDate = leaveHistoryBuilder.toDate;
		this.fromDate = leaveHistoryBuilder.fromDate;
		this.reason = leaveHistoryBuilder.reason;
		this.updatedTs = leaveHistoryBuilder.updatedTs;

	}
	
	public static LeaveHistoryBuilder builder() {
		return new LeaveHistoryBuilder();
	}
	
	
	public static class LeaveHistoryBuilder {

		private LeaveHistoryId leaveHistoryId;

		private int leaveId;

		private int departmentId;

		private Date fromDate;

		private Date toDate;

		private String leaveStatus;

		private String reason;

		private String comments;

		private Date decisionDate;

		private Timestamp updatedTs;

		public LeaveHistoryBuilder leaveHistoryId(LeaveHistoryId id) {
			this.leaveHistoryId = id;
			return this;
		}

		public LeaveHistoryBuilder leaveId(int leaveId) {
			this.leaveId = leaveId;
			return this;
		}

		public LeaveHistoryBuilder departmentId(int departmentId) {
			this.departmentId = departmentId;
			return this;
		}
		public LeaveHistoryBuilder fromDate(Date fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		public LeaveHistoryBuilder toDate(Date toDate) {
			this.toDate = toDate;
			return this;
		}
		public LeaveHistoryBuilder leaveStatus(String leaveStatus) {
			this.leaveStatus = leaveStatus;
			return this;
		}
		public LeaveHistoryBuilder reason(String reason) {
			this.reason = reason;
			return this;
		}
		public LeaveHistoryBuilder comments(String comments) {
			this.comments = comments;
			return this;
		}
		
		public LeaveHistoryBuilder decisionDate(Date decisionDate) {
			this.decisionDate = decisionDate;
			return this;
		}
		
		public LeaveHistoryBuilder updatedTs(Timestamp updatedTs) {
			this.updatedTs = updatedTs;
			return this;
		}
		
		public LeaveHistory build() {
			LeaveHistory leaveHistory =  new LeaveHistory(this);
			return leaveHistory;
		}		
	}

}
