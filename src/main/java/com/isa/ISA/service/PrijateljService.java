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
        	 posiljalac.getPrijatelji().add(k);
        	 primalac.getPrijatelji().add(k);
             userRepo.save(posiljalac);
             userRepo.save(primalac);
             posiljalac = userRepo.findByUserName(k.getPosiljalac().getUserName());
             System.out.println("posiljalac sada ima prijatelja: " + posiljalac.getPrijatelji().size());
             primalac = userRepo.findByUserName(k.getPrimalac().getUserName());
             System.out.println("primalac sada ima prijatelja: " + primalac.getPrijatelji().size());
        }else System.out.println("NISATAAAAAAAAAAAAA");
       
    }
    
    public List<Prijatelj> getUserFriends(String username){
    	List<Prijatelj> allFriends = new ArrayList<>();
    	for(Prijatelj p : prijateljRepo.findAll()){
    		System.out.println("Posilja;ac: " + p.getPosiljalac().getUserName());
    		System.out.println("Status: " + p.getStatus().toString());
    		if(p.getPosiljalac().getUserName().equals(username) && p.getStatus().toString().equals("PRIHVACENO"))
    			allFriends.add(p);
    		System.out.println("Primalac: " + p.getPrimalac().getUserName());
    		System.out.println("Status: " + p.getStatus().toString());
    		if(p.getPrimalac().getUserName().equals(username) && p.getStatus().toString().equals("PRIHVACENO"))
    			allFriends.add(p);
    	}
    		
    	return allFriends;
    }

    
    public List<RegistrovaniKorisnik> getReqFriends(String username){
        List<RegistrovaniKorisnik> allReq = new ArrayList<>();
        for(Prijatelj p : prijateljRepo.findAll()){
            if(p.getPrimalac().getUserName().equals(username) && p.getStatus().toString().equals("PRIMLJENO"))
                allReq.add(p.getPosiljalac());
        }
        return allReq;
    }


}
