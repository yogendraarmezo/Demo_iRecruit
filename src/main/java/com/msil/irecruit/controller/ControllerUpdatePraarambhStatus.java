package com.msil.irecruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Services.ParticipantService;

@Controller
public class ControllerUpdatePraarambhStatus {

	
	  @Autowired
	    ParticipantService participantserviceImpl;
	  
	  @GetMapping({ "/updatePraarambhStatus" })
	  @ResponseBody
	    private String getDealer(@RequestParam("mspin") String mspin) {
		  String msg="";
		 int result =  participantserviceImpl.updatePraaarambhtStatus(mspin);
		 if(result ==1) {
			 msg ="success"; 
		 }else {
			 msg ="error";  
		 }
		  return msg;
	  }
}
