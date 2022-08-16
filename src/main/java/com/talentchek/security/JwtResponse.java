package com.talentchek.security;

import java.util.List;

import com.talentchek.setup.roles.RolesMasterBean;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String empId;
	private List<RolesMasterBean> roles;
	private boolean success;
	private String message;
	private Integer defaultRoleId;
	private String defaultRole;
	
	private String companyCode;
	
	
	private String firstNameLastName;
	private boolean isRoleAdmin;

	
	//For File added by gokul
    private byte[] file;
    
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JwtResponse(String accessToken, String username, String empId, List<RolesMasterBean> roles,boolean success,String message,Integer defaultRoleId,String defaultRole,String companyCode,byte[] file,String firstNameLastName, boolean isRoleAdmin) {
		this.token = accessToken;
		this.username = username;
		this.setEmpId(empId);
		this.roles = roles;
		this.success = success;
		this.message = message;
		this.defaultRoleId = defaultRoleId;
		this.defaultRole = defaultRole;
		this.companyCode = companyCode;
		this.file = file;
		this.firstNameLastName = firstNameLastName;
		this.isRoleAdmin=isRoleAdmin;
	}
	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<RolesMasterBean> getRoles() {
		return roles;
	}

	public Integer getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(Integer defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public String getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getFirstNameLastName() {
		return firstNameLastName;
	}

	public void setFirstNameLastName(String firstNameLastName) {
		this.firstNameLastName = firstNameLastName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public boolean isRoleAdmin() {
		return isRoleAdmin;
	}

	public void setRoleAdmin(boolean isRoleAdmin) {
		this.isRoleAdmin = isRoleAdmin;
	}
}
