package com.bio.VO;

import java.io.Serializable;

/**
 * @Author: xyx
 * @Date: 2019-03-13 21:03
 * @Version 1.0
 */
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体内容.
     */
    private T data;

}
