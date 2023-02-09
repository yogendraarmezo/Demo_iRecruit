package com.msil.irecruit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.HO;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.QuestiwiseReport;
import com.msil.irecruit.Entities.UserLoginLog;
import com.msil.irecruit.Services.AccessKeyMasterService;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.HOService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.QuestiwiseReportService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.Services.UserLoginLogService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.service.impl.ListDmsServiceImpl;
import com.msil.irecruit.tc.entities.AssessmentReport;
import com.msil.irecruit.tc.entities.ModelProfile;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class ControllerTesting {

	 @Autowired
	 private DealerService dealerService;
	 @Autowired 
	 private FSDMService fSDMService;
	 @Autowired
	 ParticipantService participantService;
	 @Autowired
	 AccessKeyMasterService accessKeyMasterService;
	 @Autowired
	 CityService cityService;
	 @Autowired
	 StateService stateService;
	 @Autowired
	 InterviewScoreService interviewScoreService;
	 @Autowired
	 UserLoginLogService userLoginLogService;
	 @Autowired
	 OutletService outletService;	
	 @Autowired
	 private HOService hoService;
	 @Autowired
	private ListDmsServiceImpl listDmsServiceImpl;
	 
	 
		QuestiwiseReportService questiwiseReportService;
		@Autowired
		DesignationService designationService;
		
		
		@Autowired
		FSDMService fsdmservice;
		
		@Autowired
		private FSDMService fsdmService;
		
		@Autowired
		private RegionService regionService;
	// @Autowired
	// private OnScreenToggleUtil toggleUtil;
	 
	@Value("${Ap.assessmentURL}")
	private String assessmentURL;
	@Autowired
	RestTemplate restTemplate;
	
	//@PostMapping("/loginPro")
		@RequestMapping(value = "/loginApi", method = { RequestMethod.POST})
		@ResponseBody
		public String loginPro(@RequestParam("mspin")String user,@RequestParam("password")String password,
				HttpSession session) {
			  Optional<FSDM>fsdm = fSDMService.getByMspinAadPassword(user,password);
			  if(fsdm.isPresent()) { 
			
					return "Success";
				  }
			 
			Optional<Dealer>dealer  = dealerService.getByMspinAndPassword(user,password);
			if(dealer.isPresent()) {
				
				if (dealer.get().getEmail() != null  && dealer.get().getEmail()!="") {
				
				return "Success";
				}
			}
			Optional<HO>ho  = hoService.getByMspinAndPassword(user,password);
			if(ho.isPresent()) {
				
		      // return "redirect:viewAllParticapants"; 
				return "Success";
				
			}
			else {
			session.setAttribute("msg", "Invalid Login");
			return "Invalid Login";
			}
			//return "redirect:login";
		}
		
		@RequestMapping(value = "/questionWiseReport", method = { RequestMethod.POST})
		@ResponseBody
		public String getAssessmentReportTesting() {
			
			return getPaertcipant(participantService.getParticipantByDealerId());
		}
		private String getPaertcipant(List<ParticipantRegistration> partcipantList){
			final List<Designation> designations2 = designationService.getAll();
			final Map<String, String> designation = designations2.stream()
					.collect(Collectors.toMap(
							(Function<? super Designation, ? extends String>) Designation::getDesignationCode,
							(Function<? super Designation, ? extends String>) Designation::getDesignationName));
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<AssessmentReport> assessList = new ArrayList<>();
			for (ParticipantRegistration p : partcipantList) {
				AssessmentReport assess = new AssessmentReport();
				List<String> opList = new ArrayList<String>();
				String name = "";
				name = p.getFirstName();
				if (p.getMiddleName() != null && !p.getMiddleName().equals("")) {
					name += " " + p.getMiddleName();
				}
				name += " " + p.getLastName();
				assess.setName(name);
				assess.setDesignationCode(p.getFinalDesignation());
				assess.setDesignationDesc(designation.get(p.getFinalDesignation()));
				assess.setMobile(p.getMobile());
				assess.setEmail(p.getEmail());
				assess.setAccesskey(p.getAccessKey());
				if(p.getRegistration_date() != null) {
				assess.setRegistrationDate(formatter.format(p.getRegistration_date()));
				}
				if(p.getTestCompletionDate() != null) {
				assess.setAssessmentDate(formatter.format(p.getTestCompletionDate()));
				}
				assess.setTotalMark(p.getTotalMark());
				assess.setMarkObtained(p.getTestScore());
				assess.setPercentage(p.getPercentageScore());
				if(p.getPassFailStatus() ==0) {
				assess.setAssessmentStatus("Fail");
				}
				if(p.getPassFailStatus() ==1) {
					assess.setAssessmentStatus("Pass");
					}
				Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(),
						p.getDealerId());
				if (outlet.isPresent()) {
					assess.setCity(outlet.get().getCity().getCityName());
					assess.setState(outlet.get().getState().getStateName());
					assess.setDealerCode(outlet.get().getOutletCode());
					assess.setDealerName(DataProccessor.getStringValue(outlet.get().getOutletName()));
					assess.setRegion(outlet.get().getRegion().getRegionCode());
				}
				//if(p != null &&  p.getAccessKey() != null) {
				//List<QuestiwiseReport> listReport = questiwiseReportService.getByAcesskey(p.getAccessKey());
				//for (QuestiwiseReport q : listReport) {
				//	opList.add(q.getOption());
				//}
				//}
				assess.setList(opList);
				assessList.add(assess);
			}
			String msg="Error";
			if(assessList.size()>0) {
				msg ="Success";	
			}
			return msg;
		}
		
		@RequestMapping(value = "/assessmentreport", method = { RequestMethod.POST})
		@ResponseBody
		public String getAssessment(@RequestParam("accesskey")String key) {
			String name="";
			String email="";
			String mobile="";
			String msg="Error";
			String url = assessmentURL +"player/viewAssessment.jsp?accesskey="+key+"&name="+name+"&email="+email+"&mobile="+mobile+"&testid=41&attemptid=1";
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			if(response.getBody().toString().length()>0) {
				msg="Success";
			}
			return msg;
		}
		
		@RequestMapping(value = "/answerReport", method = { RequestMethod.POST})
		@ResponseBody
		public String getAnswerRepor(@RequestParam("accesskey")String key) {
			String msg="Error";
			String url = assessmentURL +"pa/viewTest.do?accesskey="+key+"&name=&email=&testid=41&attemptid=1";
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			if(response.getBody().toString().length()>0) {
				msg="Success";
			}
			return msg;
		}
		
		@RequestMapping(value = "/questionAnalysis", method = { RequestMethod.POST})
		@ResponseBody
		public String getQuestionAnalysis() {
			
			String msg="Error";
			String url = assessmentURL +"admin/questionWiseReportTesting.jsp";
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			if(response.getBody().toString().length()>0) {
				msg="Success";
			}
			return msg;
		}
}
