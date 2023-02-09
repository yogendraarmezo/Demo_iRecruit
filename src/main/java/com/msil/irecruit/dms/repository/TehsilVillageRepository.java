package com.msil.irecruit.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.TehsilVillage;

public interface TehsilVillageRepository extends JpaRepository<TehsilVillage, Long> {

	@Query("SELECT t FROM TehsilVillage t WHERE t.villageCode = :villageCode")
	Optional<TehsilVillage> findByVillageCode(String villageCode);

	@Query("SELECT t.villageCode FROM TehsilVillage t WHERE t.stateCode =:stateCode")
	List<String> getTehsilVillageByVillageCode(String stateCode);
	
	@Query("SELECT DISTINCT(t.tehsilDesc),t.tehsilCode  FROM TehsilVillage t WHERE t.stateCode = :steteCode")
	public List<Object> findByStateCode(String steteCode);
	
	//@Query("SELECT t FROM TehsilVillage t WHERE t.tehsilCode = :tehsilCode")
	@Query("SELECT DISTINCT  (t.villageName),t.villageCode FROM TehsilVillage t WHERE t.tehsilCode = :tehsilCode")
	public List<Object> findByTehsilCode(String tehsilCode);

}
