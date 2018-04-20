package com.isa.ISA.service;

import com.isa.ISA.DTO.CenovnikDTO;
import com.isa.ISA.dbModel.CenovnikSedista;
import com.isa.ISA.dbModel.Sala;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dodatno.Konverter;
import com.isa.ISA.repository.CenovnikSedistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenovnikService {

    @Autowired
    private CenovnikSedistaRepository csr;

    public List<CenovnikSedista> getCenovnici(Long id){
        return csr.findBySalaId(id);
    }


    public CenovnikDTO getDTO(Long id){

        List<CenovnikSedista> cenovnik = csr.findBySalaId(id);

        Sala s = new Sala();
        s.setId(id);

        List<CenovnikSedista> cenovnik2 = csr.findBySala(s);

    //    System.out.println("DUZINA PRE KONVERTERA"  + cenovnik.size());

    //    System.out.println("DUZINA 2 PRE KONVERTERA"  + cenovnik2.size());
        return Konverter.convertCenovnici(cenovnik);
    }

    public void setUpdatePrice(CenovnikDTO cena, Long id){
        List<CenovnikSedista> cenovnici = csr.findBySalaId(id);

        boolean reg=false;
        boolean vip=false;
        boolean love=false;
        boolean balc = false;

      //  System.out.println(cena.getDoplataBalcony() + " drugo " + cena.getDoplataVIP());

        for (CenovnikSedista temp: cenovnici) {

            switch (temp.getTip()){
                case REGULAR:
                    if(cena.getDoplataRegular() >=0) {
                  //      System.out.println("SET REGULAR");
                        temp.setDoplata(cena.getDoplataRegular());
                        reg=true;
                        csr.save(temp);
                    }
                    break;
                case VIP:
                    if(cena.getDoplataVIP() >=0){
                    //    System.out.println("SET VIP");
                        temp.setDoplata(cena.getDoplataVIP());
                        vip=true;
                        csr.save(temp);
                    }
                    break;
                case LOVEBOX:
                    if(cena.getDoplataLoveBox() >=0) {

                   //     System.out.println("SET LOVE");
                        temp.setDoplata(cena.getDoplataLoveBox());
                        love=true;
                        csr.save(temp);
                    }
                    break;
                case BALCONY:
                    if(cena.getDoplataBalcony() >=0) {
                  //      System.out.println("SET BALCONY");
                       balc=true;
                        temp.setDoplata(cena.getDoplataBalcony());
                        csr.save(temp);
                    }
                    break;
                case TAKEN:
                    break;
            }

        }


     //   if(vip)
       //     System.out.println("Opalio je VIP");

        if(!reg){
            if(cena.getDoplataRegular() >=0) {
                CenovnikSedista noviC = new CenovnikSedista();
                noviC.setDoplata(cena.getDoplataRegular());
                Sala s = new Sala();
                s.setId(id);
                noviC.setSala(s);
                noviC.setTip(TipSedista.REGULAR);
                csr.save(noviC);
            }
        }

        if(!vip){
            if(cena.getDoplataVIP() >=0) {
                CenovnikSedista noviC = new CenovnikSedista();
                noviC.setDoplata(cena.getDoplataVIP());
                Sala s = new Sala();
                s.setId(id);
                noviC.setSala(s);
                noviC.setTip(TipSedista.VIP);

           //     System.out.println("Opalio je VIP opet");
//                System.out.println(noviC.getDoplata());

                csr.save(noviC);
            }
        }

        if(!love){
            if(cena.getDoplataLoveBox() >=0) {
                CenovnikSedista noviC = new CenovnikSedista();
                noviC.setDoplata(cena.getDoplataLoveBox());
                Sala s = new Sala();
                s.setId(id);
                noviC.setSala(s);
                noviC.setTip(TipSedista.LOVEBOX);
                csr.save(noviC);
            }
        }

        if(!balc){
            if(cena.getDoplataBalcony() >=0) {
                CenovnikSedista noviC = new CenovnikSedista();
                noviC.setDoplata(cena.getDoplataBalcony());
                Sala s = new Sala();
                s.setId(id);
                noviC.setSala(s);
                noviC.setTip(TipSedista.BALCONY);
                csr.save(noviC);
            }
        }

    }

}
