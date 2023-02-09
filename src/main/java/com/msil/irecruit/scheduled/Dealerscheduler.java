package com.msil.irecruit.scheduled;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.dms.controller.DmsController;
import com.msil.irecruit.dms.payload.LoginKey;
import com.msil.irecruit.dms.payload.MSPINCursorPayload;
import com.msil.irecruit.dms.payload.MSPINSearch;
import com.msil.irecruit.dms.payload.PmcMspinAuth;


@Controller
public class Dealerscheduler {

	 @Autowired
	 OutletService outletService;	
	 @Autowired
	 private DealerService dealerService;
	
	
	public List<MSPINSearch> getMspin( String mspin) {
		String username="Administrator", password="m1cr0s0f+";
		DmsController dmsController = new DmsController();
		LoginKey loginKey = new LoginKey(username, password);
		ResponseEntity<String> responseEntity= dmsController.loginToDms(loginKey);
		System.out.println(responseEntity.getBody());
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		List<MSPINSearch>list = new ArrayList<>();
		if(!jsonObject.has("Message")) {
			String authKey = jsonObject.getString("AuthKey");
			PmcMspinAuth pmcMspinAuth = new PmcMspinAuth("1", mspin, authKey);
			ResponseEntity<String> responseEntity2= dmsController.searchMSPIN(pmcMspinAuth);
			MSPINCursorPayload mspinCursor = new MSPINCursorPayload();
			JSONObject jsonObject2= new JSONObject(responseEntity2.getBody());
			if(!jsonObject2.has("Message")) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					mspinCursor = mapper.readValue(responseEntity2.getBody(), MSPINCursorPayload.class);
					if(!mspinCursor.getP_LIST_CURSOR().isEmpty()) {
						for(MSPINSearch ms: mspinCursor.getP_LIST_CURSOR()) {
							list.add(ms);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
		}
		}
		
		return list;
	}
	
	@GetMapping("/updateOutlets")
	@ResponseBody
	public String updateLocation() {
		System.out.println("statr.....");
		List<Dealer> list =	dealerService.findByAll();
		System.out.println("list....."+list);
		for(Dealer d : list) {
			System.out.println("mspin....."+d.getMspin());
			List<MSPINSearch> l=	getMspin(d.getMspin());
			if(l.size()>0) {
				MSPINSearch m = l.get(0);
				
				Optional<Dealer>dealer = dealerService.findByMspin(d.getMspin());
				
				if(dealer.isPresent()) {
					//dealer.get().setDealerMapCode(m.getDEALER_MAP_CD());
					dealerService.updateDealer(dealer.get().getId(),m.getDEALER_MAP_CD());
					
					List<Outlet>outlet = outletService.findByDealerId(dealer.get().getId());
					for(Outlet o: outlet) {
						if(o.getLocation() != null && o.getLocation().length()>0) {
						outletService.updateOutlet(o.getOutletId(),m.getLOC_CD());
						}
					}
				}
			}
		}
		return "Success";
	}
	
}
