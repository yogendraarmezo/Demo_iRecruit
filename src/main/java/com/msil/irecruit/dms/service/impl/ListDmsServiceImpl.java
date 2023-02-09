package com.msil.irecruit.dms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.repository.ListDmsRepository;
import com.msil.irecruit.dms.service.ListDmsService;

@Service
public class ListDmsServiceImpl implements ListDmsService {
	
	@Autowired
	private ListDmsRepository listDmsRepository;

	@Override
	public Optional<ListDms> getListByListCode(String listCode) {
		return listDmsRepository.findListDmsByListCode(listCode);
	}

	@Override
	public void saveList(ListDms listDms) {
		listDmsRepository.save(listDms);
	}

	@Override
	public List<ListDms> getAllListDms() {
		return listDmsRepository.findAll();
	}

	@Override
	public List<String> getBloodGroup(String listName) {
	
		return listDmsRepository.getBloodGroup(listName);
	}

	@Override
	public List<ListDms> getByListName(String listName) {
		return listDmsRepository.findByListName(listName);
	}

}
