package com.bio.dao;

import com.bio.beans.Json;

/**
 * @Author: xyx
 * @Date: 2019-05-16 09:50
 * @Version 1.0
 */
public interface IJsonDao {

    public Json selectJsonByVersion(int version);

}
