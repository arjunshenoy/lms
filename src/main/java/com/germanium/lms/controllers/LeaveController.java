package com.germanium.lms.controllers;

import java.util.ArrayList;
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
import com.germanium.lms.service.command.ICommand;
import com.germanium.lms.service.ILeaveUtilService;
import com.germanium.lms.serviceImpl.AutoApproveInvoker;
import com.germanium.lms.serviceImpl.LeaveServiceImpl;
import com.germanium.lms.serviceImpl.TurnOffAutoApproveCommand;
import com.germanium.lms.serviceImpl.TurnOnAutoApproveCommand;

@RestController
@RequestMapping(value = "/api/v1/leave", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeaveController {

	@Autowired
	ILeaveService leaveService;
	
	@Autowired
	ILeaveUtilService leaveutilService;

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	AutoApproveInvoker invoker;

	Log log = Log.getInstance();

	@GetMapping("leaveType")
	public ResponseEntity<List<LeaveRules>> getLeaveRules() {
		log.logger.info("Request for fetching leave rules received");
		return ResponseEntity.ok().body(leaveService.getLeaveRules());
	}

	@GetMapping("leaveType/{leaveId}")
	public ResponseEntity<LeaveRules> getLeavesById(@PathVariable("leaveId") Integer leaveId) {
		log.logger.info("Request for fetching leaves for ID: " + leaveId +"received");
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
		log.logger.info("Delete request received for leave ID : "+ leaveId);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.LOCATION)
				.body(leaveService.deleteLeaveRules(leaveId));
	}

	@GetMapping("leaveStats/{employeeId}")
	public ResponseEntity<List<LeaveStats>> getLeaveStatsById(@PathVariable("employeeId") Integer employeeId) {
		log.logger.info("Fetching Leave Stats details for employee ID: " + employeeId);
		return ResponseEntity.ok().body(leaveService.getLeaveStatsById(employeeId));
	}

	@PostMapping("leaveStats/{userId}")
	public ResponseEntity<Boolean> addLeaveStatsForNewUsers(@PathVariable("userId") final Integer userId) {
		log.logger.info("Adding leave stats for new Users");
		return ResponseEntity.status(HttpStatus.OK).body(leaveService.addLeaveStatsForNewUsers(userId));
	}
	
	@GetMapping("enableDisableAutoApprove/{button}")
	public void enableDisableAutoApprove(@PathVariable("button") String button){
		 
	    if (button.equalsIgnoreCase("on")) {
	    	invoker.setCommand(new TurnOnAutoApproveCommand(leaveutilService));
	 	    invoker.buttonPressed();
	    	
	    }
	    if (button.equalsIgnoreCase("off")) {
	    	invoker.setCommand(new TurnOffAutoApproveCommand(leaveutilService));
		    invoker.buttonPressed();
	    }
	    
	    if (button.equalsIgnoreCase("undo")) {
		    invoker.undoButton();
	    	
	    }
	}

	@PostMapping("request")
	public void createLeaveRequest(@Valid @RequestBody LeaveRequestDto leaveRequest) throws LeaveServiceException {

		Leave leaveObject = LeaveFactory.getNewLeaveObject(leaveRequest);
		ActiveLeaves savedLeave = leaveService.createLeaveRequest(leaveObject);
		String autoApproval = leaveutilService.autoApproval(leaveObject);
		if (!autoApproval.equals("queue")) // if queued leave it in active leaves
			takeLeaveDecision(savedLeave.getLeaveRequestId(), autoApproval);
	}

	@GetMapping("request/{leaveId}")
	public ResponseEntity<ActiveLeaves> getActiveLeavesById(@PathVariable Integer leaveId) {
		log.logger.info("Finding active leaves for leave ID :" + leaveId);
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
		log.logger.info("Request received for "+cancelDecision+" leave");
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

	@PostMapping("getsummary/{type}/{employeeId}")
	public ResponseEntity<String> getSummary(@PathVariable("employeeId") int employeeId, @PathVariable("type") String type) {
		try {
			return ResponseEntity.ok().body(leaveutilService.getSummary(employeeId, type));
		} catch (Exception e) {
			return ResponseEntity.ok().body("Error");
		}

	}
	
	@PostMapping("getmanager/{departmentName}")
	public ResponseEntity<List<?>> getManagers(@PathVariable("departmentName") String departmentName) {
		try {
			return ResponseEntity.ok().body(leaveService.getManagers(departmentName));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new ArrayList<>());
		}

	}
}
