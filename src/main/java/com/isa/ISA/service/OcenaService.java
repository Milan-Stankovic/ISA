package com.isa.ISA.service;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcenaService {


    @Autowired
    private PozvRepository pozvRepository;

    @Autowired
    private KartaRepository kartaRepository;

    @Autowired
    private PozoristeBioskopService pbService;


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
