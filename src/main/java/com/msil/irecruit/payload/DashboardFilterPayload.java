package com.msil.irecruit.payload;

public class DashboardFilterPayload {
	private String regionCode;
	private String stateCode;
	private String cityCode;
	private Long dealerId;
	private String outletCode;
	private String dealershipCode;
	private String dateRange;
	private String dateFrom;
	private String dateTo;
	
	public DashboardFilterPayload() {
	}

	


	public DashboardFilterPayload(String regionCode, String stateCode, String cityCode, Long dealerId,
			String outletCode, String dealershipCode, String dateRange, String dateFrom, String dateTo) {
		super();
		this.regionCode = regionCode;
		this.stateCode = stateCode;
		this.cityCode = cityCode;
		this.dealerId = dealerId;
		this.outletCode = outletCode;
		this.dealershipCode = dealershipCode;
		this.dateRange = dateRange;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}




	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	

	public String getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}


	public String getDateTo() {
		return dateTo;
	}


	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDealershipCode() {
		return dealershipCode;
	}

	public void setDealershipCode(String dealershipCode) {
		this.dealershipCode = dealershipCode;
	}


	@Override
	public String toString() {
		return "DashboardFilterPayload [regionCode=" + regionCode + ", stateCode=" + stateCode + ", cityCode="
				+ cityCode + ", dealerId=" + dealerId + ", outletCode=" + outletCode + ", dealershipCode="
				+ dealershipCode + ", dateRange=" + dateRange + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}

}
