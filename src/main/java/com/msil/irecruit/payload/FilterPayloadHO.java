package com.msil.irecruit.payload;

public class FilterPayloadHO {
	private String regionCode;
    private String stateCode;
    private String cityCode;
    private String parentDealerCode;
    private String fsdmId;
    private String dealerId;
    private String dateFrom;
    private String dateTo;
    
    public FilterPayloadHO(final String regionCode, final String stateCode, final String cityCode, final String parentDealerCode, final String fsdmId, final String dealerId, final String dateFrom, final String dateTo) {
        this.regionCode = regionCode;
        this.stateCode = stateCode;
        this.cityCode = cityCode;
        this.parentDealerCode = parentDealerCode;
        this.fsdmId = fsdmId;
        this.dealerId = dealerId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    
    public String getRegionCode() {
        return this.regionCode;
    }
    
    public void setRegionCode(final String regionCode) {
        this.regionCode = regionCode;
    }
    
    public String getStateCode() {
        return this.stateCode;
    }
    
    public void setStateCode(final String stateCode) {
        this.stateCode = stateCode;
    }
    
    public String getCityCode() {
        return this.cityCode;
    }
    
    public void setCityCode(final String cityCode) {
        this.cityCode = cityCode;
    }
    
    public String getParentDealerCode() {
        return this.parentDealerCode;
    }
    
    public void setParentDealerCode(final String parentDealerCode) {
        this.parentDealerCode = parentDealerCode;
    }
    
    public String getFsdmId() {
        return this.fsdmId;
    }
    
    public void setFsdmId(final String fsdmId) {
        this.fsdmId = fsdmId;
    }
    
    public String getDealerId() {
        return this.dealerId;
    }
    
    public void setDealerId(final String dealerId) {
        this.dealerId = dealerId;
    }
    
    public String getDateFrom() {
        return this.dateFrom;
    }
    
    public void setDateFrom(final String dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public String getDateTo() {
        return this.dateTo;
    }
    
    public void setDateTo(final String dateTo) {
        this.dateTo = dateTo;
    }
    
    @Override
    public String toString() {
        return "FilterPayloadHO [regionCode=" + this.regionCode + ", stateCode=" + this.stateCode + ", cityCode=" + this.cityCode + ", parentDealerCode=" + this.parentDealerCode + ", fsdmId=" + this.fsdmId + ", dealerId=" + this.dealerId + ", dateFrom=" + this.dateFrom + ", dateTo=" + this.dateTo + "]";
    }
}
