package com.msil.irecruit.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.ParentDealer;
import com.msil.irecruit.Repositories.ParentDealerRepository;

@Service
@Transactional
public class ParentDealerService {

	@Autowired
	private ParentDealerRepository parentDealerRepository;
	@Autowired
	private OutletService outletService;
	
	public List<ParentDealer> saveAllCity(List<ParentDealer> parentDealer){
		return parentDealerRepository.saveAll(parentDealer);
	}
	
	public Optional<ParentDealer> getParentDealer(String parentDealerName){
	return parentDealerRepository.findByParentDealerName(parentDealerName);
	}
	
	public Optional<ParentDealer> getParentDealerCode(String parentDealerCode){
		return parentDealerRepository.findByParentDealerCode(parentDealerCode);
		}
	
	public List<ParentDealer> getAllParentDealer(){
		return parentDealerRepository.findAll();
	}
	
	public boolean checkDuplicate(final String parentName, final String parentCode) {
        boolean ischecked = false;
        final Optional<ParentDealer> parentDealer = parentDealerRepository.findByParentDealerNameAndParentDealerCode(parentName, parentCode);
        if (parentDealer.isPresent()) {
            ischecked = true;
        }
        return ischecked;
    }

	public List<ParentDealer> getAllParentDealerByCityCode(List<String> citiCodes) {
		 Map<Integer,Integer> map = new HashMap<>();
		 List<Integer> result = map.keySet().stream()
	                .collect(Collectors.toList());
		List<Integer> parentDealerIdList=outletService.getParentDealerIdByCityCodes(citiCodes);
		List<ParentDealer> list = parentDealerRepository.findByParentDealerIds(parentDealerIdList);
		return list ;
	}
}
