package com.msil.irecruit.Repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.FamilyDetails;
import com.msil.irecruit.Entities.ParticipantRegistration;

@Repository
@Transactional
public interface ParticipantRepository extends JpaRepository<ParticipantRegistration, Long> {


Optional<ParticipantRegistration> findByAccessKey(final String accesskey);
    
    Optional<ParticipantRegistration> findTop1ByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndEmailIgnoreCaseAndDealerId(final String firstName, final String lastName, final String email, final Long dealerId);
    
    Optional<ParticipantRegistration> findTop1ByAccessKey(final String accessKey);
    
    List<FamilyDetails> save(final List<FamilyDetails> familyDetails);
    
    List<ParticipantRegistration> findByDealerIdOrderByModifiedDateDesc(final long dealerId);

	/* fsdm */
    List<ParticipantRegistration> findByDealerIdIn(final List<Long> dealerId);
    @Query("From ParticipantRegistration p where p.dealerId In(:dealerId) and (p.fsdmApprovalStatus IN ('3'))  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> findByDealerIdPendinApproverFSDM(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId In(:dealerId) and (p.fsdmApprovalStatus IN ('1') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> findByDealerIdInprocessFSDM(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId In(:dealerId) and (p.fsdmApprovalStatus='2')  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> findByDealerIdEmployeeFSDM(final List<Long> dealerId);
    
    
    List<ParticipantRegistration> findByDealerIdInAndPrarambhStatusAndTestStatusOrderByModifiedDateDesc(final List<Long> dealerId, final String prarambhStatus, final String testStatus);
    
    @Query("From ParticipantRegistration p where p.dealerId = :dealerId and p.fsdmApprovalStatus = :fsdmApprovalStatus Order By modified_date DESC")
    List<ParticipantRegistration> getEmployee(final long dealerId, final String fsdmApprovalStatus);
    
    @Query("From ParticipantRegistration p where p.dealerId = :dealerId and p.status = :status Order By modified_date DESC")
    List<ParticipantRegistration> getHoldEmployee(final long dealerId, final String status);
    
    @Query("From ParticipantRegistration p where p.dealerId = :dealerId and p.fsdmApprovalStatus = :fsdmApprovalStatus and p.testStatus = :testStatus Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantDeealer(final long dealerId, final String fsdmApprovalStatus, final String testStatus);
    
    @Query("From ParticipantRegistration p where p.dealerId =:dealerId and (p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVInpprocess(final long dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId =:dealerId and p.fsdmApprovalStatus IN ('1','3') and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVPendingApprovel(final long dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId =:dealerId and p.fsdmApprovalStatus ='2' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVImployee(final long dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId =:dealerId and p.status ='H' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVHold(final long dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId =:dealerId and (p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) and (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVInpprocess(@Param("dealerId") final long dealerId, @Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND(p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL) and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) and (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo)  Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantFilterInpprocess(@Param("outletCode") final String outletCode, @Param("name") final String name, @Param("designation") final String designation, @Param("mspin") final String mspin, @Param("passFailStatus") final List<Integer> passFailStatus, @Param("accessKey") final String accessKey, @Param("dealerId") final Long dealerId, @Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo);
    
    //@Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND(p.fsdmApprovalStatus='2') and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) and (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation) AND (:mspin IS NULL OR p.mspin = :mspin) AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey) AND (p.dealerId=:dealerId) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) Order By modified_date DESC")
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.fsdmApprovalStatus =:fsdmApprovalStatus) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantFilterEmployee(@Param("outletCode") final String outletCode, @Param("name") final String name, @Param("designation") final String designation, @Param("mspin") final String mspin, @Param("passFailStatus") final List<Integer> passFailStatus, @Param("accessKey") final String accessKey, @Param("dealerId") final Long dealerId, @Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo, String fsdmApprovalStatus);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and (p.fsdmApprovalStatus IN ('1') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL)")
    List<ParticipantRegistration> getParticipantCSVInpprocess(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and (p.fsdmApprovalStatus IN ('1') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) AND (p.registration_date >= :dateFrom AND p.registration_date <=:dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVInpprocess(final List<Long> dealerId, final Date dateFrom, final Date dateTo);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and p.fsdmApprovalStatus IN ('3') and p.testStatus ='3' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVPendingApprovel(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and p.fsdmApprovalStatus IN ('3') and p.testStatus ='3' AND (p.registration_date >= :dateFrom AND p.registration_date <=:dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVPendingApprovel(final List<Long> dealerId, final Date dateFrom, final Date dateTo);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId  and p.fsdmApprovalStatus ='2' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVImployee(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and p.fsdmApprovalStatus='2' AND (p.registration_date >= :dateFrom AND p.registration_date <=:dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVImployee(final List<Long> dealerId, final Date dateFrom, final Date dateTo);
    
	/* HO */
    @Query("From ParticipantRegistration p where p.dealerId IN :dealerId and p.status ='H' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVHold(final List<Long> dealerId);
    
    @Query("From ParticipantRegistration p where (p.dealerId IN :dealerId OR :dealerId IS NULL) and p.status ='H' AND (p.registration_date >= :dateFrom AND p.registration_date <=:dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantCSVHold(final List<Long> dealerId, final Date dateFrom, final Date dateTo);
    
    @Query("From ParticipantRegistration p where (p.fsdmApprovalStatus IN ('1') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantInpprocessHo();
    
    @Query("From ParticipantRegistration p where p.fsdmApprovalStatus = ('2')  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantEmployeeHo();
    
    @Query("From ParticipantRegistration p where Status = 'H' Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantHoldHo();
    
    @Query("From ParticipantRegistration p where (p.fsdmApprovalStatus IN ('3'))  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantPendingApprovel();
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.outletCode = :outletCode AND p.designation = :designation Order By modified_date DESC")
    Optional<List<ParticipantRegistration>> getParticipantByOutletCodeAndDesg(@Param("outletCode") final String outletCode, @Param("designation") final String designation);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (p.firstName LIKE %:name%) OR :name='' OR (p.lastName=:name) OR (p.middleName=:name) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (p.testCompletionDate >= :dateFrom AND p.testCompletionDate < :dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByFilterData(@Param("outletCode") final String outletCode, @Param("name") final String name, @Param("designation") final String designation, @Param("mspin") final String mspin, @Param("passFailStatus") final List<Integer> passFailStatus, @Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByFilterData(@Param("outletCode") final String outletCode, @Param("name") final String name, @Param("designation") final String designation, @Param("mspin") final String mspin, @Param("passFailStatus") final List<Integer> passFailStatus, @Param("accessKey") final String accessKey, @Param("dealerId") final Long dealerId, @Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.testCompletionDate >= :dateFrom AND p.testCompletionDate < :dateTo Order By modified_date DESC")
    List<ParticipantRegistration> findByDateRange(@Param("dateFrom") final Date dateFrom, @Param("dateTo") final Date dateTo);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:assessment IS NULL OR p.testCompletionDate IS NOT NULL) AND (:interview IS NULL OR p.interviewDate IS NOT NULL) AND p.testStatus IS NOT NULL Order By modified_date DESC")
    List<ParticipantRegistration> findParticipantsByCompletionFilter(@Param("assessment") final String assessment, @Param("interview") final String interview);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE  (:designation IS NULL OR p.designation=:designation OR :designation= '' ) AND (:lastName IS NULL OR p.lastName=:lastName OR :lastName= '') Order By modified_date DESC")
    Optional<List<ParticipantRegistration>> getParticipantByNullValue(@Param("designation") final String desgignation, @Param("lastName") final String lastName);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.testStatus!=null Order By modified_date DESC")
    List<ParticipantRegistration> findAllParticipantWhoseTestIsCompleted();
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.state IN :stList OR (:stList) IS NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByHOFilters(@Param("stList") final List<String> stList);
    
   // @Query("SELECT p FROM ParticipantRegistration p WHERE (p.region IN :rgList OR (:rgList) IS NULL) AND (p.state IN :stList OR (:stList) IS NULL) AND(p.city IN :ctList OR (:ctList) IS NULL) AND (p.registration_date >= :dateFrom AND p.registration_date <=:dateTo) AND (p.dealerId IN :dealerIds OR (:dealerIds) IS NULL)")
   // List<ParticipantRegistration> findAllParticipantByHOFiltersData(final List<String> rgList, final List<String> stList, final List<String> ctList, final Date dateFrom, final Date dateTo, final List<Long> dealerIds);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.status = :status Order By modified_date DESC")
    List<ParticipantRegistration> findAllHoldParticipantByStatusOnHO(final String status);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.fsdmApprovalStatus = :fsdmApproveStatus Order By modified_date DESC")
    List<ParticipantRegistration> findAllFSDMApprovedParticipantOnHO(final String fsdmApproveStatus);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId)AND  (p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL)  Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByFilterData2(@Param("outletCode") final String outletCode, @Param("name") final String name, @Param("designation") final String designation, @Param("mspin") final String mspin, @Param("passFailStatus") final List<Integer> passFailStatus, @Param("dealerId") final Long dealerId, @Param("accessKey") final String accessKey);
    
   // @Query("SELECT p FROM ParticipantRegistration p WHERE (p.region IN :rgList OR (:rgList) IS NULL) AND (p.state IN :stList OR (:stList) IS NULL) AND(p.city IN :ctList OR (:ctList) IS NULL) AND (p.dealerId IN :dealerIds OR (:dealerIds) IS NULL)")
   // List<ParticipantRegistration> findAllParticipantByHOFiltersData2(final List rgList, final List stList, final List ctList, final List<Long> dealerIds);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.accessKey NOT IN :accessKeyList) AND p.status!=:status Order By modified_date DESC")
    List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyList(final List<String> accessKeyList, final String status);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE  p.status!=:status Order By modified_date DESC")
    List<ParticipantRegistration> getAllParticipantNotHold(final String status);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.accessKey NOT IN :accessKeyList Order By modified_date DESC")
    List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyListWithHold(final List<String> accessKeyList);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.status!='H' Order By modified_date DESC")
    List<ParticipantRegistration> findAllParticipantsOnHONotHold();
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.dealerId IN :list OR (:list) IS NULL) AND (p.registration_date >= :fromDate AND p.registration_date <= :toDate) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByDealerIdListAndDate(final List<Long> list, final Date fromDate, final Date toDate);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.dealerId = :adminId AND (p.registration_date >= :fromDate AND p.registration_date <= :toDate) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantForDetailedByYears(final Long adminId, final Date fromDate, final Date toDate);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.registration_date >= :fromDate AND p.registration_date <= :toDate) Order By modified_date DESC")
    List<ParticipantRegistration> findAllParticipantsByYear(final Date fromDate, final Date toDate);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) AND (p.status =:status) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnHoldByFilterData(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId, final Date dateFrom, final Date dateTo, final String status);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.status =:status) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnHoldByFilterData2(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId, final String status);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId IN :list OR (:list) IS NULL) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByFilterDataForFSDM(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final List<Long> list, final Date dateFrom, final Date dateTo);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId IN :list OR (:list) IS NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantByFilterDataForFSDM2(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final List<Long> list, final String accessKey);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo) AND (p.fsdmApprovalStatus =:fsdmApprovalStatus) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId, final Date dateFrom, final Date dateTo, final String fsdmApprovalStatus);
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode OR :outletCode='' ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation OR :designation='') AND (:mspin IS NULL OR p.mspin = :mspin OR :mspin='') AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey OR :accessKey='') AND (p.dealerId=:dealerId) AND (p.fsdmApprovalStatus =:fsdmApprovalStatus) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId, final String fsdmApprovalStatus);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation ) AND (:mspin IS NULL OR p.mspin = :mspin) AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey) AND (p.dealerId=:dealerId)AND  (p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnEmpMasterDealerByFilterData2(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation ) AND (:mspin IS NULL OR p.mspin = :mspin) AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey) AND (p.dealerId=:dealerId)AND  (p.fsdmApprovalStatus IN ('1','3') or p.fsdmApprovalStatus is NULL)  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantInporocessDealerByFilter(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (:outletCode IS NULL OR p.outletCode = :outletCode ) AND (:name IS NULL OR (LOWER(p.firstName) LIKE LOWER(:name)) OR :name='' OR (LOWER(p.lastName) LIKE LOWER(:name)) OR (LOWER(p.middleName) LIKE LOWER(:name)) OR (CONCAT(LOWER(p.firstName), ' ', LOWER(p.lastName)) LIKE LOWER(:name)) ) AND (:designation IS NULL OR p.finalDesignation = :designation ) AND (:mspin IS NULL OR p.mspin = :mspin) AND (p.passFailStatus IN :passFailStatus OR (:passFailStatus) IS NULL) AND (:accessKey IS NULL OR p.accessKey = :accessKey) AND (p.dealerId=:dealerId)AND  (p.fsdmApprovalStatus IN ('2') )  and p.testStatus ='3' and (Status not IN ('H') or Status is NULL) Order By modified_date DESC")
    List<ParticipantRegistration> getParticipantOnEmpMasterDealerByFilterData3(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE  ParticipantRegistration set modifiedDate=now() WHERE accessKey =:accesskey")
    public  int updateModiedDate(final String accesskey);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE  ParticipantRegistration set empProductivityRefId =:empProductivityRefId WHERE accessKey =:accesskey")
    public  int updateProductivityRefID(String empProductivityRefId,final String accesskey);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE  p.oldMspin = :oldMspin ")
    List<ParticipantRegistration> getOldMSPIN(final String oldMspin);

 //Status Filter Query
    @Query("SELECT p FROM ParticipantRegistration p WHERE p.testStatus ='3' AND (Status NOT IN ('H') or Status IS NULL) AND (:interviewSearch IS NULL OR p.interviewScore>=:interviewSearch) AND (p.prarambhStatus IN :praraambhSearch OR (:praraambhSearch) IS NULL) AND ((:fsdmSearch) IS NULL OR p.fsdmApprovalStatus IN (:fsdmSearch)) AND (p.dealerId IN :dealerIdList OR (:dealerIdList) IS NULL) Order By modified_date DESC")
	List<ParticipantRegistration> findParticipantsByCompletionFilterInProcess(Integer interviewSearch, String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList);

    @Query("SELECT p FROM ParticipantRegistration p WHERE p.testStatus ='3' AND (Status IN ('H')) AND (:interviewSearch IS NULL OR p.interviewScore>=:interviewSearch) AND (p.prarambhStatus IN :praraambhSearch OR (:praraambhSearch) IS NULL) AND ((:fsdmSearch) IS NULL OR p.fsdmApprovalStatus IN (:fsdmSearch)) AND (p.dealerId IN :dealerIdList OR (:dealerIdList) IS NULL) Order By modified_date DESC")
	List<ParticipantRegistration> findParticipantsByCompletionFilterOnHold(Integer interviewSearch, String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList);

    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.fsdmApprovalStatus IN ('3')) AND p.testStatus ='3' AND (Status NOT IN ('H') OR Status IS NULL) AND (p.prarambhStatus IN :praraambhSearch OR (:praraambhSearch) IS NULL) AND (p.dealerId IN :dealerIdList OR (:dealerIdList) IS NULL) Order By modified_date DESC")
	List<ParticipantRegistration> findParticipantsByCompletionFilterPendingApprovalFSDM(String praraambhSearch, List<Long> dealerIdList);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.fsdmApprovalStatus IN ('1','3') OR p.fsdmApprovalStatus IS NULL) AND p.testStatus ='3' AND (Status IN ('H')) AND (:interviewSearch IS NULL OR p.interviewScore>=:interviewSearch) AND (p.prarambhStatus IN :praraambhSearch OR (:praraambhSearch) IS NULL) AND (p.dealerId IN :dealerIdList OR (:dealerIdList) IS NULL) Order By modified_date DESC")
	List<ParticipantRegistration> findParticipantsByCompletionFilterOnHoldWithoutFSDM(Integer interviewSearch,
			String praraambhSearch, List<Long> dealerIdList);
    
    @Query("SELECT p FROM ParticipantRegistration p WHERE (p.fsdmApprovalStatus IN ('1') OR p.fsdmApprovalStatus IS NULL) AND p.testStatus ='3' AND (Status NOT IN ('H') OR Status IS NULL) AND (:interviewSearch IS NULL OR p.interviewScore>=:interviewSearch) AND (p.prarambhStatus IN :praraambhSearch OR (:praraambhSearch) IS NULL) AND (p.dealerId IN :dealerIdList OR (:dealerIdList) IS NULL) Order By modified_date DESC")
	List<ParticipantRegistration> findParticipantsByCompletionFilterInProcessWithoutFSDM(Integer interviewSearch,
			String praraambhSearch, List<Long> dealerIdList);

    
    @Query("SELECT p FROM ParticipantRegistration p where documents_status='final' AND finalDesignation='STR' AND (mspin IS NULL or mspin='')")
   	List<ParticipantRegistration> getCandidateMPINnotGerate();
    
    
    @Query("From ParticipantRegistration p where p.testStatus ='3' and questionReportStatus=0")
    List<ParticipantRegistration> getParticipant();
    
    
    @Query("From ParticipantRegistration p where p.testStatus ='3' AND (p.dealerId IN :dealerId OR (:dealerId) IS NULL) ")
    List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerId);
    @Query("From ParticipantRegistration p where p.testStatus ='3' AND (p.dealerId IN :dealerId OR (:dealerId) IS NULL) AND (p.registration_date >= :dateFrom AND p.registration_date <= :dateTo)")
    List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerId,Date dateFrom,Date dateTo);
    
    @Query("From ParticipantRegistration p where p.testStatus ='3'")
    List<ParticipantRegistration> getParticipantByDealerId();
   
    
  
    
    @Modifying(clearAutomatically = true)
    @Query("update ParticipantRegistration  set questionReportStatus=1   where accessKey =:accesskey")
    public int updatequestionReportStatus(String accesskey);
    
    @Modifying(clearAutomatically = true)
    @Query("update ParticipantRegistration  set prarambhStatus='2'   where mspin =:mspin")
    public int updatePraaarambhtStatus(String mspin);
    




}
