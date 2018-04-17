package com.vidor.Baby.enums;

public enum ExceptionEnum {

    OK(0, "default.success"),
    UNKNOW_EXCEPTION(-1, "unknow.error"),
    PARAMTER_EXCEPTION(100, "parameter.error");

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
