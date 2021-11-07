package com.germanium.lms.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
*
* @author Arjun Krishna Shenoy
*/

@Entity
@Table(name = "leave_stats")
@ApiModel(value = "LeaveStats", description = "Class containing the leave statistics for each user")

public class LeaveStats implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LeaveStatsId id;

	@ApiModelProperty(value = "leaveCount")
	@Column(name = "number_of_leaves")
	private float leaveCount;

	

	public LeaveStatsId getId() {
		return id;
	}

	public void setId(LeaveStatsId id) {
		this.id = id;
	}

	public float getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(float leaveCount) {
		this.leaveCount = leaveCount;
	}
}
