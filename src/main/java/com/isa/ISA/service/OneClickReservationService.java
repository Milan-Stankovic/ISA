package com.isa.ISA.service;


import com.isa.ISA.dbModel.Rezervacija;

import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OneClickReservationService {

    @Autowired
    private RezervacijaRepository repo;

    @Autowired
    private PozivRepository pozRepo;

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            readOnly = false)
    public Rezervacija reserveSeat(Rezervacija r){ // Treba testirati

        Rezervacija r1 = repo.findOne(r.getId());
        Poziv p = r1.getUrezervaciji().get(0);
        if(r1.getRezervisao() == null) {
            r1.setRezervisao(r.getRezervisao());

            p.setStatus(Status.PRIHVACENO);
            p.setOsoba(r.getRezervisao());
            p.setPozvan(false);

            pozRepo.save(p);
            repo.save(r1);
        }
        return r1;
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
