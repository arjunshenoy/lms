package com.germanium.lms.models;

import java.io.Serializable;
import java.util.Date;

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
public class SubstitutionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "substitutionId")
	@Column(name = "substitute_id")
	private int substitutionId;

	@ApiModelProperty(value = "leaveId")
	@Column(name = "leave_id")
	private int leaveId;

	@ApiModelProperty(value = "dateOfApproval")
	@Column(name = "date_of_approval")
	private Date dateOfApproval;

}
