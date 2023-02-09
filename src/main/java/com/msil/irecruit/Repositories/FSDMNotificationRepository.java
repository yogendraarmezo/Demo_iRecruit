package com.msil.irecruit.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.FSDMNotification;

@Repository
public interface FSDMNotificationRepository extends JpaRepository<FSDMNotification,Integer>{

	public Optional<FSDMNotification> findByAccesskey(String accesskey);
	
	public List<FSDMNotification> findByFsdmId(Integer fsdmId);
}
