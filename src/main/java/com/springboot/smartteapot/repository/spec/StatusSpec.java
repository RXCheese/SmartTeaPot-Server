package com.springboot.smartteapot.repository.spec;

import com.springboot.smartteapot.bean.dto.StatusCondition;
import com.springboot.smartteapot.hardware.entity.openapi.Status;
import com.springboot.smartteapot.repository.support.QueryWraper;
import com.springboot.smartteapot.repository.support.STSpecification;


public class StatusSpec extends STSpecification<Status, StatusCondition> {

	public StatusSpec(StatusCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Status> queryWraper) {
		//addLikeConditionData(queryWraper, getCondition().getData(),getCondition().getType());
		addBetweenCondition(queryWraper,"queryTimeForm","queryTimeTo","updatedAt");
		addLikeCondition(queryWraper, "updatedAt");
		addLikeCondition(queryWraper, "heatintSwitch");
		addLikeCondition(queryWraper, "temperature");
		addLikeCondition(queryWraper, "temp");
		addLikeCondition(queryWraper, "constantTimeRemainder");
		addLikeCondition(queryWraper, "heatingOrNot");
		addLikeCondition(queryWraper, "online");
		addLikeCondition(queryWraper, "taste");
		addLikeCondition(queryWraper, "constantTime");

	}

}
