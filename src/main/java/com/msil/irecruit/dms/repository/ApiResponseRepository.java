package com.msil.irecruit.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.dms.entities.ApiResponse;


@Repository
public interface ApiResponseRepository extends JpaRepository<ApiResponse, Integer>{
	
	 
    @Query("SELECT a FROM ApiResponse a where a.accesskey =:accesskey AND a.apiName =:apiName ")
   	Optional<ApiResponse> getResponse(String accesskey,String apiName);
}
