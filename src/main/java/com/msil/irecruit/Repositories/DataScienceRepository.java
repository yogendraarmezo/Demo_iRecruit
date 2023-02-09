package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.DataScience;

@Repository
public interface DataScienceRepository extends JpaRepository<DataScience,Integer>{

	public Optional<DataScience> findByAccesskey(String accesskey);
	
}
