package com.springboot.smartteapot.hardware.entity.websocket;

public class WsEntity<T> {

    private String cmd;
    private T data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
