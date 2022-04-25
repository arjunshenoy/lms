package com.germanium.lms.model.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.germanium.lms.service.lazy.ManagerList;

public class Department {

//	`department_id` int NOT NULL AUTO_INCREMENT,
//    `department_name` varchar(255)  NOT NULL,
//    `head_id` INT (255),
//	`created_timestamp` TIMESTAMP,
//	`updated_timestamp` TIMESTAMP,
//	`created_by` VARCHAR (255),
//	`updated_by` VARCHAR (255),
//	`working_hours_per_day` FLOAT DEFAULT 0,
//	`working_employees_per_day` FLOAT DEFAULT 0,
//	`leave_request_queueing` int DEFAULT 0,
	
////	@Id
////	@Column(name = "department_id")
//	private int id;
	
//	@Column(name = "department_name")
	private String departmentName;
	
//	@Column(name = "head_id")
//	private int headId;
	
//	@Column(name = "created_timestamp")
//	@CreationTimestamp
//	private Timestamp createdTs;
	
//	@Column(name = "updated_timestamp")
//	@UpdateTimestamp
//	private Timestamp updatedTs;
	
//	@Column(name = "working_hours_per_day")
	private float hoursPerDay;
	
//	@Column(name = "working_employees_per_day")
	private float emplyPerDay;
	
	private ManagerList managerList;
	
//	@Column(name = "leave_request_queueing")
//	private int leaveQueueNo;
	
	public Department(String departmentName, float hoursPerDay, float emplyPerDay, ManagerList managerList) {
		this.departmentName = departmentName;
		this.emplyPerDay = emplyPerDay;
		this.hoursPerDay = hoursPerDay;
		this.managerList = managerList;
	}
	
	public String getDepartmentName() {
		return this.departmentName;
	}
	
	public float getHoursPerDay() {
		return this.hoursPerDay;
	}
	
	public float getEmployeePerDay() {
		return this.emplyPerDay;
	}
	
	public ManagerList getManagerList() {
		return this.managerList;
	}	
}
