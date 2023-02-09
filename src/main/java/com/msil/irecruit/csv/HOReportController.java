package com.msil.irecruit.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.AccessKeyMasterService;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.DesignationService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.WorkExperienceService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.service.ListDmsService;
import com.msil.irecruit.dms.service.StateCityPinDmsService;
import com.msil.irecruit.dms.service.TehsilVillageService;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class HOReportController {

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
	InterviewScoreService interviewScoreService;
	@Autowired
	FSDMService fsdmservice;
	@Autowired
	DataScienceService dataService;
	@Autowired
	WorkExperienceService workExperienceService;
	@Autowired
	StateCityPinDmsService scpServiceDms;
	@Autowired
	ListDmsService listService;
	@Autowired
	TehsilVillageService tehsilVillageService;

	@PostMapping("/onHoldHOCSV")
	public @ResponseBody ResponseEntity<?> getCSVOnHoldForHO(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model)  throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
			List<Long> listDealer = new ArrayList<>();
			Date dateFrom = null;
			Date dateTo = null;
			
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
			//Reading DealerId and FSDM Id from String
			Long dId = null;
	        if(dealerId!=null && dealerId!="") {
	        	dId = Long.valueOf(dealerId); 
	        }
	        Integer fId = null;
	        if(fsdmId!=null && fsdmId!="") {
	        	fId=Integer.valueOf(fsdmId);
	        }
			
			List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
			outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
			if(listDealer.size()==0){ 
	        	listDealer.add((long) 0);
	        }
			List<ParticipantRegistration> participant = null;
			if (dateFrom != null && dateTo != null) {
				participant = (List<ParticipantRegistration>) this.participantService
						.getParticipantCSVHold(listDealer, dateFrom, dateTo);
			} else {
				participant = participantService.getParticipantCSVHold(listDealer);
			}
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("On Hold");
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("S. No.");
			cell = row.createCell(1);
			cell.setCellValue("Region");
			cell = row.createCell(2);
			cell.setCellValue("Dealer Code");
			cell = row.createCell(3);
			cell.setCellValue("Dealer City");
			cell = row.createCell(4);
			cell.setCellValue("Dealer Name");
			cell = row.createCell(5);
			cell.setCellValue("State");
			cell = row.createCell(6);
			cell.setCellValue("Employee Code");
			cell = row.createCell(7);
			cell.setCellValue("Title");
			cell = row.createCell(8);
			cell.setCellValue("Candidate Name");
			cell = row.createCell(9);
			cell.setCellValue("Profile");
			cell = row.createCell(10);
			cell.setCellValue("Designation Code");
			cell = row.createCell(11);
			cell.setCellValue("Designation Description");
			cell = row.createCell(12);
			cell.setCellValue("MSPIN");
			cell = row.createCell(13);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(14);
			cell.setCellValue("Email Id");
			cell = row.createCell(15);
			cell.setCellValue("Access Key");
			cell = row.createCell(16);
			cell.setCellValue("Registration Date");
			cell = row.createCell(17);
			cell.setCellValue("Assessment Completion Date");
			cell = row.createCell(18);
			cell.setCellValue("Total Marks");
			cell = row.createCell(19);
			cell.setCellValue("Marks Obtained");
			cell = row.createCell(20);
			cell.setCellValue("Percentage");
			cell = row.createCell(21);
			cell.setCellValue("Assessment Status");
			cell = row.createCell(22);
			cell.setCellValue("Re-attempt");
			cell = row.createCell(23);
			cell.setCellValue("Interview Date");
			cell = row.createCell(24);
			cell.setCellValue("Interview Score");
			cell = row.createCell(25);
			cell.setCellValue("Registration Form");
			cell = row.createCell(26);
			cell.setCellValue("Praarambh Status");
			cell = row.createCell(27);
			cell.setCellValue("FSDM Approval");
			cell = row.createCell(28);
			cell.setCellValue("Recruitment Stage");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}


			int count = 1;
			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			Map<String, String> listMap = new HashMap<String, String>();
			List<ListDms> listDms = listService.getAllListDms();
			for (ListDms list : listDms) {
				listMap.put(list.getListCode(), list.getListDesc());
			}
//			final List<ParticipantRegistration> participant = (List<ParticipantRegistration>) this.participantService
//					.getParticipantHoldHo();

			for (ParticipantRegistration pr : participant) {
				row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(count);
				final Optional<Outlet> ot = outletService.getOutletByOutletCodeAndDealerId(pr.getOutletCode(),
						pr.getDealerId());
				Outlet outlet = null;
				if (ot.isPresent()) {
					outlet = ot.get();
				}
				if (outlet != null) {
					cell = row.createCell(1);
					cell.setCellValue(outlet.getRegion().getRegionCode());
					cell = row.createCell(2);
					cell.setCellValue(outlet.getOutletCode());
					cell = row.createCell(3);
					cell.setCellValue(outlet.getCity().getCityName());
					cell = row.createCell(4);
					cell.setCellValue(outlet.getOutletName());
					cell = row.createCell(5);
					cell.setCellValue(outlet.getState().getStateName());

				} else {
					cell = row.createCell(1);
					cell.setCellValue("");
					cell = row.createCell(2);
					cell.setCellValue("");
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue("");
					cell = row.createCell(5);
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				cell.setCellValue(pr.getEmployeeCode());
				cell = row.createCell(7);
				if (pr.getTitle() != null && pr.getTitle() != "") {
					cell.setCellValue(listMap.get(pr.getTitle()));
				} else {
					cell.setCellValue("");
				}
				String name = pr.getFirstName();
				if (pr.getMiddleName() != null) {
					name += " " + pr.getMiddleName();
				}
				name += " " + pr.getLastName();
				cell = row.createCell(8);
				cell.setCellValue(name);
				cell = row.createCell(9);
				cell.setCellValue(pr.getDesignation());
				cell = row.createCell(10);
				cell.setCellValue(pr.getFinalDesignation());
				cell = row.createCell(11);
				if (pr.getFinalDesignation() != null && pr.getFinalDesignation() != "") {
					cell.setCellValue(desgMap.get(pr.getFinalDesignation()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				cell.setCellValue(pr.getMspin());
				cell = row.createCell(13);
				cell.setCellValue(pr.getMobile());
				cell = row.createCell(14);
				cell.setCellValue(pr.getEmail());
				cell = row.createCell(15);
				cell.setCellValue(pr.getAccessKey());
				cell = row.createCell(16);
				if (pr.getRegistration_date() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getRegistration_date()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(17);
				if (pr.getTestCompletionDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getTestCompletionDate()));
				} else {
					cell.setCellValue("");

				}
				cell = row.createCell(18);
				cell.setCellValue(pr.getTotalMark());
				cell = row.createCell(19);
				if (pr.getTestScore() != null) {
					cell.setCellValue(pr.getTestScore());
				} else {
					cell.setCellValue("");

				}
				cell = row.createCell(20);
				cell.setCellValue(pr.getPercentageScore());
				cell = row.createCell(21);
				if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
					if (pr.getPassFailStatus() == 1) {
						cell.setCellValue("Pass");
					} else if (pr.getPassFailStatus() == 0) {
						cell.setCellValue("Fail");
					}
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(22);
				if (pr.getReAtampStatus() == null) {
					cell.setCellValue("No");
				} else {
					cell.setCellValue("Yes");
				}
				cell = row.createCell(23);
				if (pr.getInterviewDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getInterviewDate()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(24);
				Optional<InterviewScore> intScore = interviewScoreService.findByAccesskey(pr.getAccessKey());
				if (intScore.isPresent()) {
					cell.setCellValue(intScore.get().getTotal());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(25);
				if (pr.getRegStatus() != null) {
					cell.setCellValue("Yes");
				} else {
					cell.setCellValue("No");
				}
				cell = row.createCell(26);
				String praarambhStatus="";
				if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus()!="") {
					if(pr.getPrarambhStatus().equals("1"))
						praarambhStatus="Pending";
					if(pr.getPrarambhStatus().equals("2"))
						praarambhStatus="Completed";
				}
				cell.setCellValue(praarambhStatus);
				cell = row.createCell(27);
				if (pr.getFsdmApprovalStatus() != null) {
					if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 1) {
						cell.setCellValue("Rejected");
					} else if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 3) {
						cell.setCellValue("Pending");
					}
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(28);
				String recruitmentStage = "";
				if (pr.getTestStatus() != null) {
					if (pr.getTestStatus().equals("1") || pr.getTestStatus().equals("2")
							|| pr.getTestStatus().equals("3")) {
						recruitmentStage = "In Progress";
					}
				}
				if (pr.getStatus() != null) {
					if (pr.getStatus().equalsIgnoreCase("H")) {
						recruitmentStage = "On Hold";
					}
				}
				if (pr.getFsdmApprovalStatus() != null) {
					if (pr.getFsdmApprovalStatus().equalsIgnoreCase("2")) {
						recruitmentStage = "Approved";
					}
				}
				cell.setCellValue(recruitmentStage);
				
				count++;
				// }// if for pr test check
			} // for over part list
			String responseExcelUrl = "onHold.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("onHold.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= onHold.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} // session not null
		else {
			return null;
		}
	}

	@PostMapping("/inProgressHOCSV")
	public @ResponseBody ResponseEntity<?> inProgressCandidate(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model) throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
			List<Long> listDealer = new ArrayList<>();
			Date dateFrom = null;
			Date dateTo = null;
			
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
			//Reading DealerId and FSDM Id from String
			Long dId = null;
	        if(dealerId!=null && dealerId!="") {
	        	dId = Long.valueOf(dealerId); 
	        }
	        Integer fId = null;
	        if(fsdmId!=null && fsdmId!="") {
	        	fId=Integer.valueOf(fsdmId);
	        }
			
			List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
			outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
			if(listDealer.size()==0){ 
	        	listDealer.add((long) 0);
	        }
			List<ParticipantRegistration> participant = new ArrayList<ParticipantRegistration>();
			if(dateFrom!=null && dateTo!=null ) {
	               participant = (List<ParticipantRegistration>)this.participantService.getParticipantCSVInpprocess(listDealer, dateFrom, dateTo);
	        	}else {       		
	        		participant = participantService.getParticipantCSVInpprocess(listDealer);	        		
				}
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("In Progress");
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("S. No.");
			cell = row.createCell(1);
			cell.setCellValue("Region");
			cell = row.createCell(2);
			cell.setCellValue("Dealer Code");
			cell = row.createCell(3);
			cell.setCellValue("Dealer City");
			cell = row.createCell(4);
			cell.setCellValue("Dealer Name");
			cell = row.createCell(5);
			cell.setCellValue("State");
			cell = row.createCell(6);
			cell.setCellValue("Employee Code");
			cell = row.createCell(7);
			cell.setCellValue("Title");
			cell = row.createCell(8);
			cell.setCellValue("Candidate Name");
			cell = row.createCell(9);
			cell.setCellValue("Profile");
			cell = row.createCell(10);
			cell.setCellValue("Designation Code");
			cell = row.createCell(11);
			cell.setCellValue("Designation Description");
			cell = row.createCell(12);
			cell.setCellValue("MSPIN");
			cell = row.createCell(13);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(14);
			cell.setCellValue("Email Id");
			cell = row.createCell(15);
			cell.setCellValue("Access Key");
			cell = row.createCell(16);
			cell.setCellValue("Registration Date");
			cell = row.createCell(17);
			cell.setCellValue("Assessment Completion Date");
			cell = row.createCell(18);
			cell.setCellValue("Total Marks");
			cell = row.createCell(19);
			cell.setCellValue("Marks Obtained");
			cell = row.createCell(20);
			cell.setCellValue("Percentage");
			cell = row.createCell(21);
			cell.setCellValue("Assessment Status");
			cell = row.createCell(22);
			cell.setCellValue("Re-attempt");
			cell = row.createCell(23);
			cell.setCellValue("Interview Date");
			cell = row.createCell(24);
			cell.setCellValue("Interview Score");
			cell = row.createCell(25);
			cell.setCellValue("Registration Form");
			cell = row.createCell(26);
			cell.setCellValue("Praarambh Status");
			cell = row.createCell(27);
			cell.setCellValue("FSDM Approval");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}

			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			Map<String, String> listMap = new HashMap<String, String>();
			List<ListDms> listDms = listService.getAllListDms();
			for (ListDms list : listDms) {
				listMap.put(list.getListCode(), list.getListDesc());
			}

			int count = 1;
			for (ParticipantRegistration pr : participant) {
				row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(count);
				final Optional<Outlet> ot = outletService.getOutletByOutletCodeAndDealerId(pr.getOutletCode(),
						pr.getDealerId());
				Outlet outlet = null;
				if (ot.isPresent()) {
					outlet = ot.get();
				}
				if (outlet != null) {
					cell = row.createCell(1);
					cell.setCellValue(outlet.getRegion().getRegionCode());
					cell = row.createCell(2);
					cell.setCellValue(outlet.getOutletCode());
					cell = row.createCell(3);
					cell.setCellValue(outlet.getCity().getCityName());
					cell = row.createCell(4);
					cell.setCellValue(outlet.getOutletName());
					cell = row.createCell(5);
					cell.setCellValue(outlet.getState().getStateName());

				} else {
					cell = row.createCell(1);
					cell.setCellValue("");
					cell = row.createCell(2);
					cell.setCellValue("");
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue("");
					cell = row.createCell(5);
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				cell.setCellValue(pr.getEmployeeCode());
				cell = row.createCell(7);
				if (pr.getTitle() != null && pr.getTitle() != "") {
					cell.setCellValue(listMap.get(pr.getTitle()));
				} else {
					cell.setCellValue("");
				}
				String name = pr.getFirstName();
				if (pr.getMiddleName() != null) {
					name += " " + pr.getMiddleName();
				}
				name += " " + pr.getLastName();
				cell = row.createCell(8);
				cell.setCellValue(name);
				cell = row.createCell(9);
				cell.setCellValue(pr.getDesignation());
				cell = row.createCell(10);
				cell.setCellValue(pr.getFinalDesignation());
				cell = row.createCell(11);
				if (pr.getFinalDesignation() != null && pr.getFinalDesignation() != "") {
					cell.setCellValue(desgMap.get(pr.getFinalDesignation()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				cell.setCellValue(pr.getMspin());
				cell = row.createCell(13);
				cell.setCellValue(pr.getMobile());
				cell = row.createCell(14);
				cell.setCellValue(pr.getEmail());
				cell = row.createCell(15);
				cell.setCellValue(pr.getAccessKey());
				cell = row.createCell(16);
				if (pr.getRegistration_date() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getRegistration_date()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(17);
				if (pr.getTestCompletionDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getTestCompletionDate()));
				} else {
					cell.setCellValue("");

				}
				cell = row.createCell(18);
				cell.setCellValue(pr.getTotalMark());
				cell = row.createCell(19);
				if (pr.getTestScore() != null) {
					cell.setCellValue(pr.getTestScore());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(20);
				cell.setCellValue(pr.getPercentageScore());
				cell = row.createCell(21);
				if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
					if (pr.getPassFailStatus() == 1) {
						cell.setCellValue("Pass");
					} else if (pr.getPassFailStatus() == 0) {
						cell.setCellValue("Fail");
					}
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(22);
				if (pr.getReAtampStatus() == null) {
					cell.setCellValue("No");
				} else {
					cell.setCellValue("Yes");
				}
				cell = row.createCell(23);
				if (pr.getInterviewDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getInterviewDate()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(24);
				Optional<InterviewScore> intScore = interviewScoreService.findByAccesskey(pr.getAccessKey());
				if (intScore.isPresent()) {
					cell.setCellValue(intScore.get().getTotal());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(25);
				if (pr.getRegStatus() != null) {
					cell.setCellValue("Yes");
				} else {
					cell.setCellValue("No");
				}
				cell = row.createCell(26);
				String praarambhStatus="";
				if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus()!="") {
					if(pr.getPrarambhStatus().equals("1"))
						praarambhStatus="Pending";
					if(pr.getPrarambhStatus().equals("2"))
						praarambhStatus="Completed";
				}
				cell.setCellValue(praarambhStatus);
				cell = row.createCell(27);
				if (pr.getFsdmApprovalStatus() != null) {
					if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 1) {
						cell.setCellValue("Rejected");
					} else if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 3) {
						cell.setCellValue("Pending");
					}
				} else {
					cell.setCellValue("");
				}
				count++;
				// }// if for pr test check
			} // for over part list
			String responseExcelUrl = "In Progress.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("In Progress.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= In Progress.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} // session not null
		else {
			return null;
		}
	}

	@PostMapping("/csvHOEmployee")
	public @ResponseBody ResponseEntity<?> getHOCSVEmployee(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model)  throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
			List<Long> listDealer = new ArrayList<>();
			Date dateFrom = null;
			Date dateTo = null;
			
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
			//Reading DealerId and FSDM Id from String
			Long dId = null;
	        if(dealerId!=null && dealerId!="") {
	        	dId = Long.valueOf(dealerId); 
	        }
	        Integer fId = null;
	        if(fsdmId!=null && fsdmId!="") {
	        	fId=Integer.valueOf(fsdmId);
	        }
			
			List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
			outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
			if(listDealer.size()==0){ 
	        	listDealer.add((long) 0);
	        }
			List<ParticipantRegistration> participant = new ArrayList<ParticipantRegistration>();
			if(dateFrom!=null && dateTo!=null ) {
	               participant = (List<ParticipantRegistration>)this.participantService.getParticipantCSVImployee(listDealer, dateFrom, dateTo);
	        	}else {       		
	        		participant = participantService.getParticipantCSVImployee(listDealer);	        		
				}
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Employee Master");
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("S. No.");
			cell = row.createCell(1);
			cell.setCellValue("Region");
			cell = row.createCell(2);
			cell.setCellValue("Dealer Code");
			cell = row.createCell(3);
			cell.setCellValue("Dealer City");
			cell = row.createCell(4);
			cell.setCellValue("Dealer Name");
			cell = row.createCell(5);
			cell.setCellValue("State");
			cell = row.createCell(6);
			cell.setCellValue("Employee Code");
			cell = row.createCell(7);
			cell.setCellValue("Title");
			cell = row.createCell(8);
			cell.setCellValue("Candidate Name");
			cell = row.createCell(9);
			cell.setCellValue("Profile");
			cell = row.createCell(10);
			cell.setCellValue("Designation Code");
			cell = row.createCell(11);
			cell.setCellValue("Designation Description");
			cell = row.createCell(12);
			cell.setCellValue("MSPIN");
			cell = row.createCell(13);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(14);
			cell.setCellValue("Email Id");
			cell = row.createCell(15);
			cell.setCellValue("Access Key");
			cell = row.createCell(16);
			cell.setCellValue("Registration Date");
			cell = row.createCell(17);
			cell.setCellValue("Assessment Completion Date");
			cell = row.createCell(18);
			cell.setCellValue("Total Marks");
			cell = row.createCell(19);
			cell.setCellValue("Marks Obtained");
			cell = row.createCell(20);
			cell.setCellValue("Percentage");
			cell = row.createCell(21);
			cell.setCellValue("Assessment Status");
			cell = row.createCell(22);
			cell.setCellValue("Re-attempt");
			cell = row.createCell(23);
			cell.setCellValue("Interview Date");
			cell = row.createCell(24);
			cell.setCellValue("Interview Score");
			cell = row.createCell(25);
			cell.setCellValue("Registration Form");
			cell = row.createCell(26);
			cell.setCellValue("Praarambh Status");
			cell = row.createCell(27);
			cell.setCellValue("FSDM Approval");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}


			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			Map<String, String> listMap = new HashMap<String, String>();
			List<ListDms> listDms = listService.getAllListDms();
			for (ListDms list : listDms) {
				listMap.put(list.getListCode(), list.getListDesc());
			}
//			final List<ParticipantRegistration> participant = (List<ParticipantRegistration>) this.participantService
//					.getParticipantEmployeeHo();

			int count = 1;
			for (ParticipantRegistration pr : participant) {
				row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(count);
				final Optional<Outlet> ot = outletService.getOutletByOutletCodeAndDealerId(pr.getOutletCode(),
						pr.getDealerId());
				Outlet outlet = null;
				if (ot.isPresent()) {
					outlet = ot.get();
				}
				if (outlet != null) {
					cell = row.createCell(1);
					cell.setCellValue(outlet.getRegion().getRegionCode());
					cell = row.createCell(2);
					cell.setCellValue(outlet.getOutletCode());
					cell = row.createCell(3);
					cell.setCellValue(outlet.getCity().getCityName());
					cell = row.createCell(4);
					cell.setCellValue(outlet.getOutletName());
					cell = row.createCell(5);
					cell.setCellValue(outlet.getState().getStateName());

				} else {
					cell = row.createCell(1);
					cell.setCellValue("");
					cell = row.createCell(2);
					cell.setCellValue("");
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue("");
					cell = row.createCell(5);
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				cell.setCellValue(pr.getEmployeeCode());
				cell = row.createCell(7);
				if (pr.getTitle() != null && pr.getTitle() != "") {
					cell.setCellValue(listMap.get(pr.getTitle()));
				} else {
					cell.setCellValue("");
				}
				String name = pr.getFirstName();
				if (pr.getMiddleName() != null) {
					name += " " + pr.getMiddleName();
				}
				name += " " + pr.getLastName();
				cell = row.createCell(8);
				cell.setCellValue(name);
				cell = row.createCell(9);
				cell.setCellValue(pr.getDesignation());
				cell = row.createCell(10);
				cell.setCellValue(pr.getFinalDesignation());
				cell = row.createCell(11);
				if (pr.getFinalDesignation() != null && pr.getFinalDesignation() != "") {
					cell.setCellValue(desgMap.get(pr.getFinalDesignation()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				cell.setCellValue(pr.getMspin());
				cell = row.createCell(13);
				cell.setCellValue(pr.getMobile());
				cell = row.createCell(14);
				cell.setCellValue(pr.getEmail());
				cell = row.createCell(15);
				cell.setCellValue(pr.getAccessKey());
				cell = row.createCell(16);
				if (pr.getRegistration_date() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getRegistration_date()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(17);
				if (pr.getTestCompletionDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getTestCompletionDate()));
				} else {
					cell.setCellValue("");

				}
				cell = row.createCell(18);
				cell.setCellValue(pr.getTotalMark());
				cell = row.createCell(19);
				if (pr.getTestScore() != null) {
					cell.setCellValue(pr.getTestScore());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(20);
				cell.setCellValue(pr.getPercentageScore());
				cell = row.createCell(21);
				if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
					if (pr.getPassFailStatus() == 1) {
						cell.setCellValue("Pass");
					} else if (pr.getPassFailStatus() == 0) {
						cell.setCellValue("Fail");
					}
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(22);
				if (pr.getReAtampStatus() == null) {
					cell.setCellValue("No");
				} else {
					cell.setCellValue("Yes");
				}
				cell = row.createCell(23);
				if (pr.getInterviewDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getInterviewDate()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(24);
				Optional<InterviewScore> intScore = interviewScoreService.findByAccesskey(pr.getAccessKey());
				if (intScore.isPresent()) {
					cell.setCellValue(intScore.get().getTotal());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(25);
				if (pr.getRegStatus() != null) {
					cell.setCellValue("Yes");
				} else {
					cell.setCellValue("No");
				}
				cell = row.createCell(26);
				String praarambhStatus="";
				if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus()!="") {
					if(pr.getPrarambhStatus().equals("1"))
						praarambhStatus="Pending";
					if(pr.getPrarambhStatus().equals("2"))
						praarambhStatus="Completed";
				}
				cell.setCellValue(praarambhStatus);
				cell = row.createCell(27);
				if (pr.getFsdmApprovalStatus() != null) {
					if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 1) {
						cell.setCellValue("Rejected");
					} else if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 3) {
						cell.setCellValue("Pending");
					}
				} else {
					cell.setCellValue("");
				}
				count++;
				// }// if for pr test check
			} // for over part list
			String responseExcelUrl = "Employee Master.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("Employee Master.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= Employee Master.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} // session not null
		else {
			return null;
		}
	}


	@PostMapping("/getHOCSVPendingApproval")
	public @ResponseBody ResponseEntity<?> getCSVPendingApprovel(@RequestParam("regionCode") final String regionCode, @RequestParam("stateCode") final String stateCode, @RequestParam("cityCode") final String cityCode, 
    		@RequestParam("parentDealerCode") final String parentDealerCode, @RequestParam("fsdmId") final String fsdmId, @RequestParam("dealerId") final String dealerId, 
    		@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, final HttpSession session, Model model)  throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
			List<Long> listDealer = new ArrayList<>();
			Date dateFrom = null;
			Date dateTo = null;
			
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
			//Reading DealerId and FSDM Id from String
			Long dId = null;
	        if(dealerId!=null && dealerId!="") {
	        	dId = Long.valueOf(dealerId); 
	        }
	        Integer fId = null;
	        if(fsdmId!=null && fsdmId!="") {
	        	fId=Integer.valueOf(fsdmId);
	        }
			
			List<Outlet>  outlets =outletService.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
			outlets.stream().map(Outlet :: getDealer).map(Dealer :: getId).forEachOrdered(listDealer :: add);
			if(listDealer.size()==0){ 
	        	listDealer.add((long) 0);
	        }
			List<ParticipantRegistration> participant = new ArrayList<ParticipantRegistration>();
			if(dateFrom!=null && dateTo!=null ) {
	               participant = (List<ParticipantRegistration>)this.participantService.getParticipantCSVPendingApprovel(listDealer, dateFrom, dateTo);
	        	}else {       		
	        		participant = participantService.getParticipantCSVPendingApprovel(listDealer);	        		
				}
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Pending Approval");
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("S. No.");
			cell = row.createCell(1);
			cell.setCellValue("Region");
			cell = row.createCell(2);
			cell.setCellValue("Dealer Code");
			cell = row.createCell(3);
			cell.setCellValue("Dealer City");
			cell = row.createCell(4);
			cell.setCellValue("Dealer Name");
			cell = row.createCell(5);
			cell.setCellValue("State");
			cell = row.createCell(6);
			cell.setCellValue("Employee Code");
			cell = row.createCell(7);
			cell.setCellValue("Title");
			cell = row.createCell(8);
			cell.setCellValue("Candidate Name");
			cell = row.createCell(9);
			cell.setCellValue("Profile");
			cell = row.createCell(10);
			cell.setCellValue("Designation Code");
			cell = row.createCell(11);
			cell.setCellValue("Designation Description");
			cell = row.createCell(12);
			cell.setCellValue("MSPIN");
			cell = row.createCell(13);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(14);
			cell.setCellValue("Email Id");
			cell = row.createCell(15);
			cell.setCellValue("Access Key");
			cell = row.createCell(16);
			cell.setCellValue("Registration Date");
			cell = row.createCell(17);
			cell.setCellValue("Assessment Completion Date");
			cell = row.createCell(18);
			cell.setCellValue("Total Marks");
			cell = row.createCell(19);
			cell.setCellValue("Marks Obtained");
			cell = row.createCell(20);
			cell.setCellValue("Percentage");
			cell = row.createCell(21);
			cell.setCellValue("Assessment Status");
			cell = row.createCell(22);
			cell.setCellValue("Re-attempt");
			cell = row.createCell(23);
			cell.setCellValue("Interview Date");
			cell = row.createCell(24);
			cell.setCellValue("Interview Score");
			cell = row.createCell(25);
			cell.setCellValue("Registration Form");
			cell = row.createCell(26);
			cell.setCellValue("Praarambh Status");
			cell = row.createCell(27);
			cell.setCellValue("FSDM Approval");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}

//			final List<ParticipantRegistration> participant = (List<ParticipantRegistration>) this.participantService
//					.getParticipantPendingApprovel();

			int count = 1;
			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			Map<String, String> listMap = new HashMap<String, String>();
			List<ListDms> listDms = listService.getAllListDms();
			for (ListDms list : listDms) {
				listMap.put(list.getListCode(), list.getListDesc());
			}
			for (ParticipantRegistration pr : participant) {
				row = sheet.createRow(count);
				cell = row.createCell(0);
				cell.setCellValue(count);
				final Optional<Outlet> ot = outletService.getOutletByOutletCodeAndDealerId(pr.getOutletCode(),
						pr.getDealerId());
				Outlet outlet = null;
				if (ot.isPresent()) {
					outlet = ot.get();
				}
				if (outlet != null) {
					cell = row.createCell(1);
					cell.setCellValue(outlet.getRegion().getRegionCode());
					cell = row.createCell(2);
					cell.setCellValue(outlet.getOutletCode());
					cell = row.createCell(3);
					cell.setCellValue(outlet.getCity().getCityName());
					cell = row.createCell(4);
					cell.setCellValue(outlet.getOutletName());
					cell = row.createCell(5);
					cell.setCellValue(outlet.getState().getStateName());

				} else {
					cell = row.createCell(1);
					cell.setCellValue("");
					cell = row.createCell(2);
					cell.setCellValue("");
					cell = row.createCell(3);
					cell.setCellValue("");
					cell = row.createCell(4);
					cell.setCellValue("");
					cell = row.createCell(5);
					cell.setCellValue("");
				}
				cell = row.createCell(6);
				cell.setCellValue(pr.getEmployeeCode());
				cell = row.createCell(7);
				if (pr.getTitle() != null && pr.getTitle() != "") {
					cell.setCellValue(listMap.get(pr.getTitle()));
				} else {
					cell.setCellValue("");
				}
				String name = pr.getFirstName();
				if (pr.getMiddleName() != null) {
					name += " " + pr.getMiddleName();
				}
				name += " " + pr.getLastName();
				cell = row.createCell(8);
				cell.setCellValue(name);
				cell = row.createCell(9);
				cell.setCellValue(pr.getDesignation());
				cell = row.createCell(10);
				cell.setCellValue(pr.getFinalDesignation());
				cell = row.createCell(11);
				if (pr.getFinalDesignation() != null && pr.getFinalDesignation() != "") {
					cell.setCellValue(desgMap.get(pr.getFinalDesignation()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(12);
				cell.setCellValue(pr.getMspin());
				cell = row.createCell(13);
				cell.setCellValue(pr.getMobile());
				cell = row.createCell(14);
				cell.setCellValue(pr.getEmail());
				cell = row.createCell(15);
				cell.setCellValue(pr.getAccessKey());
				cell = row.createCell(16);
				if (pr.getRegistration_date() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getRegistration_date()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(17);
				if (pr.getTestCompletionDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getTestCompletionDate()));
				} else {
					cell.setCellValue("");

				}
				cell = row.createCell(18);
				cell.setCellValue(pr.getTotalMark());
				cell = row.createCell(19);
				if (pr.getTestScore() != null) {
					cell.setCellValue(pr.getTestScore());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(20);
				cell.setCellValue(pr.getPercentageScore());
				cell = row.createCell(21);
				if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
					if (pr.getPassFailStatus() == 1) {
						cell.setCellValue("Pass");
					} else if (pr.getPassFailStatus() == 0) {
						cell.setCellValue("Fail");
					}
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(22);
				if (pr.getReAtampStatus() == null) {
					cell.setCellValue("No");
				} else {
					cell.setCellValue("Yes");
				}
				cell = row.createCell(23);
				if (pr.getInterviewDate() != null) {
					cell.setCellValue(DataProccessor.dateToString(pr.getInterviewDate()));
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(24);
				Optional<InterviewScore> intScore = interviewScoreService.findByAccesskey(pr.getAccessKey());
				if (intScore.isPresent()) {
					cell.setCellValue(intScore.get().getTotal());
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(25);
				if (pr.getRegStatus() != null) {
					cell.setCellValue("Yes");
				} else {
					cell.setCellValue("No");
				}
				cell = row.createCell(26);
				String praarambhStatus="";
				if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus()!="") {
					if(pr.getPrarambhStatus().equals("1"))
						praarambhStatus="Pending";
					if(pr.getPrarambhStatus().equals("2"))
						praarambhStatus="Completed";
				}
				cell.setCellValue(praarambhStatus);
				cell = row.createCell(27);
				if (pr.getFsdmApprovalStatus() != null) {
					if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 1) {
						cell.setCellValue("Rejected");
					} else if (Integer.parseInt(pr.getFsdmApprovalStatus()) == 3) {
						cell.setCellValue("Pending");
					}
				} else {
					cell.setCellValue("");
				}
				count++;
				// }// if for pr test check
			} // for over part list
			String responseExcelUrl = "Pending Approval.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("Pending Approval.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= Pending Approval.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} // session not null
		else {
			return null;
		}
	}

}
