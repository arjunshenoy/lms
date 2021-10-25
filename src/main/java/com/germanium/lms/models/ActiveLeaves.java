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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author, Teena Joseph
 */
@Entity
@Table(name = "active_leaves")
@Getter
@Setter
@Embeddable
@ApiModel(value = "ActiveLeaves", description = "Class holds the details of active leaves")
public class ActiveLeaves implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LeaveHistoryId leaveHistoryId;

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
}
