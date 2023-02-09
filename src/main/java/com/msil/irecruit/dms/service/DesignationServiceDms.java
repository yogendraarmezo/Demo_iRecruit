package com.msil.irecruit.dms.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.dms.entities.DesignationDms;

public interface DesignationServiceDms {
	
	public void saveDesignation(DesignationDms designationDms);
	
	public List<DesignationDms> findAllDesignationDms();

	public Boolean checkDesignationAvailability(String designationCode);

	public Optional<DesignationDms> getDesignationByCode(String designationCode);
	
	public List<DesignationDms> getDesignationByDesignationCode(List<String> listDesignationCode);
	


}
