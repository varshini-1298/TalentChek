package com.talentchek.setup.rolerights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.talentchek.core.util.DropDownList;
import com.talentchek.employeeMaster.EmployeeMasterQueryUtil;
import com.talentchek.setup.users.UsersMasterQueryUtil;
import com.talentchek.setup.users.UsersMasterResultBean;

@Repository
public class RoleRightsDaoImpl implements RoleRightsDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public RoleRightsResultBean save(RoleRightsBean bean) throws Exception {
		RoleRightsResultBean resultBean = new RoleRightsResultBean();
		try {
			
			
			if(bean.getFormList().size()>0) {
				jdbcTemplate.update(RoleRightsQueryUtil.DELETE,bean.getRoleId());
				for(Map<String,Object> formCodeStr:bean.getFormList()) {
					int formPropertyId =  jdbcTemplate.queryForObject(RoleRightsQueryUtil.GET_FORM_PROP_ID,new Object[] {formCodeStr.get("item_id").toString()}, Integer.class);
					
					//int formPropertyId = jdbcTemplate.queryForObject("", new Object[] {},Integer.class );
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("roleId", bean.getRoleId());
					saveMap.put("formPropertyId", formPropertyId);
				//	saveMap.put("userName", bean.getUserName());
					namedParameterJdbcTemplate.update(RoleRightsQueryUtil.INSERT,saveMap);
				}
			}
			
			
			
		   resultBean.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			resultBean.setSuccess(false);
		}
		
		return resultBean;
	}

	@Override
	public List<RoleRightsBean> getList() throws Exception {
		List<RoleRightsBean> objCustomerMasterBean = new ArrayList<RoleRightsBean>();
		try {
			objCustomerMasterBean = jdbcTemplate.query(RoleRightsQueryUtil.getList, new BeanPropertyRowMapper<RoleRightsBean>(RoleRightsBean.class));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return objCustomerMasterBean;
	}

	@Override
	public RoleRightsResultBean edit(Integer code) throws Exception {
		RoleRightsResultBean resultBean = new RoleRightsResultBean();
		resultBean.setSuccess(false);
		try {
			resultBean.setRolesMasterBean(jdbcTemplate.queryForObject(RoleRightsQueryUtil.SELECT_DTL,new Object[] { code }, new BeanPropertyRowMapper<RoleRightsBean>(RoleRightsBean.class)));
			resultBean.setSuccess(true);
		}
		catch(Exception e) {
			e.printStackTrace();
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	@Override
	public RoleRightsResultBean delete(Integer cusCode) throws Exception {
		RoleRightsResultBean resultBean = new RoleRightsResultBean();
		try {
			if(cusCode!=null) {
				jdbcTemplate.update(RoleRightsQueryUtil.DELETE,cusCode);
			}
			resultBean.setSuccess(true);
		}
		catch(Exception e) {
			e.printStackTrace();
			resultBean.setSuccess(false);
		}	
		return resultBean;
	}

	@Override
	public RoleRightsResultBean update(RoleRightsBean bean) throws Exception {
		RoleRightsResultBean resultBean = new RoleRightsResultBean();
		try {
			Map<String, Object> customerMasterMap = new HashMap<String, Object>();
			    
				namedParameterJdbcTemplate.update(RoleRightsQueryUtil.UPDATE_CUSTOMER_MASTER,customerMasterMap);
			   resultBean.setSuccess(true);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultBean;
	}

	@Override
	public List<RoleRightsBean> getLoginRoleList(String userName) {
		List<RoleRightsBean> rolesBean = new ArrayList<RoleRightsBean>();
		try {
			rolesBean = jdbcTemplate.query(RoleRightsQueryUtil.getLoginRoleList,new Object[] { userName }, new BeanPropertyRowMapper<RoleRightsBean>(RoleRightsBean.class));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return rolesBean;
	}

	@Override
	public RoleRightsResultBean getFormList(Integer roleId) throws Exception {
		RoleRightsResultBean resultBean =new RoleRightsResultBean ();
		resultBean.setSuccess(false);
		try {
			if(roleId!=0) {
				resultBean.setFormList(jdbcTemplate.queryForList(RoleRightsQueryUtil.GET_ROLE_BASED_FORM_LIST,new Object[] { roleId }));
			}else {
				resultBean.setFormList(jdbcTemplate.queryForList(RoleRightsQueryUtil.GET_FORM_LIST));
			}
			
			resultBean.setSuccess(true);
			
		}catch(Exception e){
			e.printStackTrace();
			resultBean.setSuccess(false);
		}
		return resultBean;
	}


}
