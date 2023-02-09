/**
 * 26/09/2022
 */
package com.msil.irecruit.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.HO;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.HOService;

/**
 * @author Armezo
 * 
 * Here we are going to code about side profile controller.
 * Like Password Change etc
 *
 */

@Controller
public class SideProfileController {
	
	@Autowired
	private FSDMService fsdmService;
	
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private HOService hoService;
	
	/*	@GetMapping("/showChangePasswordPage/{mspin}")
	public String showPasswordChangePage(HttpSession session, @PathVariable String mspin) {
		session.setAttribute("mspin",mspin);
		return "passwordChange";
	}
	
	 @PostMapping("/changePassword") 
	public String changePasswordOfUser(HttpSession session, @RequestParam String oldPassword, @RequestParam String newPassword,
												@RequestParam String confirmPassword, @RequestParam String mspin) {
		// FSDM Password Change
		Optional<FSDM> fsdm = fsdmService.getByMspinAadPassword(mspin,newPassword);
		  if(fsdm.isPresent()) {
			 // if(newPassword.equals(confirmPassword) && newPassword.length()>8) {
				  fsdmService.changePassword(mspin,confirmPassword);		  
			  	}
			 // Dealer Password Change
			 Optional<Dealer> dealer  = dealerService.getByMspinAndPassword(mspin,oldPassword);
			 if(dealer.isPresent()) {
			 		dealerService.changePassword(mspin,newPassword);	
			  		}
			 // HO Password Change
			 Optional<HO> ho = hoService.findHOByMspin(mspin);
			 if(ho.isPresent()) {
				 hoService.changePassword(mspin,newPassword);
			 }
		return "redirect:login";
	}
	
	// Change Email Id
	@GetMapping("/showChangeEmailPage")
	public String showEmailChangePage(HttpSession session, @RequestParam String mspin) {
		session.setAttribute("mspin", mspin);
		return "emailChange";
	}
	//next steps to change email
	 @PostMapping("/changeEmail") 
	public String changeEmail(HttpSession session, @RequestParam String mspin, @RequestParam String email) {
		// FSDM Password Change
				Optional<FSDM> fsdm = fsdmService.findByMspin(mspin);
				  if(fsdm.isPresent()) {
					 // if(newPassword.equals(confirmPassword) && newPassword.length()>8) {
						  fsdmService.changeEmail(mspin,email);		  
					  	}
					 // Dealer Password Change
					 Optional<Dealer> dealer  = dealerService.findByMspin(mspin);
					 if(dealer.isPresent()) {
					 		dealerService.changeEmail(mspin,email);	
					  		}
					 Optional<HO> ho = hoService.findHOByMspin(mspin);
					 if(ho.isPresent()) {
					 		hoService.changeEmail(mspin,email);	
					  		}
					 return "redirect:login";
	}
*/
}
