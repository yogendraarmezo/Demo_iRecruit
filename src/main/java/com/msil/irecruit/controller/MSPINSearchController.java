package com.msil.irecruit.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msil.irecruit.dms.controller.DmsController;
import com.msil.irecruit.dms.payload.LoginKey;
import com.msil.irecruit.dms.payload.MSPINCursorPayload;
import com.msil.irecruit.dms.payload.MSPINSearch;
import com.msil.irecruit.dms.payload.PmcMspinAuth;
import com.msil.irecruit.dms.service.DesignationServiceDms;
import com.msil.irecruit.tc.entities.ModelOldMspin;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class MSPINSearchController {
	
	@Autowired
	private DesignationServiceDms desgDmsService;
	
	
	@GetMapping("/searchMspin")
	public String searchOldMspin(HttpSession session, Model model) {
		return "mspin-search";
	}
	
	@PostMapping("/searchMspinPro")
	public String findByMspin(@RequestParam String mspin, Model model, HttpSession session) {
		String message="", leavingDate = "", status="";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
		// Making API Calls and getting data by MSPIN
		String username="Administrator", password="m1cr0s0f+";
		DmsController dmsController = new DmsController();
		LoginKey loginKey = new LoginKey(username, password);
		ResponseEntity<String> responseEntity= dmsController.loginToDms(loginKey);
		System.out.println(responseEntity.getBody());
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		if(!jsonObject.has("Message")) {
			String authKey = jsonObject.getString("AuthKey");
			PmcMspinAuth pmcMspinAuth = new PmcMspinAuth("1", mspin, authKey);
			System.out.println("MSPIN Search :::: PMC "+pmcMspinAuth);
			ResponseEntity<String> responseEntity2= dmsController.searchMSPIN(pmcMspinAuth);
			MSPINSearch mspinSearch = new MSPINSearch();
			MSPINCursorPayload mspinCursor = new MSPINCursorPayload();
			JSONObject jsonObject2= new JSONObject(responseEntity2.getBody());
			if(!jsonObject2.has("Message")) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					mspinCursor = mapper.readValue(responseEntity2.getBody(), MSPINCursorPayload.class);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(!mspinCursor.getP_LIST_CURSOR().isEmpty()) {
					for(MSPINSearch mspinSearch2: mspinCursor.getP_LIST_CURSOR()) {
						mspinSearch=mspinSearch2;
					if(!mspinSearch2.getEMP_LEAVING_DATE().isEmpty()) {
						Date elDate=null;
						try {
							elDate = simpleDateFormat.parse(mspinSearch.getEMP_LEAVING_DATE());
							leavingDate=DataProccessor.dateToString(elDate);
							
						} catch (ParseException e) {
							e.printStackTrace();
						}
						Date currentDate = new Date();
						if (elDate.after(currentDate)) {
							status="Pending";
							message="The candidate had resigned from other dealership however his/her resignation has "
							+ "not been approved hence cannot be part of recruitment process.";
						}else if(elDate.before(currentDate)){
							message="The candidate is inactive hence can be considered for the recruitment process";
							status="Inactive";
						}
					}else{
						message="The candidate is already working with other Dealership hence cannot be part of recruitment process.";
						status="Active";
					}
					model.addAttribute("mName", mspinSearch.getFIRST_NAME()+" "+mspinSearch.getMIDDLE_NAME()+" "+mspinSearch.getLAST_NAME());
					model.addAttribute("emailID", mspinSearch.getEMP_EMAIL_ID());
					model.addAttribute("desgination", desgDmsService.getDesignationByCode(mspinSearch.getEMP_DESG_CD()).get().getDesignationDesc());
					model.addAttribute("empLeavingDate", leavingDate);
					model.addAttribute("MStatus", status);
				
					}
				}else {
					message="The searched MSPIN does not exist";
				
				}
		}
		}
		model.addAttribute("search_mspin", mspin);
		model.addAttribute("message", message);
		return "mspin-search";
	}
	
	@GetMapping("/searcOldhMspin")
	@ResponseBody
	public ModelOldMspin findByOldMspin(@RequestParam String mspin) {
		System.out.println("mspin............."+mspin);
		String message="", leavingDate = "", status="";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
		ModelOldMspin oldMspin = new ModelOldMspin();
		// Making API Calls and getting data by MSPIN
		String username="Administrator", password="m1cr0s0f+";
		DmsController dmsController = new DmsController();
		LoginKey loginKey = new LoginKey(username, password);
		ResponseEntity<String> responseEntity= dmsController.loginToDms(loginKey);
		System.out.println(responseEntity.getBody());
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		if(!jsonObject.has("Message")) {
			String authKey = jsonObject.getString("AuthKey");
			PmcMspinAuth pmcMspinAuth = new PmcMspinAuth("1", mspin, authKey);
			ResponseEntity<String> responseEntity2= dmsController.searchMSPIN(pmcMspinAuth);
			MSPINSearch mspinSearch = new MSPINSearch();
			MSPINCursorPayload mspinCursor = new MSPINCursorPayload();
			JSONObject jsonObject2= new JSONObject(responseEntity2.getBody());
			if(!jsonObject2.has("Message")) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					mspinCursor = mapper.readValue(responseEntity2.getBody(), MSPINCursorPayload.class);
				} catch (IOException e1) {e1.printStackTrace();}
				if(!mspinCursor.getP_LIST_CURSOR().isEmpty()) {
					for(MSPINSearch mspinSearch2: mspinCursor.getP_LIST_CURSOR()) {
					if(!mspinSearch2.getEMP_LEAVING_DATE().isEmpty()) {
							Date tempDate =null;
							String parseDate="";
							try {
								if(mspinSearch2.getVALIDITY_DL().length()>0) {
								 tempDate = simpleDateFormat.parse(mspinSearch2.getVALIDITY_DL());
								 parseDate=DataProccessor.dateToString(tempDate);
								}
							} catch (ParseException e) {e.printStackTrace();}
							
							oldMspin.setIdProof(mspinSearch2.getID_PROOF());
							oldMspin.setValidatyDl(parseDate);
							oldMspin.setQualification(mspinSearch2.getEMP_QUALIFICATION());
							oldMspin.setPrimeryLanguage(mspinSearch2.getPRIM_LANG_CD());
							oldMspin.setSecondaryLanguage(mspinSearch2.getSEC_LANG_CD());
							oldMspin.setMsg("2");
					}else{
						oldMspin.setMsg("0");
					}
					
					}
				}else {
					oldMspin.setMsg("1");
				
				}
		}
		}
		
		return oldMspin;
	}
}
