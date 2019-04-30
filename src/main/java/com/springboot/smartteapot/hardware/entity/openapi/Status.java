package com.springboot.smartteapot.hardware.entity.openapi;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Status {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20,unique = true)
    private String updatedAt;

    @Column(length = 5)
    private String temperature;

    @Column(length = 5)
    private String heatintSwitch;

    @Column(length = 10)
    private String taste;

    @Column(length = 5)
    private String temp;

    @Column(length = 10)
    private String constantTimeRemainder;

    @Column(length = 20)
    private String constantTime;

    @Column(length = 5)
    private String heatingOrNot;

    @Column(length = 5)
    private String online;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

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

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Status{" +
                "  \"updatedAt\":" + updatedAt +
                ", \"temperature\":" + temperature +
                ", \"heatintSwitch\":" + heatintSwitch +
                ", \"taste\":\"" + taste + '\"' +
                ", \"temp\":" + temp +
                ", \"constantTimeRemainder\":" + constantTimeRemainder +
                ", \"constantTime:\":\"" + constantTime + "\""+
                ", \"heatingOrNot\":\"" + heatingOrNot + '\"' +
                ", \"online\":\"" + online + '\"' +
                '}';
    }
}
