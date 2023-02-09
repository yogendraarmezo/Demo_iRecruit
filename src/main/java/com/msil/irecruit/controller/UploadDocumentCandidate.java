package com.msil.irecruit.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDMNotification;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMNotificationService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.controller.DmsController;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class UploadDocumentCandidate {

	@Autowired
	ParticipantServiceImpl participantService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private FSDMNotificationService fsdmMNotificationService;
	@Autowired
	private DmsController dmsController;
	@Autowired
	DesignationService designationService;
	@Value("${Ap.dmsURL}")
    private String dmsURL;

	@GetMapping("/uploadCandidateDocument")
	public String generateAccesskey(@RequestParam("accessKey") String accessKey, HttpSession session, Model model) {

		ParticipantRegistration partcipantRegistration = null;
		Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);
		if (participant.isPresent()) {
			partcipantRegistration = participant.get();
		}
		model.addAttribute("participant", partcipantRegistration);
		return "upload-registrations";
	}

	/* File uploaded method */
	@PostMapping("/uploadByCandidate")
	public ResponseEntity<String> uploadFile(@RequestParam("accessKey") String accessKey,
			@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("identity_proof") String identityProof, @RequestParam("address_proof") String addressProof) {

		// String path = "C:/ParticipantUploadedFiles/"+accessKey+"/";
		String path = "/home/msilazuser01/irecruit/" + accessKey + "/";
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
		try {
			Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);
			String type = file.getOriginalFilename();

			file.transferTo(new File(path + fileName));
			if (name.equals("photograph")) {
				participant.get().setPhotograph(accessKey + "/" + fileName);
			}

			if (name.equals("resume")) {
				participant.get().setResume(accessKey + "/" + fileName);

			}
			if (name.equals("signature")) {
				participant.get().setSignature(accessKey + "/" + fileName);

			}

			if (name.equals("identitityProof")) {
				participant.get().setIdentitityProof(accessKey + "/" + fileName);
				participant.get().setIdentitityProofName(identityProof);
				participant.get().setIdProof(identityProof);

			}

			if (name.equals("addressProof")) {
				participant.get().setAddressProof(accessKey + "/" + fileName);
				participant.get().setAddressProofName(addressProof);
			}

			if (name.equals("10th")) {
				participant.get().setQualification(accessKey + "/" + fileName);

			}

			if (name.equals("12th")) {
				participant.get().setQualification2(accessKey + "/" + fileName);

			}

			if (name.equals("Graduation")) {
				participant.get().setQualification3(accessKey + "/" + fileName);

			}

			if (name.equals("resignation")) {
				participant.get().setResignationLetter(accessKey + "/" + fileName);

			}

			if (name.equals("experience")) {
				participant.get().setExperienceletter(accessKey + "/" + fileName);

			}

			if (name.equals("salaryslip")) {
				participant.get().setSalarySlip(accessKey + "/" + fileName);

			}

			if (name.equals("other")) {
				participant.get().setDocuments(accessKey + "/" + fileName);

			}
			participant.get().setModifiedDate(new Date());
			participantService.saveFiles(participant.get());
		} catch (Exception e) {
			return ResponseEntity.ok("error");
		}
		return ResponseEntity.ok("images/" + accessKey + "/" + fileName);
	}

	@PostMapping("/savedocument")
	@ResponseBody
	public String savedcument(@RequestParam("accesskey") String accessKey, @RequestParam("status") String status) {
		String msg = "";
		Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);
		if (participant.isPresent()) {
			participant.get().setDocuments_status(status);
			participant.get().setModifiedDate(new Date());
			participantService.saveFiles(participant.get());

		}
		if (status.equals("save")) {
			msg = "success";
		}
		if (status.equals("final")) {
			
			msg = "Your Documents are uploaded and your Dealer is notified.\r\n" + "\r\n"
					+ "Please contact your Dealer and inform that you have completed the process.";
		}
		uploaddocumentToCandidate(participant.get());
		uploaddocumentToDealer(participant.get());
		return msg;
	}

	@PostMapping("/savedocuments")
	@ResponseBody
	public String savedcuments(@RequestParam("accesskey") String accessKey, @RequestParam("status") String status) {
		String msg = "";
		Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);
		if (participant.isPresent()) {
			if(participant.get().getOutletCode() == null || participant.get().getEmployeeCode() == null || participant.get().getTitle() == null ||
					participant.get().getFirstName() == null || participant.get().getLastName() == null || participant.get().getAddress() == null ||
					participant.get().getState() == null || participant.get().getCity() == null || participant.get().getPin() == null ||
					participant.get().getTehsil() == null || participant.get().getVillage() == null ||  participant.get().getIdProof() == null ||
					participant.get().getBirthDate() == null || participant.get().getMobile() == null || participant.get().getAlternateContactNumber() == null ||
					participant.get().getEmail() == null || participant.get().getAdharNumber() == null || participant.get().getDL() == null ||
					participant.get().getHighestQualification() == null ||
					participant.get().getPrimaryLanguage() == null || participant.get().getSecondaryLanguage() == null) {
				System.out.println("fina..........1");
				return  "1";	
			}
			if(participant.get().getDivision() == null || participant.get().getFinalDesignation() == null || participant.get().getDepartmentCode() == null ||
				participant.get().getWorkedWithMSILBefore() == null ||  participant.get().getEmpSalary() == null || participant.get().getGender() == null ||
				participant.get().getOwnTwoWheeler() == null || participant.get().getKnowDriving() == null || participant.get().getMdsCertified() == null ) {
				System.out.println("fina..........2");
				return  "2";
			}
			if(participant.get().getPhotograph() == null || participant.get().getSignature() == null || participant.get().getIdentitityProof() == null ||
					participant.get().getAddressProof() == null || participant.get().getQualification() == null || participant.get().getQualification2() == null ||
					participant.get().getQualification3() == null || participant.get().getResume() == null ) {
				System.out.println("fina..........3");
				return  "3";
				
			}

		}
		if (participant.isPresent()) {
			participant.get().setDocuments_status(status);
			participant.get().setRegStatus("3");
			participant.get().setModifiedDate(new Date());
			if (participant.get().getJoiningDate() == null || participant.get().getJoiningDate().equals("")) {
				String date = "";
				try {
					DateFormat srcDf = new SimpleDateFormat("dd/MM/yyyy");
					date = srcDf.format(new Date());
				} catch (Exception e) {
					System.out.println("Error...." + e);
				}
				participant.get().setJoiningDate(date);
			}
			
			if (participant.get().getFinalDesignation() != null && !participant.get().getFinalDesignation().equals("STR")) {
				participant.get().setFsdmApprovalStatus("3");
				if(participant.get().getPrarambhStatus() == null || participant.get().getPrarambhStatus().equals(""))
				participant.get().setPrarambhStatus("0");
			}else {
				participant.get().setPrarambhStatus("1");
			}
			
			participantService.saveFiles(participant.get());

			/* ganerate mspin */
			if ((participant.get().getFinalDesignation() != null
					&& participant.get().getFinalDesignation().equals("STR"))
					&& (participant.get().getMspin() == null || participant.get().getMspin().equals(""))) {
				try {
				dmsController.generateMSPIN(accessKey);
				}catch(Exception e) {
					System.out.println("error..."+e);
				}
				sendEmailGenetateMSPIN(participantService.findByAccesskey(accessKey).get());
				
				
				
			}
			if (participant.get().getFinalDesignation() != null
					&& !participant.get().getFinalDesignation().equals("STR")) {
				Optional<FSDMNotification> notification = fsdmMNotificationService.getAccesskey(accessKey);
				String name = "";
				name += participant.get().getFirstName();
				if (participant.get().getMiddleName() != null) {
					name += " " + participant.get().getMiddleName();
				}
				name += " " + participant.get().getLastName();
				if (notification.isPresent()) {
					notification.get().setStatus("1");
					notification.get().setNotificationDate(new Date());
					fsdmMNotificationService.saveNotification(notification.get());
				} else {
					FSDMNotification noti = new FSDMNotification();

					noti.setStatus("1");
					noti.setAccesskey(accessKey);
					Optional<Dealer> dealer = dealerService.getById(participant.get().getDealerId());
					if (dealer.isPresent()) {
						noti.setDealerId(participant.get().getDealerId());
						for (Outlet outlet : dealer.get().getOutlet()) {
							if (outlet.getOutletCode().equals(participant.get().getOutletCode())) {

								noti.setOutletCode(outlet.getOutletCode());
								noti.setOutletName(outlet.getOutletName());
								noti.setRegion(outlet.getRegion().getRegionCode());
								noti.setFsdmId(outlet.getRegion().getFsdm().getId());
								
							}
						}
					}
					final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					noti.setRegistraionDate(formatter.format(participant.get().getRegistration_date()));
					Optional<Designation>designation = designationService.getDesignationByCode(participant.get().getFinalDesignation());
					if(designation.isPresent()) {
						noti.setDesignation(designation.get().getDesignationName());	
					}
					noti.setNotificationDate(new Date());
					noti.setCandidateName(name);
					noti.setDealerName(dealer.get().getName());
					fsdmMNotificationService.saveNotification(noti);
				}
			}
		}

		msg = "success";

		return msg;
	}
	/////

	@PostMapping("/finalDesignation")
	@ResponseBody
	public String saveDesignation(@RequestParam("accesskey") String accessKey,
			@RequestParam("designtion") String designtion) {
		String msg = "";
		Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);

		if (participant.isPresent()) {
			participant.get().setFsdmApprovalStatus("3");
			participant.get().setFinalDesignationStatus("1");
			participant.get().setFinalDesignation(designtion);
			participant.get().setModifiedDate(new Date());
			participantService.saveFiles(participant.get());

			Optional<FSDMNotification> notification = fsdmMNotificationService.getAccesskey(accessKey);
			String name = "";
			name += participant.get().getFirstName();
			if (participant.get().getMiddleName() != null) {
				name += " " + participant.get().getMiddleName();
			}
			name += " " + participant.get().getLastName();
			if (notification.isPresent()) {
				notification.get().setStatus("1");
				notification.get().setNotificationDate(new Date());
				fsdmMNotificationService.saveNotification(notification.get());
			} else {
				FSDMNotification noti = new FSDMNotification();
				noti.setStatus("1");
				noti.setAccesskey(accessKey);
				Optional<Dealer> dealer = dealerService.getById(participant.get().getDealerId());
				if (dealer.isPresent()) {
					noti.setDealerId(participant.get().getDealerId());
					for (Outlet outlet : dealer.get().getOutlet()) {
						if (outlet.getOutletCode().equals(participant.get().getOutletCode())) {
							noti.setOutletCode(outlet.getOutletCode());
							noti.setOutletName(outlet.getOutletName());
							noti.setRegion(outlet.getRegion().getRegionCode());
							noti.setFsdmId(outlet.getRegion().getFsdm().getId());
						}
					}
				}
				final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				noti.setRegistraionDate(formatter.format(participant.get().getRegistration_date()));
				Optional<Designation>designation = designationService.getDesignationByCode(participant.get().getFinalDesignation());
				if(designation.isPresent()) {
					noti.setDesignation(designation.get().getDesignationName());	
				}
				noti.setNotificationDate(new Date());
				noti.setCandidateName(name);
				noti.setDealerName(dealer.get().getName());
				fsdmMNotificationService.saveNotification(noti);
				try {
				dmsController.changeDesignation(accessKey,dmsURL);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}	
	              // this.dmsController.generateMSPIN(participant.get().getAccessKey());
	            
		}
		msg = "success";
		return msg;
	}

	@PostMapping("/getdocument")
	public ResponseEntity<String> getdocuments(@RequestParam("accessKey") String accessKey,
			@RequestParam("status") String status) {

		Optional<ParticipantRegistration> participant = participantService.findByAccesskey(accessKey);
		if (participant.isPresent()) {
			participant.get().setDocuments_status(status);
			participantService.saveFiles(participant.get());
		}
		return ResponseEntity.ok("success");
	}

	private String uploaddocumentToDealer(ParticipantRegistration participant) {
		String subjectLine = "IRECRUIT Candidate documents uploaded";
		String mailBody = DataProccessor.readFileFromResource("joiningDocsUploadSuccessToDealer");
		mailBody = mailBody.replace("${candidateName}",
				participant.getFirstName() + " " + participant.getMiddleName() + " " + participant.getLastName());
		Dealer dealer = dealerService.getById(participant.getDealerId()).get();
		mailBody = mailBody.replace("${dealerName}", dealer.getName());
		mailBody = mailBody.replace("${link}", "https://staging.irecruit.org.in/irecruit/login"); // Docs Upload
																											// link will
		mailBody = mailBody.replace("${dealerShip}", dealer.getOutlet().get(0).getOutletName());																									// be here
		mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
		if(dealer.getEmail() != null) {
			mailBody = mailBody.replace("${email}", dealer.getEmail());	
		}else {
			mailBody = mailBody.replace("${email}", "");	
		}
		if(dealer.getMobile() != null) {
			mailBody = mailBody.replace("${mobile}", dealer.getMobile());	
		}else {
			mailBody = mailBody.replace("${mobile}", "");		
		}
		// Create Payload
		SendPayload sendP = new SendPayload();
		// sendP.setTo(to);
		sendP.setTo(dealer.getEmail());
		sendP.setSubjectLine(subjectLine);
		sendP.setMsg(mailBody);
		sendP.setCc("");
		sendP.setBcc("");
		sendP.setFrom("");

		EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(),
				sendP.getMsg(), "smtp");
		System.out.println("Email Sent for Assessment Passing");

		return "success";
	}
	
	private String uploaddocumentToCandidate(ParticipantRegistration participant) {
		String subjectLine = "IRECRUIT Candidate documents uploaded";
		String mailBody = DataProccessor.readFileFromResource("joiningDocsUploadSuccess");
		mailBody = mailBody.replace("${candidateName}",
				participant.getFirstName() + " " + participant.getMiddleName() + " " + participant.getLastName());
		Dealer dealer = dealerService.getById(participant.getDealerId()).get();
		mailBody = mailBody.replace("${dealerName}", dealer.getName());
		mailBody = mailBody.replace("${link}", "https://staging.irecruit.org.in/irecruit/candidateLogin"); // Docs Upload
																											// link will
		mailBody = mailBody.replace("${dealerShip}", dealer.getOutlet().get(0).getOutletName());																									// be here
		mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
		if(dealer.getEmail() != null) {
			mailBody = mailBody.replace("${email}", dealer.getEmail());	
		}else {
			mailBody = mailBody.replace("${email}", "");	
		}
		if(dealer.getMobile() != null) {
			mailBody = mailBody.replace("${mobile}", dealer.getMobile());	
		}else {
			mailBody = mailBody.replace("${mobile}", "");		
		}
		// Create Payload
		SendPayload sendP = new SendPayload();
		// sendP.setTo(to);
		sendP.setTo(participant.getEmail());
		sendP.setSubjectLine(subjectLine);
		sendP.setMsg(mailBody);
		sendP.setCc("");
		sendP.setBcc("");
		sendP.setFrom("");

		EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(),
				sendP.getMsg(), "smtp");
		System.out.println("Email Sent for Assessment Passing");

		return "success";
	}
	
	
	
	private String sendEmailGenetateMSPIN(final ParticipantRegistration participant) {
		final String subjectLine = "iRecruit – MSPIN "+ participant.getMspin() +" Generated";
		String mailBody = DataProccessor.readFileFromResource("mspinGeneratedNotification");
		
		mailBody = mailBody.replace("${candidateName}", String.valueOf(participant.getFirstName()) + " "
				+ participant.getMiddleName() + " " + participant.getLastName());
		// smsMsg = smsMsg.replace("${name}", String.valueOf(participant.getFirstName())
		// + " " + participant.getMiddleName() + " " + participant.getLastName());
		final Dealer dealer = this.dealerService.getById((long) participant.getDealerId()).get();
		mailBody = mailBody.replace("${dealer}", dealer.getName());
		if(participant.getMspin() != null) {
		mailBody = mailBody.replace("${mspin}", participant.getMspin());
		}else {
			mailBody = mailBody.replace("${mspin}", "");
		}
		mailBody = mailBody.replace("${link}", "http://staging.irecruit.org.in/irecruit/login");
		mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
		
		// smsMsg = smsMsg.replace("${accesskey}", participant.getAccessKey());
		// SmsUtility.sendSmsHandler(participant.getMobile(), smsMsg, "MSILOT");
		final SendPayload sendP = new SendPayload();
		sendP.setTo(dealer.getEmail());
		sendP.setSubjectLine(subjectLine);
		sendP.setMsg(mailBody);
		sendP.setCc("");
		sendP.setBcc("");
		sendP.setFrom("Armezo Solutions");
		try {
			EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(),
					sendP.getMsg(), "smtp");
		} catch (Exception e) {
			System.out.println("...........Error in send mail.........");
		}
		
		 try {
		     if(dealer.getMobile() != null && !dealer.getMobile().equals("") ) {
			 String smsMsg = DataProccessor.getSMS("mspinGenerated");
			 String name="";
			 name += participant.getFirstName();
			 if(participant.getMiddleName() != null && !participant.getMiddleName().equals("")) {
				 name +=  " "+ participant.getFirstName(); 
			 }
			 name +=  " "+ participant.getLastName(); 
			 smsMsg = smsMsg.replace("${mspin}", participant.getMspin());
			 smsMsg = smsMsg.replace("${name}", name);
			 smsMsg = smsMsg.replace("${accesskey}", participant.getAccessKey());
		     SmsUtility.sendSmsHandler(dealer.getMobile() , smsMsg,"MSILOT" );
		     
		     String smsMsg1 = DataProccessor.getSMS("recruited");
		    // SmsUtility.sendSmsHandler(dealer.getMobile() , smsMsg1,"MSILOT" );
		     }
			}catch(Exception e) {
				System.out.println("Error....."+e);
	    }
		
		System.out.println("Email Sent for Docs Rejected");
		return "success";
	}
}
