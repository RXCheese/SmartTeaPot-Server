package com.springboot.smartteapot.hardware.entity.openapi;

public enum TasteEnum {

    THICK("偏浓"),
    MODERATE("适中"),
    LIGHT("偏淡");


    private String value;

    TasteEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
