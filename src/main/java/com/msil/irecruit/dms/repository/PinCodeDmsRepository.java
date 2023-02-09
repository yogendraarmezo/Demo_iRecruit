package com.msil.irecruit.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.PinCode;

public interface PinCodeDmsRepository extends JpaRepository<PinCode, Long> {

	@Query("SELECT p.pinCode FROM PinCode p")
	List<Integer> getAllPinCodeOnly();

}
