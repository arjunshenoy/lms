package com.germanium.lms.service.lazy;

import java.util.List;

import com.germanium.lms.model.dto.Manager;

public interface ManagerList {
	public List<Manager> getManagerList(String departmentName);
}
