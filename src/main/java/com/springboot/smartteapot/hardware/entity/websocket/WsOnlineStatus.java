package com.springboot.smartteapot.hardware.entity.websocket;


public class WsOnlineStatus {


    /**
     * did :
     * passcode :
     * mac :
     * online : true
     */

    private String did;
    private String passcode;
    private String mac;
    private Boolean online;

    public WsOnlineStatus() {
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
