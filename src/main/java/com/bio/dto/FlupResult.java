package com.bio.dto;

import java.io.Serializable;

public class FlupResult<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;

    private boolean success;

    private T data;

    private String error;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
