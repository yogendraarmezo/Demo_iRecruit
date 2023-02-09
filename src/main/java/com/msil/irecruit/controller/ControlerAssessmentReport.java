package com.msil.irecruit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.QuestiwiseReport;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.State;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.QuestiwiseReportService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.tc.entities.AssessmentReport;
import com.msil.irecruit.utils.DataProccessor;
import com.msil.irecruit.payload.DashboardFilterPayload;

@Controller
public class ControlerAssessmentReport {

	@Autowired
	ParticipantServiceImpl participantService;
	@Autowired
	QuestiwiseReportService questiwiseReportService;
	@Autowired
	DesignationService designationService;
	@Autowired
	OutletService outletService;
	@Autowired
	FSDMService fsdmservice;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private FSDMService fsdmService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private RegionService regionService;

	@GetMapping("/assessmentReport")
	public String getAssessmentReport(HttpSession session, Model model) {
		String role = "";
		if (session.getAttribute("role") != null) {
			role = session.getAttribute("role").toString().trim();
		
		Set<Dealer> dealers = new LinkedHashSet<>();
		Set<Outlet> outlets = new LinkedHashSet<>();
		Set<Region> regions = new LinkedHashSet<>();
		Set<State> states = new LinkedHashSet<State>();
		Set<City> cities = new LinkedHashSet<City>();
		
		List<ParticipantRegistration> partcipantList = null;
		final int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
		Date fromDate=new Date(), toDate=new Date();
		LocalDate currentDate = LocalDate.now();
		int currentMonthvalue = currentDate.getMonthValue();
		int yearValue = currentDate.getYear();
		LocalDate yearStartDate=null;
		 if(currentMonthvalue<4) {
	        	YearMonth yearMonth = YearMonth.of(yearValue-1, 4);
	        	yearStartDate = yearMonth.atDay(1);
	        	fromDate = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        }else {
				YearMonth yearMonth = YearMonth.of(yearValue, 4);
				yearStartDate = yearMonth.atDay(1);
				fromDate = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
		final List<Long> list = new ArrayList<Long>();
		if (role.equalsIgnoreCase("FS")) {
			List<Outlet> dealerCodeList = new ArrayList<Outlet>();
			final Optional<FSDM> f = (Optional<FSDM>) this.fsdmservice.getFSDM(fsdmId);
			for (final Region r : f.get().getRegion()) {
				final List<Outlet> outletList = (List<Outlet>) this.outletService.getOutletByRegion(r.getId());
				for (final Outlet outlet : outletList) {
					dealerCodeList.add(outlet);
					list.add(outlet.getDealer().getId());
				}

			}
			partcipantList = participantService.getParticipantByDealerId(list);
			Optional<FSDM> fsdm = fsdmService.getFSDM(fsdmId);
			for (Region r : fsdm.get().getRegion()) {
				regions.add(r);
				List<Outlet> outletList = outletService.getOutletByRegion(r.getId());
				for (Outlet outlet : outletList) {
					states.add(outlet.getState());
					cities.add(outlet.getCity());
					// dealerIdList.add(outlet.getDealer().getId());
					dealers.add(outlet.getDealer());
					outlets.add(outlet);
				}

			}
		}
		if (role.equalsIgnoreCase("HO")) {
			partcipantList = participantService.getParticipantByDealerId();
			List<Dealer> deal = dealerService.getAllDeealer();
			regions.addAll(regionService.getAllRegion());
			states.addAll(stateService.getAllState());
			cities.addAll(cityService.getAllCity());
			outlets.addAll(outletService.getAllOutlets());
			for (Dealer dealer : deal) {
				// dealerIdList.add(dealer.getId());
				dealers.add(dealer);
			}
		}
		if (role.equalsIgnoreCase("DL")) {
			long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			list.add(dealerId);
			partcipantList = participantService.getParticipantByDealerId(list);

			Optional<Dealer> dl = dealerService.getById(dealerId);
			List<Outlet> ot = outletService.findByDealerId(dealerId);
			HashMap<Region, Region> outletMap = new HashMap<Region, Region>();
			for (Outlet o : ot) {
				outlets.add(o);
				states.add(o.getState());
				cities.add(o.getCity());
				outletMap.put(o.getRegion(), o.getRegion());
			}

			for (Entry<Region, Region> eo : outletMap.entrySet()) {
				regions.add(eo.getValue());
			}
			dealers.add(dl.get());
		}
		model.addAttribute("assessList", getPaertcipant(partcipantList));
		List<Dealer> dealers2 = dealers.stream().sorted((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()))
				.collect(Collectors.toList());
		List<Region> regions2 = regions.stream()
				.sorted((r1, r2) -> r1.getRegionCode().compareToIgnoreCase(r2.getRegionCode()))
				.collect(Collectors.toList());
		List<State> states2 = states.stream()
				.sorted((s1, s2) -> s1.getStateName().compareToIgnoreCase(s2.getStateName()))
				.collect(Collectors.toList());
		List<City> cities2 = cities.stream()
				.sorted((c1, c2) -> c1.getCityName().compareToIgnoreCase(c2.getCityName()))
				.collect(Collectors.toList());
		List<Outlet> outlets2 = outlets.stream()
				.sorted((o1, o2) -> o1.getOutletName().compareToIgnoreCase(o2.getOutletName()))
				.collect(Collectors.toList());
		model.addAttribute("dealers", dealers2);
		model.addAttribute("regions", regions2);
		model.addAttribute("states", states2);
		model.addAttribute("cities", cities2);
		model.addAttribute("dealerCodeList", outlets2);
		return "assessmentReport";
		}else {
			return "redirect:login";
		}
	}
	
	
	@PostMapping("/searchAssessmentReport")
	public String searchAssessmentReport(@RequestParam("outletCode") String outletCode,
			@RequestParam("dealershipCode") String dealershipCode, @RequestParam("regionCode") String regionCode,
			@RequestParam("stateCode") String stateCode, @RequestParam("cityCode") String cityCode,
			@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) {
		if (session.getAttribute("userId") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DashboardFilterPayload filterPayload = new DashboardFilterPayload();
			Date dateFrom = null;
			Date dateTo = null;
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

			if (outletCode == null || outletCode.equals("")) {
				outletCode = null;
			}
			if (regionCode == null || regionCode.equals("")) {
				regionCode = null;
			}
			if(cityCode == null || cityCode.equals("")) {
				cityCode=null;
			}
			if(stateCode == null || stateCode.equals("")) {
				stateCode=null;
			}
			List<Outlet> ouletList =outletService.getOutletByRegoinStateCityDealerOutlet(regionCode, cityCode, stateCode, outletCode);
			List<Long>dealerList = new ArrayList<>();
			Set<Dealer> dealers = new LinkedHashSet<>();
			Set<Outlet> outlets = new LinkedHashSet<>();
			Set<Region> regions = new LinkedHashSet<>();
			Set<State> states = new LinkedHashSet<State>();
			Set<City> cities = new LinkedHashSet<City>();
			String role = session.getAttribute("role").toString();
			List<Long> dealerIdList = new ArrayList<Long>();
			Long dId = null;
			if (role.equalsIgnoreCase("DL")) {
				dId = Long.parseLong(session.getAttribute("userId").toString());
				Optional<Dealer> dl = dealerService.getById(dId);
				List<Outlet> ot = outletService.findByDealerId(dId);
				HashMap<Region, Region> outletMap = new HashMap<Region, Region>();
				for (Outlet o : ot) {
					outlets.add(o);
					outletMap.put(o.getRegion(), o.getRegion());
					states.add(o.getState());
					cities.add(o.getCity());
				}

				for (Entry<Region, Region> eo : outletMap.entrySet()) {
					regions.add(eo.getValue());
				}
				dealers.add(dl.get());
				dealerIdList.add(dId);
				
				if(outletCode == null || outletCode.equals("")) {
					  for(Outlet o:ouletList )	{
						  if(o.getDealer().getId() == dId) {
							  dealerList.add(o.getDealer().getId());
						  }
					  }
					}else {
						for(Outlet outlet: ouletList) {
							dealerList.add(outlet.getDealer().getId());
						}
					}
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					regions.add(r);
					List<Outlet> outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet : outletList) {
						dealerIdList.add(outlet.getDealer().getId());
						dealers.add(outlet.getDealer());
						outlets.add(outlet);
						states.add(outlet.getState());
						cities.add(outlet.getCity());
						
						   if(outletCode == null || outletCode.equals("")) {
							  for(Outlet o:ouletList )	{
								  if(o.getDealer().getId() == outlet.getDealer().getId()) {
									  dealerList.add(o.getDealer().getId());
								  }
							  }
							}
					}
				}
				
				if(outletCode != null && !outletCode.equals("")) {
						for(Outlet outlet: ouletList) {
							dealerList.add(outlet.getDealer().getId());
						}
					}
				
			} else if (role.equalsIgnoreCase("HO")) {
				List<Dealer> deal = dealerService.getAllDeealer();
				regions.addAll(regionService.getAllRegion());
				outlets.addAll(outletService.getAllOutlets());
				dealers.addAll(deal);
				states.addAll(stateService.getAllState());
				cities.addAll(cityService.getAllCity());
				for (Dealer dealer : deal) {
					dealerIdList.add(dealer.getId());
				}
				for(Outlet outlet: ouletList) {
					dealerList.add(outlet.getDealer().getId());
				}
			}
			
			
			List<ParticipantRegistration> partcipantList = null;
			if(dealerList.size() ==0) {
				dealerList.add((long) 0);	
			}
			if(dateTo != null && dateFrom != null) {
				partcipantList = participantService.getParticipantByDealerId(dealerList,dateFrom,dateTo);
			}else {
				System.out.println("Cale........."+dealerList);
			partcipantList = participantService.getParticipantByDealerId(dealerList);
			}
			model.addAttribute("assessList", getPaertcipant(partcipantList));
			// For Search
			filterPayload.setDealershipCode(dealershipCode);
			filterPayload.setRegionCode(regionCode);
			filterPayload.setStateCode(stateCode);
			filterPayload.setCityCode(cityCode);
			filterPayload.setOutletCode(outletCode);
			filterPayload.setDateFrom(dateFromm);
			filterPayload.setDateTo(dateToo);
			List<Dealer> dealers2 = dealers.stream().sorted((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()))
					.collect(Collectors.toList());
			List<Region> regions2 = regions.stream()
					.sorted((r1, r2) -> r1.getRegionCode().compareToIgnoreCase(r2.getRegionCode()))
					.collect(Collectors.toList());
			List<State> states2 = states.stream()
					.sorted((s1, s2) -> s1.getStateName().compareToIgnoreCase(s2.getStateName()))
					.collect(Collectors.toList());
			List<City> cities2 = cities.stream()
					.sorted((c1, c2) -> c1.getCityName().compareToIgnoreCase(c2.getCityName()))
					.collect(Collectors.toList());
			List<Outlet> outlets2 = outlets.stream()
					.sorted((o1, o2) -> o1.getOutletName().compareToIgnoreCase(o2.getOutletName()))
					.collect(Collectors.toList());
			model.addAttribute("dealers", dealers2);
			model.addAttribute("regions", regions2);
			model.addAttribute("states", states2);
			model.addAttribute("cities", cities2);
			model.addAttribute("dealerCodeList", outlets2);
			model.addAttribute("payload", filterPayload);
			DataProccessor.setDateRange(model);
			return "assessmentReport";
		} else {
			return "redirect:login";
		}
	}
	
	private List<AssessmentReport> getPaertcipant(List<ParticipantRegistration> partcipantList){
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
			assess.setRegistrationDate(formatter.format(p.getRegistration_date()));
			assess.setAssessmentDate(formatter.format(p.getTestCompletionDate()));
			assess.setTotalMark(p.getTotalMark());
			assess.setMarkObtained(p.getTestScore());
			assess.setPercentage(p.getPercentageScore());
			//assess.setState(p.getTestStatus());
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
			List<QuestiwiseReport> listReport = questiwiseReportService.getByAcesskey(p.getAccessKey());
			for (QuestiwiseReport q : listReport) {
				opList.add(q.getOption());
			}
			assess.setList(opList);
			assessList.add(assess);
		}
		return assessList;
	}
	
	
}
