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
    private RezervacijaService rezService;


    @Autowired
    private AdminService adminService;

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
        return rezService.acceptInvitation(username, id);
    }


    @RequestMapping(method = RequestMethod.POST,value = "/api/rezervacija/decl/{username}")
    public List<Poziv> getInvDecl(@PathVariable String username, @RequestBody Long id) {
        return rezService.declineInvitation(username, id);
        }



}
