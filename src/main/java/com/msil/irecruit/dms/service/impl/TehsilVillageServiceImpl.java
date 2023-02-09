package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.TehsilVillage;
import com.msil.irecruit.dms.repository.TehsilVillageRepository;
import com.msil.irecruit.dms.service.TehsilVillageService;

@Service
public class TehsilVillageServiceImpl implements TehsilVillageService {
	
	@Autowired
	private TehsilVillageRepository tehsilVillageRepository;

	@Override
	public void saveAllTehsilVillage(List<TehsilVillage> tehsilVillages) {
		tehsilVillageRepository.saveAll(tehsilVillages);
	}

	@Override
	public void saveTehsilVillage(TehsilVillage tehsilVillage) {
		tehsilVillageRepository.save(tehsilVillage);
	}

	@Override
	public Optional<TehsilVillage> getTehsilVillageByVillageCode(String villageCode) {
		return tehsilVillageRepository.findByVillageCode(villageCode);
	}

	@Override
	public List<String> getVillageCodeByStateCode(String stateCode) {
		return tehsilVillageRepository.getTehsilVillageByVillageCode(stateCode);
	}

	@Override
	public List<Object> findByStateCode(String stateCode) {
		return tehsilVillageRepository.findByStateCode(stateCode);
	}

	@Override
	public List<Object> findByTehsilCode(String tehsilCode) {
		// TODO Auto-generated method stub
		return tehsilVillageRepository.findByTehsilCode(tehsilCode);
	}

	@Override
	public List<TehsilVillage> getAllTehsilVillages() {
		// TODO Auto-generated method stub
		return tehsilVillageRepository.findAll();
	}

}
