package com.isa.ISA.service;

import com.isa.ISA.dbModel.Karta;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.repository.DogadjajRepository;
import com.isa.ISA.repository.KartaRepository;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IzvestajSevice {

    @Autowired
    private PozoristeBioskopRepository pbRepository;

    @Autowired
    private DogadjajRepository dogadjajRepository;

    @Autowired
    private KartaRepository kartaRepository;

    public float getOcenaAmbijenta(Long pb){
        return dogadjajRepository.findOne(pb).getProsecnaOcena();
    }

    public float getOcenaDogadjaj(Long dog){
        return dogadjajRepository.findOne(dog).getProsecnaOcena();
    }

    public int getPrihodIzmedju(Long pb, Date p, Date k){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
       // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBetween(pbi, p, k).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    public int getPrihodPre(Long pb, Date p){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
        // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBefore(pbi, p).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    public int getPrihodPosle(Long pb, Date p){

        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(pb);
        // int ukupno =0;
        return kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaAfter(pbi, p).stream().mapToInt(karta -> karta.getPunaCena()).sum(); // Mnogo kul ako radi :D

    }

    private Map<Date,Integer> getPosete(Long id, Date od, Date d2, int flag){ // Ovo bih mogao opaliti asihrono ili da salje na mail, to je jos bolje :D Tako za sve ove izvestaj
        PozoristeBioskop pbi = new PozoristeBioskop();
        pbi.setId(id);
        Map<Date, Integer> grafik = new HashMap<>();
        List<Karta> karte = new ArrayList<>();

        if(flag==0)
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBetween(pbi, od, d2).forEach(karte::add);
        else if(flag==1)
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaAfter(pbi, od).forEach(karte::add);
        else
            kartaRepository.findByPozoristeBioskopAndVremeOdrzavanjaBefore(pbi,d2).forEach(karte::add);


        for (Karta karta : karte) { // Generise mi mapu koja predstavlja grafik dana i broja prodatih karata
            Date temp = setToZero(karta.getVremeOdrzavanja());
            Integer i = grafik.get(temp);
            if(i==null){
                grafik.put(temp, 1);

            }else{
                grafik.put(temp, i+1);
            }

        }
        return grafik;

    }

    private Date setToZero(Date d){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    public Map<Date,Integer> getPoseteOdDo(Long id, Date od, Date d2){
        return getPosete(id, od, d2, 0);
    }

    public Map<Date,Integer> getPoseteOd(Long id, Date od){
        Date d2 = null;
        return getPosete(id, od, d2, 1);
    }

    public Map<Date,Integer> getPoseteDo(Long id, Date d2){
        Date od = null;
        return getPosete(id, od, d2, 2);
    }







}
