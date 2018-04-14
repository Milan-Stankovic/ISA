package com.isa.ISA.service;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService  {

    @Autowired
    private SalaRepository sRepo;

    public List<Sala> getAll(){
        List<Sala> allDog = new ArrayList<>();
        sRepo.findAll().forEach(allDog::add);
        return allDog;
    }

    public Sala getOne(Long id){
        return sRepo.findOne(id);
    }

    public void addSala(SalaDTO sd){

    }

    private Sala converter(SalaDTO s){
        Sala sal = new Sala();
        sal.setIme(s.getIme());
        sal.setBrSedista(s.getBrSedista());
        sal.setBrRed(s.getBrRed());


        return sal;


    }
    private boolean provera(SalaDTO s){
        boolean b = false;
        if(s.getBrRed()>0 && s.getBrRed()<76)
            if(s.getBrSedista()>0 && s.getBrSedista()<101)
                if(s.getIme() != null)
                    if(s.getIme() != "")
                        if(s.getIme().length()>0)
                            if(s.getUstanova() != null)
                                if(s.getUstanova() >0)
                                    if(s.getSedista() != null)
                                        if(s.getSedista().size() >0)
                                            b=true;
        return b;


    }
}
