package com.isa.ISA.service;


import com.isa.ISA.DTO.TransakcijaDTO;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.repository.RezervacijaRepository;


@Service
public class OneClickReservationService {

    @Autowired
    private RezervacijaRepository repo;

    @Autowired
    private PozivRepository pozRepo;

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public TransakcijaDTO reserveSeat(Long id, Long userId){

        TransakcijaDTO rezultat = new TransakcijaDTO();
        Rezervacija r = repo.findOne(id);
        Poziv p = r.getUrezervaciji().get(0);
        RegistrovaniKorisnik reg = new RegistrovaniKorisnik();
        reg.setId(userId);
        if(r.getRezervisao() == null) {
            r.setRezervisao(reg);
            p.setStatus(Status.PRIHVACENO);
            p.setOsoba(reg);
            p=pozRepo.save(p); //Iskreno izbegao bih ovo sa persistom iznad liste poziva u rezervaciji, ali nisam siguran kako su ostali koristili rezervacije
            rezultat.setPozivId(p.getId()); // Ovaj deo takodje ne bih morao odraditi
            repo.save(r); //Mogao bi puci program ako su oni rucno dodavali
            rezultat.setMessage("SUCCESS");
        }else{
            rezultat.setMessage("ERROR");
        }
        return rezultat;
    }



    public void setOneClick(Rezervacija r){
        if(repo.findOne(r.getId()).getRezervisao() == null)
            repo.save(r);
    }

    public void deleteOneClick(Long r){
        repo.delete(r);
    }

    public void updateOneClick(Rezervacija r){
        repo.save(r);
    }
}
