package com.isa.ISA.service;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dodatno.Konverter;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import com.isa.ISA.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService  {

    @Autowired
    private SalaRepository sRepo;

    @Autowired
    private PozoristeBioskopRepository pbRepo;

    public List<Sala> getAll(){
        List<Sala> allDog = new ArrayList<>();
        sRepo.findAll().forEach(allDog::add);
        return allDog;
    }

    public Sala getOne(Long id){
        return sRepo.findOne(id);
    }

    public void addSala(SalaDTO s){
        if(Konverter.proveraSale(s)){
            Sala sala = sRepo.save(Konverter.converterSale(s));
            PozoristeBioskop pb = pbRepo.findOne(s.getUstanova());
            pb.getSale().add(sala);
            pbRepo.save(pb);
        }
    }

    public void editSala(SalaDTO s, Long id){
        if(Konverter.proveraSale(s)){
            if(sRepo.findOne(id)!=null) {
                System.out.println("Edituje salu");
                Sala sala = Konverter.converterSale(s);
                sala.setId(id);
                sRepo.save(sala);
            }
        }
    }


}
