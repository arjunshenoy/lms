package com.germanium.lms.serviceImpl;

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.service.iterator.Iterator;
import com.germanium.lms.service.strategy.IStrategyEscalatePendingLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StrategyScheduler {

    @Autowired
    IActiveLeaveRepository activeLeaveRepository;

    @Autowired
    RestTemplate restTemplate;


    @Scheduled(cron = "* * * * * *")
    public void checkActiveLeavePending() {
        List<ActiveLeaves> pendingLeaves = (List<ActiveLeaves>) activeLeaveRepository.findAll();
        LocalDate datenow = LocalDate.now();

        if (pendingLeaves.isEmpty()) {
            System.out.println(pendingLeaves.size());
            throw new ResourceNotFoundException("No Active Leaves");
        }

        for (ActiveLeaves selectedLeave : pendingLeaves
        ) {
            System.out.println(selectedLeave.getFromDate());
            System.out.println(datenow.plusDays(5));
            long diffInMillies = Math.abs(selectedLeave.getFromDate().getTime() - System.currentTimeMillis());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
            if (diff==4) {
                System.out.println("first if");
                IStrategyEscalatePendingLeave notifyManager = new StrategyEscalateManager();
                notifyManager.checkPendingLeave(restTemplate,selectedLeave.getFromDate(),selectedLeave.getToDate(),selectedLeave.getEmployeeId());
            }
            else if (diff==3) {
                IStrategyEscalatePendingLeave notifySrManager = new StrategyEscalateManager();
                notifySrManager.checkPendingLeave(restTemplate,selectedLeave.getFromDate(),selectedLeave.getToDate(),selectedLeave.getEmployeeId());
            } else if (diff==2) {
                IStrategyEscalatePendingLeave notifyHr = new StrategyEscalateManager();
                notifyHr.checkPendingLeave(restTemplate,selectedLeave.getFromDate(),selectedLeave.getToDate(),selectedLeave.getEmployeeId());
            } else {
                System.out.println("Just write to log file");
            }
        }
    }
}
