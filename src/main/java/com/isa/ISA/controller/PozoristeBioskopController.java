package com.isa.ISA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.DTO.PozoristeBioskopDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.service.PozoristeBioskopService;

@RestController
public class PozoristeBioskopController {

    @Autowired
    private PozoristeBioskopService pbService;

    @RequestMapping("/pb")
    public List<PozoristeBioskop> getAllPozoristeBioskop(){
        return pbService.getAllPozoristeBioskop();
    }

    @RequestMapping("/p")
    public List<PozoristeBioskop> getAllPozoriste(){
        return pbService.getAllPozoriste();
    }

    @RequestMapping("/b")
    public List<PozoristeBioskop> getAllBioskop(){
        return pbService.getAllBioskop();
    }

    @RequestMapping("/pb/{id}")
    public PozoristeBioskop getPozoristeBioskop(@PathVariable Long id){ // nisam siguran da li je ovo ok ili mora string pa string u long
        return pbService.getPozoristeBioskop(id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/pb")
    public void addPozoristeBioskop(@RequestBody PozoristeBioskop pb){
        pbService.addPozoristeBioskop(pb);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/pbDTO")
    public PozoristeBioskop addPozoristeBioskopDTO(@RequestBody PozoristeBioskopDTO pb){
        return pbService.addPozoristeBioskopDTO(pb);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/pb/{id}")
    public void updatePozoristeBioskop(@RequestBody PozoristeBioskop pb, @PathVariable Long id){ // Nisam siguran ni sta ce mi id ali lepse izgleda url
        pbService.updatePozoristeBioskop(pb);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/pb/{id}")
    public void deletePozoristeBioskop(@PathVariable Long id ){
        pbService.deletePozoristeBioskop(id);
    }

    @RequestMapping("/pb/{id}/projekcije")
    public List<Projekcija> getPozoristeBioskopProj(@PathVariable Long id){ // nisam siguran da li je ovo ok ili mora string pa string u long
        PozoristeBioskop pb = pbService.getPozoristeBioskop(id);
        return pb.getProjekcije();
    }

}
