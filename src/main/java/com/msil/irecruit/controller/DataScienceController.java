package com.msil.irecruit.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.ParticipantService;

@Controller
public class DataScienceController {
	    @Autowired
		DataScienceService dataScienceService;
	    @Autowired
	    ParticipantService participantserviceImpl;
	    
	    @PostMapping({ "/datascience" })
	    @ResponseBody
	    private String getDealer(final HttpSession session, @RequestParam("accesskey") final String accesskey,
	    		@RequestParam("interviewReason") final String interviewReason,@RequestParam("reason") final String reason) {
	    	Optional<DataScience> dataScience=	dataScienceService.findByAccesskey(accesskey);
	    	if(dataScience.isPresent()) {
	    		dataScience.get().setReason(reason);
	    		dataScience.get().setInterviewStatus(interviewReason);
	    		dataScience.get().setStatus("2");
	    		//dataScience.get().setDataScienceReferenceId(reference);
	    		//dataScience.get().setDataSciencePrediction(prediction);
	    		
	    		dataScienceService.save(dataScience.get());
	    		participantserviceImpl.updateModiedDate(accesskey);
	    		
	    	}
	    	return "success";
	 }
}
