package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dodatno.Konverter;
import com.isa.ISA.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.repository.PozoristeBioskopRepository;

@Service
public class PozoristeBioskopService {

    @Autowired
    private PozoristeBioskopRepository pbRepository;

    public List<PozoristeBioskop> getAllPozoristeBioskop(){
        List<PozoristeBioskop> allPB = new ArrayList<>();
        pbRepository.findAll().forEach(allPB::add);
      //  System.out.println(allPB.size());
        return allPB;
    }

    public void addPozoristeBioskop(PozoristeBioskop pb){
        pbRepository.save(pb);
    }

    public PozoristeBioskop getPozoristeBioskop(Long l){
        return pbRepository.findOne(l);
    }

    public void updatePozoristeBioskop(PozoristeBioskop pb){
        pbRepository.save(pb);
    }

    public void deletePozoristeBioskop(Long id){
        pbRepository.delete(id);
    }

    public List<PozoristeBioskop> getAllPozoriste(){
       // List<PozoristeBioskop> allP = new ArrayList<>();

        return   pbRepository.findByTip(TipUstanove.POZORISTE);

}

    public List<PozoristeBioskop> getAllBioskop(){
      //  List<PozoristeBioskop> allB = new ArrayList<>();
        return pbRepository.findByTip(TipUstanove.BIOSKOP);
        //  System.out.println(allPB.size());

    }

    public void updateOcena(Long id, int ocena){
        PozoristeBioskop pb = pbRepository.findOne(id);
        int broj = pb.getBrojOcena();
        float prosecna = pb.getProsecnaOcena();
        float nova = (prosecna*broj + ocena)/(broj+1);
        pb.setBrojOcena(broj+1);
        pb.setProsecnaOcena(nova);
        pbRepository.save(pb);
    }


    public List<PozoristeBioskop> getPozoristeBioskop(String naziv){
        List<PozoristeBioskop> foundPB = new ArrayList<>();
        pbRepository.findByNaziv(naziv).forEach(foundPB::add);
        return foundPB;

    }









}
