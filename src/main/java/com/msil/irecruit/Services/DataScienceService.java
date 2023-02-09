package com.msil.irecruit.Services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Repositories.DataScienceRepository;

@Service
@Transactional
public class DataScienceService {

	@Autowired
	DataScienceRepository dataScienceRepository;	
	
	public DataScienceService(DataScienceRepository dataScienceRepository) {
		this.dataScienceRepository=dataScienceRepository;
	}
	
	public void save(DataScience dataScience) {
		 dataScienceRepository.save(dataScience);
	}
	
	public Optional<DataScience> findByAccesskey(String accesskey) {
		return dataScienceRepository.findByAccesskey(accesskey);
	}
	
	public void delete(DataScience dataScience) {
		 dataScienceRepository.delete(dataScience);
	}
}
