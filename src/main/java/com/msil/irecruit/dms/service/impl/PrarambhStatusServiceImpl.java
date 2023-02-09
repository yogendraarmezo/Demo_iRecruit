package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.PrarambhStatus;
import com.msil.irecruit.dms.entities.PrarambhUser;
import com.msil.irecruit.dms.repository.PrarambhStatusRepository;
import com.msil.irecruit.dms.service.PrarambhStatusService;

@Service
public class PrarambhStatusServiceImpl implements PrarambhStatusService {
	@Autowired
	private PrarambhStatusRepository prarambhStatusRepository;

	@Override
	public void savePrarambhStatus(PrarambhStatus status) {
		prarambhStatusRepository.save(status);
	}

	@Override
	public List<PrarambhStatus> getAllPrarambhStatus() {
		return prarambhStatusRepository.findAll();
	}

	@Override
	public Optional<PrarambhStatus> getByMspin(String mspin) {
		return prarambhStatusRepository.findByMspin(mspin);
	}
	
	@Override
	public Optional<PrarambhUser> getByUsername(String username){
		return prarambhStatusRepository.findByUsername(username);
		
	}


}
