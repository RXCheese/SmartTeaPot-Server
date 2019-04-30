package com.springboot.smartteapot.hardware.entity.openapi;

import java.util.List;

public class DeviceInfo {

    /**
     * product_key : 4214bf2d79694a259232431b6f2ef46b
     * did : gKufzxZwYeyd3Skbsb6mza
     * mac : accf2350d446
     * is_online : false
     * passcode : JHHOOIWJBA
     * host : m2m.gizwits.com
     * port : 1883
     * port_s : 8883
     * ws_port : 8080
     * wss_port : 8880
     * remark : 备注信息
     * is_disabled : false
     * type : normal
     * dev_alias : 设备别名
     * dev_label : []
     * role : special
     */

    /**
     * 产品product_key
     */
    private String product_key;

    /**
     * 设备id
     */
    private String did;

    /**
     * 设备mac地址
     */
    private String mac;

    /**
     * 是否在线
     */
    private Boolean is_online;

    /**
     * 设备 passcode
     */
    private String passcode;

    /**
     * 连接服务器的域名
     */
    private String host;

    /**
     * M2M 的 mqtt 端口号
     */
    private Integer port;

    /**
     * M2M 的 mqtt SSL 端口号
     */
    private Integer port_s;

    /**
     * websocket 端口号
     */
    private Integer ws_port;

    /**
     * websocket SSL 端口号
     */
    private Integer wss_port;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 是否注销
     */
    private Boolean is_disabled;

    /**
     * 设备类型，单品设备:normal,中控设备:center_control,中控子设备:sub_dev
     */
    private String type;

    /**
     * 设备别名
     */
    private String dev_alias;

    /**
     * 设备标签列表，目前用于语音 API 批量设备控制
     */
    private String role;

    /**
     * 协议版本号，’01’, ‘01_01’, ‘03’, ‘04’
     */
    private String proto_ver;

    /**
     * wifi版本号
     */
    private String wifi_soft_version;

    /**
     * 是否连接sandbox环境
     */
    private Boolean is_sandbox;

    /**
     * 绑定角色， 特殊用户:special,拥有者:owner,访客:guest,普通用户:normal
     */
    private List<?> dev_label;

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Boolean getIs_online() {
        return is_online;
    }

    public void setIs_online(Boolean is_online) {
        this.is_online = is_online;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort_s() {
        return port_s;
    }

    public void setPort_s(Integer port_s) {
        this.port_s = port_s;
    }

    public Integer getWs_port() {
        return ws_port;
    }

    public void setWs_port(Integer ws_port) {
        this.ws_port = ws_port;
    }

    public Integer getWss_port() {
        return wss_port;
    }

    public void setWss_port(Integer wss_port) {
        this.wss_port = wss_port;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIs_disabled() {
        return is_disabled;
    }

    public void setIs_disabled(Boolean is_disabled) {
        this.is_disabled = is_disabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDev_alias() {
        return dev_alias;
    }

    public void setDev_alias(String dev_alias) {
        this.dev_alias = dev_alias;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProto_ver() {
        return proto_ver;
    }

    public void setProto_ver(String proto_ver) {
        this.proto_ver = proto_ver;
    }

    public String getWifi_soft_version() {
        return wifi_soft_version;
    }

    public void setWifi_soft_version(String wifi_soft_version) {
        this.wifi_soft_version = wifi_soft_version;
    }

    public Boolean getIs_sandbox() {
        return is_sandbox;
    }

    public void setIs_sandbox(Boolean is_sandbox) {
        this.is_sandbox = is_sandbox;
    }

    public List<?> getDev_label() {
        return dev_label;
    }

    public void setDev_label(List<?> dev_label) {
        this.dev_label = dev_label;
    }
}
