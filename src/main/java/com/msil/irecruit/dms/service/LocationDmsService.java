package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.LocationDms;

public interface LocationDmsService {
	
	public void saveLocation(LocationDms locationDms);
	
	public List<LocationDms> findAllLocationDms();

	public Optional<LocationDms> getLocationByLocCode(String loc_CD);

}
