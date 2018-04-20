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


    @RequestMapping(method = RequestMethod.POST,value = "/api/reserve")
    public void reserve(@RequestBody  RezervacijaDTO r){
        rezService.reserve(r);
    }


}
