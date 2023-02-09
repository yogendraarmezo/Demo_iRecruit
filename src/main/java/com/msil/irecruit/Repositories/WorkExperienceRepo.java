package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msil.irecruit.Entities.WorkExperience;

public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {

	Optional<WorkExperience> findByWid(Long wid);
}