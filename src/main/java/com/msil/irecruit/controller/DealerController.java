
package com.msil.irecruit.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParentDealerService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.client.RestClientReattemp;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.payload.FilterPayload;
import com.msil.irecruit.tc.entities.ModelParticpantView;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class DealerController {

	 @Autowired
	    ParticipantService participantserviceImpl;
	    @Autowired
	    ParticipantService participantService;
	    @Autowired
	    CityService cityService;
	    @Autowired
	    RegionService regionService;
	    @Autowired
	    FSDMService fsdmservice;
	    @Autowired
	    DealerService dealerService;
	    @Autowired
	    ParentDealerService parentDealerService;
	    @Autowired
	    StateService stateService;
	    @Autowired
	    DesignationService designationService;
	    @Autowired
	    OutletService outletService;
	    @Autowired
	    InterviewScoreService interviewScoreService;
	    @Autowired
	    RestClientReattemp restClientReattemp;
	    @Autowired
		DataScienceService dataScienceService;
	    
	    @GetMapping({ "/viewParticapantProcess" })
	    private String getDealer(final HttpSession session, final Model model) {
	        final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        long dealerId = 0L;
	        if (session.getAttribute("userId") != null) {
	            dealerId = Long.parseLong(session.getAttribute("userId").toString());
	            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            final Optional<Dealer> dealer = dealerService.getById(dealerId);
	            final List<Designation> designations2 = designationService.getAll();
	            final Map<String, String> designation = designations2.stream().collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
	            final Map<String, String> SalesDesignation = designations2.stream().filter(p -> p.getCategory().equals("Sales")).collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
	           
	    		LocalDate lastStart = LocalDate.now().minusDays(30);
	    		LocalDate lastEnd = LocalDate.now();
	    	    	
	    		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	    	    String a = lastStart.format(formatter2);
	    	    String b = lastEnd.format(formatter2);
	    		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	    		
	    		Date dateFrom = null;
	    		Date dateTo = null;
	    		try {
					dateFrom=sdf1.parse(a);
					dateTo=sdf1.parse(b);
					dateTo = DataProccessor.addTimeInDate(dateTo);
				} catch (ParseException e1) {
				System.out.println("Cal........."+e1);
				}
	    			    		
			   //final List<ParticipantRegistration> participant =participantserviceImpl.getParticipantCSVInpprocess( dealerId, dateFrom, dateTo);
	            final List<ParticipantRegistration> participant = participantserviceImpl.getParticipantCSVInpprocess(dealerId);
	          
	            final List<Outlet> outlets = outletService.findByDealerId(dealerId);
                final List<Designation> designations3 = designationService.getAll();
                model.addAttribute("participantList",   getParticpant(participant,dealer,dealerId+""));
                model.addAttribute("outlets", (Object)outlets);
                model.addAttribute("designations", (Object)designations3);
                model.addAttribute("dealerId", (Object)dealerId);
                model.addAttribute("salesDesignation", (Object)SalesDesignation);
                model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
                DataProccessor.setDateRange(model);
	            return "hiring-in-process";
	        }
	        return "redirect:login";
	    }
	    
	    @PostMapping({ "/reAttemp" })
	    @ResponseBody
	    public String reAttemp(@RequestParam("accesskey") final String accesskey) {
	        final Optional<ParticipantRegistration> particpant = participantService.getParticipantByAccesskey(accesskey);
	        if (particpant.isPresent()) {
	        	int count =0;
	        	if(particpant.get().getReAtampCount() != null) {
	        	  count = particpant.get().getReAtampCount();
	        	}
	        	
	        	
	        	count++;
	            restClientReattemp.callClient(accesskey);
	            particpant.get().setTestScore("");
	            particpant.get().setTestStatus("2");
	            particpant.get().setTestCompletionDate((Date)null);
	            particpant.get().setPassFailStatus(0);
	            particpant.get().setTotalMark((String)null);
	            particpant.get().setPercentageScore("");
	            particpant.get().setFsdmFeedback((String)null);
	            particpant.get().setDocuments_status((String)null);
	            particpant.get().setFsdmApprovalStatus((String)null);
	            particpant.get().setReAtampStatus("Yes");
	            particpant.get().setModifiedDate(new Date());
	            particpant.get().setReAtampCount(count);
	            participantService.saveData((ParticipantRegistration)particpant.get());
	            sendEmailToReAttemp(particpant.get());
               try {				
					String sms =DataProccessor.getSMS("reAttemp");
					SmsUtility.sendSmsPromotional(particpant.get().getMobile(),sms,"522225","1007606977458974398");;
				}catch(Exception e) {
					System.out.println("Error......"+e);
				}
	        }
	        else {
	        }
	        return "Success";
	    }
	    
	    @PostMapping({ "/fixedInterViewDate" })
	    @ResponseBody
	    public String setInterviewDate(@RequestParam("date") final String date, @RequestParam("time") final String time, 
	    		@RequestParam("interviewAddress") final String interviewAddress, @RequestParam("accesskey") final String accesskey) {
	        String msg = "";
	        ParticipantRegistration p = null;
	        try {
	            final Date newDate = DataProccessor.parseDate(date);
	            if (newDate != null) {
	                final Optional<ParticipantRegistration> particpant = participantserviceImpl.getParticipantByAccesskey(accesskey);
	                if (particpant.isPresent()) {
	                    p = particpant.get();
	                    p.setInterviewDate(newDate);
	                    p.setInterviewTime(time);
	                    p.setInterviewAddress(interviewAddress);
	                    p.setModifiedDate(new Date());
	                }
	                if (p != null) {
	                    participantserviceImpl.saveData(p);
	                    msg = "save";
	                    sendEmailToScheduleInterview(particpant.get());
	                    try {
	                      String smsMsg = DataProccessor.getSMS("interviewSchedule");
	                       SmsUtility.sendSmsHandler(p.getMobile(), smsMsg,"MSILOT" );
	                    }catch(Exception e){
	                    	System.out.println("error...."+e);
	                    }
	                }
	            }
	        }
	        catch (Exception e) {
	            return e + "....";
	        }
	        return accesskey;
	    }
	    
	    @PostMapping({ "holdUnHoldParticpant" })
	    @ResponseBody
	    public String setOnHoldParicipant(@RequestParam("accesskey") final String accesskey, @RequestParam("status") final String status) {
	        final Optional<ParticipantRegistration> particpant = participantService.getParticipantByAccesskey(accesskey);
	        String msg = "";
	        if (particpant.isPresent()) {
	            particpant.get().setStatus(status);
	            particpant.get().setModifiedDate(new Date());
	            participantService.saveData((ParticipantRegistration)particpant.get());
	        }
	        if (status.equals("H")) {
	            msg = "Candidate Holde ";
	        }
	        if (status.equals("I")) {
	            msg = "Candidate UnHold ";
	        }
	        return msg;
	    }
	    
	    private String sendEmailToScheduleInterview(final ParticipantRegistration participant) {
	        final String subjectLine = "iRecruit- Your Job Application: Interview Notification";
	        String mailBody = DataProccessor.readFileFromResource("interviewScheduleEmail");
	        mailBody = mailBody.replace("${candidateName}", String.valueOf(participant.getFirstName()) + " " + participant.getMiddleName() + " " + participant.getLastName());
	        final Dealer dealer = dealerService.getById((long)participant.getDealerId()).get();
	        mailBody = mailBody.replace("${dealerName}", dealer.getName());
	        mailBody = mailBody.replace("${interviewDate}", DataProccessor.dateToString(participant.getInterviewDate()));
	        mailBody = mailBody.replace("${interviewTime}", participant.getInterviewTime());
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
	        final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(participant.getOutletCode(), participant.getDealerId());
	        if (outlet.isPresent()) {
	            final String location = String.valueOf(dealer.getName()) + "," + outlet.get().getState().getStateName() + ", " + outlet.get().getCity().getCityName();
	            mailBody = mailBody.replace("${location}", participant.getInterviewAddress());
	            mailBody = mailBody.replace("${fullLocation}", participant.getInterviewAddress()+","+location);
	        }
	        else {
	            System.out.println("Outlet not found");
	        }
	        final SendPayload sendP = new SendPayload();
	        sendP.setTo(participant.getEmail());
	        sendP.setSubjectLine(subjectLine);
	        sendP.setMsg(mailBody);
	        sendP.setCc(dealer.getEmail());
	        sendP.setBcc("");
	        sendP.setFrom("Armezo Solutions");
	        EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(), sendP.getMsg(), "smtp");
	        System.out.println("Sent to " + participant.getEmail());
	        return "success";
	    }
	    
	    private String sendEmailToReAttemp(final ParticipantRegistration participant) {
	        final String subjectLine = "iRecruit - Your Job Application: Registration & Assessment";
	        String mailBody = DataProccessor.readFileFromResource("reattempt");
	        mailBody = mailBody.replace("${candidateName}", String.valueOf(participant.getFirstName()) + " " + participant.getMiddleName() + " " + participant.getLastName());
	        final Dealer dealer = dealerService.getById((long)participant.getDealerId()).get();
	        mailBody = mailBody.replace("${dealerName}", dealer.getName());
	        final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(participant.getOutletCode(), participant.getDealerId());
	        String name = "";
	        name = String.valueOf(name) + participant.getFirstName();
	        if (participant.getMiddleName() != null) {
	            name = String.valueOf(name) + " " + participant.getLastName();
	        }
	        name = String.valueOf(name) + " " + participant.getLastName();
	        if (outlet.isPresent()) {
	            mailBody = mailBody.replace("${dealerShipName}", outlet.get().getOutletName());
	        }
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
	        mailBody = mailBody.replace("${candidateName}", name);
	        mailBody = mailBody.replace("${accesskey}", participant.getAccessKey());
	        mailBody = mailBody.replace("${assessment}", "http://staging.irecruit.org.in/irecruit/candidateLogin");
	        final SendPayload sendP = new SendPayload();
	        sendP.setTo(participant.getEmail());
	        sendP.setSubjectLine(subjectLine);
	        sendP.setMsg(mailBody);
	        sendP.setCc(dealer.getEmail());
	        sendP.setBcc("");
	        sendP.setFrom("Armezo Solutions");
	        try {
	            EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(), sendP.getMsg(), "smtp");
	            System.out.println("Sent to " + participant.getEmail());
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("...........Error in send mail.........");
	        }
	        System.out.println("Email Sent for Assessment Passing");
	        return "success";
	    }
	    
	    @GetMapping({ "/testing" })
	    @ResponseBody
	    public String testing() {
	        return "success";
	    }
	    
	    @PostMapping({ "/filterParticipant" })
	    public String dealerFilters(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation,@RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			List<Integer> passFStatus = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (dateFromm != null && dateFromm != "") {
					dateFrom = sdf.parse(dateFromm);
				}
				if (dateToo != null && dateToo != "") {
					dateTo = sdf.parse(dateToo);
					dateTo = DataProccessor.addTimeInDate(dateTo);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode="";
				if(candidateName==null)
					candidateName="";
				if(uniqueCode==null) {
					uniqueCode="";
				}
				if(designation==null)
					designation="";
				if(mspin==null)
					mspin=null;
				if(passFailStatus=="") {
					passFStatus.add(1);
					passFStatus.add(0);
				}else {
					System.out.println(passFailStatus);
					passFStatus.add(Integer.valueOf(passFailStatus));
				}

				
				dealerId = Long.parseLong(session.getAttribute("userId").toString());
				Optional<Dealer> dealer = dealerService.getById(dealerId);
				List<ParticipantRegistration> participantList=null;
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantFilterInpprocess(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,dealerId,dateFrom,dateTo);
				}else {
					participantList= participantserviceImpl.getParticipantByFilterData2(outletCode, candidateName, designation, mspin, passFStatus, dealerId, uniqueCode);

				}
	            //listParticipant = setDataInProcess(participantList, dealer);
				listParticipant = getParticpant(participantList,dealer,dealerId+"");
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	            if(outletCode!=null || outletCode!="") {
	            	Map<String,String> map = new HashMap<>();	            	
	            	model.addAttribute("outletCode1", outletCode);
	            }
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,"");
	            filterPayload.setDateFrom(dateFromm);
	            filterPayload.setDateTo(dateToo);
	            model.addAttribute("payload", filterPayload);
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("outlets", (Object)outlets);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "hiring-in-process";
	        }
	        return "redirect:login";
	    }
	    //Filter For Employee Master
	    @PostMapping({ "/filterMasterParticipant" })
	    public String dealerFiltersForEmployeeMaster(@RequestParam("outlet") String outletCode, @RequestParam("candidateName") String candidateName,@RequestParam("uniqueCode") String uniqueCode, 
				@RequestParam("designation") String designation,@RequestParam("mspin") String mspin, @RequestParam("passFailStatus") String passFailStatus, 
				@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session, Model model) {
	        List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	        
	        final List<Designation> designations2 = designationService.getAll();
	        final Map<String, String> finalDesignation = designations2.stream().collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
	        
	        List<Integer> passFStatus= new ArrayList<>();
			Date dateFrom=null;
			Date dateTo=null;
			Long dealerId=0L;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (dateFromm != null && dateFromm != "") {
					dateFrom = sdf.parse(dateFromm);
				}
				if (dateToo != null && dateToo != "") {
					dateTo = sdf.parse(dateToo);
					dateTo = DataProccessor.addTimeInDate(dateTo);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (session.getAttribute("userId") != null) {
				//check data 
				if(outletCode==null )
					outletCode=null;
				if(candidateName==null)
					candidateName=null;
				if(uniqueCode==null) {
					uniqueCode=null;
				}
				if(designation==null)
					designation=null;
				if(mspin==null)
					mspin=null;
				if(passFailStatus=="") {
					passFStatus.add(1);
					passFStatus.add(0);
				}else {
					passFStatus.add(Integer.valueOf(passFailStatus));
				}
				
				dealerId = Long.parseLong(session.getAttribute("userId").toString());
				Optional<Dealer> dealer = dealerService.getById(dealerId);
				List<ParticipantRegistration> participantList=null;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String fsdmApprovalStatus="2";
				if(dateFrom!=null && dateTo!=null)
				{
					participantList = participantserviceImpl.getParticipantFilterEmployee(outletCode,candidateName, designation, mspin,passFStatus,uniqueCode,dealerId,dateFrom,dateTo,fsdmApprovalStatus);
				}else {
					participantList= participantserviceImpl.getParticipantOnEmployeeMasterDealerByFilterData( outletCode,
							candidateName,  designation,  mspin,  passFStatus,  uniqueCode,
							 dealerId,  fsdmApprovalStatus);
				}
	            
				listParticipant = getParticpant(participantList,dealer,dealerId+"");
	            FilterPayload filterPayload = new FilterPayload(outletCode,candidateName,uniqueCode,designation,mspin,passFailStatus,"");
	            filterPayload.setDateFrom(dateFromm);
	            filterPayload.setDateTo(dateToo);
	            model.addAttribute("payload", filterPayload);
	            final List<Outlet> outlets = outletService.findByDealerId((long)dealerId);
	            final List<Designation> designations = designationService.getAll();
	            model.addAttribute("participantList", (Object)listParticipant);
	            model.addAttribute("outlets", (Object)outlets);
	            model.addAttribute("designations", (Object)designations);
	            model = DataProccessor.setDateRange(model);
	            model.addAttribute("dealerId", (Object)dealerId);
	            model.addAttribute("pass", DataProccessor.getPassFailStatusMap());
	            return "employeeDealer";
	        }
	        return "redirect:login";
	    }
	    
	    @PostMapping({ "/completionProcess" })
	public String checkCompletionProcessFilter(@RequestParam(required = false) final String interview,
			@RequestParam(required = false) final String prarambh, @RequestParam(required = false) final String fsdm, final HttpSession session, Model model) {
		List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
		List<Long> dealerIdList = new ArrayList<Long>();
		FilterPayload payload = new FilterPayload();
		payload.setInterview(interview);
		payload.setPraraambh(prarambh);
		payload.setFsdmApproved(fsdm);
		Integer interviewSearch = null;
		String praraambhSearch = prarambh;
		List<String> fsdmSearch=new ArrayList<String>();
		if (interview != null)
			interviewSearch = 0;
		if (prarambh != null)
			praraambhSearch = "1";
		if(fsdm!=null) {
			 fsdmSearch.add("1");
			 fsdmSearch.add("3");
			 }
		
		if (session.getAttribute("userId") != null) {
			Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			dealerIdList.add(dealerId);
			final Optional<Dealer> dealer = dealerService.getById(dealerId);
			final List<ParticipantRegistration> list = (List<ParticipantRegistration>) participantserviceImpl.findParticipantsByCompletionFilterInProcess(interviewSearch, praraambhSearch, fsdmSearch, dealerIdList);
			//listParticipant = setDataInProcess(list, dealer);
			
			listParticipant = getParticpant(list,dealer,dealerId+"");
			final List<Outlet> outlets = outletService.findByDealerId(dealerId);
			final List<Designation> designations = designationService.getAll();
			model = DataProccessor.setDateRange(model);
			model.addAttribute("participantList", (Object) listParticipant);
			model.addAttribute("outlets", (Object) outlets);
			model.addAttribute("designations", (Object) designations);
			model.addAttribute("dealerId", (Object) dealerId);
			model.addAttribute("payload", (Object) payload);
			return "hiring-in-process";
		}
		return "redirect:login";
	}
	    
	   
	    
	    public boolean getInterviewStatus(String interviewDate) {
	    	boolean check = false;
	    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
             LocalDateTime now = LocalDateTime.now();  
            String  d1= dtf.format(now);
               
                //Format of the date defined in the input String
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                //Desired format: 24 hour format: Change the pattern as per the need
                DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = null;
                String output = null;
                try{
                   //Converting the input String to Date
              	 date= df.parse(interviewDate);
              	 System.out.println("dat........."+date);
                   //Changing the format of date and storing it in String
              	 output = outputformat.format(date);
                   //Displaying the date
              	
                }catch(ParseException pe){
                   pe.printStackTrace();
                 }
                
                SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                Date d11 = sdformat.parse(d1);
                Date d22 = sdformat.parse(output);
                
                if(d22.compareTo(d11) >= 0) {
                	check = true;
                	System.out.println("if......"+output+"......"+d1);  
                 }else {
                	 System.out.println("else......"+output+"....."+d1);   
                 }
                }catch(Exception e) {
             	 System.out.println("error...."+e);  
                }
                return check;
	    }
	    
	/* get partictant */
	    public List<ModelParticpantView> getParticpant(List<ParticipantRegistration> participant,Optional<Dealer> dealer,String dealerId){
	    	 final List<Designation> designations2 = designationService.getAll();
	         final Map<String, String> designation = designations2.stream().collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
	    	 final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    	  final List<ModelParticpantView> listParticipant = new ArrayList<ModelParticpantView>();
	    	  for (final ParticipantRegistration p2 : participant) {
                  final ModelParticpantView modelParticpantView = new ModelParticpantView();
                  modelParticpantView.setParticipantName(String.valueOf(String.valueOf(p2.getFirstName())) + " " + p2.getMiddleName() + " " + p2.getLastName());
                  modelParticpantView.setAccesskey(p2.getAccessKey());
                  if (p2.getRegistration_date() != null) {
                      modelParticpantView.setDateOfRegistration(formatter.format(p2.getRegistration_date()));
                  }
                  if (p2.getTestCompletionDate() != null) {
                      modelParticpantView.setAssessment_Completion_date(formatter.format(p2.getTestCompletionDate()));
                  }
                  if (p2.getTestStatus() != null) {
                      modelParticpantView.setTestStatus(p2.getTestStatus());
                      modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p2.getInterviewScore()));
                      modelParticpantView.setPassFailStatus(p2.getPassFailStatus());
                  }
                  else {
                      modelParticpantView.setTestStatus("");
                  }
                  modelParticpantView.setInterViewScore(DataProccessor.getIntegerValue(p2.getInterviewScore()));
                  if (p2.getPassFailStatus() != 0) {
                      modelParticpantView.setPassFailStatus(p2.getPassFailStatus());
                  }
                  else {
                      modelParticpantView.setPassFailStatus(0);
                  }
                  modelParticpantView.setEmail(p2.getEmail());
                  modelParticpantView.setMobile(p2.getMobile());
                  if (p2.getTestScore() != null) {
                      modelParticpantView.setTestScore(p2.getTestScore());
                  }
                  else {
                      modelParticpantView.setTestScore("0");
                  }
                  if (p2.getPercentageScore() != null) {
                      modelParticpantView.setPercentageScore(p2.getPercentageScore());
                  }
                  else {
                      modelParticpantView.setPercentageScore("0");
                  }
                  if (p2.getTotalMark() != null) {
                      modelParticpantView.setTotalMark(p2.getTotalMark());
                  }
                  else {
                      modelParticpantView.setTotalMark("40");
                  }
                  if (p2.getInterviewDate() != null) {
                      final String regDate = formatter.format(p2.getInterviewDate());
                      final String s = String.valueOf(String.valueOf(regDate)) + ", " + p2.getInterviewTime();
                      modelParticpantView.setInterViewDate(s);   
                      
                     if( getInterviewStatus(regDate+" "+p2.getInterviewTime()) ){
                  	   modelParticpantView.setIntterviewFormStatus("1");
                     }else {
                  	   modelParticpantView.setIntterviewFormStatus("0"); 
                     }
                  } else {
                    	modelParticpantView.setIntterviewFormStatus("0"); 
                        modelParticpantView.setInterViewDate("");
                  }
                  modelParticpantView.setDesignation(p2.getDesignation());
                  if (p2.getPrarambhStatus() == null) {
                      modelParticpantView.setPrarambhStatus("N/A");
                  }
                  else {
                      modelParticpantView.setPrarambhStatus(p2.getPrarambhStatus());
                  }
                  if (p2.getFsdmApprovalStatus() == null) {
                      modelParticpantView.setFsdmApprovalStatus("");
                  }
                  else if (p2.getFsdmApprovalStatus().equals("1")) {
                      modelParticpantView.setFsdmApprovalStatus("Rejected");
                  }
                  else if (p2.getFsdmApprovalStatus().equals("2")) {
                      modelParticpantView.setFsdmApprovalStatus("Approved");
                  }
                  else if (p2.getFsdmApprovalStatus().equals("3")) {
                      modelParticpantView.setFsdmApprovalStatus("Pending");
                  }
                  if (p2.getRegStatus() != null && Integer.parseInt(p2.getRegStatus()) == 3) {
                      modelParticpantView.setRegStatus("0");
                  }
                  else {
                      modelParticpantView.setRegStatus("1");
                  }
                  if (p2.getFinalDesignation() != null) {
                      modelParticpantView.setFinalDesignation((String)designation.get(p2.getFinalDesignation()));
                      modelParticpantView.setFinalDesignationCode(p2.getFinalDesignation());
                  }
                  else {
                      modelParticpantView.setFinalDesignation("");
                      modelParticpantView.setFinalDesignationCode("");
                  }
                  modelParticpantView.setOutletCode(DataProccessor.getStringValue(p2.getOutletCode()));
                  final Optional<InterviewScore> interView = interviewScoreService.findByAccesskey(p2.getAccessKey());
                  if (interView.isPresent()) {
                      modelParticpantView.setInterViewStatus(interView.get().getStatus());
                      modelParticpantView.setInterViewPassFailStatus(interView.get().getPass_fail_status());
                  }
                  else {
                      modelParticpantView.setInterViewStatus("");
                      modelParticpantView.setInterViewPassFailStatus("");
                  }
                  if (dealer.isPresent()) {
                      modelParticpantView.setDealerName(DataProccessor.getStringValue(dealer.get().getName()));
                      modelParticpantView.setMspin(p2.getMspin());
                      modelParticpantView.setPrarambhStatus(p2.getPrarambhStatus());
                  }
                  Optional<DataScience>d = dataScienceService.findByAccesskey(p2.getAccessKey());
                  if(d.isPresent()) {
                  	modelParticpantView.setDataScienceResult(DataProccessor.getStringValue(d.get().getDataScienceResult()));
                  	modelParticpantView.setDataScienceReason(DataProccessor.getStringValue(d.get().getReason()));
                  	modelParticpantView.setDataScienceStatus(DataProccessor.getStringValue(d.get().getStatus()));
                  	modelParticpantView.setDataScienceInterViewStatus(DataProccessor.getStringValue(d.get().getInterviewStatus()));
                  	modelParticpantView.setReference(DataProccessor.getStringValue(d.get().getDataScienceReferenceId()));
                  	modelParticpantView.setPrediction(DataProccessor.getStringValue(d.get().getDataSciencePrediction()));
                  }	                    

                  final Optional<Outlet> outlet = outletService.getOutletByOutletCodeAndDealerId(p2.getOutletCode(), Long.valueOf(dealerId));
                  if (outlet.isPresent()) {
                      modelParticpantView.setRegionCode(outlet.get().getRegion().getRegionCode());
                      modelParticpantView.setParentDealer(outlet.get().getParentDealer().getParentDealerName());
                      modelParticpantView.setDealerName(outlet.get().getOutletName());
                      modelParticpantView.setOutletCode(outlet.get().getOutletCode());
                      modelParticpantView.setCity(outlet.get().getCity().getCityName());
                      modelParticpantView.setState(outlet.get().getState().getStateName());
                  }
                  else {
                      modelParticpantView.setRegionCode("");
                      modelParticpantView.setParentDealer("");
                      modelParticpantView.setDealerName("");
                      modelParticpantView.setOutletCode("");
                      modelParticpantView.setCity("");
                      modelParticpantView.setState("");
                  }
                  String fsdmReason ="";
                 if( p2.getFsdmFeedback() != null &&  !p2.getFsdmFeedback().equals("")) {
              	   fsdmReason = p2.getFsdmFeedback();
                 }
                 if(p2.getFsdmRejectionType() != null && !p2.getFsdmRejectionType().equals("")) {
              	   fsdmReason += " &#013 "+ p2.getFsdmRejectionType();  
                 }
                 if(p2.getFsdmRejectionReason() != null && !p2.getFsdmRejectionReason().equals("")) {
              	   fsdmReason += " &#013 "+ p2.getFsdmRejectionReason();    
                 }
                 if(p2.getFsdmRejectionComment() != null &&  !p2.getFsdmRejectionComment().equals("") ) {
              	   fsdmReason += " &#013 "+ p2.getFsdmRejectionComment();      
                 }
                 modelParticpantView.setFsdmReason(fsdmReason);
                 modelParticpantView.setAptitude(p2.getAptitudeScore());
                 modelParticpantView.setAttitude(p2.getAttitudeScore());
                  listParticipant.add(modelParticpantView);
                  
	    	  }
	    	  return listParticipant;
	    }
	    
	   
	    
	
}