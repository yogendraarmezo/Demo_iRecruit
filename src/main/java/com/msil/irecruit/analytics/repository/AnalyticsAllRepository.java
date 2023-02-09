package com.msil.irecruit.analytics.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.analytics.entity.AnalyticsAll;

public interface AnalyticsAllRepository extends JpaRepository<AnalyticsAll, Long> {
	
	
	@Query("SELECT a FROM AnalyticsAll a WHERE a.fsdmApproval=:approval")
	List<AnalyticsAll> findAllAnalyticsWhereFSDMApproved(String approval);

	@Query("SELECT a.accesskey FROM AnalyticsAll a WHERE (a.dealerId IN :dealerIds OR (:dealerIds) IS NULL)")
	List<String> getAccessKeyList(List<Long> dealerIds);

	@Query("SELECT a FROM AnalyticsAll a WHERE (a.dealerId IN :dealerId OR (:dealerId) IS NULL) AND (a.recruitDate >= :dateFrom AND a.recruitDate <= :dateTo)")
	List<AnalyticsAll> getDataByAnyTypeLoginByDealerIds(List<Long> dealerId, Date dateFrom, Date dateTo);

	@Query("SELECT a FROM AnalyticsAll a WHERE (:dealerCode IS NULL OR a.dealerCode = :dealerCode OR :dealerCode='') AND (:dealershipCode IS NULL OR a.dealerCode = :dealershipCode OR :dealershipCode='') AND "
			+ "(:regionCode IS NULL OR a.region = :regionCode OR :regionCode = '') AND  (a.state = :stateCode OR :stateCode IS NULL OR :stateCode = '') AND (a.city = :cityCode OR :cityCode IS NULL OR :cityCode = '') AND "
			+ "(:approved IS NULL OR a.fsdmApproval = :approved OR :approved ='') AND (a.recruitDate >= :dateFrom AND a.recruitDate <= :dateTo) AND (a.dealerId IN :dealerIdList OR :dealerIdList IS NULL)")
	List<AnalyticsAll> getAllAnalyticsBySearchQuery(String dealerCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);

	@Query("SELECT a FROM AnalyticsAll a WHERE (:dealerCode IS NULL OR a.dealerCode = :dealerCode OR :dealerCode='') AND (:dealershipCode IS NULL OR a.dealerCode = :dealershipCode OR :dealershipCode='') AND "
			+  "(:regionCode IS NULL OR a.region = :regionCode OR :regionCode = '')  AND  (a.state = :stateCode OR :stateCode IS NULL OR :stateCode = '') AND (a.city = :cityCode OR :cityCode IS NULL OR :cityCode = '') AND "
			+ "(:approved IS NULL OR a.fsdmApproval = :approved OR :approved ='') AND (a.dealerId IN :dealerIdList OR :dealerIdList IS NULL)")
	List<AnalyticsAll> getAnalyticsBySearchData(String dealerCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, List<Long> dealerIdList);
	
	@Query("SELECT a FROM AnalyticsAll a WHERE (:dealerCode IS NULL OR a.dealerCode = :dealerCode OR :dealerCode='') AND (:dealershipCode IS NULL OR a.dealerCode = :dealershipCode OR :dealershipCode='') AND "
			+ "(:regionCode IS NULL OR a.region = :regionCode OR :regionCode = '') AND (a.state = :stateCode OR :stateCode IS NULL OR :stateCode = '') AND (a.city = :cityCode OR :cityCode IS NULL OR :cityCode = '') AND "
			+ "(:approved IS NULL OR a.fsdmApproval = :approved OR :approved ='') AND (a.recruitDate >= :dateFrom AND a.recruitDate <= :dateTo) AND (a.dealerId IN :dealerIdList OR :dealerIdList IS NULL)")
	List<AnalyticsAll> getAllAnalyticsBySearchQueryFSDMApproved(String dealerCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, Date dateFrom,
			Date dateTo, List<Long> dealerIdList);
//	@Query("SELECT a FROM AnalyticsAll a WHERE (:dealerCode IS NULL OR a.dealerCode = :dealerCode OR :dealerCode='') AND (a.dealerId =:dealerId OR :dealerId IS NULL) AND "
//			+ "(:regionCode IS NULL OR a.region = :regionCode OR :regionCode = '') AND (a.recruitDate >= :dateFrom AND a.recruitDate <= :dateTo) AND (a.dealerId IN :dealerIdList OR :dealerIdList IS NULL)")
//	List<AnalyticsAll> getAllAnalyticsBySearchQueryFSDMApproved(String dealerCode, Long dealerId, String regionCode, Date dateFrom,
//			Date dateTo, List<Long> dealerIdList);
	
	@Query("SELECT a FROM AnalyticsAll a WHERE (:dealerCode IS NULL OR a.dealerCode = :dealerCode OR :dealerCode='') AND (:dealershipCode IS NULL OR a.dealerCode = :dealershipCode OR :dealershipCode='') AND "
			+  "(:regionCode IS NULL OR a.region = :regionCode OR :regionCode = '')  AND (a.state = :stateCode OR :stateCode IS NULL OR :stateCode = '') AND (a.city = :cityCode OR :cityCode IS NULL OR :cityCode = '') AND "
			+ "(:approved IS NULL OR a.fsdmApproval = :approved OR :approved ='') AND (a.dealerId IN :dealerIdList OR :dealerIdList IS NULL)")
	List<AnalyticsAll> getAnalyticsBySearchDataFSDMAprroved(String dealerCode, String dealershipCode, String regionCode, String stateCode, String cityCode, String approved, List<Long> dealerIdList);

	@Query("SELECT a FROM AnalyticsAll a WHERE a.dealerId =:dealerId")
	List<AnalyticsAll> findByDealerId(Long dealerId);
	@Query("SELECT a FROM AnalyticsAll a WHERE a.accesskey =:accessKey")
	Optional<AnalyticsAll> findByAccesskey(String accessKey);

	@Query("SELECT a FROM AnalyticsAll a WHERE a.accesskey IN :accesskeyList OR :accesskeyList IS NULL")
	List<AnalyticsAll> findAnalyticsByAccesskeyList(List<String> accesskeyList);

	//Temporary Method
//	@Query("SELECT a FROM AnalyticsAll a WHERE a.accesskey =:accessKey")
//	List<AnalyticsAll> findAllAnalyticsByAccesskeyAndDealerId(String accessKey,Long id);

	@Query("SELECT a FROM AnalyticsAll a WHERE a.accesskey =:accessKey AND a.dealerId=:dealerId AND a.participantId=:pId")
	Optional<AnalyticsAll> findByAccesskeyAndDealerId(String accessKey, Long dealerId, Long pId);
	@Query("SELECT a FROM AnalyticsAll a WHERE a.accesskey =:accessKey AND a.dealerId=:dealerId")
	List<AnalyticsAll> findAllAnaByAccesskeyDealerId(String accessKey, Long dealerId);

	
}