package com.msil.irecruit.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.AccessKeyMaster;
import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.ModelGenerateAccesskey;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.AccessKeyMasterService;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.payload.FilterPayload;
import com.msil.irecruit.tc.entities.ModelRemainder;
import com.msil.irecruit.utils.Accesskey;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class AccessKeyMasterController {
	
	/*
	 * @Autowired private SmsUtility smsUtility;
	 */

	@Autowired
	AccessKeyMasterService accessKeyMasterService;
	@Autowired
	ParticipantServiceImpl participantService;
	@Autowired
	DesignationService designationService;

	@Autowired
	DealerService dealerService;
	@Autowired
	OutletService outletService;	
	@Autowired
	DataScienceService dataScienceService;

	@Value("${spring.url}")
	private String url;

	@GetMapping("/generateAccesskey")
	public String generateAccesskey(HttpSession session, Model model) {
		if (session.getAttribute("userId") != null) {
			 final List<Designation> designations2 = (List<Designation>)this.designationService.getAll();
	          Map<String, String> designation = designations2.stream().collect(Collectors.toMap(Designation  :: getDesignationCode , Designation  :: getDesignationName));
			List<ModelGenerateAccesskey> participantList = new ArrayList<>();
			long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			List<AccessKeyMaster> keys = accessKeyMasterService.getByDealer(dealerId);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			for (AccessKeyMaster key : keys) {
				ModelGenerateAccesskey modelGenerateAccesskey  = new ModelGenerateAccesskey();
				Optional<ParticipantRegistration> pa = participantService
						.getParticipantByAccesskey(key.getAccesskey());
				if (pa.isPresent()) {
					if(pa.get().getTestStatus() ==null ||Integer.parseInt(pa.get().getTestStatus() )< 3) {
                 
					modelGenerateAccesskey.setAccesskey(pa.get().getAccessKey());
					modelGenerateAccesskey.setName(pa.get().getFirstName()+" "+pa.get().getMiddleName()+" "+pa.get().getLastName());
					modelGenerateAccesskey.setMobile(pa.get().getMobile());
					modelGenerateAccesskey.setDesignation(pa.get().getDesignation());
					modelGenerateAccesskey.setEmail(pa.get().getEmail());
					modelGenerateAccesskey.setDateOfApplication(formatter.format(pa.get().getRegistration_date()));
		
					String status = "";
					if (pa.get().getTestStatus() == null || pa.get().getTestStatus().equals("0")) {
						status = "Not Started";
					} else if (pa.get().getTestStatus() != null || pa.get().getTestStatus().equals("1")) {
						status = "Started";

					}
					modelGenerateAccesskey.setTestStatus(status);
					modelGenerateAccesskey.setAccesskeyStatus(key.getStatus());
					
					if(pa.get().getReactivationDate() != null) {
						Long t1 = pa.get().getReactivationDate().getTime();
						Long t2 = new Date().getTime();
						int tm = (int) (t2 - t1) / (60 * 60 * 1000);
						if (tm > 48) {
							modelGenerateAccesskey.setReactivateStatus("1");
						}
					}else if(pa.get().getSendMailDate() != null) {
						Long t1 = pa.get().getSendMailDate().getTime();
						Long t2 = new Date().getTime();
						int tm = (int) (t2 - t1) / (60 * 60 * 1000);
						if (tm > 72) {
							modelGenerateAccesskey.setReactivateStatus("1");
						}
					}
					
					
					final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(pa.get().getOutletCode(), dealerId);
                    if(outlet.isPresent()) {
                    	modelGenerateAccesskey.setRegionCode(outlet.get().getRegion().getRegionCode());
                    	modelGenerateAccesskey.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
                    	modelGenerateAccesskey.setDealerName(outlet.get().getOutletName());
                    	modelGenerateAccesskey.setOutletCode(outlet.get().getOutletCode());
                    	modelGenerateAccesskey.setCity(outlet.get().getCity().getCityName());
                    	modelGenerateAccesskey.setState(outlet.get().getState().getStateName());
                    	
                    }else {
                    	modelGenerateAccesskey.setRegionCode("");
                    	modelGenerateAccesskey.setParentDealer("");
                    	modelGenerateAccesskey.setDealerName("");
                    	modelGenerateAccesskey.setOutletCode("");
                    	modelGenerateAccesskey.setCity("");
                    	modelGenerateAccesskey.setState("");
                    	
                    }
                    if (pa.get().getFinalDesignation() != null && !pa.get().getFinalDesignation().equals("")) {
                    	modelGenerateAccesskey.setFinalDesignation((String)designation.get(pa.get().getFinalDesignation()));
                    	modelGenerateAccesskey.setFinalDesignationCode(pa.get().getFinalDesignation());
                    }
					participantList.add(modelGenerateAccesskey);
                    }
					
				} if(!pa.isPresent()) {
					modelGenerateAccesskey.setAccesskey(key.getAccesskey());
					modelGenerateAccesskey.setTestStatus("Email Not Send");
					modelGenerateAccesskey.setAccesskeyStatus(key.getStatus());
					participantList.add(modelGenerateAccesskey);
				}
				
			}
			//dealerId
			
			Optional<Dealer> dealer = dealerService.getById(dealerId); 
		
			model.addAttribute("dealer", dealer.get());
			model.addAttribute("participant", participantList);
			model.addAttribute("designationList", designationService.getDesignation());
			model.addAttribute("outletList", dealer.get().getOutlet());
			DataProccessor.setDateRange(model);
			//model.addAttribute("outletList", "dfsdf");
			
		}else {
			return "redirect:login";
		}
		return "accesskey_list";
	}
	
	

	@PostMapping("/generateAccesskeyPro")
	@ResponseBody
	public String generateAccesskeyPro(HttpSession session, @RequestParam("noOfKey") String noOfKeys) {
		//session.setAttribute("userId", "2");
		if (session.getAttribute("userId") != null) {
			int keys = 0;
			if (noOfKeys.length() > 0) {
				keys = Integer.parseInt(noOfKeys);
			}

			long dealerId = Long.parseLong(session.getAttribute("userId").toString());

			String accesskey = "";
			List<AccessKeyMaster> keyLsit = new ArrayList<>();
			for (int i = 0; i < keys; i++) {
				boolean check = true;
				while (check) {
					accesskey = Accesskey.getAccesskey();
					if (!accessKeyMasterService.getAccesskey(accesskey).isPresent()) {
						check = false;
					}
				}
				AccessKeyMaster accessKeyMaser = new AccessKeyMaster();
				accessKeyMaser.setAccesskey(accesskey);
				accessKeyMaser.setDealerId(dealerId);
				accessKeyMaser.setModifiedDate(new Date());
				keyLsit.add(accessKeyMaser);
			}
			accessKeyMasterService.saveAccesskey(keyLsit);

		}else {
			return "error";
		}
		return "success";
	}

	@PostMapping("/deleteAccesskey")
	public String deleteAccesskey(HttpSession session, @RequestParam("accesskey") String accesskey) {
		accessKeyMasterService.deleteByAccesskey(accesskey);

		return "redirect:generateAccesskey";
	}

	@PostMapping("/assignAccesskeyPro")
	@ResponseBody
	public String assignAccesskeyPro(HttpSession session, @RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName, @RequestParam("lastName") String lastName,
			@RequestParam("mobile") String mobile, @RequestParam("email") String email,
			@RequestParam("designation") String designation, @RequestParam("accesskey") String accesskey,
			@RequestParam("dealerCode")  String  dealerCode,@RequestParam("profile")String profile,
			@RequestParam("designationDatascience")String designationDatascience) {
		System.out.println("Cal................");
		if (session.getAttribute("userId") != null) {
			
			String dealerId = session.getAttribute("userId").toString();
			Optional<ParticipantRegistration> participant = participantService
					.getParticipantByFirstNameAndLastNameAndEmail(firstName, lastName, email,Long.parseLong(dealerId));
			if (participant.isPresent()) {
				int month = participant.get().getRegistration_date().getMonth();
				if(month< 6) {
				return participant.get().getAccessKey();
				}else {
					ParticipantRegistration participantReg = new ParticipantRegistration();
					participantReg.setAccessKey(accesskey);
					participantReg.setFirstName(firstName);
					participantReg.setMiddleName(middleName);
					participantReg.setLastName(lastName);
					participantReg.setMobile(mobile);
					participantReg.setEmail(email);
					participantReg.setDesignation(designation);
					participantReg.setFinalDesignation(designationDatascience);
					participantReg.setDivision(designation);
					
					participantReg.setRegistration_date(new Date());
					participantReg.setDealerId(Long.parseLong(dealerId));
					participantReg.setRegStatus("1");
					participantReg.setOutletCode(dealerCode);
					participantReg.setSendMailDate(new Date());
					participantReg.setModifiedDate(new Date());
					participantService.saveData(participantReg);
					if(designation.equals("Sales")) {
					DataScience dataScience = new DataScience();
					dataScience.setAccesskey(accesskey);
					dataScience.setDesignation(designationDatascience);
					dataScience.setProfile(profile);
					dataScienceService.save(dataScience);
					}
					accessKeyMasterService.updateModiedDate(accesskey);
					String name="";
					name   += firstName;
					if(middleName != null && middleName.length()>0) {
						name   +=" "+ middleName;	
					}
					name   +=" "+lastName;	
					try {
						
					sendEmail(accesskey, name, email, participantReg,"first");			
					// sending sms after email sent

					String sms =DataProccessor.getSMS("sendAccessKey");
					SmsUtility.sendSmsPromotional(mobile,sms,"522225","1007458194897060999");
					}catch(Exception e) {
						System.out.println("Error......"+e);
					}
					
					
					
				}
			} else {
				ParticipantRegistration participantReg = new ParticipantRegistration();
				participantReg.setAccessKey(accesskey);
				participantReg.setFirstName(firstName);
				participantReg.setMiddleName(middleName);
				participantReg.setLastName(lastName);
				participantReg.setMobile(mobile);
				participantReg.setEmail(email);
				participantReg.setDesignation(designation);
				participantReg.setFinalDesignation(designationDatascience);
				participantReg.setDivision(designation);
				
				participantReg.setRegistration_date(new Date());
				participantReg.setDealerId(Long.parseLong(dealerId));
				participantReg.setRegStatus("1");
				participantReg.setOutletCode(dealerCode);
				participantReg.setSendMailDate(new Date());
				participantReg.setModifiedDate(new Date());
				participantService.saveData(participantReg);
				if(designation.equals("Sales")) {
				DataScience dataScience = new DataScience();
				dataScience.setAccesskey(accesskey);
				dataScience.setDesignation(designationDatascience);
				dataScience.setProfile(profile);
				dataScienceService.save(dataScience);
				}
				accessKeyMasterService.updateModiedDate(accesskey);
				String name="";
				name   += firstName;
				if(middleName != null && middleName.length()>0) {
					name   +=" "+ middleName;	
				}
				name   +=" "+lastName;	
				try {
					
				sendEmail(accesskey, name, email, participantReg,"first");			
				// sending sms after email sent

				String sms =DataProccessor.getSMS("sendAccessKey");
				SmsUtility.sendSmsPromotional(mobile,sms,"522225","1007458194897060999");
				}catch(Exception e) {
					System.out.println("Error......"+e);
				}

			}
				
		}
		else {
			return "redirect:login";
		}
		return "success";
	}
	
	
	@PostMapping("/remainderEmail")
	@ResponseBody
	public String sedRemainderEmail(HttpSession session, @RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName, @RequestParam("lastName") String lastName,
			@RequestParam("mobile") String mobile, @RequestParam("email") String email,
			@RequestParam("designation") String designation, @RequestParam("accesskey") String accesskey,
			@RequestParam("dealerCode")  String  dealerCode,@RequestParam("profile")String profile,
			@RequestParam("designationDatascience")String designationDatascience) {
		
		if (session.getAttribute("userId") != null) {
			String dealerId = session.getAttribute("userId").toString();
			Optional<ParticipantRegistration> participant = participantService
					.getParticipantByAccesskey(accesskey);
			if (participant.isPresent()) {
				ParticipantRegistration p = new ParticipantRegistration();
				p = participant.get();
				p.setFirstName(firstName);
				p.setMiddleName(middleName);
				p.setLastName(lastName);
				p.setMobile(mobile);
				p.setEmail(email);
				p.setDesignation(designation);
				p.setFinalDesignation(designationDatascience);
				p.setDivision(designation);
				p.setRegistration_date(new Date());
				p.setDealerId(Long.parseLong(dealerId));
				p.setRegStatus("1");
				
				p.setSendMailDate(new Date());
				p.setOutletCode(dealerCode);
				p.setModifiedDate(new Date());
				participantService.saveData(p);	
				if(designation.equals("Sales")) {
					Optional<DataScience>dataScience =dataScienceService.findByAccesskey(accesskey);
					if(dataScience.isPresent()) {
					dataScience.get().setAccesskey(accesskey);
					dataScience.get().setDesignation(designationDatascience);
					dataScience.get().setProfile(profile);
					dataScienceService.save(dataScience.get());
					}else {
						DataScience d = new DataScience();
						d.setAccesskey(accesskey);
						d.setDesignation(designationDatascience);
						d.setProfile(profile);
						dataScienceService.save(d);
					}
					}else {
						Optional<DataScience>d=  dataScienceService.findByAccesskey(accesskey);
						if(d.isPresent()) {
							dataScienceService.delete(d.get());	
						}
					}
				accessKeyMasterService.updateModiedDate(accesskey);
				sendEmail(accesskey, firstName + " " + middleName + " " + lastName, email, participant.get(),"reminder");			
				// sending sms after email sent
				// here send name, mobile, sender details as params
				try {
				String msg = DataProccessor.getSMS("applyReminder");
				msg = msg.replace("${link}", "http://staging.irecruit.org.in/irecruit/candidateLogin");
			    SmsUtility.sendSmsHandler(mobile, msg,"MSILOT");
				}catch(Exception e) {
					System.out.println("error...."+e);
				}
			}
		}else {
			return "redirect:login";
		}
		return "Success";
	}
	
	@PostMapping("/assignAccesskeyReminder")
	public ResponseEntity<ModelRemainder> assignAccesskeyRe(HttpSession session, @RequestParam("accesskey") String accesskey) {
		Optional<ParticipantRegistration> participant = participantService
				.getParticipantByAccesskey(accesskey);
		ModelRemainder model = new  ModelRemainder();
		if(participant.isPresent()) {			
			model.setFirstName(participant.get().getFirstName());
			model.setMiddleName(participant.get().getMiddleName());
			model.setLastName(participant.get().getLastName());
			model.setEmail(participant.get().getEmail());
			model.setMobile(participant.get().getMobile());
			model.setDesignation(participant.get().getDesignation());
			model.setDealerCode(participant.get().getOutletCode());	
			if(participant.get().getDesignation().equals("Sales")) {
				Optional<DataScience> d =dataScienceService.findByAccesskey(accesskey);
				 if(d.isPresent()) {
					 model.setProfile(d.get().getProfile());	
					 model.setDesignationDatScience(d.get().getDesignation());
				 }
			}
		}
		  return ResponseEntity.ok(model);
	}
	@PostMapping("/reAssignAccesskeyPro")
	@ResponseBody
	public String assignAccesskeyRePro(HttpSession session, @RequestParam("firstName") String firstName,
			@RequestParam("middleName") String middleName, @RequestParam("lastName") String lastName,
			@RequestParam("mobile") String mobile, @RequestParam("email") String email,
			@RequestParam("designation") String designation, @RequestParam("accesskey") String accesskey,
			@RequestParam("dealerCode")  String  dealerCode,@RequestParam("profile")String profile,
			@RequestParam("designationDatascience")String designationDatascience) {	
		if (session.getAttribute("userId") != null) {
			String dealerId = session.getAttribute("userId").toString();	
			Optional<ParticipantRegistration>pr = participantService.findByAccesskey(accesskey);
			 ParticipantRegistration participantReg = new ParticipantRegistration();
			if(pr.isPresent()) {
				participantReg = pr.get();
				participantReg.setAccessKey(accesskey);
				participantReg.setFirstName(firstName);
				participantReg.setMiddleName(middleName);
				participantReg.setLastName(lastName);
				participantReg.setMobile(mobile);
				participantReg.setEmail(email);
				participantReg.setDesignation(designation);
				participantReg.setFinalDesignation(designationDatascience);
				participantReg.setDivision(designation);
				participantReg.setRegistration_date(new Date());
				participantReg.setDealerId(Long.parseLong(dealerId));
				participantReg.setRegStatus("1");				
				participantReg.setOutletCode(dealerCode);
				participantReg.setSendMailDate(new Date());
				participantReg.setModifiedDate(new Date());
				participantService.saveData(participantReg);
				accessKeyMasterService.updateModiedDate(accesskey);
			}else {			 
				participantReg.setAccessKey(accesskey);
				participantReg.setFirstName(firstName);
				participantReg.setMiddleName(middleName);
				participantReg.setLastName(lastName);
				participantReg.setMobile(mobile);
				participantReg.setEmail(email);
				participantReg.setDesignation(designation);
				participantReg.setFinalDesignation(designationDatascience);
				participantReg.setDivision(designation);
				participantReg.setRegistration_date(new Date());
				participantReg.setDealerId(Long.parseLong(dealerId));
				participantReg.setRegStatus("1");
				
				participantReg.setOutletCode(dealerCode);
				participantReg.setSendMailDate(new Date());
				participantReg.setModifiedDate(new Date());
				participantService.saveData(participantReg);
				accessKeyMasterService.updateModiedDate(accesskey);
			}
				if(designation.equals("Sales")) {
					Optional<DataScience> d =dataScienceService.findByAccesskey(accesskey);
					if(d.isPresent()) {
						d.get().setAccesskey(accesskey);
						d.get().setDesignation(designationDatascience);
						d.get().setProfile(profile);
						dataScienceService.save(d.get());
					}else {
					DataScience dataScience = new DataScience();
					dataScience.setAccesskey(accesskey);
					dataScience.setDesignation(designationDatascience);
					dataScience.setProfile(profile);
					dataScienceService.save(dataScience);
					}
					}

				try {
					
					sendEmail(accesskey, firstName + " " + middleName + " " + lastName, email, participantReg,"first");			
					// sending sms after email sent
					String sms =DataProccessor.getSMS("sendAccessKey");
					SmsUtility.sendSmsPromotional(mobile,sms,"522225","1007458194897060999");;
				}catch(Exception e) {
					System.out.println("Error......"+e);
				}

			
		}else {
			return "redirect:login";
		}
		return "Success";
	}

	@PostMapping("/assignOldAccesskey")
	@ResponseBody
	public String assignOldAccesskey(HttpSession session, @RequestParam("accesskey") String accesskey) {
		Optional<ParticipantRegistration> participant = participantService.getParticipantByAccesskey(accesskey);
		if (participant.isPresent()) {
			participant.get().setRegistration_date(new Date());
			participant.get().setModifiedDate(new Date());
		}
		return "redirect:generateAccesskey";
	}

	@PostMapping("/blockdAccesskey")
	public String blockdAccesskey(HttpSession session, @RequestParam("accesskey") String accesskey,
			@RequestParam("status") String status) {
		Optional<AccessKeyMaster> key = accessKeyMasterService.getAccesskey(accesskey);
		AccessKeyMaster accessKeyMaster  = new AccessKeyMaster();
		if (key.isPresent()) {
			accessKeyMaster = key.get();
			if (status.equals("B")) {
				accessKeyMaster.setStatus("B");
			} else {
				accessKeyMaster.setStatus("A");
			}
			accessKeyMasterService.updateAccesskey(accessKeyMaster);
		}
		return "redirect:generateAccesskey";
	}
	
	@PostMapping("/reactivate")
	public String reactivateKey(@RequestParam("accesskey") String accesskey) {
		Optional<AccessKeyMaster> key = accessKeyMasterService.getAccesskey(accesskey);
		Optional<ParticipantRegistration> participant = participantService.getParticipantByAccesskey(accesskey);
		if (participant.isPresent()) {
			ParticipantRegistration reg = participant.get();
			reg.setReactivationDate(new Date());
			reg.setModifiedDate(new Date());
			participantService.saveData(reg);
			accessKeyMasterService.updateModiedDate(accesskey);
		}else {
		if (key.isPresent() && !participant.isPresent()) {
			AccessKeyMaster askey = key.get();
			askey.setCreatedDate(new Date());
			accessKeyMasterService.updateAccesskey(askey);
			accessKeyMasterService.updateModiedDate(accesskey);
		}
		}
		return "redirect:generateAccesskey";
	}

	public String sendEmail(String accesskey, String name, String to, ParticipantRegistration participant,String type) {
		String subjectLine="";
		String mailBody="";
		if(type.equals("first")) {
		 mailBody=DataProccessor.readFileFromResource("firstEmail");
		}
		if(type.equals("reminder")) {
			 mailBody=DataProccessor.readFileFromResource("firstEmail");
			}
		if(type.equals("first")) {
			subjectLine="iRecruit – Your Job Application: Registration & Assessment";
		}
		if(type.equals("reminder")) {
			subjectLine="Reminder: iRecruit – Your Job Application: Registration & Assessment";
		}
		mailBody = mailBody.replace("${candidateName}", name);
		Dealer dealer =dealerService.getById(participant.getDealerId()).get();
		if(dealer!=null) {
			mailBody = mailBody.replace("${dealerName}",dealer.getName());
			if(dealer.getMobile() != null && !dealer.getMobile().equals("")) {
			 mailBody = mailBody.replace("${mobile}",dealer.getMobile());
			}else {
				 mailBody = mailBody.replace("${mobile}","");	
			}
			if(dealer.getEmail() != null && !dealer.getEmail().equals("")) {
				 mailBody = mailBody.replace("${email}",dealer.getEmail());
			}else {
				 mailBody = mailBody.replace("${email}","");
			}
		}
		Optional<Outlet> outlet =outletService.getOutletByOutletNameAndDealerId(participant.getOutletCode(),participant.getDealerId());
		if(outlet.isPresent()) {
			mailBody = mailBody.replace("${dealerShipName}",outlet.get().getOutletName());	
		}
		mailBody = mailBody.replace("${link}", url + "?accesskey=" +accesskey);
		mailBody = mailBody.replace("${accesskey}", accesskey);
		mailBody = mailBody.replace("${assessment}", "http://staging.irecruit.org.in/irecruit/candidateLogin");

		SendPayload sendP = new SendPayload();
		//sendP.setTo(to);
		sendP.setTo(to);
		sendP.setSubjectLine(subjectLine);
		sendP.setMsg(mailBody);
		sendP.setCc("");
		sendP.setBcc("");
		sendP.setFrom("Armezo Solutions");
		
			EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(),
					sendP.getMsg(), "smtp");

		return "success";
	}
	
	@PostMapping("/getDatascienceDesignation")
	@ResponseBody
	public String getDatascienceDesignation(HttpSession session, @RequestParam("profile") String profile) {
		List<Designation> list =designationService.getDesignationByProfile(profile);
		String reult="<Option value=''>Select</Option>";
		for(Designation d: list) {
			reult += "<Option value="+d.getDesignationCode()+">"+d.getDesignationName()+" </Option>";
		}
		reult += "<Option value='STR'>Sales Trainee </Option>";
		return reult;
	}
	// AccessKey Filter
	@PostMapping("/accesskeyFilter")
	public String accesskeyFilterByDate(@RequestParam("dateRange") String dateRange, HttpSession session, Model model) {
		if (session.getAttribute("userId") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
			Date dateFrom=null;
			Date dateTo=null;
			if(!dateRange.equals("Select Date")) {
				String[] splitDate=dateRange.split("-");
				try {
					dateFrom=sdf.parse(splitDate[0].trim());
					dateTo=sdf.parse(splitDate[1].trim());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}		
			}
			List<ModelGenerateAccesskey> participantList = new ArrayList<>();
			Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			List<AccessKeyMaster> keys = new ArrayList<AccessKeyMaster>();
			if(dateFrom!=null && dateTo!=null) {
				keys= accessKeyMasterService.getByDealerIdAndDateRange(dealerId,dateFrom,dateTo);
			}else {				
				keys = accessKeyMasterService.getByDealer(dealerId);
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			for (AccessKeyMaster key : keys) {
				ModelGenerateAccesskey modelGenerateAccesskey  = new ModelGenerateAccesskey();
				Optional<ParticipantRegistration> pa = participantService
						.getParticipantByAccesskey(key.getAccesskey());
				if (pa.isPresent()) {
					if(pa.get().getTestStatus() ==null ||Integer.parseInt(pa.get().getTestStatus() )< 3) {
                 
					modelGenerateAccesskey.setAccesskey(pa.get().getAccessKey());
					modelGenerateAccesskey.setName(pa.get().getFirstName()+" "+pa.get().getMiddleName()+" "+pa.get().getLastName());
					modelGenerateAccesskey.setMobile(pa.get().getMobile());
					modelGenerateAccesskey.setDesignation(pa.get().getDesignation());
					modelGenerateAccesskey.setEmail(pa.get().getEmail());
					modelGenerateAccesskey.setDateOfApplication(formatter.format(pa.get().getRegistration_date()));
		
					String status = "";
					if (pa.get().getTestStatus() == null || pa.get().getTestStatus().equals("0")) {
						status = "Not Started";
					} else if (pa.get().getTestStatus() != null || pa.get().getTestStatus().equals("1")) {
						status = "Started";

					}
					modelGenerateAccesskey.setTestStatus(status);
					modelGenerateAccesskey.setAccesskeyStatus(key.getStatus());
					
					if(pa.get().getReactivationDate() != null) {
						Long t1 = pa.get().getReactivationDate().getTime();
						Long t2 = new Date().getTime();
						int tm = (int) (t2 - t1) / (60 * 60 * 1000);
						if (tm > 48) {
							modelGenerateAccesskey.setReactivateStatus("1");
						}
					}else if(pa.get().getSendMailDate() != null) {
						Long t1 = pa.get().getSendMailDate().getTime();
						Long t2 = new Date().getTime();
						int tm = (int) (t2 - t1) / (60 * 60 * 1000);
						if (tm > 72) {
							modelGenerateAccesskey.setReactivateStatus("1");
						}
					}
					final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(pa.get().getOutletCode(), dealerId);
                    if(outlet.isPresent()) {
                    	modelGenerateAccesskey.setRegionCode(outlet.get().getRegion().getRegionCode());
                    	modelGenerateAccesskey.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
                    	modelGenerateAccesskey.setDealerName(outlet.get().getOutletName());
                    	modelGenerateAccesskey.setOutletCode(outlet.get().getOutletCode());
                    	modelGenerateAccesskey.setCity(outlet.get().getCity().getCityName());
                    	modelGenerateAccesskey.setState(outlet.get().getState().getStateName());
                    	
                    }else {
                    	modelGenerateAccesskey.setRegionCode("");
                    	modelGenerateAccesskey.setParentDealer("");
                    	modelGenerateAccesskey.setDealerName("");
                    	modelGenerateAccesskey.setOutletCode("");
                    	modelGenerateAccesskey.setCity("");
                    	modelGenerateAccesskey.setState("");
                    	
                    }
					participantList.add(modelGenerateAccesskey);
                    }
					
				} if(!pa.isPresent()) {
					modelGenerateAccesskey.setAccesskey(key.getAccesskey());
					modelGenerateAccesskey.setTestStatus("Email Not Send");
					modelGenerateAccesskey.setAccesskeyStatus(key.getStatus());
					participantList.add(modelGenerateAccesskey);
				}
				
			}
			Optional<Dealer> dealer = dealerService.getById(dealerId); 
			model.addAttribute("dealer", dealer.get());
			model.addAttribute("participant", participantList);
			model.addAttribute("designationList", designationService.getDesignation());
			model.addAttribute("outletList", dealer.get().getOutlet());
			DataProccessor.setDateRange(model);
			 FilterPayload filterPayload = new FilterPayload();
			 filterPayload.setDateRange(dateRange);
	            model.addAttribute("payload", filterPayload);
		}else {
			return "redirect:login";
		}
		return "accesskey_list";
	}
	

}
