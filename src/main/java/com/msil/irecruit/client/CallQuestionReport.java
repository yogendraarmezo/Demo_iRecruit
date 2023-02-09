package com.msil.irecruit.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Entities.QuestiwiseReport;
import com.msil.irecruit.Services.QuestiwiseReportService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;

@Service
public class CallQuestionReport {

	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ParticipantServiceImpl participantServiceImpl;
	@Autowired
	QuestiwiseReportService questiwiseReportService;
	@Value("${Ap.assessmentURL}")
	private String assessmentURL;
	
	public  void callClient() {
		System.out.println("init.............");
		List<ParticipantRegistration> list =participantServiceImpl.getParticipant();
		System.out.println("size............."+list.size());
		int j=0;
		for(ParticipantRegistration p:list) {
			//if(j==1) {
				//break;
			//}
			System.out.println("get acceskey.............");
			List<QuestiwiseReport>queList=	questiwiseReportService.getByAcesskey(p.getAccessKey());
			if(queList.size()==0) {
				System.out.println("get if.............");
			String url=assessmentURL+"pa/QuestionReportByAccesskey?accesskey="+p.getAccessKey()+"&testid=41";	
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			JSONArray arr = new JSONArray(response.getBody());
			List<QuestiwiseReport>saveList = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				QuestiwiseReport que = new QuestiwiseReport();
				JSONObject res = arr.getJSONObject(i); 
				que.setAccesskey(res.getString("accesskey"));
				que.setOption(res.getString("seloption"));
				que.setSrno(res.getInt("srno"));
				que.setQuestion(res.getInt("questionid"));
				que.setSecsion(res.getInt("sectionid"));
				saveList.add(que);
			}
			questiwiseReportService.saveAll(saveList);
			participantServiceImpl.updatequestionReportStatus(p.getAccessKey());
			System.out.println("update.............");
			}
			j++;
		}
	}
	
	
	public  void callClient(String accesskey) {
		
			System.out.println("get acceskey.............");
			List<QuestiwiseReport>queList=	questiwiseReportService.getByAcesskey(accesskey);
			if(queList.size()==0) {
				System.out.println("get if.............");
			String url=assessmentURL+"pa/QuestionReportByAccesskey?accesskey="+accesskey+"&testid=41";	
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			JSONArray arr = new JSONArray(response.getBody());
			List<QuestiwiseReport>saveList = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				QuestiwiseReport que = new QuestiwiseReport();
				JSONObject res = arr.getJSONObject(i); 
				que.setAccesskey(res.getString("accesskey"));
				que.setOption(res.getString("seloption"));
				que.setSrno(res.getInt("srno"));
				que.setQuestion(res.getInt("questionid"));
				que.setSecsion(res.getInt("sectionid"));
				saveList.add(que);
			}
			questiwiseReportService.saveAll(saveList);
			participantServiceImpl.updatequestionReportStatus(accesskey);
			System.out.println("update.............");
			}
		
		
	}
}
