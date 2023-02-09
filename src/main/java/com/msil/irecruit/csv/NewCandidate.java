package com.msil.irecruit.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msil.irecruit.Entities.AccessKeyMaster;
import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.Designation;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.WorkExperience;
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
import com.msil.irecruit.dms.entities.StateCityPinDms;
import com.msil.irecruit.dms.entities.TehsilVillage;
import com.msil.irecruit.dms.service.ListDmsService;
import com.msil.irecruit.dms.service.StateCityPinDmsService;
import com.msil.irecruit.dms.service.TehsilVillageService;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class NewCandidate {

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
	


	@PostMapping("/newCandidateCSV")
	public @ResponseBody ResponseEntity<?> getNewCandidate(HttpSession session, Model model)
			throws FileNotFoundException {

		if (session.getAttribute("userId") != null) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("New Candidate");
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			Row row = null;
			row = sheet.createRow(0);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellValue("S.No.");
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
			cell.setCellValue("Candidate Name");
			cell = row.createCell(7);
			cell.setCellValue("Profile");
			cell = row.createCell(8);
			cell.setCellValue("Designation Code");
			cell = row.createCell(9);
			cell.setCellValue("Designation Description");
			cell = row.createCell(10);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(11);
			cell.setCellValue("Email Id");
			cell = row.createCell(12);
			cell.setCellValue("Access Key");
			cell = row.createCell(13);
			cell.setCellValue("Registration Date");
			cell = row.createCell(14);
			cell.setCellValue("Communication");
			cell = row.createCell(15);
			cell.setCellValue("Status");
			cell = row.createCell(16);
			cell.setCellValue("Re-activate");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}

			long dealerId = Long.parseLong(session.getAttribute("userId").toString());
			List<AccessKeyMaster> keys = accessKeyMasterService.getByDealer(dealerId);
			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			int counter = 1;
			for (AccessKeyMaster key : keys) {
				Optional<ParticipantRegistration> pa = participantService.getParticipantByAccesskey(key.getAccesskey());
				if (pa.isPresent()) {
					if (pa.get().getTestStatus() == null || Integer.parseInt(pa.get().getTestStatus()) < 3) {
						row = sheet.createRow(counter);
						cell = row.createCell(0);
						cell.setCellValue(counter);
						final Optional<Outlet> outlet = outletService
								.getOutletByOutletCodeAndDealerId(pa.get().getOutletCode(), dealerId);
						if (outlet.isPresent()) {
							cell = row.createCell(1);
							cell.setCellValue(outlet.get().getRegion().getRegionCode());
							cell = row.createCell(2);
							cell.setCellValue(outlet.get().getOutletCode());
							cell = row.createCell(3);
							cell.setCellValue(outlet.get().getCity().getCityName());
							cell = row.createCell(4);
							cell.setCellValue(outlet.get().getOutletName());
							cell = row.createCell(5);
							cell.setCellValue(outlet.get().getState().getStateName());

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
						String name = pa.get().getFirstName();
						if (pa.get().getMiddleName() != null) {
							name += " " + pa.get().getMiddleName();
						}
						name += " " + pa.get().getLastName();
						cell = row.createCell(6);
						cell.setCellValue(name);
						cell = row.createCell(7);
						cell.setCellValue(pa.get().getDesignation());
						cell = row.createCell(8);
						cell.setCellValue(pa.get().getFinalDesignation());
						cell = row.createCell(9);
						if (pa.get().getFinalDesignation() != null && pa.get().getFinalDesignation() != "") {
							cell.setCellValue(desgMap.get(pa.get().getFinalDesignation()));
						} else {
							cell.setCellValue("");
						}
						cell = row.createCell(10);
						cell.setCellValue(pa.get().getMobile());
						cell = row.createCell(11);
						cell.setCellValue(pa.get().getEmail());
						cell = row.createCell(12);
						cell.setCellValue(pa.get().getAccessKey());
						cell = row.createCell(13);
						if (pa.get().getRegistration_date() != null) {
							cell.setCellValue(DataProccessor.dateToString(pa.get().getRegistration_date()));
						} else {
							cell.setCellValue("");
						}
						cell = row.createCell(14);
						cell.setCellValue("YES");

						if (pa.get().getTestStatus() == null || pa.get().getTestStatus().equals("0")) {
							cell = row.createCell(15);
							cell.setCellValue("Not Started");
						} else if (pa.get().getTestStatus() != null || pa.get().getTestStatus().equals("1")) {
							cell = row.createCell(15);
							cell.setCellValue("Started");
						}
						cell=row.createCell(16);
						String reactivateStatus="No";
						if(pa.get().getReactivationDate() != null) {
							Long t1 = pa.get().getReactivationDate().getTime();
							Long t2 = new Date().getTime();
							int tm = (int) (t2 - t1) / (60 * 60 * 1000);
							if (tm > 48) {
								reactivateStatus="Yes";
							}
						}else if(pa.get().getSendMailDate() != null) {
							Long t1 = pa.get().getSendMailDate().getTime();
							Long t2 = new Date().getTime();
							int tm = (int) (t2 - t1) / (60 * 60 * 1000);
							if (tm > 24) {
								reactivateStatus="Yes";
							}
						}else {
							reactivateStatus="No";
						}
						cell.setCellValue(reactivateStatus);
					} else {
						continue;
					}
				} else {
					row = sheet.createRow(counter);
					cell = row.createCell(0);
					cell.setCellValue(counter);
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
					cell = row.createCell(6);
					cell.setCellValue("");
					cell = row.createCell(7);
					cell.setCellValue("");
					cell = row.createCell(8);
					cell.setCellValue("");
					cell = row.createCell(9);
					cell.setCellValue("");
					cell = row.createCell(10);
					cell.setCellValue("");
					cell = row.createCell(11);
					cell.setCellValue("");
					cell = row.createCell(12);
					cell.setCellValue(key.getAccesskey());
					cell = row.createCell(13);
					cell.setCellValue("");
					cell = row.createCell(14);
					cell.setCellValue("No");
					cell = row.createCell(15);
					cell.setCellValue("Email not sent");
					cell = row.createCell(15);
					cell.setCellValue("No");
				}
				counter++;
			}
			String responseExcelUrl = "New Candidate.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("New Candidate.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= New Candidate.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} else {
			return null;
		}

	}

	@PostMapping("/onHoldCSV")
	public @ResponseBody ResponseEntity<?> getCSVOnHold(@RequestParam("outlet") String outletCode,
			@RequestParam("candidate") String candidateName, @RequestParam("unique") String uniqueCode,
			@RequestParam("desig") String designation, @RequestParam("mspinS") String mspin,
			@RequestParam("pass") String passFailStatus, @RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) throws FileNotFoundException {

		if (session.getAttribute("userId") != null) {
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

			Date dateFrom = null;
			Date dateTo = null;
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
			// check data
			if (outletCode == null)
				outletCode = "";
			if (candidateName == null)
				candidateName = "";
			if (uniqueCode == null) {
				uniqueCode = "";
			}
			if (designation == null)
				designation = "";
			if (mspin == null || mspin == "")
				System.out.println("MSPINNNNNNNNNNNN : " + mspin);
			mspin = "";
			System.out.println("MSPINNNNNNNNNNNN : " + mspin);
			if (passFailStatus == "" || passFailStatus == ",") {
				passFStatus.add(1);
				passFStatus.add(0);
			} else {
				System.out.println(passFailStatus);
				passFStatus.add(Integer.valueOf(passFailStatus));
			}

			System.out
					.println("Date in CSV : " + dateFrom + ",    To Date " + dateTo + "     Name :::" + candidateName);
			String role = session.getAttribute("role").toString();
			final String status = "H";
			List<Outlet> outletList = null;
			List<ParticipantRegistration> participantList = new ArrayList<ParticipantRegistration>();
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				outletList = outletService.getOutletByDealerId(dealerId);
				if (dateFrom != null && dateTo != null) {
					participantList = participantService.getParticipantOnHoldByFilterData(outletCode, candidateName,
							designation, mspin, passFStatus, uniqueCode, dealerId, dateFrom, dateTo, status);
				} else {
					participantList = participantService.getParticipantOnHoldByFilterData2(outletCode, candidateName,
							designation, mspin, passFStatus, dealerId, uniqueCode, status);
				}
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				List<Long> list = new ArrayList<>();
				Optional<FSDM> f = fsdmservice.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet3 : outletList) {
						list.add(outlet3.getDealer().getId());
					}
				}
				List<ParticipantRegistration> fsdmPart = new ArrayList<ParticipantRegistration>();
				if (dateFrom != null && dateTo != null) {
					fsdmPart = participantService.getParticipantByFilterDataForFSDM(outletCode, candidateName,
							designation, mspin, passFStatus, uniqueCode, list, dateFrom, dateTo);
				} else {
					fsdmPart = participantService.getParticipantByFilterDataForFSDM2(outletCode, candidateName,
							designation, mspin, passFStatus, list, uniqueCode);
				}
				for (ParticipantRegistration p : fsdmPart) {
					if (p.getStatus() != null && p.getStatus().equals("H")) {
						participantList.add(p);
					}
				}
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
			for (ParticipantRegistration pr : participantList) {
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

	@PostMapping("/inProgressCSV")
	public @ResponseBody ResponseEntity<?> inProgressCandidate(@RequestParam("outlet") String outletCode,
			@RequestParam("candidate") String candidateName, @RequestParam("unique") String uniqueCode,
			@RequestParam("desig") String designation, @RequestParam("mspinS") String mspin,
			@RequestParam("pass") String passFailStatus, @RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) throws FileNotFoundException {
		if (session.getAttribute("userId") != null) {
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

			Date dateFrom = null;
			Date dateTo = null;
			List<Integer> passFStatus = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (dateFromm.length()>5) {
					dateFrom = sdf.parse(dateFromm);
				}
				if (dateToo.length()>5) {
					dateTo = sdf.parse(dateToo);
					dateTo = DataProccessor.addTimeInDate(dateTo);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// check data
			if (outletCode == null)
				outletCode = null;
			if (candidateName == null)
				candidateName = null;
			if (uniqueCode == null) {
				uniqueCode = null;
			}
			if (designation == null)
				designation = null;
			if (mspin == null || mspin.equals(""))
				System.out.println("MSPPPPPPPPPPPPP :" + mspin);
			mspin = null;
			System.out.println("MSPPPPPPPPPPPPP :" + mspin);
			if (passFailStatus.equals("1") || passFailStatus.equals("0")) {
				passFStatus.add(Integer.valueOf(passFailStatus));
			} else {
				passFStatus.add(1);
				passFStatus.add(0);
			}
			
			String role = session.getAttribute("role").toString();
			List<ParticipantRegistration> partList = new ArrayList<ParticipantRegistration>();
			List<Outlet> outletList = null;
			if (role.equalsIgnoreCase("DL")) {
				Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
				// outletList=outletService.getOutletByDealerId(dealerId);
				if (dateFrom != null && dateTo != null) {
					partList = participantService.getParticipantFilterInpprocess(outletCode, candidateName, designation,
							mspin, passFStatus, uniqueCode, dealerId, dateFrom, dateTo);
				} else {
					partList = participantService.getParticipantByFilterData2(outletCode, candidateName, designation,
							mspin, passFStatus, dealerId, uniqueCode);
				}
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				List<Long> list = new ArrayList<>();
				Optional<FSDM> f = fsdmservice.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet3 : outletList) {
						list.add(outlet3.getDealer().getId());
					}
				}
				List<ParticipantRegistration> participantList = new ArrayList<ParticipantRegistration>();
				if (dateFrom != null && dateTo != null) {
					participantList = participantService.getParticipantByFilterDataForFSDM(outletCode, candidateName,
							designation, mspin, passFStatus, uniqueCode, list, dateFrom, dateTo);
				} else {
					participantList = participantService.getParticipantByFilterDataForFSDM2(outletCode, candidateName,
							designation, mspin, passFStatus, list, uniqueCode);
				}
				for (final ParticipantRegistration p : participantList) {
		            if (p.getTestStatus() != null && Integer.parseInt(p.getTestStatus()) == 3 && (p.getFsdmApprovalStatus() == null  || Integer.parseInt(p.getFsdmApprovalStatus()) == 1) && (p.getStatus() == null || !p.getStatus().equals("H"))) {
		            	partList.add(p);
		            }
			    }
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
			for (ParticipantRegistration pr : partList) {
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
			System.out.println("Data added in CSV file.");
			String responseExcelUrl = "In Progress.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
				System.out.println("Error..excel.." + e);
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

	@PostMapping("/csvEmployee")
	public @ResponseBody ResponseEntity<?> getCSVEmployee(@RequestParam("outlet") String outletCode,
			@RequestParam("candidate") String candidateName, @RequestParam("unique") String uniqueCode,
			@RequestParam("desig") String designation, @RequestParam("mspinS") String mspin,
			@RequestParam("pass") String passFailStatus, @RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) throws FileNotFoundException {

		if (session.getAttribute("userId") != null) {
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
			cell.setCellValue("Re-attemp");
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

			Date dateFrom = null;
			Date dateTo = null;
			Long dealerId = Long.parseLong(session.getAttribute("userId").toString());
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
			// check data
			if (outletCode == null)
				outletCode = "";
			if (candidateName == null)
				candidateName = "";
			if (uniqueCode == null) {
				uniqueCode = "";
			}
			if (designation == null)
				designation = "";
			if (mspin == null || mspin == "")
				mspin = "";
			if (passFailStatus == "" || passFailStatus == ",") {
				passFStatus.add(1);
				passFStatus.add(0);
			} else {
				System.out.println(passFailStatus);
				passFStatus.add(Integer.valueOf(passFailStatus));
			}
			System.out
					.println("Date in CSV : " + dateFrom + ",    To Date " + dateTo + "     Name :::" + candidateName);
			final String fsdmApprovalStatus = "2";
			String role = session.getAttribute("role").toString();
			List<ParticipantRegistration> participantList = null;
			List<Outlet> outletList = null;
			if (role.equalsIgnoreCase("DL")) {
				outletList = outletService.getOutletByDealerId(dealerId);
				if (dateFrom != null && dateTo != null) {
					participantList = participantService.getParticipantOnEmployeeMasterDealerByFilterData(outletCode,
							candidateName, designation, mspin, passFStatus, uniqueCode, dealerId, dateFrom, dateTo,
							fsdmApprovalStatus);
				} else {
					System.out.println("ou........." + outletCode + "...c....." + candidateName + "..d.." + designation
							+ ".....ms..." + mspin + "..ps...." + passFStatus + "..u.." + uniqueCode + "...dl..."
							+ dealerId);
					participantList = participantService.getParticipantOnEmployeeMasterDealerByFilterData(outletCode,
							candidateName, designation, mspin, passFStatus, uniqueCode, dealerId, "2");
				}
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				List<Long> list = new ArrayList<>();
				Optional<FSDM> f = fsdmservice.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet3 : outletList) {
						list.add(outlet3.getDealer().getId());
					}
				}
				if (dateFrom != null && dateTo != null) {
					participantList = participantService.getParticipantByFilterDataForFSDM(outletCode, candidateName,
							designation, mspin, passFStatus, uniqueCode, list, dateFrom, dateTo);
				} else {

					participantList = participantService.getParticipantByFilterDataForFSDM2(outletCode, candidateName,
							designation, mspin, passFStatus, list, uniqueCode);
				}
			}

			List<ParticipantRegistration> partList = new ArrayList<ParticipantRegistration>();
			for(ParticipantRegistration p : participantList) {
				if(p.getTestStatus() !=null && Integer.parseInt(p.getTestStatus() )== 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus())==2)) {
					partList.add(p);
				}
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
			for (ParticipantRegistration pr : partList) {
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
			System.out.println("Data added in CSV file.");
			String responseExcelUrl = "Employee Master.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
				System.out.println("Error..excel.." + e);
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

	////

	@PostMapping("/getCSVPendingApprovel")
	public @ResponseBody ResponseEntity<?> getCSVPendingApprovel(@RequestParam("outlet") String outletCode,
			@RequestParam("candidate") String candidateName, @RequestParam("unique") String uniqueCode,
			@RequestParam("desig") String designation, @RequestParam("mspinS") String mspin,
			@RequestParam("pass") String passFailStatus, @RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) throws FileNotFoundException {

		if (session.getAttribute("userId") != null) {
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
			cell.setCellValue("Re-attemp");
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

			Date dateFrom = null;
			Date dateTo = null;

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
			// check data
			if (outletCode == null)
				outletCode = "";
			if (candidateName == null)
				candidateName = "";
			if (uniqueCode == null) {
				uniqueCode = "";
			}
			if (designation == null)
				designation = "";
			if (mspin == null || mspin == "")
				System.out.println("MSPINNNNNNNNNNNN : " + mspin);
			mspin = "";
			System.out.println("MSPINNNNNNNNNNNN : " + mspin);
			if (passFailStatus == "" || passFailStatus == ",") {
				passFStatus.add(1);
				passFStatus.add(0);
			} else {
				System.out.println(passFailStatus);
				passFStatus.add(Integer.valueOf(passFailStatus));
			}
			String role = "";
			List<ParticipantRegistration> partList = new ArrayList<>();
			if (session.getAttribute("role") != null) {
				role = session.getAttribute("role").toString().trim();
			}
			if (role.equalsIgnoreCase("FS")) {
				int fsdmId = 0;
				if (session.getAttribute("userId") != null) {
					fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				}

				final Optional<FSDM> f = (Optional<FSDM>) this.fsdmservice.getFSDM(fsdmId);
				final List<Long> list = new ArrayList<Long>();
				for (final Region r : f.get().getRegion()) {
					final List<Outlet> outletLsit = (List<Outlet>) this.outletService.getOutletByRegion(r.getId());
					for (final Outlet outlet : outletLsit) {
						list.add(outlet.getDealer().getId());
					}
				}
				List<ParticipantRegistration> fsdmPart = null;
				if (dateFrom != null && dateTo != null) {
					fsdmPart = participantService.getParticipantByFilterDataForFSDM(outletCode, candidateName,
							designation, mspin, passFStatus, uniqueCode, list, dateFrom, dateTo);
				} else {
					fsdmPart = participantService.getParticipantByFilterDataForFSDM2(outletCode, candidateName,
							designation, mspin, passFStatus, list, uniqueCode);
				}
				for (ParticipantRegistration p : fsdmPart) {
					if (p.getTestStatus() != null && Integer.parseInt(p.getTestStatus()) == 3 && (p.getFsdmApprovalStatus() != null && Integer.parseInt(p.getFsdmApprovalStatus()) == 3) &&
		            		(p.getStatus() == null || !p.getStatus().equals("H"))) {
						partList.add(p);
					}
				}
			} else if (role.equalsIgnoreCase("HO")) {
				partList = participantService.getParticipantPendingApprovel();
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
			for (ParticipantRegistration pr : partList) {
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
			System.out.println("Data added in CSV file.");
			String responseExcelUrl = "Pending Approval.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
				System.out.println("Error..excel.." + e);
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

	// Report
	@GetMapping("/detailedCSV")
	public ResponseEntity<?> getReportDetailed(@RequestParam("dateFromm") String dateFromm, 
			@RequestParam("dateToo") String dateToo, HttpSession session,
			Model model) throws FileNotFoundException {
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(dateFromm!=null && dateFromm!="") {
					date1=sdf.parse(dateFromm);
			}
			if(dateToo!=null && dateToo!="") {
				date2=sdf.parse(dateToo);
				date2=DataProccessor.addTimeInDate(date2);
			}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
//		if (!dateRange.equalsIgnoreCase("Select Date")) {
//			String[] splitDate = dateRange.split("-");
//			try {
//				date1 = sdf.parse(splitDate[0].trim());
//				date2 = sdf.parse(splitDate[1].trim());
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//		}
		if (session.getAttribute("userId") != null) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Detailed");
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
			cell.setCellValue("Old MSPIN");
			cell = row.createCell(14);
			cell.setCellValue("Gender");
			cell = row.createCell(15);
			cell.setCellValue("DOB");
			cell = row.createCell(16);
			cell.setCellValue("DOJ");
			cell = row.createCell(17);
			cell.setCellValue("Mobile Number");
			cell = row.createCell(18);
			cell.setCellValue("Alternative Contact No.");
			cell = row.createCell(19);
			cell.setCellValue("Email Id");
			cell = row.createCell(20);
			cell.setCellValue("Candidate Status");
			cell = row.createCell(21);
			cell.setCellValue("Recruitment Stage");
			cell = row.createCell(22);
			cell.setCellValue("Remarks");
			cell = row.createCell(23);
			cell.setCellValue("Age (in years)");
			cell = row.createCell(24);
			cell.setCellValue("Pincode");
			cell = row.createCell(25);
			cell.setCellValue("Candidate City");
			cell = row.createCell(26);
			cell.setCellValue("Tehsil");
			cell = row.createCell(27);
			cell.setCellValue("Village");
			cell = row.createCell(28);
			cell.setCellValue("Highest Qualification");
			cell = row.createCell(29);
			cell.setCellValue("Primary Language");
			cell = row.createCell(30);
			cell.setCellValue("Secondary Language");
			cell = row.createCell(31);
			cell.setCellValue("Access Key");
			cell = row.createCell(32);
			cell.setCellValue("Registration Date");
			cell = row.createCell(33);
			cell.setCellValue("Assessment Completion Date");
			cell = row.createCell(34);
			cell.setCellValue("Aptitude Score");
			cell = row.createCell(35);
			cell.setCellValue("Attitude Score");
			cell = row.createCell(36);
			cell.setCellValue("Total Marks");
			cell = row.createCell(37);
			cell.setCellValue("Marks Obtained");
			cell = row.createCell(38);
			cell.setCellValue("Percentage");
			cell = row.createCell(39);
			cell.setCellValue("Assessment Status");
			cell = row.createCell(40);
			cell.setCellValue("Interview Date");
			cell = row.createCell(41);
			cell.setCellValue("Interview Score");
			cell = row.createCell(42);
			cell.setCellValue("Interview Status");
			cell = row.createCell(43);
			cell.setCellValue("Praarambh Status");
			cell = row.createCell(44);
			cell.setCellValue("FSDM Approval Status");
			cell = row.createCell(45);
			cell.setCellValue("FSDM Rejection Reason");
			cell = row.createCell(46);
			cell.setCellValue("FSDM Approval Date");
			cell = row.createCell(47);
			cell.setCellValue("Ownership of Two Wheeler");
			cell = row.createCell(48);
			cell.setCellValue("Ownership of Four Wheeler");
			cell = row.createCell(49);
			cell.setCellValue("Knows driving Two Wheeler");
			cell = row.createCell(50);
			cell.setCellValue("Knows driving Four Wheeler");
			cell = row.createCell(51);
			cell.setCellValue("MDS Certified");
			cell = row.createCell(52);
			cell.setCellValue("Driving License");
			cell = row.createCell(53);
			cell.setCellValue("License Number");
			cell = row.createCell(54);
			cell.setCellValue("Validity of License");
			cell = row.createCell(55);
			cell.setCellValue("Work Experience");
			cell = row.createCell(56);
			cell.setCellValue("Total Work Experience (in months)");
			cell = row.createCell(57);
			cell.setCellValue("Worked with MSIL Before");
			cell = row.createCell(58);
			cell.setCellValue("MSIL Experience (in months)");
			cell = row.createCell(59);
			cell.setCellValue("Auto Industry Background");
			cell = row.createCell(60);
			cell.setCellValue("Experience in Automobile Industry (in months)");
			cell = row.createCell(61);
			cell.setCellValue("Experience in Non-Automobile Industry (in months)");
			cell = row.createCell(62);
			cell.setCellValue("Previous Company");
			cell = row.createCell(63);
			cell.setCellValue("Recruitment Source");
			//cell = row.createCell(64);
			//cell.setCellValue("Recruitment Profile");
			cell = row.createCell(64);
			cell.setCellValue("ID Proof");
			cell = row.createCell(65);
			cell.setCellValue("ID Proof Number");
			cell = row.createCell(66);
			cell.setCellValue("Emp Salary (Per Month)");
			cell = row.createCell(67);
			cell.setCellValue("Data Science Ref ID");
			cell = row.createCell(68);
			cell.setCellValue("Data Science Prediction");
			cell = row.createCell(69);
			cell.setCellValue("Interview Based on Data Science");
			cell = row.createCell(70);
			cell.setCellValue("Reason (data science)");
			cell = row.createCell(71);
			cell.setCellValue("PF Number");
			cell = row.createCell(72);
			cell.setCellValue("Bank A/C number");
			cell = row.createCell(73);
			cell.setCellValue("ESI number");
			cell = row.createCell(74);
			cell.setCellValue("Marital Status");
			cell = row.createCell(75);
			cell.setCellValue("Blood Group");

			for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the row
				row.getCell(i).setCellStyle(style);// Set the style
			}

			Long adminId = Long.parseLong(session.getAttribute("userId").toString());
			List<ParticipantRegistration> partList = new ArrayList<ParticipantRegistration>();
			Map<String, String> cityMap = new HashMap<String, String>();
			Map<String, String> listMap = new HashMap<String, String>();
			List<StateCityPinDms> cities = scpServiceDms.getAllStateCityPinDms();
			List<ListDms> listDms = listService.getAllListDms();
			for (StateCityPinDms c : cities) {
				cityMap.put(c.getCityCode(), c.getCityDesc());
			}
			for (ListDms list : listDms) {
				listMap.put(list.getListCode(), list.getListDesc());
			}
			Map<String, String> desgMap = new HashMap<String, String>();
			List<Designation> listDesg = designationService.getAll();
			for (Designation list : listDesg) {
				desgMap.put(list.getDesignationCode(), list.getDesignationName());
			}
			// Tehsil Village mapping
			List<TehsilVillage> tvList = tehsilVillageService.getAllTehsilVillages();
			Map<String, String> tehsilMap = new HashMap<>();
			Map<String, String> villageMap = new HashMap<>();
			for (TehsilVillage t : tvList) {
				tehsilMap.put(t.getTehsilCode(), t.getTehsilDesc());
				villageMap.put(t.getVillageCode(), t.getVillageName());
			}
			List<Outlet> outletList = null;
			Date fromDate = new Date(), toDate = new Date();
			LocalDate currentDate = LocalDate.now();
			int currentMonthvalue = currentDate.getMonthValue();
			int yearValue = currentDate.getYear();
			LocalDate yearStartDate = null;
			if (date1 != null && date2 != null) {

				fromDate = date1;
				toDate = date2;

			} else {
				if (currentMonthvalue < 4) {
					YearMonth yearMonth = YearMonth.of(yearValue - 1, 4);
					yearStartDate = yearMonth.atDay(1);
					fromDate = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				} else {
					YearMonth yearMonth = YearMonth.of(yearValue, 4);
					yearStartDate = yearMonth.atDay(1);
					fromDate = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
				}
			}
			String role = session.getAttribute("role").toString();
			if (role.equalsIgnoreCase("DL")) {
				partList = participantService.getParticipantForDetailedByYears(adminId, fromDate, toDate);
				outletList = outletService.getOutletByDealerId(adminId);
			} else if (role.equalsIgnoreCase("FS")) {
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				List<Long> list = new ArrayList<>();
				Optional<FSDM> f = fsdmservice.getFSDM(fsdmId);
				for (Region r : f.get().getRegion()) {
					outletList = outletService.getOutletByRegion(r.getId());
					for (Outlet outlet3 : outletList) {
						list.add(outlet3.getDealer().getId());
					}
				}
				partList = participantService.getParticipantByDealerIdListAndDate(list, fromDate, toDate);
			} else {
				partList = participantService.findAllParticipantsByYear(fromDate, toDate);
				outletList = outletService.getAllOutlets();
			}
			List<ParticipantRegistration> participants= new ArrayList<ParticipantRegistration>();
			if(role.equalsIgnoreCase("DL")) {
				participants.addAll(partList);
			}else if (role.equalsIgnoreCase("FS") || role.equalsIgnoreCase("HO")) {
				for(ParticipantRegistration p : partList) {
					if((p.getFsdmApprovalStatus()!=null && (p.getFsdmApprovalStatus().equals("2") || p.getFsdmApprovalStatus().equals("3"))) || 
							(p.getPrarambhStatus()!=null && p.getPrarambhStatus().equals("1") ) || (p.getDocuments_status()!=null && p.getDocuments_status().equals("final"))){
						participants.add(p);
					}
				}
			}
			int count = 1;
			for (ParticipantRegistration pr : participants) {
				if (pr.getRegStatus() != null && pr.getRegStatus() != "") {
					DataScience dataScience = null;
					Optional<DataScience> dataScienceOpt = dataService.findByAccesskey(pr.getAccessKey());
					Optional<InterviewScore> intScore = interviewScoreService.findByAccesskey(pr.getAccessKey());
					if (dataScienceOpt.isPresent()) {
						dataScience = dataScienceOpt.get();
					} else {
						dataScience = new DataScience();
					}
					Outlet outlet = null;
					row = sheet.createRow(count);
					cell = row.createCell(0);
					cell.setCellValue(count);
					for (Outlet o : outletList) {
						if (o.getOutletCode().equalsIgnoreCase(pr.getOutletCode())) {
							outlet = o;
						}
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
					cell.setCellValue(pr.getOldMspin());
					cell = row.createCell(14);
					if (pr.getGender() != null && pr.getGender() != "") {
						cell.setCellValue(listMap.get(pr.getGender()));
					}
					cell = row.createCell(15);
					if (pr.getBirthDate() != null) {
						Date birthDate = DataProccessor.parseDate(pr.getBirthDate());
						if (birthDate != null) {
							cell.setCellValue(DataProccessor.dateToString(birthDate));
						} else {
							cell.setCellValue("");
						}
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(16);
					if (pr.getJoiningDate() != null) {
						Date joining = DataProccessor.parseDate(pr.getJoiningDate());
						if (joining != null) {
							cell.setCellValue(DataProccessor.dateToString(joining));
						} else {
							cell.setCellValue("");
						}
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(17);
					cell.setCellValue(pr.getMobile());
					cell = row.createCell(18);
					if (pr.getAlternateContactNumber() != null) {
						cell.setCellValue(pr.getAlternateContactNumber().toString());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(19);
					cell.setCellValue(pr.getEmail());
					cell = row.createCell(20);
					String candidateStatus = "";
					if (pr.getAccessKey() != null) {
						candidateStatus = "Registered";
					}
					if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
						if (pr.getPassFailStatus() != 0 && pr.getPassFailStatus() == 1) {
							candidateStatus = "Passed";
						}
					}
					if (pr.getInterviewScore() != null) {
						if (intScore.get().getPass_fail_status() != null
								&& intScore.get().getPass_fail_status() != "") {
							if (intScore.get().getPass_fail_status().equalsIgnoreCase("pass")) { // Interview Pass fail
																									// status
								candidateStatus = "Offered";
							}
						}
					}
					if (pr.getFsdmApprovalStatus() != null && pr.getFsdmApprovalStatus().equalsIgnoreCase("2")) {
						candidateStatus = "Recruited";
					}
					cell.setCellValue(candidateStatus);
					cell = row.createCell(21);
					String recruitmentStage = "";
					String remarks="";
					if(pr.getAccessKey()!=null) {
						recruitmentStage = "In Progress";
						remarks="Assessment Not Cleared";
					}
					if (pr.getTestStatus() != null) {
						if (pr.getTestStatus().equals("1") || pr.getTestStatus().equals("2")
								|| pr.getTestStatus().equals("3")) {
							recruitmentStage = "In Progress";
							remarks="Assessment Not Cleared";
						}
					}
					if(intScore.isPresent()) {
					if(intScore.get().getInterviewStatus() != null ) {
						if( intScore.get().getInterviewStatus().equals("Selected")) {
						recruitmentStage = "In Progress";
						remarks="Interview Cleared";
					  }
					}
					}
					if (pr.getFsdmApprovalStatus() != null) {
						if (pr.getFsdmApprovalStatus().equalsIgnoreCase("3") || pr.getFsdmApprovalStatus().equalsIgnoreCase("1")) {
							recruitmentStage = "In Progress";
							remarks="FSDM Approval Pending";
						}
					}
					if (pr.getFsdmApprovalStatus() != null) {
						if (pr.getFsdmApprovalStatus().equalsIgnoreCase("2")) {
							recruitmentStage = "Approved";
							remarks="";
						}
					}
					
					if((pr.getFinalDesignation() != null && pr.getFinalDesignation().equals("STR"))
							&& (pr.getPrarambhStatus() != null && pr.getPrarambhStatus().equals("1"))) {
						recruitmentStage = "In Progress";
						remarks="Praarambh Pending";
					}
					if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus().equals("2") && pr.getFsdmApprovalStatus()!=null 
							&& pr.getFsdmApprovalStatus().equals("3") && pr.getFinalDesignation()!=null && pr.getFinalDesignation().equals("STR")) {
						remarks="Assign Designation";
					}
					if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus().equals("2") && pr.getFsdmApprovalStatus()!=null 
							&& pr.getFsdmApprovalStatus().equals("3") && !pr.getFinalDesignation().equals("STR")) {
						remarks="FSDM Approval Pending";
					}
					if (pr.getStatus() != null) {
						if (pr.getStatus().equalsIgnoreCase("H")) {
							recruitmentStage = "On Hold";
						}
					}
					cell.setCellValue(recruitmentStage);
					//Remarks
					cell= row.createCell(22);
					cell.setCellValue(remarks);
					// Age in years
					String ageInYears = "";
					if (pr.getBirthDate() != null && pr.getBirthDate() != "") {
						ageInYears = DataProccessor
								.BirthDateInYearsConversion(DataProccessor.parseDate(pr.getBirthDate()));
					}
					cell = row.createCell(23);
					cell.setCellValue(ageInYears);
					cell = row.createCell(24);
					if (pr.getPin() != null) {
						cell.setCellValue(pr.getPin());
					}
					cell = row.createCell(25);
					if (pr.getCity() != null && pr.getCity() != "") {
						cell.setCellValue(cityMap.get(pr.getCity()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(26);
					if (pr.getTehsil() != null && pr.getTehsil() != "") {
						cell.setCellValue(tehsilMap.get(pr.getTehsil()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(27);
					if (pr.getVillage() != null && pr.getVillage() != "") {
						cell.setCellValue(villageMap.get(pr.getVillage()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(28);
					if (pr.getHighestQualification() != null && pr.getHighestQualification() != "") {
						cell.setCellValue(listMap.get(pr.getHighestQualification()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(29);
					if (pr.getPrimaryLanguage() != null && pr.getPrimaryLanguage() != "") {
						cell.setCellValue(listMap.get(pr.getPrimaryLanguage()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(30);
					if (pr.getSecondaryLanguage() != null && pr.getSecondaryLanguage() != "") {
						cell.setCellValue(listMap.get(pr.getSecondaryLanguage()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(31);
					cell.setCellValue(pr.getAccessKey());
					cell = row.createCell(32);
					if (pr.getRegistration_date() != null) {
						cell.setCellValue(DataProccessor.dateToString(pr.getRegistration_date()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(33);
					if (pr.getTestCompletionDate() != null) {
						cell.setCellValue(DataProccessor.dateToString(pr.getTestCompletionDate()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(34);
					if (pr.getAptitudeScore() != null) {
						cell.setCellValue(pr.getAptitudeScore());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(35);
					if (pr.getAttitudeScore() != null) {
						cell.setCellValue(pr.getAttitudeScore());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(36);
					if (pr.getTotalMark() != null) {
						cell.setCellValue(pr.getTotalMark());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(37);
					if (pr.getTestScore() != null) {
						cell.setCellValue(pr.getTestScore());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(38); // percentage
					// count percentage
					if (pr.getTestScore() != null && pr.getTotalMark() != null) {
						double score = Double.valueOf(pr.getTestScore());
						double total = Double.valueOf(pr.getTotalMark());
						double per = (score / total) * 100;
						double roundOf = Math.round(per * 100) / 100.00;
						cell.setCellValue(roundOf);
					}
					cell = row.createCell(39);
					if (pr.getTestStatus() != null && pr.getTestStatus().equalsIgnoreCase("3")) {
						if (pr.getPassFailStatus() == 1) {
							cell.setCellValue("Pass");
						} else if (pr.getPassFailStatus() == 0) {
							cell.setCellValue("Fail");
						}
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(40);
					if (pr.getInterviewDate() != null) {
						cell.setCellValue(DataProccessor.dateToString(pr.getInterviewDate()));
					}
					cell = row.createCell(41);
					if (pr.getInterviewScore() != null) {
						cell.setCellValue(pr.getInterviewScore());
					}
					cell = row.createCell(42);
					if (intScore.isPresent()) {
						cell.setCellValue(intScore.get().getInterviewStatus()); // Interview Status
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(43);
					String praarambhStatus="";
					if(pr.getPrarambhStatus()!=null && pr.getPrarambhStatus()!="") {
						if(pr.getPrarambhStatus().equals("1"))
							praarambhStatus="Pending";
						if(pr.getPrarambhStatus().equals("2"))
							praarambhStatus="Completed";
					}
					cell.setCellValue(praarambhStatus);
					cell = row.createCell(44);
					if (pr.getFsdmApprovalStatus() != null) {
						if (pr.getFsdmApprovalStatus().equals("1")) {
							cell.setCellValue("Rejected");
						} else if (pr.getFsdmApprovalStatus().equals("3")) {
							cell.setCellValue("Pending");
						} else if (pr.getFsdmApprovalStatus().equals("2")) {
							cell.setCellValue("Approved");
						}
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(45);
					String fsdmF = "";
					if (pr.getFsdmFeedback() != null)
						fsdmF = fsdmF + "Reason : " + pr.getFsdmFeedback();
					if (pr.getFsdmRejectionType() != null)
						fsdmF = fsdmF + "%nId Type : " + pr.getFsdmRejectionType();
					if (pr.getFsdmRejectionReason() != null)
						fsdmF = fsdmF + "%nDetails : " + pr.getFsdmRejectionReason();
					if (pr.getFsdmRejectionComment() != null)
						fsdmF = fsdmF + "%nComment : " + pr.getFsdmRejectionComment();
					cell.setCellValue(String.format(fsdmF));
					cell = row.createCell(46);
					if (pr.getFsdmApprovalDate() != null) {
						cell.setCellValue(DataProccessor.dateToString(pr.getFsdmApprovalDate())); // FSDM Approval Date
																									// set here
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(47);
					cell.setCellValue(pr.getOwnTwoWheeler());
					cell = row.createCell(48);
					cell.setCellValue(pr.getOwnFourWheeler());
					cell = row.createCell(49);
					cell.setCellValue(pr.getTwoWheeler());
					cell = row.createCell(50);
					cell.setCellValue(pr.getFourWheeler());
					cell = row.createCell(51);
					cell.setCellValue(pr.getMdsCertified());
					cell = row.createCell(52);
					cell.setCellValue(pr.getDL());
					cell = row.createCell(53);
					if (pr.getDL() != null && pr.getLicenseNo() != null) {
						if (pr.getDL().equalsIgnoreCase("Yes")) {
							cell.setCellValue(pr.getLicenseNo());
						}
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(54);
					if (pr.getValidityOfLicence() != null) {
						Date validity = DataProccessor.parseDate(pr.getValidityOfLicence());
						if (validity != null) {
							cell.setCellValue(DataProccessor.dateToString(validity));
						}
					}
					cell = row.createCell(55);
					if (pr.getFresher() != null && !pr.getFresher().equals("")) {
						cell.setCellValue(pr.getFresher().substring(0, 1).toUpperCase() + pr.getFresher().substring(1));
					} // Previous Company
					if (pr.getExperience() != null && !pr.getExperience().equals("")) {
						cell.setCellValue(
								pr.getExperience().substring(0, 1).toUpperCase() + pr.getExperience().substring(1)); // Previous
																														// Company
					}
					cell = row.createCell(56);
					if (pr.getTotal() != null) {
						cell.setCellValue(pr.getTotal());
					}
					cell = row.createCell(57);
					cell.setCellValue(pr.getWorkedWithMSILBefore());
					cell = row.createCell(58);
					cell.setCellValue(pr.getMsilExp()); // MSIL Work Experience in months
					cell = row.createCell(59);
					String autoIndustryBackground = "";
					List<WorkExperience> workList = pr.getWorkExperience();
					if (workList != null && workList.size() >= 1) {
						List<String> list1 = new ArrayList<String>();
						for (WorkExperience w : workList) {
							list1.add(w.getAutoIndustryExperience());
						}
						if (list1.contains("Yes") || list1.contains("yes")) {
							autoIndustryBackground = "Yes";
						} else {
							autoIndustryBackground = "No";
						}
					}
					if (pr.getAutoIndustryExperience() != null && pr.getAutoIndustryExperience() > 0) {
						autoIndustryBackground = "Yes";
					} else {
						autoIndustryBackground = "No";
					}
					cell.setCellValue(autoIndustryBackground); // Auto Industry background
					cell = row.createCell(60);
					if (pr.getAutoIndustryExperience() != null) {
						cell.setCellValue(pr.getAutoIndustryExperience());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(61);
					if (pr.getNonAutoIndustryExperience() != null) {
						cell.setCellValue(pr.getNonAutoIndustryExperience());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(62);
					/* privious company */
					String preCompanyName = "";
					if (pr.getWorkExperience() != null && pr.getWorkExperience().size() != 0) {
						for (WorkExperience w : pr.getWorkExperience()) {
							preCompanyName = w.getCompanyName();
						}
					}
					if (pr.getWorkExperience() != null && pr.getWorkExperience().size() > 1) {
						for (WorkExperience w : pr.getWorkExperience()) {
							preCompanyName += ", " + w.getCompanyName();
						}
					}
					cell.setCellValue(preCompanyName); // Previous Company

					cell = row.createCell(63);
					cell.setCellValue(pr.getSource());
					//cell = row.createCell(64);
					//cell.setCellValue(dataScience.getProfile()); // Rural/Urban from Data science
					cell = row.createCell(64);
					if (pr.getIdProof() != null && pr.getIdProof() != "") {
						cell.setCellValue(listMap.get(pr.getIdProof()));
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(65);
					if (pr.getAdharNumber() != null) {
						cell.setCellValue(pr.getAdharNumber().toString());
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(66);
					cell.setCellValue(pr.getEmpSalary());
					cell = row.createCell(67);
					cell.setCellValue(dataScience.getDataScienceReferenceId()); // Data Science Ref ID
					cell = row.createCell(68);
					cell.setCellValue(dataScience.getDataSciencePrediction()); // Data Sce Prediction
					cell = row.createCell(69);
					if (dataScience.getInterviewStatus() != null) {
						cell.setCellValue(dataScience.getInterviewStatus().substring(0, 1).toUpperCase()
								+ dataScience.getInterviewStatus().substring(1)); // Interview Based on Data sce
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(70);
					cell.setCellValue(dataScience.getReason()); // Reason Data sce
					cell = row.createCell(71);
					cell.setCellValue(pr.getPfNumber());
					cell = row.createCell(72);
					cell.setCellValue(pr.getBankAccountNumber());
					cell = row.createCell(73);
					cell.setCellValue(pr.getEsiNumber());
					cell = row.createCell(74);
					cell.setCellValue(pr.getMartialStatus());
					cell = row.createCell(75);
					if (pr.getBloodGroup() != null && pr.getBloodGroup() != "") {
						cell.setCellValue(listMap.get(pr.getBloodGroup()));
					} else {
						cell.setCellValue("");
					}
					count++;
				} // if
			} // for

			String responseExcelUrl = "Detailed.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			}
			File file = new File("Detailed.csv");
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= Detailed.csv");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		} else {
			return null;
		}
	}
	
	
	
}
