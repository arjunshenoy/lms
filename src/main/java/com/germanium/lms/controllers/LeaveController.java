package com.germanium.lms.controllers;

import java.util.List;
import javax.validation.Valid;
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

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.dto.LeaveRequestDto;
import com.germanium.lms.model.dto.Log;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.model.factory.LeaveFactory;
import com.germanium.lms.service.ILeaveService;

@RestController
@RequestMapping(value = "/api/v1/leave", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveController {

	@Autowired
	ILeaveService leaveService;

	@GetMapping("leaveType")
	public ResponseEntity<List<LeaveRules>> getLeaveRules() throws Exception {
		Log log = Log.getInstance();
		log.logger.info("Request for fetching leave rules received");
		return ResponseEntity.ok().body(leaveService.getLeaveRules());
	}

	@GetMapping("leaveType/{leaveId}")
	public ResponseEntity<LeaveRules> getLeavesById(@PathVariable("leaveId") Integer leaveId){
		Log log = Log.getInstance();
		log.logger.info("Request for fetching leaves for ID" + leaveId +  "received");
		return ResponseEntity.ok().body(leaveService.findLeavesById(leaveId));
	}

	@PostMapping("leaveType")
	public ResponseEntity<LeaveRules> createLeaveRules(@Valid @RequestBody LeaveRules leaveType) {
  	Log log = Log.getInstance();
		log.logger.info("Request for adding new leave received");
		LeaveRules leaveTypeDetails = leaveService.createLeaveRules(leaveType);
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION).body(leaveTypeDetails);
	}

	@PutMapping("leaveType/{leaveId}")
	public ResponseEntity<?> updateLeaveRules(@PathVariable("leaveId") final Integer leaveTypeId,
			@Valid @RequestBody LeaveRules leaveRule) throws Exception {
		Log log = Log.getInstance();
		log.logger.info("Request for updating leave rules received");
		leaveService.updateLeaveRules(leaveTypeId, leaveRule);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body("Leave type Updated Successfully");
	}

	@DeleteMapping(value = "leaveType/{leaveId}")
	public ResponseEntity<?> deleteLeaveRules(@PathVariable("leaveId") Integer leaveId) throws ResourceNotFoundException {
		Log log = Log.getInstance();
		log.logger.info("Delete request received for leave ID :" + leaveId);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body(leaveService.deleteLeaveRules(leaveId));
	}

	@GetMapping("leaveStats/{employeeId}")
	public ResponseEntity<List<LeaveStats>> getLeaveStatsById(@PathVariable("employeeId") Integer employeeId) {
		Log log = Log.getInstance();
		log.logger.info("Fetching Leave Stats details for employee Id:" + employeeId);
		return ResponseEntity.ok().body(leaveService.getLeaveStatsById(employeeId));
	}

	@PostMapping("leaveStats/{userId}")
	public ResponseEntity<Boolean> addLeaveStatsForNewUsers(@PathVariable("userId") final Integer userId) {
		Log log = Log.getInstance();
		log.logger.info("Adding leave stats for new Users");
		return ResponseEntity.status(HttpStatus.OK).body(leaveService.addLeaveStatsForNewUsers(userId));
	}

	@PostMapping("request")
	public void createLeaveRequest(@Valid @RequestBody LeaveRequestDto leaveRequest) {
		try {
			Leave leaveObject = LeaveFactory.getNewLeaveObject(leaveRequest);
			ActiveLeaves savedLeave = leaveService.createLeaveRequest(leaveObject);
			String autoApproval = leaveService.autoApproval(leaveObject);
			if (!autoApproval.equals("queue")) // if queued leave it in active leaves
				takeLeaveDecision(savedLeave.getLeaveRequestId(), autoApproval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("request/{leaveId}")
	public ResponseEntity<ActiveLeaves> getActiveLeavesById(@PathVariable Integer leaveId) {
		logger.info("Finding active leaves for leave {}", leaveId);
		return ResponseEntity.ok().body(leaveService.getActiveLeavesById(leaveId));
	}

	@PostMapping("request/{leaveId}/{decision}")
	public ResponseEntity<?> takeLeaveDecision(@PathVariable("leaveId") Integer leaveId,
			@PathVariable String decision) {
		try {
			return ResponseEntity.ok().body(leaveService.takeLeaveDecision(leaveId, decision));
		} catch (Exception e) {
			return ResponseEntity.ok().body(false);
		}

	}
	
	@PostMapping("cancelRequest/{leaveRequestId}/{cancelDecision}")
	public ResponseEntity<Boolean> cancelWithdrawLeave(@PathVariable("leaveRequestId") Integer leaveRequestId,
			@PathVariable("cancelDecision")  String cancelDecision) {
		Log log = Log.getInstance();
		log.logger.info("Request received for" + cancelDecision +" leave");
		try {
			return ResponseEntity.ok().body(leaveService.cancelWithdrawLeave(leaveRequestId, cancelDecision));
		} catch (Exception e) {
			return ResponseEntity.ok().body(false);
		}

	}

}
