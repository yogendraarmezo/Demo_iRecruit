package com.msil.irecruit.Services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msil.irecruit.Entities.FSDM;
import com.msil.irecruit.Repositories.FSDMRepository;



@Service
@Transactional
public class FSDMService {
	
	@Autowired
	private FSDMRepository fsdmepository;
	public FSDMService(FSDMRepository fsdmepository) {
		this.fsdmepository=fsdmepository;
	}
	public List<FSDM> saveAllFSDMS(List<FSDM> regionList){
		return fsdmepository.saveAll(regionList);
	}
	
	public List<FSDM> getAllFSDM(){
		return fsdmepository.findAll();
	}
	
	public Optional<FSDM> getByMspinAadPassword(String mspin,String password){
		return fsdmepository.findByMspinAndPassword(mspin,password);
	}
	
	public Optional<FSDM> getByPassword(String password){
		return fsdmepository.findByPassword(password);
	}
	
	public Optional<FSDM> getFSDM(int id){
		return fsdmepository.findById(id);
	}
	
	public boolean checkDuplicate(final String mspin) {
        boolean ischecked = false;
        final Optional<FSDM> fsdm = (Optional<FSDM>)this.fsdmepository.findByMspin(mspin);
        if (fsdm.isPresent()) {
            ischecked = true;
        }
        return ischecked;
    }

	public Optional<FSDM> findByMspinAndEmail(String mspin,String email) {
		return fsdmepository.findByMspinAndEmail(mspin, email);
	}
	
	public void addEmailAddressAfterLogin(String mspin, String email) {
		fsdmepository.addEmailAddressAfterLogin(mspin,email);
	}
	public void changePassword(String mspin, String confirmPassword) {
		fsdmepository.changePasswordByMspin(mspin,confirmPassword);
	}
	public Optional<FSDM> findByMspin(String mspin) {
		return fsdmepository.findByMspin(mspin);
	}

	public void changeEmail(String mspin, String newEmail) {
		fsdmepository.changeEmail(mspin, newEmail);
		
	}
	public void changeEmailById(int id,String email ) {
		fsdmepository.changeEmailById(id, email);
	}
	public Optional<FSDM>findByIdAndEmail(int id,String email){
		return fsdmepository.findByIdAndEmail(id, email);
	}
	public void updatePassword( int id,String password) {
		fsdmepository.updatePassword(  id, password);
	}
	
}
