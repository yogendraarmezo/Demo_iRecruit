package com.msil.irecruit.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer>{
public Optional<Region> findByregionCode(String RegionCode);

@Query("SELECT r.fsdm FROM Region r WHERE r.id IN :regionList")
public List<FSDM> getAllFSDMByRegionId(List<Integer> regionList);

@Query("SELECT r FROM Region r WHERE r.id IN :regionList")
public List<Region> getAllRegionByRegionIdList(List<Integer> regionList);

@Query("SELECT r FROM Region r WHERE r.regionCode IN :regionList")
public List<Region> getAllRegionByRegionCodeList(List<String> regionList);

@Query("SELECT r FROM Region r WHERE r.fsdm.id=:fId")
public List<Region> getRegionByFSDMId(Integer fId);
	
	//public List<Region> findByFSDM(FSDM fsdm);
}
