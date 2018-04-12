package com.isa.ISA.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.PozoristeBioskopService;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegKorController {

    @Autowired
    private UserService userService;

    @Autowired
    private PozoristeBioskopService pbService;

    @RequestMapping(method = RequestMethod.GET,value = "/api/regKor/{username}/visited")
    public List<PozoristeBioskop> getVisitedP(@PathVariable String username){
        RegistrovaniKorisnik k = userService.getUser(username);
        if(k==null){
            return new ArrayList<PozoristeBioskop>();
        }
        List<Rezervacija> kRez = k.getRezervacije();
        List<Projekcija> kRezProjekcije  = kRez.stream().map(Rezervacija::getProjekcija).collect(Collectors.toList());
        List<Sala> kRezSala  = kRezProjekcije.stream().map(Projekcija::getSala).collect(Collectors.toList());
        List<PozoristeBioskop> kRezUstanova  = kRezSala.stream().map(Sala::getUstanova).collect(Collectors.toList());
        return kRezUstanova;
    }



}
