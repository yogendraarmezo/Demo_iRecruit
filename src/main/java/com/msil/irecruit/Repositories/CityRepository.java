package com.msil.irecruit.Repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.Entities.City;
import com.msil.irecruit.Entities.State;

public interface CityRepository extends JpaRepository<City,Integer> {
	public Optional<City> findByCityNameAndCityCode(String cityName,String cityCode);
	public Optional<City> findByCityName(String cityName);
	
	@Query("SELECT c FROM City c WHERE c.stateCode IN :stateCodes")
	public List<City> findAllCityByStateCode(Collection<String> stateCodes);
	
	@Query("SELECT c FROM City c WHERE c.stateCode =:stateCodes")
	public List<City> findCityByStateCode(String stateCodes);
}
