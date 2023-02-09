package com.msil.irecruit.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.dms.entities.ListDms;

public interface ListDmsRepository extends JpaRepository<ListDms, Long> {

	@Query("SELECT l FROM ListDms l WHERE l.listCode=:listCode")
	Optional<ListDms> findListDmsByListCode(String listCode);
	
	@Query("SELECT listCode FROM ListDms l WHERE l.listName=:listName")
	List<String> getBloodGroup(String listName);
	
	@Query("SELECT listCode FROM ListDms l WHERE l.listName=:listName")
	List<String> getFa(String listName);
	
	@Query("SELECT l FROM ListDms l WHERE l.listName=:listName ORDER BY listDesc ASC")
	List<ListDms> findByListName(String listName);
	

}
