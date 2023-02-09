package com.msil.irecruit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.AccessKeyMaster;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.HO;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.UserLoginLog;
import com.msil.irecruit.Services.AccessKeyMasterService;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.HOService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.Services.UserLoginLogService;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.service.impl.ListDmsServiceImpl;
import com.msil.irecruit.tc.entities.ModelProfile;
import com.msil.irecruit.utils.OnScreenToggleUtil;


@Controller
public class ContollerLogin {

	 @Autowired
	 private DealerService dealerService;
	 @Autowired 
	 private FSDMService fSDMService;
	 @Autowired
	 ParticipantService participantService;
	 @Autowired
	 AccessKeyMasterService accessKeyMasterService;
	 @Autowired
	 CityService cityService;
	 @Autowired
	 StateService stateService;
	 @Autowired
	 InterviewScoreService interviewScoreService;
	 @Autowired
	 UserLoginLogService userLoginLogService;
	 @Autowired
	 OutletService outletService;	
	 @Autowired
	 private HOService hoService;
	 @Autowired
	private ListDmsServiceImpl listDmsServiceImpl;
	// @Autowired
	// private OnScreenToggleUtil toggleUtil;
	 
	@Value("${Ap.assessmentURL}")
	private String assessmentURL;
	 
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	//@PostMapping("/loginPro")
	@RequestMapping(value = "/loginPro", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPro(@RequestParam("user")String user,@RequestParam("password")String password,
			HttpSession session) {
		String role="";
		ModelProfile profile = new ModelProfile();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Optional<UserLoginLog>loginLog= userLoginLogService.getloginLog(user);
		if(loginLog.isPresent()) {
			if(loginLog.get().getLastLogin() != null) {
				session.setAttribute("lastLogin", formatter.format(loginLog.get().getLastLogin()));
			}else {
				session.setAttribute("lastLogin", formatter.format(new Date()));	
			}
			loginLog.get().setLastLogin(new Date());
			userLoginLogService.updateLoginLog(loginLog.get());
		}else {
			//toggleUtil.saveToggleForFirstTime(session);
			UserLoginLog log = new UserLoginLog();
			log.setMspin(user);
			log.setLastLogin(new Date());
			userLoginLogService.updateLoginLog(log);
		}
		System.out.println("Username111 : "+user+", Password : "+password);
		
		  Optional<FSDM>fsdm = fSDMService.getByMspinAadPassword(user,password);
		  if(fsdm.isPresent()) { 
			    fsdm.get().getRegion().get(0).getRegionCode();
			    session.setAttribute("role", "FS");
				session.setAttribute("userId", fsdm.get().getId());
				session.setAttribute("email", fsdm.get().getEmail());
				session.setAttribute("name", fsdm.get().getName());
				session.setAttribute("mspin", fsdm.get().getMspin());
			// return "redirect:viewParticapant";  
				return "redirect:analytics";

			///  return "redirect:viewParticapant";  
			  }else {
				  System.out.println("else............."); 
			  }
		 
		Optional<Dealer>dealer  = dealerService.getByMspinAndPassword(user,password);
		if(dealer.isPresent()) {
			if(dealer.get().getStatus() != null && dealer.get().getStatus().equals(false)) {
				session.setAttribute("msg", "D");
				return "redirect:login";
			}
			if (dealer.get().getEmail() != null  && !dealer.get().getEmail().equals("") && dealer.get().getEmail().length()>0) {
			session.setAttribute("mspin", dealer.get().getMspin());
			List<Outlet>listOutlet = outletService.findByDealerId(dealer.get().getId());
			if(listOutlet.size()>0) {
				session.setAttribute("name", listOutlet.get(0).getOutletName());	
			}
			session.setAttribute("dealerName", dealer.get().getName());
			session.setAttribute("city", dealer.get().getOutlet().get(0).getCity().getCityName());
			session.setAttribute("state", dealer.get().getOutlet().get(0).getState().getStateName());
			session.setAttribute("region", dealer.get().getOutlet().get(0).getRegion().getRegionCode());
			session.setAttribute("email", dealer.get().getEmail());
			session.setAttribute("role", "DL");
			session.setAttribute("userId", dealer.get().getId());
	       /// return "redirect:generateAccesskey"; 
			return "redirect:analytics";
			}else {
				session.setAttribute("user", user);
				session.setAttribute("password", password);
				session.setAttribute("mspin", dealer.get().getMspin());
				return "addEmailAddress";
			}
		}
		Optional<HO>ho  = hoService.getByMspinAndPassword(user,password);
		if(ho.isPresent()) {
			if (ho.get().getEmail() != null  && ho.get().getEmail()!="") {
			session.setAttribute("mspin", ho.get().getMspin());
			session.setAttribute("name", ho.get().getName());
			session.setAttribute("email", ho.get().getEmail());
			session.setAttribute("city", "India");  // dynamic value will be here
			session.setAttribute("state", "India");
			session.setAttribute("region", "India");
			session.setAttribute("role", "HO");
			session.setAttribute("userId", ho.get().getId());
	      // return "redirect:viewAllParticapants"; 
			return "redirect:analytics";
			}else {
				session.setAttribute("user", user);
				session.setAttribute("password", password);
				session.setAttribute("mspin", ho.get().getMspin());
				return "addEmailAddress";
			}
		}
		else {
		session.setAttribute("msg", "Invalid Login");
		return "redirect:login";
		}
		//return "redirect:login";
	}
	// Add email handler
	@PostMapping("/addEmailAddress")
	public String addEmailAddressToAdminLogin(HttpSession session, @RequestParam String email, @RequestParam String user, 
										@RequestParam String password, @RequestParam String mspin ) {
		Optional<Dealer>dealer  = dealerService.getByMspinAndPassword(user,password);
		if(dealer.isPresent()) {
			dealerService.addEmailAddress(dealer.get().getId(), email);
		}else {
			session.setAttribute("msgEmail", "I");
		}
		Optional<FSDM>fsdm = fSDMService.getByMspinAadPassword(user,password);
		  if(fsdm.isPresent()) { 
			  fSDMService.addEmailAddressAfterLogin(fsdm.get().getMspin(),email);
		  }
		  
		  Optional<HO> ho = hoService.findHOByMspin(mspin);
		  if(ho.isPresent()) {
			  hoService.changeEmail(mspin, email);
		  }
		  
		//loginPro(user, password, session);
		return "redirect:loginPro?user="+user+"&password="+password;
	//  ?user=mayank&pass=pass
	}
	
	@GetMapping("/candidateLogin")
	public String candidateLogin(Model model) {
		getCapcha(model, "");
		return "candidateLogin";
	}
	
	
	
	@PostMapping("/candidateLoginPro")
	public String testLoginPro(@RequestParam("txtAkey") String accesskey,
			@RequestParam("textresult") String inResult, @RequestParam("calculationresult") String fResult,
			Model model, HttpSession session) {
		
		
		if (!accesskey.equals("")) {
			Optional<AccessKeyMaster> key = accessKeyMasterService.getAccesskey(accesskey);
			if (key.isPresent()) {
				if (key.get().getStatus().equals("B")) {
					session.setAttribute("msg", "B");
					return "redirect:candidateLogin";
				}
			}else {
				session.setAttribute("msg", "I");
				return "redirect:candidateLogin";
			}
		}
		
		Optional<ParticipantRegistration>participant = participantService.getParticipantByAccesskey(accesskey);
		if (participant.isPresent()) {
			
            if((participant.get().getSendMailDate() != null) && (participant.get().getTestStatus() == null || participant.get().getTestStatus() != null)) {
            	int tm=0;
				Long t1 = participant.get().getSendMailDate().getTime();
				Long t2 = new Date().getTime();
				if(participant.get().getReactivationDate() != null) {
					t1 = participant.get().getReactivationDate().getTime();
					tm = (int) (t2 - t1) / (60 * 60 * 1000);
					System.out.println("re time..1........"+tm);
					if (tm >= 48 ) {
						System.out.println("re time..2........"+tm);
						session.setAttribute("msg", "E");
						return "redirect:candidateLogin";
					}
					System.out.println("re time..3........"+tm);
				}else {
					tm = (int) (t2 - t1) / (60 * 60 * 1000);
					System.out.println("reg time.........."+tm);
					if (tm >= 72 ) {
						session.setAttribute("msg", "E");
						return "redirect:candidateLogin";
					}
				}
			}
			
			if(participant.get().getTestStatus() == null ){
				model.addAttribute("participant", participant.get());
				model.addAttribute("cityList", cityService.getAllCity());
				model.addAttribute("stateList", stateService.getAllState());
				return "redirect:instruction?accesskey="+accesskey;		
			}else if(participant.get().getDocuments_status()!= null) {
				session.setAttribute("msg", "D");
				return "redirect:candidateLogin";	
			}else if(Integer.parseInt(participant.get().getTestStatus()) == 1){
				return "redirect:"+assessmentURL+"assess/alloginpro?accesskey="+participant.get().getAccessKey();
				//return "redirect:http://localhost:8081/ExideIndustries/assess/alloginpro?accesskey="+participant.get().getAccessKey();
			}else if(Integer.parseInt(participant.get().getTestStatus()) == 3) {
				Optional<InterviewScore> score = interviewScoreService.findByAccesskey(accesskey);
				if(score.isPresent()) {
					if(score.get().getStatus().equals("final")) {
						//return "redirect:uploadCandidateDocument?accessKey="+accesskey;
						
						return "redirect:instructionUploadFile?accesskey="+accesskey;
					}
				}
				session.setAttribute("msg", "C");
				return "redirect:candidateLogin";	
			}else if(Integer.parseInt(participant.get().getTestStatus()) == 2) {
				participant.get().setTestStatus("1");
				participantService.saveData(participant.get());
				//return "redirect:http://localhost:8081/ExideIndustries/assess/IRCreg?accesskey="+participant.get().getAccessKey();
				return "redirect:"+assessmentURL+"assess/IRCreg?accesskey="+participant.get().getAccessKey();
			}
			
			
			
		}
		
		
		
		return "redirect:candidateLogin";
	}
	
	
	@GetMapping("/instruction")
	public String getInstruction(@RequestParam("accesskey") String accesskey,Model model) {
		model.addAttribute("accesskey", accesskey);
		return "Instructions";
	}
	
	@PostMapping("/instructionPro")
	public String getInstructionPro(@RequestParam("accesskey") String accesskey,Model model) {
		model.addAttribute("accesskey", accesskey);
		Optional<ParticipantRegistration>participant = participantService.getParticipantByAccesskey(accesskey);
		if (participant.isPresent()) {
			if(participant.get().getTestStatus() == null ){
				model.addAttribute("participant", participant.get());
				model.addAttribute("cityList", cityService.getAllCity());
				model.addAttribute("stateList", stateService.getAllState());
				model.addAttribute("gender", listDmsServiceImpl.getByListName("GENDER"));
				
				List<ListDms>listQuali = listDmsServiceImpl.getByListName("EDUCATION");
				List<ListDms>ListDms = new ArrayList<>();
				for(ListDms l:listQuali) {
					if(l.getListCode().equals("HSC") || l.getListCode().equals("SSC")) {
						continue;
					}
					ListDms.add(l);
				}
				model.addAttribute("qualification", ListDms);
				model.addAttribute("language", (Object)this.listDmsServiceImpl.getByListName("LANGUAGE"));
				model.addAttribute("title", (Object)this.listDmsServiceImpl.getByListName("TITLE_CD"));
				String name="";
				name += participant.get().getFirstName();
				if(participant.get().getMiddleName() != null && !participant.get().getMiddleName().equals("")) {
					name += " "+participant.get().getMiddleName();
				}
				name += " "+participant.get().getLastName();
				model.addAttribute("candidateFirsName", participant.get().getFirstName());
				model.addAttribute("CandidateLastName", participant.get().getLastName());
				return "Participant Registration";
				
			}
		}
		return "login";
	}
	
	
	@GetMapping("/instructionUploadFile")
	public String getInstructionUploadFile(@RequestParam("accesskey") String accesskey,Model model) {
		model.addAttribute("accesskey", accesskey);
		/* redirect:candidateLogin   registration-final*/
		
		//return "redirect:uploadCandidateDocument?accessKey="+accesskey;
		return "uploadocumentInstruction";
	}
	
	@GetMapping("/instructionUploadFilePro")
	public String getInstructionUploadFilePro(@RequestParam("accesskey") String accesskey,Model model) {
		model.addAttribute("accesskey", accesskey);
		return "redirect:uploadCandidateDocument?accessKey="+accesskey;
	}
	
	private void getCapcha(Model model, String resutl) {
		List<Object> list = getCaptcha();
		model.addAttribute("r1", list.get(0));
		model.addAttribute("r2", list.get(1));
		model.addAttribute("r3", list.get(2));
		model.addAttribute("r4", list.get(3));
		model.addAttribute("msg", resutl);
	}

	public static ArrayList<Object> getCaptcha() {
		final ArrayList<Object> captcha_details = new ArrayList<Object>();
		final Random generator = new Random();
		final int op1 = generator.nextInt(9);
		final int op2 = generator.nextInt(9);
		final int operator = generator.nextInt(3);
		int result = 0;
		final char[] operator_array = { '+', '-', '*' };
		switch (operator) {
		case 0: {
			result = op1 + op2;
			break;
		}
		case 1: {
			if (op1 >= op2) {
				result = op1 - op2;
				break;
			}
			if (op2 > op1) {
				result = op2 - op1;
				break;
			}
			break;
		}
		case 2: {
			result = op1 * op2;
			break;
		}
		}
		if (op1 >= op2) {
			captcha_details.add(op1);
			captcha_details.add(new StringBuilder().append(operator_array[operator]).toString());
			captcha_details.add(op2);
			captcha_details.add(result);
		} else {
			captcha_details.add(op2);
			captcha_details.add(new StringBuilder().append(operator_array[operator]).toString());
			captcha_details.add(op1);
			captcha_details.add(result);
		}
		return captcha_details;
	}
	
	@PostMapping("/logout")
	public String loginPro(HttpSession session) {
		session.invalidate();
		return "redirect:login";
		
	}
	
	@GetMapping("/termsCondition")
	public String getTermsCondition() {
		return "terms";
		
	}
	
	@GetMapping("/privacy")
	public String getPrivacy() {
		return "privacy";
		
	}
	
	@GetMapping("/session-expired")
	public String gettemp(@RequestParam("param") String param,Model model) {
		if(param != null && !param.equals("") ) {
		model.addAttribute("param", param);
		}else {
			model.addAttribute("param", "");
		}
		return "session-expired";
		
	}
	
	@PostMapping("/deactivateDealer")
	@ResponseBody
	public String deactivateDealer(@RequestParam("mspin") String mspin) {	
		if(mspin != null && !mspin.equals("")) {
			Optional<Dealer>dealer  = dealerService.findByMspin(mspin);
			dealerService.deactivateDealer(false,new Date(),dealer.get().getId());
		}
		
		return "success";
		
	}
	
	@PostMapping("/activateDealer")
	@ResponseBody
	public String activateDealer(@RequestParam("mspin") String mspin) {	
		if(mspin != null && !mspin.equals("")) {
			Optional<Dealer>dealer  = dealerService.findByMspin(mspin);
			dealerService.deactivateDealer(true,new Date(),dealer.get().getId());
		}
		
		return "success";
		
	}
	
	
}




