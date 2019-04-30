package com.springboot.smartteapot.hardware.entity.openapi;

public class GizwitsUser {


    /**
     * token : f8324047f20144f6914e7be19304f943
     * uid : f082f4e235974cfeb6a1b40a6024f47e
     * expire_at : 1504772734
     */

    private String token;
    private String uid;
    private Integer expire_at;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(Integer expire_at) {
        this.expire_at = expire_at;
    }
}
