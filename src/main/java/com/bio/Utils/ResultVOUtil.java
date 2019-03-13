package com.bio.Utils;

import com.bio.dto.FlupResult;

public class ResultVOUtil {

    public static FlupResult success(Object object) {
        FlupResult result = new FlupResult();
        result.setData(object);
        result.setSuccess(true);
        result.setError("success");
        return result;
    }

    public static FlupResult success() {
        return success(null);
    }

    public static FlupResult failure(Integer code, String msg) {
        FlupResult result = new FlupResult();
        result.setSuccess(false);
        result.setError(msg);
        result.setCode(code);
        return result;

    }
}
