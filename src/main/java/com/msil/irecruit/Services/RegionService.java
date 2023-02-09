package com.msil.irecruit.Services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Repositories.RegionRepository;

@Service
@Transactional
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	public RegionService(RegionRepository regionRepository) {
		this.regionRepository=regionRepository;
	}
	
	public List<Region> saveAllCity(List<Region> regionList){
		return regionRepository.saveAll(regionList);
	}
	
	public List<Region> getAllRegion(){
		return regionRepository.findAll();
	}
	
	
	public Optional<Region>getReagion(String regionCode){
	return	 (Optional<Region>)this.regionRepository.findByregionCode(regionCode);
	}
	
	public boolean checkDuplicate(final String regionCode) {
        boolean ischecked = false;
        final Optional<Region> region = (Optional<Region>)this.regionRepository.findByregionCode(regionCode);
        if (region.isPresent()) {
            ischecked = true;
        }
        return ischecked;
    }

	public List<FSDM> getAllFSMByRegionId(List<Integer> regionList) {
		return regionRepository.getAllFSDMByRegionId(regionList);
	}

	public List<Region> getAllRegionByRegionIdList(List<Integer> regionList) {
		return regionRepository.getAllRegionByRegionIdList(regionList);
	}
	
	public List<Region> getAllRegionByRegionCodeList(List<String> regionList){
		return regionRepository.getAllRegionByRegionCodeList(regionList);
	}
	public List<Region> getRegionByFSDMId(Integer fId) {
		return regionRepository.getRegionByFSDMId(fId);
	}
	
}
