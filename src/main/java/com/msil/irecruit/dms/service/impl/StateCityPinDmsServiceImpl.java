package com.msil.irecruit.dms.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.StateCityPinDms;
import com.msil.irecruit.dms.repository.StateCityPinDmsRepository;
import com.msil.irecruit.dms.service.StateCityPinDmsService;

@Service
public class StateCityPinDmsServiceImpl implements StateCityPinDmsService {
	
	@Autowired
	private StateCityPinDmsRepository stateCityPinDmsRepository;

	@Override
	public void saveStateCityPin(StateCityPinDms stateCityPinDms) {
		stateCityPinDmsRepository.save(stateCityPinDms);
	}

	@Override
	public List<StateCityPinDms> getAllStateCityPinDms() {
		return stateCityPinDmsRepository.findAll();
	}

	@Override
	public Optional<StateCityPinDms> getStateCityPinByPinCode(String pinCode) {
		return stateCityPinDmsRepository.findStateCityPinByPinCode(pinCode);
	}

	@Override
	public List<StateCityPinDms> getStateCityListByStateCode(String stateCode) {
		return stateCityPinDmsRepository.getStateCityListByStateCode(stateCode);
	}

	@Override
	public List<String> getAllPinCodesOfCityByCityCode(String cityCode) {
		return stateCityPinDmsRepository.getAllPinCodesOfCityByCityCode(cityCode);
	}

	@Override
	public List<Object> getAllState() {
		
		return  stateCityPinDmsRepository.getAllState();
	}

	@Override
	public List<Object> getAllCity(String stateDesc) {
		return stateCityPinDmsRepository.getAllCity(stateDesc);
	}


	@Override
	public List<Object> getAllCity() {
		return stateCityPinDmsRepository.getAllCity();
	}

	@Override
	public List<String> getPinByCity(String cityCode) {
		return stateCityPinDmsRepository.getPinByCity(cityCode);
	}

	@Override
	public List<String> getPinByState(String stateCode) {
		return stateCityPinDmsRepository.getPinByState(stateCode);
	}

	@Override
	public Optional<String> getStateByStateCode(String stateCode) {
		return stateCityPinDmsRepository.getStateByStateCode(stateCode);
	}

	@Override
	public Optional<String> getStateByCityCode(String cityCode) {
		return stateCityPinDmsRepository.getStateByCityCode(cityCode);
	}

	@Override
	public Optional<String> getStateCodeByCityCode(String cityCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
