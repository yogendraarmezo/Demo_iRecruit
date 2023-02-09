package com.msil.irecruit.dms.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataScienceResponse {

	@JsonProperty
	private String Request_ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Predicted_Productivity_band;
	@JsonProperty
	private String ResidenceOf;
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> Dealer_Code;
	
	
	public String getRequest_ID() {
		return Request_ID;
	}
	public void setRequest_ID(String request_ID) {
		Request_ID = request_ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPredicted_Productivity_band() {
		return Predicted_Productivity_band;
	}
	public void setPredicted_Productivity_band(String predicted_Productivity_band) {
		Predicted_Productivity_band = predicted_Productivity_band;
	}
	public String getResidenceOf() {
		return ResidenceOf;
	}
	public void setResidenceOf(String residenceOf) {
		ResidenceOf = residenceOf;
	}
	public List<String> getDealer_Code() {
		return Dealer_Code;
	}
	public void setDealer_Code(List<String> dealer_Code) {
		Dealer_Code = dealer_Code;
	}
	
	
	
}
