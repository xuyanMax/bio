package com.bio.exception;

import com.bio.enums.ResultEnum;

public class FlupException extends RuntimeException {

    private Integer code;

    public FlupException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
