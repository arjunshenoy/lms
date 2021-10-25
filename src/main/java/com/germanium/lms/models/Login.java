package com.germanium.lms.models;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Ajin Pius Michel
 */

@Entity
@Table(name = "login")
@Getter
@Setter
@ApiModel(value = "Login", description = "Class that holds the login details of Users")
public class Login {
	
	@Id
	@Column(name = "employee_id")
	@NotNull
	@ApiModelProperty(value = "employeeId")
	private int employeeId;
	
	@Column(name = "email")
	@ApiModelProperty(value = "email")
	private String email;
	
	@Column(name = "created_ts")
	@CreationTimestamp
	@ApiModelProperty(value = "createdTs")
	private Timestamp createdTs;	
}
