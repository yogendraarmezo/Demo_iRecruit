package com.msil.irecruit.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.email.util.EmailUtility;
import com.msil.irecruit.email.util.SendPayload;
import com.msil.irecruit.email.util.SmsUtility;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class InterviewScoreControlle {
	
	@Value("${pdf.exeUrl}")
	private String exeUrl;
	
	@Value("${pdf.downloadPdfFile}")
	private String downloadPath; 
	
	

	@Autowired
	InterviewScoreService interviewScoreService;
	@Autowired
	ParticipantServiceImpl participantService;
	@Autowired
	private DealerService dealerService;

	@GetMapping("interviewForm")
	public String interview(@RequestParam("accesskey") String accesskey, Model model,HttpSession session) {
		if (session.getAttribute("userId") != null) {
		Optional<ParticipantRegistration> particpant = participantService.getParticipantByAccesskey(accesskey);
		Optional<InterviewScore> score = interviewScoreService.findByAccesskey(accesskey);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if(particpant.isPresent()) {
			model.addAttribute("designation", particpant.get().getDesignation());	
			model.addAttribute("date",formatter.format( particpant.get().getInterviewDate()));	
			model.addAttribute("accesskey", particpant.get().getAccessKey());
			model.addAttribute("photograph", particpant.get().getPhotograph());
			if(particpant.get().getDocuments_status() == null) {
			   model.addAttribute("editStatus", 1);
			}
			
			if(particpant.get().getRegStatus() == null || particpant.get().getRegStatus() != "3" ) {
				   model.addAttribute("editStatus", 1);
		    }
			
			String name="";
			name= particpant.get().getFirstName();
			
			if(particpant.get().getMiddleName() != null ) {
				name = 	name+" "+particpant.get().getMiddleName();
			}
			if(particpant.get().getLastName() != null ) {
				name = 	name+" "+particpant.get().getLastName();
			}
			model.addAttribute("name", name);	
		}
		if (score.isPresent()) {
			model.addAttribute("score", score.get());
		}else {
			model.addAttribute("score", new InterviewScore());	
		}
		}else {
			return "redirect:login";
		}
		return "interviewForm";
	}

	@GetMapping("interviewSampmePDF")
	public String interviewSampmePDF() {
		return "interviewForm";
	}
	@PostMapping("/interview")
	@ResponseBody
	public String saveInterviewScore(@RequestParam("name_a") String name_a, @RequestParam("name_b") String name_b,
			@RequestParam("name_c") String name_c, @RequestParam("designation_a") String designation_a,
			@RequestParam("designation_b") String designation_b, @RequestParam("designation_c") String designation_c,
			@RequestParam("mobile_a") String mobile_a, @RequestParam("mobile_b") String mobile_b,
			@RequestParam("mobile_c") String mobile_c, @RequestParam("clarity_a") String clarity_a,
			@RequestParam("clarity_b") String clarity_b, @RequestParam("clarity_c") String clarity_c,
			@RequestParam("presentability_a") String presentability_a,@RequestParam("presentability_b") String presentability_b,
			@RequestParam("presentability_c") String presentability_c, @RequestParam("attitude_a") String attitude_a,
			@RequestParam("attitude_b") String attitude_b, @RequestParam("attitude_c") String attitude_c,
			@RequestParam("situation_a") String situation_a, @RequestParam("situation_b") String situation_b,
			@RequestParam("situation_c") String situation_c, @RequestParam("total_a") String total_a,
			@RequestParam("total_b") String total_b, @RequestParam("total_c") String total_c,
			@RequestParam("total_avt") String total_avt, @RequestParam("accesskey") String accesskey,
			@RequestParam("status") String status,@RequestParam("pass_fail") String pass_fail_status,
			@RequestParam("percentage") String percentage,
			@RequestParam("total")String total,HttpSession session) {
		String msg="";
		if (session.getAttribute("userId") != null) {
		Optional<InterviewScore> interviewscore = interviewScoreService.findByAccesskey(accesskey);

		InterviewScore score = new InterviewScore();
		 if(interviewscore.isPresent()) {
			score = interviewscore.get();
		}else {
			score.setAccessKey(accesskey);
		}
		
		score.setName_a(name_a);score.setName_b(name_b);score.setName_c(name_c);
		score.setDesignation_a(designation_a);score.setDesignation_b(designation_b);score.setDesignation_c(designation_c);
		score.setMobile_a(mobile_a);score.setMobile_b(mobile_b);score.setMobile_c(mobile_c);		
		score.setClarity_a(clarity_a);score.setClarity_b(clarity_b);score.setClarity_c(clarity_c);
		score.setPresentability_a(presentability_a);score.setPresentability_b(presentability_b);score.setPresentability_c(presentability_c);
		score.setAttitude_a(attitude_a);score.setAttitude_b(attitude_b);score.setAttitude_c(attitude_c);
		score.setSituation_a(situation_a);score.setSituation_b(situation_b);score.setSituation_c(situation_c);
		score.setTotal_a(total_a);score.setTotal_b(total_b);score.setTotal_c(total_c);
		score.setTotal_avt(total_avt);
		score.setDesignation_a(designation_a);
		score.setTotal(total);
		score.setStatus(status);
		score.setPass_fail_status(pass_fail_status);
		score.setPercentage(percentage);
		if(status.equals("final")) {
			
			if(pass_fail_status.equals("pass")) {
			   score.setInterviewStatus("Selected");
			   msg="The Candidate is shortlisted in the Interview Process.\r\n" + 
			   		"\r\n" + 
			   		"You must speak with selected candidate to submit their self-attested Documents to complete the Joining Formalities. \r\n" + 
			   		"\r\n" + 
			   		"An Automated Email and SMS sent to Candidates for this.\r\n" + 
			   		"\r\n" + 
			   		"NOTE: You may choose to share the Letter of Intent (LOI) / Offer Letter to selected candidate if required.";
			}else {
				msg="The Candidate is NOT shortlisted in the Interview Process.";
				score.setInterviewStatus("not Selected");	
			}
		}else {
		msg="save";	
		}
		interviewScoreService.saveInterviewScore(score);
		participantService.updateModiedDate(accesskey);
		if(status.equals("final")) {
			Optional<ParticipantRegistration> particpant = participantService.getParticipantByAccesskey(accesskey);	
			if(particpant.isPresent()) {
				particpant.get().setInterviewScore(Integer.parseInt(total));
				participantService.saveData(particpant.get());
			if(pass_fail_status.equals("pass")) {
				String name=particpant.get().getFirstName();
				if(particpant.get().getMiddleName() != null) {
					name  = name+particpant.get().getLastName();
				}
				name = name+particpant.get().getLastName();
				sendEmailShortlisted(particpant.get());	
				try {
			     //send sms for shortlisted 
				 String smsMsg = DataProccessor.getSMS("shortlist");
			     SmsUtility.sendSmsHandler(particpant.get().getMobile(), smsMsg,"MSILOT" );
				}catch(Exception e) {
					System.out.println("Error....."+e);
				}
			}
			}
		}
		
		}else {
			return "redirect:login";
		}
		return msg;
	}
	
	@GetMapping("/printInterviewForm")
	public String printInterView(@RequestParam("accesskey") String accesskey, Model model) {
		
		Optional<ParticipantRegistration> particpant = participantService.getParticipantByAccesskey(accesskey);
		Optional<InterviewScore> score = interviewScoreService.findByAccesskey(accesskey);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if(particpant.isPresent()) {
			model.addAttribute("designation", particpant.get().getDesignation());	
			model.addAttribute("date",formatter.format( particpant.get().getInterviewDate()));	
			model.addAttribute("accesskey", particpant.get().getAccessKey());	
			model.addAttribute("photograph", particpant.get().getPhotograph());
			String name="";
			name= particpant.get().getFirstName();
			
			if(particpant.get().getMiddleName() != null ) {
				name = 	name+" "+particpant.get().getMiddleName();
			}
			if(particpant.get().getLastName() != null ) {
				name = 	name+" "+particpant.get().getLastName();
			}
			model.addAttribute("name", name);	
		}
		if (score.isPresent()) {
			model.addAttribute("score", score.get());
		}else {
			model.addAttribute("score", new InterviewScore());	
		}
		
		return "printInterviewScore";
	}
	
	
	/*
	 * @GetMapping("/PDFInterviewScore") public String
	 * pdfReport(@RequestParam("accesskey")String accesskey){ try {
	 * pdfReport(accesskey,downloadPath); }catch(Exception e) {
	 * System.out.println("error.d........"+e); } return
	 * "redirect:/downloadInterviewScore/"+accesskey; }
	 */
	
	
	/*
	 * public int pdfReport(String accessKey, String downloadPath) {
	 * 
	 * String fileName = accessKey;
	 * 
	 * try {
	 * 
	 * 
	 * File mkDr = new File(downloadPath);
	 * 
	 * mkDr.setWritable(true); mkDr.setReadable(true);
	 * 
	 * if (!mkDr.exists()) mkDr.mkdirs(); String line; OutputStream stdin = null;
	 * InputStream stderr = null; InputStream stdout = null;
	 * 
	 * System.out.println(exeUrl + " " +apurl+ "printIntrviwForm?accesskey="
	 * +accessKey+" "+ downloadPath + fileName + ".pdf"); Process process =
	 * Runtime.getRuntime().exec(exeUrl + " " + apurl+"printIntrviwForm?accesskey="
	 * +accessKey+" "+ downloadPath + fileName + ".pdf");
	 * 
	 * stdin = process.getOutputStream(); stderr = process.getErrorStream(); stdout
	 * = process.getInputStream();
	 * 
	 * // "write" the parms into stdin line = "param1" + "\n";
	 * stdin.write(line.getBytes()); stdin.flush();
	 * 
	 * line = "param2" + "\n"; stdin.write(line.getBytes()); stdin.flush();
	 * 
	 * line = "param3" + "\n"; stdin.write(line.getBytes()); stdin.flush();
	 * 
	 * stdin.close();
	 * 
	 * // clean up if any output in stdout BufferedReader brCleanUp = new
	 * BufferedReader(new InputStreamReader(stdout)); while ((line =
	 * brCleanUp.readLine()) != null) { System.out.println("[Stdout] " + line); }
	 * brCleanUp.close();
	 * 
	 * // clean up if any output in stderr brCleanUp = new BufferedReader(new
	 * InputStreamReader(stderr)); while ((line = brCleanUp.readLine()) != null) {
	 * System.out.println("[Stderr] " + line); } brCleanUp.close();
	 * 
	 * }
	 * 
	 * catch (IOException e) { System.out.println("eror.........."+e); } return 1; }
	 */
	
	
	@RequestMapping(path = "/downloadInterviewScore/{accesskey}", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(@PathVariable("accesskey") String accesskey) throws IOException {

		
		File file = new File(downloadPath + accesskey + ".pdf");

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("Content-Disposition", "attachment; filename=" + accesskey + ".pdf");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))

				.body(resource);
	}
	
	
	@RequestMapping(path = "/downloadInterviewForm", method = RequestMethod.POST)
	public ResponseEntity<Resource> downloadInterviewForm(@RequestParam("fileName") String fileName) throws IOException {
		String path="";
		if(fileName.equals("interview")) {
		   path="classpath:static/pdfTemplate/InterviewEvaluationSheet.pdf";
		}
		if(fileName.equals("suggestive")) {
			   path="classpath:static/pdfTemplate/SuggestiveQuestion.pdf";
			}
		  File file = null;
	
		try {
			 file = ResourceUtils.getFile(path);
		} catch (FileNotFoundException e) {
			System.out.println("Error : File Not Found");
			e.printStackTrace();
		} 
		
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		if(fileName.equals("interview")) {
		headers.add("Content-Disposition", "attachment; filename=InterviewEvaluationSheet.pdf");
		}
		if(fileName.equals("suggestive")) {
			headers.add("Content-Disposition", "attachment; filename=SuggestiveQuestion.pdf");	
		}
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))

				.body(resource);
	}
	
	
	private String sendEmailShortlisted(ParticipantRegistration participant) {
		String subjectLine="iRecruit – Your Job Application: Shortlisted";
		String mailBody=DataProccessor.readFileFromResource("shortlistedEmail");
		//String smsMsg = DataProccessor.getSMS("shortlist");
		mailBody = mailBody.replace("${candidateName}", participant.getFirstName()+" "+participant.getMiddleName()+" "+participant.getLastName());
		Dealer dealer =dealerService.getById(participant.getDealerId()).get();
			mailBody = mailBody.replace("${dealerName}",dealer.getName());
			mailBody = mailBody.replace("${link}", "http://staging.irecruit.org.in/irecrui/candidateLogin");      //Docs Upload link will be here
			mailBody = mailBody.replace("${accesskey}",participant.getAccessKey());
			if(dealer.getMobile() != null) {
			  mailBody = mailBody.replace("${mobile}",dealer.getMobile());
			}else {
				 mailBody = mailBody.replace("${mobile}","");	
			}
			if(dealer.getEmail() != null) {
			   mailBody = mailBody.replace("${email}",dealer.getEmail());
			}else {
				mailBody = mailBody.replace("${email}","");
			}
		//Create Payload
		SendPayload sendP = new SendPayload();
		//sendP.setTo(to);
		sendP.setTo(participant.getEmail());
		sendP.setSubjectLine(subjectLine);
		sendP.setMsg(mailBody);
		sendP.setCc(dealer.getEmail());
		sendP.setBcc("");
		sendP.setFrom("Armezo Solutions");
		EmailUtility.sendMail(sendP.getTo(), sendP.getFrom(), sendP.getCc(), sendP.getBcc(), sendP.getSubjectLine(),
				sendP.getMsg(), "smtp");
		// Sending SMS
		//SmsUtility.sendSmsHandler(participant.getMobile(), smsMsg, "MSILOT");
		System.out.println("Email Sent for Assessment Passing");
		
		return "success";
	}

}
