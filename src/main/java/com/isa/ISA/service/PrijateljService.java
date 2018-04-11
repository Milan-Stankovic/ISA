package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.enums.StatusPrijateljstva;
import com.isa.ISA.dbModel.korisnici.Prijatelj;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.PrijateljRepository;
import com.isa.ISA.repository.UserRepository;

@Service
public class PrijateljService {
	@Autowired
    private PrijateljRepository prijateljRepo;
	@Autowired
    private UserRepository userRepo;

    public List<Prijatelj> getAllFriends(){
        List<Prijatelj> allFriends = new ArrayList<>();
        prijateljRepo.findAll().forEach(allFriends::add);
        System.out.println(allFriends.size());
        return allFriends;

    }

/*    public Prijatelj getUser(String username){
        return prijateljRepo.findByUserName(username);
    }*/


    public void addFriendship(Prijatelj k){
        prijateljRepo.save(k);
        RegistrovaniKorisnik posiljalac = userRepo.findByUserName(k.getPosiljalac().getUserName());
        RegistrovaniKorisnik primalac = userRepo.findByUserName(k.getPrimalac().getUserName());
        System.out.println("status: " + k.getStatus());
        if(k.getStatus().toString().equals("PRIHVACENO")){
        	System.out.println("dodao bih prijateljstvo al nece");
        	/* posiljalac.getPrijatelji().add(k);
        	 primalac.getPrijatelji().add(k);
             userRepo.save(posiljalac);
             userRepo.save(primalac);*/
        }else System.out.println("NISATAAAAAAAAAAAAA");
       
    }
    
    public List<Prijatelj> getUserFriends(String username){
    	List<Prijatelj> allFriends = new ArrayList<>();
    	for(Prijatelj p : prijateljRepo.findAll())
    		if(p.getPosiljalac().getUserName().equals(username))
    			allFriends.add(p);
    		else if(p.getPrimalac().getUserName().equals(username) && p.getStatus().equals("PRIHVACENO"))
    			allFriends.add(p);
    	return allFriends;
    }

	public void connectFriends() {
		for(RegistrovaniKorisnik r : userRepo.findAll()){
			for(Prijatelj p : prijateljRepo.findAll()){
				if(p.getPosiljalac().getUserName().equals(r.getUserName())){
					if(r.getPrijatelji()==null)
						r.setPrijatelji(new ArrayList<Prijatelj>());
					r.getPrijatelji().add(p);
				}
				else if(p.getPrimalac().getUserName().equals(r.getUserName())){
					if(r.getPrijatelji()==null)
						r.setPrijatelji(new ArrayList<Prijatelj>());
					r.getPrijatelji().add(p);
				}
			}
			
			userRepo.save(r);
		}
			
		
				
					
						
		
	}

	/*public RegistrovaniKorisnik findByEmail(String email) {
		// TODO Auto-generated method stub
		return prijateljRepo.findByEmail(email);
	}*/


}
