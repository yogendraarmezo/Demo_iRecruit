package com.msil.irecruit.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;

@Service
public class OnHoldScheduler {

	
	@Autowired
	ParticipantServiceImpl participantserviceImpl;
	
	@Scheduled(cron="0 00 00 * * *")
	public void setOnHold() {
		participantserviceImpl.getParticipant();
	}
}
