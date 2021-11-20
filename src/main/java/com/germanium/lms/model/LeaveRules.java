package com.germanium.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*
* @author Arjun Krishna Shenoy
*/

@Entity
@ApiModel(value = "LeaveRules", description = "Table that hold the leave details" )
@Table(name ="leave_rules")
public class LeaveRules {
	
	@Id
	@Column(name = "leave_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name = "leaveId")
	private int leaveId;
	
	
	@Column(name = "name")
	@ApiModelProperty(name = "name")
	private String name;
	
	@Column(name = "lapse_date")
	@ApiModelProperty(name = "lapseDate")
	private String lapseDate;
	
	@Column(name = "carry_over_count")
	@ApiModelProperty(name = "carryOverCount")
	private int carryOverCount;
	
	@Column(name = "max_leaves_count")
	@ApiModelProperty(name = "maxLeavesCount")
	private int maxLeavesCount;
	
	@Column(name = "cost_incurred")
	@ApiModelProperty(name = "costIncurred")
	private int costIncurred;
	
	@Column(name = "combinable_leaves")
	@ApiModelProperty(name = "combinableLeaves")
	private String combinableLeaves;
	
	
	@Column(name = "number_of_leaves")
	@ApiModelProperty(name = "numberOfLeaves")
	private float leaveCount;
	
	
	@Column(name = "rule_expression")
	@ApiModelProperty(name = "ruleExpression")
	private String ruleExpression;


	public int getLeaveId() {
		return leaveId;
	}


	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLapseDate() {
		return lapseDate;
	}


	public void setLapseDate(String lapseDate) {
		this.lapseDate = lapseDate;
	}


	public int getCarryOverCount() {
		return carryOverCount;
	}


	public void setCarryOverCount(int carryOverCount) {
		this.carryOverCount = carryOverCount;
	}


	public int getMaxLeavesCount() {
		return maxLeavesCount;
	}


	public void setMaxLeavesCount(int maxLeavesCount) {
		this.maxLeavesCount = maxLeavesCount;
	}


	public int getCostIncurred() {
		return costIncurred;
	}


	public void setCostIncurred(int costIncurred) {
		this.costIncurred = costIncurred;
	}


	public String getCombinableLeaves() {
		return combinableLeaves;
	}


	public void setCombinableLeaves(String combinableLeaves) {
		this.combinableLeaves = combinableLeaves;
	}


	public float getLeaveCount() {
		return leaveCount;
	}


	public void setLeaveCount(float leaveCount) {
		this.leaveCount = leaveCount;
	}


	public String getRuleExpression() {
		return ruleExpression;
	}


	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}
	

	
	
}
