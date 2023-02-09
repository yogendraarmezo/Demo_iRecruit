package com.msil.irecruit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.payload.FilterPayload;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class ControllerFSDM {

	    @Autowired
	    ParticipantService participantserviceImpl;
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
	    
	    @GetMapping({ "/viewParticapant" })
	    private String getDealer(final HttpSession session, final Model model) {
	        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
	        Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
	        if (session.getAttribute("userId") != null) {
	            final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletList = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletList) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
	            final List<ParticipantRegistration> participant = participantserviceImpl.findByDealerIdInFSDM(list);
	            for (final ParticipantRegistration p : participant) {
	              //  if (p.getFsdmApprovalStatus() != null && (Integer.parseInt(p.getFsdmApprovalStatus()) == 1 || Integer.parseInt(p.getFsdmApprovalStatus()) == 3) && (p.getStatus() == null || !p.getStatus().equals("H"))) {
	                    final ModelParticpantView modelParticpantView = new ModelParticpantView();
	                    modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
	                    modelParticpantView.setAccesskey(p.getAccessKey());
	                    if (p.getRegistration_date() != null) {
	                        modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
	                    }
	                    if (p.getTestCompletionDate() != null) {
	                        modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
	                    }
	                    modelParticpantView.setEmail(p.getEmail());
	                    modelParticpantView.setMobile(p.getMobile());
	                    modelParticpantView.setDesignation(p.getDesignation());
	                    if (p.getTotalMark() != null) {
	                        modelParticpantView.setTotalMark(p.getTotalMark());
	                    }
	                    else {
	                        modelParticpantView.setTotalMark("40");
	                    }
	                    if (p.getTestScore() != null) {
	                        modelParticpantView.setTestScore(p.getTestScore());
	                    }
	                    else {
	                        modelParticpantView.setTestScore("");
	                    }
	                    if (p.getPercentageScore() != null) {
	                        modelParticpantView.setPercentageScore(p.getPercentageScore());
	                    }
	                    else {
	                        modelParticpantView.setPercentageScore("");
	                    }
	                    modelParticpantView.setTestStatus(p.getTestStatus());
	                    modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));
	                    modelParticpantView.setPassFailStatus(p.getPassFailStatus());
	                    if (p.getInterviewDate() != null) {
	                        final String regDate = formatter.format(p.getInterviewDate());
	                        final String s = String.valueOf(regDate) + " " + p.getInterviewTime();
	                        modelParticpantView.setInterViewDate(s);
	                    }
	                    else {
	                        modelParticpantView.setInterViewDate("");
	                    }
	                    final Optional<InterviewScore> interView = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(p.getAccessKey());
	                    if (interView.isPresent()) {
	                        modelParticpantView.setInterViewStatus(interView.get().getStatus());
	                        modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
	                    }
	                    modelParticpantView.setMspin(p.getMspin());
	                    modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());
	                    if (p.getFsdmApprovalStatus() == null) {
	                        modelParticpantView.setFsdmApprovalStatus("");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("1")) {
	                        modelParticpantView.setFsdmApprovalStatus("Rejected");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("2")) {
	                        modelParticpantView.setFsdmApprovalStatus("Approved");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("3")) {
	                        modelParticpantView.setFsdmApprovalStatus("Pending");
	                    }
	                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
	                    modelParticpantView.setDealerCode(p.getOutletCode());
	                    final Optional<Outlet> outlet2 = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
	                    if (outlet2.isPresent()) {
	                        modelParticpantView.setRegionCode(outlet2.get().getRegion().getRegionCode());
	                        modelParticpantView.setParentDealer(outlet2.get().getParentDealer().getParentDealerName());
	                        modelParticpantView.setDealerName(outlet2.get().getOutletName());
	                        modelParticpantView.setOutletCode(outlet2.get().getOutletCode());
	                        modelParticpantView.setCity(outlet2.get().getCity().getCityName());
	                        modelParticpantView.setState(outlet2.get().getState().getStateName());
	                    }
	                    else {
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
	            final List<Designation> designations3 = designationService.getAll();
	            DataProccessor.setDateRange(model);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations3);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "FSDM";
	        }
	        return "redirect:login";
	    }
	    
	    @GetMapping({ "/viewParticapantIprocess" })
	    private String getDealerInprocess(final HttpSession session, final Model model) {
	        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
	        Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
	        if (session.getAttribute("userId") != null) {
	            final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletLsit) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
	            final List<ParticipantRegistration> participant = participantserviceImpl.findByDealerIdInprocessFSDM(list);
	            for (final ParticipantRegistration p : participant) {
	                    final ModelParticpantView modelParticpantView = new ModelParticpantView();
	                    modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
	                    modelParticpantView.setAccesskey(p.getAccessKey());
	                    modelParticpantView.setDesignation(p.getDesignation());
	                    if (p.getRegistration_date() != null) {
	                        modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
	                    }
	                    if (p.getTestCompletionDate() != null) {
	                        modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
	                    }
	                    modelParticpantView.setEmail(p.getEmail());
	                    modelParticpantView.setMobile(p.getMobile());
	                    if (p.getTotalMark() != null) {
	                        modelParticpantView.setTotalMark(p.getTotalMark());
	                    }
	                    else {
	                        modelParticpantView.setTotalMark("40");
	                    }
	                    if (p.getTestScore() != null) {
	                        modelParticpantView.setTestScore(p.getTestScore());
	                    }
	                    else {
	                        modelParticpantView.setTestScore("");
	                    }
	                    if (p.getPercentageScore() != null) {
	                        modelParticpantView.setPercentageScore(p.getPercentageScore());
	                    }
	                    else {
	                        modelParticpantView.setPercentageScore("");
	                    }
	                    modelParticpantView.setTestStatus(p.getTestStatus());
	                    modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));
	                    modelParticpantView.setPassFailStatus(p.getPassFailStatus());
	                    if (p.getInterviewDate() != null) {
	                        final String regDate = formatter.format(p.getInterviewDate());
	                        final String s = String.valueOf(regDate) + ", " + p.getInterviewTime();
	                        modelParticpantView.setInterViewDate(s);
	                    }
	                    else {
	                        modelParticpantView.setInterViewDate("");
	                    }
	                    final Optional<InterviewScore> interView = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(p.getAccessKey());
	                    if (interView.isPresent()) {
	                        modelParticpantView.setInterViewStatus(interView.get().getStatus());
	                        modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
	                    }
	                    modelParticpantView.setMspin(p.getMspin());
	                    modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());
	                    if (p.getFsdmApprovalStatus() == null) {
	                        modelParticpantView.setFsdmApprovalStatus("");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("1")) {
	                        modelParticpantView.setFsdmApprovalStatus("Rejected");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("2")) {
	                        modelParticpantView.setFsdmApprovalStatus("Approved");
	                    }
	                    else if (p.getFsdmApprovalStatus().equals("3")) {
	                        modelParticpantView.setFsdmApprovalStatus("Pending");
	                    }
	                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
	                    modelParticpantView.setDealerCode(p.getOutletCode());
	                    final Optional<Outlet> outlet2 = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
	                    if (outlet2.isPresent()) {
	                        modelParticpantView.setRegionCode(outlet2.get().getRegion().getRegionCode());
	                        modelParticpantView.setParentDealer(outlet2.get().getParentDealer().getParentDealerName());
	                        modelParticpantView.setDealerName(outlet2.get().getOutletName());
	                        modelParticpantView.setOutletCode(outlet2.get().getOutletCode());
	                        modelParticpantView.setCity(outlet2.get().getCity().getCityName());
	                        modelParticpantView.setState(outlet2.get().getState().getStateName());
	                    }
	                    else {
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
	            
	            final List<Designation> designations3 = designationService.getAll();
	            DataProccessor.setDateRange(model);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations3);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	           
	            return "FSDMInprocess";
	        }
	        return "redirect:login";
	    }
	    
	    @PostMapping({ "/filterParticipantOnFSDM" })
	    public String dealerFilters(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation, @RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateRange") String dateRange, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			
			if(!dateRange.equals("Select Date")) {
			String[] splitDate=dateRange.split("-");
			try {
				dateFrom=sdf.parse(splitDate[0].trim());
				dateTo=sdf.parse(splitDate[1].trim());
				dateTo=DataProccessor.addTimeInDate(dateTo);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode="";
				if(candidateName==null)
					candidateName="";
				if(uniqueCode==null) {
					uniqueCode="";
				}
				if(designation==null)
					designation="";
				if(mspin==null)
					mspin="";
				List<Integer> passFStatus = new ArrayList<>();
				if(passFailStatus=="") {
						passFStatus.add(1);
						passFStatus.add(0);
					}else {
						passFStatus.add(Integer.valueOf(passFailStatus));
					}
				
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletLsit) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
				List<ParticipantRegistration> participantList=null;
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantByFilterDataForFSDM(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,list,dateFrom,dateTo);
				}else {
					participantList= participantserviceImpl.getParticipantByFilterDataForFSDM2(outletCode, candidateName, designation, mspin, passFStatus, list, uniqueCode);
				}
			    for (final ParticipantRegistration p : participantList) {
			    	
		            if (p.getTestStatus() != null && Integer.parseInt(p.getTestStatus()) == 3 && (p.getFsdmApprovalStatus() == null || Integer.parseInt(p.getFsdmApprovalStatus()) == 3 || Integer.parseInt(p.getFsdmApprovalStatus()) == 1) && (p.getStatus() == null || !p.getStatus().equals("H"))) {
		            	listParticipant.add(setDataToMPV(p));
		            }
			    }
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	            //Adding data to Search 
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,dateRange);
	            model.addAttribute("payload", filterPayload);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "FSDMInprocess";
	        }
	        return "redirect:login";
	    }
	    
	    // Filter For Pending Approval
	    @PostMapping({ "/filterParticipantOnFSDMPending" })
	    public String filterForFSDMInPending(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation,@RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateRange") String dateRange, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			
			if(!dateRange.equals("Select Date")) {
			String[] splitDate=dateRange.split("-");
			try {
				dateFrom=sdf.parse(splitDate[0].trim());
				dateTo=sdf.parse(splitDate[1].trim());
				dateTo=DataProccessor.addTimeInDate(dateTo);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode="";
				if(candidateName==null)
					candidateName="";
				if(uniqueCode==null) {
					uniqueCode="";
				}
				if(designation==null)
					designation="";
				if(mspin==null)
					mspin="";
				List<Integer> passFStatus = new ArrayList<>();
				if(passFailStatus=="") {
						passFStatus.add(1);
						passFStatus.add(0);
					}else {
						passFStatus.add(Integer.valueOf(passFailStatus));
					}
				
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletLsit) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
				List<ParticipantRegistration> participantList=null;
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantByFilterDataForFSDM(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,list,dateFrom,dateTo);
				}else {
					participantList= participantserviceImpl.getParticipantByFilterDataForFSDM2(outletCode, candidateName, designation, mspin, passFStatus, list, uniqueCode);
				}
			    for (final ParticipantRegistration p : participantList) {
			    	
		            if (p.getTestStatus() != null && Integer.parseInt(p.getTestStatus()) == 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus()) == 3) &&
		            		(p.getStatus() == null || !p.getStatus().equals("H"))) {
		            	listParticipant.add(setDataToMPV(p));
		            }
			    }
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	          //Adding data to Search 
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,dateRange);
	            model.addAttribute("payload", filterPayload);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "FSDM";
	        }
	        return "redirect:login";
	    }
	    
	    // Filter For FSDM Hold Employee
	    @PostMapping({ "/filterParticipantOnFSDMHold" })
	    public String filterForFSDMHold(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation,@RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateRange") String dateRange, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			
			if(!dateRange.equals("Select Date")) {
			String[] splitDate=dateRange.split("-");
			try {
				dateFrom=sdf.parse(splitDate[0].trim());
				dateTo=sdf.parse(splitDate[1].trim());
				dateTo=DataProccessor.addTimeInDate(dateTo);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode="";
				if(candidateName==null)
					candidateName="";
				if(uniqueCode==null) {
					uniqueCode="";
				}
				if(designation==null)
					designation="";
				if(mspin==null)
					mspin="";
				List<Integer> passFStatus = new ArrayList<>();
				if(passFailStatus=="") {
						passFStatus.add(1);
						passFStatus.add(0);
					}else {
						passFStatus.add(Integer.valueOf(passFailStatus));
					}
				
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletLsit) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
				List<ParticipantRegistration> participantList=null;
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantByFilterDataForFSDM(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,list,dateFrom,dateTo);
				}else {
					participantList= participantserviceImpl.getParticipantByFilterDataForFSDM2(outletCode, candidateName, designation, mspin, passFStatus, list, uniqueCode);
				}
			    for (final ParticipantRegistration p : participantList) {
			    	
			    	if (p.getStatus() != null && p.getStatus().equals("H")) {
			    		listParticipant.add(setDataToMPV(p));
		            }
			    }
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	          //Adding data to Search 
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,dateRange);
	            model.addAttribute("payload", filterPayload);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "viewHoldEmployeeByFSDM";
	        }
	        return "redirect:login";
	    }
	    
	    // FSDM Employee Master
	    @PostMapping({ "/filterEmployeeMasterFSDM" })
	    public String filterForFSDMEmployeeMaster(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation,@RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateRange") String dateRange, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			
			if(!dateRange.equals("Select Date")) {
			String[] splitDate=dateRange.split("-");
			try {
				dateFrom=sdf.parse(splitDate[0].trim());
				dateTo=sdf.parse(splitDate[1].trim());
				dateTo=DataProccessor.addTimeInDate(dateTo);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode="";
				if(candidateName==null)
					candidateName="";
				if(uniqueCode==null) {
					uniqueCode="";
				}
				if(designation==null)
					designation="";
				if(mspin==null)
					mspin="";
				List<Integer> passFStatus = new ArrayList<>();
				if(passFailStatus=="") {
						passFStatus.add(1);
						passFStatus.add(0);
					}else {
						passFStatus.add(Integer.valueOf(passFailStatus));
					}
				
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
	            final List<Long> list = new ArrayList<Long>();
	            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
	            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
	            for (final Region r : f.get().getRegion()) {
	                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
	                for (final Outlet outlet : outletLsit) {
	                	dealerCodeList.add(outlet);
	                    list.add(outlet.getDealer().getId());
	                }
	            }
				List<ParticipantRegistration> participantList=null;
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantByFilterDataForFSDM(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,list,dateFrom,dateTo);
				}else {
					participantList= participantserviceImpl.getParticipantByFilterDataForFSDM2(outletCode, candidateName, designation, mspin, passFStatus, list, uniqueCode);
				}
			    for (final ParticipantRegistration p : participantList) {
			    	
			    	if(p.getTestStatus() !=null && Integer.parseInt(p.getTestStatus() )== 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus())==2)) {
			    		listParticipant.add(setDataToMPV(p));
		            }
			    }
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	          //Adding data to Search 
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,dateRange);
	            model.addAttribute("payload", filterPayload);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("dealerCodeList", dealerCodeList);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "employeeFSDM";
	        }
	        return "redirect:login";
	    }
	    // Status Filters
	    @PostMapping({ "/statusFSDMHold" })
		public String checkCompletionProcessFilterOnFSDMHold(@RequestParam(required = false) final String interview,
				@RequestParam(required = false) final String prarambh, @RequestParam(required = false) final String fsdm, final HttpSession session, Model model) {
			List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
			List<Long> dealerIdList = new ArrayList<Long>();
			FilterPayload payload = new FilterPayload();
			payload.setInterview(interview);
			payload.setPraraambh(prarambh);
			payload.setFsdmApproved(fsdm);
			Integer interviewSearch = null;
			String praraambhSearch = prarambh;
			List<String> fsdmSearch=new ArrayList<String>();
			if (interview != null)
				interviewSearch = 0;
			if (prarambh != null)
				praraambhSearch = "1";
			if(fsdm!=null) {
				 fsdmSearch.add("1");
				 fsdmSearch.add("3");
				 }

			if (session.getAttribute("userId") != null) {
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
		            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
		            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
		            for (final Region r : f.get().getRegion()) {
		                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
		                for (final Outlet outlet : outletLsit) {
		                	dealerCodeList.add(outlet);
		                    dealerIdList.add(outlet.getDealer().getId());
		                }
		            }
				final List<ParticipantRegistration> list = participantserviceImpl.findParticipantsByCompletionFilterOnHold(interviewSearch, praraambhSearch, fsdmSearch,dealerIdList);
				  for (final ParticipantRegistration p : list) {
				    	
				    	if (p.getStatus() != null && p.getStatus().equals("H")) {
				    		listParticipant.add(setDataToMPV(p));
			            }
				    }
				 final List<Designation> designations = designationService.getAll();
		            DataProccessor.setDateRange(model);
		            model.addAttribute("payload", payload);
		            model.addAttribute("participantList", (Object)listParticipant);
		            model.addAttribute("dealerCodeList", dealerCodeList);
		            model.addAttribute("designations", (Object)designations);
		            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
		            return "viewHoldEmployeeByFSDM";
			}
			return "redirect:login";
		}
	    
	    @PostMapping({ "/statusFSDMInProcess" })
		public String checkCompletionProcessFilterFSDMInProgress(@RequestParam(required = false) final String interview,
				@RequestParam(required = false) final String prarambh, @RequestParam(required = false) final String fsdm, final HttpSession session, Model model) {
			List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
			List<Long> dealerIdList = new ArrayList<Long>();
			FilterPayload payload = new FilterPayload();
			payload.setInterview(interview);
			payload.setPraraambh(prarambh);
			payload.setFsdmApproved(fsdm);
			Integer interviewSearch = null;
			String praraambhSearch = prarambh;
			List<String> fsdmSearch=new ArrayList<String>();
			if (interview != null)
				interviewSearch = 0;
			if (prarambh != null)
				praraambhSearch = "1";
			if(fsdm!=null) {
				 fsdmSearch.add("1");
				 }


			if (session.getAttribute("userId") != null) {
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
		            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
		            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
		            for (final Region r : f.get().getRegion()) {
		                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
		                for (final Outlet outlet : outletLsit) {
		                	dealerCodeList.add(outlet);
		                    dealerIdList.add(outlet.getDealer().getId());
		                }
		            }
				final List<ParticipantRegistration> list = participantserviceImpl.findParticipantsByCompletionFilterInProcess(interviewSearch, praraambhSearch, fsdmSearch, dealerIdList);
				  for (final ParticipantRegistration p : list) {
					  if(p.getFsdmApprovalStatus()==null || Integer.parseInt(p.getFsdmApprovalStatus())==1){
				    		listParticipant.add(setDataToMPV(p));
					  }
				    }
				 final List<Designation> designations = designationService.getAll();
		            DataProccessor.setDateRange(model);
		            model.addAttribute("payload", payload);
		            model.addAttribute("participantList", (Object)listParticipant);
		            model.addAttribute("dealerCodeList", dealerCodeList);
		            model.addAttribute("designations", (Object)designations);
		            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
		            return "FSDMInprocess";
			}
			return "redirect:login";
		}
	    // Pending Approval Status
	    @PostMapping({ "/statusPendingApproval" })
		public String checkCompletionProcessFilterFSDMPendingApproval(	@RequestParam(required = false) final String prarambh, final HttpSession session, Model model) {
			List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
			List<Long> dealerIdList = new ArrayList<Long>();
			FilterPayload payload = new FilterPayload();
			payload.setPraraambh(prarambh);
			String praraambhSearch = prarambh;
			if (prarambh != null)
				praraambhSearch = "1";

			if (session.getAttribute("userId") != null) {
				final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
		            List<Outlet> dealerCodeList = new ArrayList<Outlet>();
		            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
		            for (final Region r : f.get().getRegion()) {
		                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
		                for (final Outlet outlet : outletLsit) {
		                	dealerCodeList.add(outlet);
		                    dealerIdList.add(outlet.getDealer().getId());
		                }
		            }
				final List<ParticipantRegistration> list = participantserviceImpl.findParticipantsByCompletionFilterPendingApprovalFSDM(praraambhSearch, dealerIdList);
				  for (final ParticipantRegistration p : list) {
				    		listParticipant.add(setDataToMPV(p));
				    }
				 final List<Designation> designations = designationService.getAll();
		            DataProccessor.setDateRange(model);
		            model.addAttribute("payload", payload);
		            model.addAttribute("participantList", (Object)listParticipant);
		            model.addAttribute("dealerCodeList", dealerCodeList);
		            model.addAttribute("designations", (Object)designations);
		            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
		            return "FSDM";
			}
			return "redirect:login";
		}
	    
	    // Set Data in Process*************************************	    
	    private ModelParticpantView setDataToMPV(ParticipantRegistration p) {
	    	 final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
	         Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
	    	final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    	final ModelParticpantView modelParticpantView = new ModelParticpantView();
            modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
            modelParticpantView.setAccesskey(p.getAccessKey());
            modelParticpantView.setDesignation(p.getDesignation());
            if (p.getTotalMark() != null) {
                modelParticpantView.setTotalMark(p.getTotalMark());
            }
            else {
                modelParticpantView.setTotalMark("40");
            }
            if (p.getTestScore() != null) {
                modelParticpantView.setTestScore(p.getTestScore());
            }
            else {
                modelParticpantView.setTestScore("");
            }
            if (p.getPercentageScore() != null) {
                modelParticpantView.setPercentageScore(p.getPercentageScore());
            }
            else {
                modelParticpantView.setPercentageScore("");
            }
            modelParticpantView.setTestStatus(p.getTestStatus());
            modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));
            modelParticpantView.setPassFailStatus(p.getPassFailStatus());
            if (p.getInterviewDate() != null) {
                final String regDate = formatter.format(p.getInterviewDate());
                final String s = String.valueOf(regDate) + ", " + p.getInterviewTime();
                modelParticpantView.setInterViewDate(s);
            }
            else {
                modelParticpantView.setInterViewDate("");
            }
            final Optional<InterviewScore> interView = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(p.getAccessKey());
            if (interView.isPresent()) {
                modelParticpantView.setInterViewStatus(interView.get().getStatus());
                modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
            }
            modelParticpantView.setMspin(p.getMspin());
            modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());
            if (p.getFsdmApprovalStatus() == null) {
                modelParticpantView.setFsdmApprovalStatus("");
            }
            else if (p.getFsdmApprovalStatus().equals("1")) {
                modelParticpantView.setFsdmApprovalStatus("Rejected");
            }
            else if (p.getFsdmApprovalStatus().equals("2")) {
                modelParticpantView.setFsdmApprovalStatus("Approved");
            }
            else if (p.getFsdmApprovalStatus().equals("3")) {
                modelParticpantView.setFsdmApprovalStatus("Pending");
            }
            modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
            modelParticpantView.setDealerCode(p.getOutletCode());
            final Optional<Outlet> outlet2 = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
            if (outlet2.isPresent()) {
                modelParticpantView.setRegionCode(outlet2.get().getRegion().getRegionCode());
                modelParticpantView.setParentDealer(outlet2.get().getParentDealer().getParentDealerName());
                modelParticpantView.setDealerName(outlet2.get().getOutletName());
                modelParticpantView.setOutletCode(outlet2.get().getOutletCode());
                modelParticpantView.setCity(outlet2.get().getCity().getCityName());
                modelParticpantView.setState(outlet2.get().getState().getStateName());
            }
            else {
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
            return modelParticpantView;
	    }
	
	
}
