package com.schedule.dynamic.schedule.constant;

public enum JobStatusEnum {
    RUNNING("running");
    private String code;

    JobStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
