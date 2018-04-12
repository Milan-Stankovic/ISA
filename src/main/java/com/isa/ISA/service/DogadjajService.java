package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.DTO.DogadjajDTO;
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

    private Dogadjaj converter(DogadjajDTO d){
        Dogadjaj dog = new Dogadjaj();
        dog.setTrajanje(d.getTrajanje());
        dog.setZanr(d.getZanr());
        dog.setReziser(d.getReziser());
        dog.setOpis(d.getOpis());
        dog.setNaziv(d.getNaziv());
        dog.setGlumci(null);
        dog.setGlumciStr(d.getGlumciStr());
        dog.setDonosiBodova(d.getDonosiBodova());
        dog.setProsecnaOcena(d.getProsecnaOcena());
        dog.setBrojOcena(d.getBrojOcena());
        return dog;

    }
    private boolean provera(DogadjajDTO d){

        if(d.getBrojOcena()>=0)
            if(d.getDonosiBodova()>=0)
                if(d.getGlumciStr().length()>0)
                    if(d.getNaziv().length()>0)
                        if(d.getOpis().length()>0)
                            if(d.getReziser().length()>0)
                                if(d.getTrajanje()>0)
                                    if(d.getZanr() != null)
                                        return true;
        return false;

    }

    public void addDogadjaj(DogadjajDTO d){
        if(provera(d))
            dogRepo.save(converter(d));
    }

    public void addDogadjaj2(Dogadjaj d){
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
