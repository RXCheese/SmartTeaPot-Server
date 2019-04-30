package com.springboot.smartteapot.hardware.entity.openapi;

public class LatestStatus {

    private String did;

    private String updated_at;

    private Attr attr;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "{" +
                "\"did\":\"" + did + '\"' +
                ", \"updated_at\":" + updated_at +
                ", \"attr\":" + attr.toString() +
                '}';
    }
}
