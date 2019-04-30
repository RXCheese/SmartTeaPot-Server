package com.springboot.smartteapot.hardware.entity.websocket;

import java.util.List;

public class WsRead {

    /**
     * 设备did
     */
    private String did;

    /**
     * （变长数据点可选参数：传入需要读取的数据点名称，参数省略表示读取全部数据点；定长数据点读操作忽略该参数）
     */
    private List<String> names;

    public WsRead() {}

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
