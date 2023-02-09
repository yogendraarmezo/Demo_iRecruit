package com.msil.irecruit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.City;
import com.msil.irecruit.Entities.DataScience;
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
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParentDealerService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.payload.FilterPayloadHO;
import com.msil.irecruit.payload.HOFilterPayload;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class HOController {
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
	DataScienceService dataScienceService;
    
    @GetMapping({ "/viewAllParticapants" })
    private String viewAllParticipantsOnHO(final HttpSession session, Model model) {
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        if (session.getAttribute("userId") != null) {
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantInpprocessHo();
            listParticipant = this.setDataToHOProcess(participant);
            model = setDataToNewFilter(model);
            model.addAttribute("participantList", (Object)listParticipant);
            return "HO";
        }
        return "redirect:login";
    }
    
    @GetMapping({ "/viewAllCompletedParticipant" })
    public String viewAllCompletedParticipantOnHO(final HttpSession session, Model model) {
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        if (session.getAttribute("userId") != null) {
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantEmployeeHo();
            listParticipant = this.setDataToHOProcess(participant);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList", (Object)listParticipant);
            return "HOEmployee";
        }
        return "redirect:login";
    }
    
    @GetMapping({ "/viewAllHoldParticipant" })
    public String viewAllHoldParticipantOnHO(final HttpSession session, Model model) {
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        if (session.getAttribute("userId") != null) {
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantHoldHo();
            listParticipant = this.setDataToHOProcess(participant);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList",listParticipant);
            return "HoldParticipantHO";
        }
        return "redirect:login";
    }
    
    @GetMapping({ "/viewAllPendingApprovalParticipant" })
    public String viewAllHPendingApprovalParticipantOn(final HttpSession session, Model model) {
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        if (session.getAttribute("userId") != null) {
            final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantPendingApprovel();
            listParticipant = this.setDataToHOProcess(participant);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList",listParticipant);
            return "hoPendingApproval";
        }
        return "redirect:login";
    }
    
    @PostMapping({ "/hoFilter" })
    public String hoFilterPage(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model) {
        List<Long> listDealer = new ArrayList<>();
        Date dateFrom = null;
        Date dateTo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (dateFromm != null && dateFromm != "") {
				dateFrom = sdf.parse(dateFromm);
			}
			if (dateToo != null && dateToo != "") {
				dateTo = sdf.parse(dateToo);
				dateTo = DataProccessor.addTimeInDate(dateTo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        // Parse Integer Value
        Long dId = null;
        if(dealerId!=null && dealerId!="") {
        	dId = Long.valueOf(dealerId); 
        }
        Integer fId = null;
        if(fsdmId!=null && fsdmId!="") {
        	fId=Integer.valueOf(fsdmId);
        }
        FilterPayloadHO payload = new FilterPayloadHO(regionCode, stateCode, cityCode, parentDealerCode, fsdmId, dealerId, dateFromm, dateToo);
		/*
		 * List<String> rgList1 = new ArrayList<String>(); List<String> stList = new
		 * ArrayList<String>(); List<String> ctList = new ArrayList<String>();
		 * List<String> pdList = new ArrayList<String>(); List<Integer> fList1 = new
		 * ArrayList<Integer>(); List<Long> dList = new ArrayList<Long>(); for (final
		 * String g : regions) { rgList1.add(g); } stList.addAll(Arrays.asList(states));
		 * ctList.addAll(Arrays.asList(cities)); for (final String f : fsdms) {
		 * fList1.add(Integer.parseInt(f)); } pdList.addAll(Arrays.asList(pDealers));
		 * for (final String d : dealers) { dList.add(Long.parseLong(d)); }
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); try { if
		 * (dateFromm != null && dateFromm != "") { dateFrom = sdf.parse(dateFromm); }
		 * if (dateToo != null && dateToo != "") { dateTo = sdf.parse(dateToo); dateTo =
		 * DataProccessor.addTimeInDate(dateTo); } } catch (ParseException e) {
		 * e.printStackTrace(); } HOFilterPayload payload = new HOFilterPayload(rgList1,
		 * stList, ctList, pdList, dList, fList1, dateFromm,dateToo); if
		 * (rgList1.isEmpty()) { rgList1 = null; } if (stList.isEmpty()) { stList =
		 * null; } if (ctList.isEmpty()) { ctList = null; } if (pdList.isEmpty()) {
		 * pdList = null; } if (dList.isEmpty()) { dList = null; }else {
		 * listDealer.addAll(dList); } if (fList1.isEmpty()) { fList1 = null; }
		 */
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        List<Outlet> outlets = outletService.getOutletForHoFilter(regionCode,stateCode,cityCode,parentDealerCode,fId,dId);
        //List<Outlet>  li =outletService.getDealerForHO(pdList,dList,rgList1, ctList, stList,fList1 );
       // List<Outlet>  li =outletService.getDealerForHOFilter(rgList1,stList,ctList,pdList,fList1,dList );
      // li.stream().map(Outlet :: getOutletName).forEachOrdered(System.out::println);
        outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
        if(listDealer.size()==0){ 
        	listDealer.add((long) 0);
        }
        if (session.getAttribute("userId") != null) {
        	List<ParticipantRegistration> participants =new ArrayList<ParticipantRegistration>();
        	
        	if(dateFrom!=null && dateTo!=null ) {
                    participants = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantCSVInpprocess(listDealer, dateFrom, dateTo);	
        	}else {     
        		   participants = participantserviceImpl.getParticipantCSVInpprocess(listDealer);	
        		 
			}
            listParticipant = setDataToHOProcess(participants);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList", listParticipant);
            model.addAttribute("payload", payload);
            return "HO";
        }
        return "redirect:login";
    }
    
    @PostMapping({ "/hoFilterPendingApprovel" })
    public String hoFilterPendingApprovel(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model) {
        List<Long> listDealer = new ArrayList<>();
        FilterPayloadHO payload = new FilterPayloadHO(regionCode, stateCode, cityCode, parentDealerCode, fsdmId, dealerId, dateFromm, dateToo);
        Date dateFrom = null;
        Date dateTo = null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (dateFromm != null && dateFromm != "") {
				dateFrom = sdf.parse(dateFromm);
			}
			if (dateToo != null && dateToo != "") {
				dateTo = sdf.parse(dateToo);
				dateTo = DataProccessor.addTimeInDate(dateTo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long dId = null;
        if(dealerId!=null && dealerId!="") {
        	dId = Long.valueOf(dealerId); 
        }
        Integer fId = null;
        if(fsdmId!=null && fsdmId!="") {
        	fId=Integer.valueOf(fsdmId);
        }
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
 		outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
 		if(listDealer.size()==0){ 
        	listDealer.add((long) 0);
        }
        if (session.getAttribute("userId") != null) {
        	List<ParticipantRegistration> participants =new ArrayList<ParticipantRegistration>();
        	if(dateFrom!=null && dateTo!=null) {
               participants = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantCSVPendingApprovel(listDealer, dateFrom, dateTo);
        	}else {       		
        		participants = participantserviceImpl.getParticipantCSVPendingApprovel(listDealer);	        		
			}
            listParticipant = setDataToHOProcess(participants);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList", listParticipant);
            model.addAttribute("payload", payload);
            return "hoPendingApproval";
        }
        return "redirect:login";
    }
    
    @PostMapping({ "/hoFilterEmployee" })
    public String hoFilterEmployee(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model) {
        List<Long> listDealer = new ArrayList<>();
        FilterPayloadHO payload = new FilterPayloadHO(regionCode, stateCode, cityCode, parentDealerCode, fsdmId, dealerId, dateFromm, dateToo);
        Date dateFrom = null;
        Date dateTo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (dateFromm != null && dateFromm != "") {
				dateFrom = sdf.parse(dateFromm);
			}
			if (dateToo != null && dateToo != "") {
				dateTo = sdf.parse(dateToo);
				dateTo = DataProccessor.addTimeInDate(dateTo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long dId = null;
        if(dealerId!=null && dealerId!="") {
        	dId = Long.valueOf(dealerId); 
        }
        Integer fId = null;
        if(fsdmId!=null && fsdmId!="") {
        	fId=Integer.valueOf(fsdmId);
        }
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
        outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
 		if(listDealer.size()==0){ 
        	listDealer.add((long) 0);
        }
        if (session.getAttribute("userId") != null) {
        	List<ParticipantRegistration> participants =new ArrayList<ParticipantRegistration>();
        	if(dateFrom!=null && dateTo!=null) {
               participants = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantCSVImployee(listDealer, dateFrom, dateTo);
        	}else {       		
        		participants = participantserviceImpl.getParticipantCSVImployee(listDealer);	        		
			}
            listParticipant = setDataToHOProcess(participants);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList", listParticipant);
            model.addAttribute("payload", payload);
            return "HOEmployee";
            
            
        }
        return "redirect:login";
    }
    //
    @PostMapping({ "/hoFilterHold" })
    public String hoFilterHold(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model) {
        List<Long> listDealer = new ArrayList<>();
        Date dateFrom = null;
        Date dateTo = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (dateFromm != null && dateFromm != "") {
				dateFrom = sdf.parse(dateFromm);
			}
			if (dateToo != null && dateToo != "") {
				dateTo = sdf.parse(dateToo);
				dateTo = DataProccessor.addTimeInDate(dateTo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long dId = null;
        if(dealerId!=null && dealerId!="") {
        	dId = Long.valueOf(dealerId); 
        }
        Integer fId = null;
        if(fsdmId!=null && fsdmId!="") {
        	fId=Integer.valueOf(fsdmId);
        }
        //Filter Payload
       FilterPayloadHO payload = new FilterPayloadHO(regionCode, stateCode, cityCode, parentDealerCode, fsdmId, dealerId, dateFromm, dateToo);
        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
		
        outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
        if(listDealer.size()==0){ 
        	listDealer.add((long) 0);
        }
        
        if (session.getAttribute("userId") != null) {
        	List<ParticipantRegistration> participants =null;
        	if(dateFrom!=null && dateTo!=null) {
               participants = (List<ParticipantRegistration>)this.participantserviceImpl.getParticipantCSVHold(listDealer, dateFrom, dateTo);
        	}else {       		
        		participants = participantserviceImpl.getParticipantCSVHold(listDealer);	        		
			}

            listParticipant = setDataToHOProcess(participants);
            model = this.setDataToNewFilter(model);
            model.addAttribute("participantList", listParticipant);
            model.addAttribute("payload", payload);
           
            return "HoldParticipantHO";
        }
        return "redirect:login";
    }
    //
    @GetMapping({ "/allStateHO" })
    @ResponseBody
    public String getAllStateByRegion(@RequestParam final String[] regions) {
        final List<String> regionList = new ArrayList<String>();
        for (final String g : regions) {
        	regionList.add(g);
        }
        String stateList = "";
        List<State> statessList = new ArrayList<State>(); 
		/* 
        if (!regionList.isEmpty()) {
            statessList = (List<State>)this.outletService.getOutletByRgionList((List)regionList);
        }
        else {
            statessList = (List<State>)this.stateService.getAllState();
        }*/
        statessList =   stateService.getStateByRegionCode(regionList);
        statessList.sort(Comparator.comparing(State :: getStateName));
        for (final State s : statessList) {
            stateList = String.valueOf(stateList) + "<li><label><input type='checkbox' class='regs' id='" + s.getStateCode() + "' value='" + s.getStateCode() + "' />" + s.getStateName() + "</label> </li>";
        }
        return stateList;
    }
    
    @GetMapping({ "/allCityHO" })
    @ResponseBody
    public String getAllCityByState(@RequestParam final String[] stateCode) {
        final List<String> stateCodes = Arrays.asList(stateCode);
        String cityList = "";
        List<City> cities = new ArrayList<City>();
        if (!stateCodes.isEmpty()) {
            cities = cityService.getAllCityByStateCode(stateCodes);
        }
        else {
            cities = (List<City>)this.cityService.getAllCity();
        }
        cities.sort(Comparator.comparing(City :: getCityName));
        //Collections.sort(cities, (c1,c2) -> c1.getCityName().compareToIgnoreCase(c2.getCityName()));
        for (final City c : cities) {
            cityList = String.valueOf(cityList) + "<li><label><input type='checkbox' class='city' id='" + c.getCityCode() + "' value='" + c.getCityCode() + "' />" + c.getCityName() + "</label> </li>";
        }
        return cityList;
    }
    
    @GetMapping({ "/allParentDealerHO" })
    @ResponseBody
    public String getAllParentDealersByCity(@RequestParam final String[] cityCode) {
        final List<String> citiCodes = Arrays.asList(cityCode);
        String pdList = "";
        Set<ParentDealer> pDealers = new LinkedHashSet<ParentDealer>();
        if (!citiCodes.isEmpty()) {
        	List<Outlet> outlets= outletService.getOutletByCityCodes(citiCodes);
        	outlets.stream().map(Outlet :: getParentDealer ).forEachOrdered(pDealers :: add);
            //pDealers = (List<ParentDealer>)parentDealerService.getAllParentDealerByCityCode(citiCodes);
        }
        else {
           // List<ParentDealer> pd = (List<ParentDealer>)this.parentDealerService.getAllParentDealer();
            pDealers.addAll(this.parentDealerService.getAllParentDealer());
        }
       // pDealers.stream().map(ParentDealer :: getParentDealerCode).forEachOrdered(System.out::println);
        Map<String, String> map = pDealers.stream().collect(
       		  Collectors.toMap(ParentDealer :: getParentDealerCode ,ParentDealer :: getParentDealerName));
        Map<String, String> sortedMap = DataProccessor.sortMapByValueStringAcs(map);
        for (Map.Entry<String, String> pd : sortedMap.entrySet()) {
            pdList +=  "<li><label><input type='checkbox' class='pd' id='" + pd.getKey() + "' value='" + pd.getKey() + "' />" + pd.getValue() + "</label> </li>";
        }
        return pdList;
    }
    
    @GetMapping({ "/allDealerHO" })
    @ResponseBody
    public String getAllDealerByPDCodes(@RequestParam final String[] pdCodes) {
        final List<String> pdCodeList = Arrays.asList(pdCodes);
        String dealerList = "";
        Set<Dealer> dealers = new LinkedHashSet<Dealer>();
        if (pdCodeList.isEmpty()) {
            //dealers = (Set<Dealer>)this.dealerService.getAllDeealer();
          //  dealers.addAll(this.dealerService.getAllDeealer());
        }else {
			List<Outlet> outlets=outletService.getOutletsByPDCodes(pdCodeList);
			outlets.stream().map(Outlet :: getDealer).forEachOrdered(dealers :: add);
		}
        for (final Dealer d : dealers) {
            dealerList = String.valueOf(dealerList) + "<li><label><input type='checkbox' class='dealer' id='" + d.getMspin() + "' value='" + d.getMspin() + "' /> " + d.getName() + "</label></li>";
        }
        return dealerList;
    }
    
    // FSDm By Region Code
    @GetMapping({"/allFsdmHO"})
    @ResponseBody
    public String getFSDMByRegionCode(@RequestParam final String[] regions) {
    	final List<String> regionList = new ArrayList<String>();
        for (final String g : regions) {
        	regionList.add(g);
        }
        String fsdmList = "";
        Set<FSDM> fsdmSet = new LinkedHashSet<FSDM>();
        
        if (!regionList.isEmpty()) {
        	List<Region> regionList2 =regionService.getAllRegionByRegionCodeList(regionList);
        	regionList2.stream().map(Region :: getFsdm).forEachOrdered(fsdmSet :: add);
        	//fsdmSet.addAll(regionService.getAllFSMByRegionId(regionList));    
        }
        else {
            fsdmSet.addAll(this.fsdmservice.getAllFSDM());
        }
        //fsdmSet.stream().sorted(Comparator.comparing(FSDM :: getName));
        fsdmSet.stream().sorted((f1,f2) -> f1.getName().compareToIgnoreCase(f2.getName()));
        //fsdmSet.stream().map(FSDM :: getName).forEach(System.out :: println);
        for (final FSDM f : fsdmSet) {
        	fsdmList = String.valueOf(fsdmList) + "<li><label><input type='checkbox' class='fsdm' id='" + f.getId() + "' value='" + f.getId() + "' />" + f.getName() + "</label> </li>";
        }
    	return fsdmList;
    }
    
    private List<ModelParticpantView> setDataToHOProcess(final List<ParticipantRegistration> participants) {
        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
        Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
        for (final ParticipantRegistration p : participants) {
            if (p.getTestStatus() != null && (p.getTestStatus().equals("1") || p.getTestStatus().equals("3"))) {
                final ModelParticpantView modelParticpantView = new ModelParticpantView();
               
                if (p.getRegistration_date() != null) {
                    modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
                }
                if (p.getTestCompletionDate() != null) {
                    modelParticpantView.setAssessment_Completion_date(formatter.format(p.getTestCompletionDate()));
                }
                modelParticpantView.setParticipantName(String.valueOf(p.getFirstName()) + " " + p.getMiddleName() + " " + p.getLastName());
                modelParticpantView.setAccesskey(p.getAccessKey());
                modelParticpantView.setDesignation(p.getDesignation());
                if (p.getTotalMark() != null) {
                    modelParticpantView.setTotalMark(p.getTotalMark());
                }
                else {
                    modelParticpantView.setTotalMark("00");
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
                    modelParticpantView.setInterViewStatus(interView.get().getStatus());
                    modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
                }
                else {
                    modelParticpantView.setInterViewStatus("");
                    modelParticpantView.setInterViewPassFailStatus("");
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
                
                Optional<DataScience>d = dataScienceService.findByAccesskey(p.getAccessKey());
                if(d.isPresent()) {
                	modelParticpantView.setDataScienceResult(DataProccessor.getStringValue(d.get().getDataScienceResult()));
                	modelParticpantView.setDataScienceReason(DataProccessor.getStringValue(d.get().getReason()));
                	modelParticpantView.setDataScienceStatus(DataProccessor.getStringValue(d.get().getStatus()));
                	modelParticpantView.setDataScienceInterViewStatus(DataProccessor.getStringValue(d.get().getInterviewStatus()));
                	modelParticpantView.setReference(DataProccessor.getStringValue(d.get().getDataScienceReferenceId()));
                	modelParticpantView.setPrediction(DataProccessor.getStringValue(d.get().getDataSciencePrediction()));
                }
                final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(p.getOutletCode(), p.getDealerId());
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
                modelParticpantView.setDateOfRegistration(formatter.format(p.getRegistration_date()));
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
        return listParticipant;
    }
    
    //Set Date To New Filter page
    private Model setDataToNewFilter(Model model) {
    	List<Region> regionList = regionService.getAllRegion();
    	List<State> stateList = stateService.getAllState();
    	List<City> cityList = cityService.getAllCity();
    	List<ParentDealer> parentDealerList = parentDealerService.getAllParentDealer();
    	List<FSDM> fsdmList = fsdmservice.getAllFSDM();
    	List<Dealer> dealerList = dealerService.getAllDeealer();
    	
    	
    	//Sorting List Data
    	List<Region> regions = regionList.stream().sorted((r1,r2)-> r1.getRegionCode().compareToIgnoreCase(r2.getRegionCode())).collect(Collectors.toList());
    	List<State> states = stateList.stream().sorted((s1, s2)-> s1.getStateName().compareToIgnoreCase(s2.getStateName())).collect(Collectors.toList());
    	List<City> cities = cityList.stream().sorted((c1,c2)-> c1.getCityName().compareToIgnoreCase(c2.getCityName())).collect(Collectors.toList());
    	List<ParentDealer> parentDealers = parentDealerList.stream().sorted((p1,p2)->p1.getParentDealerName().compareToIgnoreCase(p2.getParentDealerName())).collect(Collectors.toList());
    	List<FSDM> fsdms = fsdmList.stream().sorted((f1,f2)->f1.getName().compareToIgnoreCase(f2.getName())).collect(Collectors.toList());
    	List<Dealer> dealers = dealerList.stream().sorted((d1,d2)->d1.getName().compareToIgnoreCase(d2.getName())).collect(Collectors.toList());
    	
    	//Adding to model
    	model.addAttribute("regions", (Object)regions);
    	model.addAttribute("states", (Object)states);
    	model.addAttribute("cities", (Object)cities);
    	model.addAttribute("pDealers", (Object)parentDealers);
    	model.addAttribute("fsdms", (Object)fsdms);
    	model.addAttribute("dealers", (Object)dealers);
    	return model;
    }
    
    /*
    private Model setDataToFilterPage(Model model) {
        final Map<String, String> regionMap = new HashMap<String, String>();
        final Map<String, String> stateMap = new HashMap<String, String>();
        final Map<String, String> cityMap = new HashMap<String, String>();
        final Map<String, String> parentDMap = new HashMap<String, String>();
        final Map<Long, String> dealerMap = new HashMap<Long, String>();
        final Map<Integer, String> fSDMMap = new HashMap<Integer, String>();
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
            dealerMap.put(dl.getId(), dl.getName());
        }
        final List<FSDM> fsdms = (List<FSDM>)this.fsdmservice.getAllFSDM();
        for (final FSDM fsdm : fsdms) {
            fSDMMap.put(fsdm.getId(), fsdm.getName());
        }
        
       //Sorting map value
        Map<String, String> regionMapSorted=DataProccessor.sortMapByValueStringAcs(regionMap);
        Map<String, String> stateMapSorted=DataProccessor.sortMapByValueStringAcs(stateMap);
        Map<String, String> cityMapSorted = DataProccessor.sortMapByValueStringAcs(cityMap);
        Map<String, String> parentDMapSorted= DataProccessor.sortMapByValueStringAcs(parentDMap);
        Map<Long, String> dealerMapSorted=DataProccessor.sortMapByValueLongAcs(dealerMap);
        Map<Integer, String> fSDMMapSorted= DataProccessor.sortMapByValueIntegerAcs(fSDMMap);
        model = DataProccessor.setDateRange(model);
        model.addAttribute("regionMap", (Object)regionMapSorted);
        model.addAttribute("stateMap", (Object)stateMapSorted);
        model.addAttribute("cityMap", (Object)cityMapSorted);
        model.addAttribute("parentDMap", (Object)parentDMapSorted);
        model.addAttribute("dealerMap", (Object)dealerMapSorted);
        model.addAttribute("FSDMMap", (Object)fSDMMapSorted);
        return model;
    }
    */
    
//    // Get Participant By Date Wise
//    public List<ParticipantRegistration> getParticipantByDate(Date dateFrom, Date dateTo){
//    	List<ParticipantRegistration> part = participantserviceImpl.findByRegisterDateRange(dateFrom, dateTo);
//    	return part;
//    }

}
