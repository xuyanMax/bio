package com.bio.service;

import com.bio.beans.Center;

import java.util.List;

public interface ICenterService {

    Center findPersonInCentersByCenterid(int idcenter);

    Center findPersonInCentersByIdperson(int idperson);

    List<Center> findNoCenters();
}
