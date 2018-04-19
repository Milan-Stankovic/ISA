package com.isa.ISA.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isa.ISA.DTO.ProjekcijaDTO;
import com.isa.ISA.DTO.OneClickDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dodatno.Konverter;
import com.isa.ISA.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjekcijaService {

    @Autowired
    private ProjekcijaRepository pr;

    @Autowired
    private SalaRepository sr;

    @Autowired
    private DogadjajRepository dr;

    @Autowired
    private KartaRepository kRepo;

    @Autowired
    private PozivRepository pozRepo;

    @Autowired
    private RezervacijaRepository rezRepo;

    public List<Projekcija> getAll(){
        List<Projekcija> allP = new ArrayList<>();
        pr.findAll().forEach(allP::add);
        return allP;
    }

    public Projekcija getProjekcija(Long id){
        return pr.findOne(id);
    }

    public List<Sediste> getZauzeto(Long id){return pr.findOne(id).getZauzetaSedista();}


    private Long findSediste(List<Sediste> sedista, int red, int sediste){
        Long l=-1l;

        for (Sediste temp : sedista) {
            if(temp.getBroj() == sediste && temp.getRed()==red)
                return temp.getId();
        }

        return l;
    }

    private boolean isTaken(List<Sediste> sedista, Long id){
        for (Sediste temp:sedista) {
            if(temp.getId()==id)
                return true;
        }
        return false;
    }

    public void zauzmiMesto(Long id, OneClickDTO d){

        Projekcija p = pr.findOne(id);
        PozoristeBioskop pb = new PozoristeBioskop();
        pb.setId(d.getBpId());
        int brojSedista = d.getBrojSedista();


        for (SedisteDTO tempSediste:d.getSedista()) {
            if(tempSediste.isChecked()){
                int tempRed = tempSediste.getId();

                Sediste temp = new Sediste();
                int red = tempSediste.getId()/brojSedista;
                int sed = tempSediste.getId()%brojSedista;
                Long sedId = findSediste(p.getSala().getSedista(), red, sed);
                if(sedId != -1l) {
                    if(!isTaken(p.getZauzetaSedista(), sedId)){
                        temp.setId(sedId);
                        Rezervacija rez = new Rezervacija();
                        rez.setRezervisao(null);
                        rez.setPopust(d.getCena());
                        rez.setProjekcija(p);
                        Poziv poz = new Poziv();
                        poz.setStatus(Status.CEKA);
                        poz.setPozvan(false);
                        poz.setOsoba(null);
                        Karta k = new Karta();
                        k.setVremeOdrzavanja(p.getVreme());
                        k.setPozoristeBioskop(pb);
                        k.setPunaCena(d.getCena());
                        k.setSediste(temp);
                        k = kRepo.save(k);
                        poz.setKarta(k);
                        List<Poziv> pozivi = new ArrayList<>();
                        rez.setUrezervaciji(pozivi);
                        rez = rezRepo.save(rez);

                        poz.setRezervacija(rez);
                        poz = pozRepo.save(poz);
                        pozivi.add(poz);
                        rez.setUrezervaciji(pozivi);
                        rezRepo.save(rez);
                    }

                }

            }

        }

    }

    public Sala getSala(Long id){
        return pr.findOne(id).getSala();
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
