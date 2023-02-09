package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.LocationDms;
import com.msil.irecruit.dms.repository.LocationDmsRepository;
import com.msil.irecruit.dms.service.LocationDmsService;

@Service
public class LocationDmsServiceImpl implements LocationDmsService {
	
	@Autowired
	private LocationDmsRepository locationRepository;

	@Override
	public void saveLocation(LocationDms locationDms) {
		locationRepository.save(locationDms);
	}

	@Override
	public List<LocationDms> findAllLocationDms() {
		return locationRepository.findAll();
	}

	@Override
	public Optional<LocationDms> getLocationByLocCode(String locationCode) {
		return locationRepository.findLocationByLocCode(locationCode);
	}

}
