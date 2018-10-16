package com.bio.service;

import com.bio.beans.Relative;

import java.util.List;

public interface IRelativeService {
    void addRelative(Relative relative);

    void removeRelativeByIdperson1AndIdperson2(Integer idperson1, Integer idperson2);

    List<Relative> findRelativesByIdperson1(Integer idperson1);

    Relative findRelativesByIdperson2(Integer idperson2);

}
