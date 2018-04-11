package com.isa.ISA.controller;


import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.service.ProjekcijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ProjekcijaController {

    @Autowired
    private ProjekcijaService ps;

    @RequestMapping("/projekcije")
    private List<Projekcija> getAllProjekcija(){
        List<Projekcija> allP = new ArrayList<>();
        ps.getAll().forEach(allP::add);
        return allP;
    }

    @RequestMapping("/pb/{id}/projekcije/{id}")
    private Projekcija getProjekcija(@PathVariable Long id){
        return ps.getProjekcija(id);
    }

    @RequestMapping("/pb/{id}/projekcije/od")
    private List<Projekcija> getProjekcijaOd(@RequestBody Date d, @PathVariable Long id){
       List<Projekcija> projekcije = new ArrayList<>();
       ps.getProjekcijePre(d).forEach(projekcije::add);
       return projekcije;
    }

    @RequestMapping("/pb/{id}/projekcije/do")
    private List<Projekcija> getProjekcijaDo(@RequestBody Date d, @PathVariable Long id){
        List<Projekcija> projekcije = new ArrayList<>();
        ps.getProjekcijeNakon(d).forEach(projekcije::add);
        return projekcije;
    }
    @RequestMapping("/pb/{id}/projekcije/izmedju")
    private List<Projekcija> getProjekcijaIzmedju(@RequestBody Date od, @RequestBody Date d2, @PathVariable Long id){
        List<Projekcija> projekcije = new ArrayList<>();
        ps.getProjekcijeOdDo(od,d2).forEach(projekcije::add);
        return projekcije;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pb/{id}/projekcije")
    public void addProjekcija(@RequestBody Projekcija pr, @PathVariable Long id){
        ps.addProjekcija(pr);
    }

    @RequestMapping("/pb/{id}/sala/{id}/projekcije")
    public List<Projekcija> getProjekcije(@RequestBody Sala s, @PathVariable Long id){
        List<Projekcija> projekcije = new ArrayList<>();
        ps.getProjekcijeizSale(s).forEach(projekcije::add);
        return projekcije;
    }

    @RequestMapping("/pb/{id}/dogadjaj/{id}/projekcije")
    public List<Projekcija> getProjekcije(@RequestBody Dogadjaj d, @PathVariable Long id){
        List<Projekcija> projekcije = new ArrayList<>();
        ps.getProjekcije(d).forEach(projekcije::add);
        return projekcije;
    }

    @RequestMapping("/pb/{id}/dogadjaj/{id}/sala/{id2}/projekcije")
    public List<Projekcija> getProjekcije(@RequestBody Dogadjaj d, @RequestBody Sala s, @PathVariable Long id, @PathVariable Long id2){
        List<Projekcija> projekcije = new ArrayList<>();
        ps.getProjekcijeizSaleDogadjaja(s, d).forEach(projekcije::add);
        return projekcije;
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/pb/{id}/projekcije/{id2}")
    public void updateProjekcija(@RequestBody Projekcija pr, @PathVariable Long id, @PathVariable Long id2){ // Nisam siguran ni sta ce mi id ali lepse izgleda url
        ps.updateProjekcija(pr);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/pb/{id}/projekcije/{id2}")
    public void deleteProjekcija(@PathVariable Long id, @PathVariable Long id2 ){
        ps.deleteProjekcija(id2);

    }




}
