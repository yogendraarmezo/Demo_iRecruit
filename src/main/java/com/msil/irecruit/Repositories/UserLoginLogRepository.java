package com.msil.irecruit.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msil.irecruit.Entities.UserLoginLog;

public interface UserLoginLogRepository   extends JpaRepository<UserLoginLog,Long>{
	
	Optional<UserLoginLog> findByMspin(String mspin);

}
