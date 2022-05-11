package com.pegasus.ams.mgmt.constant;

public enum ErrorCode {

    DUPLICATE_ID("001", "DUPLICATE_ID"),
    ROLE_IN_USED("006", "ROLE_IN_USED"),
    UNAUTHORIZED("007", "UNAUTHORIZED"),
    DUPLICATE_NAME("008", "DUPLICATE_NAME"),
    APPLICATION_ERROR("999", "APPLICATION_ERROR");

    private final String code;
    private final String key;

    ErrorCode(String code, String key) {
        this.code = code;
        this.key = key;
    }

    public String getCode() {
        return ProjectConstants.MODULE_CODE + code;
    }

    public String getKey() {
        return key;
    }
}