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


    public void delete(Long id){
        rezRepo.delete(id);
    }

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


    public List<Rezervacija> getQuckRezAdmin(Long pbId){

        List<Rezervacija> rez =rezRepo.findByRezervisao(null);
        System.out.println("NASAO REZERVACIJA : "+rez.size());


        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findByRezervisaoAndProjekcijaSalaUstanovaId(null, pbId).forEach(allP::add);
        //  for (Rezervacija r:allP){
        //    r.getProjekcija().ge

        //}

        return allP;

    }


    public List<Rezervacija> getQuckRez(Long pbId){
        List<Rezervacija> allP = new ArrayList<>();
        rezRepo.findByRezervisaoAndProjekcijaAktivnaAndProjekcijaSalaUstanovaId(null, true, pbId).forEach(allP::add); // opet mnogo kul ako radi
        System.out.println(allP.size());
      //  for (Rezervacija r:allP){
        //    r.getProjekcija().ge

        //}

        return allP;

    }


    public void addRez(Rezervacija r){
        rezRepo.save(r);
    }
}
