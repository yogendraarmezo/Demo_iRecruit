package com.msil.irecruit.analytics.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
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
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParentDealer;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.State;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParentDealerService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.analytics.entity.AnalyticsAll;
import com.msil.irecruit.analytics.payload.ActionPointsPayload;
import com.msil.irecruit.analytics.payload.AgeWisePayload;
import com.msil.irecruit.analytics.payload.AssessmentReportPayload;
import com.msil.irecruit.analytics.payload.CandidateExperiencePayload;
import com.msil.irecruit.analytics.payload.CandidateNonAutoExperiencePayload;
import com.msil.irecruit.analytics.payload.DesignationTypePayload;
import com.msil.irecruit.analytics.payload.GenderDiversityPayload;
import com.msil.irecruit.analytics.payload.OverviewPayload;
import com.msil.irecruit.analytics.payload.RecruitmentSourcePayload;
import com.msil.irecruit.analytics.service.AnalyticsAllService;
import com.msil.irecruit.dms.entities.PrarambhStatus;
import com.msil.irecruit.dms.service.PrarambhStatusService;
import com.msil.irecruit.payload.DashboardFilterPayload;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
//@RequestMapping("/analytics")
public class AnalyticsController {

	@Autowired
	private AnalyticsAllService allService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private OutletService outletService;
	@Autowired
	private InterviewScoreService interviewService;
	@Autowired
	private FSDMService fsdmService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private PrarambhStatusService prarambhService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private ParentDealerService parentDealerService;

	// Insert into Analytics Table From Participants Table
	private void insertAnalyticsData(HttpSession session, List<Long> dealerIdList) {
		// removing duplicated ids
		Set<Long> set = new LinkedHashSet<Long>();
		set.addAll(dealerIdList);
		dealerIdList.clear();
		dealerIdList.addAll(set);
		List<AnalyticsAll> analyticsAlls = new ArrayList<AnalyticsAll>();
		List<String> accessKeyList = allService.getAccessKeyList(dealerIdList);
		List<ParticipantRegistration> partList = participantService.getParticipantByDealerIdLsit(dealerIdList);
//		for(ParticipantRegistration p : partList) {
//			if(p.getStatus()!=null && p.getStatus().equalsIgnoreCase("H")) {
//				partList.remove(p);
//			}
//		}
		for (Iterator iterator = partList.iterator(); iterator.hasNext();) {
			ParticipantRegistration p = (ParticipantRegistration) iterator.next();
			if (p.getStatus() != null && p.getStatus().equalsIgnoreCase("H")) {

				Optional<AnalyticsAll> ana = allService.getAnalyticsByAccesskey(p.getAccessKey(), p.getDealerId(),
						p.getId());
				if (ana.isPresent()) {
					allService.deleteById(ana.get().getId());
				}
				iterator.remove();
			}
		}
		if (!partList.isEmpty()) {
			for (ParticipantRegistration part : partList) {
				String autoExp = "", nonAutoExp = "", interviewStatus = "", assessmentStatus = "";
				AnalyticsAll analytics = new AnalyticsAll();
				if (accessKeyList.contains(part.getAccessKey())) {
					Optional<AnalyticsAll> a = allService.getAnalyticsByAccesskey(part.getAccessKey(),
							part.getDealerId(), part.getId());
					if (a.isPresent()) {
						analytics = a.get();
						analytics.setId(a.get().getId());

					}
				}
				analytics.setParticipantId(part.getId());
				analytics.setDealerId(part.getDealerId());
				analytics.setAccesskey(part.getAccessKey());
				analytics.setRegistered(part.getRegStatus());
				analytics.setAssessments(part.getTestStatus());
				analytics.setPassFailStatus(String.valueOf(part.getPassFailStatus()));
				/*
				 * if (part.getRegStatus() != null) { if (part.getTestStatus() != null &&
				 * part.getTestStatus().equals("3")) { assessmentStatus = "Completed"; } else {
				 * assessmentStatus = "Not Completed"; } }
				 */
				analytics.setAssessmentStatus(part.getTestStatus());

				Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(part.getOutletCode(),
						part.getDealerId());
				if (outlet.isPresent()) {
					analytics.setDealerCode(outlet.get().getOutletCode());
					analytics.setDealerName(outlet.get().getDealer().getName());
					analytics.setRecruitDate(part.getRegistration_date());
					analytics.setRecSource(part.getSource());
					analytics.setRegion(outlet.get().getRegion().getRegionCode());
					analytics.setState(outlet.get().getState().getStateCode());
					analytics.setCity(outlet.get().getCity().getCityCode());
					analytics.setDealershipName(outlet.get().getOutletCode());
				}
				if (part.getAutoIndustryExperience() != null) {
					autoExp = part.getAutoIndustryExperience().toString();
				}
				if (part.getNonAutoIndustryExperience() != null) {
					nonAutoExp = part.getNonAutoIndustryExperience().toString();
				}
				analytics.setCandidateExperience(autoExp);
				analytics.setCandidateExperienceNonAuto(nonAutoExp);
				analytics.setAssessmentReport(part.getPercentageScore());
				analytics.setDesignationType(part.getDesignation()); // Sales or Non Sales type only //Final Designation
				Date birthDate = new Date();
				if (part.getBirthDate() != null) {
					birthDate = DataProccessor.parseDate(part.getBirthDate());
				}
				if (birthDate != null)
					analytics.setAge(DataProccessor.BirthDateInYearsConversion(birthDate));
				analytics.setGender(part.getGender());
				Optional<InterviewScore> iScore = interviewService.findByAccesskey(part.getAccessKey());
				if (part.getPassFailStatus() == 1) {
					if (iScore.isPresent()) {
						interviewStatus = "Completed";
						InterviewScore is = iScore.get();
						if ((is.getStatus() != null && is.getStatus().equalsIgnoreCase("final"))
								&& (is.getPass_fail_status() != null
										&& is.getPass_fail_status().equalsIgnoreCase("pass"))) {
							analytics.setOfferStatus("Yes");
						}
						if (iScore.get().getStatus() == null || iScore.get().getStatus().equals("save")) {
							interviewStatus = "Pending";
						}
					} else {
						interviewStatus = "Pending";
					}
				}
				if (part.getFsdmApprovalStatus() != null && part.getFsdmApprovalStatus().equals("2")) {
					analytics.setRecruitedStatus("Yes");
				}

				analytics.setInterviewStatus(interviewStatus);
				if (iScore.isPresent() && iScore.get().getPass_fail_status() != null
						&& iScore.get().getPass_fail_status().equals("pass")) {
					if (part.getDocuments_status() != null && (part.getDocuments_status().equalsIgnoreCase("save")
							|| part.getDocuments_status().equalsIgnoreCase("final"))) {
						analytics.setDocumentUploadStatus("1");
					} else {
						analytics.setDocumentUploadStatus("2");
					}
				}
				if (part.getMspin() != null && part.getMspin() != "" && part.getFinalDesignation() != null
						&& part.getFinalDesignation().equalsIgnoreCase("STR")) {
					Optional<PrarambhStatus> ps = prarambhService.getByMspin(part.getMspin());
					if (ps.isPresent()) {
						String prarambhS = ps.get().getPrarambhStatus();
						analytics.setPrarambhStatus(prarambhS);
					}
				}
				analytics.setFsdmApproval(part.getFsdmApprovalStatus());
				analytics.setFinalDesignation(part.getFinalDesignation());
				analytics.setMspin(part.getMspin());
				// setting popup data
				analytics.setFirstName(part.getFirstName());
				analytics.setMiddleName(part.getMiddleName());
				analytics.setLastName(part.getLastName());
				analytics.setMarksObtained(part.getTestScore());
				analytics.setMobile(part.getMobile());
				analytics.setRegistrationDate(part.getRegistration_date());
				analytics.setAssessmentDate(part.getTestCompletionDate());
				if (part.getModifiedDate() != null) {
					analytics.setModifiedDate(part.getModifiedDate());
				} else {
					analytics.setModifiedDate(part.getRegistration_date());
				}
				analytics.setInterviewDate(part.getInterviewDate());
				analyticsAlls.add(analytics);
			} // for
			allService.saveAll(analyticsAlls);
		}

	}

	@GetMapping("/analytics")
	public String showAllAnalytics(Model model, HttpSession session) {
		if (session.getAttribute("role") != null) {
			Set<Dealer> dealers = new LinkedHashSet<>();
			Set<Outlet> outlets = new LinkedHashSet<>();
			Set<Region> regions = new LinkedHashSet<>();
			Set<State> states = new LinkedHashSet<State>();
			Set<City> cities = new LinkedHashSet<City>();
			String role = session.getAttribute("role").toString();
			List<Long> dealerIdList = new ArrayList<Long>();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
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
				dealerIdList.add(dealerId);
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					regions.add(r);
					List<Outlet> outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet : outletList) {
						states.add(outlet.getState());
						cities.add(outlet.getCity());
						dealerIdList.add(outlet.getDealer().getId());
						dealers.add(outlet.getDealer());
						outlets.add(outlet);
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				List<Dealer> deal = dealerService.getAllDeealer();
				regions.addAll(regionService.getAllRegion());
				states.addAll(stateService.getAllState());
				cities.addAll(cityService.getAllCity());
				outlets.addAll(outletService.getAllOutlets());
				for (Dealer dealer : deal) {
					dealerIdList.add(dealer.getId());
					dealers.add(dealer);
				}
			} else {
				return "redirect:login";
			}
			// allService.getDataByAnyTypeLoginByDealerIds(dealerIdList);
			insertAnalyticsData(session, dealerIdList);
			
			// Date YTD wise Data
			Date dateFrom = new Date();
			Date dateTo = new Date();
			LocalDate currentDate = LocalDate.now();
			int currentMonthvalue = currentDate.getMonthValue();
			int yearValue = currentDate.getYear();
			LocalDate yearStartDate = null;
			if (currentMonthvalue < 4) {
				YearMonth yearMonth = YearMonth.of(yearValue - 1, 4);
				yearStartDate = yearMonth.atDay(1);
				dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			} else {
				YearMonth yearMonth = YearMonth.of(yearValue, 4);
				yearStartDate = yearMonth.atDay(1);
				dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
			//System.out.println("YTD Date : From > "+dateFrom+"<To Date >"+dateTo);
			// Get All objects
			OverviewPayload overview = allService.getOverviewAnalytics(dealerIdList,dateFrom,dateTo);
			ActionPointsPayload action = allService.getActionPending(dealerIdList,dateFrom,dateTo);
			RecruitmentSourcePayload source = allService.getRecruitmentSourceAnalytics(dealerIdList,dateFrom,dateTo);
			CandidateExperiencePayload exp = allService.getCandidateExpAnalytics(dealerIdList,dateFrom,dateTo);
			CandidateNonAutoExperiencePayload nonAuto = allService.getCandidateNonAutoExperience(dealerIdList,dateFrom,dateTo);
			AssessmentReportPayload assessment = allService.getAssessmentReportAnalytics(dealerIdList,dateFrom,dateTo);
			DesignationTypePayload designation = allService.getDesignationTypeAnalytics(dealerIdList,dateFrom,dateTo);
			AgeWisePayload age = allService.getAgeWiseAnalytics(dealerIdList,dateFrom,dateTo);
			GenderDiversityPayload gender = allService.getGenderAnalytics(dealerIdList,dateFrom,dateTo);

			model.addAttribute("overview", overview);
			model.addAttribute("action", action);
			model.addAttribute("source", source);
			model.addAttribute("experience", exp);
			model.addAttribute("expNonAuto", nonAuto);
			model.addAttribute("assessment", assessment);
			model.addAttribute("designation", designation);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);

			// For Search
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
			model.addAttribute("defaultDate", DataProccessor.getYTDDateInString());
			DataProccessor.setDateRange(model);
			return "deshboard";
		} else {
			return "redirect:login";
		}
	}

	// Search Option
	@PostMapping("/dashboardFilter")
	public String searchDashboard(@RequestParam("outletCode") String outletCode,
			@RequestParam("dealershipCode") String dealershipCode, @RequestParam("regionCode") String regionCode,
			@RequestParam("stateCode") String stateCode, @RequestParam("cityCode") String cityCode, @RequestParam("approved") String approved,
			@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) {
		if (session.getAttribute("userId") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DashboardFilterPayload filterPayload = new DashboardFilterPayload();
			Date date1 = null;
			Date date2 = null;
			Date dateFrom = new Date();
			Date dateTo = new Date();
			try {
				if (dateFromm != null && dateFromm != "") {
					date1 = sdf.parse(dateFromm);
				}
				if (dateToo != null && dateToo != "") {
					date2 = sdf.parse(dateToo);
					date2 = DataProccessor.addTimeInDate(date2);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//YTD Date By Default
			LocalDate currentDate = LocalDate.now();
			int currentMonthvalue = currentDate.getMonthValue();
			int yearValue = currentDate.getYear();
			LocalDate yearStartDate = null;
			if (date1 != null && date2 != null) {
				dateFrom = date1;
				dateTo = date2;
			} else {
				if (currentMonthvalue < 4) {
					YearMonth yearMonth = YearMonth.of(yearValue - 1, 4);
					yearStartDate = yearMonth.atDay(1);
					dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				} else {
					YearMonth yearMonth = YearMonth.of(yearValue, 4);
					yearStartDate = yearMonth.atDay(1);
					dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				}
			}

			/*
			 * String dateRange2 = dateRange.substring(0, 11); if
			 * (!dateRange2.equals("Select Date")) { String[] splitDate =
			 * dateRange.split("-"); try { dateFrom = sdf.parse(splitDate[0].trim()); dateTo
			 * = sdf.parse(splitDate[1].trim()); dateTo =
			 * DataProccessor.addTimeInDate(dateTo); } catch (ParseException e1) {
			 * e1.printStackTrace(); } }
			 */
			if (outletCode == null)
				outletCode = null;
			if (regionCode == null) {
				regionCode = null;
			}
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
			}
			// Get All objects
			OverviewPayload overview = allService.getOverviewAnalytics(outletCode, dealershipCode, regionCode,
					stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			ActionPointsPayload action = allService.getActionPending(outletCode, dealershipCode, regionCode, stateCode,
					cityCode, approved, dateFrom, dateTo, dealerIdList);
			RecruitmentSourcePayload source = allService.getRecruitmentSourceAnalytics(outletCode, dealershipCode,
					regionCode, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			CandidateExperiencePayload exp = allService.getCandidateExpAnalytics(outletCode, dealershipCode, regionCode,
					stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			CandidateNonAutoExperiencePayload nonAuto = allService.getCandidateNonAutoExperience(outletCode,
					dealershipCode, regionCode, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			AssessmentReportPayload assessment = allService.getAssessmentReportAnalytics(outletCode, dealershipCode,
					regionCode, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			DesignationTypePayload designation = allService.getDesignationTypeAnalytics(outletCode, dealershipCode,
					regionCode, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			AgeWisePayload age = allService.getAgeWiseAnalytics(outletCode, dealershipCode, regionCode, stateCode,
					cityCode, approved, dateFrom, dateTo, dealerIdList);
			GenderDiversityPayload gender = allService.getGenderAnalytics(outletCode, dealershipCode, regionCode,
					stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
			model.addAttribute("overview", overview);
			model.addAttribute("action", action);
			model.addAttribute("source", source);
			model.addAttribute("experience", exp);
			model.addAttribute("expNonAuto", nonAuto);
			model.addAttribute("assessment", assessment);
			model.addAttribute("designation", designation);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);
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
			model.addAttribute("defaultDate", DataProccessor.getYTDDateInString());
			DataProccessor.setDateRange(model);
			return "deshboard";
		} else {
			return "redirect:login";
		}
	}

	// Get Data By accesskey
	@GetMapping("/analyticsByAccesskey")
//	@ResponseBody
	public String getParticipantByAccesskeyList(@RequestParam String accesskeyList, @RequestParam String status,
			Model model, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			List<String> accesskeyList2 = new ArrayList<String>();
			if (accesskeyList != null && accesskeyList.length() > 5) {
				String[] array = accesskeyList.split(",");
				for (String s : array) {
					s = s.replaceAll("\"", "");
					s = s.replaceAll("\\s+", "");
					accesskeyList2.add(s);
				}
			}
			List<ModelParticpantView> mpvList = new ArrayList<ModelParticpantView>();
			List<AnalyticsAll> list = allService.getAllAnalyticsByAccesskeyList(accesskeyList2);
			Collections.sort(list, (o1, o2) -> o2.getRegistrationDate().compareTo(o1.getRegistrationDate()));
			for (AnalyticsAll a : list) {
				ModelParticpantView mpv = new ModelParticpantView();
				String name = "";
				name = a.getFirstName();
				if (a.getMiddleName() != null) {
					name += " " + a.getMiddleName();
				}
				name += " " + a.getLastName();
				mpv.setParticipantName(name);
				mpv.setDesignation(a.getDesignationType());
				mpv.setMobile(a.getMobile());
				mpv.setAccesskey(a.getAccesskey());
				if (a.getRegistrationDate() != null) {
					mpv.setDateOfRegistration(DataProccessor.dateToString(a.getRegistrationDate()));
				}
				if (a.getAssessmentDate() != null) {
					mpv.setAssessment_Completion_date(DataProccessor.dateToString(a.getAssessmentDate()));
				}
				mpv.setTestScore(a.getMarksObtained());
				if (a.getPassFailStatus() != null && a.getPassFailStatus() != "") {
					mpv.setPassFailStatus(Integer.valueOf(a.getPassFailStatus()));
				}
				// Based on Status adding Status
				if (status.equalsIgnoreCase("Overview")) {
					if (a.getRegistered() != null && Integer.valueOf(a.getRegistered()) >= 1) {
						mpv.setStatus("Registered");
					}
					if (a.getAssessments() != null && a.getAssessments().equals("3")) {
						mpv.setStatus("Assessment");
					}
					if (a.getPassFailStatus() != null && a.getPassFailStatus().equals("1")) {
						mpv.setStatus("Passed");
					}
					if (a.getOfferStatus() != null && a.getOfferStatus().equalsIgnoreCase("Yes")) {
						mpv.setStatus("Offered");
					}
					if (a.getRecruitedStatus() != null && a.getRecruitedStatus().equalsIgnoreCase("Yes")) {
						mpv.setStatus("Recruited");
					}
				} else if (status.equalsIgnoreCase("Action")) {
					if ((a.getAssessmentStatus() == null || a.getAssessmentStatus() == ""
							|| !(a.getAssessmentStatus() != null && a.getAssessmentStatus().equals("3")))
							&& (a.getRegistered() != null && Integer.valueOf(a.getRegistered()) >= 1)) {
						mpv.setStatus("Assessment");
					}
					if (a.getInterviewStatus().equals("Pending")) {
						mpv.setStatus("Interview");
					}
					if (a.getDocumentUploadStatus() != null && a.getDocumentUploadStatus().equals("2")) {
						mpv.setStatus("Document");
					}
					if (a.getMspin() != null && a.getFinalDesignation() != null
							&& a.getFinalDesignation().equalsIgnoreCase("STR") && a.getPrarambhStatus() == null) {
						mpv.setStatus("Praraambh");
					}
					if (a.getFsdmApproval() != null && a.getFsdmApproval().equals("3")) {
						mpv.setStatus("Approval");
					}
				} else if (status.equalsIgnoreCase("Source")) {
					if (a.getRecSource() != null) {
						if (a.getRecSource().equalsIgnoreCase("Referrals"))
							mpv.setStatus("Referrals");
						if (a.getRecSource().equalsIgnoreCase("Direct Walk-in"))
							mpv.setStatus("Direct Walk-in");
						if (a.getRecSource().equalsIgnoreCase("Advertisement"))
							mpv.setStatus("Advertisement");
						if (a.getRecSource().equalsIgnoreCase("Job Consultant"))
							mpv.setStatus("Job Consultant");
						if (a.getRecSource().equalsIgnoreCase("Social Media"))
							mpv.setStatus("Social Media");
						if (a.getRecSource().equalsIgnoreCase("Others"))
							mpv.setStatus("Others");
					}
				} else if (status.equalsIgnoreCase("Auto")) {
					if (a.getCandidateExperience() != null && a.getCandidateExperience().length() > 0) {
						if (Integer.valueOf(a.getCandidateExperience()) < 3)
							mpv.setStatus("Between 0-3 Months");
						if (Integer.valueOf(a.getCandidateExperience()) >= 3
								&& Integer.valueOf(a.getCandidateExperience()) < 6)
							mpv.setStatus("Between 3-6 Months");
						if (Integer.valueOf(a.getCandidateExperience()) >= 6
								&& Integer.valueOf(a.getCandidateExperience()) < 12)
							mpv.setStatus("Between 6-12 Months");
						if (Integer.valueOf(a.getCandidateExperience()) >= 12
								&& Integer.valueOf(a.getCandidateExperience()) < 24)
							mpv.setStatus("Between 1-2 Years");
						if (Integer.valueOf(a.getCandidateExperience()) >= 24
								&& Integer.valueOf(a.getCandidateExperience()) < 60)
							mpv.setStatus("Between 2-5 Years");
						if (Integer.valueOf(a.getCandidateExperience()) >= 60
								&& Integer.valueOf(a.getCandidateExperience()) < 120)
							mpv.setStatus("Between 5-10 Years");
						if (Integer.valueOf(a.getCandidateExperience()) >= 120)
							mpv.setStatus("More Than 10 Years");
					}
				} else if (status.equalsIgnoreCase("NonAuto")) {
					if (a.getCandidateExperienceNonAuto() != null && a.getCandidateExperienceNonAuto().length() > 0) {
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) < 3)
							mpv.setStatus("Between 0-3 Months");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 3
								&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 6)
							mpv.setStatus("Between 3-6 Months");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 6
								&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 12)
							mpv.setStatus("Between 6-12 Months");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 12
								&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 24)
							mpv.setStatus("Between 1-2 Years");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 24
								&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 60)
							mpv.setStatus("Between 2-5 Years");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 60
								&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 120)
							mpv.setStatus("Between 5-10 Years");
						if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 120)
							mpv.setStatus("More Than 10 Years");
					}
				} else if (status.equalsIgnoreCase("Assessment")) {
					if (a.getAssessmentReport() != null && a.getAssessmentReport().length() > 0) {
						if (Integer.valueOf(a.getAssessmentReport()) < 40)
							mpv.setStatus("Less than 40");
						if (Integer.valueOf(a.getAssessmentReport()) >= 40
								&& Integer.valueOf(a.getAssessmentReport()) < 60)
							mpv.setStatus("Between 40-60");
						if (Integer.valueOf(a.getAssessmentReport()) >= 60
								&& Integer.valueOf(a.getAssessmentReport()) < 80)
							mpv.setStatus("Between 60-80");
						if (Integer.valueOf(a.getAssessmentReport()) > 80)
							mpv.setStatus("More than 80");
					} else if (status.equalsIgnoreCase("Designation")) {
						if (a.getDesignationType() != null && a.getDesignationType().length() > 0) {
							if (a.getDesignationType().equalsIgnoreCase("Sales"))
								mpv.setStatus("Sales");
							if (a.getDesignationType().equalsIgnoreCase("Sales Support"))
								mpv.setStatus("Sales Support");
						}
					} else if (status.equalsIgnoreCase("Gender")) {
						if (a.getGender() != null && a.getGender().length() > 0) {
							if (a.getGender().equalsIgnoreCase("Male") || a.getGender().equalsIgnoreCase("M"))
								mpv.setStatus("Male");
							if (a.getGender().equalsIgnoreCase("Female") || a.getGender().equalsIgnoreCase("F"))
								mpv.setStatus("Female");
							if (a.getGender().equalsIgnoreCase("Other") || a.getGender().equalsIgnoreCase("Transgender")
									|| a.getGender().equalsIgnoreCase("U"))
								mpv.setStatus("Others");
						}
					} else if (status.equalsIgnoreCase("Age")) {
						if (a.getAge() != null && a.getAge().length() > 0) {
							if (Integer.valueOf(a.getAge()) < 20)
								mpv.setStatus("Between 18-20");
							if (Integer.valueOf(a.getAge()) >= 20 && Integer.valueOf(a.getAge()) < 25)
								mpv.setStatus("Between 20-25");
							if (Integer.valueOf(a.getAge()) >= 25 && Integer.valueOf(a.getAge()) < 30)
								mpv.setStatus("Between 25-30");
							if (Integer.valueOf(a.getAge()) >= 30 && Integer.valueOf(a.getAge()) < 35)
								mpv.setStatus("Between 30-35");
							if (Integer.valueOf(a.getAge()) >= 35 && Integer.valueOf(a.getAge()) < 40)
								mpv.setStatus("Between 35-40");
							if (Integer.valueOf(a.getAge()) >= 40)
								mpv.setStatus("More than 40");
						}
					}
				}
				mpvList.add(mpv);
			}
			model.addAttribute("participantList", mpvList);
			model.addAttribute("status", status);
			return "dashboardData";
		} // session
		else {
			return "redirect:login";
		}
	}

	// Dynamic Drop down
	@PostMapping("/stateByRegionCode")
	@ResponseBody
	public String getStateByRegion(@RequestParam String regionCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String role = session.getAttribute("role").toString();
			String stateOption = "<option value=''>State</option>";
			Set<State> states = new LinkedHashSet<State>();
			List<Outlet> outletList = new ArrayList<Outlet>();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				outletList = outletService.getOutletByDealerId(dealerId);
				// outletList.stream().map(Outlet::getState).forEachOrdered(states::add);
			} else if (role.equalsIgnoreCase("FS")) {
				if (regionCode != null && regionCode != "") {
					outletList = outletService.getOutletByRegionCode(regionCode);
				} else {
					int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
					Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
					for (Region r : f.get().getRegion()) {
						outletList = outletService.getOutletByRegion(r.getId());
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				if (regionCode != null && regionCode != "") {
					outletList = outletService.getOutletByRegionCode(regionCode);
				} else {
					states.addAll(stateService.getAllState());
				}
			}
			outletList.stream().map(Outlet::getState).forEachOrdered(states::add);
			List<State> states2 = new ArrayList<State>();
			if(regionCode!=null && regionCode!="") {
			states2 = stateService.getStateByRegionCode(regionCode).stream()
					.sorted((s1, s2) -> s1.getStateName().compareToIgnoreCase(s2.getStateName()))
					.collect(Collectors.toList());
			}else {
				states2=states.stream().sorted((s1,s2)->s1.getStateName().compareToIgnoreCase(s2.getStateName())).collect(Collectors.toList());
			}
			for (State s : states2) {
				stateOption = String.valueOf(stateOption) + "<option value='" + s.getStateCode() + "'>"
						+ s.getStateName() + "</option>";
			}
			return stateOption;
		} else {
			return "redirect:login";
		}
	}

	@PostMapping("/cityByStateCode")
	@ResponseBody
	public String getCityByStateCode(@RequestParam String stateCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String cityOption = "<option value=''>City </option>";
			Set<City> cities = new LinkedHashSet<>();
			Set<Outlet> outlets = new LinkedHashSet<>();
			String role = session.getAttribute("role").toString();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				outlets.addAll(outletService.getOutletByDealerId(dealerId));
			} else if (role.equalsIgnoreCase("FS")) {
				if (stateCode != null && stateCode != "") {
					outlets.addAll(outletService.getOutletByStateCode(stateCode));
				} else {
					int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
					Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
					for (Region r : f.get().getRegion()) {
						outlets.addAll(outletService.getOutletByRegion(r.getId()));
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				if (stateCode != null && stateCode != "") {
					outlets.addAll(outletService.getOutletByStateCode(stateCode));
				} else {
					cities.addAll(cityService.getAllCity());
				}
			}
			outlets.stream().map(Outlet::getCity).forEachOrdered(cities::add);
			List<City> cities2 = new ArrayList<City>();
			if(stateCode!=null && stateCode!="") {
				cities2=cityService.getAllCityByStateCode(stateCode).stream()
						.sorted((c1, c2) -> c1.getCityName().compareToIgnoreCase(c2.getCityName()))
						.collect(Collectors.toList());
			}else {
				cities2=cities.stream()
						.sorted((c1, c2) -> c1.getCityName().compareToIgnoreCase(c2.getCityName()))
						.collect(Collectors.toList());
			}
			for (City c : cities2) {
				cityOption = String.valueOf(cityOption) + "<option value='" + c.getCityCode() + "'>" + c.getCityName()
						+ "</option>";
			}
			return cityOption;
		} else {
			return "redirect:login";
		}
	}

	@PostMapping("/dealershipByCityCode")
	@ResponseBody
	public String getDealersByCityCode(@RequestParam String cityCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String dealershipOption = "<option value=''>Dealership </option>";
			Set<Outlet> outlets = new LinkedHashSet<>();
			List<String> cityCodeList = new ArrayList<>();
			cityCodeList.add(cityCode);
			String role = session.getAttribute("role").toString();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				outlets.addAll(outletService.getOutletByDealerId(dealerId));
			} else if (role.equalsIgnoreCase("FS")) {
				if (cityCode != null && cityCode != "") {
					outlets.addAll(outletService.getOutletByCityCodes(cityCodeList));
				} else {
					int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
					Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
					for (Region r : f.get().getRegion()) {
						outlets.addAll(outletService.getOutletByRegion(r.getId()));
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				if (cityCode != null && cityCode != "") {
					outlets.addAll(outletService.getOutletByCityCodes(cityCodeList));
				} else {
					outlets.addAll(outletService.getAllOutlets());
				}
			}
			// Adding Dealership name in a Set
			// Set<String> setName = new LinkedHashSet<String>();
			// outlets.stream().map(Outlet::getOutletName).forEachOrdered(setName::add);
			// List<String> dealershipName =
			// setName.stream().sorted((n1,n2)->n1.compareToIgnoreCase(n2)).collect(Collectors.toList());
			// outlets.stream().map(Outlet::getDealer).forEachOrdered(dealers::add);
			List<Outlet> outlets2 = outlets.stream()
					.sorted((o1, o2) -> o1.getOutletName().compareToIgnoreCase(o2.getOutletName()))
					.collect(Collectors.toList());
			for (Outlet d : outlets2) {
				dealershipOption = String.valueOf(dealershipOption) + "<option value='" + d.getOutletCode() + "'>"
						+ d.getOutletName() + "</option>";
			}
			return dealershipOption;
		} // session
		else {
			return "redirect:login";
		}
	}

	@PostMapping("/outletByOutletCode")
	@ResponseBody
	public String getoutletByOutletCode(@RequestParam String outletCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String role = session.getAttribute("role").toString();
			Set<Outlet> outlets = new LinkedHashSet<Outlet>();
			if (role.equalsIgnoreCase("DL")) {
				Long dId = Long.parseLong(session.getAttribute("userId").toString());
				if (outletCode != null && outletCode != "") {
					Optional<Outlet> out = outletService.getOutletByOutletCodeAndDealerId(outletCode, dId);
					if (out.isPresent()) {
						outlets.add(out.get());
					}
				} else {
					outlets.addAll(outletService.findByDealerId(dId));
				}
			} else if (role.equalsIgnoreCase("FS")) {
				if (outletCode != null && outletCode != "") {
					Optional<Outlet> out = outletService.getOutletByOutletCode(outletCode);
					if (out.isPresent()) {
						outlets.add(out.get());
					}
				} else {
					int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
					Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
					for (Region r : f.get().getRegion()) {
						outlets.addAll(outletService.getOutletByRegion(r.getId()));
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				if (outletCode != null && outletCode != "") {
					Optional<Outlet> out = outletService.getOutletByOutletCode(outletCode);
					if (out.isPresent()) {
						outlets.add(out.get());
					}
				} else {
				outlets.addAll(outletService.getAllOutlets());
				}
			}
			String outletOption = "<option value=''>Dealer Code </option>";
			List<Outlet> outList = new ArrayList<Outlet>();
			outList=outlets.stream().sorted((o1,o2)->o1.getOutletName().compareToIgnoreCase(o2.getOutletName())).collect(Collectors.toList());
			for (Outlet o : outList) {
				outletOption = String.valueOf(outletOption) + "<option value='" + o.getOutletCode() + "'>"
						+ o.getOutletCode() + "</option>";
			}
			return outletOption;
		} // session
		else {
			return "redirect:login";
		}
	}
	//FSDM by RegionCode
	@PostMapping("/fsdmByRegionCode")
	@ResponseBody
	public String getFSDMByRegionCode(@RequestParam String regionCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String fsdmOption = "<option value=''>FSDM </option>";
			List<FSDM> fsdms = new ArrayList<FSDM>();
			if(regionCode!=null && regionCode!="") {
				Optional<Region> region =regionService.getReagion(regionCode);
				if(region.isPresent()) {
					fsdms.add(region.get().getFsdm());
				}
			}else {
				fsdms=fsdmService.getAllFSDM();
			}
			List<FSDM> fsdms2 = fsdms.stream().sorted((f1,f2)-> f1.getName().compareToIgnoreCase(f2.getName())).collect(Collectors.toList());
			for(FSDM f : fsdms2) {
				fsdmOption=String.valueOf(fsdmOption)+"<option value='"+f.getId()+"'>"
						+f.getName()+"</option>";
			}
			return fsdmOption;
		}else {
			return "redirect:login";
		}
	}
	
	//Parent Dealer by CityCode
	@PostMapping("/parentDealerByCityCode")
	@ResponseBody
	public String getParentDealerByCityCode(@RequestParam String cityCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String pdOption = "<option value=''>Parent Dealer</option>";
			List<Outlet> outlets = new ArrayList<Outlet>();
			List<String> cityCodeList = new ArrayList<String>();
			Set<ParentDealer> parentDealers = new LinkedHashSet<ParentDealer>();
			if(cityCode!=null && cityCode!="") {
				cityCodeList.add(cityCode);
				outlets=outletService.getOutletByCityCodes(cityCodeList);
				outlets.stream().map(Outlet::getParentDealer).forEachOrdered(parentDealers::add);
			}else {
				parentDealers.addAll(parentDealerService.getAllParentDealer());
			}
			List<ParentDealer> pDealers = parentDealers.stream().sorted((p1,p2)->p1.getParentDealerName().compareToIgnoreCase(p2.getParentDealerName())).collect(Collectors.toList());
			for(ParentDealer p : pDealers) {
				pdOption=String.valueOf(pdOption)+"<option value='"+p.getParentDealerCode()+"'>"
						+p.getParentDealerCode()+"-"+p.getParentDealerName()+"</option>";
			}
			return pdOption;
		}else {
			return "redirect:login";
		}
	}
	// FSDM by Parent Dealer Code
	@PostMapping("/fsdmByParentDealer")
	@ResponseBody
	public String getFSDMByParentDealer(@RequestParam String pdCode) {
		String fsdmOption = "<option value=''>FSDM</option>";
		Set<FSDM> fsdms = new LinkedHashSet<FSDM>();
		List<Outlet> outlets = new ArrayList<Outlet>();
		List<String> pdCodeList = new ArrayList<String>();
		if(pdCode!=null && pdCode!="") {
			pdCodeList.add(pdCode);
			outlets = outletService.getOutletsByPDCodes(pdCodeList);
			outlets.stream().map(Outlet::getRegion).map(Region::getFsdm).forEachOrdered(fsdms::add);
			}
		
		for(FSDM f : fsdms) {
			fsdmOption=String.valueOf(fsdmOption)+"<option value='"+f.getId()+"'>"
					+f.getName()+"</option>";
		}
		
		return fsdmOption;
	}
	//Get Dealer By FSDM
	@PostMapping("/dealerByFSDM")
	@ResponseBody
	public String getDealerByFSDMId(@RequestParam String fsdmId ) {
		String dealerOption = "<option value=''>Dealer</option>";
		Integer fId = null;
		Set<Outlet> outlets = new LinkedHashSet<Outlet>();
		Set<Dealer> dealers = new LinkedHashSet<Dealer>();
		
		if(fsdmId!=null && fsdmId!="") {
			fId=Integer.valueOf(fsdmId);
			//Get Outlet by FID
			List<Region> regions =regionService.getRegionByFSDMId(fId);
			for(Region r : regions) {
				outlets.addAll(outletService.getOutletByRegionCode(r.getRegionCode()));
			}
			outlets.stream().map(Outlet::getDealer).forEachOrdered(dealers::add);
			List<Dealer> dealers2 = dealers.stream().sorted((d1,d2)->d1.getName().compareToIgnoreCase(d2.getName())).collect(Collectors.toList());
			for(Dealer d : dealers2) {
				dealerOption=String.valueOf(dealerOption)+"<option value='"+d.getId()+"'>"+d.getName()+"</option>";
			}		
		}
		return dealerOption;
	}
	
	//Dealer By Parent Dealer Code
	@PostMapping("/dealerByParentDealerCode")
	@ResponseBody
	public String getDealerByParentDealerCode(@RequestParam String pdCode, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			String dealerOption = "<option value=''>Dealer</option>";
			Set<Dealer> dealers = new LinkedHashSet<Dealer>();
			List<Outlet> outlets = new ArrayList<Outlet>();
			List<String> pdCodeList = new ArrayList<String>();
			if(pdCode!=null && pdCode!="") {
				pdCodeList.add(pdCode);
				outlets= outletService.getOutletsByPDCodes(pdCodeList);
				outlets.stream().map(Outlet::getDealer).forEachOrdered(dealers::add);
			}else {
				dealers.addAll(dealerService.getAllDeealer());
			}
			List<Dealer> dealers2 = dealers.stream().sorted((d1,d2)->d1.getName().compareToIgnoreCase(d2.getName())).collect(Collectors.toList());
			for(Dealer d : dealers2) {
				dealerOption=String.valueOf(dealerOption)+"<option value='"+d.getId()+"'>"+d.getName()+"</option>";
			}			
			return dealerOption;
		}else {
			return "redirect:login";
		}
		
	}
	

}
