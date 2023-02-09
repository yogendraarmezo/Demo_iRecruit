package com.msil.irecruit.dms.service;

import java.util.List;

import com.msil.irecruit.dms.entities.PinCode;

public interface PinCodeServiceDms {
	
	public void savePinCode(PinCode pinCode);
	
	public List<PinCode> getAllPinCodes();

	public List<String> getAllPinCodesOnly();

}
