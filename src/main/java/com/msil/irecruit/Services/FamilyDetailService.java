package com.msil.irecruit.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.FamilyDetails;
import com.msil.irecruit.Repositories.FamilyDetailsRepo;

@Service
public class FamilyDetailService {

	private FamilyDetailsRepo familyDetailsRepo;
	
	private FamilyDetailService(FamilyDetailsRepo familyDetailsRepo) {
		this.familyDetailsRepo=familyDetailsRepo;
	}

	public void update(FamilyDetails familyDetails) {
		familyDetailsRepo.save(familyDetails);
	}
	
	public Optional<FamilyDetails> getByFid(long fid){
		return familyDetailsRepo.findByFid(fid);
	}
}
