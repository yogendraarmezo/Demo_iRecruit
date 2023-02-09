package com.msil.irecruit.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.HO;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.ServicesImpl.HOServiceImpl;

@Controller
public class ChangeController {
	@Autowired
	private FSDMService fsdmService;
	
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private HOServiceImpl hoService;
	
	@GetMapping("/showChangePasswordPage/{mspin}")
	public String showPasswordChangePage(HttpSession session, @PathVariable String mspin) {
		session.setAttribute("mspin",mspin);
		return "passwordChange";
	}
	
	@PostMapping("/changePassword")
	@ResponseBody
	public String changePasswordOfUser(HttpSession session, @RequestParam String oldPassword, 
			@RequestParam String newPassword, @RequestParam String mspin) {
		  String msg="";
		  if (session.getAttribute("userId") != null) { 
		  int id =Integer.parseInt(session.getAttribute("userId").toString());
		  String role = session.getAttribute("role").toString();
		  Optional<FSDM> fsdm = fsdmService.getFSDM(id);
		  Optional<Dealer> dealer  = dealerService.getByMspinAndPassword(mspin,oldPassword);
		  Optional<HO> ho = hoService.findById(id);
		  
		  if (role.equalsIgnoreCase("FS")) {
			  System.out.println("FS.........");
			  if(fsdm.isPresent()) {
				  fsdm.get().setPassword(newPassword);
				 // fsdmService.sa
				  fsdmService.updatePassword(id,newPassword);
				  msg="1";
			  }
			} 
		  if (role.equalsIgnoreCase("DL")) {
			 	  dealerService.changePassword(mspin,newPassword);	
			 	 msg="1";
			 }
		  if (role.equalsIgnoreCase("HO")) {
			  if(ho.isPresent()) {
				  ho.get().setPassword(newPassword);
				  hoService.saveHO(ho.get());
				 //hoService.changePassword(mspin,newPassword);
				 msg="1";
			  }
			 }
		  }else {
				 msg="0"; 
			 }
		return msg;
	}
	
	// Change Email Id
	@GetMapping("/showChangeEmailPage")
	public String showEmailChangePage(HttpSession session, @RequestParam String mspin) {
		session.setAttribute("mspin", mspin);
		return "emailChange";
	}
	
	@PostMapping("/changeEmail")
	@ResponseBody
	public String changeEmail(HttpSession session, @RequestParam String mspin, @RequestParam String oldEmail,@RequestParam String newEmail) {
		String msg="";
		if (session.getAttribute("userId") != null) {         
		            int id =Integer.parseInt(session.getAttribute("userId").toString());
				    Optional<FSDM> fsdm      = fsdmService.findByIdAndEmail(id,oldEmail);
				    Optional<Dealer> dealer  = dealerService.findByMspinAndEmail(mspin,oldEmail);
				    Optional<HO> ho          = hoService.findById(id);
				    String role = session.getAttribute("role").toString();
				    	  if (role.equalsIgnoreCase("FS")) {
				    		  if(fsdm.isPresent()) {
				    			  System.out.println("FS..........");
						        fsdmService.changeEmailById(id,newEmail);	
						        session.setAttribute("email", newEmail);
						        msg="1";
				    		  }
				    	  }
					
						if (role.equalsIgnoreCase("DL")) {
							if(dealer.isPresent()) {
					 		  dealerService.changeEmail(mspin,newEmail);	
					 		  session.setAttribute("email", newEmail);
					 		  msg="1";
							}
						}
					
						if (role.equalsIgnoreCase("HO")) {
							if(ho.isPresent()) {
								 System.out.println("Ho..........");
								 ho.get().setEmail(newEmail);
								 hoService.saveHO(ho.get());
					 		  //hoService.changeEmail(newEmail,id);
					 		  session.setAttribute("email", newEmail);
					 		  msg="1";
							}
						}
					}else {
						msg="0";	
					}
					 return msg;
	}
}
