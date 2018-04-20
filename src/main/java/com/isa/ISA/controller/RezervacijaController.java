package com.isa.ISA.controller;

import com.isa.ISA.DTO.InvitationDTO;
import com.isa.ISA.dbModel.Projekcija;
import com.isa.ISA.dbModel.Rezervacija;
import com.isa.ISA.dbModel.Sediste;
import com.isa.ISA.dbModel.enums.Status;
import com.isa.ISA.dbModel.enums.TipAdmina;
import com.isa.ISA.dbModel.korisnici.Poziv;
import com.isa.ISA.dbModel.korisnici.RegistrovaniKorisnik;
import com.isa.ISA.repository.PozivRepository;
import com.isa.ISA.repository.RezervacijaRepository;
import com.isa.ISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RezervacijaController {

    @Autowired
    private UserService userService;

    @Autowired
    private RezervacijaService rezService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private PozivRepository pozivRepo;

    @RequestMapping(method = RequestMethod.GET,value = "/api/rezervacija/{id}")
    public Rezervacija getRez(@PathVariable Long id){

        return rezService.getRez(id);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/api/rezervacija/delete/{id}")
    public void deleteRez(@PathVariable Long id){
        rezService.delete(id);
    }

    @RequestMapping("/api/quickRezervacije/{pbId}")
    public List<Rezervacija> getQuicRez(@PathVariable Long pbId){
        return rezService.getQuckRez(pbId);
    }

    @RequestMapping("/api/quickRezervacije/{id}/admin/{pbId}")
    public List<Rezervacija> getQuicRezAdmin(@PathVariable Long id, @PathVariable Long pbId){
        if(adminService.getAdmin(id).getTip() !=TipAdmina.POZBI)
            return null;

        return rezService.getQuckRezAdmin(pbId);
    }

    @RequestMapping("/api/rezervacije/")
        public List<Rezervacija> getAll(){
        return  rezService.getAll();
    }


    @RequestMapping(method = RequestMethod.POST,value = "/api/rezervacija/acc/{username}")
    public List<Poziv> getInvAccepted(@PathVariable String username, @RequestBody Long id) {
        RegistrovaniKorisnik reg = userService.getUser(username);
            System.out.println("ACCEPTED");
            int bodovi = 0;

            for(Rezervacija r : reg.getRezervacije())
                if(r.getId()==id)
                    for(Poziv p : r.getUrezervaciji())
                        if(p.getOsoba().getUserName().equals(username)){
                            p.setStatus(Status.PRIHVACENO);
                            rezService.addRez(r);
                            bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
                            break;
                        }
            bodovi = bodovi + reg.getBodovi();
            reg.setBodovi(bodovi);
            ArrayList<Poziv> samoPrihvacene = new ArrayList<>();
            for(Rezervacija r : reg.getRezervacije())
                for(Poziv p : r.getUrezervaciji())
                    if(p.getOsoba().getUserName().equals(username))
                        if(!p.getStatus().toString().equals("ODBIJENO"))
                            samoPrihvacene.add(p);

        userService.addUser(reg);
        return samoPrihvacene;


    }


    @RequestMapping(method = RequestMethod.POST,value = "/api/rezervacija/decl/{username}")
    public List<Poziv> getInvDecl(@PathVariable String username, @RequestBody Long id) {
        RegistrovaniKorisnik reg = userService.getUser(username);

        System.out.println("DECLINED");
        int bodovi = 0;
        Sediste zaVracanje = null;
        for(Rezervacija r : reg.getRezervacije())
            if(r.getId()==id)
                for(Poziv p : r.getUrezervaciji())
                    if(p.getOsoba().getUserName().equals(username)){
                         p.setStatus(Status.ODBIJENO);
                         pozivRepo.save(p);
                         zaVracanje = p.getKarta().getSediste();
                         bodovi = r.getProjekcija().getDogadjaj().getDonosiBodova();
                         rezService.addRez(r);
                         break;
                    }
            bodovi = reg.getBodovi() - bodovi;
            if(bodovi<0) bodovi = 0;
            ArrayList<Poziv> samoPrihvacene = new ArrayList<>();
            for(Rezervacija r : reg.getRezervacije())
                for(Poziv p : r.getUrezervaciji())
                    if(p.getOsoba().getUserName().equals(username))
                        if(!p.getStatus().toString().equals("ODBIJENO"))
                            samoPrihvacene.add(p);
            reg.setBodovi(bodovi);
            userService.addUser(reg);

            if(zaVracanje!=null){
                Rezervacija rez = rezService.getRez(id);
                int idx = -1;
                for(int i = 0; i < rez.getProjekcija().getZauzetaSedista().size(); i++)
                    if(rez.getProjekcija().getZauzetaSedista().get(i).getId()==zaVracanje.getId()) {
                        idx = i;
                        break;
                    }

            if(idx!=-1)
                rez.getProjekcija().getZauzetaSedista().remove(idx);

            projekcijaService.addProjekcija(rez.getProjekcija());
            rezService.addRez(rez);
            }
            return samoPrihvacene;
        }



}
