package com.springboot.smartteapot.hardware.entity.websocket;

public class WsLoginRes {

    /**
     * websocket登录结果，true成功，false失败
     */
    Boolean success;

    public WsLoginRes(){}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
