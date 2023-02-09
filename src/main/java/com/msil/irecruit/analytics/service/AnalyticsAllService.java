package com.msil.irecruit.analytics.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

public interface AnalyticsAllService {
	
	void saveAllAnalytics(AnalyticsAll analyticsAll);
	void saveAll(List<AnalyticsAll> analyticsAlls);
	List<AnalyticsAll> getAllAnalytics();
	OverviewPayload getOverviewAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	RecruitmentSourcePayload getRecruitmentSourceAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	CandidateExperiencePayload getCandidateExpAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	CandidateNonAutoExperiencePayload getCandidateNonAutoExperience(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	AssessmentReportPayload getAssessmentReportAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	DesignationTypePayload getDesignationTypeAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	AgeWisePayload getAgeWiseAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	GenderDiversityPayload getGenderAnalytics(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	ActionPointsPayload getActionPending(List<Long> dealerIdList, Date dateFrom, Date dateTo);
	List<String> getAccessKeyList(List<Long> dealerIdList);
	List<AnalyticsAll> getDataByAnyTypeLoginByDealerIds(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, List<Long> dealerIdList, Date dateFrom, Date dateTo);
	
	// Filter Methods
	OverviewPayload getOverviewAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);
	ActionPointsPayload getActionPending(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);
	RecruitmentSourcePayload getRecruitmentSourceAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode,
			String approved, Date dateFrom, Date dateTo, List<Long> dealerIdList);
	CandidateExperiencePayload getCandidateExpAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode,
			String approved, Date dateFrom, Date dateTo, List<Long> dealerIdList);
	CandidateNonAutoExperiencePayload getCandidateNonAutoExperience(String outletCode, String dealershipCode,
			String regionCode,  String stateCode, String cityCode, String approved, Date dateFrom, Date dateTo, List<Long> dealerIdList);
	AssessmentReportPayload getAssessmentReportAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode,
			String approved, Date dateFrom, Date dateTo, List<Long> dealerIdList);
	DesignationTypePayload getDesignationTypeAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode,
			String approved, Date dateFrom, Date dateTo, List<Long> dealerIdList);
	AgeWisePayload getAgeWiseAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);
	GenderDiversityPayload getGenderAnalytics(String outletCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);
	Optional<AnalyticsAll> getAnalyticsByAccesskey(String accessKey, Long dealerId, Long pId);
	List<AnalyticsAll> getAllAnalyticsByAccesskeyList(List<String> accesskeyList);
	void deleteById(Long id);
	/*
	 * List<AnalyticsAll> getDataByAnyTypeLoginByDealerIds(String outletCode, String
	 * dealershipCode, String regionCode, String stateCode, String cityCode,
	 * List<Long> dealerIdList, Date dateFrom, Date dateTo);
	 */
	List<AnalyticsAll> getDataByAnyTypeLoginByDealerIds(List<Long> dealerIdList, Date dateFrom, Date dateTo);
}
