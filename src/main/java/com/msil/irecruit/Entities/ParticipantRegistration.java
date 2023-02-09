package com.msil.irecruit.Entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Participant_Registration_MSIL")
public class ParticipantRegistration {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id")
	    private Long Id;
	    @Column(name =  "access_key")
	    private String  accessKey;
	    @Column(name =  "Employee_Code")
	    private String  employeeCode;
	    @Column(name = "Worked_With_MSIL_Before")
	    private String workedWithMSILBefore;
	    @Column(name = "Old_MSPIN")
	    private String oldMspin;
	    @Column(name = "MSPIN")
	    private String mspin;
	    @Column(name = "Status")
	    private String status;
	    @Column(name = "Title")
	    private String title;
	    @Column(name = "First_Name")
	    private String firstName;
	    @Column(name = "Middle_Name")
	    private String middleName;
	    @Column(name = "Last_Name")
	    private String lastName;
	    @Column(name = "Address")
	    private String address;
	    @Column(name = "city")
	    private String city;
	    @Column(name = "state")
	    private String state;
	    @Column(name = "Pin")
	    private Integer pin;
	    @Column(name = "Id_Proof")
	    private String idProof;
	    @Column(name = "Birth_Date")
	    private String birthDate;
	    @Column(name = "Mobile")
	    private String mobile;	 
	    @Column(name = "Email")
	    private String email;
	    @Column(name = "License_No")
	    private String licenseNo;
	    @Column(name = "Validity_Of_Licence")
	    private String validityOfLicence;
	    @Column(name = "Highest_Qualification")
	    private String highestQualification;
	    @Column(name = "adhar_number")
	    private Long adharNumber;
	    @Column(name = "two_Wheeler")
	    private String twoWheeler;
	    @Column(name = "four_Wheeler")
	    private String fourWheeler;
	    @Column(name = "own_two_Wheeler")
	    private String ownTwoWheeler;
	    @Column(name = "own_four_Wheeler")
	    private String ownFourWheeler;
	    @Column(name = "Primary_Language")
	    private String primaryLanguage;
	    @Column(name = "Secondary_Language")
	    private String secondaryLanguage;
	    @Column(name = "Division")
	    private String division;
	    @Column(name = "Designation")
	    private String designation;
	    @Column(name = "final_designation")
	    private String finalDesignation;
	    @Column(name = "Department_Code")
	    private String departmentCode;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "Interview_Date")
	    private Date interviewDate;
	    @Column(name = "joining_Date")
	    private String joiningDate;
	    @Column(name = "Emp_Salary")
	    private String empSalary;
	    @Column(name = "Emp_Productivity_Ref_Id")
	    private String empProductivityRefId;
	    @Column(name = "Gender")
	    private String gender;
	    @Column(name = "Pf_Number")
	    private String pfNumber;
	    @Column(name = "Bank_Account_Number")
	    private String bankAccountNumber;
	    @Column(name = "ESI_Number")
	    private String esiNumber;
	    @Column(name = "Tehsil")
	    private String tehsil;
	    @Column(name = "Village")
	    private String village;
	    @Column(name = "Ownership_Of_Twowheeler")
	    private String ownershipOfTwoWheeler;
	    @Column(name = "Knows_Driving")
	    private String knowDriving;
	    @Column(name = "MDS_Certified")
	    private String mdsCertified;
	    @Column(name = "Aptitude_Score")
	    private Integer aptitudeScore;
	    @Column(name = "Attitude_Score")
	    private Integer attitudeScore;
	    @Column(name = "InterView_Score")
	    private Integer interviewScore;
	    @Column(name = "Psychometric_Score")
	    private Integer psychometricScore;
	    @Column(name = "Assessment_Date")
	    private String assessmentDate;
	    @Column(name = "Name")
	    private String cname;
	    @Column(name = "contact_no")
	    private String contactNo;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "Anniversary_Date")
	    private String anniversaryDate;
	    @Column(name = "Martial_Status")
	    private String martialStatus;
	    @Column(name = "Married")
	    private String married;
	    @Column(name = "Single")
	    private String single;
	    @Column(name = "Divorce")
	    private String divorce;
	    @Column(name = "Blood_Group")
	    private String bloodGroup;
	    @Column(name = "Fresher")
	    private String fresher;
	    @Column(name = "experience")
	    private String experience;
	    @Column(name = "Sr_No")
	    private Integer srNo;
	    @Column(name = "total")
	    private Integer total;
	    @Column(name = "Auto_Industry_Experience")
	    private Integer autoIndustryExperience;
	    @Column(name = "Non_Auto_Industry_Experience")
	    private Integer nonAutoIndustryExperience;
	    @Column(name = "source")
	    private String source;
	    @Column(name = "Company_Name")
	    private String companyName;
	    @Column(name = "Exp_In_Mths")
	    private Integer expInMths;
	    @Column(name = "Previous_Designation")
	    private String previousDesignation;
	    @Column(name = "Work_Area")
	    private String workArea;
	    @Column(name = "photograph")
	    private String photograph;
	    @Column(name = "signature")
	    private String signature;
	    @Column(name = "identitityProof")
	    private String identitityProof;    
	    @Column(name = "identitity_proof_name")
	    private String identitityProofName;    
	    @Column(name = "addressProof")
	    private String addressProof;
	    @Column(name = "address_proofName")
	    private String addressProofName;
	    @Column(name = "qualification")
	    private String qualification;
	    @Column(name = "qualification2")
	    private String qualification2;
	    @Column(name = "qualification3")
	    private String qualification3;
	    @Column(name = "resignationLetter")
	    private String resignationLetter;
	    @Column(name = "experienceletter")
	    private String experienceletter;
	    @Column(name = "salaryslip")
	    private String salarySlip;
	    @Column(name = "documents")
	    private String documents;
	    @Column(name = "resume")
	    private String resume;
	    @Column(name = "adhar")
	    private String adhar;
	    @Column(name = "dealerId")
	    private Long dealerId;
	    @Column(name = "Outlet_Code")
	    private String outletCode;
	    @Column(name = "fsdm_feedback")
	    private String fsdmFeedback;
	    @Column(name = "test_status")
	    private String testStatus;
	    @DateTimeFormat(pattern = "dd-MM-yyyy")
	    @Column(name = "registration_date")
	    private Date    registration_date;
	    @DateTimeFormat(pattern = "dd-MM-yyyy")
	    @Column(name = "test_completion_date")
	    private Date testCompletionDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "send_mail_date")
	    private Date sendMailDate;
	    @Column(name = "other")
	    private String other;
	    @Column(name = "regStatus")
	    private String regStatus;
	    @Column(name = "prarambh_status")
	    private String prarambhStatus;
	    @Column(name = "fsdm_approval_status")
	    private String fsdmApprovalStatus;
	    @DateTimeFormat(pattern = "dd-MM-yyyy")
	    @Column(name = "fsdm_approval_date")
	    private Date    fsdmApprovalDate;
	    @Column(name = "interview_time")
	    private String interviewTime;
	    @Column(name = "testScore")
	    private String testScore;		
		@Column(name = "totalMark")
		private String totalMark;		
		@Column(name = "percentage_score")
		private String percentageScore;		
	    @Column(name = "passFailStatus")
	    private int passFailStatus;
	    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participantRegistration", cascade = { CascadeType.ALL })
	    private List<FamilyDetails> familyDetails;
	    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participantRegistration", cascade = { CascadeType.ALL })
	    private List<EmergencyContact> emergencyContact;
	    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participantRegistration", cascade = { CascadeType.ALL })
	    private List<WorkExperience> workExperience;	    
	    @Column(name = "documents_status")
		private String documents_status;
	    @Column(name = "Alternate_Contact_Number")
	    private String alternateContactNumber;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "reactivation_date")
	    private Date reactivationDate;	    
	    @Column(name = "personal_flag")
		private String personalFlag;
	    @Column(name = "employment_flag")
		private String employmentFlag;
	    @Column(name = "general_flag")
		private String generalFlag;
	    @Column(name = "work_flag")
		private String workFlag;
	    @Column(name = "family_flag")
		private String familyFlag;
	    @Column(name = "emergency_flag")
		private String emergencyFlag;	    
	    @Column(name = "DL")
		private String DL;	    
	    @Column(name = "finalDesignation_status")
		private String finalDesignationStatus;	    
	    @Column(name = "re_atamp_status")
		private String reAtampStatus;
	    @Column(name = "re_atamp_count")
	  	private Integer reAtampCount;
	    @Column(name = "msil_exp")
		private String msilExp;
	    @Column(name = "age")
	    private String age;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "modified_date")
	    private Date modifiedDate;
	    
	    @Column(name = "fsdm_rejection_type")
	    private String fsdmRejectionType;
	    @Column(name = "fsdm_rejection_reason")
	    private String fsdmRejectionReason;
	    @Column(name = "fsdm_rejection_comment")
	    private String fsdmRejectionComment;
	    @Column(name = "old_mspin_status")
	    private String oldMSPINStatus;
	    
	    @Column(name = "question_report_status")
	    private Integer questionReportStatus;
	    @Column(name = "interview_address")
	    private String interviewAddress;
	    
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getWorkedWithMSILBefore() {
		return workedWithMSILBefore;
	}
	public void setWorkedWithMSILBefore(String workedWithMSILBefore) {
		this.workedWithMSILBefore = workedWithMSILBefore;
	}
	public String getOldMspin() {
		return oldMspin;
	}
	public void setOldMspin(String oldMspin) {
		this.oldMspin = oldMspin;
	}
	public String getMspin() {
		return mspin;
	}
	public void setMspin(String mspin) {
		this.mspin = mspin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public String getIdProof() {
		return idProof;
	}
	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getValidityOfLicence() {
		return validityOfLicence;
	}
	public void setValidityOfLicence(String validityOfLicence) {
		this.validityOfLicence = validityOfLicence;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public Long getAdharNumber() {
		return adharNumber;
	}
	public void setAdharNumber(Long adharNumber) {
		this.adharNumber = adharNumber;
	}
	public String getTwoWheeler() {
		return twoWheeler;
	}
	public void setTwoWheeler(String twoWheeler) {
		this.twoWheeler = twoWheeler;
	}
	public String getFourWheeler() {
		return fourWheeler;
	}
	public void setFourWheeler(String fourWheeler) {
		this.fourWheeler = fourWheeler;
	}
	
	public String getOwnTwoWheeler() {
		return ownTwoWheeler;
	}
	public void setOwnTwoWheeler(String ownTwoWheeler) {
		this.ownTwoWheeler = ownTwoWheeler;
	}
	public String getOwnFourWheeler() {
		return ownFourWheeler;
	}
	public void setOwnFourWheeler(String ownFourWheeler) {
		this.ownFourWheeler = ownFourWheeler;
	}
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getFinalDesignation() {
		return finalDesignation;
	}
	public void setFinalDesignation(String finalDesignation) {
		this.finalDesignation = finalDesignation;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public Date getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(String empSalary) {
		this.empSalary = empSalary;
	}
	public String getEmpProductivityRefId() {
		return empProductivityRefId;
	}
	public void setEmpProductivityRefId(String empProductivityRefId) {
		this.empProductivityRefId = empProductivityRefId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPfNumber() {
		return pfNumber;
	}
	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getEsiNumber() {
		return esiNumber;
	}
	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}
	public String getTehsil() {
		return tehsil;
	}
	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getOwnershipOfTwoWheeler() {
		return ownershipOfTwoWheeler;
	}
	public void setOwnershipOfTwoWheeler(String ownershipOfTwoWheeler) {
		this.ownershipOfTwoWheeler = ownershipOfTwoWheeler;
	}
	public String getKnowDriving() {
		return knowDriving;
	}
	public void setKnowDriving(String knowDriving) {
		this.knowDriving = knowDriving;
	}
	public String getMdsCertified() {
		return mdsCertified;
	}
	public void setMdsCertified(String mdsCertified) {
		this.mdsCertified = mdsCertified;
	}
	public Integer getAptitudeScore() {
		return aptitudeScore;
	}
	public void setAptitudeScore(Integer aptitudeScore) {
		this.aptitudeScore = aptitudeScore;
	}
	public Integer getAttitudeScore() {
		return attitudeScore;
	}
	public void setAttitudeScore(Integer attitudeScore) {
		this.attitudeScore = attitudeScore;
	}
	public Integer getInterviewScore() {
		return interviewScore;
	}
	public void setInterviewScore(Integer interviewScore) {
		this.interviewScore = interviewScore;
	}
	public Integer getPsychometricScore() {
		return psychometricScore;
	}
	public void setPsychometricScore(Integer psychometricScore) {
		this.psychometricScore = psychometricScore;
	}
	public String getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getAnniversaryDate() {
		return anniversaryDate;
	}
	public void setAnniversaryDate(String anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}
	public String getMartialStatus() {
		return martialStatus;
	}
	public void setMartialStatus(String martialStatus) {
		this.martialStatus = martialStatus;
	}
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public String getDivorce() {
		return divorce;
	}
	public void setDivorce(String divorce) {
		this.divorce = divorce;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getFresher() {
		return fresher;
	}
	public void setFresher(String fresher) {
		this.fresher = fresher;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public Integer getSrNo() {
		return srNo;
	}
	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getAutoIndustryExperience() {
		return autoIndustryExperience;
	}
	public void setAutoIndustryExperience(Integer autoIndustryExperience) {
		this.autoIndustryExperience = autoIndustryExperience;
	}
	public Integer getNonAutoIndustryExperience() {
		return nonAutoIndustryExperience;
	}
	public void setNonAutoIndustryExperience(Integer nonAutoIndustryExperience) {
		this.nonAutoIndustryExperience = nonAutoIndustryExperience;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getExpInMths() {
		return expInMths;
	}
	public void setExpInMths(Integer expInMths) {
		this.expInMths = expInMths;
	}
	public String getPreviousDesignation() {
		return previousDesignation;
	}
	public void setPreviousDesignation(String previousDesignation) {
		this.previousDesignation = previousDesignation;
	}
	public String getWorkArea() {
		return workArea;
	}
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	public String getPhotograph() {
		return photograph;
	}
	public void setPhotograph(String photograph) {
		this.photograph = photograph;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getIdentitityProof() {
		return identitityProof;
	}
	public void setIdentitityProof(String identitityProof) {
		this.identitityProof = identitityProof;
	}
	public String getIdentitityProofName() {
		return identitityProofName;
	}
	public void setIdentitityProofName(String identitityProofName) {
		this.identitityProofName = identitityProofName;
	}
	public String getAddressProof() {
		return addressProof;
	}
	public void setAddressProof(String addressProof) {
		this.addressProof = addressProof;
	}
	public String getAddressProofName() {
		return addressProofName;
	}
	public void setAddressProofName(String addressProofName) {
		this.addressProofName = addressProofName;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getQualification2() {
		return qualification2;
	}
	public void setQualification2(String qualification2) {
		this.qualification2 = qualification2;
	}
	public String getQualification3() {
		return qualification3;
	}
	public void setQualification3(String qualification3) {
		this.qualification3 = qualification3;
	}
	
	public String getResignationLetter() {
		return resignationLetter;
	}
	public void setResignationLetter(String resignationLetter) {
		this.resignationLetter = resignationLetter;
	}
	public String getExperienceletter() {
		return experienceletter;
	}
	public void setExperienceletter(String experienceletter) {
		this.experienceletter = experienceletter;
	}
	public String getSalarySlip() {
		return salarySlip;
	}
	public void setSalarySlip(String salarySlip) {
		this.salarySlip = salarySlip;
	}
	public String getDocuments() {
		return documents;
	}
	public void setDocuments(String documents) {
		this.documents = documents;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getAdhar() {
		return adhar;
	}
	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	
	
	public String getFsdmFeedback() {
		return fsdmFeedback;
	}
	public void setFsdmFeedback(String fsdmFeedback) {
		this.fsdmFeedback = fsdmFeedback;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	
	
	public Date getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
	public Date getTestCompletionDate() {
		return testCompletionDate;
	}
	public void setTestCompletionDate(Date testCompletionDate) {
		this.testCompletionDate = testCompletionDate;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getRegStatus() {
		return regStatus;
	}
	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}
	public String getPrarambhStatus() {
		return prarambhStatus;
	}
	public void setPrarambhStatus(String prarambhStatus) {
		this.prarambhStatus = prarambhStatus;
	}
	public String getFsdmApprovalStatus() {
		return fsdmApprovalStatus;
	}
	
	
	public Date getFsdmApprovalDate() {
		return fsdmApprovalDate;
	}
	public void setFsdmApprovalDate(Date fsdmApprovalDate) {
		this.fsdmApprovalDate = fsdmApprovalDate;
	}
	public void setFsdmApprovalStatus(String fsdmApprovalStatus) {
		this.fsdmApprovalStatus = fsdmApprovalStatus;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}
	public String getTestScore() {
		return testScore;
	}
	public void setTestScore(String testScore) {
		this.testScore = testScore;
	}
	public String getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(String totalMark) {
		this.totalMark = totalMark;
	}
	public String getPercentageScore() {
		return percentageScore;
	}
	public void setPercentageScore(String percentageScore) {
		this.percentageScore = percentageScore;
	}
	public int getPassFailStatus() {
		return passFailStatus;
	}
	public void setPassFailStatus(int passFailStatus) {
		this.passFailStatus = passFailStatus;
	}
	public List<FamilyDetails> getFamilyDetails() {
		return familyDetails;
	}
	public void setFamilyDetails(List<FamilyDetails> familyDetails) {
		this.familyDetails = familyDetails;
	}
	public List<EmergencyContact> getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(List<EmergencyContact> emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public List<WorkExperience> getWorkExperience() {
		return workExperience;
	}
	public void setWorkExperience(List<WorkExperience> workExperience) {
		this.workExperience = workExperience;
	}
	public String getDocuments_status() {
		return documents_status;
	}
	public void setDocuments_status(String documents_status) {
		this.documents_status = documents_status;
	}
	public String getAlternateContactNumber() {
		return alternateContactNumber;
	}
	public void setAlternateContactNumber(String alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}
	public Date getSendMailDate() {
		return sendMailDate;
	}
	public void setSendMailDate(Date sendMailDate) {
		this.sendMailDate = sendMailDate;
	}
	public Date getReactivationDate() {
		return reactivationDate;
	}
	public void setReactivationDate(Date reactivationDate) {
		this.reactivationDate = reactivationDate;
	}

	public String getPersonalFlag() {
		return personalFlag;
	}
	public void setPersonalFlag(String personalFlag) {
		this.personalFlag = personalFlag;
	}
	public String getEmploymentFlag() {
		return employmentFlag;
	}
	public void setEmploymentFlag(String employmentFlag) {
		this.employmentFlag = employmentFlag;
	}
	public String getGeneralFlag() {
		return generalFlag;
	}
	public void setGeneralFlag(String generalFlag) {
		this.generalFlag = generalFlag;
	}
	public String getWorkFlag() {
		return workFlag;
	}
	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}
	public String getFamilyFlag() {
		return familyFlag;
	}
	public void setFamilyFlag(String familyFlag) {
		this.familyFlag = familyFlag;
	}
	public String getEmergencyFlag() {
		return emergencyFlag;
	}
	public void setEmergencyFlag(String emergencyFlag) {
		this.emergencyFlag = emergencyFlag;
	}
	public String getDL() {
		return DL;
	}
	public void setDL(String dL) {
		DL = dL;
	}
	public String getFinalDesignationStatus() {
		return finalDesignationStatus;
	}
	public void setFinalDesignationStatus(String finalDesignationStatus) {
		this.finalDesignationStatus = finalDesignationStatus;
	}
	public String getReAtampStatus() {
		return reAtampStatus;
	}
	public void setReAtampStatus(String reAtampStatus) {
		this.reAtampStatus = reAtampStatus;
	}
	
	public Integer getReAtampCount() {
		return reAtampCount;
	}
	public void setReAtampCount(Integer reAtampCount) {
		this.reAtampCount = reAtampCount;
	}
	public String getMsilExp() {
		return msilExp;
	}
	public void setMsilExp(String msilExp) {
		this.msilExp = msilExp;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getFsdmRejectionType() {
		return fsdmRejectionType;
	}
	public void setFsdmRejectionType(String fsdmRejectionType) {
		this.fsdmRejectionType = fsdmRejectionType;
	}
	public String getFsdmRejectionReason() {
		return fsdmRejectionReason;
	}
	public void setFsdmRejectionReason(String fsdmRejectionReason) {
		this.fsdmRejectionReason = fsdmRejectionReason;
	}
	public String getFsdmRejectionComment() {
		return fsdmRejectionComment;
	}
	public void setFsdmRejectionComment(String fsdmRejectionComment) {
		this.fsdmRejectionComment = fsdmRejectionComment;
	}
	public String getOldMSPINStatus() {
		return oldMSPINStatus;
	}
	public void setOldMSPINStatus(String oldMSPINStatus) {
		this.oldMSPINStatus = oldMSPINStatus;
	}
	public Integer getQuestionReportStatus() {
		return questionReportStatus;
	}
	
	public void setQuestionReportStatus(Integer questionReportStatus) {
		this.questionReportStatus = questionReportStatus;
	}
	public String getInterviewAddress() {
		return interviewAddress;
	}
	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}
	
	
	
	
	
	
}
