package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.DepartmentDms;

public interface DepartmentDmsService {
	
	public void saveDepartmentDetails(DepartmentDms departmentDms);
	
	public List<DepartmentDms> findallDepartmentDms();
	
	Optional<DepartmentDms> findDepartmentByDpCode(String departmentCode);


}
