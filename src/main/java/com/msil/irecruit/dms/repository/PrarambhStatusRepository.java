package com.msil.irecruit.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.msil.irecruit.dms.entities.PrarambhStatus;
import com.msil.irecruit.dms.entities.PrarambhUser;

public interface PrarambhStatusRepository extends JpaRepository<PrarambhStatus, Long> {

	Optional<PrarambhStatus> findByMspin(String mspin);

	@Query("SELECT p FROM PrarambhUser p WHERE p.username=:username")
	Optional<PrarambhUser> findByUsername(String username);

}
