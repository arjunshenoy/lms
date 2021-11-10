package com.germanium.lms.model;

// @author: Chinmay Jose K M

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Department")
@Getter
@Setter
@ApiModel(value = "Department", description = "Class that holds the Department details")
public class Department {
	
	@Id
	@Column(name = "department_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "departmentId")
	private Integer departmentId;

	@Column(name = "department_name")
	@ApiModelProperty(value = "departmentName")
	private String departmentName;

	@Column(name = "head_id")
	@ApiModelProperty(value = "headId")
	private String headId;
}
