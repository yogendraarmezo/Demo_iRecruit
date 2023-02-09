package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.HO;

@Repository
public interface HORepository extends JpaRepository<HO, Integer> {
    Optional<HO> findByMspin(String mspin);
    Optional<HO> findByMspinAndEmail(String mspin,String email);
    Optional<HO> findByIdAndEmail(int id,String email);
    
    Optional<HO> findById(int id);
	
	@Query("SELECT h FROM HO h WHERE h.mspin=:mspin AND h.password=:password")
	Optional<HO> findHOByMspinAndPassword(String mspin, String password);

	@Modifying
	@Query("UPDATE HO h SET h.email = :email WHERE h.mspin = :mspin ")
	public void changeEmail(String mspin, String email);
	
	

	@Modifying
	@Query("UPDATE HO h SET h.password = :password WHERE h.mspin = :mspin ")
	public void changePassword(String mspin, String password);

}
