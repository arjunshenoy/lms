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

import com.germanium.lms.models.LeaveRules;
import com.germanium.lms.models.LeaveStats;
import com.germanium.lms.service.ILeaveService;

@RestController
@RequestMapping(value = "/api/v1/leave", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveController {

	Logger logger = LoggerFactory.getLogger(LeaveController.class);

	@Autowired
	ILeaveService leaveService;

	@GetMapping("leaveType")
	public ResponseEntity<List<LeaveRules>> getLeaveRules() {
		return ResponseEntity.ok().body(leaveService.getLeaveRules());
	}

	@GetMapping("leaveType/{leaveId}")
	public ResponseEntity<LeaveRules> getLeavesById(@PathVariable("leaveId") Integer leaveId) throws Exception {
		return ResponseEntity.ok().body(leaveService.findLeavesById(leaveId));

	}

	@PostMapping("leaveType")
	public ResponseEntity<List<LeaveRules>> createLeaveRules(@Valid @RequestBody List<LeaveRules> leaveType) {
		List<LeaveRules> leaveTypeDetails = leaveService.createLeaveRules(leaveType);
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION).body(leaveTypeDetails);
	}

	@PutMapping("leaveType/{leaveId}")
	public ResponseEntity<?> updateLeaveRules(@PathVariable("leaveId") final Integer leaveTypeId,
			@Valid @RequestBody LeaveRules leaveRule) throws Exception {

		leaveService.updateLeaveRules(leaveTypeId, leaveRule);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body("Leave type Updated Successfully");
	}

	@DeleteMapping(value = "leaveType/{leaveId}")
	public ResponseEntity<?> deleteLeaveRules(@PathVariable("leaveId")Integer leaveId) throws Exception
	{
		logger.info("Delete request received for leave ID : {}", leaveId);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION).body(leaveService.deleteLeaveRules(leaveId));
	}
	
	@GetMapping("getLeaveStats/{employeeId}")
	public ResponseEntity<List<LeaveStats>> getLeaveStatsById(@PathVariable("employeeId") Integer employeeId) {
		logger.info("Fetching Leave Stats details for employee Id: " +employeeId);
		List<LeaveStats> lstats = leaveService.getLeaveStatsById(employeeId);
		System.out.println(lstats.get(0).getLeaveCount());
		return ResponseEntity.ok().body(lstats);
	}
}
