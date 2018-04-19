package com.isa.ISA.controller;

import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.service.RezervacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PozivController {

    @Autowired
    private RezervacijaService rezervacijaService;


    @RequestMapping(value = "/poziv/user/{username}", method = RequestMethod.POST)
    public String post(@PathVariable String username, @RequestBody Rezervacija r){ return "yaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaay";}


    @RequestMapping(value = "/poziv/user/{username}", method = RequestMethod.DELETE)
    public String get(@PathVariable String username, @RequestBody Rezervacija re) { return "yaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaay";}
}
