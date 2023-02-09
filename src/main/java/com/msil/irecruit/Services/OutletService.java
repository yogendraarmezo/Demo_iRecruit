package com.msil.irecruit.Services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.State;
import com.msil.irecruit.Repositories.OutletRepository;

@Service
@Transactional
public class OutletService {

//	@Autowired
	private OutletRepository outletRepository;
	
	public OutletService(OutletRepository outletRepository) {
		this.outletRepository=outletRepository;
	}
	
	public List<Outlet> getOutletByRegion(Integer regoin) {
		return outletRepository.getOutletByRgion(regoin);
	}
	
	public List<Outlet> saveAll(List<Outlet> Outlet) {
		return outletRepository.saveAll(Outlet);
	}


	public List<Outlet> findByDealerId(long dealerId) {
		Optional<List<Outlet>> outletList = outletRepository.findByDealerId(dealerId);
		if(outletList.isPresent()) {
		return outletList.get();
		}else {
			return new ArrayList<Outlet>();	
		}
	}

	public Optional<Outlet> getOutletByOutletNameAndDealerId(String outletName, Long dealerId) {
		Optional<Outlet> outletOptional = outletRepository.findByNameAndDealerId(outletName,dealerId);
		return outletOptional;
	}
	public Optional<Outlet> getOutletByOutletCodeAndDealerId(String outletCode, Long dealerId) {
		Optional<Outlet> outletOptional = outletRepository.findByOutletCodeAndDealerId(outletCode,dealerId);
		return outletOptional;
	}

	public List<Outlet> getOutletByCityCodes(List<String> citiCodes) {
		return outletRepository.findOutletByCityCodes(citiCodes);
	}

	public List<Integer> getParentDealerIdByCityCodes(List<String> citiCodes) {
		return outletRepository.findParentDealerIdByCityCodes(citiCodes);
	}

	public List<Integer> getOutletIdByPDCodes(List<String> pdCodeList) {
		return outletRepository.findDealerIdByPDCodes(pdCodeList);
	}

	public List<Integer> getOutletByOutletIds(List<Integer> outletIds) {
		System.out.println("By O ids");
		return outletRepository.findDealerIdsByOutletId(outletIds);
	}

	public List<Outlet> getAllOutletByPDCodeDealerMspinRegionCode(List<String> pdList,
			List<String> dList, List<String> rgList) {
		return outletRepository.findAllOutletByPDCodeDealerMspinRegionId(pdList,dList,rgList);
	}
	
	public List<State> getOutletByRgionList(List<Integer> regions) {
		return outletRepository.getOutletByRgionList(regions);
	}
	public List<Outlet> getOutletByDealerId(Long dealerId) {
		return outletRepository.findOutletByDealerId(dealerId);
	}

	
	public  List<Outlet> getDealerForHO(List<String> pdList, List<Long> dList,List<String> rgList,
			List<String> cityList,List<String> stateList,List<Integer> fList  ){
		return outletRepository.getDealerForHO(pdList,dList,rgList,cityList, stateList,fList );
	}


	//public Optional<Outlet> getOutletByOutletCodeAndDealerId(String outletCode, Long dealerId) {
	//	return outletRepository.getOutletByOutletCodeAndDealerId(outletCode, dealerId);


	public List<Outlet> getAllOutlets() {
		return outletRepository.findAll();
	}
	
	public List<Outlet> getOutletByRegoinStateCityDealerOutlet( String regionCode,String cityList,String stateList,String outletCode ){
		return outletRepository.getOutletByRegoinStateCityDealerOutlet(regionCode, cityList, stateList, outletCode);
	}

	public List<Outlet> getOutletsByPDCodes(List<String> pdCodeList) {
		return outletRepository.getOutletsByPDCodes(pdCodeList);
	}

	public List<Outlet> getDealerForHOFilter(List<String> rgList1, List<String> stList, List<String> ctList,
			List<String> pdList, List<Integer> fList1, List<Long> dList) {
		return outletRepository.getDealerForHOFilter(rgList1,stList,ctList,pdList,fList1,dList);
	}

	public List<Outlet> getOutletForDashboardFilterReport(Integer region, String stateCode, String cityCode,
			String dealership, String outletCode, Long dealerId) {
		return outletRepository.getOutletForDashboardFilterReport(region,stateCode,cityCode,dealership,outletCode,dealerId);
	}

	public List<Outlet> getOutletByStateCode(String stateCode) {
		
		return outletRepository.getOutletByStateCode(stateCode);
	}

	public List<Outlet> getOutletByRegionCode(String regionCode) {
		return outletRepository.getOutletByRegionCode(regionCode);
	}
	
	public Optional<Outlet> getOutletByOutletCode(String outletCode){
		return outletRepository.getOutletByOutletCode(outletCode);
	}
	public List<Outlet> getOutletForHoFilter(final String regionCode, final String stateCode, final String cityCode, final String parentDealerCode, final Integer fId, final Long dId) {
        return (List<Outlet>)this.outletRepository.getOutletForHoFilter(regionCode, stateCode, cityCode, parentDealerCode, fId, dId);
    }
	
	public int updateOutlet(Integer outletId,String location) {
		outletRepository.updateOutlet( outletId, location) ;
		return 1;
	}

}
