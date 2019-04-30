package com.springboot.smartteapot.repository.spec;


import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.bean.dto.AdminCondition;
import com.springboot.smartteapot.repository.support.STSpecification;
import com.springboot.smartteapot.repository.support.QueryWraper;

/**

 */
public class AdminSpec extends STSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
