package com.msil.irecruit.Services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Repositories.InterviewScoreRepo;

@Service
@Transactional
public class InterviewScoreService {

	@Autowired
	private InterviewScoreRepo interviewScoreRepo;
	
	public InterviewScore saveInterviewScore(InterviewScore interviewScore) {
		return interviewScoreRepo.save(interviewScore);
		
	}
	public Optional<InterviewScore> findByAccesskey(String accesskey) {
     return interviewScoreRepo.findByAccessKey(accesskey);		
	}
}

