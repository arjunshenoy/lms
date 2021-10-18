package com.germanium.lms.models;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@Getter
@Setter
public class Login {
	
	@Id
	@Column(name = "employee_id")
	@NotNull
	private int employeeId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "created_ts")
	@CreationTimestamp
	private Timestamp createdTs;	
}
