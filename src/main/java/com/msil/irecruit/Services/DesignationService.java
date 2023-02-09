package com.msil.irecruit.Services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Repositories.DesignationRepository;

@Service
@Transactional
public class DesignationService {
	
	@Autowired
	DesignationRepository designationRepository;
	
	
	public List<Designation> getAll(){
		return designationRepository.findAll();
	}
	
	public List<String> getDesignation(){
		return designationRepository.getDesignation();
	}
	public List<String> getDesignationCategory(String category){
		return designationRepository.getDesignationCategory(category);
	}
	
	public List<Designation> getDesignationByProfile(String profile){
		return designationRepository.getDesignationByProfile(profile);
	}
	
	public Optional<Designation> getDesignationByCode(String designationCode){
		return designationRepository.getDesignationByCode(designationCode);
	}
	
	


}
