package com.talentchek.setup.rolerights;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.talentchek.core.util.BasicResultBean;

public class RoleRightsResultBean extends BasicResultBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public boolean success;
	private RoleRightsBean rolesMasterBean;
	private List<RoleRightsBean> rolesMasterDetails;
	private List<Map<String,Object>> formList;
	
	public List<Map<String, Object>> getFormList() {
		return formList;
	}
	public void setFormList(List<Map<String, Object>> formList) {
		this.formList = formList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public RoleRightsBean getRolesMasterBean() {
		return rolesMasterBean;
	}
	public void setRolesMasterBean(RoleRightsBean rolesMasterBean) {
		this.rolesMasterBean = rolesMasterBean;
	}
	public List<RoleRightsBean> getRolesMasterDetails() {
		return rolesMasterDetails;
	}
	public void setRolesMasterDetails(List<RoleRightsBean> rolesMasterDetails) {
		this.rolesMasterDetails = rolesMasterDetails;
	}

	
	
}
