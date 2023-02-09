package com.msil.irecruit.dms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.City;
import com.msil.irecruit.dms.repository.CityDmsRepository;
import com.msil.irecruit.dms.service.CityServiceDms;

@Service
public class CityServiceImplDms implements CityServiceDms {

	@Autowired
	private CityDmsRepository cityDmsRepository;
	@Override
	public void saveCity(City city) {
		cityDmsRepository.save(city);
	}

	@Override
	public List<City> getAllCity() {
		return cityDmsRepository.findAll();
	}

	@Override
	public List<String> getAllCityCodes() {
		return cityDmsRepository.getAllCityCodes();
	}

}
