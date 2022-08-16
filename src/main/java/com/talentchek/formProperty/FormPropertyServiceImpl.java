package com.talentchek.formProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentchek.core.util.CustomException;

@Service
public class FormPropertyServiceImpl implements FormPropertyService{
	@Autowired
	FormPropertyDao formPropertyDao;
	
	@Override
	public FormPropertyResultBean getFormProperty(Integer roleId) throws CustomException {
		// TODO Auto-generated method stub
		return formPropertyDao.getFormProperty(roleId);
	}

}
