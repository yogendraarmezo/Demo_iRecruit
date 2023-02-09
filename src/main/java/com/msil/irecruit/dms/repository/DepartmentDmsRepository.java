package com.msil.irecruit.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.DepartmentDms;

public interface DepartmentDmsRepository extends JpaRepository<DepartmentDms, Long> {

	@Query("SELECT d FROM DepartmentDms d WHERE d.departmentCode=:departmentCode")
	Optional<DepartmentDms> findDepartmentByDepartmentCode(String departmentCode);

}
