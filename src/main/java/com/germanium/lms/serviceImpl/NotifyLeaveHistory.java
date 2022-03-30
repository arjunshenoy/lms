package com.germanium.lms.serviceImpl;

import java.util.Optional;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.service.bridge.ISendAPI;
import com.germanium.lms.service.bridge.Notifier;

public class NotifyLeaveHistory extends Notifier {
	String status;
	Optional<ActiveLeaves> optionalLeave;
	Optional<LeaveHistory> optionalLeaveHistory;
    public NotifyLeaveHistory(String sub, ISendAPI sender, Optional<ActiveLeaves> optionalLeave, Optional<LeaveHistory> optionalLeaveHistory, String status) {
        super(sub, sender);
        this.optionalLeave = optionalLeave;
        this.optionalLeaveHistory = optionalLeaveHistory;
        this.status = status;
    }
    
    
	public String buildMsg() {
		assert((optionalLeave!=null && optionalLeave.isPresent()) || (optionalLeaveHistory!=null && optionalLeaveHistory.isPresent()));
		int id = optionalLeave!=null ? optionalLeave.get().getEmployeeId() : optionalLeaveHistory.get().getLeaveHistoryId().getEmployeeId();
		String content = "Leave Application Request by " + id;
		content += " is " + this.status+ "\n\n";
		content += "Details :\n";
		content += "Leave type :"+ ((optionalLeave!=null) ? optionalLeave.get().getLeaveId() : optionalLeaveHistory.get().getLeaveId()) + "\n";
		content += "Leave Starts on :"+ ((optionalLeave!=null) ? optionalLeave.get().getFromDate() : optionalLeaveHistory.get().getFromDate())+"\n";
		content += "Leave Ends on :"+((optionalLeave!=null) ? optionalLeave.get().getToDate() : optionalLeaveHistory.get().getToDate())+"\n";
		return content;
	}
}

