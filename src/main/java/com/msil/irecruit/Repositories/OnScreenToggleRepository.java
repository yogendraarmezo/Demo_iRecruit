package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msil.irecruit.Entities.OnScreenToggle;

public interface OnScreenToggleRepository extends JpaRepository<OnScreenToggle, Long> {

	@Query("SELECT t FROM OnScreenToggle t WHERE t.adminId = :adminId")
	Optional<OnScreenToggle> getOneToggleByAdminId(@Param("adminId") Long adminId);

	@Query("SELECT t FROM OnScreenToggle t WHERE t.mspin = :mspin")
	Optional<OnScreenToggle> getOneToggleByMspin(String mspin);

	@Query("SELECT t FROM OnScreenToggle t WHERE t.adminId = :adminId AND t.adminRole = :adminRole")
	Optional<OnScreenToggle> findByAdminIdAndRole(@Param("adminId") Long adminId, @Param("adminRole") String adminRole);

	@Query("SELECT t FROM OnScreenToggle t WHERE (t.adminId = :adminId) AND (t.module = :module)")
	Optional<OnScreenToggle> getOneToggleByAdminModule(@Param("adminId") Long adminId, @Param("module") String module);
	//@Modifying
	//@Query("SELECT t FROM OnScreenToggle t WHERE")
	//void updateOneToggleColumnByColumnName(Long adminId, String module, String columnName, String status);
	
	
}
