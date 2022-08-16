package com.talentchek.employeeMaster;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentchek.core.util.CustomException;
import com.talentchek.usermanagement.User;

@Service
public class EmployeeMasterServiceImpl implements EmployeeMasterService{
	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Override
	public EmployeeMasterResultBean addEmployeeMaster(EmployeeMasterBean employeeMasterBean) throws CustomException {
		// TODO Auto-generated method stub
		return employeeMasterDao.addEmployeeMaster(employeeMasterBean);
	}

	@Override
	public Optional<User> getEmployeeUserName(String userName) throws Exception {
		// TODO Auto-generated method stub
		return employeeMasterDao.getEmployeeUserName(userName);
	}
	
}
