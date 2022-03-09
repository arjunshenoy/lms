package com.germanium.lms.model.dto;

public class LeaveRulesDto {
	
	private int leaveId;
	
	
	private String name;
	
	private String lapseDate;
	
	private int carryOverCount;
	
	private int maxLeavesCount;
	
	private int costIncurred;
	
	private String combinableLeaves;
	
	private float leaveCount;
	
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