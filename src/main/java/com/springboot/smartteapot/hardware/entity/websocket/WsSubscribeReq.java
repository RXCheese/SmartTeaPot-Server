package com.springboot.smartteapot.hardware.entity.websocket;

public class WsSubscribeReq {

    /**
     * 设备did
     */
    private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
