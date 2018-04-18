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

        return allFriends;

    }


    public void addFriendship(Prijatelj k){
        prijateljRepo.save(k);
        RegistrovaniKorisnik posiljalac = userRepo.findByUserName(k.getPosiljalac().getUserName());
        RegistrovaniKorisnik primalac = userRepo.findByUserName(k.getPrimalac().getUserName());
        System.out.println("status: " + k.getStatus());
        if(k.getStatus().toString().equals("PRIHVACENO")){

        	 posiljalac.getPrijatelji().add(k);
        	 primalac.getPrijatelji().add(k);
             userRepo.save(posiljalac);
             userRepo.save(primalac);
             posiljalac = userRepo.findByUserName(k.getPosiljalac().getUserName());
             System.out.println("posiljalac sada ima prijatelja: " + posiljalac.getPrijatelji().size());
             primalac = userRepo.findByUserName(k.getPrimalac().getUserName());
             System.out.println("primalac sada ima prijatelja: " + primalac.getPrijatelji().size());
        }else if(k.getStatus().toString().equals("POSLATO")){
            System.out.println("nikom nista ovaj treba da prihvati");
            prijateljRepo.save(k);
        }else if(k.getStatus().toString().equals("PRIMLJENO")){
            primalac.getPrijatelji().remove(k);
            k.setStatus(StatusPrijateljstva.PRIHVACENO);
            prijateljRepo.save(k);
            posiljalac.getPrijatelji().add(k);
            primalac.getPrijatelji().add(k);
            userRepo.save(posiljalac);
            userRepo.save(primalac);

        }
       
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
            if(p.getPrimalac().getUserName().equals(username) && p.getStatus().toString().equals("POSLATO"))
                allReq.add(p.getPosiljalac());
        }
        return allReq;
    }

    public List<RegistrovaniKorisnik> getSentFriends(String username){
        List<RegistrovaniKorisnik> allReq = new ArrayList<>();
        for(Prijatelj p : prijateljRepo.findAll()){
            if(p.getPosiljalac().getUserName().equals(username) && p.getStatus().toString().equals("POSLATO"))
                allReq.add(p.getPrimalac());
        }
        return allReq;
    }


    public void remove(Prijatelj p) {
        prijateljRepo.delete(p);
    }

    public Prijatelj getPrimalacPosiljalac(String primalac, String posiljalac) {
        for(Prijatelj p : prijateljRepo.findAll()){
            if(p.getPrimalac().getUserName().equals(primalac) && p.getPosiljalac().getUserName().equals(posiljalac) && p.getStatus().toString().equals("POSLATO"))
                return p;
        }
        return null;
    }
}
