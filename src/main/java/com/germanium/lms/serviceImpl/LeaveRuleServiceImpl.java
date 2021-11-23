package com.germanium.lms.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveRuleService;

@Service
public class LeaveRuleServiceImpl implements ILeaveRuleService {
	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	public boolean checkLeaveTypeRequestedForUserId(int leaveId, Integer userId) {
		Optional<LeaveRules> leaveDetails = leaveRulesRepo.findById(leaveId);
		if (leaveDetails.isEmpty()) {
			throw new ResourceNotFoundException("Leave with leave Id : " + leaveId + " not found");
		}
		LeaveStats leaveStats = leaveStatsRepo.findLeaveTypeByUserIdAndLeaveId(leaveDetails.get().getLeaveId(), userId);
		return leaveStats != null;
	}


	/*
	 * This method is used to reset the leave stats table. We are using a cron job
	 * that is executed 12 am everyday. Spring internally uses quartz scheduler
	 */
	@Scheduled(cron = "0 0 * * * *")
	public boolean resetLeaveStats() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		Optional<List<LeaveRules>> lapseLeaves = leaveRulesRepo.findByLapseDate(dateFormat.format(date));
		lapseLeaves.get().stream().forEach(leave -> {
			List<LeaveStats> leaveStats = leaveStatsRepo.findByIdLeaveId(leave.getLeaveId());
			leaveStats.stream().forEach(stats -> {
				if (stats.getLeaveCount() > leave.getCarryOverCount()) {
					stats.setLeaveCount(leave.getCarryOverCount() + leave.getLeaveCount());
				} else {
					stats.setLeaveCount(stats.getLeaveCount() + leave.getLeaveCount());
				}
			});
			leaveStatsRepo.saveAll(leaveStats);
		});
		return true;
	}
}
