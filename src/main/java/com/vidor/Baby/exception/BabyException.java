package com.vidor.Baby.exception;

public class BabyException extends RuntimeException {

    private Integer code;

    private String msg;

    public BabyException() {
    }

    public BabyException(String msg) {
        this.msg = msg;
    }

    public BabyException(Integer code, String msg) {
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
