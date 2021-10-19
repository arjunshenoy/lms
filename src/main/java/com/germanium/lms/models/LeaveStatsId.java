package com.germanium.lms.models;

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
@Getter
@Setter
public class LeaveStatsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "leave_id")
	@ApiModelProperty(name = "leaveId")
	private int leaveId;

	@Column(name = "emplopyee_id")
	@ApiModelProperty(name = "employeeId")
	private int employeeId;

}
