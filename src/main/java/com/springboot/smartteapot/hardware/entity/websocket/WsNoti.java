package com.springboot.smartteapot.hardware.entity.websocket;

import java.util.Map;

public class WsNoti {

    /**
     * 设备did
     */
    private String did;

    /**
     * 标准数据点键值对
     */
    private Map<String, Object> attrs;

    public WsNoti(){}

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }
}


