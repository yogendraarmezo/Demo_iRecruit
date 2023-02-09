package com.msil.irecruit.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msil.irecruit.dms.entities.StateDms;

public interface StateDmsRepository extends JpaRepository<StateDms, Long> {

	@Query("SELECT s.stateCode FROM StateDms s")
	List<String> getAllStateCodes();

	//@Query("SELECT s FROM State s WHERE s.stateCode:=stateCode")
	//Optional<StateDms> findStateByStateCode(@Param("stateCode") String stateCode);

}
