package com.springboot.smartteapot.bean.dto;

import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;

public class SharingCondition extends SharingConfig {

	private String type;

	private String data;

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
