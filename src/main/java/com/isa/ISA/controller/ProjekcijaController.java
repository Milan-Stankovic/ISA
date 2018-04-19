package com.isa.ISA.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isa.ISA.DTO.OneClickDTO;
import com.isa.ISA.DTO.ProjekcijaDTO;
import com.isa.ISA.dbModel.Sediste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.service.ProjekcijaService;

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

    @RequestMapping("/pb/{id1}/projekcije/{id2}")
    private Projekcija getProjekcija(@PathVariable Long id2){
        return ps.getProjekcija(id2);
    }

    @RequestMapping("/projekcija/{id}")
    private Projekcija getProjekcijaEz(@PathVariable Long id){
        return ps.getProjekcija(id);
    }

    @RequestMapping("/projekcija/sala/{id}")
    private Sala getSala(@PathVariable Long id){
        return ps.getSala(id);
    }

    @RequestMapping("/projekcija/zauzeto/{id}")
    private List<Sediste> getZauzeto(@PathVariable Long id){
        return ps.getZauzeto(id);
    }

    @RequestMapping(value="/projekcija/zauzmi/{id}", method = RequestMethod.POST)
    private void zauzmiOneClick(@PathVariable Long id, @RequestBody OneClickDTO o){
        ps.zauzmiMesto(id, o);
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

    @RequestMapping(method = RequestMethod.PUT, value = "/projekcija/{id}")
    public void updateProjekcijaLikeABoss(@RequestBody ProjekcijaDTO pr, @PathVariable Long id){
        ps.greatUpdateProjekcija(pr, id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/pb/{id}/projekcije/{id2}")
    public void updateProjekcija(@RequestBody Projekcija pr, @PathVariable Long id, @PathVariable Long id2){ // Nisam siguran ni sta ce mi id ali lepse izgleda url
        ps.updateProjekcija(pr);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/pb/{id}/projekcije/{id2}")
    public void deleteProjekcija(@PathVariable Long id, @PathVariable Long id2 ){
        ps.deleteProjekcija(id2);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/projekcija/{id}")
    public void deleteProjekcijaLikeABoss(@PathVariable Long id){
        ps.deleteProjekcija(id);

    }




}
