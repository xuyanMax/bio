package com.bio.dao;

import com.bio.beans.Center;

public interface ICenterDao {
    public int selectCenterIdById(int centerid);
    public Center selectCenterByIdperson(int idperson);
}
