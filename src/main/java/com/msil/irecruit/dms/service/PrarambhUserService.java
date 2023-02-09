package com.msil.irecruit.dms.service;

import java.util.Optional;

import com.msil.irecruit.dms.entities.PrarambhUser;

public interface PrarambhUserService {
	
	public void savePrarambhUser(PrarambhUser user);
	
	Optional<PrarambhUser> getByUsernameAndPassword(String username, String password);
	Optional<PrarambhUser> getByUsername(String username);
	Optional<PrarambhUser> getByPassword(String password);
	
	

}
