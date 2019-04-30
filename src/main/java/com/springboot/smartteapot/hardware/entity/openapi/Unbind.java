package com.springboot.smartteapot.hardware.entity.openapi;

import java.util.List;

public class Unbind {

    private List<String> failed;
    private List<String> success;

    public List<String> getFailed() {
        return failed;
    }

    public void setFailed(List<String> failed) {
        this.failed = failed;
    }

    public List<String> getSuccess() {
        return success;
    }

    public void setSuccess(List<String> success) {
        this.success = success;
    }
}
