package com.msil.irecruit.dms.payload;

import java.util.List;

public class DealerPayload {

	private String mspin;
	private String dealer_name;
	private String dealer_mobile;
	private String dealer_email;
	private String dealer_map_code;
	private List<OutletPayload> OUTLET;
	public String getMspin() {
		return mspin;
	}
	public void setMspin(String mspin) {
		this.mspin = mspin;
	}
	public String getDealer_name() {
		return dealer_name;
	}
	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}
	public String getDealer_mobile() {
		return dealer_mobile;
	}
	public void setDealer_mobile(String dealer_mobile) {
		this.dealer_mobile = dealer_mobile;
	}
	public String getDealer_email() {
		return dealer_email;
	}
	public void setDealer_email(String dealer_email) {
		this.dealer_email = dealer_email;
	}
	public String getDealer_map_code() {
		return dealer_map_code;
	}
	public void setDealer_map_code(String dealer_map_code) {
		this.dealer_map_code = dealer_map_code;
	}
	public List<OutletPayload> getOUTLET() {
		return OUTLET;
	}
	public void setOUTLET(List<OutletPayload> oUTLET) {
		OUTLET = oUTLET;
	}
	
	
	
	
}
