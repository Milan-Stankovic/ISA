package com.isa.ISA.service;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RezervacijaService {

    @Autowired
    private RezervacijaRepository rezRepo;


    public Rezervacija getRez(Long id){
        return rezRepo.findOne(id);
    }

    public List<Rezervacija> getRezByUser(String id){
        return rezRepo.findByRezervisaoUserName(id);
    }

    public List<Rezervacija> getAll(){
        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findAll().forEach(allP::add);
        return allP;

    }

    public void addRez(Rezervacija r){
        rezRepo.save(r);
    }
}
