package com.springboot.smartteapot.repository.spec;

import com.springboot.smartteapot.bean.dto.SharingCondition;
import com.springboot.smartteapot.bean.dto.StatusCondition;
import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;
import com.springboot.smartteapot.hardware.entity.openapi.Status;
import com.springboot.smartteapot.repository.support.QueryWraper;
import com.springboot.smartteapot.repository.support.STSpecification;


public class SharingSpec extends STSpecification<SharingConfig, SharingCondition> {

	public SharingSpec(SharingCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<SharingConfig> queryWraper) {
		addLikeCondition(queryWraper, "configName");
		addLikeCondition(queryWraper, "leaf");
		addLikeCondition(queryWraper, "author");
		addLikeCondition(queryWraper, "taste");
	}

}
