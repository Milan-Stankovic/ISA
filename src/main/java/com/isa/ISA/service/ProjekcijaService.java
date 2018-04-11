package com.isa.ISA.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.ISA.dbModel.Dogadjaj;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.repository.ProjekcijaRepository;

@Service
public class ProjekcijaService {

    @Autowired
    private ProjekcijaRepository pr;

    public List<Projekcija> getAll(){
        List<Projekcija> allP = new ArrayList<>();
        pr.findAll().forEach(allP::add);
        return allP;
    }

    public Projekcija getProjekcija(Long id){
        return pr.findOne(id);
    }

    public void deleteProjekcija(Long id){
        pr.delete(id);
    }

    public void updateProjekcija(Projekcija p){
        pr.save(p);
    }

    public void addProjekcija(Projekcija p){
        pr.save(p);
    }

    public List<Projekcija> getProjekcije(Dogadjaj d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findByDogadjaj(d).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeNakon(Date d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findByVremeAfter(d).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijePre(Date d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findByVremeBefore(d).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeOdDo(Date od, Date dok){
        List<Projekcija> allP = new ArrayList<>();
        pr.findByVremeBetween(od, dok).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeizSale(Sala s){
        List<Projekcija> allP = new ArrayList<>();
        pr.findBySala(s).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeizSaleDogadjaja(Sala s, Dogadjaj d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findBySalaAndDogadjaj(s, d).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeizSalePosle(Sala s, Date d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findBySalaAndVremeAfter(s, d).forEach(allP::add);
        return allP;
    }


    public List<Projekcija> getProjekcijeizSalePre(Sala s, Date d){
        List<Projekcija> allP = new ArrayList<>();
        pr.findBySalaAndVremeBefore(s, d).forEach(allP::add);
        return allP;
    }

    public List<Projekcija> getProjekcijeizSaleIzmedju(Sala s, Date d, Date b){
        List<Projekcija> allP = new ArrayList<>();
        pr.findBySalaAndVremeBetween(s, d, b).forEach(allP::add);
        return allP;
    }




}
