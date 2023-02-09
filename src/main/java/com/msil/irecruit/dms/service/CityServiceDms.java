package com.msil.irecruit.dms.service;

import java.util.List;

import com.msil.irecruit.dms.entities.City;

public interface CityServiceDms {
	
	public void saveCity(City cityList);
	
	public List<City> getAllCity();

	public List<String> getAllCityCodes();
	
	

}
