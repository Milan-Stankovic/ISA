package com.isa.ISA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.SalaRepository;


@RestController
public class SalaController {

    @Autowired
    private SalaRepository temp; // Ko god koristi sale neka ovo promeni i fino napravi servis, ovo je za testiranje

    @RequestMapping("/sale")
    public Sala getAllPozoristeBioskop(){
        return temp.findOne(1l);
    }

    @RequestMapping("/saleTest")
    public PozoristeBioskop getSala(){
        return temp.findOne(1l).getUstanova();
    }
}
