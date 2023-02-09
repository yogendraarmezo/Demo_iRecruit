package com.msil.irecruit.Repositories;


import java.util.Date;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.Entities.Dealer;

public interface DealerRepository extends JpaRepository<Dealer,Long>{


	public Optional<Dealer> findByMspin(String mspin);
	public Optional<Dealer> findByMspinAndEmail(String mspin,String email);

	public Optional<Dealer> findByPassword(String password);
	
	public Optional<Dealer> findByMspinAndPassword(String mspin,String password);
	
	public Optional<Dealer> findById(long id);
	
	@Modifying
	@Query("UPDATE Dealer d SET d.email = :email WHERE d.id = :id")
	public void addEmailAddress(Long id, String email);	

	@Modifying
	@Query("UPDATE Dealer d SET d.password = :password WHERE d.mspin = :mspin")
	public void changePassword(String mspin, String password);

	@Modifying
	@Query("UPDATE Dealer d SET d.email = :email WHERE d.mspin = :mspin")
	public void changeEmail(String mspin, String email);
	
	@Modifying
	@Query("UPDATE Dealer d SET d.status = :status, deactivationDate =:deactivationDate WHERE d.id = :id")
	public void deactivateDealer(boolean status,Date deactivationDate, Long id);
	
	@Modifying
	@Query("UPDATE Dealer d SET d.dealerMapCode =:dealerMapCode   where id =:id")
	 public int updateDealer(Long id,String dealerMapCode);
	
	
	
	
	
	
	
	

}
