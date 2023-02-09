package com.msil.irecruit.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.StateCityPinDms;

public interface StateCityPinDmsRepository extends JpaRepository<StateCityPinDms, Long> {

	@Query("SELECT s FROM StateCityPinDms s WHERE s.pinCode=:pinCode")
	Optional<StateCityPinDms> findStateCityPinByPinCode(String pinCode);
	
	@Query("SELECT s FROM StateCityPinDms s WHERE s.stateCode=:stateCode")
	List<StateCityPinDms> getStateCityListByStateCode(String stateCode);

	@Query("SELECT DISTINCT(s.cityDesc),cityCode FROM StateCityPinDms s WHERE s.stateCode=:stateCode Order By s.cityDesc")
	List<Object> getAllCity(String stateCode);
	
	@Query("SELECT  s.pinCode FROM StateCityPinDms s WHERE s.cityCode=:cityCode Order By s.pinCode ASC")
	List<String> getAllPinCodesOfCityByCityCode(String cityCode);
	
	@Query("SELECT DISTINCT  (s.stateDesc),s.stateCode FROM StateCityPinDms s  Order By s.stateDesc")
	List<Object> getAllState();
	
	@Query("SELECT DISTINCT  (s.cityDesc),cityCode FROM StateCityPinDms s  Order By s.cityDesc")
	List<Object> getAllCity();
	
	@Query("SELECT s.pinCode FROM StateCityPinDms s  WHERE s.cityCode=:cityCode Order By s.pinCode ASC")
	List<String> getPinByCity(String cityCode);
	
	@Query("SELECT s.pinCode FROM StateCityPinDms s  WHERE s.stateCode=:stateCode")
	List<String> getPinByState(String stateCode);
	
	@Query("SELECT DISTINCT(s.stateDesc) FROM StateCityPinDms s  WHERE s.stateCode=:stateCode")
	Optional<String> getStateByStateCode(String stateCode);
	
	@Query("SELECT DISTINCT(s.cityDesc) FROM StateCityPinDms s  WHERE s.cityCode=:cityCode")
	Optional<String> getStateByCityCode(String cityCode);
	
	@Query("SELECT DISTINCT(s.stateCode) FROM StateCityPinDms s  WHERE s.cityCode=:cityCode")
	Optional<String> getStateCodeByCityCode(String cityCode);
	
	

}
