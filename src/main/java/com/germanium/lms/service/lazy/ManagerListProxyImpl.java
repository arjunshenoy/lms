package com.germanium.lms.service.lazy;

import java.util.List;

import com.germanium.lms.model.dto.Manager;

public class ManagerListProxyImpl implements ManagerList {

	private ManagerList managerList;
	@Override
	public List<Manager> getManagerList(String departmentName) {
		if (managerList == null) {
            System.out.println("Fetching list of employees");
            managerList = new ManagerListImpl();
        }
        return managerList.getManagerList(departmentName);
	}

}
