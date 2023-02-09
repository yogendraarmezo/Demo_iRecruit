package com.msil.irecruit.ServicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.EmergencyContact;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.FamilyDetails;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantAttemp;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.WorkExperience;
import com.msil.irecruit.Repositories.DealerRepository;
import com.msil.irecruit.Repositories.EmergencyContactRepo;
import com.msil.irecruit.Repositories.FSDMRepository;
import com.msil.irecruit.Repositories.FamilyDetailsRepo;
import com.msil.irecruit.Repositories.OutletRepository;
import com.msil.irecruit.Repositories.ParticipantAssementDetailsRepo;
import com.msil.irecruit.Repositories.ParticipantAttemptRepository;
import com.msil.irecruit.Repositories.ParticipantRepository;
import com.msil.irecruit.Repositories.ParticipantScoreRepo;
import com.msil.irecruit.Repositories.RegionRepository;
import com.msil.irecruit.Repositories.WorkExperienceRepo;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {

	@Autowired
	ParticipantRepository participantrepository;

	@Autowired
	ParticipantAttemptRepository participantAttemptRepository;

	@Autowired
	ParticipantAssementDetailsRepo participantAssementDetailsRepo;

	@Autowired
	ParticipantScoreRepo participantScoreRepo;

	@Autowired
	FamilyDetailsRepo familyDetailsRepo;

	@Autowired
	EmergencyContactRepo emergencyContactRepo;

	@Autowired
	WorkExperienceRepo workExperienceRepo;

	@Autowired
	FSDMRepository fsdmRepo;

	@Autowired
	DealerRepository dealerRepo;

	@Autowired
	RegionRepository regionRepo;

	@Autowired
	OutletRepository outletRepo;

	@Autowired
	private OutletService outletService;

	// save Participants

	// save Participants

	public ParticipantRegistration saveData(ParticipantRegistration participant) {
		return participantrepository.save(participant);

	}

	public ParticipantRegistration saveLoginDetails(ParticipantRegistration participantRegistration) {
		return participantrepository.save(participantRegistration);

	}

	// Get all Participants Family_details
	public List<FamilyDetails> getAllFamilyDetails() {
		return familyDetailsRepo.findAll();
	}

	// Get all Participants Emergency_Contact
	public List<EmergencyContact> getAllEmergencyContact() {
		return emergencyContactRepo.findAll();

	}

	// Get all Participants Work_Experience
	public List<WorkExperience> getAllWorkExperience() {
		return workExperienceRepo.findAll();

	}

	// when Participant start test then Save some data into participant_attempt'
	// Table
	public String startAssessment(ParticipantAttemp entity) {

		participantAttemptRepository.save(entity);

		return "assessment";
	}

	// save assesment detail in 'participant_attempt' and also save
	// participant_assement_details & P_score Table
	public String saveAssessment(ParticipantAttemp entity) {

		participantAttemptRepository.save(entity);
		return "assessment";
	}

	public ParticipantRegistration saveFiles(ParticipantRegistration partupload) {
		return participantrepository.save(partupload);
	}

//	public Optional<ParticipantRegistration> findByAccesskey(String accesskey) {
//
//	@Override
//	public ParticipantRegistration findByAccesskey(String accesskey) {
//

	public Optional<ParticipantRegistration> findByAccesskey(String accesskey) {
		return participantrepository.findByAccessKey(accesskey);
	}

	public Optional<ParticipantRegistration> getParticipantByFirstNameAndLastNameAndEmail(String fistName,
			String lastName, String email, Long dealerId) {

		return participantrepository.findTop1ByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndEmailIgnoreCaseAndDealerId(
				fistName, lastName, email, dealerId);
	}

	public Optional<ParticipantRegistration> getParticipantByAccesskey(String accesskey) {
		return participantrepository.findTop1ByAccessKey(accesskey);
	}

	public void deleteById(Long fid) {
		familyDetailsRepo.deleteById(fid);
	}

	public void saveFamily(FamilyDetails familyDetails) {
		familyDetailsRepo.save(familyDetails);
	}

	public void deleteById1(Long eid) {
		emergencyContactRepo.deleteById(eid);
	}

	public void saveEmergency(EmergencyContact emergencyContact) {
		emergencyContactRepo.save(emergencyContact);
	}

	public void deleteById2(Long wid) {
		workExperienceRepo.deleteById(wid);
	}

	public void saveWorkExperience(WorkExperience workExperience) {
		workExperienceRepo.save(workExperience);
	}

	public List<ParticipantRegistration> getParticipantByDealerId(long dealerId) {
		return participantrepository.findByDealerIdOrderByModifiedDateDesc(dealerId);

	}

	public List<ParticipantRegistration> getParticipantByDealerIdLsit(List<Long> dealerIds) {
		return participantrepository.findByDealerIdIn(dealerIds);

	}

	public String getFsdmName(int id) {
		Optional<FSDM> fsdm = fsdmRepo.findById(id);
		return fsdm.get().getName();
	}

	@Override
	public List<ParticipantRegistration> getAllParticipant() {
		return participantrepository.findAll();
	}

	@Override
	public String getDealerName(Long dealerId) {
		// Getting dealer name by dealer ID
		return dealerRepo.findById(dealerId).get().getName();
	}

	@Override
	public Outlet getOutletByDealerIdAndOutletCode(Long dealerId, String outletCode) {
		// getting Outlet by Dealer Id
		List<Outlet> outlets = dealerRepo.findById(dealerId).get().getOutlet();
		Outlet outletObj = null;
		for (Outlet outlet : outlets) {
			// matching same outletCode
			if (outlet.getOutletCode().equals(outletCode)) {
				outletObj = outlet;
			}
		}
		return outletObj;
	}

	// Getting Outlet By OutletCode
	@Override
	public Outlet getOutLetByOutletCode(String outletCode) {
		return outletRepo.getOutletByCode(outletCode);
	}

	@Override
	public EmergencyContact getEmergencyContactById(Long id) {
		return emergencyContactRepo.findById(id).get();
	}

	@Override
	public FamilyDetails getOneFamilyDetailsById(Long fid) {
		return familyDetailsRepo.findById(fid).get();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByOutletCodeAndDesg(String outletCode, String designation) {
		Optional<List<ParticipantRegistration>> participantOptional = participantrepository
				.getParticipantByOutletCodeAndDesg(outletCode, designation);
		return participantOptional.get();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Date dateFrom, Date dateTo) {
		List<ParticipantRegistration> part = participantrepository.getParticipantByFilterData(outletCode, candidateName,
				designation, mspin, passFailStatus, dateFrom, dateTo);
		return part;
	}

	@Override
	public List<ParticipantRegistration> findByDateRange(Date dateFrom, Date dateTo) {
		return participantrepository.findByDateRange(dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> findParticipantsByCompletionFilter(String assessment, String interview) {
		return participantrepository.findParticipantsByCompletionFilter(assessment, interview);
	}

	@Override
	public List<ParticipantRegistration> getEmployee(Long dealerId, String fsdmApprovalStatus) {
		return participantrepository.getEmployee(dealerId, fsdmApprovalStatus);
	}

	@Override
	public List<ParticipantRegistration> getHoldEmployee(long dealerId, String status) {
		return participantrepository.getHoldEmployee(dealerId, status);
	}

	@Override
	public List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, String uniqueCode, Long dealerId,
			Date dateFrom, Date dateTo) {
		List<ParticipantRegistration> part = participantrepository.getParticipantByFilterData(outletCode, candidateName,
				designation, mspin, passFailStatus, uniqueCode, dealerId, dateFrom, dateTo);

		return part;
	}

	@Override
	public List<ParticipantRegistration> getParticipantForNotification(List<Long> dealerId, String prarambhStatus,
			String testStatus) {
		return participantrepository.findByDealerIdInAndPrarambhStatusAndTestStatusOrderByModifiedDateDesc(dealerId,
				prarambhStatus, testStatus);
	}

	@Override
	public List<ParticipantRegistration> findAllByTestStatus() {
		return participantrepository.findAllParticipantWhoseTestIsCompleted();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByHOFilters(List<String> stList) {
		return participantrepository.getParticipantByHOFilters(stList);
	}

	@Override
	public List<ParticipantRegistration> getParticipantByHOFiltersData(List<String> rgList, List<String> stList,
			List<String> ctList, List<String> pdList, List<String> dList, List<String> fList, Date dateFrom,
			Date dateTo) {
		List<Outlet> outlets = outletService.getAllOutletByPDCodeDealerMspinRegionCode(pdList, dList, rgList);
		List<Long> dealerIds = new ArrayList<Long>();
		for (Outlet o : outlets) {
			dealerIds.add(o.getDealer().getId());
		}
		if (dealerIds.isEmpty()) {
			dealerIds = null;
		}
		// return
		// participantrepository.findAllParticipantByHOFiltersData(rgList,stList,ctList,dateFrom,dateTo,dealerIds);
		return null;
	}

	@Override
	public List<ParticipantRegistration> findAllHoldParticipantsOnHO(String status) {
		// return participantrepository.findAllHoldParticipantByStatusOnHO(status);
		return new ArrayList<ParticipantRegistration>();
	}

	@Override
	public List<ParticipantRegistration> getAllFSDMApprovedParticipantOnHO(String fsdmApproveStatus) {
		// return
		// participantrepository.findAllFSDMApprovedParticipantOnHO(fsdmApproveStatus);
		return new ArrayList<ParticipantRegistration>();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Long dealerId) {
		return null;
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVInpprocess(long dealerId) {
		return participantrepository.getParticipantCSVInpprocess(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVHold(long dealerId) {
		return participantrepository.getParticipantCSVHold(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVImployee(long dealerId) {
		return participantrepository.getParticipantCSVImployee(dealerId);
	}

	public List<ParticipantRegistration> getParticipantByFilterData2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Long dealerId, String uniqueCode) {
		return participantrepository.getParticipantByFilterData2(outletCode, candidateName, designation, mspin,
				passFailStatus, dealerId, uniqueCode);
		// return participantrepository.getParticipantInporocessDealerByFilter(
		// outletCode, uniqueCode,
		// designation, mspin, passFailStatus, uniqueCode, dealerId);

	}

	@Override
	public List<ParticipantRegistration> getParticipantByHOFiltersData2(List rgList, List stList, List ctList,
			List pdList, List dList, List fList) {
		List<Outlet> outlets = outletService.getAllOutletByPDCodeDealerMspinRegionCode(pdList, dList, rgList);
		List<Long> dealerIds = new ArrayList<Long>();
		for (Outlet o : outlets) {
			dealerIds.add(o.getDealer().getId());
		}
		if (dealerIds.isEmpty()) {
			dealerIds = null;
		}
		// return participantrepository.findAllParticipantByHOFiltersData2(rgList,
		// stList, ctList, dealerIds);
		return null;
	}

	// Dashboard Uses
	@Override
	public List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyList(List<String> accessKeyList,
			String holdStatus) {
		return participantrepository.getAllParticipantNotInWithAccessKeyList(accessKeyList, holdStatus);
	}

	@Override
	public List<ParticipantRegistration> getAllParticipantNotHold(String status) {
		return participantrepository.getAllParticipantNotHold(status);
	}

	@Override
	public List<ParticipantRegistration> getAllParticipantNotInWithAccessKeyListWithHold(List<String> accessKeyList) {
		return participantrepository.getAllParticipantNotInWithAccessKeyListWithHold(accessKeyList);
	}

	@Override
	public List<ParticipantRegistration> findAllParticipantsOnHONotHold() {
		return participantrepository.findAllParticipantsOnHONotHold();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByDealerIdListAndDate(List<Long> list, Date fromDate,
			Date toDate) {
		return participantrepository.getParticipantByDealerIdListAndDate(list, fromDate, toDate);
	}

	@Override
	public List<ParticipantRegistration> getParticipantForDetailedByYears(Long adminId, Date fromDate, Date toDate) {
		return participantrepository.getParticipantForDetailedByYears(adminId, fromDate, toDate);
	}

	@Override
	public List<ParticipantRegistration> findAllParticipantsByYear(Date fromDate, Date toDate) {
		return participantrepository.findAllParticipantsByYear(fromDate, toDate);
	}

	@Override
	public List<ParticipantRegistration> getParticipantOnHoldByFilterData(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, String uniqueCode, Long dealerId,
			Date dateFrom, Date dateTo, String status) {
		return participantrepository.getParticipantOnHoldByFilterData(outletCode, candidateName, designation, mspin,
				passFailStatus, uniqueCode, dealerId, dateFrom, dateTo, status);
	}

	@Override
	public List<ParticipantRegistration> getParticipantOnHoldByFilterData2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, Long dealerId, String uniqueCode,
			String status) {
		return participantrepository.getParticipantOnHoldByFilterData2(outletCode, candidateName, designation, mspin,
				passFailStatus, uniqueCode, dealerId, status);
	}

	@Override
	public List<ParticipantRegistration> getParticipantByFilterDataForFSDM(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, String uniqueCode, List<Long> list,
			Date dateFrom, Date dateTo) {
		return participantrepository.getParticipantByFilterDataForFSDM(outletCode, candidateName, designation, mspin,
				passFailStatus, uniqueCode, list, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantByFilterDataForFSDM2(String outletCode, String candidateName,
			String designation, String mspin, List<Integer> passFailStatus, List<Long> list, String uniqueCode) {
		return participantrepository.getParticipantByFilterDataForFSDM2(outletCode, candidateName, designation, mspin,
				passFailStatus, list, uniqueCode);
	}

	@Override
	public List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(String outletCode,
			String candidateName, String designation, String mspin, List<Integer> passFailStatus, String uniqueCode,
			Long dealerId, Date dateFrom, Date dateTo, String fsdmApprovalStatus) {
		return participantrepository.getParticipantOnEmployeeMasterDealerByFilterData(outletCode, candidateName,
				designation, mspin, passFailStatus, uniqueCode, dealerId, dateFrom, dateTo, fsdmApprovalStatus);
	}

	@Override
	public List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData2(String outletCode,
			String candidateName, String designation, String mspin, List<Integer> passFailStatus, Long dealerId,
			String uniqueCode) {
		return participantrepository.getParticipantOnEmpMasterDealerByFilterData2(outletCode, candidateName,
				designation, mspin, passFailStatus, uniqueCode, dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantInpprocessHo() {
		return participantrepository.getParticipantInpprocessHo();
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(List<Long> dealerId, Date dateFrom,
			Date dateTo) {
		return participantrepository.getParticipantCSVPendingApprovel(dealerId, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVImployee(List<Long> dealerId, Date dateFrom, Date dateTo) {
		return participantrepository.getParticipantCSVImployee(dealerId, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVHold(List<Long> dealerId, Date dateFrom, Date dateTo) {
		return participantrepository.getParticipantCSVHold(dealerId, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVInpprocess(List<Long> dealerId, Date dateFrom, Date dateTo) {
		return participantrepository.getParticipantCSVInpprocess(dealerId, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantEmployeeHo() {
		return participantrepository.getParticipantEmployeeHo();
	}

	@Override
	public List<ParticipantRegistration> getParticipantHoldHo() {
		return participantrepository.getParticipantHoldHo();
	}

	@Override
	public List<ParticipantRegistration> getParticipantPendingApprovel() {
		return participantrepository.getParticipantPendingApprovel();
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVInpprocess(List<Long> dealerId) {
		return participantrepository.getParticipantCSVInpprocess(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVImployee(List<Long> dealerId) {
		return participantrepository.getParticipantCSVImployee(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVHold(List<Long> dealerId) {
		return participantrepository.getParticipantCSVHold(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(List<Long> dealerId) {
		return participantrepository.getParticipantCSVPendingApprovel(dealerId);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVPendingApprovel(long dealerId) {
		List<Long> list = new ArrayList<Long>();
		list.add(dealerId);
		return participantrepository.getParticipantCSVPendingApprovel(list);
	}

	@Override
	public List<ParticipantRegistration> getParticipantCSVInpprocess(long dealerId, Date dateFrom, Date dateTo) {

		return participantrepository.getParticipantCSVInpprocess(dealerId, dateFrom, dateTo);
	}

	/* filter dealer */
	@Override
	public List<ParticipantRegistration> getParticipantFilterInpprocess(String outletCode, String name,
			String designation, String mspin, List<Integer> passFailStatus, String accessKey, Long dealerId,
			Date dateFrom, Date dateTo) {
		return participantrepository.getParticipantFilterInpprocess(outletCode, name, designation, mspin,
				passFailStatus, accessKey, dealerId, dateFrom, dateTo);
	}

	@Override
	public List<ParticipantRegistration> getParticipantFilterEmployee(String outletCode, String name,
			String designation, String mspin, List<Integer> passFailStatus, String accessKey, Long dealerId,
			Date dateFrom, Date dateTo,String fsdmApprovalStatus) {
		List<ParticipantRegistration> list =participantrepository.getParticipantFilterEmployee(outletCode, name, designation, mspin, passFailStatus,
				accessKey, dealerId, dateFrom, dateTo,fsdmApprovalStatus);
		return list;
	}

	@Override
	public List<ParticipantRegistration> getParticipantOnEmployeeMasterDealerByFilterData(String outletCode,
			String name, String designation, String mspin, List<Integer> passFailStatus, String accessKey,
			Long dealerId, String fsdmApprovalStatus) {
		return participantrepository.getParticipantOnEmployeeMasterDealerByFilterData(outletCode, name, designation,
				mspin, passFailStatus, accessKey, dealerId, fsdmApprovalStatus);
	}

	@Override
	public int updateModiedDate(String accesskey) {
		int result = participantrepository.updateModiedDate(accesskey);
		return result;
	}

	@Override
	public List<ParticipantRegistration> findByDealerIdInFSDM(List<Long> dealerId) {
		return participantrepository.findByDealerIdPendinApproverFSDM(dealerId);
	}

	@Override
	public List<ParticipantRegistration> findByDealerIdInprocessFSDM(List<Long> dealerId) {
		return participantrepository.findByDealerIdInprocessFSDM(dealerId);
	}

	@Override
	public List<ParticipantRegistration> findByDealerIdEmployeeFSDM(List<Long> dealerId) {
		return participantrepository.findByDealerIdEmployeeFSDM(dealerId);
	}

	@Override
	public int updateProductivityRefID(String empProductivityRefId, String accesskey) {
		return participantrepository.updateProductivityRefID(empProductivityRefId, accesskey);
	}

	@Override
	public List<ParticipantRegistration> getOldMSPIN(String oldMspin) {
		return participantrepository.getOldMSPIN(oldMspin);
	}
	
	@Override
	public List<ParticipantRegistration> findParticipantsByCompletionFilterInProcess(Integer interviewSearch,
			String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList) {
		List<ParticipantRegistration> list= new ArrayList<ParticipantRegistration>();
		if(fsdmSearch.isEmpty() || fsdmSearch==null) {
			list=participantrepository.findParticipantsByCompletionFilterInProcessWithoutFSDM(interviewSearch,praraambhSearch,dealerIdList);
		}else {
			list= participantrepository.findParticipantsByCompletionFilterInProcess(interviewSearch,praraambhSearch,fsdmSearch, dealerIdList);
		}
		return list;
	}
	@Override
	public List<ParticipantRegistration> findParticipantsByCompletionFilterOnHold(Integer interviewSearch,
			String praraambhSearch, List<String> fsdmSearch, List<Long> dealerIdList) {
		List<ParticipantRegistration> list= new ArrayList<ParticipantRegistration>();
		if(fsdmSearch.isEmpty() || fsdmSearch==null) {
			list=participantrepository.findParticipantsByCompletionFilterOnHoldWithoutFSDM(interviewSearch,praraambhSearch,dealerIdList);
		}else {
			list= participantrepository.findParticipantsByCompletionFilterOnHold(interviewSearch,praraambhSearch,fsdmSearch,dealerIdList);
		}
		return list;
	}

	@Override
	public List<ParticipantRegistration> findParticipantsByCompletionFilterPendingApprovalFSDM(String praraambhSearch,
			List<Long> dealerIdList) {
		return participantrepository.findParticipantsByCompletionFilterPendingApprovalFSDM(praraambhSearch,dealerIdList);
	}

	@Override
	public List<ParticipantRegistration> getCandidateMPINnotGerate() {
		// TODO Auto-generated method stub
		return participantrepository.getCandidateMPINnotGerate();
	}

	@Override
	public List<ParticipantRegistration> getParticipant() {
		// TODO Auto-generated method stub
		return   participantrepository.getParticipant();
	}

	@Override
	public int updatequestionReportStatus(String accesskey) {
		
		return participantrepository.updatequestionReportStatus(accesskey);
	}

	@Override
	public List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerIdList) {
		// TODO Auto-generated method stub
		return   participantrepository.getParticipantByDealerId(dealerIdList);
	}
	
	@Override
	public List<ParticipantRegistration> getParticipantByDealerId() {
		// TODO Auto-generated method stub
		return   participantrepository.getParticipantByDealerId();
	}

	@Override
	public List<ParticipantRegistration> getParticipantByDealerId(List<Long> dealerIdList, Date dateFrom, Date dateTo) {
		// TODO Auto-generated method stub
		return participantrepository.getParticipantByDealerId(dealerIdList,dateFrom,dateTo);
	}

	@Override
	public int updatePraaarambhtStatus(String mspin) {
		// TODO Auto-generated method stub
		return participantrepository.updatePraaarambhtStatus( mspin);
	}
	

	

}
