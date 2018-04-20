package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.OneClickAfterDTO;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.korisnici.Poziv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepo;


    public List<RegistrovaniKorisnik> getAllUsers(){
        List<RegistrovaniKorisnik> allUsers = new ArrayList<>();
        usersRepo.findAll().forEach(allUsers::add);

        return allUsers;

    }

    public RegistrovaniKorisnik getUser(String username){
        return usersRepo.findByUserName(username);
    }

    public RegistrovaniKorisnik getUserID(Long id){
        return usersRepo.findById(id);
    }


    public void addUser(RegistrovaniKorisnik k){
        usersRepo.save(k);
    }

	public RegistrovaniKorisnik findByEmail(String email) {
		// TODO Auto-generated method stub
		return usersRepo.findByEmail(email);
	}

	public void updateUser(OneClickAfterDTO ocad){
        Poziv p = new Poziv();
        p.setId(ocad.getPozivId());
        Rezervacija r = new Rezervacija();
        r.setId(ocad.getRezervacijaID());
        RegistrovaniKorisnik reg = usersRepo.findOne(ocad.getUserId());
        reg.getPozivi().add(p);
        reg.getRezervacije().add(r);
        usersRepo.save(reg);
    }
	
	public List<RegistrovaniKorisnik> searchImePrezime(String ime, String prezime){
		return usersRepo.findByImeAndPrezimeIgnoreCase(ime, prezime);
	}
	public List<RegistrovaniKorisnik> searchIme(String ime){
		return usersRepo.findByImeIgnoreCase(ime);
	}
	public List<RegistrovaniKorisnik> searchPrezime(String prezime){
		return usersRepo.findByPrezimeIgnoreCase(prezime);
	}



}
