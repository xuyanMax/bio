package com.bio.service;

import com.bio.beans.Json;

/**
 * @Author: xyx
 * @Date: 2019-05-16 10:09
 * @Version 1.0
 */
public interface IJsonService {

    public Json findJsonByVersion(int version);
}
