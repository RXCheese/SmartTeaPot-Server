package com.springboot.smartteapot.hardware.entity.websocket;

public class WsLoginReq {


    /**
     * appid :
     * uid :
     * token :
     * p0_type :
     * heartbeat_interval : 1
     * auto_subscribe : true
     */

    private String appid;
    private String uid;
    private String token;
    private String p0_type;
    private Integer heartbeat_interval;
    private Boolean auto_subscribe;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getP0_type() {
        return p0_type;
    }

    public void setP0_type(String p0_type) {
        this.p0_type = p0_type;
    }

    public Integer getHeartbeat_interval() {
        return heartbeat_interval;
    }

    public void setHeartbeat_interval(Integer heartbeat_interval) {
        this.heartbeat_interval = heartbeat_interval;
    }

    public Boolean getAuto_subscribe() {
        return auto_subscribe;
    }

    public void setAuto_subscribe(Boolean auto_subscribe) {
        this.auto_subscribe = auto_subscribe;
    }
}
