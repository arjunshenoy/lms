package com.germanium.lms.model.dto;

public class Manager {
	
	private int id;
	
	private String department;

	private String name;
	
	public Manager(int id, String department, String name) {
		this.id = id;
		this.department = department;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	public String getName() {
		return this.name;
	}
}
