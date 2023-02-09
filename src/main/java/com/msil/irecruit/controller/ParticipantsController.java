package com.msil.irecruit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.hibernate.TypeMismatchException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.EmergencyContact;
import com.msil.irecruit.Entities.FSDMNotification;
import com.msil.irecruit.Entities.FamilyDetails;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.WorkExperience;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.EmergencyContactService;
import com.msil.irecruit.Services.FSDMNotificationService;
import com.msil.irecruit.Services.FamilyDetailService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.WorkExperienceService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.controller.DmsController;
import com.msil.irecruit.dms.entities.DepartmentDms;
import com.msil.irecruit.dms.entities.DesignationDms;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.payload.LoginKey;
import com.msil.irecruit.dms.payload.TehsilVillageRequest;
import com.msil.irecruit.dms.service.DepartmentDmsService;
import com.msil.irecruit.dms.service.DesignationServiceDms;
import com.msil.irecruit.dms.service.ListDmsService;
import com.msil.irecruit.dms.service.StateCityPinDmsService;
import com.msil.irecruit.dms.service.TehsilVillageService;
import com.msil.irecruit.dms.service.impl.DesignationDmsServiceImpl;
import com.msil.irecruit.dms.service.impl.ListDmsServiceImpl;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.utils.DataProccessor;

@Controller
@RequestMapping("")
public class ParticipantsController {

	 @Autowired
	    private ParticipantServiceImpl participantservice;
	    @Autowired
	    private OutletService outletService;
	    @Autowired
	    private DealerService dealerService;
	    @Autowired
	    private FamilyDetailService familyDetailService;
	    @Autowired
	    private EmergencyContactService emergencyContactService;
	    @Autowired
	    private WorkExperienceService workExperienceService;
	    @Autowired
	    private ListDmsServiceImpl listDmsServiceImpl;
	    @Autowired
	    private DesignationServiceDms designationServiceDms;
	    @Autowired
	    private DepartmentDmsService departmentDmsService;
	    @Autowired
	    DesignationService designationService;
	    @Autowired
	    private StateCityPinDmsService stateCityPinDmsService;
	    @Autowired
	    private FSDMNotificationService fsdmMNotificationService;
	    @Autowired
	    DataScienceService dataScienceService;
	    @Autowired
	    InterviewScoreService interviewScoreService;
	    @Autowired
	    private TehsilVillageService tService;
	    @Autowired
	    private DmsController dmsController;
	    @Value("${Ap.assessmentURL}")
	    private String assessmentURL;
	    
	    @Value("${Ap.dmsURL}")
		private String dmsURL;
	    
	    @GetMapping({ "/registration" })
	    public String home(@ModelAttribute("participantRegistration") final ParticipantRegistration participantRegistration, @RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> partipant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (!partipant.isPresent() || partipant.get().getRegStatus() == null) {
	            model.addAttribute("participant", (Object)partipant.get());
	            return "Participant Registration";
	        }
	        if (partipant.get().getRegStatus().equals("2")) {
	            return "redirect:https://assess.armezo.com/iRecruit/assess/alloginpro?accesskey=" + partipant.get().getAccessKey();
	        }
	        return "done";
	    }
	    
	    @PostMapping({ "/login" })
	    public String participantLogin(final ParticipantRegistration participantRegistration) {
	        this.participantservice.saveLoginDetails(participantRegistration);
	        return "Participant Registration";
	    }
	    
	    @PostMapping({ "/upload" })
	    public ResponseEntity<String> uploadFile(@RequestParam("accessKey") final String accessKey, @RequestParam("file") final MultipartFile file, @RequestParam("name") final String name, @RequestParam("identity_proof") final String identityProof, @RequestParam("address_proof") final String addressProof) {
	        System.out.println("Cal.....id...." + identityProof + "...ad...." + addressProof);
	        final String path = "/home/msilazuser01/irecruit/" + accessKey + "/";
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        final File directory = new File(path);
	        if (!directory.exists()) {
	            directory.mkdir();
	        }
	        try {
	            file.transferTo(new File(String.valueOf(String.valueOf(path)) + fileName));
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        if (name.equals("photograph")) {
	            participant.get().setPhotograph(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("resume")) {
	            participant.get().setResume(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("signature")) {
	            participant.get().setSignature(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("identitityProof")) {
	            participant.get().setIdentitityProof(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	            participant.get().setIdentitityProofName(identityProof);
	            participant.get().setIdProof(identityProof);
	        }
	        if (name.equals("addressProof")) {
	            participant.get().setAddressProof(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	            participant.get().setAddressProofName(addressProof);
	        }
	        if (name.equals("10th")) {
	            participant.get().setQualification(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("12th")) {
	            participant.get().setQualification2(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("Graduation")) {
	            participant.get().setQualification3(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("resigningLetter")) {
	            participant.get().setResignationLetter(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("experienceletter")) {
	            participant.get().setExperienceletter(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("salaryslip")) {
	            participant.get().setSalarySlip(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        if (name.equals("documents")) {
	            participant.get().setDocuments(String.valueOf(String.valueOf(accessKey)) + "/" + fileName);
	        }
	        participant.get().setModifiedDate(new Date());
	        this.participantservice.saveFiles((ParticipantRegistration)participant.get());
	        return (ResponseEntity<String>) ResponseEntity.ok(("images/" + accessKey + "/" + fileName));
	    }
	    
	    @ExceptionHandler({ TypeMismatchException.class })
	    @PostMapping({ "/reg" })
	    public String addParticipants(@ModelAttribute("participantRegistration") @Validated final ParticipantRegistration participantRegistration, final BindingResult result) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(participantRegistration.getAccessKey());
	        participant.get().setBirthDate(participantRegistration.getBirthDate());
	        participant.get().setGender(participantRegistration.getGender());
	        participant.get().setAddress(participantRegistration.getAddress());
	        participant.get().setCity(participantRegistration.getCity());
	        participant.get().setState(participantRegistration.getState());
	        participant.get().setPin(participantRegistration.getPin());
	        participant.get().setHighestQualification(participantRegistration.getHighestQualification());
	        participant.get().setAdharNumber(participantRegistration.getAdharNumber());
	        participant.get().setTwoWheeler(participantRegistration.getTwoWheeler());
	        participant.get().setFourWheeler(participantRegistration.getFourWheeler());
	        if (participantRegistration.getTwoWheeler() != null) {
	            participant.get().setKnowDriving(participantRegistration.getTwoWheeler());
	        }
	        participant.get().setOwnTwoWheeler(participantRegistration.getOwnTwoWheeler());
	        participant.get().setOwnFourWheeler(participantRegistration.getOwnFourWheeler());
	        participant.get().setTitle(participantRegistration.getTitle());
	        participant.get().setSource(participantRegistration.getSource());
	        participant.get().setTotal(participantRegistration.getTotal());
	        participant.get().setExperience(participantRegistration.getExperience());
	        participant.get().setFresher(participantRegistration.getFresher());
	        participant.get().setAutoIndustryExperience(participantRegistration.getAutoIndustryExperience());
	        participant.get().setNonAutoIndustryExperience(participantRegistration.getNonAutoIndustryExperience());
	        participant.get().setRegStatus("2");
	        participant.get().setTestStatus("1");
	        participant.get().setPrimaryLanguage(participantRegistration.getPrimaryLanguage());
	        participant.get().setSecondaryLanguage(participantRegistration.getSecondaryLanguage());
	        participant.get().setMartialStatus(participantRegistration.getMartialStatus());
	        participant.get().setWorkedWithMSILBefore(participantRegistration.getWorkedWithMSILBefore());
	        participant.get().setOldMspin(participantRegistration.getOldMspin());
	        participant.get().setMsilExp(participantRegistration.getMsilExp());
	        participant.get().setKnowDriving(participantRegistration.getKnowDriving());
	        participant.get().setAge(participantRegistration.getAge());
	        participant.get().setMdsCertified(participantRegistration.getMdsCertified());
	        participant.get().setDL(participantRegistration.getDL());
	        participant.get().setOldMspin(participantRegistration.getOldMspin());
	        participant.get().setOldMSPINStatus(participantRegistration.getOldMSPINStatus());
	        participant.get().setModifiedDate(new Date());
	        if (participant.get().getDesignation() != null && participant.get().getDesignation().equals("Sales") && participantRegistration.getWorkedWithMSILBefore() != null && participantRegistration.getWorkedWithMSILBefore().equals("No")) {
	            participant.get().setFinalDesignation("STR");
	        }
	        final ParticipantRegistration p = this.participantservice.saveData((ParticipantRegistration)participant.get());
	        if (participant.get().getDesignation().equals("Sales")) {
	            final Optional<DataScience> dataScience = (Optional<DataScience>)this.dataScienceService.findByAccesskey(participantRegistration.getAccessKey());
	            if (dataScience.isPresent()) {
	                dataScience.get().setFirstName(p.getFirstName());
	                dataScience.get().setMiddleName(p.getMiddleName());
	                dataScience.get().setLastName(p.getLastName());
	                if (p.getTotal() != null) {
	                    dataScience.get().setTotal(p.getTotal().toString());
	                }
	                dataScience.get().setWorkedWithMSILBefore(p.getWorkedWithMSILBefore());
	                dataScience.get().setOldMspin(p.getOldMspin());
	                dataScience.get().setMsilExp(p.getMsilExp());
	                final Optional<String> city = (Optional<String>)this.stateCityPinDmsService.getStateByCityCode(p.getCity());
	                if (city.isPresent()) {
	                    dataScience.get().setResidenceOf((String)city.get());
	                }
	                if (p.getAutoIndustryExperience() == null || p.getAutoIndustryExperience() == 0) {
	                    dataScience.get().setAutoIndustryExperience("No");
	                }
	                else {
	                    dataScience.get().setAutoIndustryExperience("Yes");
	                }
	                if (p.getTwoWheeler() != null) {
	                    dataScience.get().setKnowDriving(p.getTwoWheeler());
	                }
	                if (p.getFourWheeler() != null) {
	                    dataScience.get().setKnowDriving(p.getFourWheeler());
	                }
	                dataScience.get().setDL(p.getDL());
	                dataScience.get().setMdsCertified(p.getMdsCertified());
	                dataScience.get().setOwnTwoWheeler(p.getOwnTwoWheeler());
	                dataScience.get().setHighestQualification(p.getHighestQualification());
	                dataScience.get().setGender(p.getGender());
	                dataScience.get().setAge(p.getAge());
	                dataScience.get().setPrimaryLanguage(p.getPrimaryLanguage());
	                dataScience.get().setSecondaryLanguage(p.getSecondaryLanguage());
	                dataScience.get().setDealerCode(p.getOutletCode());
	                dataScience.get().setMartialStatus(p.getMartialStatus());
	                this.dataScienceService.save((DataScience)dataScience.get());
	            }
	        }
	        return "redirect:" + this.assessmentURL + "assess/IRCreg?accesskey=" + participant.get().getAccessKey();
	    }
	    
	    @GetMapping({ "/profileDetails" })
	    public String getParticipantProfile(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> partipantOptional = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        final ParticipantRegistration participant = partipantOptional.get();
	        final Optional<Dealer> dealer = (Optional<Dealer>)this.dealerService.getById((long)participant.getDealerId());
	        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        final List<Outlet> outList = (List<Outlet>)dealer.get().getOutlet();
	        String assessmentDate = "";
	        String interviewDate = "";
	        String fsdm = "";
	        String outletName = "";
	        String outletCode = "";
	        String region = "";
	        final String joiningDate = "";
	        String dealerName = "";
	        String city = "";
	        String state = "";
	        String fsdmmApprovalDate = "";
	        for (final Outlet o : outList) {
	            if (o.getOutletCode().equals(participant.getOutletCode())) {
	                fsdm = o.getRegion().getFsdm().getName();
	                participant.setOutletCode(o.getOutletName());
	                outletName = o.getOutletName();
	                outletCode = o.getOutletCode();
	                region = o.getRegion().getRegionCode();
	                dealerName = dealer.get().getName();
	                fsdm = o.getRegion().getFsdm().getName();
	                region = o.getRegion().getRegionCode();
	                city = o.getCity().getCityName();
	                state = o.getState().getStateName();
	            }
	        }
	        if (participant.getTestCompletionDate() != null) {
	            assessmentDate = formatter.format(participant.getTestCompletionDate());
	        }
	        if (participant.getInterviewDate() != null) {
	            interviewDate = formatter.format(participant.getInterviewDate());
	        }
	        if (participant.getFsdmApprovalDate() != null) {
	            fsdmmApprovalDate = formatter.format(participant.getFsdmApprovalDate());
	        }
	        participant.getJoiningDate();
	        this.stateCityPinDmsService.getStateByStateCode(participant.getState());
	        this.stateCityPinDmsService.getStateByCityCode(participant.getCity());
	        model.addAttribute("fsdmmApprovalDate", (Object)fsdmmApprovalDate);
	        model.addAttribute("city", (Object)city);
	        model.addAttribute("state", (Object)state);
	        model.addAttribute("dealerName", (Object)outletName);
	        model.addAttribute("fsdm", (Object)fsdm);
	        model.addAttribute("outletName", (Object)outletName);
	        model.addAttribute("outletCode", (Object)outletCode);
	        model.addAttribute("region", (Object)region);
	        model.addAttribute("Accessment_date", (Object)assessmentDate);
	        model.addAttribute("interviewDate", (Object)interviewDate);
	        model.addAttribute("joiningDate", (Object)joiningDate);
	        model.addAttribute("profile", (Object)participant);
	        return "profile";
	    }
	    
	    @GetMapping({ "/getPersonalDetails" })
	    public String getParticipantPersonalDetails(final HttpSession session, @RequestParam("accesskey") final String accesskey, final Model model) {
	        if (session.getAttribute("userId") != null) {
	            final String dealerId = session.getAttribute("userId").toString();
	            final Optional<ParticipantRegistration> participantOptional = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	            final ParticipantRegistration participant = participantOptional.get();
	            model.addAttribute("language", (Object)this.listDmsServiceImpl.getByListName("LANGUAGE"));
	            final List<ListDms> listQuali = (List<ListDms>)this.listDmsServiceImpl.getByListName("EDUCATION");
	            final List<ListDms> ListDms = new ArrayList<ListDms>();
	            for (final ListDms l : listQuali) {
	                if (!l.getListCode().equals("HSC")) {
	                    if (l.getListCode().equals("SSC")) {
	                        continue;
	                    }
	                    ListDms.add(l);
	                }
	            }
	            model.addAttribute("qualification", (Object)ListDms);
	            model.addAttribute("title", (Object)this.listDmsServiceImpl.getByListName("TITLE_CD"));
	            model.addAttribute("ID", (Object)this.listDmsServiceImpl.getByListName("ID"));
	            final Map<String, String> map = new HashMap<String, String>();
	            final List<Object> cityList = (List<Object>)this.stateCityPinDmsService.getAllCity();
	            for (final Object city : cityList) {
	                final Object[] obj = (Object[])city;
	                map.put((String)obj[1], (String)obj[0]);
	            }
	            final Map<String, String> mapState = new HashMap<String, String>();
	            final List<Object> stateList = (List<Object>)this.stateCityPinDmsService.getAllState();
	            for (final Object state : stateList) {
	                final Object[] obj2 = (Object[])state;
	                mapState.put((String)obj2[1], (String)obj2[0]);
	            }
	            if(participant.getEmployeeCode() == null) {
	            	participant.setEmployeeCode(participant.getId().toString());
	            }
	            final Map<String, String> villageMap = new HashMap<String, String>();
	            final Map<String, String> TehsilMap = new HashMap<String, String>();
	            if (participantOptional.isPresent() && participantOptional.get().getTehsil() != null) {
	                final List<Object> tehshilList = (List<Object>)this.tService.findByTehsilCode(participantOptional.get().getTehsil());
	                for (final Object t : tehshilList) {
	                    final Object[] obj3 = (Object[])t;
	                    final String name = (String)obj3[0];
	                    final String code = (String)obj3[1];
	                    villageMap.put(code, name);
	                }
	                model.addAttribute("village", (Object)villageMap);
	            }
	            else {
	                model.addAttribute("village", (Object)villageMap);
	            }
	            if (participantOptional.isPresent() && participantOptional.get().getState() != null) {
	                final List<Object> tehshilList = (List<Object>)this.tService.findByStateCode(participantOptional.get().getState());
	                for (final Object t : tehshilList) {
	                    final Object[] obj3 = (Object[])t;
	                    final String name = (String)obj3[0];
	                    final String code = (String)obj3[1];
	                    TehsilMap.put(code, name);
	                }
	                model.addAttribute("tehsil", (Object)TehsilMap);
	            }
	            else {
	                model.addAttribute("tehsil", (Object)TehsilMap);
	            }
	            model.addAttribute("stateList", (Object)mapState);
	            model.addAttribute("city", (Object)map);
	            model.addAttribute("outletLsit", (Object)this.outletService.findByDealerId((long)participant.getDealerId()));
	            model.addAttribute("personal", (Object)participant);
	            return "personal-details";
	        }
	        return "redirect:login";
	    }
	    
	    @PostMapping({ "/savePersonalDetails" })
	    public String saveParticipantPersonalDetails(@ModelAttribute("participantRegistration") @Validated final ParticipantRegistration participantRegistration, final BindingResult result, @RequestParam final String accessKey, @RequestParam final String btnValue) {
	        final Optional<ParticipantRegistration> participantOptional = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        final ParticipantRegistration participant = participantOptional.get();
	        participant.setEmployeeCode(participantRegistration.getEmployeeCode());
	        participant.setTitle(participantRegistration.getTitle());
	        participant.setFirstName(participantRegistration.getFirstName());
	        participant.setMiddleName(participantRegistration.getMiddleName());
	        participant.setLastName(participantRegistration.getLastName());
	        participant.setAddress(participantRegistration.getAddress());
	        participant.setState(participantRegistration.getState());
	        participant.setCity(participantRegistration.getCity());
	        participant.setPin(participantRegistration.getPin());
	        participant.setTehsil(participantRegistration.getTehsil());
	        participant.setVillage(participantRegistration.getVillage());
	        participant.setIdProof(participantRegistration.getIdProof());
	        participant.setBirthDate(participantRegistration.getBirthDate());
	        participant.setMobile(participantRegistration.getMobile());
	        participant.setAlternateContactNumber(participantRegistration.getAlternateContactNumber());
	        participant.setEmail(participantRegistration.getEmail());
	        participant.setAdharNumber(participantRegistration.getAdharNumber());
	        participant.setLicenseNo(participantRegistration.getLicenseNo());
	        participant.setValidityOfLicence(participantRegistration.getValidityOfLicence());
	        participant.setHighestQualification(participantRegistration.getHighestQualification());
	        participant.setPrimaryLanguage(participantRegistration.getPrimaryLanguage());
	        participant.setSecondaryLanguage(participantRegistration.getSecondaryLanguage());
	        participant.setDL(participantRegistration.getDL());
	        participant.setOutletCode(participantRegistration.getOutletCode());
	        if (btnValue.equals("next")) {
	            participant.setPersonalFlag("1");
	        }
	        participant.setModifiedDate(new Date());
	        this.participantservice.saveData(participant);
	        if (btnValue.equals("next")) {
	            return "redirect:getempdetails?accesskey=" + accessKey;
	        }
	        if (btnValue.equals("save")) {
	            return "redirect:getPersonalDetails?accesskey=" + accessKey;
	        }
	        return "redirect:getPersonalDetails?accesskey=" + accessKey;
	    }
	    
	    @GetMapping({ "/getempdetails" })
	    public String getParticipantEmploymentDetails(final ParticipantRegistration participantRegistration, @RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (participant.isPresent() && (participant.get().getEmpProductivityRefId() == null || participant.get().getEmpProductivityRefId().equals(""))) {
	            final Optional<DataScience> d = (Optional<DataScience>)this.dataScienceService.findByAccesskey(accesskey);
	            if (d.isPresent() && d.get().getDataScienceReferenceId() != null) {
	                this.participantservice.updateProductivityRefID(d.get().getDataScienceReferenceId(), accesskey);
	                participant.get().setEmpProductivityRefId(d.get().getDataScienceReferenceId());
	            }
	        }
	        if (participant.get().getDepartmentCode()== null) {
	        	participant.get().setDepartmentCode("SAL");
	        }
	        if (participant.get().getKnowDriving() == null) {
	            participant.get().setKnowDriving(participant.get().getTwoWheeler());
	        }
	        if (participant.get().getOldMspin() != null && !participant.get().getOldMspin().endsWith("")) {
	            participant.get().setOldMSPINStatus("2");
	        }
	        model.addAttribute("emp", (Object)participant.get());
	        model.addAttribute("gender", (Object)this.listDmsServiceImpl.getByListName("GENDER"));
	        model.addAttribute("departmentCode", (Object)this.departmentDmsService.findallDepartmentDms());
	        final List<String> listCategary = (List<String>)this.designationService.getDesignationCategory(participant.get().getDesignation());
	        model.addAttribute("designation", (Object)this.designationServiceDms.getDesignationByDesignationCode((List)listCategary));
	        return "employment-details";
	    }
	    
	    @PostMapping({ "/getFinalDesignation" })
	    @ResponseBody
	    private String getDesignation(@RequestParam("profile") final String profile) {
	        System.out.print("profile..........." + profile);
	        String result = "";
	        final List<String> listCategary = (List<String>)this.designationService.getDesignationCategory(profile);
	        final List<DesignationDms> designationList = (List<DesignationDms>)this.designationServiceDms.getDesignationByDesignationCode((List)listCategary);
	        result = "<option value=''>Select</option>";
	        for (final DesignationDms d : designationList) {
	            result = String.valueOf(result) + "<option value=" + d.getDesignationCode() + ">" + d.getDesignationDesc() + "</option>";
	        }
	        return result;
	    }
	    
	    @PostMapping({ "/saveEmploymentDetails" })
	    public String saveParticipantEmploymentDetails(final ParticipantRegistration participantRegistration, @RequestParam final String accessKey, @RequestParam final String btnSave) {
	        final Optional<ParticipantRegistration> participantOptional = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        final ParticipantRegistration participant = participantOptional.get();
	        participant.setDivision(participantRegistration.getDivision());
	        participant.setDesignation(participantRegistration.getDivision());
	        participant.setDepartmentCode(participantRegistration.getDepartmentCode());
	        participant.setWorkedWithMSILBefore(participantRegistration.getWorkedWithMSILBefore());
	        participant.setOldMspin(participantRegistration.getOldMspin());
	        participant.setOldMSPINStatus(participantRegistration.getOldMSPINStatus());
	        participant.setInterviewDate(participantRegistration.getInterviewDate());
	        participant.setEmpSalary(participantRegistration.getEmpSalary());
	        participant.setEmpProductivityRefId(participantRegistration.getEmpProductivityRefId());
	        participant.setGender(participantRegistration.getGender());
	        participant.setPfNumber(participantRegistration.getPfNumber());
	        participant.setBankAccountNumber(participantRegistration.getBankAccountNumber());
	        participant.setEsiNumber(participantRegistration.getEsiNumber());
	        participant.setOwnTwoWheeler(participantRegistration.getOwnTwoWheeler());
	        participant.setKnowDriving(participantRegistration.getKnowDriving());
	        participant.setMdsCertified(participantRegistration.getMdsCertified());
	        participant.setFinalDesignation(participantRegistration.getFinalDesignation());
	        if (btnSave.equals("Next")) {
	            participant.setEmploymentFlag("1");
	        }
	        participant.setModifiedDate(new Date());
	        this.participantservice.saveData(participant);
	        if (btnSave.equals("Save")) {
	            return "redirect:getempdetails?accesskey=" + accessKey;
	        }
	        if (btnSave.equals("Next")) {
	            return "redirect:getgeneraldetails?accesskey=" + accessKey;
	        }
	        return "redirect:getempdetails?accesskey=" + accessKey;
	    }
	    
	    @GetMapping({ "/getgeneraldetails" })
	    public String getParticipantGeneralDetails(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        model.addAttribute("general", (Object)participant.get());
	        final List<String> martialStatus = new ArrayList<String>();
	        final List<String> blodgroup = new ArrayList<String>();
	        martialStatus.add("Married");
	        martialStatus.add("Single");
	        martialStatus.add("Divorced");
	        model.addAttribute("martialStatus", (Object)martialStatus);
	        model.addAttribute("blodgroup", (Object)this.listDmsServiceImpl.getByListName("BLOOD_GRP"));
	        return "general-details";
	    }
	    
	    @PostMapping({ "/saveGeneralDetails" })
	    public String saveParticipantGeneralDetails(@RequestParam("accesskey") final String accesskey, @RequestParam("martialStatus") final String martialStatus, 
	    		@RequestParam("anniversary_date") final String anniversary_date, @RequestParam("blodgroup") final String blodgroup, 
	    		@RequestParam("btnStatus") final String btnStatus) {
	     
	    	final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        participant.get().setAnniversaryDate(anniversary_date);
	        participant.get().setBloodGroup(blodgroup);
	        participant.get().setMartialStatus(martialStatus);
	        String url = "";
	        if (btnStatus.equals("N")) {
	        	 System.out.println("Tes.............1");
	            participant.get().setGeneralFlag("1");
	            url = "redirect:getWorkExperience?accesskey=" + accesskey+"&param=";
	        }
	        if (btnStatus.equals("S")) {
	        	 System.out.println("Tes.............2");
	            url = "redirect:getgeneraldetails?accesskey=" + accesskey;
	        }
	        participant.get().setModifiedDate(new Date());
	        this.participantservice.saveData((ParticipantRegistration)participant.get());
	        System.out.println("url............."+url);
	        return url;
	    }
	    
	    @GetMapping({ "/getWorkExperience" })
	    public String getParticipantWorkExperienceDetails(@RequestParam("accesskey") final String accesskey,@RequestParam("param") final String param,
	    		final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (participant.isPresent()) {
	            if (participant.get().getExperience() != null && participant.get().getExperience().equals("fresher")) {
	                model.addAttribute("workexperience", (Object)participant.get());
	                return "work-experience";
	            }
	            if (participant.get().getExperience() != null) {
	                participant.get().getExperience().equals("experience");
	            }
	        }

	        participant.get().getWorkExperience();
	        model.addAttribute("workexperience", (Object)participant.get());
	        return "work-experience";
	    }
	    
	    @PostMapping({ "/saveWorkExperience" })
	    public String saveParticipantWorkExperienceDetails(@RequestParam("accessKey") final String accessKey, @RequestParam("status") final String status, @RequestParam("btnStatus") final String btnStatus, final Model model) {
	        System.out.println("Freser.......................");
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        if (participant.isPresent()) {
	            if (status.equals("fresher")) {
	                participant.get().setFresher(status);
	                participant.get().setExperience("");
	            }
	            if (status.equals("experience")) {
	                participant.get().setFresher((String)null);
	                participant.get().setExperience("experience");
	            }
	            if (btnStatus.equals("Next")) {
	                participant.get().setWorkFlag("1");
	            }
	            participant.get().setModifiedDate(new Date());
	            this.participantservice.saveData((ParticipantRegistration)participant.get());
	        }
	        String url = "";
	        if (status.equals("fresher")) {
	            model.addAttribute("workexperience", (Object)participant.get());
	            url = "work-experience";
	        }
	        else {
	            url = "redirect:getWorkExperienceExp?accesskey=" + accessKey;
	        }
	        return url;
	    }
	    
	    @GetMapping({ "/getWorkExperienceExp" })
	    public String getParticipantWorkExperienceExpDetails(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (participant.isPresent()) {
	            final List<WorkExperience> participantWorkExp = (List<WorkExperience>)participant.get().getWorkExperience();
	            model.addAttribute("workexperienceExp", (Object)participant.get());
	            model.addAttribute("participantWorkExp", (Object)participantWorkExp);
	        }
	        else {
	            model.addAttribute("workexperienceExp", (Object)new ParticipantRegistration());
	            model.addAttribute("participantWorkExp", (Object)new ArrayList());
	        }
	        return "work-experience-exp";
	    }
	    
	    @PostMapping({ "/saveWorkExperienceExp" })
	    public String saveParticipantWorkExperienceExpDetails(@RequestParam final String accessKey, @RequestParam final String autoIndustryExperience, @RequestParam final String companyName, @RequestParam final String expInMths, @RequestParam final String previousDesignation, @RequestParam final String workArea) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        final ArrayList<WorkExperience> ListworkExp = new ArrayList<WorkExperience>();
	        final WorkExperience workExperience = new WorkExperience();
	        workExperience.setAutoIndustryExperience(autoIndustryExperience);
	        workExperience.setCompanyName(companyName);
	        workExperience.setPreviousDesignation(previousDesignation);
	        workExperience.setExpInMths(Integer.valueOf(Integer.parseInt(expInMths)));
	        workExperience.setWorkArea(workArea);
	        workExperience.setParticipantRegistration((ParticipantRegistration)participant.get());
	        ListworkExp.add(workExperience);
	        if (participant.isPresent()) {
	            participant.get().setWorkExperience((List)ListworkExp);
	            participant.get().setWorkFlag("1");
	            participant.get().setExperience("experience");
	            participant.get().setFresher((String)null);
	            participant.get().setModifiedDate(new Date());
	            this.participantservice.saveData((ParticipantRegistration)participant.get());
	        }
	        return "redirect:getWorkExperienceExp?accesskey=" + accessKey;
	    }
	    
	    @PostMapping({ "/deleteWorkExperience" })
	    @ResponseBody
	    public String deleteWorkExperienceExp(@RequestParam final String wid, @RequestParam("accesskey") final String accesskey) {
	        final Optional<WorkExperience> workExperience = (Optional<WorkExperience>)this.workExperienceService.getById(Long.valueOf(Long.parseLong(wid)));
	        if (workExperience.isPresent()) {
	            this.workExperienceService.delete((WorkExperience)workExperience.get());
	            this.participantservice.updateModiedDate(accesskey);
	        }
	        return "success";
	    }
	    
	    @PostMapping({ "/updateWorkExperience" })
	    @ResponseBody
	    public String updateWorkExperienceExp(@RequestParam("id") final String id, @RequestParam("workArea") final String workArea, @RequestParam("previousDesignation") final String previousDesignation, @RequestParam("expInMths") final String expInMths, @RequestParam("companyName") final String companyName, @RequestParam("autoIndustryExperience") final String autoIndustryExperience, @RequestParam("accesskey") final String accesskey) {
	        final Optional<WorkExperience> work = (Optional<WorkExperience>)this.workExperienceService.getById(Long.valueOf(Long.parseLong(id)));
	        if (work.isPresent()) {
	            work.get().setAutoIndustryExperience(autoIndustryExperience);
	            work.get().setCompanyName(companyName);
	            work.get().setExpInMths(Integer.valueOf(Integer.parseInt(expInMths)));
	            work.get().setPreviousDesignation(previousDesignation);
	            work.get().setWorkArea(workArea);
	            this.workExperienceService.save((WorkExperience)work.get());
	            this.participantservice.updateModiedDate(accesskey);
	        }
	        return "success";
	    }
	    
	    @GetMapping({ "/getfamilydetails" })
	    public String getParticipantFamilyDetails(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        model.addAttribute("partcipant", (Object)participant.get());
	        participant.get().getFamilyDetails();
	        model.addAttribute("relationship", (Object)this.listDmsServiceImpl.getByListName("ENQUIRY_RELATION"));
	        if (participant.get().getFamilyDetails().size() > 0) {
	            model.addAttribute("familyDetail", (Object)participant.get().getFamilyDetails());
	        }
	        else {
	            model.addAttribute("familyDetail", (Object)new ArrayList());
	        }
	        return "family-member-details";
	    }
	    
	    @GetMapping({ "/getOneFamilyDetails" })
	    public String editFamily(@RequestParam("fid") final Long fid, final Model model) {
	        final FamilyDetails familyDetails = this.participantservice.getOneFamilyDetailsById(fid);
	        model.addAttribute("familyDetailsOne", (Object)familyDetails);
	        return "family-member-details";
	    }
	    
	    @PostMapping({ "/savefamilydetails" })
	    public String saveParticipantFamilyDetails(final FamilyDetails familyDetails, @RequestParam final String accessKey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        familyDetails.setParticipantRegistration((ParticipantRegistration)participant.get());
	        final ArrayList<FamilyDetails> Listfamily = new ArrayList<FamilyDetails>();
	        Listfamily.add(familyDetails);
	        participant.get().setFamilyDetails((List)Listfamily);
	        participant.get().setFamilyFlag("1");
	        participant.get().setModifiedDate(new Date());
	        this.participantservice.saveData((ParticipantRegistration)participant.get());
	        return "redirect:getfamilydetails?accesskey=" + participant.get().getAccessKey();
	    }
	    
	    @GetMapping({ "/deleteOneFamilyDetails" })
	    public String deleteFamilyDetails(@RequestParam("fid") final Long fid) {
	        this.participantservice.deleteById(fid);
	        return "family-member-details";
	    }
	    
	    @PostMapping({ "/updateFamily" })
	    public String updateFamilyDetails(@RequestParam("accesskey") final String accesskey, @RequestParam("fid") final String fid, @RequestParam("member") final String member, @RequestParam("relatonShip") final String relatonShip) {
	        final Optional<FamilyDetails> familyDetails = (Optional<FamilyDetails>)this.familyDetailService.getByFid(Long.parseLong(fid));
	        if (familyDetails.isPresent()) {
	            familyDetails.get().setMemberName(member);
	            familyDetails.get().setRelationship(relatonShip);
	            this.familyDetailService.update((FamilyDetails)familyDetails.get());
	            this.participantservice.updateModiedDate(accesskey);
	        }
	        return "family-member-details";
	    }
	    
	    @GetMapping({ "/getEmergencyContact" })
	    public String getParticipantEmergencyContact(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        model.addAttribute("emergencyContact", (Object)participant.get());
	        final List<EmergencyContact> emergency = (List<EmergencyContact>)participant.get().getEmergencyContact();
	        model.addAttribute("emergency", (Object)emergency);
	        return "emergency-contact";
	    }
	    
	    @PostMapping({ "/saveEmergencyContact" })
	    public String saveParticipantEmergencyContact(final EmergencyContact emergencyContact, @RequestParam final String accessKey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        emergencyContact.setParticipantRegistration((ParticipantRegistration)participant.get());
	        final ArrayList<EmergencyContact> emergeContact = new ArrayList<EmergencyContact>();
	        emergeContact.add(emergencyContact);
	        participant.get().setEmergencyContact((List)emergeContact);
	        participant.get().setEmergencyFlag("1");
	        participant.get().setModifiedDate(new Date());
	        this.participantservice.saveData((ParticipantRegistration)participant.get());
	        return "redirect:getEmergencyContact?accesskey=" + participant.get().getAccessKey();
	    }
	    
	    @GetMapping({ "/getOneEmergencyContact" })
	    public String getEmergencyContactById(final Model model, @RequestParam final Long id) {
	        final EmergencyContact emergencyContact = this.participantservice.getEmergencyContactById(id);
	        model.addAttribute("emergencyContact", (Object)emergencyContact);
	        return "emergency-contact";
	    }
	    
	    @PostMapping({ "/deleteEmergency" })
	    public String deleteEmergencyContact(@RequestParam final String id, @RequestParam("accesskey") final String accesskey) {
	        this.emergencyContactService.deleteById(Long.valueOf(Long.parseLong(id)));
	        this.participantservice.updateModiedDate(accesskey);
	        return "emergency-contact";
	    }
	    
	    @PostMapping({ "/updateEmergency" })
	    public String updateParticipant(@RequestParam final String id, @RequestParam final String name, @RequestParam final String mobile, @RequestParam final String accessKey) {
	        final Optional<EmergencyContact> emergencyContact = (Optional<EmergencyContact>)this.emergencyContactService.getEmergencyContact(Long.valueOf(Long.parseLong(id)));
	        if (emergencyContact.isPresent()) {
	            emergencyContact.get().setCname(name);
	            emergencyContact.get().setContactNo(mobile);
	            this.emergencyContactService.update((EmergencyContact)emergencyContact.get());
	            this.participantservice.updateModiedDate(accessKey);
	        }
	        return "emergency-contact";
	    }
	    
	    @GetMapping({ "/getAssessmentScore" })
	    public String getParticipantAssessmentScore(@Validated final ParticipantRegistration participantRegistration, final BindingResult result, @RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        model.addAttribute("Assmntscore", (Object)participant.get());
	        final Optional<InterviewScore> interviewScore = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(accesskey);
	        if (interviewScore.isPresent()) {
	        	Double value = Double.parseDouble(interviewScore.get().getTotal_avt());
	        	
	            model.addAttribute("interviewScore", Math.round(value));
	        }
	        return "assessment-scores";
	    }
	    
	    @PostMapping({ "/saveAssessmentScore" })
	    @ResponseBody
	    public String saveParticipantAssessmentScore(final ParticipantRegistration participantRegistration, @RequestParam final String accessKey) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accessKey);
	        this.participantservice.saveData((ParticipantRegistration)participant.get());
	        return "saved AssessmentScore success";
	    }
	    
	    @GetMapping({ "/uploadDocoment" })
	    public String uploadDocument(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        model.addAttribute("participant", (Object)participant.get());
	        return "upload-documents";
	    }
	    
	    @PostMapping({ "/getOldMSPIN" })
	    @ResponseBody
	    public String getOldMSPIN(@RequestParam("oldMSPIN") final String oldMSPIN, @RequestParam("accesskey") final String accesskey) {
	        String dealerName = "";
	        String dealerCode = "";
	        String name = "";
	        String result = "";
	        final List<ParticipantRegistration> participant = (List<ParticipantRegistration>)this.participantservice.getOldMSPIN(oldMSPIN);
	        if (participant.size() > 0) {
	            name = participant.get(0).getFirstName();
	            if (participant.get(0).getMiddleName() != null) {
	                name = String.valueOf(name) + " " + participant.get(0).getMiddleName();
	            }
	            name = String.valueOf(name) + " " + participant.get(0).getLastName();
	            final List<Outlet> ot = (List<Outlet>)this.outletService.findByDealerId((long)participant.get(0).getDealerId());
	            if (ot.size() > 0) {
	                dealerName = ot.get(0).getOutletName();
	                dealerCode = ot.get(0).getOutletCode();
	            }
	            if (!accesskey.equals(participant.get(0).getAccessKey())) {
	                if (!dealerName.equals("") && !dealerCode.equals("") && !name.equals("")) {
	                    result = "This MSPIN is already mapped with " + name + " working at " + dealerName + " " + dealerCode;
	                }
	                else {
	                    result = "";
	                }
	            }
	            else {
	                result = "2";
	            }
	        }
	        return result;
	    }
	    
	    @Async
	    @GetMapping({ "/feedback" })
	    @ResponseBody
	    public String saveFeedback(@RequestParam("accesskey") final String accesskey, @RequestParam("feedback") final String feedback, @RequestParam("fsdmRejectionType") final String fsdmRejectionType, @RequestParam("fsdmRejectionReason") final String fsdmRejectionReason, @RequestParam("fsdmRejectionComment") final String fsdmRejectionComment) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (participant.isPresent()) {
	            participant.get().setFsdmFeedback(feedback);
	            participant.get().setFsdmApprovalStatus("1");
	            participant.get().setDocuments_status("save");
	            participant.get().setModifiedDate(new Date());
	            participant.get().setFsdmRejectionType(fsdmRejectionType);
	            participant.get().setFsdmRejectionReason(fsdmRejectionReason);
	            participant.get().setFsdmRejectionComment(fsdmRejectionComment);
	            System.out.println("mspin...1."+ participant.get().getMspin());
	            ParticipantRegistration p = participantservice.saveData(participant.get());
	            System.out.println("mspin...2."+ p.getMspin());
	            sendEmailThatDocsRejected(participant.get());
	            try {
	            this.dmsController.approverFSDM(participant.get().getAccessKey(),"M",dmsURL);
	            }catch(Exception e) {
	            	System.out.println("Error...."+e);
	            }
	            
	        }
	        return "success";
	    }
	    
	    @GetMapping({ "/approvel" })
	    @ResponseBody
	    public String saveApprovel(@RequestParam("accesskey") final String accesskey) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        if (participant.isPresent()) {
	            participant.get().setFsdmApprovalStatus("2");
	            participant.get().setFsdmApprovalDate(new Date());
	            if(participant.get().getOldMspin() != null && participant.get().getOldMspin().length()>0 ) {
	            	participant.get().setMspin(participant.get().getOldMspin());
	            }
	            /* if ((participant.get().getFinalDesignation() != null && !participant.get().getFinalDesignation().equals("STR")) && (participant.get().getMspin() == null || participant.get().getMspin().equals(""))) {
	                participant.get().setMspin(participant.get().getOldMspin());
	                String smsMsg1 = DataProccessor.getSMS("recruited");
	                String smsMsg = DataProccessor.getSMS("mspinGenerated");
	   			 String name="";
	   			 name += participant.get().getFirstName();
	   			 if(participant.get().getMiddleName() != null && !participant.get().equals("")) {
	   				 name +=  " "+ participant.get().getFirstName(); 
	   			 }
	   			 name +=  " "+ participant.get().getLastName(); 
	   			 smsMsg = smsMsg.replace("${mspin}", participant.get().getMspin());
	   			 smsMsg = smsMsg.replace("${name}", name);
	   			 smsMsg = smsMsg.replace("${accesskey}", participant.get().getAccessKey());
	   			  final Dealer dealer = this.dealerService.getById((long) participant.get().getDealerId()).get();
	   			    if(dealer.getMobile() != null && !dealer.getMobile().equals("")) {
	   		          SmsUtility.sendSmsHandler(dealer.getMobile() , smsMsg1,"MSILOT" );
	   			    }
	            }*/
	            this.participantservice.saveData((ParticipantRegistration)participant.get());
	            
	            if ((participant.get().getFinalDesignation() != null && !participant.get().getFinalDesignation().equals("STR")) && (participant.get().getMspin() == null || participant.get().getMspin().equals(""))) {
	               this.dmsController.generateMSPIN(participant.get().getAccessKey());
	            }
	            final Optional<FSDMNotification> noti = (Optional<FSDMNotification>)this.fsdmMNotificationService.getAccesskey(accesskey);
	            if (noti.isPresent()) {
	                this.fsdmMNotificationService.dellete((int)noti.get().getId());
	            }
	            participant.get().setModifiedDate(new Date());
	          
	            this.sendEmailForDocsApproval(participant.get());
	            try {
	            	  this.dmsController.approverFSDM(participant.get().getAccessKey(),"M",dmsURL);
	            }catch(Exception e) {
	            	System.out.println("Error...."+e);
	            }
	            
	           
	        }
	        return "success";
	    }
	    
	    private String sendEmailForDocsApproval(final ParticipantRegistration participant) {
	        final String subjectLine = "Application Documents Approved";
	        String mailBody = DataProccessor.readFileFromResource("fsdmApprovedDocs");
	        mailBody = mailBody.replace("${candidateName}", String.valueOf(String.valueOf(participant.getFirstName())) + " " + participant.getMiddleName() + " " + participant.getLastName());
	        final Dealer dealer = this.dealerService.getById((long)participant.getDealerId()).get();
	        mailBody = mailBody.replace("${dealerName}", dealer.getName());
	        mailBody = mailBody.replace("${link}", "http://staging.irecruit.org.in/irecruit/candidateLogin");
	        mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
	        final SendPayload sendP = new SendPayload();
	        sendP.setTo(dealer.getEmail());
	        sendP.setSubjectLine("Application Documents Approved");
	        sendP.setMsg(mailBody);
	        sendP.setCc("");
	        sendP.setBcc("");
	        sendP.setFrom("Armezo Solutions");
	        try {
	         //   EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(), sendP.getMsg(), "smtp");
	        } catch (Exception e) {
	            System.out.println("...........Error in send mail.........");
	        }
	        
	        try {
	        	if(dealer.getMobile() != null && !dealer.getMobile().equals("")) {
			     //send sms for shortlisted 
				 String smsMsg = DataProccessor.getSMS("fsdmApproved");
				 String name="";
				 name += participant.getFirstName();
				 if(participant.getMiddleName() != null && !participant.getMiddleName().equals("")) {
					 name +=  " "+ participant.getFirstName(); 
				 }
				 name +=  " "+ participant.getLastName(); 
				 smsMsg = smsMsg.replace("${name}", name);
				 smsMsg = smsMsg.replace("${accesskey}", participant.getAccessKey());
			     SmsUtility.sendSmsHandler(dealer.getMobile(), smsMsg,"MSILOT" );
			     
			     String smsMsg1 = DataProccessor.getSMS("recruited");
			     smsMsg1 = smsMsg1.replace("${name}", name);
				 smsMsg1 = smsMsg1.replace("${accesskey}", participant.getAccessKey());
			     String a =  SmsUtility.sendSmsHandler(dealer.getMobile(), smsMsg1,"MSILOT" );
			     System.out.println("re......."+a);
	        }
				}catch(Exception e) {
					System.out.println("Error....."+e);
		    }
	        return "success";
	    }
	    
	    private String sendEmailThatDocsRejected(final ParticipantRegistration participant) {
	        final String subjectLine = "iRecruit- Candidate Application Rejected";
	        String mailBody = "";
	        mailBody=	DataProccessor.readFileFromResource("fsdmRejectedDocs");
	        if((participant.getFinalDesignationStatus() != null && participant.getFinalDesignationStatus().equals("1"))
	        		&& (participant.getMspin() != null && !participant.getMspin().equals(""))) { 
	        			  mailBody=	DataProccessor.readFileFromResource("fsdmSTRRejectedDocs");
	        }
	        mailBody = mailBody.replace("${candidateName}", String.valueOf(String.valueOf(participant.getFirstName())) + " " + participant.getMiddleName() + " " + participant.getLastName());
	        final Dealer dealer = this.dealerService.getById((long)participant.getDealerId()).get();
	        mailBody = mailBody.replace("${dealerName}", dealer.getName());
	        mailBody = mailBody.replace("${link}", "http://staging.irecruit.org.in/irecruit/candidateLogin");
	        if((participant.getFinalDesignationStatus() != null && participant.getFinalDesignationStatus().equals("1"))
	        		&& (participant.getMspin() != null && !participant.getMspin().equals(""))) {
	        	 mailBody = mailBody.replace("${accesskey}", participant.getMspin());
	        }else {
	        mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
	        }
	        String fsdmReason = "";
	        if (participant.getFsdmFeedback() != null && !participant.getFsdmFeedback().equals("")) {
	            fsdmReason = "<br>Reason:" + participant.getFsdmFeedback();
	        }
	        if (participant.getFsdmRejectionType() != null && !participant.getFsdmRejectionType().equals("")) {
	            fsdmReason = String.valueOf(fsdmReason) + "<br>Id Type: " + participant.getFsdmRejectionType();
	        }
	        if (participant.getFsdmRejectionReason() != null && !participant.getFsdmRejectionReason().equals("")) {
	            fsdmReason = String.valueOf(fsdmReason) + "<br>Details: " + participant.getFsdmRejectionReason();
	        }
	        if (participant.getFsdmRejectionComment() != null && !participant.getFsdmRejectionComment().equals("")) {
	            fsdmReason = String.valueOf(fsdmReason) + "<br>Comments: " + participant.getFsdmRejectionComment();
	        }
	        mailBody = mailBody.replace("${fsdmReason}", fsdmReason);
	        final SendPayload sendP = new SendPayload();
	        sendP.setTo(dealer.getEmail());
	        sendP.setSubjectLine("iRecruit- Candidate Application Rejected");
	        sendP.setMsg(mailBody);
	        sendP.setCc("");
	        sendP.setBcc("");
	        sendP.setFrom("Armezo Solutions");
	        try {
	            EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(), sendP.getMsg(), "smtp");
	        }
	        catch (Exception e) {
	            System.out.println("...........Error in send mail.........");
	        }
	        System.out.println("Email Sent for Docs Rejected");
	        try {
			    if(dealer.getMobile() != null && !dealer.getMobile().equals("")) {
				 String smsMsg = DataProccessor.getSMS("rejected");
				 String name="";
				 name += participant.getFirstName();
				 if(participant.getMiddleName() != null && !participant.getMiddleName().equals("")) {
					 name +=  " "+ participant.getFirstName(); 
				 }
				 name +=  " "+ participant.getLastName(); 
				 smsMsg = smsMsg.replace("${name}", name);
				 smsMsg = smsMsg.replace("${accesskey}", participant.getAccessKey());
			     SmsUtility.sendSmsHandler(dealer.getMobile(), smsMsg,"MSILOT" );
			     System.out.println("msg.......1");
			    }
				}catch(Exception e) {
					System.out.println("Error....."+e);
		    }
	        
	        return "success";
	    }
	    
	    @GetMapping({ "/finalSubmit" })
	    public String finalDocumentSubmit(@RequestParam("accesskey") final String accesskey, final Model model) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantservice.findByAccesskey(accesskey);
	        final List<String> feedbackList = new ArrayList<String>();
	        feedbackList.add("Documents Incomplete");
	        feedbackList.add("Information Incomplete");
	        model.addAttribute("participant", (Object)participant.get());
	        model.addAttribute("feedbackList", (Object)feedbackList);
	        return "finalSubmit";
	    }

}
