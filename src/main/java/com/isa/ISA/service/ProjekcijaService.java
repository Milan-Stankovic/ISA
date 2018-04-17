package com.isa.ISA.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isa.ISA.DTO.ProjekcijaDTO;
import com.isa.ISA.dodatno.Konverter;
import com.isa.ISA.repository.DogadjajRepository;
import com.isa.ISA.repository.SalaRepository;
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

    @Autowired
    private SalaRepository sr;

    @Autowired
    private DogadjajRepository dr;

    public List<Projekcija> getAll(){
        List<Projekcija> allP = new ArrayList<>();
        pr.findAll().forEach(allP::add);
        return allP;
    }

    public Projekcija getProjekcija(Long id){
        return pr.findOne(id);
    }

    public Projekcija getProjekcijaID(Long id){
        return pr.getProjekcijaById(id);
    }

    public void deleteProjekcija(Long id){
        pr.delete(id);
    }

    public void updateProjekcija(Projekcija p){
        pr.save(p);
    }

    public void greatUpdateProjekcija(ProjekcijaDTO p, Long id){
        if(Konverter.proveraProjekcije(p)){
            Projekcija proj = getProjekcija(id);
            proj.setAktivna(p.isAktivna());
            Sala s = sr.findOne(p.getSala());
            proj.setSala(s);
            proj.setCena(p.getCena());
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try{
                Date date = dateFormat.parse(p.getDate());
                proj.setVreme(date);
                pr.save(proj);
            }catch (ParseException e) {
                e.printStackTrace();
            }

        }



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


    public void deleteProjekcijaByIds(List<Long> ids){
        for(Long id:ids){
            pr.delete(id);
        }
    }

    public ArrayList<Long> getProjekcijeToBeDeleted(Long id){
        ArrayList<Long> ids = new ArrayList<>();
        Sala s = sr.findOne(id);
        List<Projekcija>  projekcije=  pr.findBySala(s);
        for (Projekcija p: projekcije) {
            ids.add(p.getId());
        }
        return ids;
    }

    public void deleteProjekcijaByDogadjaj(Long id){
        Dogadjaj d = dr.findOne(id);
        List<Projekcija>  projekcije=  pr.findByDogadjaj(d);
        for (Projekcija p: projekcije) {
            pr.delete(p);
        }

    }




}
