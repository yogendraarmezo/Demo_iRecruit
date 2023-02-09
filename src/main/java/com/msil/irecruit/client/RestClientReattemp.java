package com.msil.irecruit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientReattemp {
	
	
	@Autowired
	RestTemplate restTemplate;
	 @Value("${Ap.assessmentURL}")
		private String assessmentURL;
	
	public  String callClient(String accesskey) {
		
		    // String url="http://localhost:8081/ExideIndustries/pa/deleteAccesskey?accesskey="+accesskey;
			String url=assessmentURL+"pa/deleteAccesskey?accesskey="+accesskey;
				
				//ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
				
				
				 ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
				return response.getBody();
				

	}

}
