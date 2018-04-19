package com.isa.ISA.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.repository.KartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    private KartaRepository kartaRepo;

    @RequestMapping("api/karte")
    public List<Karta> allKarte(){

        List<Karta> karte = new ArrayList<>();
        kartaRepo.findAll().forEach(karte::add);
        return  karte;

    }

    @RequestMapping("/api/hi")
    public String hi(){
        return "Hello ISA";
    }


    @RequestMapping("/forward")
    public ResponseEntity forward(){

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/PigCinema/index.html"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
      //  return "forward:/PigCinema/index.html";
    }

}
