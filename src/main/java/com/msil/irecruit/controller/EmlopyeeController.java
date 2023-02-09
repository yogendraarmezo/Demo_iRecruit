package com.msil.irecruit.controller;

import java.text.ParseException;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParentDealerService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.client.RestClientReattemp;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class EmlopyeeController {
	
	@Autowired
	ParticipantServiceImpl participantserviceImpl;
	@Autowired
	CityService cityService;
	@Autowired
	RegionService regionService;
	@Autowired
	FSDMService fsdmservice;
	@Autowired
	DealerService dealerService;
	@Autowired
	ParentDealerService parentDealerService;
	@Autowired
	StateService stateService;
	@Autowired
	DesignationService designationService;
	@Autowired
	OutletService outletService;	
	@Autowired
	InterviewScoreService interviewScoreService;
	@Autowired
	RestClientReattemp restClientReattemp;
	
	
	@GetMapping("/getEmployeeByDealer")
	public String getEmployeeDealer(HttpSession session, Model model) {
		List<ModelParticpantView> listParticipant = new ArrayList<>();
		
		if (session.getAttribute("userId") != null) {
			long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById(dealerId);
			final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
			Map<String, String> designation3 = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
			Map<String, String> SalesDesignation = designations2.stream().filter(p ->p.getCategory().equals("Sales")).collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<ParticipantRegistration> participant = participantserviceImpl.getEmployee(dealerId,"2");
			
		
			for (ParticipantRegistration p : participant) {
				if(p.getTestStatus() !=null && Integer.parseInt(p.getTestStatus() )== 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus())==2)) {
				ModelParticpantView modelParticpantView = new ModelParticpantView();
				modelParticpantView
						.setParticipantName(p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName());
				modelParticpantView.setAccesskey(p.getAccessKey());
				modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
				 if(p.getTestCompletionDate() != null) {
                 	modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                 }
				if(p.getTestStatus() != null) {
				modelParticpantView.setTestStatus(p.getTestStatus());
				modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));

				modelParticpantView.setPassFailStatus(Integer.valueOf(p.getPassFailStatus()));
				}else {
					modelParticpantView.setTestStatus("");
				}
				modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));	
				if(p.getPassFailStatus() != 0) {
				modelParticpantView.setPassFailStatus((p.getPassFailStatus()));
				}else {
					modelParticpantView.setPassFailStatus(0);	
				}
					
				modelParticpantView.setEmail(p.getEmail());
				modelParticpantView.setMobile(p.getMobile());
				
				if(p.getTestScore() != null) {
					modelParticpantView.setTestScore(p.getTestScore());	
				}else {
					modelParticpantView.setTestScore("0");	
				}
							
				if(p.getPercentageScore() != null) {
					modelParticpantView.setPercentageScore(p.getPercentageScore());
				}else {
					
						modelParticpantView.setPercentageScore("0");
					
				}
				if(p.getTotalMark() != null) {
					modelParticpantView.setTotalMark(p.getTotalMark());
				}else {
					modelParticpantView.setTotalMark("40");	
				}
				
				if(p.getInterviewDate() != null) {
					
				String	regDate = formatter.format(p.getInterviewDate());
				String  s =	regDate+" "+p.getInterviewTime();
				modelParticpantView.setInterViewDate(s);
				}else {
					modelParticpantView.setInterViewDate("");	
				}
				modelParticpantView.setInterViewStatus("N/A");
				modelParticpantView.setDesignation(p.getDesignation());
				if(p.getPrarambhStatus() == null) {
				modelParticpantView.setPrarambhStatus("N/A");
				}else {
					modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());	
				}
				if(p.getFsdmApprovalStatus() == null) {
					modelParticpantView.setFsdmApprovalStatus("Approval Awaited");
				}else if(p.getFsdmApprovalStatus().equals("1")) {
					modelParticpantView.setFsdmApprovalStatus("Feedback Given");
				}else if(p.getFsdmApprovalStatus().equals("2")) {
					modelParticpantView.setFsdmApprovalStatus("Approved");		
				}else if(p.getFsdmApprovalStatus().equals("3")) {
					modelParticpantView.setFsdmApprovalStatus("Approval Awaited");		
				}
				modelParticpantView.setOutletCode(DataProccessor.getStringValue(p.getOutletCode()));
				Optional<InterviewScore> interView = interviewScoreService.findByAccesskey(p.getAccessKey());
				if(interView.isPresent()) {
					modelParticpantView.setInterViewStatus(interView.get().getStatus());
					 modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
				} else {
                    modelParticpantView.setInterViewStatus("");
                    modelParticpantView.setInterViewPassFailStatus("");
                }
				
				
				if (dealer.isPresent()) {
					modelParticpantView.setDealerName(DataProccessor.getStringValue(dealer.get().getName()));	 
				}
				modelParticpantView.setMspin(DataProccessor.getStringValue(p.getMspin()));
				modelParticpantView.setPrarambhStatus(DataProccessor.getStringValue(p.getPrarambhStatus()));
				final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(),p.getDealerId());
	             if(outlet.isPresent()) {
	             	modelParticpantView.setRegionCode(outlet.get().getRegion().getRegionCode());
	             	modelParticpantView.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
	             	modelParticpantView.setDealerName(outlet.get().getOutletName());
	             	modelParticpantView.setOutletCode(outlet.get().getOutletCode());
	             	modelParticpantView.setCity(outlet.get().getCity().getCityName());
	             	modelParticpantView.setState(outlet.get().getState().getStateName());
	             }else {
	             	modelParticpantView.setRegionCode("");
	             	modelParticpantView.setParentDealer("");
	             	modelParticpantView.setDealerName("");
	             	modelParticpantView.setOutletCode("");
	             	modelParticpantView.setCity("");
	             	modelParticpantView.setState("");
	             }
	             if (p.getFinalDesignation() != null) {
                     modelParticpantView.setFinalDesignation((String)designation3.get(p.getFinalDesignation()));
                     modelParticpantView.setFinalDesignationCode(p.getFinalDesignation());
                 }
	             String fsdmReason ="";
	             if( p.getFsdmFeedback() != null && !p.getFsdmFeedback().equals("")) {
	          	   fsdmReason = p.getFsdmFeedback();
	             }
	             if(p.getFsdmRejectionType() != null && !p.getFsdmRejectionType().equals("")) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionType();  
	             }
	             if(p.getFsdmRejectionReason() != null &&! p.getFsdmRejectionReason().equals("")) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionReason();    
	             }
	             if(p.getFsdmRejectionComment() != null &&  !p.getFsdmRejectionComment().equals("") ) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionComment();      
	             }
	             modelParticpantView.setFsdmReason(fsdmReason);
				listParticipant.add(modelParticpantView);
				}
			}
			final List<Outlet> outlets = (List<Outlet>)this.outletService.findByDealerId(dealerId);
			final List<Designation> designations = (List<Designation>)this.designationService.getAll();
			model.addAttribute("outlets", (Object)outlets);
			model.addAttribute("designations", (Object)designations);
			model.addAttribute("dealerId", (Object)dealerId);
			model.addAttribute("salesDesignation", SalesDesignation);
			model.addAttribute("pass", DataProccessor.getPassFailStatusMap());

		}else {
			return "redirect:login";
		}
		model=DataProccessor.setDateRange(model);
		model.addAttribute("participantList", listParticipant);
		return "employeeDealer";
	}
	
	@GetMapping("/getEmployeeByFSDM")
	public String getEmployeeFSDM(HttpSession session, Model model) {
      List<ModelParticpantView> listParticipant = new ArrayList<>();
      List<Outlet> dealerCodeList = new ArrayList<Outlet>();
		
		if (session.getAttribute("userId") != null) {
			
			final List<Designation> designations2 = designationService.getAll();
	        final Map<String, String> designation = designations2.stream().collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
			
			int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<Long>list = new ArrayList<>();
			Optional<FSDM> f =	fsdmservice.getFSDM(fsdmId);
			for(Region r: f.get().getRegion()) {
				List<Outlet> outletList=	outletService.getOutletByRegion(r.getId());
				for(Outlet outlet: outletList) {
					dealerCodeList.add(outlet);
					list.add(outlet.getDealer().getId());
				}
			}			
			List<ParticipantRegistration> participant = participantserviceImpl.findByDealerIdEmployeeFSDM(list);		
			for (ParticipantRegistration p : participant) {
				if(p.getTestStatus() !=null && Integer.parseInt(p.getTestStatus() )== 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus())==2)) {
				ModelParticpantView modelParticpantView = new ModelParticpantView();				
				modelParticpantView
						.setParticipantName(p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName());
				modelParticpantView.setAccesskey(p.getAccessKey());
				modelParticpantView.setDesignation(p.getDesignation());
				if(p.getTotalMark() != null) {
					modelParticpantView.setTotalMark(p.getTotalMark());
				}else {
					modelParticpantView.setTotalMark("40");	
				}
				if(p.getTestScore() != null) {
					modelParticpantView.setTestScore(p.getTestScore());	
				}else {
					modelParticpantView.setTestScore(p.getAttitudeScore()+"");	
				}
				if(p.getPercentageScore() != null) {
					modelParticpantView.setPercentageScore(p.getPercentageScore());
				}else {
					if(p.getAttitudeScore() != null && p.getAttitudeScore()>0) {
						int  passingPer = (p.getAttitudeScore() / 40)*100;
						modelParticpantView.setPercentageScore(passingPer+"");
					}
				}
				
				modelParticpantView.setTestStatus(p.getTestStatus());
				modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));				
				modelParticpantView.setPassFailStatus(p.getPassFailStatus());
				if(p.getInterviewDate() != null) {					
					String	regDate = formatter.format(p.getInterviewDate());
					String  s =	regDate+" "+p.getInterviewTime();
					modelParticpantView.setInterViewDate(s);
				}else {
						modelParticpantView.setInterViewDate("");	
				}
				Optional<InterviewScore> interView = interviewScoreService.findByAccesskey(p.getAccessKey());
				if(interView.isPresent()) {
					modelParticpantView.setInterViewStatus(interView.get().getStatus());
					 modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
				} else {
                    modelParticpantView.setInterViewStatus("");
                    modelParticpantView.setInterViewPassFailStatus("");
                }
				modelParticpantView.setMspin(p.getMspin());
				modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());

				if(p.getFsdmApprovalStatus() == null) {
					modelParticpantView.setFsdmApprovalStatus("Approval Awaited");
				}else if(p.getFsdmApprovalStatus().equals("1")){
					modelParticpantView.setFsdmApprovalStatus("Feedback Given");	
				}else if(p.getFsdmApprovalStatus().equals("2")) {
					modelParticpantView.setFsdmApprovalStatus("Approved");		
				}
				else if(p.getFsdmApprovalStatus().equals("3")) {
					modelParticpantView.setFsdmApprovalStatus("Approval Awaited");		
				}
				modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
				 if(p.getTestCompletionDate() != null) {
                 	modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                 }
				modelParticpantView.setDealerCode(p.getOutletCode());
				modelParticpantView.setMobile(p.getMobile());
				final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(),p.getDealerId());
	             if(outlet.isPresent()) {
	             	modelParticpantView.setRegionCode(outlet.get().getRegion().getRegionCode());
	             	modelParticpantView.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
	             	modelParticpantView.setDealerName(outlet.get().getOutletName());
	             	modelParticpantView.setOutletCode(outlet.get().getOutletCode());
	             	modelParticpantView.setCity(outlet.get().getCity().getCityName());
	             	modelParticpantView.setState(outlet.get().getState().getStateName());
	             }else {
	             	modelParticpantView.setRegionCode("");
	             	modelParticpantView.setParentDealer("");
	             	modelParticpantView.setDealerName("");
	             	modelParticpantView.setOutletCode("");
	             	modelParticpantView.setCity("");
	             	modelParticpantView.setState("");
	             }
	             if (p.getFinalDesignation() != null) {
                     modelParticpantView.setFinalDesignation((String)designation.get(p.getFinalDesignation()));
                     modelParticpantView.setFinalDesignationCode(p.getFinalDesignation());
                 }
	             String fsdmReason ="";
	             if( p.getFsdmFeedback() != null && !p.getFsdmFeedback().equals("")) {
	          	   fsdmReason = p.getFsdmFeedback();
	             }
	             if(p.getFsdmRejectionType() != null && !p.getFsdmRejectionType().equals("")) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionType();  
	             }
	             if(p.getFsdmRejectionReason() != null &&! p.getFsdmRejectionReason().equals("")) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionReason();    
	             }
	             if(p.getFsdmRejectionComment() != null &&  !p.getFsdmRejectionComment().equals("") ) {
	          	   fsdmReason += " &#013 "+ p.getFsdmRejectionComment();      
	             }
	             modelParticpantView.setFsdmReason(fsdmReason);
				listParticipant.add(modelParticpantView);
				}
			}

		}else {
			return "redirect:login";
		}
		final List<Designation> designations3 = designationService.getAll();
        DataProccessor.setDateRange(model);
        model.addAttribute("participantList", (Object)listParticipant);
        model.addAttribute("dealerCodeList", dealerCodeList);
        model.addAttribute("designations", (Object)designations3);
        model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
		return "employeeFSDM";
	}
	
}
