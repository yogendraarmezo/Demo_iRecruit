package com.msil.irecruit.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.dms.entities.DesignationDms;

@Repository
public interface DesignationDemRepository extends JpaRepository<DesignationDms, Long> {

	@Query("SELECT d FROM DesignationDms d WHERE d.designationCode=:designationCode")
	Optional<DesignationDms> findDesignationByCode(String designationCode);
	
	public List<DesignationDms> findByDesignationCodeIn(List<String> designationCode);
	
	@Query("SELECT d FROM DesignationDms d")
	public List<DesignationDms> getAll();

}
