package com.germanium.lms.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
	private int leaveId;

	@Column(name = "emplopyee_id")
	private int employeeId;

}
