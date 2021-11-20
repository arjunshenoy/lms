package com.germanium.lms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;

public interface ILeaveStatisticsRepository extends JpaRepository<LeaveStats, LeaveStatsId> {

	@Query("SELECT leaveStat FROM LeaveStats leaveStat WHERE leaveStat.id.employeeId = :id")
	List<LeaveStats> findByEmployeeId(@Param("id") Integer employeeId);
	
	@Query("SELECT leaveStat FROM LeaveStats leaveStat WHERE leaveStat.id.employeeId = :userId and leaveStat.id.leaveId = :leaveId")
	LeaveStats findLeaveTypeByUserIdAndLeaveId(int leaveId, Integer userId);

	List<LeaveStats> findByIdLeaveId(int leaveId);
		
	}

	

