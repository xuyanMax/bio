package com.bio.service.impl;

import com.bio.beans.Json;
import com.bio.dao.IJsonDao;
import com.bio.service.IJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xyx
 * @Date: 2019-05-16 10:09
 * @Version 1.0
 */
@Service
public class JsonServiceImpl implements IJsonService {

    @Autowired
    private IJsonDao jsonDao;

    @Override
    public Json findJsonByVersion(int version) {
        return jsonDao.selectJsonByVersion(version);
    }
}
