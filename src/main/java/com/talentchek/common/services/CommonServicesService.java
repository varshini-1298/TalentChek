package com.talentchek.common.services;

import java.util.HashMap;
import java.util.List;

import com.talentchek.core.util.DropDownList;
import com.talentchek.core.util.ResultResponse;
import com.talentchek.security.FcmtokenBean;
import com.talentchek.setup.users.UsersMasterBean;

public interface CommonServicesService {
	
	boolean validateUserName(String userName);
	
	void insertOtp(String userId,String emailId,String otp);
	
	HashMap<String, Object> validateOtp(String userId,String otp);
	
	UsersMasterBean getUserDetails(String empid);
	
	Integer getCountValue(String empid);

	List<DropDownList> getCompanyMasterList();

	List<DropDownList> getDebitMemoList(String companyId);
	
	List<DropDownList> getManufacturerList();
	
	HashMap<String, Object> forgotPassword(String userNameEmailId,String otpForForgotPassword);
	
	boolean validateUnique(String tableName,String columnName,String columnValue);

	boolean verify(String recaptchaResponse);

	List<DropDownList> getStateDropdownList();

	ResultResponse updateFcmToken(FcmtokenBean fcmtokenBean);

	
}