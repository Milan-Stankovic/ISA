package com.isa.ISA.dodatno;

import com.isa.ISA.DTO.SalaDTO;
import com.isa.ISA.DTO.SedisteDTO;
import com.isa.ISA.dbModel.PozoristeBioskop;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.repository.PozoristeBioskopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Konverter {

    @Autowired
    private PozoristeBioskopRepository pbRepository;

    public static Sala converterSale(SalaDTO s){
        Sala sal = new Sala();
        System.out.println("IME JE : ");
        System.out.println(s.getIme());
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
            if(sed.isChecked())
                temp.setTipSedista(TipSedista.valueOf(sed.getType()));
            else
                temp.setTipSedista(TipSedista.REGULAR);
            sedista.add(temp);
        }
        sal.setSedista(sedista);

        return sal;


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
}
