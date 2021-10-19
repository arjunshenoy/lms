package com.germanium.lms.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author, Teena Joseph
 */
@Data
@Embeddable
@EqualsAndHashCode
public class LeaveHistoryId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column (name = "employee_id")
	@NotNull
	@ApiModelProperty(value = "employeeId")
	private int employeeId;
	
	@Column (name = "date_of_application")
	@NotNull
	@ApiModelProperty(value = "dateOfApplication")
	private Date dateOfApplication;

}
