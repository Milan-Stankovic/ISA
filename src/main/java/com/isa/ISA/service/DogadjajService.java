package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.DogadjajDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dodatno.Konverter;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
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



    public void addDogadjaj(DogadjajDTO d){
        if(Konverter.proveraDogadjaja(d)){
            Dogadjaj dog = Konverter.converterDogadjaja(d);
            List<Projekcija> proj= new ArrayList<>();
            dog.setPrikazujeSe(proj);
            System.out.println("TU SAM WTF IZNAD SAVE DOG");
            dogRepo.save(dog);
           // dogRepo.save(Konverter.converterDogadjaja(d));
        }
    }


    public List<Dogadjaj> findByPozBi(Long id){
        PozoristeBioskop pb = new PozoristeBioskop();
        pb.setId(id);
        return dogRepo.findByMestoOdrzavanja(pb);
    }

    public void addDogadjaj2(Dogadjaj d){
            dogRepo.save(d);
    }

    public void updateDogadjaj(DogadjajDTO d, Long id){

        if(Konverter.proveraDogadjaja(d)) {
            Dogadjaj dog = Konverter.converterDogadjaja(d);
            dog.setId(id);
            dogRepo.save(dog);
        }
    }

    public void updateDogadjaj2(Dogadjaj d){
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
        if(broj ==0){
            d.setBrojOcena(1);
            d.setProsecnaOcena(projekcijaOcena);
        }else {
            float nova = (prosecna * broj + projekcijaOcena) / (broj + 1);
            d.setBrojOcena(broj + 1);
            d.setProsecnaOcena(nova);
        }
        dogRepo.save(d);
        pozBiService.updateOcena(pozoristeBioskopId, ambijentOcena);
    }


}
