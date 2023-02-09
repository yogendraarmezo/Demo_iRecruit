package com.msil.irecruit.dms.controller;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msil.irecruit.Entities.DataScience;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.DataScienceService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.entities.ApiResponse;
import com.msil.irecruit.dms.entities.DataScienceAuth;
import com.msil.irecruit.dms.entities.DataScienceRequest;
import com.msil.irecruit.dms.entities.DataScienceResponse;
import com.msil.irecruit.dms.payload.CityListCursor;
import com.msil.irecruit.dms.service.impl.ApiResponseService;

@Controller
@RequestMapping("/dms")
public class DataSciencComtroller {

	 @Autowired
	DataScienceService dataScienceService;
	@Autowired
	private ParticipantServiceImpl participantservice;
	
	@Value("${Data.dataScienceAuthURL}")
	private String dataScienceAuthURL;
	
	@Value("${Data.dataScienceURL}")
	private String dataScienceURL;
	@Autowired
    ApiResponseService apiResponseService;
	
	 @PostMapping("/dataScienceLogin") 
			@ResponseBody
			public String  dataScienceLogin(){
				String url =dataScienceAuthURL;
				DataScienceAuth ath = new DataScienceAuth("RECRTUser","Co0$OGW8S415%cS","RECRT");
				RestTemplate template = new RestTemplate();
				ResponseEntity<String> responseEntity = template.postForEntity(url, ath, String.class);
				final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
				// Saving api response log in db
		        ApiResponse apiResponse = new ApiResponse();
				apiResponse.setApiResponce(jsonObject.toString());
				apiResponse.setApiDate(new Date());
				apiResponse.setAccesskey("");
				apiResponse.setApiName("LoginToDataScience");
				apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
				apiResponseService.save(apiResponse);
			return responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			}
			
			@GetMapping("/getpredictedProductivity")
			@ResponseBody
			public String getpredictedProductivity(@RequestBody DataScienceRequest request,@RequestParam("accesskey") final String accesskey) {
				System.out.println("Start..........1............");
				RestTemplate template = new RestTemplate();
				String token= dataScienceLogin();
				System.out.println(token);
				String url =dataScienceURL;
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Authorization", token);
			
				HttpEntity<DataScienceRequest> httpEntity = new HttpEntity<DataScienceRequest>(request, headers);
				ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
				ObjectMapper mapper = new ObjectMapper();
				try {
					System.out.println("cal..........1............"+response.getBody());
					
					DataScienceResponse	d=mapper.readValue(response.getBody(), DataScienceResponse.class);
					System.out.println("cal..........1dd............"+accesskey);	
					Optional<DataScience>data = dataScienceService.findByAccesskey(accesskey);
						
					if(data.isPresent()) {
						data.get().setDataSciencePrediction(d.getPredicted_Productivity_band());
						data.get().setDataScienceReferenceId(d.getRequest_ID());
						dataScienceService.save(data.get());
						System.out.println("cal..........2www");
					}	
					Optional<ParticipantRegistration>articipant  =	participantservice.findByAccesskey(accesskey);
					if(articipant.isPresent()) {
						participantservice.updateProductivityRefID(d.getRequest_ID(), accesskey);
					}
				}catch(Exception e) {
				System.out.println("Error..cal.........."+e);	
				}
				final JSONObject jsonObject = new JSONObject((String)response.getBody());
				// Saving api response log in db
		        ApiResponse apiResponse = new ApiResponse();
				apiResponse.setApiResponce(jsonObject.toString());
				apiResponse.setApiDate(new Date());
				apiResponse.setAccesskey("");
				apiResponse.setApiName("GetPredictedProductivity");
				apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
				apiResponseService.save(apiResponse);
				return response.getBody();
			}
}
