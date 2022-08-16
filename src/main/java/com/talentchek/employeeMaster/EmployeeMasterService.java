package com.talentchek.employeeMaster;

import java.util.Optional;

import com.talentchek.usermanagement.User;

public interface EmployeeMasterService {

	EmployeeMasterResultBean addEmployeeMaster(EmployeeMasterBean employeeMasterBean) throws Exception;
	
	Optional<User> getEmployeeUserName(String userName) throws Exception;
}
