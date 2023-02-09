package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.PrarambhStatus;
import com.msil.irecruit.dms.entities.PrarambhUser;

public interface PrarambhStatusService {
	
	public void savePrarambhStatus(PrarambhStatus status);
	
	public List<PrarambhStatus> getAllPrarambhStatus();
	
	Optional<PrarambhStatus> getByMspin(String mspin);

	Optional<PrarambhUser> getByUsername(String username);

}
