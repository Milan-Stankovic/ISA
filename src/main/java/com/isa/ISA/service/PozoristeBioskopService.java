package com.isa.ISA.service;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.enums.TipUstanove;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<PozoristeBioskop> allP = new ArrayList<>();
        pbRepository.findByTip(TipUstanove.POZORISTE).addAll(allP);
        //  System.out.println(allPB.size());
        return allP;
    }

    public List<PozoristeBioskop> getAllBioskop(){
        List<PozoristeBioskop> allB = new ArrayList<>();
        pbRepository.findByTip(TipUstanove.BIOSKOP).addAll(allB);
        //  System.out.println(allPB.size());
        return allB;
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


}
