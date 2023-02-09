package com.msil.irecruit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msil.irecruit.Entities.OnScreenToggle;
import com.msil.irecruit.Services.OnScreenToggleService;

@Component
public class OnScreenToggleUtil {
	
	/* @Autowired 
	private OnScreenToggleService toggleService;
	
	// Save Toggle Data For First Time User Login For All Module
	public String saveToggleForFirstTime(HttpSession session) {
		if (session.getAttribute("role") != null) {
			OnScreenToggle toggle = new OnScreenToggle();
			String role = session.getAttribute("role").toString();
			Long adminId = 0L;
			List<String> moduleList = new ArrayList<String>();
			moduleList.add("In Progress");
			moduleList.add("On Hold");
			moduleList.add("Employee Master");
			if (role.equalsIgnoreCase("DL")) {
				adminId = Long.parseLong(session.getAttribute("userId").toString());
				toggle.setAdminRole("DL");
				moduleList.add("New Candidate");
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				adminId = Long.valueOf(fsdmId);
				toggle.setAdminRole("FS");
				moduleList.add("Pending For Approval");
			} else if (role.equalsIgnoreCase("HO")) {
				int hoId = Integer.parseInt(session.getAttribute("userId").toString());
				adminId = Long.valueOf(hoId);
				toggle.setAdminRole("HO");
				moduleList.add("Pending For Approval");
			} 
			
			for(String s : moduleList) {
				toggle.setAdminId(adminId);
				toggle.setModule(s);
				//Check For Existing Data
				Optional<OnScreenToggle> optional = toggleService.getOneScreenToggleByAdminIdAndModule(adminId, s);
				if(!optional.isPresent()) {
					toggle=saveToggleUtil(toggle);
					toggleService.saveScreenToggle(toggle);
				}
			}
			return "Success";
		}else {
			return "redirect:login";
		}
		
	
	}
	
	// Save Util On Different module
	private static OnScreenToggle saveToggleUtil(OnScreenToggle toggle) {
		toggle.setSrNo("1");
		toggle.setRegion("1");
		toggle.setCandidateName("1");
		toggle.setMspin("1");
		toggle.setAccesskey("1");
		toggle.setDesignation("1");
		toggle.setRegistrationDate("1");
		toggle.setMobileNumber("1");
		toggle.setAssessmentDate("1");
		toggle.setTotalMark("1");
		toggle.setObtainMark("1");
		toggle.setPercentage("1");
		toggle.setAssessmentStatus("1");
		toggle.setAssessmentReport("1");
		toggle.setDataScience("1");
		toggle.setInterviewDate("1");
		toggle.setInterviewScore("1");
		toggle.setRegistrationForm("1");
		toggle.setPraarambhStatus("1");
		toggle.setFinalDesignation("1");
		toggle.setFsdmApproval("1");
		toggle.setOnHold("1");
		toggle.setInterviewForm("1");
		toggle.setEmailId("1");
		toggle.setReactivate("1");
		toggle.setStatus("1");
		toggle.setAssessmentCompletionDate("1");
		toggle.setCommunication("1");
		toggle.setDesignationCode("1");
		toggle.setProfile("1");
		toggle.setDesignationDesc("1");
		return toggle;
	}
	
*/
}
