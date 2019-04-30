package com.springboot.smartteapot.hardware.entity.openapi;

public enum ConstantTimeEnum {

    MIN2("2分钟"),
    MIN3("3分钟"),
    MIN4("4分钟"),
    MIN5("5分钟");

    private String value;

    ConstantTimeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

