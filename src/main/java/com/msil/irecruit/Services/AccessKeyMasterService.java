package com.msil.irecruit.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.AccessKeyMaster;
import com.msil.irecruit.Repositories.AccessKeyMasterRepository;

@Service
@Transactional
public class AccessKeyMasterService {
	
	@Autowired
	private  AccessKeyMasterRepository accessKeyMasterRepository;
	
	public Optional<AccessKeyMaster>getAccesskey(String accesskey){
		return accessKeyMasterRepository.findByAccesskey(accesskey);
	}
	
	public AccessKeyMaster updateAccesskey(AccessKeyMaster entity){
		return accessKeyMasterRepository.save(entity);
	}
	
	public List<AccessKeyMaster> saveAccesskey(List<AccessKeyMaster> keys){
		return accessKeyMasterRepository.saveAll(keys);
	}
	
	public List<AccessKeyMaster> getByDealer(Long dealerId){
		return accessKeyMasterRepository.findByDealerIdOrderByModifiedDateDesc(dealerId);
	}
	
	public boolean deleteByAccesskey(String accesskey){
		boolean status = false;
		Optional<AccessKeyMaster>key =	accessKeyMasterRepository.findByAccesskey(accesskey);
		if(key.isPresent()) {
		   accessKeyMasterRepository.delete(key.get());
		   status = true;
		}
		
		return status;
	}

	public List<AccessKeyMaster> getByDealerIdAndDateRange(Long dealerId, Date dateFrom, Date dateTo) {
		return accessKeyMasterRepository.getByDealerIdAndDateRange(dealerId,dateFrom,dateTo);
	}
	
	public void updateModiedDate(String accesskey) {
		 accessKeyMasterRepository.updateModiedDate(accesskey);
	}
	
	
	
	
}
