package com.msil.irecruit.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Integer>{

	@Query("SELECT DISTINCT  (d.category) FROM Designation d  ")
	public List<String> getDesignation();
	
	@Query("SELECT   d.designationCode FROM Designation d where d.category =:category ")
	public List<String> getDesignationCategory(String category);
	
	@Query("SELECT   d FROM Designation d where d.recruitmentProfile =:profile ")
	public List<Designation> getDesignationByProfile(String profile);
	
	@Query("SELECT   d FROM Designation d where d.designationCode =:designationCode ")
	public Optional<Designation> getDesignationByCode(String designationCode);
}
