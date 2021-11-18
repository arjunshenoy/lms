package com.germanium.lms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.germanium.lms.model.Department;
import com.germanium.lms.service.IDepartmentService;

@RestController
@RequestMapping(value = "/api/v1/department" , produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

	Logger logger = LoggerFactory.getLogger(LeaveController.class);
	
	@Autowired
	IDepartmentService departmentService;
	
	@PostMapping()
	public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
		logger.info("Request received for creating department");
		Department departmentDetails = departmentService.createDepartment(department);
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION).body(departmentDetails);		
	}
	
	@GetMapping()
	public ResponseEntity<List<Department>> getDepartment() {
		logger.info("Request received for getting department details");
		return ResponseEntity.ok().body(departmentService.getDepartments());
	}
	
	@GetMapping("{departmentId}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("departmentId") Integer departmentId) throws Exception {
		logger.info("Request received for getting details of department ID : {}", departmentId);
		return ResponseEntity.ok().body(departmentService.findDepartmentById(departmentId));
	}
	
	@PutMapping("{departmentId}")
	public ResponseEntity<?> updateDepartment (@PathVariable("departmentId") final Integer departmentId,
			@Valid @RequestBody Department department) throws Exception{
		logger.info("Update request received for department ID : {}", departmentId);
		departmentService.updateDepartment(departmentId, department);		
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION).body("Department Details updated Successfully");
		
	}
	
	@DeleteMapping("{departmentId}")
	public ResponseEntity<?> deleteDepartment(@PathVariable("departmentId")Integer departmentId) throws Exception{
		logger.info("Delete request received for department ID : {}", departmentId);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION).body(departmentService.deleteDepartment(departmentId));
	}
}
