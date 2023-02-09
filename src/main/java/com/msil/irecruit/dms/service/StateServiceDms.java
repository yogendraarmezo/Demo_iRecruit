package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.StateDms;

public interface StateServiceDms {
	
	public void saveState(StateDms stateDms);
	
	public List<StateDms> getAllStates();

	public List<String> getAllStateCodes();

	public Optional<StateDms> getStateByStateCode(String state_CD);

}
