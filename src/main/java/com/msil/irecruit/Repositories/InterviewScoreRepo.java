package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.InterviewScore;

@Repository
public interface InterviewScoreRepo extends JpaRepository<InterviewScore, Integer> {

	//@Query(" From InterviewScore WHERE accessKey =:access_key ")
	//@Query(value = "SELECT * FROM interview_score i WHERE i.access_key =access_key", nativeQuery = true)
	Optional<InterviewScore> findByAccessKey(String accessKey);

}
