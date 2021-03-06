package com.bio.dao;

import com.bio.beans.Center;

import java.util.List;

public interface ICenterDao {

    public Center selectCenterIdById(int centerid);

    public Center selectCenterByIdperson(int idperson);

    public List<Center> selectCentersByNoCenters();

    public Center selectCenterByCenterName(String center);

    public List<Center> selectCentersBy1And2();
}
