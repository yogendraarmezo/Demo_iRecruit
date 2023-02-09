package com.msil.irecruit.ServicesImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.OnScreenToggle;
import com.msil.irecruit.Repositories.OnScreenToggleRepository;
import com.msil.irecruit.Services.OnScreenToggleService;

@Service
public class OnScreenToggleServiceImpl implements OnScreenToggleService {
	
	@Autowired
	private OnScreenToggleRepository toggleRepo;

	@Override
	public void saveScreenToggle(OnScreenToggle toggle) {
		toggleRepo.save(toggle);
	}

	@Override
	public void deleteById(Long id) {
		toggleRepo.deleteById(id);
	}
	@Override
	public Optional<OnScreenToggle> getOneScreenToggleByAdminId(Long adminId) {
		return toggleRepo.getOneToggleByAdminId(adminId);
	}

	@Override
	public Optional<OnScreenToggle> getOneScreenToggleByMspin(String mspin) {
		return toggleRepo.getOneToggleByMspin(mspin);
	}
	@Override
	public Optional<OnScreenToggle> getOneScreenToggleByAdminIdAndRole(Long adminId, String role) {
		return toggleRepo.findByAdminIdAndRole(adminId,role);
	}
	
	@Override
	public Optional<OnScreenToggle> getOneScreenToggleByAdminIdAndModule(Long adminId, String module) {
		Optional<OnScreenToggle> toogle =toggleRepo.getOneToggleByAdminModule(adminId,module);
		System.out.println("Admin Id : "+adminId+"<> Module : "+module);
		return toogle;
	}

	@Override
	public void deleteOneOnScreenToggleByAdminIdAndModule(Long adminId, String module) {
	//	toggleRepo.deleteOnScreenToggleAdminIdModule(adminId,module);
	}

	@Override
	public void updateToggeleColumnByAdminIdAndModule(Long adminId, String module, String columnName, String status) {
		//toggleRepo.updateOneToggleColumnByColumnName(adminId,module,columnName,status);
	}

}
