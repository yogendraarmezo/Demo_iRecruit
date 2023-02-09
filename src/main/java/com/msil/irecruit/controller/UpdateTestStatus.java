package com.msil.irecruit.controller;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.client.CallQuestionReport;
import com.msil.irecruit.dms.controller.DataSciencComtroller;
import com.msil.irecruit.dms.entities.DataScienceRequest;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.service.impl.ListDmsServiceImpl;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class UpdateTestStatus {

	    @Autowired
	    private ParticipantServiceImpl participantserviceImpl;
	    @Autowired
	    private DealerService dealerService;
	    @Autowired
	    private OutletService outletService;
	    @Autowired
	    private DataScienceService dataScienceService;
	    @Autowired
	    private DataSciencComtroller dataSciencComtroller;
	    @Autowired
		private ListDmsServiceImpl listDmsServiceImpl;
	    @Autowired
	    private CallQuestionReport callQuestionReport;
	    
	    @PostMapping({ "/updateTestStatus" })
	    @ResponseBody
	    public String updateAptitute(@RequestParam("accesskey") final String accesskey, @RequestParam("testScore") final double testScore,
	    		@RequestParam("testStatus") final int testStatus,@RequestParam("totalMark") final double totalMark,
	    		@RequestParam("section_1") final double section_1, @RequestParam("section_2") final double section_2) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantserviceImpl.findByAccesskey(accesskey);
	        if (participant.isPresent()) {
	            participant.get().setTestStatus("3");
	            participant.get().setTestScore(new StringBuilder(String.valueOf(testScore)).toString());
	            participant.get().setTotalMark(new StringBuilder(String.valueOf(totalMark)).toString());
	            double passingPer = 0.0;
	            if (testScore > 0.0) {
	                passingPer = testScore / totalMark * 100.0;
	            }
	            double percentages=doubleRoundHalfUpWith2DecimalPlaces(0, passingPer);
	        	int percentage = (int)percentages;
	            participant.get().setPercentageScore(new StringBuilder(String.valueOf(percentage)).toString());
	            participant.get().setPassFailStatus(testStatus);
	            participant.get().setTestCompletionDate(new Date());
	            participant.get().setAptitudeScore(Integer.valueOf((int)section_1));
	            participant.get().setAttitudeScore(Integer.valueOf((int)section_2));
	            participant.get().setPsychometricScore(Integer.valueOf((int)testScore));
	            System.out.println("test status..........." + testStatus);
	            try {
	                if (testStatus == 1) {
	                    final Optional<DataScience> dataScience = (Optional<DataScience>)this.dataScienceService.findByAccesskey(accesskey);
	                    if (dataScience.isPresent()) {
	                        final DataScienceRequest reqset = new DataScienceRequest();
	                        reqset.setAge(DataProccessor.parseInt(dataScience.get().getAge()));
	                        reqset.setExperience(DataProccessor.parseInt(dataScience.get().getTotal()));
	                        reqset.setGender(dataScience.get().getGender());
	                        reqset.setHasDriverLicense(DataProccessor.changeYesNo(dataScience.get().getDL()));
	                        reqset.setHasTwoWheeler(DataProccessor.changeYesNo(dataScience.get().getOwnTwoWheeler()));
	                        reqset.setHaveAutoExperience(DataProccessor.changeYesNo(dataScience.get().getAutoIndustryExperience()));
	                        reqset.setHighestQualification(DataProccessor.getStringValue(dataScience.get().getHighestQualification()));
	                        reqset.setIsMSDrivingCertified(DataProccessor.changeYesNo(dataScience.get().getMdsCertified()));
	                        reqset.setKnowsDriving(DataProccessor.changeYesNo(dataScience.get().getKnowDriving()));
	                        String name = "";
	                        name =  dataScience.get().getFirstName();
	                        if (dataScience.get().getMiddleName() != null && !dataScience.get().getMiddleName().equals("")) {
	                            name +=" " + dataScience.get().getMiddleName();
	                        }
	                        name += " " + dataScience.get().getLastName();
	                        reqset.setName(name);
	                        System.out.println("Cal........"+dataScience.get().getOldMspin());
	                        if( dataScience.get().getOldMspin() != null && !dataScience.get().getOldMspin().equals("")) {
	                        reqset.setOldMSPIN(DataProccessor.parseInt(dataScience.get().getOldMspin()));
	                        }else {
	                        	 reqset.setOldMSPIN(0);
	                        }
	                        reqset.setPreviouslyWorked(DataProccessor.changeYesNo(dataScience.get().getWorkedWithMSILBefore()));
	                        Optional<ListDms> qua1 = listDmsServiceImpl.getListByListCode(dataScience.get().getPrimaryLanguage());
	                        if(qua1.isPresent()) {
	                        reqset.setPrimaryLanguage(qua1.get().getListDesc());
	                        }else {
	                        	 reqset.setPrimaryLanguage("");	
	                        }
	                        Optional<ListDms> qua2 = listDmsServiceImpl.getListByListCode(dataScience.get().getSecondaryLanguage());
	                        if(qua2.isPresent()) {
		                        reqset.setSecondaryLanguage(qua2.get().getListDesc());
		                        }else {
		                        	 reqset.setSecondaryLanguage("");	
		                        }
	                        reqset.setProfile(dataScience.get().getProfile());
	                        reqset.setResidenceOf(DataProccessor.getStringValue(dataScience.get().getResidenceOf()));
	                        if (dataScience.get().getMsilExp() != null && !dataScience.get().getMsilExp().equals("")) {
	                            reqset.setTotalMSILExperience(DataProccessor.parseInt(dataScience.get().getMsilExp()));
	                        }
	                        else {
	                            reqset.setTotalMSILExperience(0);
	                        }
	                        System.out.println("Cal..........."+reqset);
	                        this.dataSciencComtroller.getpredictedProductivity(reqset, accesskey);
	                        System.out.println("api called............");
	                    }
	                }
	            }
	            catch (Exception e) {
	                System.out.println("error..cal..sp." + e);
	            }
	            participant.get().setModifiedDate(new Date());;
	            participantserviceImpl.saveData(participant.get());
	            sendEmail(participant.get(), testStatus);
	            callQuestionReport.callClient(accesskey);
	        }
	        return "Success";
	    }
	    
	    public static double doubleRoundHalfUpWith2DecimalPlaces(int decimalPlaces, double doubleValue){
			BigDecimal bd = new BigDecimal(doubleValue);

			// setScale is immutable
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return bd.doubleValue();
		}
	    
	    @PostMapping({ "/getDealerName" })
	    @ResponseBody
	    public Map<String, String> getDealerName(@RequestParam("accesskey") final String accesskey) {
	        final Optional<ParticipantRegistration> participant = (Optional<ParticipantRegistration>)this.participantserviceImpl.findByAccesskey(accesskey);
	        final Map<String, String> map = new HashMap<String, String>();
	        if (participant.isPresent()) {
	            final Optional<Outlet> outlet = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(participant.get().getOutletCode(), participant.get().getDealerId());
	            if (outlet.isPresent()) {
	                map.put("dealerName", outlet.get().getDealer().getName());
	                map.put("dealerShip", outlet.get().getOutletName());
	                
	                String name = participant.get().getFirstName();
	                if (participant.get().getMiddleName() != null && !participant.get().getMiddleName().equals("")) {
	                    name = String.valueOf(String.valueOf(name)) + " " + participant.get().getMiddleName();
	                }
	                name = String.valueOf(String.valueOf(name)) + " " + participant.get().getLastName();
	                map.put("name", name);
	                map.put("message", "1");
	            }
	            else {
	                map.put("message", "0");
	            }
	        }
	        else {
	            map.put("message", "0");
	        }
	        return map;
	    }
	    
	    private String sendEmail(final ParticipantRegistration participant, final int stattus) {
	        String subjectLine = "iRecruit - Your Job Application: Thank You";
	        if (stattus == 0) {
	            subjectLine = "iRecruit - Your Job Application: Thank You";
	        }
	        String mailBody = DataProccessor.readFileFromResource("passEmail");
	        if (stattus == 0) {
	            mailBody = DataProccessor.readFileFromResource("failOrReattemptEmail");
	        }
	        String name = "";
	        name = String.valueOf(String.valueOf(name)) + participant.getFirstName();
	        if (participant.getMiddleName() != null) {
	            name +=  " " + participant.getMiddleName();
	        }
	        name += " " + participant.getLastName();
	        mailBody = mailBody.replace("${candidateName}", name);
	        final Dealer dealer = this.dealerService.getById((long)participant.getDealerId()).get();
	        if (dealer != null) {
	        	if(dealer.getName() != null && !dealer.getName().equals("")) {
	            mailBody = mailBody.replace("${dealerName}", dealer.getName());
	        	}else {
	        		  mailBody = mailBody.replace("${dealerName}", "");	
	        	}
	        	if(dealer.getMobile() != null && !dealer.getMobile().equals("")) {
	            mailBody = mailBody.replace("${mobile}", dealer.getMobile());
	        	}else {
	        		 mailBody = mailBody.replace("${mobile}", "");	
	        	}
	        	if(dealer.getEmail() != null && !dealer.getEmail().equals("")) {
	            mailBody = mailBody.replace("${email}", dealer.getEmail());
	        	}else {
	        		 mailBody = mailBody.replace("${email}", "");
	        	}
	        }
	        final Optional<Outlet> outlet = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(participant.getOutletCode(), participant.getDealerId());
	        if (outlet.isPresent()) {
	            mailBody = mailBody.replace("${dealershipName}", outlet.get().getOutletName());
	        }
	        final SendPayload sendP = new SendPayload();
	        sendP.setTo(participant.getEmail());
	        sendP.setSubjectLine(subjectLine);
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
	        System.out.println("Email Sent for Assessment Passing");
	        return "success";
	    }
	
}
