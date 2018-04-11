package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.repository.DogadjajRepository;

@Service
public class DogadjajService {

    @Autowired
    private DogadjajRepository dogRepo;

    @Autowired
    private PozoristeBioskopService pozBiService;

    public List<Dogadjaj> getAllDogadjaj(){
        List<Dogadjaj> allDog = new ArrayList<>();
        dogRepo.findAll().forEach(allDog::add);
        return allDog;
    }

    public void addDogadjaj(Dogadjaj d){
        dogRepo.save(d);
    }

    public void updateDogadjaj(Dogadjaj d){
        dogRepo.save(d);
    }

    public void deleteDogadjaj(Long id){
        dogRepo.delete(id);
    }

    public Dogadjaj getDogadjaj(Long l){
        return dogRepo.findOne(l);
    }

    public List<Dogadjaj> getAllDogadjaj(String s){
        return dogRepo.findByNaziv(s);
    }

    public void oceniDogadjaj(int ambijentOcena, int projekcijaOcena, Long pozoristeBioskopId, Long dogadjajID ){
        Dogadjaj d = dogRepo.findOne(dogadjajID);
        int broj = d.getBrojOcena();
        float prosecna = d.getProsecnaOcena();
        float nova = (prosecna*broj + projekcijaOcena)/(broj+1);
        d.setBrojOcena(broj+1);
        d.setProsecnaOcena(nova);
        dogRepo.save(d);
        pozBiService.updateOcena(pozoristeBioskopId, ambijentOcena);
    }


}
