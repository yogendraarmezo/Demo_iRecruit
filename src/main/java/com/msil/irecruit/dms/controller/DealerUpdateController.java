package com.msil.irecruit.dms.controller;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msil.irecruit.Entities.City;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParentDealer;
import com.msil.irecruit.Entities.Region;
import com.msil.irecruit.Services.CityService;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.Services.ParentDealerService;
import com.msil.irecruit.Services.RegionService;
import com.msil.irecruit.Services.StateService;
import com.msil.irecruit.dms.payload.DealerPayload;
import com.msil.irecruit.dms.payload.OutletPayload;
import com.msil.irecruit.utils.Accesskey;
import com.msil.irecruit.Entities.State;


@Controller
//@RequestMapping("/prarambh")
public class DealerUpdateController {

	    @Autowired
	    private OutletService outletService;
	    @Autowired
	    private DealerService dealerService;
	    @Autowired
	    private CityService cityService;
	    @Autowired
	    private StateService stateService;
	    @Autowired
	    private RegionService regionService;
	    @Autowired
	    private ParentDealerService parentDealerService;
	
	@PostMapping("/updateDealer")
	private ResponseEntity<?> getPrarambhStatus(@RequestBody DealerPayload dealerPayload){
		
		if((dealerPayload.getDealer_name().equals("string") && dealerPayload.getDealer_name().equals(""))  ||
			
		(dealerPayload.getDealer_email().equals("string") && dealerPayload.getDealer_email().equals("")) ||
			
		(dealerPayload.getDealer_map_code().equals("string") && dealerPayload.getDealer_map_code().equals("")) ||
			
		(dealerPayload.getDealer_mobile().equals("string") && dealerPayload.getDealer_mobile().equals("")) ) {
			
		
		Optional<Dealer> dealer = dealerService.getByMSPIN(dealerPayload.getMspin());
		if(dealer.isPresent()) {
			dealer.get().setName(dealerPayload.getDealer_name());
			dealer.get().setEmail(dealerPayload.getDealer_email());
			dealer.get().setMobile(dealerPayload.getDealer_mobile());
			dealer.get().setDealerMapCode(dealerPayload.getDealer_map_code());
			
			List<Outlet> outletList = dealer.get().getOutlet();
			List<Outlet> updateOutlet= new ArrayList<>();
			List<OutletPayload> list = dealerPayload.getOUTLET();
			for(OutletPayload outlet :list) {
				for(Outlet o : outletList) {
					if(o.getOutletCode() == outlet.getOutlet_code()){
						Optional<Outlet> updateOut = outletService.getOutletByOutletCode(o.getOutletCode());
						Optional<City> city = cityService.getCity(outlet.getCity_name());
						if(city.isPresent()) {
							updateOut.get().setCity(city.get());
						}
						Optional<State> state = stateService.getState(outlet.getState_name());
						if(state.isPresent()) {
							updateOut.get().setState(state.get());
						}
						Optional<Region>region = regionService.getReagion(outlet.getRegion_code());
						if(region.isPresent()) {
							updateOut.get().setRegion(region.get());
						}
						Optional<ParentDealer>ParentDealer = parentDealerService.getParentDealerCode(outlet.getParent_dealer_code());
						if(ParentDealer.isPresent()) {
							updateOut.get().setParentDealer(ParentDealer.get());	
						}
						updateOut.get().setLocation(outlet.getLocation());	
						updateOut.get().setDealer(dealer.get());
						updateOutlet.add(updateOut.get());
					}
				}
			}
			dealer.get().setOutlet(updateOutlet);
			dealerService.save(dealer.get());
			//outletService.saveAll(updateOutlet);
		}else {
			Dealer d = new Dealer();
			d.setName(dealerPayload.getDealer_name());
			d.setEmail(dealerPayload.getDealer_email());
			d.setMobile(dealerPayload.getDealer_mobile());
			d.setDealerMapCode(dealerPayload.getDealer_map_code());
			d.setMspin(dealerPayload.getMspin());
			d.setPassword(genratePasswordDealer());
			List<Outlet> updateOutlet= new ArrayList<>();
			List<OutletPayload> list = dealerPayload.getOUTLET();
			for(OutletPayload outlet :list) {
				
				Outlet o = new Outlet();
						//Optional<Outlet> updateOut = outletService.getOutletByOutletCode(outlet.getOUTLET_CODE());
						Optional<City> city = cityService.getCity(outlet.getCity_name());
						if(city.isPresent()) {
							o.setCity(city.get());
						}
						Optional<State> state = stateService.getState(outlet.getState_name());
						if(state.isPresent()) {
							o.setState(state.get());
						}
						Optional<Region>region = regionService.getReagion(outlet.getRegion_code());
						if(region.isPresent()) {
							o.setRegion(region.get());
						}
						Optional<ParentDealer>ParentDealer = parentDealerService.getParentDealerCode(outlet.getParent_dealer_code());
						if(ParentDealer.isPresent()) {
							o.setParentDealer(ParentDealer.get());	
						}
						o.setLocation(outlet.getLocation());	
						o.setOutletName(outlet.getOutlet_name());
						o.setOutletCode(outlet.getOutlet_code());
						o.setDealer(d);
						updateOutlet.add(o);
					
				
			}
			d.setOutlet(updateOutlet);
			dealerService.save(d);	
		}
		}
		return ResponseEntity.ok("Success");
	}
	
	private String genratePasswordDealer() {
		String password="";
		boolean check= false;
		while(!check) {
		password = Accesskey.getAccesskey();
		Optional<Dealer> tdm = dealerService.getByPassword(password);
		if(tdm.isPresent()) {
			check = false;
		}else {
			check = true; 
		}
		}
		return password;
	}
}
