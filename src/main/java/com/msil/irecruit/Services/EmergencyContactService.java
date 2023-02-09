package com.msil.irecruit.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.EmergencyContact;
import com.msil.irecruit.Repositories.EmergencyContactRepo;

@Service
public class EmergencyContactService {
 
	private EmergencyContactRepo emergencyContactRepo;
	
	public EmergencyContactService(EmergencyContactRepo emergencyContactRepo) {
		this.emergencyContactRepo=emergencyContactRepo;
	}
	
	public void deleteById(Long id) {
		Optional<EmergencyContact>dmergencyContact = emergencyContactRepo.findById(id);
		if(dmergencyContact.isPresent()) {
		emergencyContactRepo.delete(dmergencyContact.get()); 
		}
	}
	
	public Optional<EmergencyContact> getEmergencyContact(Long id){
		return  emergencyContactRepo.findById(id);
	}
	
	public void update(EmergencyContact emergencyContact) {
		emergencyContactRepo.save(emergencyContact);
	}
}
