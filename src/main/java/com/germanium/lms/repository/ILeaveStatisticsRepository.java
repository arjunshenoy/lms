package com.germanium.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.germanium.lms.models.LeaveStats;
import com.germanium.lms.models.LeaveStatsId;

public interface ILeaveStatisticsRepository extends JpaRepository<LeaveStats, LeaveStatsId> {

}
