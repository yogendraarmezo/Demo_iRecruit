package com.msil.irecruit.Repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.Entities.AccessKeyMaster;

public interface AccessKeyMasterRepository extends JpaRepository<AccessKeyMaster,Long>{
	
	Optional<AccessKeyMaster> findByAccesskey(String accesskey);
	List<AccessKeyMaster> findByDealerIdOrderByModifiedDateDesc(Long dealerId);
	@Query("SELECT a FROM AccessKeyMaster a WHERE (a.dealerId=:dealerId) AND (a.createdDate>=:dateFrom AND a.createdDate<=:dateTo)")
	List<AccessKeyMaster> getByDealerIdAndDateRange(Long dealerId, Date dateFrom, Date dateTo);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE  AccessKeyMaster set modifiedDate=now() WHERE accesskey =:accesskey")
    public  void updateModiedDate(final String accesskey);

}
