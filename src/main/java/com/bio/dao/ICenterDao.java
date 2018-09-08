package com.bio.dao;

import com.bio.beans.Center;

public interface ICenterDao {
    public Center selectCenterIdById(int centerid);
    public Center selectCenterByIdperson(int idperson);
}
