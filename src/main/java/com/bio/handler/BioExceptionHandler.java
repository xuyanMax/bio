package com.bio.handler;

import com.bio.Utils.ResultVOUtil;
import com.bio.dto.FlupResult;
import com.bio.exception.FlupException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xyx
 * @Date: 2019-03-13 20:01
 * @Version 1.0
 */

@ControllerAdvice
public class BioExceptionHandler {

    @ExceptionHandler(value = FlupException.class)
    @ResponseBody
    public FlupResult handlerBioException(FlupException e){
        return ResultVOUtil.failure(e.getCode(), e.getMessage());
    }

}
