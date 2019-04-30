package com.springboot.smartteapot.bean.dto;

import com.springboot.smartteapot.hardware.entity.openapi.Status;

public class StatusCondition extends Status {

	private String type;

	private String data;

	private String queryTimeForm;

	private String queryTimeTo;

	public String getQueryTimeForm() {
		return queryTimeForm;
	}

	public void setQueryTimeForm(String queryTimeForm) {
		this.queryTimeForm = queryTimeForm;
	}

	public String getQueryTimeTo() {
		return queryTimeTo;
	}

	public void setQueryTimeTo(String queryTimeTo) {
		this.queryTimeTo = queryTimeTo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
