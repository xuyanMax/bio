package com.bio.dao;

import com.bio.beans.Relative;

import java.util.List;

public interface IRelativeDao {

    void insertRelative(Relative relative);

    void deleteRelativeByIdperson1AndIdperson2(Integer ideperson1, Integer idperson2);

    void deleteRelativeByIdperson1(Integer ideperson1);

    List<Relative> selectRelativesByIdperson1(Integer idperson1);

    Relative selectRelativesByIdperson2(Integer idperson2);

}
