package com.msil.irecruit.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.LocationDms;

public interface LocationDmsRepository extends JpaRepository<LocationDms, Long> {

	@Query("SELECT l FROM LocationDms l WHERE l.locationCode=:locationCode")
	Optional<LocationDms> findLocationByLocCode(String locationCode);

}
