package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.FSDM;

@Repository
public interface FSDMRepository extends JpaRepository<FSDM,Integer>{

	public Optional<FSDM> findByMspin(String mspin);
	public Optional<FSDM> findByMspinAndEmail(String mspin,String email);
	public Optional<FSDM> findByIdAndEmail(int id,String email);
	
	//@Query("SELECT  * from  FSDM   WHERE mspin = :mspin and password = :password ")
	public Optional<FSDM> findByMspinAndPassword(String mspin,String password);
	
	public Optional<FSDM> findByPassword(String password);
	
	public Optional<FSDM> findById(int id);

	@Modifying
	@Query("UPDATE FSDM f SET f.email = :email WHERE f.mspin = :mspin")
	public void addEmailAddressAfterLogin(String mspin, String email);
	
	@Modifying
	@Query("UPDATE FSDM f SET f.password = :password WHERE f.mspin = :mspin")
	public void changePasswordByMspin(String mspin, String password);

	@Modifying
	@Query("UPDATE FSDM f SET f.email = :email WHERE f.mspin = :mspin")
	public void changeEmail(String mspin, String email);
	
	@Modifying
	@Query("UPDATE FSDM f SET f.email = :email WHERE f.id = :id")
	public void changeEmailById( int id,String email);
	
	@Modifying
	@Query("UPDATE FSDM f SET f.password = :password WHERE f.id = :id")
	public void updatePassword( int id,String password);
}
