package com.isa.ISA.controller;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.service.ProjekcijaService;
import com.isa.ISA.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.SalaRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
public class SalaController {

    @Autowired
    private SalaService sService; // Ko god koristi sale neka ovo promeni i fino napravi servis, ovo je za testiranje

    @Autowired
    private ProjekcijaService ps;

    @RequestMapping("/sale")
    public List<Sala> getAllPozoristeBioskop(){
        return sService.getAll();
    }

    @RequestMapping("/sala/{id}")
    public Sala getSala(@PathVariable Long id){
        return sService.getOne(id);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/sala/add")
    public void addSala(@RequestBody SalaDTO s){
        sService.addSala(s);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/sala/edit/{id}")
    public void editSala(@RequestBody SalaDTO s, @PathVariable Long id){ sService.editSala(s, id); }

    @RequestMapping(method = RequestMethod.DELETE, value = "/sala/delete/{pbId}/{id}")
    public void deleteSala(@PathVariable Long pbId, @PathVariable Long id){
       ArrayList<Long> ids= ps.getProjekcijeToBeDeleted(id);
       sService.deleteProjekcijeFromPB(ids, pbId);
        ps.deleteProjekcijaByIds(ids);
        sService.deleteSala(id, pbId,ids);

    }

    @RequestMapping("/sala/ustanova/{id}")
    public PozoristeBioskop getSalaUstanova(@PathVariable Long id){

        Sala s = sService.getOne(id);
        return s.getUstanova();
    }

}
