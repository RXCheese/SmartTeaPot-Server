package com.springboot.smartteapot.hardware.entity.websocket;

public class WsInvalidMsg {

    /**
     * 错误码
     */
    private Integer error_code;

    /**
     * 描述文本
     */
    private String msg;

    public WsInvalidMsg(){}

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
