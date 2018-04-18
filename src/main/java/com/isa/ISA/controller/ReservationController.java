package com.isa.ISA.controller;

import com.isa.ISA.DTO.InvitationDTO;
import com.isa.ISA.DTO.RezervacijaDTO;
import com.isa.ISA.dbModel.*;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.enums.TipSedista;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.service.ProjekcijaService;
import com.isa.ISA.service.ReservationService;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService rezService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjekcijaService projekcijaService;


    public ReservationController(){}

    @RequestMapping(method = RequestMethod.POST,value = "/api/reserve")
    public void reserve(@RequestBody  RezervacijaDTO r){
        rezService.reserve(r);
    }

/*
    @RequestMapping(method = RequestMethod.GET,value = "/api/accept/{username}/event/{rezID}")
    public void acceptRez(@PathVariable String username, @PathVariable Long rezID){
       System.out.println("usao da sacuvaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa");
      *//*   RegistrovaniKorisnik k = userService.getUser(username);
        Rezervacija rez = rezService.getRez(r.getId());
        for(Poziv p : rez.getUrezervaciji()){
            if(p.getOsoba().getId()==k.getId()){
                if(!p.getStatus().toString().equals("PRIHVACENO"))
                    p.setStatus(Status.PRIHVACENO);
                else
                    return rez;
            }
        }
        rezService.addRez(rez);
        k.getRezervacije().add(rez);
        userService.addUser(k);
        return rez;*//*

    }*/
/*

    @RequestMapping(method = RequestMethod.GET,value = "/api/reserve/decline/{username}/event/{rezID}")
    public void declineRez(@PathVariable String username, @PathVariable Long rezID){
        RegistrovaniKorisnik k = userService.getUser(username);
        Rezervacija rez = rezService.getRez(rezID);
        int idx = -1;
        for(int i = 0; i < rez.getUrezervaciji().size(); i++){
            if(rez.getUrezervaciji().get(i).getOsoba().getId()==k.getId()){
                rez.getUrezervaciji().get(i).setStatus(Status.ODBIJENO);
                idx = i;
                break;
            }
        }
        Projekcija p = rez.getProjekcija();
        Long sedID= Long.valueOf(-1);

        if(idx!=-1){
            sedID = p.getZauzetaSedista().get(idx).getId();
            p.getZauzetaSedista().remove(idx);
        }

        projekcijaService.addProjekcija(p);
        rezService.addRez(rez);



        rezService.addRez(rez);

    }
*/


}
