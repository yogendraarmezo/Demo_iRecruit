package com.msil.irecruit.dms.service;

import java.util.List;
import java.util.Optional;

import com.msil.irecruit.dms.entities.TehsilVillage;

public interface TehsilVillageService {
	
	void saveAllTehsilVillage(List<TehsilVillage> tehsilVillages);
	void saveTehsilVillage(TehsilVillage tehsilVillage);
	Optional<TehsilVillage> getTehsilVillageByVillageCode(String villageCode);
	List<String> getVillageCodeByStateCode(String p_STATE_CD);
	
	List<Object> findByStateCode(String stateCode);
	public List<Object> findByTehsilCode(String tehsilCode);
	public List<TehsilVillage> getAllTehsilVillages();

}
