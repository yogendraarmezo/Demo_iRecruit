package com.msil.irecruit.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msil.irecruit.Entities.City;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.State;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.OutletService;
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
import com.msil.irecruit.payload.DashboardFilterPayload;
import com.msil.irecruit.tc.entities.ModelDashboardAggregate;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class DashboardReport {

	@Autowired
	DealerService dealerService;
	@Autowired
	OutletService outletService;
	@Autowired
	private FSDMService fsdmService;
	@Autowired
	RegionService regionService;
	@Autowired
	StateService stateService;
	@Autowired
	CityService cityService;
	@Autowired
	private AnalyticsAllService allService;

	// Show Links
	@GetMapping("/showAllLinksCSV")
	public String showLinksForCSV(@RequestParam String flag, HttpSession session, Model model) {
		if (session.getAttribute("role") != null) {
			Set<Dealer> dealers = new LinkedHashSet<>();
			Set<Outlet> outlets = new LinkedHashSet<>();
			Set<Region> regions = new LinkedHashSet<>();
			Set<State> states = new LinkedHashSet<State>();
			Set<City> cities = new LinkedHashSet<City>();
			List<Long> dealerIdList = new ArrayList<Long>();
			String role = session.getAttribute("role").toString();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				outlets.addAll(outletService.getOutletByDealerId(dealerId));
				dealerIdList.add(dealerId);
				for (Outlet o : outlets) {
					dealers.add(o.getDealer());
					regions.add(o.getRegion());
					states.add(o.getState());
					cities.add(o.getCity());
				}
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

			// // Aggregate CSV Data
			OverviewPayload overview = allService.getOverviewAnalytics(dealerIdList,dateFrom,dateTo);
			ActionPointsPayload action = allService.getActionPending(dealerIdList,dateFrom,dateTo);
			RecruitmentSourcePayload source = allService.getRecruitmentSourceAnalytics(dealerIdList,dateFrom,dateTo);
			CandidateExperiencePayload exp = allService.getCandidateExpAnalytics(dealerIdList,dateFrom,dateTo);
			CandidateNonAutoExperiencePayload nonAuto = allService.getCandidateNonAutoExperience(dealerIdList,dateFrom,dateTo);
			AssessmentReportPayload assessment = allService.getAssessmentReportAnalytics(dealerIdList,dateFrom,dateTo);
			DesignationTypePayload designation = allService.getDesignationTypeAnalytics(dealerIdList,dateFrom,dateTo);
			AgeWisePayload age = allService.getAgeWiseAnalytics(dealerIdList,dateFrom,dateTo);
			GenderDiversityPayload gender = allService.getGenderAnalytics(dealerIdList,dateFrom,dateTo);
			//Aggregates Data
			List<AnalyticsAll> analyticsAlls = allService.getDataByAnyTypeLoginByDealerIds(dealerIdList,dateFrom,dateTo);
			List<ModelDashboardAggregate> aggregates = getAggregateData(analyticsAlls);
			// Save Same type accesskeyList
			Set<String> overviewSet = new LinkedHashSet<String>();
			overviewSet.addAll(overview.getRegistered());
			overviewSet.addAll(overview.getAssessments());
			overviewSet.addAll(overview.getPass());
			overviewSet.addAll(overview.getOffer());
			overviewSet.addAll(overview.getRecruited());
			Set<String> actionSet = new LinkedHashSet<String>();
			actionSet.addAll(action.getAssessmentStatus());
			actionSet.addAll(action.getInterviewStatus());
			actionSet.addAll(action.getDocumentUploadStatus());
			actionSet.addAll(action.getPrarambhStatus());
			actionSet.addAll(action.getFsdmApproval());
			Set<String> sourceSet = new LinkedHashSet<String>();
			sourceSet.addAll(source.getAdvertisement());
			sourceSet.addAll(source.getDirectWalkIn());
			sourceSet.addAll(source.getReferrals());
			sourceSet.addAll(source.getJobConsultant());
			sourceSet.addAll(source.getOthers());
			sourceSet.addAll(source.getSocialMedia());
			Set<String> expAutoSet = new LinkedHashSet<String>();
			expAutoSet.addAll(exp.getLessThan3Months());
			expAutoSet.addAll(exp.getMonths3To6());
			expAutoSet.addAll(exp.getMonths6To1Year());
			expAutoSet.addAll(exp.getYear1To2Year());
			expAutoSet.addAll(exp.getYear2To5Year());
			expAutoSet.addAll(exp.getYear5To10Year());
			Set<String> expNonAutoSet = new LinkedHashSet<String>();
			expNonAutoSet.addAll(exp.getLessThan3Months());
			expNonAutoSet.addAll(exp.getMonths3To6());
			expNonAutoSet.addAll(exp.getMonths6To1Year());
			expNonAutoSet.addAll(exp.getYear1To2Year());
			expNonAutoSet.addAll(exp.getYear2To5Year());
			expNonAutoSet.addAll(exp.getYear5To10Year());
			Set<String> assessmentSet = new LinkedHashSet<String>();
			assessmentSet.addAll(assessment.getLessThan40());
			assessmentSet.addAll(assessment.getBetween40To60());
			assessmentSet.addAll(assessment.getBetween60To80());
			assessmentSet.addAll(assessment.getMoreThan80());
			Set<String> desgSet = new LinkedHashSet<String>();
			desgSet.addAll(designation.getSales());
			desgSet.addAll(designation.getNonSales());
			Set<String> ageSet = new LinkedHashSet<String>();
			ageSet.addAll(age.getLessThan20());
			ageSet.addAll(age.getBetween20To25());
			ageSet.addAll(age.getBetween25To30());
			ageSet.addAll(age.getBetween30To35());
			ageSet.addAll(age.getBetween35To40());
			ageSet.addAll(age.getMoreThan40());
			Set<String> genderSet = new LinkedHashSet<String>();
			genderSet.addAll(gender.getMale());
			genderSet.addAll(gender.getFemale());

			// Setting Dashboard Related Data In Model
			model.addAttribute("overview", overview);
			model.addAttribute("action", action);
			model.addAttribute("source", source);
			model.addAttribute("experience", exp);
			model.addAttribute("expNonAuto", nonAuto);
			model.addAttribute("assessment", assessment);
			model.addAttribute("designation", designation);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);
			//Aggregate Only
			model.addAttribute("aggregates", aggregates);
			// Aggregate CSV Data
			model.addAttribute("overviewSet", overviewSet);
			model.addAttribute("actionSet", actionSet);
			model.addAttribute("sourceSet", sourceSet);
			model.addAttribute("expAutoSet", expAutoSet);
			model.addAttribute("expNonAutoSet", expNonAutoSet);
			model.addAttribute("assessmentSet", assessmentSet);
			model.addAttribute("desgSet", desgSet);
			model.addAttribute("ageSet", ageSet);
			model.addAttribute("genderSet", genderSet);

			// Aggregate All Data of Candidate
			model.addAttribute("overviewAll", getParticipantAllDataByAccesskeyList(overviewSet, "Overview"));
			model.addAttribute("actionAll", getParticipantAllDataByAccesskeyList(actionSet, "Action"));
			model.addAttribute("sourceAll", getParticipantAllDataByAccesskeyList(sourceSet, "Source"));
			model.addAttribute("expAutoAll", getParticipantAllDataByAccesskeyList(expAutoSet, "Auto"));
			model.addAttribute("expNonAutoAll", getParticipantAllDataByAccesskeyList(expNonAutoSet, "NonAuto"));
			model.addAttribute("assessmentAll", getParticipantAllDataByAccesskeyList(assessmentSet, "Assessment"));
			model.addAttribute("desgAll", getParticipantAllDataByAccesskeyList(desgSet, "Designation"));
			model.addAttribute("ageAll", getParticipantAllDataByAccesskeyList(ageSet, "Age"));
			model.addAttribute("genderAll", getParticipantAllDataByAccesskeyList(genderSet, "Gender"));

			// Single Points Data Of Overview
			/*
			 * model.addAttribute("registeredAll",
			 * getParticipantAllDataByAccesskeyList(overview.getRegistered(),
			 * "Registered")); model.addAttribute("asmtAll",
			 * getParticipantAllDataByAccesskeyList(overview.getAssessments(),
			 * "Assessment")); model.addAttribute("passedAll",
			 * getParticipantAllDataByAccesskeyList(overview.getPass(), "Passed"));
			 * model.addAttribute("offeredAll",
			 * getParticipantAllDataByAccesskeyList(overview.getOffer(), "Offered"));
			 * model.addAttribute("recruitedAll",
			 * getParticipantAllDataByAccesskeyList(overview.getRecruited(), "Recruited"));
			 * 
			 * // Single Points Data Of Pending Action Points
			 * model.addAttribute("asmtPendingAll",
			 * getParticipantAllDataByAccesskeyList(action.getAssessmentStatus(),
			 * "Assessment")); model.addAttribute("interviewPendingAll",
			 * getParticipantAllDataByAccesskeyList(action.getInterviewStatus(),
			 * "Interview")); model.addAttribute("documentPendingAll",
			 * getParticipantAllDataByAccesskeyList(action.getDocumentUploadStatus(),
			 * "Document")); model.addAttribute("praraambhPendingAll",
			 * getParticipantAllDataByAccesskeyList(action.getPrarambhStatus(),
			 * "Praraambh")); model.addAttribute("fsdmPendingAll",
			 * getParticipantAllDataByAccesskeyList(action.getFsdmApproval(),
			 * "FSDM Approval")); // Single Points Data Of Recruited Source Points
			 * model.addAttribute("sourceReferrals",
			 * getParticipantAllDataByAccesskeyList(source.getReferrals(), "Referrals"));
			 * model.addAttribute("sourceAdvertisement",
			 * getParticipantAllDataByAccesskeyList(source.getAdvertisement(),
			 * "Advertisement")); model.addAttribute("sourceDirect",
			 * getParticipantAllDataByAccesskeyList(source.getDirectWalkIn(),
			 * "Direct Walk-In")); model.addAttribute("sourceConsultant",
			 * getParticipantAllDataByAccesskeyList(source.getJobConsultant(),
			 * "Job Consultant")); model.addAttribute("sourceSocial",
			 * getParticipantAllDataByAccesskeyList(source.getSocialMedia(),
			 * "Social Media")); model.addAttribute("sourceOtheres",
			 * getParticipantAllDataByAccesskeyList(source.getOthers(), "Otheres")); //
			 * Single Points Data Of Experience Points model.addAttribute("exp",
			 * getParticipantAllDataByAccesskeyList(exp.getLessThan3Months(),
			 * "Less Than 3 Months"));
			 * 
			 */

			// Search Filter Data
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
			DataProccessor.setDateRange(model);
			// Flag Data
			model.addAttribute("flag", flag);

		} else {
			return "redirect:login";
		}
		return "reports";
	}

	// Dash board Search Filter For reports
	@PostMapping("/dashboardReportFilter")
	public String getDashBoardReportFilterData(@RequestParam("outletCode") String outletCode,
			@RequestParam("dealershipCode") String dealershipCode, @RequestParam("regionCode") String regionCode,
			@RequestParam("stateCode") String stateCode, @RequestParam("cityCode") String cityCode, @RequestParam("approved") String approved,
			@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) {
		if (session.getAttribute("role") != null) {
			DashboardFilterPayload filterPayload = new DashboardFilterPayload();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			List<Dealer> dealers = new ArrayList<>();
			List<Outlet> outlets = new ArrayList<>();
			List<Region> regions = new ArrayList<>();
			List<State> states = new ArrayList<State>();
			List<City> cities = new ArrayList<City>();
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

			// Use Search Criteria
			// Get Outlet First By Search
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
			//Get All Analytics
			List<AnalyticsAll> analyticsAlls = allService.getDataByAnyTypeLoginByDealerIds(outletCode,dealershipCode,regionCode,stateCode,cityCode, approved, dealerIdList,dateFrom,dateTo);
			//Only Aggregate Data
			List<ModelDashboardAggregate> aggregates = getAggregateData(analyticsAlls);

			// Save Same type accesskeyList
			Set<String> overviewSet = new LinkedHashSet<String>();
			overviewSet.addAll(overview.getRegistered());
			overviewSet.addAll(overview.getAssessments());
			overviewSet.addAll(overview.getPass());
			overviewSet.addAll(overview.getOffer());
			overviewSet.addAll(overview.getRecruited());
			Set<String> actionSet = new LinkedHashSet<String>();
			actionSet.addAll(action.getAssessmentStatus());
			actionSet.addAll(action.getInterviewStatus());
			actionSet.addAll(action.getDocumentUploadStatus());
			actionSet.addAll(action.getPrarambhStatus());
			actionSet.addAll(action.getFsdmApproval());
			Set<String> sourceSet = new LinkedHashSet<String>();
			sourceSet.addAll(source.getAdvertisement());
			sourceSet.addAll(source.getDirectWalkIn());
			sourceSet.addAll(source.getReferrals());
			sourceSet.addAll(source.getJobConsultant());
			sourceSet.addAll(source.getOthers());
			sourceSet.addAll(source.getSocialMedia());
			Set<String> expAutoSet = new LinkedHashSet<String>();
			expAutoSet.addAll(exp.getLessThan3Months());
			expAutoSet.addAll(exp.getMonths3To6());
			expAutoSet.addAll(exp.getMonths6To1Year());
			expAutoSet.addAll(exp.getYear1To2Year());
			expAutoSet.addAll(exp.getYear2To5Year());
			expAutoSet.addAll(exp.getYear5To10Year());
			Set<String> expNonAutoSet = new LinkedHashSet<String>();
			expNonAutoSet.addAll(exp.getLessThan3Months());
			expNonAutoSet.addAll(exp.getMonths3To6());
			expNonAutoSet.addAll(exp.getMonths6To1Year());
			expNonAutoSet.addAll(exp.getYear1To2Year());
			expNonAutoSet.addAll(exp.getYear2To5Year());
			expNonAutoSet.addAll(exp.getYear5To10Year());
			Set<String> assessmentSet = new LinkedHashSet<String>();
			assessmentSet.addAll(assessment.getLessThan40());
			assessmentSet.addAll(assessment.getBetween40To60());
			assessmentSet.addAll(assessment.getBetween60To80());
			assessmentSet.addAll(assessment.getMoreThan80());
			Set<String> desgSet = new LinkedHashSet<String>();
			desgSet.addAll(designation.getSales());
			desgSet.addAll(designation.getNonSales());
			Set<String> ageSet = new LinkedHashSet<String>();
			ageSet.addAll(age.getLessThan20());
			ageSet.addAll(age.getBetween20To25());
			ageSet.addAll(age.getBetween25To30());
			ageSet.addAll(age.getBetween30To35());
			ageSet.addAll(age.getBetween35To40());
			ageSet.addAll(age.getMoreThan40());
			Set<String> genderSet = new LinkedHashSet<String>();
			genderSet.addAll(gender.getMale());
			genderSet.addAll(gender.getFemale());

			// Setting Dashboard Related Data In Model
			model.addAttribute("overview", overview);
			model.addAttribute("action", action);
			model.addAttribute("source", source);
			model.addAttribute("experience", exp);
			model.addAttribute("expNonAuto", nonAuto);
			model.addAttribute("assessment", assessment);
			model.addAttribute("designation", designation);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);

			// Aggregate CSV Data
			model.addAttribute("overviewSet", overviewSet);
			model.addAttribute("actionSet", actionSet);
			model.addAttribute("sourceSet", sourceSet);
			model.addAttribute("expAutoSet", expAutoSet);
			model.addAttribute("expNonAutoSet", expNonAutoSet);
			model.addAttribute("assessmentSet", assessmentSet);
			model.addAttribute("desgSet", desgSet);
			model.addAttribute("ageSet", ageSet);
			model.addAttribute("genderSet", genderSet);

			// Aggregate All Data of Candidate
			model.addAttribute("overviewAll", getParticipantAllDataByAccesskeyList(overviewSet, "Overview"));
			model.addAttribute("actionAll", getParticipantAllDataByAccesskeyList(actionSet, "Action"));
			model.addAttribute("sourceAll", getParticipantAllDataByAccesskeyList(sourceSet, "Source"));
			model.addAttribute("expAutoAll", getParticipantAllDataByAccesskeyList(expAutoSet, "Auto"));
			model.addAttribute("expNonAutoAll", getParticipantAllDataByAccesskeyList(expNonAutoSet, "NonAuto"));
			model.addAttribute("assessmentAll", getParticipantAllDataByAccesskeyList(assessmentSet, "Assessment"));
			model.addAttribute("desgAll", getParticipantAllDataByAccesskeyList(desgSet, "Designation"));
			model.addAttribute("ageAll", getParticipantAllDataByAccesskeyList(ageSet, "Age"));
			model.addAttribute("genderAll", getParticipantAllDataByAccesskeyList(genderSet, "Gender"));
			//Aggregates
			model.addAttribute("aggregates", aggregates);

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

		} else {
			return "redirect:login";
		}
		return "reports";
	}
	
	//Get Aggregate Data 
	private List<ModelDashboardAggregate> getAggregateData(List<AnalyticsAll> analyticsAlls) {
		List<ModelDashboardAggregate> aggregates= new ArrayList<ModelDashboardAggregate>();
		Collections.sort(analyticsAlls,(a1,a2)->a1.getModifiedDate().compareTo(a2.getModifiedDate()));
		for(AnalyticsAll analytics : analyticsAlls) {
			ModelDashboardAggregate aggregate = new ModelDashboardAggregate();
			String name = "";
			name = analytics.getFirstName();
			if (analytics.getMiddleName() != null) {
				name += " " + analytics.getMiddleName();
			}
			name += " " + analytics.getLastName();
			aggregate.setName(name);
			if(analytics.getDesignationType()!=null) {
				aggregate.setProfile(analytics.getDesignationType());
			}else {
				aggregate.setProfile("");
			}
			aggregate.setMobile(analytics.getMobile());
			if (analytics.getRegistrationDate() != null) {
				aggregate.setRegistrationDate(DataProccessor.dateToString(analytics.getRegistrationDate()));
			}
			if (analytics.getAssessmentDate() != null) {
				aggregate.setAssessmentDate(DataProccessor.dateToString(analytics.getAssessmentDate()));
			}
			aggregate.setTestScore(analytics.getMarksObtained());
			if (analytics.getPassFailStatus() != null && analytics.getPassFailStatus() != "") {
				aggregate.setPassFailStatus(Integer.valueOf(analytics.getPassFailStatus()));
			}
			aggregate.setFinalDesignationCode(analytics.getFinalDesignation());
			aggregate.setMspin(analytics.getMspin());
			aggregate.setAccesskey(analytics.getAccesskey());
			aggregate.setOverview(getStatus(analytics, "Overview"));
			aggregate.setActionPoints(getStatus(analytics, "Action"));
			aggregate.setRecruitmentSource(getStatus(analytics, "Source"));
			aggregate.setSalesNonSales(getStatus(analytics, "Designation"));
			aggregate.setGender(getStatus(analytics, "Gender"));
			aggregate.setExpAuto(getStatus(analytics, "Auto"));
			aggregate.setExpNonAuto(getStatus(analytics, "NonAuto"));
			aggregate.setAgeWise(getStatus(analytics, "Age"));
			aggregate.setAssessmentReport(getStatus(analytics, "Assessment"));
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	// Get Analytics All Data By Accesskey List
	public List<ModelParticpantView> getParticipantAllDataByAccesskeyList(List<String> accesskeyList, String status) {
		List<ModelParticpantView> mpvList = new ArrayList<ModelParticpantView>();
		List<AnalyticsAll> list = allService.getAllAnalyticsByAccesskeyList(accesskeyList);
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
			mpv.setMspin(a.getMspin());
			mpv.setDesignation(a.getDesignationType());
			mpv.setMobile(a.getMobile());
			mpv.setAccesskey(a.getAccesskey());
			mpv.setFinalDesignationCode(a.getFinalDesignation());
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
			mpv.setStatus(getStatus(a, status));
			mpvList.add(mpv);

		}
		return mpvList;
	}

	public List<ModelParticpantView> getParticipantAllDataByAccesskeyList(Set<String> accesskeyList, String status) {
		List<ModelParticpantView> mpvList = new ArrayList<ModelParticpantView>();
		List<String> acckeyList2 = new ArrayList<String>();
		acckeyList2.addAll(accesskeyList);
		List<AnalyticsAll> list = new ArrayList<AnalyticsAll>();
		if (acckeyList2 != null) {
			list = allService.getAllAnalyticsByAccesskeyList(acckeyList2);
		}
		Collections.sort(list, (o1, o2) -> o2.getModifiedDate().compareTo(o1.getModifiedDate()));
		for (AnalyticsAll a : list) {
			ModelParticpantView mpv = new ModelParticpantView();
			String name = "";
			name = a.getFirstName();
			if (a.getMiddleName() != null) {
				name += " " + a.getMiddleName();
			}
			name += " " + a.getLastName();
			mpv.setParticipantName(name);
			mpv.setMspin(a.getMspin());
			mpv.setDesignation(a.getDesignationType());
			mpv.setMobile(a.getMobile());
			mpv.setAccesskey(a.getAccesskey());
			mpv.setFinalDesignationCode(a.getFinalDesignation());
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
			mpv.setStatus(getStatus(a, status));

			mpvList.add(mpv);

		}
		return mpvList;
	}

	// Get Status
	// Based on Status adding Status
	public String getStatus(AnalyticsAll a, String status) {
		String newStatus = "";
		if (status.equalsIgnoreCase("Overview")) {
			if (a.getRegistered() != null && Integer.valueOf(a.getRegistered()) >= 1) {
				newStatus = "Registered";
			}
			if (a.getAssessments() != null && a.getAssessments().equals("3")) {
				newStatus = "Assessment";
			}
			if (a.getPassFailStatus() != null && a.getPassFailStatus().equals("1")) {
				newStatus = "Passed";
			}
			if (a.getOfferStatus() != null && a.getOfferStatus().equalsIgnoreCase("Yes")) {
				newStatus = "Offered";
			}
			if (a.getRecruitedStatus() != null && a.getRecruitedStatus().equalsIgnoreCase("Yes")) {
				newStatus = "Recruited";
			}
		} else if (status.equalsIgnoreCase("Action")) {
			if ((a.getAssessmentStatus() == null || a.getAssessmentStatus() == ""
					|| !(a.getAssessmentStatus() != null && a.getAssessmentStatus().equals("3")))
					&& (a.getRegistered() != null && Integer.valueOf(a.getRegistered()) >= 1)) {
				newStatus = "Assessment";
			}
			if (a.getInterviewStatus().equals("Pending")) {
				newStatus = "Interview";
			}
			if (a.getDocumentUploadStatus() != null && a.getDocumentUploadStatus().equals("2")) {
				newStatus = "Document";
			}
			if (a.getMspin() != null && a.getFinalDesignation() != null
					&& a.getFinalDesignation().equalsIgnoreCase("STR") && a.getPrarambhStatus() == null) {
				newStatus = "Praarambh";
			}
			if (a.getFsdmApproval() != null && a.getFsdmApproval().equals("3")) {
				newStatus = "FSDM Approval";
			}
		} else if (status.equalsIgnoreCase("Source")) {
			if (a.getRecSource() != null) {
				if (a.getRecSource().equalsIgnoreCase("Referrals"))
					newStatus = "Referrals";
				if (a.getRecSource().equalsIgnoreCase("Direct Walk-in"))
					newStatus = "Direct Walk-in";
				if (a.getRecSource().equalsIgnoreCase("Advertisement"))
					newStatus = "Advertisement";
				if (a.getRecSource().equalsIgnoreCase("Job Consultant"))
					newStatus = "Job Consultant";
				if (a.getRecSource().equalsIgnoreCase("Social Media"))
					newStatus = "Social Media";
				if (a.getRecSource().equalsIgnoreCase("Others"))
					newStatus = "Others";
			}
		} else if (status.equalsIgnoreCase("Auto")) {
			if (a.getCandidateExperience() != null && a.getCandidateExperience().length() > 0) {
				if (Integer.valueOf(a.getCandidateExperience()) < 3)
					newStatus = "Between 0-3 Months";
				if (Integer.valueOf(a.getCandidateExperience()) >= 3 && Integer.valueOf(a.getCandidateExperience()) < 6)
					newStatus = "Between 3-6 Months";
				if (Integer.valueOf(a.getCandidateExperience()) >= 6
						&& Integer.valueOf(a.getCandidateExperience()) < 12)
					newStatus = "Between 6-12 Months";
				if (Integer.valueOf(a.getCandidateExperience()) >= 12
						&& Integer.valueOf(a.getCandidateExperience()) < 24)
					newStatus = "Between 1-2 Years";
				if (Integer.valueOf(a.getCandidateExperience()) >= 24
						&& Integer.valueOf(a.getCandidateExperience()) < 60)
					newStatus = "Between 2-5 Years";
				if (Integer.valueOf(a.getCandidateExperience()) >= 60
						&& Integer.valueOf(a.getCandidateExperience()) < 120)
					newStatus = "Between 5-10 Years";
				if (Integer.valueOf(a.getCandidateExperience()) >= 120)
					newStatus = "More Than 10 Years";
			}
		} else if (status.equalsIgnoreCase("NonAuto")) {
			if (a.getCandidateExperienceNonAuto() != null && a.getCandidateExperienceNonAuto().length() > 0) {
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) < 3)
					newStatus = "Between 0-3 Months";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 3
						&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 6)
					newStatus = "Between 3-6 Months";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 6
						&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 12)
					newStatus = "Between 6-12 Months";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 12
						&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 24)
					newStatus = "Between 1-2 Years";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 24
						&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 60)
					newStatus = "Between 2-5 Years";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 60
						&& Integer.valueOf(a.getCandidateExperienceNonAuto()) < 120)
					newStatus = "Between 5-10 Years";
				if (Integer.valueOf(a.getCandidateExperienceNonAuto()) >= 120)
					newStatus = "More Than 10 Years";
			}
		} else if (status.equalsIgnoreCase("Assessment")) {
			if (a.getAssessmentReport() != null && a.getAssessmentReport().length() > 0) {
				if (Integer.valueOf(a.getAssessmentReport()) < 40)
					newStatus = "Less than 40";
				if (Integer.valueOf(a.getAssessmentReport()) >= 40 && Integer.valueOf(a.getAssessmentReport()) < 60)
					newStatus = "Between 40-60";
				if (Integer.valueOf(a.getAssessmentReport()) >= 60 && Integer.valueOf(a.getAssessmentReport()) < 80)
					newStatus = "Between 60-80";
				if (Integer.valueOf(a.getAssessmentReport()) > 80)
					newStatus = "More than 80";
			}
		} else if (status.equalsIgnoreCase("Designation")) {
			if (a.getDesignationType() != null && a.getDesignationType().length() > 0) {
				if (a.getDesignationType().equalsIgnoreCase("Sales"))
					newStatus = "Sales";
				if (a.getDesignationType().equalsIgnoreCase("Sales Support"))
					newStatus = "Sales Support";
			}
		} else if (status.equalsIgnoreCase("Gender")) {
			if (a.getGender() != null && a.getGender().length() > 0) {
				if (a.getGender().equalsIgnoreCase("Male") || a.getGender().equalsIgnoreCase("M"))
					newStatus = "Male";
				if (a.getGender().equalsIgnoreCase("Female") || a.getGender().equalsIgnoreCase("F"))
					newStatus = "Female";
				if (a.getGender().equalsIgnoreCase("Other") || a.getGender().equalsIgnoreCase("Transgender")
						|| a.getGender().equalsIgnoreCase("U"))
					newStatus = "Others";
			}
		} else if (status.equalsIgnoreCase("Age")) {
			if (a.getAge() != null && a.getAge().length() > 0) {
				if (Integer.valueOf(a.getAge()) < 20)
					newStatus = "Between 18-20";
				if (Integer.valueOf(a.getAge()) >= 20 && Integer.valueOf(a.getAge()) < 25)
					newStatus = "Between 20-25";
				if (Integer.valueOf(a.getAge()) >= 25 && Integer.valueOf(a.getAge()) < 30)
					newStatus = "Between 25-30";
				if (Integer.valueOf(a.getAge()) >= 30 && Integer.valueOf(a.getAge()) < 35)
					newStatus = "Between 30-35";
				if (Integer.valueOf(a.getAge()) >= 35 && Integer.valueOf(a.getAge()) < 40)
					newStatus = "Between 35-40";
				if (Integer.valueOf(a.getAge()) >= 40)
					newStatus = "More than 40";
			}
		}
		return newStatus;
	}

	// Dashboard CSV Report
	@PostMapping("/dashboardCSVReport")
	public ResponseEntity<?> generateDashboardReport(@RequestParam("outletCode") String outletCode,
			@RequestParam("dealershipCode") String dealershipCode, @RequestParam("regionCode") String regionCode,
			@RequestParam("stateCode") String stateCode, @RequestParam("cityCode") String cityCode, @RequestParam("approved") String approved,
			@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session)
			throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
			List<Long> dealerIdList = new ArrayList<>();
			Set<Region> regions = new LinkedHashSet<Region>();
			String role = session.getAttribute("role").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			// get dealer id list
			Set<Outlet> outlets = new LinkedHashSet<Outlet>();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				List<Outlet> out = outletService.getOutletByDealerId(dealerId);
				outlets.addAll(out);
				out.stream().map(Outlet::getRegion).forEachOrdered(regions::add);
				dealerIdList.add(dealerId);
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				Optional<FSDM> f = fsdmService.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					regions.add(r);
					List<Outlet> outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet : outletList) {
						outlets.add(outlet);
						dealerIdList.add(outlet.getDealer().getId());
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				List<Dealer> dealers = dealerService.getAllDeealer();
				regions.addAll(regionService.getAllRegion());
				dealers.stream().map(Dealer::getId).forEachOrdered(dealerIdList::add);
			}
			List<Region> regionList = new ArrayList<Region>();
			// Check region Code
			if (regionCode != null && regionCode != "") {
				Optional<Region> region = regionService.getReagion(regionCode);
				if (region.isPresent()) {
					regionList.add(region.get());
				}
			} else {
				regionList.addAll(regions);
			}
			

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("DashboardReport");
			XSSFCellStyle style = workbook.createCellStyle();
			// style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			// style.setFillPattern(FillPatternType.BIG_SPOTS);
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("Bird View");
			cell = row.createCell(1);
			cell.setCellValue("Overview");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue("Overview");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 5));
			cell = row.createCell(3);
			cell.setCellValue("Overview");
			cell = row.createCell(4);
			cell.setCellValue("Overview");
			cell = row.createCell(5);
			cell.setCellValue("Overview");
			cell = row.createCell(6);
			cell.setCellValue("Action Points");
			cell = row.createCell(7);
			cell.setCellValue("Action Points");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 10));
			cell = row.createCell(8);
			cell.setCellValue("Action Points");
			cell = row.createCell(9);
			cell.setCellValue("Action Points");
			cell = row.createCell(10);
			cell.setCellValue("Action Points");
			cell = row.createCell(11);
			cell.setCellValue("Recruitment Source");
			cell = row.createCell(12);
			cell.setCellValue("Recruitment Source");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 16));
			cell = row.createCell(13);
			cell.setCellValue("Recruitment Source");
			cell = row.createCell(14);
			cell.setCellValue("Recruitment Source");
			cell = row.createCell(15);
			cell.setCellValue("Recruitment Source");
			cell = row.createCell(16);
			cell.setCellValue("Recruitment Source");
			cell = row.createCell(17);
			cell.setCellValue("Profile");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 18));
			cell = row.createCell(18);
			cell.setCellValue("Profile");
			cell = row.createCell(19);
			cell.setCellValue("Gender Diversity");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 20));
			cell = row.createCell(20);
			cell.setCellValue("Gender Diversity");
			cell = row.createCell(21);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(22);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(23);
			cell.setCellValue("Auto Industry Experience");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 21, 27));
			cell = row.createCell(24);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(25);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(26);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(27);
			cell.setCellValue("Auto Industry Experience");
			cell = row.createCell(28);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(29);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(30);
			cell.setCellValue("Non-Auto Industry Experience");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 28, 34));
			cell = row.createCell(31);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(32);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(33);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(34);
			cell.setCellValue("Non-Auto Industry Experience");
			cell = row.createCell(35);
			cell.setCellValue("Age Wise");
			cell = row.createCell(36);
			cell.setCellValue("Age Wise");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 35, 40));
			cell = row.createCell(37);
			cell.setCellValue("Age Wise");
			cell = row.createCell(38);
			cell.setCellValue("Age Wise");
			cell = row.createCell(39);
			cell.setCellValue("Age Wise");
			cell = row.createCell(40);
			cell.setCellValue("Age Wise");
			cell = row.createCell(41);
			cell.setCellValue("Assessment Report");
			cell = row.createCell(42);
			cell.setCellValue("Assessment Report");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 41, 44));
			cell = row.createCell(43);
			cell.setCellValue("Assessment Report");
			cell = row.createCell(44);
			cell.setCellValue("Assessment Report");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}
			// 2nd Headers

			row = sheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("Regions");
			cell = row.createCell(1);
			cell.setCellValue("Registered");
			cell = row.createCell(2);
			cell.setCellValue("Assessment");
			cell = row.createCell(3);
			cell.setCellValue("Passed");
			cell = row.createCell(4);
			cell.setCellValue("Offered");
			cell = row.createCell(5);
			cell.setCellValue("Recruited");
			cell = row.createCell(6);
			cell.setCellValue("Assessment");
			cell = row.createCell(7);
			cell.setCellValue("Interview");
			cell = row.createCell(8);
			cell.setCellValue("Documents");
			cell = row.createCell(9);
			cell.setCellValue("Praarambh");
			cell = row.createCell(10);
			cell.setCellValue("FSDM Approval");
			cell = row.createCell(11);
			cell.setCellValue("Referrals");
			cell = row.createCell(12);
			cell.setCellValue("Direct Walk In");
			cell = row.createCell(13);
			cell.setCellValue("Advertisement");
			cell = row.createCell(14);
			cell.setCellValue("Job Consultant");
			cell = row.createCell(15);
			cell.setCellValue("Social Media");
			cell = row.createCell(16);
			cell.setCellValue("Other");
			cell = row.createCell(17);
			cell.setCellValue("Sales");
			cell = row.createCell(18);
			cell.setCellValue("Non Sales");
			cell = row.createCell(19);
			cell.setCellValue("Male");
			cell = row.createCell(20);
			cell.setCellValue("Female");
			cell = row.createCell(21);
			cell.setCellValue("Less Than 3 Months");
			cell = row.createCell(22);
			cell.setCellValue("Between 3-6 Months");
			cell = row.createCell(23);
			cell.setCellValue("Between 6-12 Months");
			cell = row.createCell(24);
			cell.setCellValue("Between 1-2 Years");
			cell = row.createCell(25);
			cell.setCellValue("Between 2-5 Years");
			cell = row.createCell(26);
			cell.setCellValue("Between 5-10 Years");
			cell = row.createCell(27);
			cell.setCellValue("More Than 10 Years");
			cell = row.createCell(28);
			cell.setCellValue("Less Than 3 Months");
			cell = row.createCell(29);
			cell.setCellValue("Between 3-6 Months");
			cell = row.createCell(30);
			cell.setCellValue("Between 6-12 Months");
			cell = row.createCell(31);
			cell.setCellValue("Between 1-2 Years");
			cell = row.createCell(32);
			cell.setCellValue("Between 2-5 Years");
			cell = row.createCell(33);
			cell.setCellValue("Between 5-10 Years");
			cell = row.createCell(34);
			cell.setCellValue("More Than 10 Years");
			cell = row.createCell(35);
			cell.setCellValue("Between 18-20 Years ");
			cell = row.createCell(36);
			cell.setCellValue("Between 20-25 Years");
			cell = row.createCell(37);
			cell.setCellValue("Between 25-30 Years");
			cell = row.createCell(38);
			cell.setCellValue("Between 30-35 Years");
			cell = row.createCell(39);
			cell.setCellValue("Between 35-40 Years");
			cell = row.createCell(40);
			cell.setCellValue("More Than 40 Years");
			cell = row.createCell(41);
			cell.setCellValue("Less Than 40%");
			cell = row.createCell(42);
			cell.setCellValue("Between 40-60%");
			cell = row.createCell(43);
			cell.setCellValue("Between 60-80%");
			cell = row.createCell(44);
			cell.setCellValue("More Than 80%");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}

			// Data based on region
			int count = 2;
			List<Region> list=regionList.stream().sorted((r1,r2) -> r1.getRegionCode().compareToIgnoreCase(r2.getRegionCode())).collect(Collectors.toList());
			for (Region r : list) {
				String regionCode2 = r.getRegionCode();
				// Get Data by by region Code
				OverviewPayload overview = allService.getOverviewAnalytics(outletCode, dealershipCode, regionCode2,
						stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				ActionPointsPayload action = allService.getActionPending(outletCode, dealershipCode, regionCode2,
						stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				RecruitmentSourcePayload source = allService.getRecruitmentSourceAnalytics(outletCode, dealershipCode,
						regionCode2, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				CandidateExperiencePayload exp = allService.getCandidateExpAnalytics(outletCode, dealershipCode,
						regionCode2, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				CandidateNonAutoExperiencePayload nonAuto = allService.getCandidateNonAutoExperience(outletCode,
						dealershipCode, regionCode2, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				AssessmentReportPayload assessment = allService.getAssessmentReportAnalytics(outletCode, dealershipCode,
						regionCode2, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				DesignationTypePayload designation = allService.getDesignationTypeAnalytics(outletCode, dealershipCode,
						regionCode2, stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);
				AgeWisePayload age = allService.getAgeWiseAnalytics(outletCode, dealershipCode, regionCode2, stateCode,
						cityCode, approved, dateFrom, dateTo, dealerIdList);
				GenderDiversityPayload gender = allService.getGenderAnalytics(outletCode, dealershipCode, regionCode2,
						stateCode, cityCode, approved, dateFrom, dateTo, dealerIdList);

				row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(regionCode2);
				cell = row.createCell(1);
				if(!overview.getRegistered().isEmpty()) {
				cell.setCellValue(overview.getRegistered().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(2);
				if(!overview.getAssessments().isEmpty()) {
				cell.setCellValue(overview.getAssessments().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(3);
				if(!overview.getPass().isEmpty()) {
				cell.setCellValue(overview.getPass().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(4);
				if(!overview.getOffer().isEmpty()) {
				cell.setCellValue(overview.getOffer().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(5);
				if(!overview.getRecruited().isEmpty()) {
				cell.setCellValue(overview.getRecruited().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				if(!action.getAssessmentStatus().isEmpty()) {
				cell.setCellValue(action.getAssessmentStatus().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(7);
				if(!action.getInterviewStatus().isEmpty()) {
				cell.setCellValue(action.getInterviewStatus().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(8);
				if(!action.getDocumentUploadStatus().isEmpty()) {
				cell.setCellValue(action.getDocumentUploadStatus().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(9);
				if(!action.getPrarambhStatus().isEmpty()) {
				cell.setCellValue(action.getPrarambhStatus().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(10);
				if(!action.getFsdmApproval().isEmpty()) {
				cell.setCellValue(action.getFsdmApproval().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(11);
				if(!source.getReferrals().isEmpty()) {
				cell.setCellValue(source.getReferrals().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				if(!source.getDirectWalkIn().isEmpty()) {
				cell.setCellValue(source.getDirectWalkIn().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(13);
				if(!source.getAdvertisement().isEmpty()) {
				cell.setCellValue(source.getAdvertisement().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(14);
				if(!source.getJobConsultant().isEmpty()) {
				cell.setCellValue(source.getJobConsultant().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(15);
				if(!source.getSocialMedia().isEmpty()) {
				cell.setCellValue(source.getSocialMedia().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(16);
				if(!source.getOthers().isEmpty()) {
				cell.setCellValue(source.getOthers().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(17);
				if(!designation.getSales().isEmpty()) {
				cell.setCellValue(designation.getSales().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(18);
				if(!designation.getNonSales().isEmpty()) {
				cell.setCellValue(designation.getNonSales().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(19);
				if(!gender.getMale().isEmpty()) {
				cell.setCellValue(gender.getMale().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(20);
				if(!gender.getFemale().isEmpty()) {
				cell.setCellValue(gender.getFemale().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(21);
				if(!exp.getLessThan3Months().isEmpty()) {
				cell.setCellValue(exp.getLessThan3Months().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(22);
				if(!exp.getMonths3To6().isEmpty()) {
				cell.setCellValue(exp.getMonths3To6().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(23);
				if(!exp.getMonths6To1Year().isEmpty()) {
				cell.setCellValue(exp.getMonths6To1Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(24);
				if(!exp.getYear1To2Year().isEmpty()) {
				cell.setCellValue(exp.getYear1To2Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(25);
				if(!exp.getYear2To5Year().isEmpty()) {
				cell.setCellValue(exp.getYear2To5Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(26);
				if(!exp.getYear5To10Year().isEmpty()) {
				cell.setCellValue(exp.getYear5To10Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(27);
				if(!exp.getMoreThan10Year().isEmpty()) {
				cell.setCellValue(exp.getMoreThan10Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(28);
				if(!nonAuto.getLessThan3Months().isEmpty()) {
				cell.setCellValue(nonAuto.getLessThan3Months().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(29);
				if(!nonAuto.getMonths3To6().isEmpty()) {
				cell.setCellValue(nonAuto.getMonths3To6().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(30);
				if(!nonAuto.getMonths6To1Year().isEmpty()) {
				cell.setCellValue(nonAuto.getMonths6To1Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(31);
				if(!nonAuto.getYear1To2Year().isEmpty()) {
				cell.setCellValue(nonAuto.getYear1To2Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(32);
				if(!nonAuto.getYear2To5Year().isEmpty()) {
				cell.setCellValue(nonAuto.getYear2To5Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(33);
				if(!nonAuto.getYear5To10Year().isEmpty()) {
				cell.setCellValue(nonAuto.getYear5To10Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(34);
				if(!nonAuto.getMoreThan10Year().isEmpty()) {
				cell.setCellValue(nonAuto.getMoreThan10Year().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(35);
				if(!age.getLessThan20().isEmpty()) {
				cell.setCellValue(age.getLessThan20().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(36);
				if(!age.getBetween20To25().isEmpty()) {
				cell.setCellValue(age.getBetween20To25().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(37);
				if(!age.getBetween25To30().isEmpty()) {
				cell.setCellValue(age.getBetween25To30().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(38);
				if(!age.getBetween30To35().isEmpty()) {
				cell.setCellValue(age.getBetween30To35().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(39);
				if(!age.getBetween35To40().isEmpty()) {
				cell.setCellValue(age.getBetween35To40().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(40);
				if(!age.getMoreThan40().isEmpty()) {
				cell.setCellValue(age.getMoreThan40().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(41);
				if(!assessment.getLessThan40().isEmpty()) {
				cell.setCellValue(assessment.getLessThan40().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(42);
				if(!assessment.getBetween40To60().isEmpty()) {
				cell.setCellValue(assessment.getBetween40To60().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(43);
				if(!assessment.getBetween60To80().isEmpty()) {
				cell.setCellValue(assessment.getBetween60To80().size());
				}else {
					cell.setCellValue("");
				}
				cell = row.createCell(44);
				if(!assessment.getMoreThan80().isEmpty()) {
				cell.setCellValue(assessment.getMoreThan80().size());
				}else {
					cell.setCellValue("");
				}
				count++;
			}
			String responseExcelUrl = "DashboardReport.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("DashboardReport.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= DashboardReport.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

		} else {
			return null;
		}

	}

}
