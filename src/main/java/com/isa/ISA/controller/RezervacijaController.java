package com.isa.ISA.controller;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.repository.RezervacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RezervacijaController {

    @Autowired
    private RezervacijaRepository rezRepo;

    @RequestMapping(method = RequestMethod.GET,value = "/api/rezervacija/{id}")
    public Rezervacija getRez(@PathVariable Long id){

        return rezRepo.findOne(id);
    }
}
