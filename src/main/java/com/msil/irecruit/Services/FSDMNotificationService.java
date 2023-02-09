package com.msil.irecruit.Services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.FSDMNotification;
import com.msil.irecruit.Repositories.FSDMNotificationRepository;

@Service
@Transactional
public class FSDMNotificationService {

	@Autowired
	public  FSDMNotificationRepository fsdmNotificationRepository;
	
	public FSDMNotificationService(FSDMNotificationRepository fsdmNotificationRepository) {
		this.fsdmNotificationRepository=fsdmNotificationRepository;
	}
	
	public void saveNotification(FSDMNotification fsdmNotification) {
		fsdmNotificationRepository.save(fsdmNotification);
	}
	
	public Optional<FSDMNotification> getAccesskey(String accesskey){
		return fsdmNotificationRepository.findByAccesskey(accesskey);
	}
	
	public List<FSDMNotification> getByFSDMId(Integer fsdmId){
		return fsdmNotificationRepository.findByFsdmId(fsdmId);
	}
	public void dellete(int id){
		 fsdmNotificationRepository.deleteById(id);
	}
}
