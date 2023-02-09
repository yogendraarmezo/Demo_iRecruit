package com.msil.irecruit.Services;

import java.util.Optional;

import com.msil.irecruit.Entities.OnScreenToggle;

public interface OnScreenToggleService {
	
	public void saveScreenToggle(OnScreenToggle toggle);
	public void deleteById(Long id);
	public Optional<OnScreenToggle> getOneScreenToggleByAdminId(Long adminId);
	public Optional<OnScreenToggle> getOneScreenToggleByMspin(String mspin);
	public Optional<OnScreenToggle> getOneScreenToggleByAdminIdAndRole(Long adminId, String role);
	public Optional<OnScreenToggle> getOneScreenToggleByAdminIdAndModule(Long adminId, String module);
	public void deleteOneOnScreenToggleByAdminIdAndModule(Long adminId, String module);
	public void updateToggeleColumnByAdminIdAndModule(Long adminId, String module, String columnName, String status);
	

}
