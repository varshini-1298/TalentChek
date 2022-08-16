package com.talentchek.employeeMaster;

import java.util.Optional;

import com.talentchek.core.util.CustomException;
import com.talentchek.usermanagement.User;

public interface EmployeeMasterDao {
	public EmployeeMasterResultBean addEmployeeMaster(EmployeeMasterBean objEmployee) throws CustomException;
	
	public Optional<User> getEmployeeUserName(String userName) throws CustomException;
}
