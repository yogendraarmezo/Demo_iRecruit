package com.msil.irecruit.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.PrarambhUser;

public interface PrarambhUserRepository extends JpaRepository<PrarambhUser, Long> {

	Optional<PrarambhUser> findByUsernameAndPassword(String username, String password);

	@Query("SELECT p FROM PrarambhUser p WHERE p.username =:username")
	Optional<PrarambhUser> findByUsername(String username);

}
