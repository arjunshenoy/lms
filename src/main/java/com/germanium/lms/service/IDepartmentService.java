package com.germanium.lms.service;

import java.util.List;

import com.germanium.lms.models.Department;

public interface IDepartmentService {

	public Department createDepartment(Department department); 
	
	public List<Department> getDepartments();
	
	public Department updateDepartment(Integer departmentId, Department department) throws Exception;

	public boolean deleteDepartment(Integer departmentId) throws Exception;

	public Department findDepartmentById(Integer departmentId) throws Exception;

}
