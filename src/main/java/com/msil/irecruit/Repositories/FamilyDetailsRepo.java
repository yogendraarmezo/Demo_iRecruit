package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msil.irecruit.Entities.FamilyDetails;

public interface FamilyDetailsRepo extends JpaRepository<FamilyDetails, Long> {

	Optional<FamilyDetails>findByFid(long fid);

}
