package com.msil.irecruit.Services;


import java.util.Date;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.EmergencyContact;
import com.msil.irecruit.Entities.FamilyDetails;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantAttemp;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.WorkExperience;

public interface ParticipantService {
	
	public ParticipantRegistration saveData(ParticipantRegistration entity);
	public List<ParticipantRegistration> getAllParticipant();
	public String startAssessment(ParticipantAttemp entity);
	public String saveAssessment(ParticipantAttemp entity);
	public ParticipantRegistration saveFiles(ParticipantRegistration participantRegistration);
	public Optional<ParticipantRegistration> findByAccesskey(String accesskey);
	public ParticipantRegistration saveLoginDetails(ParticipantRegistration participantRegistration);
	public  Optional<ParticipantRegistration> getParticipantByFirstNameAndLastNameAndEmail(String fistName,String lastName,String email,Long dealerId);
	public  Optional<ParticipantRegistration> getParticipantByAccesskey(String accesskey);
	public List<WorkExperience> getAllWorkExperience();
	public void deleteById(Long wid);
	public List<FamilyDetails> getAllFamilyDetails();
	public void saveFamily(FamilyDetails familyDetails);
	public List<EmergencyContact> getAllEmergencyContact();
	public String getDealerName(Long dealerId);
	public Outlet getOutletByDealerIdAndOutletCode(Long dealerId, String outletCode);
	public Outlet getOutLetByOutletCode(String outletCode);
	public EmergencyContact getEmergencyContactById(Long id);
	public FamilyDetails getOneFamilyDetailsById(Long fid);
	public List<ParticipantRegistration> getEmployee(Long dealerId,String fsdmApprovalStatus);
	public List<ParticipantRegistration> getParticipantByDealerId(long dealerId);
	public List<ParticipantRegistration> getParticipantByOutletCodeAndDesg(String outletCode, String designation);
	public List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Date dateFrom, Date dateTo);
	public List<ParticipantRegistration> findByDateRange(Date d1, Date d2);
	public List<ParticipantRegistration> findParticipantsByCompletionFilter(String assessment, String interview);
	public List<ParticipantRegistration> getHoldEmployee(long dealerId,String status);
	public List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Long dealerId);

	public List<ParticipantRegistration> getParticipantForNotification(List<Long> dealerId, String prarambhStatus,String testStatus);
	public List<ParticipantRegistration> findAllByTestStatus();
	public List<ParticipantRegistration> getParticipantByHOFilters(List<String> rgList);
	public List<ParticipantRegistration> getParticipantByHOFiltersData(List<String> rgList, List<String> stList,
					List<String> ctList, List<String> pdList, List<String> dList, List<String> fList, Date dateFrom, Date dateTo);
	public List<ParticipantRegistration> getParticipantByDealerIdLsit(List<Long> list);
	public List<ParticipantRegistration> findAllHoldParticipantsOnHO(String status);
	public List<ParticipantRegistration> getAllFSDMApprovedParticipantOnHO(String fsdmApproveStatus);
	List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFStatus, String uniqueCode, Long dealerId, Date dateFrom, Date dateTo);

	
	public List<ParticipantRegistration>  getParticipantCSVInpprocess(long dealerId);
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(long dealerId );
	public List<ParticipantRegistration>  getParticipantCSVHold(long dealerId);
	public List<ParticipantRegistration>  getParticipantCSVImployee(long dealerId);
	
	/* FSDM */
	public List<ParticipantRegistration> getParticipantCSVInpprocess(List<Long> dealerId );
    public List<ParticipantRegistration> getParticipantCSVImployee(List<Long> dealerId );
	public List<ParticipantRegistration> getParticipantCSVHold(List<Long> dealerId);
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(List<Long> dealerId );
	public List<ParticipantRegistration> getParticipantCSVInpprocess(List<Long> dealerId,Date dateFrom, Date dateTo );
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(List<Long> dealerId,Date dateFrom, Date dateTo );
	public List<ParticipantRegistration> getParticipantCSVImployee(List<Long> dealerId,Date dateFrom, Date dateTo );
	public List<ParticipantRegistration> getParticipantCSVHold(List<Long> dealerId,Date dateFrom, Date dateTo );
	
	public List<ParticipantRegistration> getParticipantByHOFiltersData2(List rgList, List stList, List ctList,
			List pdList, List dList, List fList);
	public List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyList(List<String> accessKeyList, String string);
	public List<ParticipantRegistration> getAllParticipantNotHold(String status);
	public List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyListWithHold(List<String> accessKeyList);

	public List<ParticipantRegistration> findAllParticipantsOnHONotHold();
	public List<ParticipantRegistration> getParticipantByDealerIdListAndDate(List<Long> list, Date fromDate, Date toDate);
	public List<ParticipantRegistration> getParticipantForDetailedByYears(Long adminId, Date fromDate, Date toDate);
	public List<ParticipantRegistration> findAllParticipantsByYear(Date fromDate, Date toDate);
	public List<ParticipantRegistration> getParticipantOnHoldByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFStatus, String uniqueCode, Long dealerId, Date dateFrom,
			Date dateTo, String status);
	public List<ParticipantRegistration> getParticipantOnHoldByFilterData2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFStatus, Long dealerId, String uniqueCode, String status);
	public List<ParticipantRegistration> getParticipantByFilterDataForFSDM(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, String uniqueCode, List<Long> list, Date dateFrom,
			Date dateTo);
	public List<ParticipantRegistration> getParticipantByFilterDataForFSDM2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, List<Long> list, String uniqueCode);
	public List<ParticipantRegistration> getParticipantByFilterData2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFStatus, Long dealerId, String uniqueCode);
	public List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(String outletCode,
			String candidateName, String designation, String mspin, List<Integer> passFStatus, String uniqueCode, Long dealerId,
			Date dateFrom, Date dateTo, String fsdmApprovalStatus);
	
	public List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData2(String outletCode,
			String candidateName, String designation, String mspin, List<Integer> passFStatus, Long dealerId, String uniqueCode);

	
	/* Ho */
	 public List<ParticipantRegistration> getParticipantInpprocessHo();
	 public List<ParticipantRegistration> getParticipantEmployeeHo();
	 public List<ParticipantRegistration> getParticipantHoldHo();
	 public List<ParticipantRegistration> getParticipantPendingApprovel();
	 
	 
	 public List<ParticipantRegistration> getParticipantCSVInpprocess(@Param("dealerId")long dealerId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	 
	/* filtr dealer */
	 List<ParticipantRegistration> getParticipantFilterInpprocess( String outletCode,String name, String designation,String mspin,List<Integer> passFailStatus, 
				String accessKey,Long dealerId,Date dateFrom,Date dateTo);
	 
	 List<ParticipantRegistration> getParticipantFilterEmployee(@Param("outletCode") String outletCode,@Param("name") String name,
				@Param("designation") String designation, @Param("mspin") String mspin, @Param("passFailStatus") List<Integer> passFailStatus, 
				@Param("accessKey") String accessKey, @Param("dealerId") Long dealerId,@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, String fsdmApprovalStatus);
	 
	 List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(final String outletCode, final String name, final String designation, final String mspin, final List<Integer> passFailStatus, final String accessKey, final Long dealerId, final String fsdmApprovalStatus);
	 public  int updateModiedDate(final String accesskey);
	 public  int updateProductivityRefID(String empProductivityRefId,final String accesskey);
	 
	/* fsdm */
	 List<ParticipantRegistration> findByDealerIdInFSDM(final List<Long> dealerId);
	 List<ParticipantRegistration> findByDealerIdInprocessFSDM(final List<Long> dealerId);
	 List<ParticipantRegistration> findByDealerIdEmployeeFSDM(final List<Long> dealerId);
	 List<ParticipantRegistration> getOldMSPIN(final String oldMspin);
	  //Status Filter
	public List<ParticipantRegistration> findParticipantsByCompletionFilterInProcess(Integer interviewSearch,
			String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList);
	public List<ParticipantRegistration> findParticipantsByCompletionFilterOnHold(Integer interviewSearch,
			String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList);
	public List<ParticipantRegistration> findParticipantsByCompletionFilterPendingApprovalFSDM(String praraambhSearch,
			List<Long> dealerIdList);
	
	List<ParticipantRegistration> getCandidateMPINnotGerate();
	
	List<ParticipantRegistration> getParticipant();
	List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerIdList);
	 List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerIdList,Date dateFrom,Date dateTo);
	List<ParticipantRegistration> getParticipantByDealerId();
    public int updatequestionReportStatus(String accesskey);
    public int updatePraaarambhtStatus(String mspin);
	
}

