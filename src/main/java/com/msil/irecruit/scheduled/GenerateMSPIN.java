package com.msil.irecruit.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.controller.DmsController;

@Service
public class GenerateMSPIN {

	@Autowired
	ParticipantServiceImpl participantService;
	@Autowired
	private DmsController dmsController;
	@Value("${Ap.dmsURL}")
	private String dmsURL;

	// @Scheduled(cron = "0 03 13 59 * ?")
	@Scheduled(cron = "0 0 * * * *")
	public void scheduleTaskUsingCronExpression() {

		List<ParticipantRegistration> partcipantList = participantService.getCandidateMPINnotGerate();
		System.out.println("Size......."+partcipantList.size());
		if (partcipantList.size() > 0) {
			System.out.println("Cal...update api.......");
			for (ParticipantRegistration p : partcipantList) {
				dmsController.generateMSPIN(p.getAccessKey());
			}
		}

	}
}
