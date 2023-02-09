package com.msil.irecruit.dms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.ApiResponse;
import com.msil.irecruit.dms.repository.ApiResponseRepository;

@Service
public class ApiResponseService {

	@Autowired
	private ApiResponseRepository apiResponseRepository;
	
	public void save(ApiResponse apiResponse) {
		apiResponseRepository.save(apiResponse);
	}
	
	public Optional<ApiResponse> getResponse(String accesskey,String apiName){
		return apiResponseRepository.getResponse( accesskey,apiName);
	}
}
