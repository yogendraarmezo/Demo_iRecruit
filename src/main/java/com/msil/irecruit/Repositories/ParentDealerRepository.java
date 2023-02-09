package com.msil.irecruit.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msil.irecruit.Entities.ParentDealer;

public interface ParentDealerRepository extends JpaRepository<ParentDealer,Integer>{
	public Optional<ParentDealer> findByParentDealerNameAndParentDealerCode(String parentName,String parentCode);
	public Optional<ParentDealer> findByParentDealerName(String parentDealerName);
	public Optional<ParentDealer> findByParentDealerCode(String parentDealerCode);
	@Query("SELECT DISTINCT p FROM ParentDealer p WHERE p.id IN :parentDealerIdList")
	public List<ParentDealer> findByParentDealerIds(List<Integer> parentDealerIdList);
	
	@Query("SELECT DISTINCT(p) FROM ParentDealer p WHERE p.parentDealerName IN :parentDealerIdList")
	public List<ParentDealer> findByParentDealerName(List<String> parentDealerIdList);
}
