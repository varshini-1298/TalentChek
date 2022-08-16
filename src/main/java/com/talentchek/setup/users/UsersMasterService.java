package com.talentchek.setup.users;

import java.util.List;

public interface UsersMasterService {

	UsersMasterResultBean save(UsersMasterBean bean) throws Exception;

	public List<UsersMasterBean> getList() throws Exception;

	UsersMasterResultBean edit(String usersId) throws Exception;

	UsersMasterResultBean delete(Integer code) throws Exception;
	
	UsersMasterResultBean update(UsersMasterBean bean) throws Exception;
	
	UsersMasterResultBean getRoleList() throws Exception;

	UsersMasterResultBean oldPasswordValidation(String validatePasswordvalidatePassword,String userId) throws Exception;

	UsersMasterResultBean updatePassword(UsersMasterBean bean) throws Exception;

	boolean resetPasswordCheck(String userId) throws Exception;

}