package com.msil.irecruit.scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.FSDMNotification;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMNotificationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.email.util.EmailUtility;

@Service
public class FSDMScheduled {
	
	@Autowired
	private FSDMNotificationService fsdmMNotificationService;
	@Autowired
	FSDMService fsdmService;
	@Autowired
	OutletService outletService;
	@Autowired
	ParticipantServiceImpl participantserviceImpl;
	@Autowired
	private DealerService dealerService;
	@Autowired
	DesignationService designationService;
	
	
	//@Scheduled(cron="0 00 09 * * *")
	//@Scheduled(cron="0 35 16 * * *")
	@Scheduled(cron="0 00 09 * * *")
	public void sendNotification() {
		
		List<FSDM> fsdmlist = fsdmService.getAllFSDM();
		  final List<Designation> designations2 = designationService.getAll();
          final Map<String, String> designation = designations2.stream().collect(Collectors.toMap((Function<? super Designation, ? extends String>)Designation::getDesignationCode, (Function<? super Designation, ? extends String>)Designation::getDesignationName));
		for(FSDM f : fsdmlist) {
			
			List<FSDMNotification> notificationList=  fsdmMNotificationService.getByFSDMId(f.getId());
			String table1="";
			String table2="";
			if(notificationList.size()>0) {
				  table1  ="<table border='1'><tr><td>Dealer Name</td><td>Dealer Code</td><td>Registration Date</td><td>Candidate Name</td><td>Designation</td></tr>";
			  for(FSDMNotification noti : notificationList) {
				  table1 += "<tr><td>"+ noti.getDealerName()+"</td>"
				  		+ "<td>"+noti.getOutletCode()+"</td>"
						+ "<td>"+noti.getRegistraionDate()+"</td>"
						+ "<td>"+noti.getCandidateName()+"</td>"
				        + "<td>"+noti.getDesignation()+"</td></tr>";
			  }
			  table1 += "</table>";
			}
			List<Long>list = new ArrayList<>();
			List<Region> regionList = f.getRegion();
			for(Region r: regionList) {
				List<Outlet> outletList=	outletService.getOutletByRegion(r.getId());
				for(Outlet outlet : outletList){
				    list.add(outlet.getDealer().getId());
				}
				
			}
			  final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<ParticipantRegistration> can= participantserviceImpl.getParticipantForNotification(list,"2","3");
			if(can.size()>0) {
				  table2  ="<table border='1'><tr><td>Dealer Name</td><td>Dealer Code</td><td>Registration Date</td><td>Candidate Name</td><td>Designation</td></tr>";
			for(ParticipantRegistration c: can) {
				Optional<Dealer>dealer = dealerService.getById(c.getDealerId());
				String name="";
				 name  += c.getFirstName();
				  if(c.getMiddleName() != null) {
					  name += c.getMiddleName();				
				  }
				  name += c.getLastName();
				if(dealer.isPresent()) {
					String outletCode="",outletName="",region="";
					 for(Outlet o :dealer.get().getOutlet()) {
						 if(c.getOutletCode().equals(o.getOutletCode())) {
							 outletCode = o.getOutletCode();
							 outletName = o.getOutletName();
							 region     = o.getRegion().getRegionCode();
						 }
					 }
					 table2 += "<tr><td>"+outletName +"</td>"
					 		+ "<td>"+ outletCode+"</td>"
					 		+ "<td>"+ formatter.format(c.getRegistration_date())+"</td>"
					 		+ "<td>"+name +"</td>"
					 		
					 		+ "<td>"+designation.get(c.getFinalDesignation())+"</td>"
					 		+ "</tr>";	
				}
			}
			table2 += "</table>";
			}
			
			if(table1.length()>0 || table2.length()>0) {
				String msg ="";
				msg = "<font style='font-family:verdana;font-size:12px;font-weight:normal;color:black;'> ";
				msg += " Dear "+f.getName()+"<br><br>";
				if(table1.length()>0) {
				msg += "Please find below the details of ";
				msg += "Candidate(s) who has/have completed ";
				msg += "recruitment process with Dealer(s) and ";
				msg +="are pending for your approval.<br> Kindly visit ";
				msg +="iRecruit portal to view and approve ";
				msg +="candidate for MSPIN generation.<br><br>";
				msg +="<b>Pending for FSDM Approval Cases:</b>";
					msg +=table1;
				}
				if(table2.length()>0) {
				msg +="<br><br><b>Pending Praarambh Cases:</b><br>";
				msg +="Please find below the details of ";
				msg +="Candidate(s) who have not completed ";
				msg +="Praarambh Induction Program in iLearn ";
				msg +="App. Please sensitize your Dealer accordingly.<br>"	;	
					msg +=table2;
				}
				msg +="<br><b>iRecruit Team</b><br>";
				msg +="This is an auto-generated system email. Please do not reply to this email as it will not be received.";
				LocalDate lastEnd = LocalDate.now();
				DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/YYYY");
				lastEnd.format(formatter2);
				sendEmail(f.getEmail(),"iRecruit – iRecruit: Daily Notification "+lastEnd.format(formatter2),msg);
			}
		}
		
	}
	
	
	public void sendEmail(String to, String sbject, String msg) {
			EmailUtility.sendMail(to, "Armezo Solutions", "", "", sbject, msg, "smtp");
	}

}
