package com.msil.irecruit.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msil.irecruit.client.CallQuestionReport;


@Service
public class UpdateQusestionReport {
	@Autowired
	CallQuestionReport callQuestionReport;
	

	// @Scheduled(cron = "0 03 13 59 * ?")
	@Scheduled(cron = "0 0 * * * *")
	public void scheduleTaskUsingCronExpression() {
        System.out.println("cal.question.....");
		callQuestionReport.callClient();

	}
}
