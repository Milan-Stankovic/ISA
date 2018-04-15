package com.isa.ISA.controller;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.SalaRepository;

import java.util.List;


@RestController
public class SalaController {

    @Autowired
    private SalaService sService; // Ko god koristi sale neka ovo promeni i fino napravi servis, ovo je za testiranje

    @RequestMapping("/sale")
    public List<Sala> getAllPozoristeBioskop(){
        return sService.getAll();
    }

    @RequestMapping("/sala/{id}")
    public Sala getSala(@PathVariable Long id){
        return sService.getOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sala/add")
    public void addSala(@RequestBody SalaDTO sd){
        sService.addSala(sd);
    }
}
