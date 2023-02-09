package com.msil.irecruit.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.UserLoginLog;
import com.msil.irecruit.Repositories.UserLoginLogRepository;

@Service
public class UserLoginLogService {
	
	private  UserLoginLogRepository userLoginLogRepository;
	
	public UserLoginLogService(UserLoginLogRepository userLoginLogRepository) {
		this.userLoginLogRepository=userLoginLogRepository;
	}
	
	public Optional<UserLoginLog> getloginLog(String mspin){
		return userLoginLogRepository.findByMspin(mspin);
	}
	
	public void updateLoginLog(UserLoginLog userLoginLog){
		 userLoginLogRepository.save(userLoginLog);
	}
	

}
