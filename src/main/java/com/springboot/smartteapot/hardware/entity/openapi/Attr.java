package com.springboot.smartteapot.hardware.entity.openapi;

public class Attr {

    private String temperature;

    private String heatintSwitch;

    private String taste;

    private String temp;

    private String constantTimeRemainder;

    private String constantTime;

    private String heatingOrNot;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeatintSwitch() {
        return heatintSwitch;
    }

    public void setHeatintSwitch(String heatintSwitch) {
        this.heatintSwitch = heatintSwitch;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getConstantTimeRemainder() {
        return constantTimeRemainder;
    }

    public void setConstantTimeRemainder(String constantTimeRemainder) {
        this.constantTimeRemainder = constantTimeRemainder;
    }

    public String getConstantTime() {
        return constantTime;
    }

    public void setConstantTime(String constantTime) {
        this.constantTime = constantTime;
    }

    public String getHeatingOrNot() {
        return heatingOrNot;
    }

    public void setHeatingOrNot(String heatingOrNot) {
        this.heatingOrNot = heatingOrNot;
    }

    @Override
    public String toString() {
        return "{" +
                "\"temperature\":" + temperature +
                ", \"heatintSwitch\":'" + heatintSwitch +
                ", \"taste\":\"" + taste + '\"' +
                ", \"temp\":'" + temp +
                ", \"constantTimeRemainder\":'" + constantTimeRemainder +
                ", \"constantTime\":\"" + constantTime + '\"' +
                ", \"heatingOrNot\":\"" + heatingOrNot + '\"' +
                '}';
    }
}