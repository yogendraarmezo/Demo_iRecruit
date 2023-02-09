package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.DepartmentDms;
import com.msil.irecruit.dms.repository.DepartmentDmsRepository;
import com.msil.irecruit.dms.service.DepartmentDmsService;

@Service
public class DepartmentDmsServiceImpl implements DepartmentDmsService {

	@Autowired
	private DepartmentDmsRepository departmentRepository;
	@Override
	public void saveDepartmentDetails(DepartmentDms departmentDms) {
		departmentRepository.save(departmentDms);
	}

	@Override
	public List<DepartmentDms> findallDepartmentDms() {
		return departmentRepository.findAll();
	}

	@Override
	public Optional<DepartmentDms> findDepartmentByDpCode(String departmentCode) {
		return departmentRepository.findDepartmentByDepartmentCode(departmentCode);
	}

}
