package com.germanium.lms.controllers;

import java.util.List;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.germanium.lms.exception.LeaveServiceException;
import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.dto.LeaveRequestDto;
import com.germanium.lms.model.dto.LeaveRulesDto;
import com.germanium.lms.model.dto.Log;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.model.factory.LeaveFactory;
import com.germanium.lms.service.ILeaveService;

@RestController
@RequestMapping(value = "/api/v1/leave", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveController {

	@Autowired
	ILeaveService leaveService;
	
	@Autowired
    private ModelMapper modelMapper;

	Log log = Log.getInstance();

	@GetMapping("leaveType")
	public ResponseEntity<List<LeaveRules>> getLeaveRules() {
		log.logger.info("Request for fetching leave rules received");
		return ResponseEntity.ok().body(leaveService.getLeaveRules());
	}

	@GetMapping("leaveType/{leaveId}")
	public ResponseEntity<LeaveRules> getLeavesById(@PathVariable("leaveId") Integer leaveId) {
		log.logger.info(String.format("Request for fetching leaves for ID %d received", leaveId));
		return ResponseEntity.ok().body(leaveService.findLeavesById(leaveId));
	}

	@PostMapping("leaveType")
	public ResponseEntity<LeaveRules> createLeaveRules(@Valid @RequestBody LeaveRulesDto leaveTypeDto) {
		log.logger.info("Request for adding new leave received");
		LeaveRules leaveType = modelMapper.map(leaveTypeDto, LeaveRules.class);
		LeaveRules leaveTypeDetails = leaveService.createLeaveRules(leaveType);
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION).body(leaveTypeDetails);
	}

	@PutMapping("leaveType/{leaveId}")
	public ResponseEntity<?> updateLeaveRules(@PathVariable("leaveId") final Integer leaveTypeId,
			@Valid @RequestBody LeaveRulesDto leaveRuleDto) throws Exception {
		log.logger.info("Request for updating leave rules received");
		LeaveRules leaveRule = modelMapper.map(leaveRuleDto, LeaveRules.class);
		leaveService.updateLeaveRules(leaveTypeId, leaveRule);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body("Leave type Updated Successfully");
	}



	@DeleteMapping(value = "leaveType/{leaveId}")
	public ResponseEntity<Boolean> deleteLeaveRules(@PathVariable("leaveId") Integer leaveId)
			throws ResourceNotFoundException {
		log.logger.info(String.format("Delete request received for leave ID : %d", leaveId));
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body(leaveService.deleteLeaveRules(leaveId));
	}

	@GetMapping("leaveStats/{employeeId}")
	public ResponseEntity<List<LeaveStats>> getLeaveStatsById(@PathVariable("employeeId") Integer employeeId) {
		log.logger.info(String.format("Fetching Leave Stats details for employee ID %s" , employeeId));
		return ResponseEntity.ok().body(leaveService.getLeaveStatsById(employeeId));
	}

	@PostMapping("leaveStats/{userId}")
	public ResponseEntity<Boolean> addLeaveStatsForNewUsers(@PathVariable("userId") final Integer userId) {
		log.logger.info("Adding leave stats for new Users");
		return ResponseEntity.status(HttpStatus.OK).body(leaveService.addLeaveStatsForNewUsers(userId));
	}

	@PostMapping("request")
	public void createLeaveRequest(@Valid @RequestBody LeaveRequestDto leaveRequest) throws LeaveServiceException {

		Leave leaveObject = LeaveFactory.getNewLeaveObject(leaveRequest);
		ActiveLeaves savedLeave = leaveService.createLeaveRequest(leaveObject);
		String autoApproval = leaveService.autoApproval(leaveObject);
		if (!autoApproval.equals("queue")) // if queued leave it in active leaves
			takeLeaveDecision(savedLeave.getLeaveRequestId(), autoApproval);
	}

	@GetMapping("request/{leaveId}")
	public ResponseEntity<ActiveLeaves> getActiveLeavesById(@PathVariable Integer leaveId) {
		log.logger.info(String.format("Finding active leaves for leave ID %d", leaveId));
		return ResponseEntity.ok().body(leaveService.getActiveLeavesById(leaveId));
	}

	@PostMapping("request/{leaveId}/{decision}")
	public ResponseEntity<Boolean> takeLeaveDecision(@PathVariable("leaveId") Integer leaveId,
			@PathVariable String decision) {
		try {
			return ResponseEntity.ok().body(leaveService.takeLeaveDecision(leaveId, decision));
		} catch (LeaveServiceException e) {
			return ResponseEntity.ok().body(false);
		}

	}

	@PostMapping("cancelRequest/{leaveRequestId}/{cancelDecision}")
	public ResponseEntity<Boolean> cancelWithdrawLeave(@PathVariable("leaveRequestId") Integer leaveRequestId,
			@PathVariable("cancelDecision") String cancelDecision) {
		log.logger.info(String.format("Request received for %s leave", cancelDecision));
		try {
			return ResponseEntity.ok().body(leaveService.cancelWithdrawLeave(leaveRequestId, cancelDecision));
		} catch (Exception e) {
			return ResponseEntity.ok().body(false);
		}

	}
	
	@PostMapping("undo-decision/{leaveRequestId}")
	public ResponseEntity<Boolean> undoLeaveDecision(@PathVariable("leaveRequestId") Integer leaveRequestId) {
		try {
			return ResponseEntity.ok().body(leaveService.undoLeaveDecision(leaveRequestId));
		} catch (Exception e) {
			return ResponseEntity.ok().body(false);
		}

	}

}
