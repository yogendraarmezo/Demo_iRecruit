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
import org.springframework.web.bind.annotation.RequestParam;

import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Entities.WorkExperience;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.Services.FSDMService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParticipantService;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.service.ListDmsService;
import com.msil.irecruit.utils.DataProccessor;

@Controller
public class DataScienceCSVReportController {
	@Autowired
	private DataScienceService DSService;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private OutletService outletService;
	@Autowired
	private FSDMService fsdmService;
	@Autowired
	private ListDmsService listService;
	
	@GetMapping("/dataScienceCSV")
	public ResponseEntity<?> getReportDetailed(@RequestParam("dateFromm") String dateFromm, @RequestParam("dateToo") String dateToo, HttpSession session, Model model) throws FileNotFoundException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		Date dateFrom = new Date();
		Date dateTo = new Date();
		try {
			if (dateFromm != null && dateFromm != "") {
				date1 = sdf.parse(dateFromm);
			}
			if (dateToo != null && dateToo != "") {
				date2 = sdf.parse(dateToo);
				date2 = DataProccessor.addTimeInDate(date2);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//YTD Date By Default
		LocalDate currentDate = LocalDate.now();
		int currentMonthvalue = currentDate.getMonthValue();
		int yearValue = currentDate.getYear();
		LocalDate yearStartDate = null;
		if (date1 != null && date2 != null) {
			dateFrom = date1;
			dateTo = date2;
		} else {
			if (currentMonthvalue < 4) {
				YearMonth yearMonth = YearMonth.of(yearValue - 1, 4);
				yearStartDate = yearMonth.atDay(1);
				dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			} else {
				YearMonth yearMonth = YearMonth.of(yearValue, 4);
				yearStartDate = yearMonth.atDay(1);
				dateFrom = Date.from(yearStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
		}
		
		
		if (session.getAttribute("userId") != null) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("DataScience"); 
			
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
			cell.setCellValue("Dealer Name");
			cell = row.createCell(3);
			cell.setCellValue("Dealer Code");
			cell = row.createCell(4);
			cell.setCellValue("FOR Code");
			cell = row.createCell(5);
			cell.setCellValue("City");
			cell = row.createCell(6);
			cell.setCellValue("Recruitment Profile");
			cell = row.createCell(7);
			cell.setCellValue("Sales Role");
			cell = row.createCell(8);
			cell.setCellValue("Work Exp (Months)");
			cell = row.createCell(9);
			cell.setCellValue("Work with MSIL");
			cell = row.createCell(10);
			cell.setCellValue("MSIL Exp (Months)");
			cell = row.createCell(11);
			cell.setCellValue("Old MSPIN");
			cell = row.createCell(12);
			cell.setCellValue("Auto Industry Exp");
			cell = row.createCell(13);
			cell.setCellValue("Knows Driving");
			cell = row.createCell(14);
			cell.setCellValue("Driving Lic");
			cell = row.createCell(15);
			cell.setCellValue("MDS Certified");
			cell = row.createCell(16);
			cell.setCellValue("Own 2W");
			cell = row.createCell(17);
			cell.setCellValue("Highest Qualification");
			cell = row.createCell(18);
			cell.setCellValue("Gender");
			cell = row.createCell(19);
			cell.setCellValue("Age (Yrs)");
			cell = row.createCell(20);
			cell.setCellValue("Primary Lang");
			cell = row.createCell(21);
			cell.setCellValue("Second Lang");
			cell = row.createCell(22);
			cell.setCellValue("Marital Status");
			cell = row.createCell(23);
			cell.setCellValue("Data Science Prediction");
			cell = row.createCell(24);
			cell.setCellValue("Data Science Interview");
			cell = row.createCell(25);
			cell.setCellValue("Reasons");
			cell = row.createCell(26);
			cell.setCellValue("MSPIN");
			cell = row.createCell(27);
			cell.setCellValue("REF_ID");
			
			for(int i = 0; i < row.getLastCellNum(); i++){//For each cell in the row 
				row.getCell(i).setCellStyle(style);//Set the style
			}
			
			
			
			
			Long adminId = Long.parseLong(session.getAttribute("userId").toString());
			List<ParticipantRegistration> partList = new ArrayList<ParticipantRegistration>();
			List<Outlet> outletList = null;
			
			String role = session.getAttribute("role").toString();
			if(role.equalsIgnoreCase("DL")) {
				partList= participantService.getParticipantForDetailedByYears(adminId,dateFrom,dateTo);
				outletList=outletService.getOutletByDealerId(adminId);					
			}else if (role.equalsIgnoreCase("FSDM")) {					
				int fsdmId = Integer.parseInt(session.getAttribute("userId").toString());
				List<Long>list = new ArrayList<>();
				Optional<FSDM> f =	fsdmService.getFSDM(fsdmId);
				for(Region r: f.get().getRegion()) {
					outletList=	outletService.getOutletByRegion(r.getId());
					for(Outlet outlet3: outletList) {
						list.add(outlet3.getDealer().getId());
					}
				}
				partList = participantService.getParticipantByDealerIdListAndDate(list,dateFrom,dateTo);
			}else {
				partList=participantService.findAllParticipantsByYear(dateFrom,dateTo);
				outletList=outletService.getAllOutlets();
			}
			
			
			int count = 1;
			Map<String, String> listMap = new HashMap<String,String>();
			List<ListDms> dmsList = listService.getAllListDms();
			for(ListDms list : dmsList) {
				listMap.put(list.getListCode(), list.getListDesc());
			}
			for(ParticipantRegistration pr : partList) {
				if(pr.getDesignation()!=null && pr.getDesignation().equals("Sales")) {
					DataScience dataScience = new DataScience();
					 System.out.println(".......ee...............1");
					Optional<DataScience>	dataScienceOpt = DSService.findByAccesskey(pr.getAccessKey());
					 System.out.println(".......ee...............2");
					if(dataScienceOpt.isPresent()) {
						dataScience=dataScienceOpt.get();
					}
					Outlet outlet =null;
					row= sheet.createRow(count);
					cell=row.createCell(0);
					cell.setCellValue(count);
					//final Outlet outlet = participantService.getOutletByDealerIdAndOutletCode(adminId, pr.getOutletCode());
					for(Outlet o : outletList) {
						if(o.getOutletCode().equalsIgnoreCase(pr.getOutletCode())) {
							outlet=o;
						}
					}
                    if(outlet!=null) {
                    	cell = row.createCell(1);
						cell.setCellValue(outlet.getRegion().getRegionCode());
						cell = row.createCell(2);
						cell.setCellValue(outlet.getOutletName());
						cell = row.createCell(3);
						cell.setCellValue(dataScience.getDealerCode());
						cell = row.createCell(4);
						//For Code
						String code1= dataScience.getDealerCode();
						if(code1!=null && code1!="") {
						String[] codeSplit=code1.split("-");
						String	code3=codeSplit[1].trim();
						cell.setCellValue(code3);
						}else {
							cell.setCellValue("");
						}
						cell = row.createCell(5);
						cell.setCellValue(outlet.getCity().getCityName());
						                 		                    	
                    }else {
                    	cell = row.createCell(1);
						cell.setCellValue("");
						cell = row.createCell(2);
						cell.setCellValue("");
						cell = row.createCell(3);
						cell.setCellValue(dataScience.getDealerCode());
						cell = row.createCell(4);
						cell.setCellValue("");
						cell = row.createCell(5);
						cell.setCellValue("");                    	
                    }
                    cell = row.createCell(6);
					cell.setCellValue(dataScience.getProfile());
					cell = row.createCell(7);
					cell.setCellValue(dataScience.getDesignation());      // Sales Role
					String workExp ="";
					List<WorkExperience> workExpList = pr.getWorkExperience();
					if(!workExpList.isEmpty()) {
						int workInMonths=0;
						for(WorkExperience w : workExpList) {
							workInMonths = workInMonths+w.getExpInMths();
						}
						workExp=String.valueOf(workInMonths);
					}
					cell = row.createCell(8);
					cell.setCellValue(workExp);
					cell = row.createCell(9);
					cell.setCellValue(dataScience.getWorkedWithMSILBefore());
					cell = row.createCell(10);
					cell.setCellValue(dataScience.getMsilExp());
					cell = row.createCell(11);
					cell.setCellValue(dataScience.getOldMspin());
					cell = row.createCell(12);
					cell.setCellValue(dataScience.getAutoIndustryExperience());
					cell = row.createCell(13);
					cell.setCellValue(dataScience.getKnowDriving());
					cell = row.createCell(14);
					cell.setCellValue(dataScience.getDL());
					cell = row.createCell(15);
					cell.setCellValue(dataScience.getMdsCertified());
					cell = row.createCell(16);
					cell.setCellValue(dataScience.getOwnTwoWheeler());
					cell = row.createCell(17);
					if(dataScience.getHighestQualification()!=null && dataScience.getHighestQualification()!="") {
						cell.setCellValue(listMap.get(dataScience.getHighestQualification()));
					}
					cell = row.createCell(18);
					if(dataScience.getGender()!=null && dataScience.getGender()!="") {
						cell.setCellValue(listMap.get(dataScience.getGender()));
					}
					cell = row.createCell(19);
					cell.setCellValue(dataScience.getAge());
					cell = row.createCell(20);
					if(dataScience.getPrimaryLanguage()!=null && dataScience.getPrimaryLanguage()!="") {
						cell.setCellValue(listMap.get(dataScience.getPrimaryLanguage()));      
					}else {
						cell.setCellValue("");
					}
					cell = row.createCell(21);
					if(dataScience.getSecondaryLanguage()!=null && dataScience.getSecondaryLanguage()!="") {
						cell.setCellValue(listMap.get(dataScience.getSecondaryLanguage())); 
					}else {
						cell.setCellValue("");
					}
					cell = row.createCell(22);
					cell.setCellValue(dataScience.getMartialStatus());
					cell = row.createCell(23);
					cell.setCellValue(dataScience.getDataSciencePrediction());
					cell = row.createCell(24);
					if (dataScience.getInterviewStatus() != null) {
						cell.setCellValue(dataScience.getInterviewStatus().substring(0, 1).toUpperCase()
								+ dataScience.getInterviewStatus().substring(1)); // Interview Based on Data sce
					} else {
						cell.setCellValue("");
					}
					cell = row.createCell(25);
					cell.setCellValue(dataScience.getReason());
					cell = row.createCell(26);
					cell.setCellValue(pr.getMspin());
					cell = row.createCell(27);
					cell.setCellValue(dataScience.getDataScienceReferenceId());                    
                    count++;
				}//if
			}//for
			
			
			System.out.println("Data added in CSV file.");
			String responseExcelUrl = "DataScience.csv";
			try (FileOutputStream outputStream = new FileOutputStream(responseExcelUrl)) {
				workbook.write(outputStream);
			} catch (Exception e) {
			System.out.println("Error..excel.."+e);
			}			
			    File file = new File("DataScience.csv");
		        HttpHeaders header = new HttpHeaders();
		        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= DataScience.csv");
		        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		        header.add("Pragma", "no-cache");
		        header.add("Expires", "0");
		    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));			
			return ResponseEntity.ok()
		            .headers(header)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
			
		}else {
			return null;
		}
	}

}
