package com.isa.ISA.service;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RezervacijaService {

    @Autowired
    private RezervacijaRepository rezRepo;


    public Rezervacija getRez(Long id){
        return rezRepo.findOne(id);
    }

    public void addRez(Rezervacija r){
        rezRepo.save(r);
    }
}
