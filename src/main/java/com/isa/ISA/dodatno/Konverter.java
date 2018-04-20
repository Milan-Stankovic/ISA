package com.isa.ISA.dodatno;

import com.isa.ISA.DTO.*;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.DogadjajStatus;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Konverter {

    @Autowired
    private PozoristeBioskopRepository pbRepository;

    public static Sala converterSale(SalaDTO s){
        Sala sal = new Sala();
        //System.out.println("IME JE : ");
      //  System.out.println(s.getIme());
        sal.setIme(s.getIme());
        sal.setBrSedista(s.getBrSedista());
        sal.setBrRed(s.getBrRed());
        PozoristeBioskop pb = new PozoristeBioskop();
        pb.setId(s.getUstanova());
        sal.setUstanova(pb);
        int brojSedista = s.getBrSedista();

        ArrayList<Sediste> sedista = new ArrayList<>();

        for (SedisteDTO sed:s.getSedista()) {
            Sediste temp = new Sediste();
            temp.setRed(sed.getId()/brojSedista);
            temp.setBroj(sed.getId()%brojSedista);
            if(sed.getType() !=null)
                temp.setTipSedista(TipSedista.valueOf(sed.getType()));
            else
                temp.setTipSedista(TipSedista.REGULAR);
            sedista.add(temp);
        }
        sal.setSedista(sedista);

        return sal;


    }


    public static CenovnikDTO convertCenovnici(List<CenovnikSedista> cenovnici){

        CenovnikDTO povratna = new CenovnikDTO();
        povratna.setDoplataRegular(0);
        povratna.setDoplataBalcony(0);
        povratna.setDoplataVIP(0);
        povratna.setDoplataLoveBox(0);
     //   System.out.println("DUZINA JE "+cenovnici.size());

        for (CenovnikSedista tem: cenovnici) {
            switch (tem.getTip()) {
                case REGULAR:
              //      System.out.println("NASAO REGULAR");
                    povratna.setDoplataRegular(tem.getDoplata());
                    break;
                case VIP:
              //      System.out.println("NASAO VIP");
                    povratna.setDoplataVIP(tem.getDoplata());
                    break;
                case LOVEBOX:
               //     System.out.println("NASAO LOVEBOX");
                    povratna.setDoplataLoveBox(tem.getDoplata());
                    break;
                case BALCONY:
                //    System.out.println("NASAO BALCONY");
                    povratna.setDoplataBalcony(tem.getDoplata());
                    break;
                case TAKEN:
                    break;
            }

        }

        return  povratna;

    }

    public static boolean proveraSale(SalaDTO s){
        boolean b = false;
        if(s.getBrRed()>0 && s.getBrRed()<76)
            if(s.getBrSedista()>0 && s.getBrSedista()<101)
                if(s.getIme() != null)
                    if(s.getIme() != "")
                        if(s.getIme().length()>0)
                            if(s.getUstanova() != null)
                                if(s.getUstanova() >0)
                                    if(s.getSedista() != null)
                                        if(s.getSedista().size() >0)
                                                b=true;
        return b;


    }

    public static Dogadjaj converterDogadjaja(DogadjajDTO d){
        Dogadjaj dog = new Dogadjaj();
        dog.setTrajanje(d.getTrajanje());
        dog.setZanr(d.getZanr());
        dog.setReziser(d.getReziser());
        dog.setOpis(d.getOpis());
        dog.setNaziv(d.getNaziv());
        dog.setGlumciStr(d.getGlumciStr());
        dog.setDonosiBodova(d.getDonsiBodova());
        dog.setProsecnaOcena(5);
        dog.setBrojOcena(1);
        dog.setSlika(d.getSlika());
        DogadjajStatus dos = DogadjajStatus.valueOf(d.getStatus());
        dog.setStatus(dos);
        PozoristeBioskop pb = new PozoristeBioskop();
        pb.setId(d.getPbId());
        dog.setMestoOdrzavanja(pb);
        return dog;

    }


    public static boolean proveraProjekcije(ProjekcijaDTO p){
       boolean b = false;

        if(p.getCena() >0)
            if(p.getDate() != null)
                if(p.getDate().length() >0)
                    if(p.getDogadjaj() !=null)
                        if(p.getDogadjaj()>0)
                            if(p.getSala() != null)
                                if(p.getSala() >0)
                                    if(p.getUstanova() != null)
                                        if(p.getUstanova() > 0)
                                            b= true;
        return b;
    }

    public static boolean proveraDogadjaja(DogadjajDTO d){

        if(d.getGlumciStr().length()>0)
            if(d.getNaziv().length()>0)
                if(d.getOpis().length()>0)
                    if(d.getReziser().length()>0)
                        if(d.getTrajanje()>0)
                            if(d.getZanr() != null)
                                if(d.getPbId() != null)
                                    if(d.getSlika() !=null)
                                        if(d.getSlika().length() >0)
                                            return true;
        return false;

    }
}
