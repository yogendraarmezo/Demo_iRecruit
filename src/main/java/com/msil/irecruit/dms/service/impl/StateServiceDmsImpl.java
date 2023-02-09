package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.StateDms;
import com.msil.irecruit.dms.repository.StateDmsRepository;
import com.msil.irecruit.dms.service.StateServiceDms;

@Service
public class StateServiceDmsImpl implements StateServiceDms {

	@Autowired
	private StateDmsRepository stateRepository;
	@Override
	public void saveState(StateDms stateDms) {
		stateRepository.save(stateDms);
	}

	@Override
	public List<StateDms> getAllStates() {
		return stateRepository.findAll();
	}

	@Override
	public List<String> getAllStateCodes() {
		return stateRepository.getAllStateCodes();
	}

	@Override
	public Optional<StateDms> getStateByStateCode(String stateCode) {
		//return stateRepository.findStateByStateCode(stateCode);
		return null;
	}
	

}
