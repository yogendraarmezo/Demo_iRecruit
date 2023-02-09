package com.msil.irecruit.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.QuestiwiseReport;
import com.msil.irecruit.Repositories.QuestiwiseReportRepository;

@Service
public class QuestiwiseReportService {
	
@Autowired
private QuestiwiseReportRepository questiwiseReportRepository;


  public void saveAll(List<QuestiwiseReport>list) {
	questiwiseReportRepository.saveAll(list);
  }
  
  public List<QuestiwiseReport> getByAcesskey(String accesskey){
	return  questiwiseReportRepository.getByAcesskey(accesskey);
  }
}
