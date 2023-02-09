package com.msil.irecruit.dms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.PinCode;
import com.msil.irecruit.dms.repository.PinCodeDmsRepository;
import com.msil.irecruit.dms.service.PinCodeServiceDms;

@Service
public class PinCodeServiceDmsImpl implements PinCodeServiceDms {

	@Autowired
	private PinCodeDmsRepository pinCodeRepository;
	@Override
	public void savePinCode(PinCode pinCode) {
		pinCodeRepository.save(pinCode);
	}

	@Override
	public List<PinCode> getAllPinCodes() {
		return pinCodeRepository.findAll();
	}
	@Override
	public List<String> getAllPinCodesOnly() {
		List<Integer> list = pinCodeRepository.getAllPinCodeOnly();
		List<String> list2 = new ArrayList<>();
		if(!list.isEmpty()) {
		for(Integer i:list) {
			list2.add(String.valueOf(i));
		}
		}
		return list2; 
	}

}
