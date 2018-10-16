package com.bio.service;

import com.bio.beans.Center;

public interface ICenterService {
    Center findPersonInCentersByCenterid(int idcenter);

    Center findPersonInCentersByIdperson(int idperson);
}
