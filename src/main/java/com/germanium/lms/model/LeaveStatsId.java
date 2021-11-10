package com.germanium.lms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
*
* @author Arjun Krishna Shenoy
*/

@Embeddable
public class LeaveStatsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "leave_id")
	@ApiModelProperty(name = "leaveId")
	private int leaveId;

	@Column(name = "employee_id")
	@ApiModelProperty(name = "employeeId")
	private int employeeId;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
