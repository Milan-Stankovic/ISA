package com.isa.ISA.controller;

import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.RezervacijaRepository;
import com.isa.ISA.service.ProjekcijaService;
import com.isa.ISA.service.RezervacijaService;
import com.isa.ISA.service.SalaService;
import com.isa.ISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RezervacijaController {

    @Autowired
    private UserService userService;

    @Autowired
    private RezervacijaService rezService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @RequestMapping(method = RequestMethod.GET,value = "/api/rezervacija/{id}")
    public Rezervacija getRez(@PathVariable Long id){

        return rezService.getRez(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "api/user/invitation/accept/{username}/event/{rezID}")
    public void acceptRez(HttpServletResponse response, @PathVariable String username, @PathVariable Long rezID){
        RegistrovaniKorisnik k = userService.getUser(username);
        Rezervacija rez = rezService.getRez(rezID);
        for(Poziv p : rez.getUrezervaciji()){
            if(p.getOsoba().getId()==k.getId()){
                p.setStatus(Status.PRIHVACENO);
            }
        }
        rezService.addRez(rez);

        try {
            response.sendRedirect("http://localhost:8096/#!/invitation/"+username+"/event/"+rezID);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "api/user/invitation/decline/{username}/event/{rezID}")
    public void declineRez(HttpServletResponse response, @PathVariable String username, @PathVariable Long rezID){
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

        try {
            response.sendRedirect("http://localhost:8096/#!/invitation/"+username+"/event/"+rezID);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
