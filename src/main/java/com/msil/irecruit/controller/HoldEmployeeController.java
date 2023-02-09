package com.msil.irecruit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.msil.irecruit.Entities.City;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParentDealer;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.State;
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
import com.msil.irecruit.client.RestClientReattemp;
import com.msil.irecruit.payload.FilterPayload;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class HoldEmployeeController {
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
    @Autowired
    RestClientReattemp restClientReattemp;
    
    @GetMapping({ "/viewHoldEmployeeByDealer" })
    public String getHoldEmployeeDealer(final HttpSession session, Model model) {
        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        if (session.getAttribute("userId") != null) {
            final long dealerId = Long.parseLong(session.getAttribute("userId").toString());
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById(dealerId);
            final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
            Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
            Map<String, String> SalesDesignation = designations2.stream().filter(p ->p.getCategory().equals("Sales")).collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
           
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.getHoldEmployee(dealerId, "H");
            for (final ParticipantRegistration p : participant) {
                if (p.getStatus() != null && p.getStatus().equals("H")) {
                    final ModelParticpantView modelParticpantView = new ModelParticpantView();
                    modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
                    modelParticpantView.setAccesskey(p.getAccessKey());
                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
                    if (p.getTestCompletionDate() != null) {
                        modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                    }
                    if (p.getTestStatus() != null) {
                        modelParticpantView.setTestStatus(p.getTestStatus());
                        modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));
                        modelParticpantView.setPassFailStatus((int)p.getPassFailStatus());
                    }
                    else {
                        modelParticpantView.setTestStatus("");
                    }
                    modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p.getInterviewScore()));
                    if (p.getPassFailStatus() != 0) {
                        modelParticpantView.setPassFailStatus(p.getPassFailStatus());
                    }
                    else {
                        modelParticpantView.setPassFailStatus(0);
                    }
                    modelParticpantView.setEmail(p.getEmail());
                    modelParticpantView.setMobile(p.getMobile());
                    if (p.getTestScore() != null) {
                        modelParticpantView.setTestScore(p.getTestScore());
                    }
                    else {
                        modelParticpantView.setTestScore("0");
                    }
                    if (p.getPercentageScore() != null) {
                        modelParticpantView.setPercentageScore(p.getPercentageScore());
                    }
                    else {
                        modelParticpantView.setPercentageScore("0");
                    }
                    if (p.getTotalMark() != null) {
                        modelParticpantView.setTotalMark(p.getTotalMark());
                    }
                    else {
                        modelParticpantView.setTotalMark("40");
                    }
                    if (p.getInterviewDate() != null) {
                        final String regDate = formatter.format(p.getInterviewDate());
                        final String s = String.valueOf(regDate) + " " + p.getInterviewTime();
                        modelParticpantView.setInterViewDate(s);
                    }
                    else {
                        modelParticpantView.setInterViewDate("");
                    }
                    modelParticpantView.setDesignation(p.getDesignation());
                    if (p.getPrarambhStatus() == null) {
                        modelParticpantView.setPrarambhStatus("");
                    }
                    else {
                        modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());
                    }
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
                    modelParticpantView.setStatus("Hold");
                    modelParticpantView.setOutletCode(DataProccessor.getStringValue(p.getOutletCode()));
                    
                    final Optional<InterviewScore> interView = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(p.getAccessKey());
                    if (interView.isPresent()) {
                        modelParticpantView.setInterViewStatus(DataProccessor.getStringValue(interView.get().getStatus()));
                        modelParticpantView.setInterViewPassFailStatus(DataProccessor.getStringValue(interView.get().getPass_fail_status()));
                    }
                    if (dealer.isPresent()) {
                        modelParticpantView.setDealerName(DataProccessor.getStringValue(dealer.get().getName()));
                    }
                    modelParticpantView.setMspin(DataProccessor.getStringValue(p.getMspin()));
                    modelParticpantView.setPrarambhStatus(DataProccessor.getStringValue(p.getPrarambhStatus()));
                    final Optional<Outlet> outlet = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
                    if (outlet.isPresent()) {
                        modelParticpantView.setRegionCode(outlet.get().getRegion().getRegionCode());
                        modelParticpantView.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
                        modelParticpantView.setDealerName(outlet.get().getOutletName());
                        modelParticpantView.setOutletCode(outlet.get().getOutletCode());
                        modelParticpantView.setCity(outlet.get().getCity().getCityName());
                        modelParticpantView.setState(outlet.get().getState().getStateName());
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
            }
           final List<Outlet> outlets = (List<Outlet>)this.outletService.findByDealerId(dealerId);
			final List<Designation> designations = (List<Designation>)this.designationService.getAll();
			model.addAttribute("participantList", (Object)listParticipant);
			model.addAttribute("outlets", (Object)outlets);
			model=DataProccessor.setDateRange(model);
			model.addAttribute("designations", (Object)designations);
			model.addAttribute("dealerId", (Object)dealerId);
			model.addAttribute("salesDesignation", SalesDesignation);
			model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
            return "emloyeeHoldDealer";
        }
        return "redirect:login";
    }
    
    @GetMapping({ "/viewHoldEmployeeByFSDM" })
    public String getHoldEmployeeFSDM(final HttpSession session, final Model model) {
        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        List<Outlet> dealerCodeList = new ArrayList<Outlet>();
        final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
        Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
        if (session.getAttribute("userId") != null) {
            final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            final List<Long> list = new ArrayList<Long>();
            final Optional<FSDM> f = (Optional<FSDM>)this.fsdmservice.getFSDM(fsdmId);
            for (final Region r : f.get().getRegion()) {
                final List<Outlet> outletLsit = (List<Outlet>)this.outletService.getOutletByRegion(r.getId());
                for (final Outlet outlet : outletLsit) {
                	dealerCodeList.add(outlet);
                    list.add(outlet.getDealer().getId());
                }
            }
            final List<ParticipantRegistration> participant = participantserviceImpl.getParticipantByDealerIdLsit(list);
            for (final ParticipantRegistration p : participant) {
                final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById((long)p.getDealerId());
                if (p.getStatus() != null && p.getStatus().equals("H")) {
                    final ModelParticpantView modelParticpantView = new ModelParticpantView();
                    modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
                    modelParticpantView.setAccesskey(p.getAccessKey());
                    modelParticpantView.setDesignation(p.getDesignation());
                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
                    if (p.getTestCompletionDate() != null) {
                        modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                    }
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
                        modelParticpantView.setTestScore(new StringBuilder().append(p.getAttitudeScore()).toString());
                    }
                    if (p.getPercentageScore() != null) {
                        modelParticpantView.setPercentageScore(p.getPercentageScore());
                    }
                    else if (p.getAttitudeScore() != null && p.getAttitudeScore() > 0) {
                        final int passingPer = p.getAttitudeScore() / 40 * 100;
                        modelParticpantView.setPercentageScore(new StringBuilder(String.valueOf(passingPer)).toString());
                    }
                    modelParticpantView.setEmail(p.getEmail());
                    modelParticpantView.setMobile(p.getMobile());
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
                        modelParticpantView.setInterViewStatus(DataProccessor.getStringValue(interView.get().getStatus()));
                        modelParticpantView.setInterViewPassFailStatus(DataProccessor.getStringValue(interView.get().getPass_fail_status()));
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
                    modelParticpantView.setStatus("Hold");
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
            }
            final List<Designation> designations3 = designationService.getAll();
            DataProccessor.setDateRange(model);
            model.addAttribute("participantList", (Object)listParticipant);
            model.addAttribute("dealerCodeList", dealerCodeList);
            model.addAttribute("designations", (Object)designations3);
            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
            return "viewHoldEmployeeByFSDM";
        }
        return "redirect:login";
    }
    
    @GetMapping({ "/viewAllHoldParticipantOnHO" })
    public String viewAllHoldParticipantsOnHO(final HttpSession session, final Model model) {
        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
        Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
        if (session.getAttribute("userId") != null) {
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.findAllHoldParticipantsOnHO("H");
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            for (final ParticipantRegistration p : participant) {
                final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById((long)p.getDealerId());
                if (p.getTestStatus() != null && (p.getTestStatus().equals("1") || p.getTestStatus().equals("3"))) {
                    final ModelParticpantView modelParticpantView = new ModelParticpantView();
                    modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
                    modelParticpantView.setAccesskey(p.getAccessKey());
                    modelParticpantView.setDesignation(p.getDesignation());
                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
                    if (p.getTestCompletionDate() != null) {
                        modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                    }
                    if (p.getTotalMark() != null) {
                        modelParticpantView.setTotalMark(p.getTotalMark());
                    }
                    else {
                        modelParticpantView.setTotalMark("0");
                    }
                    if (p.getTestScore() != null) {
                        modelParticpantView.setTestScore(p.getTestScore());
                    }
                    else {
                        modelParticpantView.setTestScore(new StringBuilder().append(p.getAttitudeScore()).toString());
                    }
                    if (p.getPercentageScore() != null) {
                        modelParticpantView.setPercentageScore(p.getPercentageScore());
                    }
                    else if (p.getAttitudeScore() != null && p.getAttitudeScore() > 0) {
                        final int passingPer = p.getAttitudeScore() / 40 * 100;
                        modelParticpantView.setPercentageScore(new StringBuilder(String.valueOf(passingPer)).toString());
                    }
                    modelParticpantView.setEmail(p.getEmail());
                    modelParticpantView.setMobile(p.getMobile());
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
                        modelParticpantView.setInterViewStatus(DataProccessor.getStringValue(interView.get().getStatus()));
                        modelParticpantView.setInterViewPassFailStatus(DataProccessor.getStringValue(interView.get().getPass_fail_status()));
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
                    final Optional<Outlet> outlet = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
                    if (outlet.isPresent()) {
                        modelParticpantView.setRegionCode(outlet.get().getRegion().getRegionCode());
                        modelParticpantView.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
                        modelParticpantView.setDealerName(outlet.get().getOutletName());
                        modelParticpantView.setOutletCode(outlet.get().getOutletCode());
                        modelParticpantView.setCity(outlet.get().getCity().getCityName());
                        modelParticpantView.setState(outlet.get().getState().getStateName());
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
            }
            final Map<String, String> regionMap = new HashMap<String, String>();
            final Map<String, String> stateMap = new HashMap<String, String>();
            final Map<String, String> cityMap = new HashMap<String, String>();
            final Map<String, String> parentDMap = new HashMap<String, String>();
            final Map<String, String> dealerMap = new HashMap<String, String>();
            final Map<String, String> fSDMMap = new HashMap<String, String>();
            final List<Region> regions = (List<Region>)this.regionService.getAllRegion();
            for (final Region r : regions) {
                regionMap.put(r.getRegionCode(), r.getRegionCode());
            }
            final List<State> states = (List<State>)this.stateService.getAllState();
            for (final State st : states) {
                stateMap.put(st.getStateCode(), st.getStateName());
            }
            final List<City> cities = (List<City>)this.cityService.getAllCity();
            for (final City city : cities) {
                cityMap.put(city.getCityCode(), city.getCityName());
            }
            final List<ParentDealer> parentDealers = (List<ParentDealer>)this.parentDealerService.getAllParentDealer();
            for (final ParentDealer pd : parentDealers) {
                parentDMap.put(pd.getParentDealerCode(), pd.getParentDealerName());
            }
            final List<Dealer> dealers = (List<Dealer>)this.dealerService.getAllDeealer();
            for (final Dealer dl : dealers) {
                dealerMap.put(dl.getMspin(), dl.getName());
            }
            final List<FSDM> fsdms = (List<FSDM>)this.fsdmservice.getAllFSDM();
            for (final FSDM fsdm : fsdms) {
                fSDMMap.put(fsdm.getMspin(), fsdm.getName());
            }
            model.addAttribute("participantList", (Object)listParticipant);
            model.addAttribute("regionMap", (Object)regionMap);
            model.addAttribute("stateMap", (Object)stateMap);
            model.addAttribute("cityMap", (Object)cityMap);
            model.addAttribute("parentDMap", (Object)parentDMap);
            model.addAttribute("dealerMap", (Object)dealerMap);
            model.addAttribute("FSDMMap", (Object)fSDMMap);
            return "HoldParticipantHO";
        }
        return "redirect:login";
    }
    //Dealer Hold Filter
    @PostMapping({ "/holdFilterParticipant" })
    public String dealerFilters(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
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
			
			dealerId = Long.parseLong(session.getAttribute("userId").toString());
			final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById(dealerId);
			final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
			Map<String, String> designation3 = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
			Map<String, String> SalesDesignation = designations2.stream().filter(p ->p.getCategory().equals("Sales")).collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
			final String status = "H";
			List<ParticipantRegistration> participantList=null;
			if(dateFrom!=null && dateTo!=null)
			{
				participantList = participantserviceImpl.getParticipantOnHoldByFilterData(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,dealerId,dateFrom,dateTo,status);
			}else {
				participantList= participantserviceImpl.getParticipantOnHoldByFilterData2(outletCode, candidateName, designation, mspin, passFStatus, dealerId, uniqueCode,status);
			}
            listParticipant = setDataToMPVByDealer(participantList, dealer);
            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
            final List<Designation> designations = designationService.getAll();
          //Adding data to Search 
            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,dateRange);
            model.addAttribute("payload", filterPayload);
            model.addAttribute("participantList", (Object)listParticipant);
            model.addAttribute("outlets", (Object)outlets);
            model.addAttribute("designations", (Object)designations);
            model = DataProccessor.setDateRange(model);
            model.addAttribute("dealerId", (Object)dealerId);
            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
            return "emloyeeHoldDealer";
        }
        return "redirect:login";
    }
    //Status Filter For Hold Employee
    @PostMapping({ "/completionStatusHold" })
	public String checkCompletionProcessFilter(@RequestParam(required = false) final String interview,
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
			Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			dealerIdList.add(dealerId);
			final Optional<Dealer> dealer = dealerService.getById(dealerId);
			final List<ParticipantRegistration> list = participantserviceImpl.findParticipantsByCompletionFilterOnHold(interviewSearch, praraambhSearch, fsdmSearch, dealerIdList);
			listParticipant = setDataToMPVByDealer(list, dealer);
			final List<Outlet> outlets = outletService.findByDealerId(dealerId);
			final List<Designation> designations = designationService.getAll();
			model = DataProccessor.setDateRange(model);
			model.addAttribute("participantList", (Object) listParticipant);
			model.addAttribute("outlets", (Object) outlets);
			model.addAttribute("designations", (Object) designations);
			model.addAttribute("dealerId", (Object) dealerId);
			model.addAttribute("payload", (Object) payload);
			return "emloyeeHoldDealer";
		}
		return "redirect:login";
	}
    	// Set Data
	private List<ModelParticpantView> setDataToMPVByDealer(List<ParticipantRegistration> part, Optional<Dealer> dealer) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<ModelParticpantView> listMPV = new ArrayList<>();
		  final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
          Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
		for (ParticipantRegistration p : part) {
			if(p.getStatus() != null && p.getStatus().equals("H")) {
			ModelParticpantView modelParticpantView = new ModelParticpantView();
			modelParticpantView.setParticipantName(p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName());
			modelParticpantView.setAccesskey(p.getAccessKey());
			modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
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
				modelParticpantView.setFsdmApprovalStatus("");
			}else if(p.getFsdmApprovalStatus().equals("1")) {
				modelParticpantView.setFsdmApprovalStatus("Rejected");
			}else if(p.getFsdmApprovalStatus().equals("2")) {
				modelParticpantView.setFsdmApprovalStatus("Approved");		
			}else if(p.getFsdmApprovalStatus().equals("3")) {
				modelParticpantView.setFsdmApprovalStatus("Pending");		
			}
			modelParticpantView.setStatus("Hold");
			modelParticpantView.setOutletCode(DataProccessor.getStringValue(p.getOutletCode()));
			Optional<InterviewScore> interView = interviewScoreService.findByAccesskey(p.getAccessKey());
			if(interView.isPresent()) {
				modelParticpantView.setInterViewStatus(interView.get().getStatus());
				 modelParticpantView.setInterViewPassFailStatus(DataProccessor.getStringValue(interView.get().getPass_fail_status()));
			}
			
			if (dealer.isPresent()) {
				modelParticpantView.setDealerName(DataProccessor.getStringValue(dealer.get().getName()));
				modelParticpantView.setMspin(p.getMspin());
				modelParticpantView.setPrarambhStatus(p.getPrarambhStatus());
					 
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
			listMPV.add(modelParticpantView);
			}
		}
		return listMPV;
	}

}
