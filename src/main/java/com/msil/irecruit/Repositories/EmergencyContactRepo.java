package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msil.irecruit.Entities.EmergencyContact;

public interface EmergencyContactRepo extends JpaRepository<EmergencyContact, Long> {
	
	
	Optional<EmergencyContact> findById(Long id);

}
