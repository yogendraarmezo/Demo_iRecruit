package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.DesignationDms;
import com.msil.irecruit.dms.repository.DesignationDemRepository;
import com.msil.irecruit.dms.service.DesignationServiceDms;

@Service
@Transactional
public class DesignationDmsServiceImpl implements DesignationServiceDms {
	
	@Autowired
	private DesignationDemRepository designationDemRepository;
	
	public DesignationDmsServiceImpl(DesignationDemRepository designationDemRepository) {
		this.designationDemRepository=designationDemRepository;
	}

	@Override
	public void saveDesignation(DesignationDms designationDms) {
		designationDemRepository.save(designationDms);
	}

	@Override
	public List<DesignationDms> findAllDesignationDms() {
		return designationDemRepository.findAll();
	}

	@Override
	public Boolean checkDesignationAvailability(String designationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<DesignationDms> getDesignationByCode(String designationCode) {
		
		return designationDemRepository.findDesignationByCode(designationCode);
	}


	@Override
	public List<DesignationDms> getDesignationByDesignationCode(List<String> listDesignationCode) {
		return designationDemRepository.findByDesignationCodeIn(listDesignationCode);	
	}

}
