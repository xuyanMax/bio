package com.bio.enums;

public enum ResultEnum {

    SUCCESS(1, "success"),
    FAILURE(-1, "failure"),
    FACTOR_NOT_MATCH(10, "sql提取factor参数与要求不一致");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
