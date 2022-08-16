package com.talentchek.formProperty;

import com.talentchek.core.util.CustomException;

public interface FormPropertyDao {
	public FormPropertyResultBean getFormProperty(Integer roleId) throws CustomException;
	
}
