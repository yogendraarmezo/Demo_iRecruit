package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.StateCityPinDms;

public interface StateCityPinDmsService {
	
	public void saveStateCityPin(StateCityPinDms stateCityPinDms);
	
	public List<StateCityPinDms> getAllStateCityPinDms();
	
	public Optional<StateCityPinDms> getStateCityPinByPinCode(String pinCode);
	
	//Get All City by State Code/
	public List<StateCityPinDms> getStateCityListByStateCode(String stateCode);
	
	// Get All PinCodes By City Code
	public List<String> getAllPinCodesOfCityByCityCode(String cityCode);
	
	public List<Object> getAllState();
	
	public List<Object> getAllCity(String stateCode);
	public List<Object> getAllCity();
	

	public List<String> getPinByCity(String cityCode);
	public List<String> getPinByState(String stateCode);
	
	public Optional<String> getStateByStateCode(String stateCode);
	public Optional<String> getStateByCityCode(String cityCode);
	
	public Optional<String> getStateCodeByCityCode(String cityCode);
	
	

}
