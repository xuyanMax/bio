package com.bio.exception;

import com.bio.enums.ResultEnum;

public class FlupException extends RuntimeException {

    private Integer code;

    public FlupException(ResultEnum resultEnum) {

        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();

    }

    public FlupException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
