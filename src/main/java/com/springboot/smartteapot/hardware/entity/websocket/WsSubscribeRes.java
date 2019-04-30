package com.springboot.smartteapot.hardware.entity.websocket;

import java.util.List;

public class WsSubscribeRes {

    private List<SuccessBean> success;
    private List<FailedBean> failed;


    public static class SuccessBean {
        /**
         * did :
         * error_code : 0
         * msg :
         */

        private String did;
        private Integer error_code;
        private String msg;
    }


    public static class FailedBean {
        /**
         * did :
         * error_code : 0
         * msg :
         */

        private String did;
        private Integer error_code;
        private String msg;
    }

    public WsSubscribeRes() {}

    public List<SuccessBean> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessBean> success) {
        this.success = success;
    }

    public List<FailedBean> getFailed() {
        return failed;
    }

    public void setFailed(List<FailedBean> failed) {
        this.failed = failed;
    }
}
