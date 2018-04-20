package com.isa.ISA.service;

import com.isa.ISA.DTO.OcenaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozvRepository;

@Service
public class OcenaService {


    @Autowired
    private PozvRepository pozvRepository;

    @Autowired
    private KartaRepository kartaRepository;

    @Autowired
    private PozoristeBioskopService pbService;

    @Autowired
    private DogadjajService dogServ;


    public void oceniSve(Long id, OcenaDTO o){
        Poziv p = pozvRepository.findOne(id);
        Karta k = kartaRepository.findOne(p.getKarta().getId());
        pbService.updateOcena( k.getPozoristeBioskop().getId(), o.getOcenaAmbijenta());
        p.setOcenaAmbijenta(o.getOcenaAmbijenta());
        p.setOcenaFilma(o.getOcenaDogadjaja());
        p.setOcenjeno(true);
        pozvRepository.save(p);
        dogServ.oceniDogadjaj(o.getOcenaDogadjaja(), p.getRezervacija().getProjekcija().getDogadjaj().getId());

    }

    public void oceniA(Long pozivID, int ocenaAmbijenta){
        Poziv p = pozvRepository.findOne(pozivID);
        Karta k = kartaRepository.findOne(p.getKarta().getId());
        pbService.updateOcena( k.getPozoristeBioskop().getId(), ocenaAmbijenta);
        p.setOcenaAmbijenta(ocenaAmbijenta);
        pozvRepository.save(p);
    }

    public void oceniD(Long pozivID,int ocenaDogadjaja){
        Poziv p = pozvRepository.findOne(pozivID);
        p.setOcenaFilma(ocenaDogadjaja);
        pozvRepository.save(p);
    }
}
