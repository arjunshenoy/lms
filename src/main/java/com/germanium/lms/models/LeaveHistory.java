package com.germanium.lms.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author, Teena Joseph
 */

@Entity
@Table (name = "leave_history")
@Getter
@Setter
@Embeddable
public class LeaveHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LeaveHistoryId leaveHistoryId;
	
	@Column (name = "leave_id")
	private int leaveId;
	
	@Column (name = "department_id")
	private int departmentId;
	
	@Column (name = "from_date")
	private Date fromDate;
	
	@Column (name = "to_date")
	private Date toDate;
	
	@Column (name = "leave_status")
	private String leaveStatus;
	
	@Column (name = "reason")
	private String reason;
	
	@Column (name = "comments")
	private String comments;
	
	@Column (name = "decision_date")
	private Date decisionDate;
	
	@Column (name = "updated_ts")
	@UpdateTimestamp
	private Timestamp updatedTs;
	
	
}
