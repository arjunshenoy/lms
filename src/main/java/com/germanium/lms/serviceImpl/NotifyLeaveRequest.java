package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.bridge.ISendAPI;
import com.germanium.lms.service.bridge.Notifier;

public class NotifyLeaveRequest extends Notifier {
	Leave leaveRequest;
    public NotifyLeaveRequest(String sub, ISendAPI sender, Leave leaveRequest) {
        super(sub, sender);
        this.leaveRequest = leaveRequest;
    }

	public String buildMsg() {
		String content = "Leave Application Request by " + leaveRequest.getEmployeeId() + " submitted successfully \n\n";
		content += "Details :\n";
		content += "Leave type :"+leaveRequest.getLeaveId()+"\n";
		content += "Leave Starts on :"+leaveRequest.getFromDate()+"\n";
		content += "Leave Ends on :"+leaveRequest.getToDate()+"\n";
		return content;
	}
}