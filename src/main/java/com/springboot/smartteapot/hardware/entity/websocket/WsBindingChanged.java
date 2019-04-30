package com.springboot.smartteapot.hardware.entity.websocket;


public class WsBindingChanged {


    /**
     * did :
     * bind : true
     */

    private String did;
    private Boolean bind;

    public WsBindingChanged() {}

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Boolean getBind() {
        return bind;
    }

    public void setBind(Boolean bind) {
        this.bind = bind;
    }


}
