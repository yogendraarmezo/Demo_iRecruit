package com.msil.irecruit.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Repositories.DealerRepository;

@Service
@Transactional
public class DealerService {

	//@Autowired
	private DealerRepository dealerRepository;
	
	public DealerService(DealerRepository dealerRepository) {
		this.dealerRepository=dealerRepository;
	}
	

	public List<Dealer>  saveAll(List<Dealer>tdmList){
		return dealerRepository.saveAll(tdmList);
	}
	
	public List<Dealer>  getAllDeealer(){
		return dealerRepository.findAll();
	}
	
	public Optional<Dealer>  getByMspinAndPassword(String mspin,String password){
		return dealerRepository.findByMspinAndPassword(mspin,password);
	}
	
	public Optional<Dealer>  getById(long id){
		return dealerRepository.findById(id);
	}
	
	
	public boolean checkDuplicate(final String mspin) {
        boolean ischecked = false;
        final Optional<Dealer> dealer = dealerRepository.findByMspin(mspin);
        if (dealer.isPresent()) {
            ischecked = true;
        }
        return ischecked;
    }
	
	public Optional<Dealer> getByMSPIN(final String mspin) {
       
		return   dealerRepository.findByMspin(mspin);   
    }
	public Optional<Dealer> getByPassword(String password){
		return dealerRepository.findByPassword(password);
	}
	
	public Optional<Dealer> findByMspinAndEmail(String mspin,String email) {
		return dealerRepository.findByMspinAndEmail(mspin, email);
	}

	public Dealer save(Dealer d) {
		return dealerRepository.save(d);
	}

	public void addEmailAddress(Long id, String email) {
		dealerRepository.addEmailAddress(id,email);
		
	}


	public void changePassword(String mspin, String newPassword) {
		dealerRepository.changePassword(mspin, newPassword);
	}


	public Optional<Dealer> findByMspin(String mspin) {
		return dealerRepository.findByMspin(mspin);
	}


	public void changeEmail(String mspin, String newEmail) {
		dealerRepository.changeEmail(mspin,newEmail);
	}
	
	public void deactivateDealer(boolean status,Date deactivationDate, Long id) {
		dealerRepository.deactivateDealer(status,deactivationDate,id);
	}
	
	public List<Dealer> findByAll() {
		return dealerRepository.findAll();
	}
	
	 public int updateDealer(Long id,String dealerMapCode) {
		 dealerRepository.updateDealer( id, dealerMapCode);
		 return 1;
	}

}
