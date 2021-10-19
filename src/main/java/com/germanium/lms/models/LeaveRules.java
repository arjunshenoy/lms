package com.germanium.lms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@ApiModel(value = "LeaveRules", description = "Table that hold the leave details" )
@Table(name ="leave_rules")

@Getter
@Setter
public class LeaveRules {
	
	@Id
	@Column(name = "leave_id")
	@ApiModelProperty(name = "leaveId")
	private int leaveId;
	
	
	@Column(name = "name")
	@ApiModelProperty(name = "name")
	private String name;
	
	@Column(name = "number_of_leaves")
	@ApiModelProperty(name = "numberOfLeaves")
	private float leaveCount;
	
	
	@Column(name = "rule_expression")
	@ApiModelProperty(name = "ruleExpression")
	private String ruleExpression;
	

}
