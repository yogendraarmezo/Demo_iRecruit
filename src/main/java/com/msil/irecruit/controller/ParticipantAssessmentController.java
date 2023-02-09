package com.msil.irecruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.ParticipantAttemp;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Repositories.ParticipantAttemptRepository;
import com.msil.irecruit.Services.ParticipantService;

@Controller
public class ParticipantAssessmentController {

	@Autowired
	ParticipantAttemptRepository participantAttemptRepository;
	
	
	@Autowired
	ParticipantService participantService;
	    
	    
		
		
		
	@PostMapping("/instructions")
	public String showAssessment(ParticipantAttemp entity, ModelMap map) {
		
           return "redirect:https://demo.armezosolutions.com:8443/glenmark/pt/regIRC?txtAkey=58PU3K";
		
	}	

	
	//PayLoad for testing
	    
		
		
		
	/*@PostMapping("/instructions")
	public String showAssessment(ParticipantAttemp entity, ModelMap map) {
		
           //participantserviceImpl.startAssessment(entity);
           return "redirect:https://demo.armezosolutions.com:8443/glenmark/pt/regIRC?txtAkey=58PU3K";
		
	}*/	


	@PostMapping("/saveQuestion")
	@ResponseBody
	public String saveAssessmentQuestions(ParticipantAttemp entity) {
		
		participantService.saveAssessment(entity);

		return "Thank You, Your Assement is completed!!!";
	}
	
	
	
	/*Both two handler use for upload files and update files in db behalf of accessKey*/	
	@GetMapping("/uploadInstruction")
	public String uploadInstruction(@ModelAttribute("participantRegistration") @Validated ParticipantRegistration participantRegistration,
			BindingResult result ,
			@RequestParam("accesskey") String accesskey, Model model) {

		ParticipantRegistration partipant = participantService.findByAccesskey(accesskey).get();

		//ParticipantRegistration partipant = participantserviceImpl.findByAccesskey(accesskey);

		//Optional<ParticipantRegistration> partipant = participantserviceImpl.findByAccesskey(accesskey);

		model.addAttribute("participant", partipant);	
		return "upload-registrations";
	}	
	
	
	@PostMapping("/uploadfiles")
	@ResponseBody
     public	String uploadFiles(ParticipantRegistration participantRegistration,String accessKey) {
		
		ParticipantRegistration partupload= participantService.findByAccesskey(accessKey).get();
    	 participantService.saveFiles(partupload);
		//ParticipantRegistration partupload= participantserviceImpl.findByAccesskey(accessKey);
    	// participantserviceImpl.saveFiles(partupload);
    	 return "All Documents uploaded Successfully";    	 
     } 

}
