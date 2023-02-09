package com.msil.irecruit.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msil.irecruit.Entities.EmailTemplate;



public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Integer>{
Optional<EmailTemplate> findById(int id);
	
	@Query(" From EmailTemplate  where  status =:status ")
	List<EmailTemplate> getEmailBody(@Param("status")boolean status);
}
