package com.msil.irecruit.Services;

import java.util.Optional;

import com.msil.irecruit.Entities.HO;

public interface HOService {

	public void saveHO(HO ho);
	
	public Optional<HO> findHOByMspin(String mspin);
	
	public void deleteHO(Integer id);

	public Optional<HO> getByMspinAndPassword(String user, String password);
	
	public Optional<HO> getByMspinAndEmail(String mspin, String email);

	public void changeEmail(String mspin, String newEmail);

	public void changePassword(String mspin, String newPassword);
	
	 Optional<HO> findByIdAndEmail(int id,String email);
	 public void changeEmail(String email, int id);
	 Optional<HO> findById(int id);
}
